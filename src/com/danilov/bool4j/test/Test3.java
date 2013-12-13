package com.danilov.bool4j.test;

import java.util.List;
import java.util.Map;

import ru.matlog.bool4j.expression.Calculable;
import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.RecursiveCalculableFactoryImpl;
import ru.matlog.bool4j.parser.Parser;
import ru.matlog.bool4j.parser.RecursiveParserImpl;

import com.danilov.bool4j.util.Util;
import com.danilov.bool4j.util.VariablesSet;

public class Test3 implements ITest {

	@Override
	public void test() {
		Parser p = new RecursiveParserImpl();
		Expression exp = p.parse("((y xor x) xor ((x * y) * z))");
		Util.Log(exp.toString());
		Calculable calc = exp.toCalculable(new RecursiveCalculableFactoryImpl());
		VariablesSet varSet = new VariablesSet(calc.getVariableNames());
		List<String> vv = varSet.getKeySet();
		for (String key : vv) {
			Util.LogNoLn(key + " ");
		}
		Util.Log("f");
		while(varSet.notEnd()) {
			Map<String, Boolean> m = varSet.get();
			calc.with(m);
			for (String key : vv) {
				Boolean var = m.get(key);
				String s;
				if (var) {
					s = "1";
				} else {
					s = "0";
				}
				Util.LogNoLn(s + " ");
			}
			Boolean val = calc.calculate();
			String s;
			if (val) {
				s = "1";
			} else {
				s = "0";
			}
			Util.LogNoLn(s + " ");
			Util.Log("");
			varSet.moveToNext();
		}
	}

}
