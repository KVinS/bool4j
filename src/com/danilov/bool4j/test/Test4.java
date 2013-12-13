package com.danilov.bool4j.test;

import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.parser.Parser;
import ru.matlog.bool4j.parser.RecursiveParserImpl;

import com.danilov.bool4j.util.Util;
import com.danilov.converter.Converter;
import com.danilov.converter.SDNFConverter;
import com.danilov.converter.JPolynomConverter;
import com.danilov.converter.SKNFConverter;

public class Test4 implements ITest {

	@Override
	public void test() {
		Parser p = new RecursiveParserImpl();
		Expression exp = p.parse("((x -> (y -> z)) * (x + y))");
		Converter c = new SDNFConverter();
		Expression exp1 = c.convert(exp);
		if (exp1 != null) {
			Util.Log(exp1.toString());
		}
		c = new SKNFConverter();
		exp1 = c.convert(exp);
		if (exp1 != null) {
			Util.Log(exp1.toString());
		}
		c = new JPolynomConverter();
		c.convert(exp);
	}

}
