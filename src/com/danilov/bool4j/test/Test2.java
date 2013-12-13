package com.danilov.bool4j.test;

import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.parser.Parser;
import ru.matlog.bool4j.parser.RecursiveParserImpl;

import com.danilov.bool4j.util.Util;

public class Test2 implements ITest{

	@Override
	public void test() {
		Parser p = new RecursiveParserImpl();
		Expression exp = p.parse("(x + 0 + z + z + y + 1)");
		Util.Log("Desire: (x + 0 + z + z + y + 1)");
		Util.Log("Result:" + exp.toString());
	    exp = p.parse("(x + y + (z + 1 + y) + u)");
		Util.Log("Desire: (x + y + (z + 1 + y) + u)");
		Util.Log("Result:" + exp.toString());
	    exp = p.parse("(x + y + (((z <=> z) xor 1)) + u)");
		Util.Log("Desire: (x + y + (((z <=> z) xor 1)) + u)");
		Util.Log("Result:" + exp.toString());
		exp = p.parse("(x + (y + ((z -> z) xor y)) + u)");
		Util.Log("Desire: (x + (y + ((z -> z) xor y)) + u)");
		Util.Log("Result:" + exp.toString());
	}

}
