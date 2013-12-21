package com.danilov.converter;

/*
 * Конвертер выражения в полином Жегалкина
 */

import com.danilov.bool4j.util.VariablesSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.matlog.bool4j.expression.Calculable;
import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.RecursiveCalculableFactoryImpl;
import ru.matlog.bool4j.expression.operator.Operators;
import ru.matlog.bool4j.parser.Parser;
import ru.matlog.bool4j.parser.RecursiveParserImpl;



public class JPolynomConverter implements Converter {

	@Override
	public Expression convert(final Expression expression) {
		Calculable calc = expression.toCalculable(new RecursiveCalculableFactoryImpl());
		VariablesSet varSet = new VariablesSet(calc.getVariableNames());
		List<String> varKeySet = varSet.getKeySet();
		int size = (int) Math.pow(2, varKeySet.size());
		int prevLen = size;
		boolean[] prev = new boolean[prevLen];
		int counter = 0;
		
		while(varSet.notEnd()) {
			Map<String, Boolean> m = varSet.get();
			calc.with(m);
			Boolean val = calc.calculate();
			prev[counter] = val;
			counter++;
			varSet.moveToNext();
		}
		
		List<Boolean> values = new ArrayList<Boolean>();
		values.add(prev[0]);
		
		while (prevLen > 1) {
			boolean[] tmp = new boolean[prevLen - 1];
			
			for (int i = 0; i < prevLen - 1; i++) {
				tmp[i] = (prev[i] ^ prev[i + 1]);
			}
			
			values.add(tmp[0]);
			prevLen = prevLen - 1;
			prev = tmp;
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("(");
		boolean first = true;
		int i = 0;
		
		for (Boolean value : values) {
			if (value) {
				String var = test(i, varKeySet);
				if (first) {
					first = false;
				} else {
					stringBuilder.append(' ' + Operators.XOR.REPRESENTATION + ' ');
				}
				stringBuilder.append(var);	
			}
			i++;
		}
		
		stringBuilder.append(")");
		String s = stringBuilder.toString();
		Parser parser = new RecursiveParserImpl();
		Expression expr = parser.parse(s);
		
		return expr;
	}
	
	private String test(final int pos, final List<String> variables) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("(");
		boolean first = true;
		boolean atLeastOneSet = false;
		
		for (int i = variables.size() - 1; i >= 0 ; i--) {
			int pow = (int) Math.pow(2, i);
			int segmentNum = pos / pow;
			
			if ((segmentNum % 2) != 0) {
				atLeastOneSet = true;
				String var = variables.get(variables.size() - i - 1);
				
				if (first) {
					first = false;
				} else {
					stringBuilder.append(" * ");
				}
				stringBuilder.append(var);
			}
		}
		
		stringBuilder.append(")");
		String s = stringBuilder.toString();
		
		if (!atLeastOneSet) {
			s = "1";
		}
		
		return s;
	}

}
