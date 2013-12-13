package com.danilov.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.matlog.bool4j.expression.Calculable;
import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.RecursiveCalculableFactoryImpl;
import ru.matlog.bool4j.expression.Variable;
import ru.matlog.bool4j.expression.function.Function;
import ru.matlog.bool4j.expression.function.Functions;
import ru.matlog.bool4j.expression.operator.Operator;
import ru.matlog.bool4j.expression.operator.Operators;

import com.danilov.bool4j.util.Util;
import com.danilov.bool4j.util.VariablesSet;

public class SDNFConverter implements Converter {

	@Override
	public Expression convert(final Expression expression) {
		Calculable calculable = expression.toCalculable(new RecursiveCalculableFactoryImpl());
		List<Map<String, Boolean>> variablesSets = new ArrayList<Map<String, Boolean>>();
		Set<String> vars = expression.getVariablesNames();
		VariablesSet variablesSet = new VariablesSet(vars);
		while(variablesSet.notEnd()) {
			Map<String, Boolean> variables = variablesSet.get();
			Boolean calculatedValue = calculable.with(variables).calculate();
			if (calculatedValue) {
				variablesSets.add(Util.cloneMap(variables));
			}
			variablesSet.moveToNext();
		}
		if (variablesSets.size() == 0) {
			return null;
		}
		return toExpression(variablesSets, variablesSet.getKeySet());
	}
	
	private Expression toExpression(final List<Map<String, Boolean>> variablesSets, final List<String> keys) {
		Expression finalResult = null;
		Operator res = Operators.getOperator("+");
		Operator tmp;
		Operator tmp2;
		for (Map<String, Boolean> set : variablesSets) {
			tmp = Operators.getOperator("*");
			for (String key : keys) {
				Expression exp;
				Boolean val = set.get(key);
				Variable variable = new Variable();
				variable.setVariable(key);
				exp = variable;
				if (!val) {
					Function f = Functions.getFunction("neg");
					f.setArguments(exp);
					exp = f;
				}
				if (!tmp.firstOperandSet()) {
					tmp.setFirstOperand(exp);
				} else if (tmp.firstOperandSet() && tmp.secondOperandSet()) {
					tmp2 = tmp;
					tmp = Operators.getOperator("*");
					tmp.setFirstOperand(tmp2);
					tmp.setSecondOperand(exp);
				} else if (tmp.firstOperandSet()){
					tmp.setSecondOperand(exp);
				}
			}
			Expression mulRes = tmp;
			if (tmp.getSecondOperand() == null) {
				mulRes = tmp.getFirstOperand();
			}
			if (!res.firstOperandSet()) {
				res.setFirstOperand(mulRes);
			} else if (res.firstOperandSet() && res.secondOperandSet()) {
				tmp2 = res;
				res = Operators.getOperator("+");
				res.setFirstOperand(tmp2);
				res.setSecondOperand(mulRes);
			} else if (res.firstOperandSet()) {
				res.setSecondOperand(mulRes);
			}
		}
		finalResult = res;
		if (!res.secondOperandSet()) {
			finalResult = res.getFirstOperand();
		}
		return finalResult;
	}

}
