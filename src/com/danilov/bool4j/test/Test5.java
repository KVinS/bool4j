package com.danilov.bool4j.test;
import java.util.HashMap;

import ru.matlog.bool4j.closures.ClosureClass;
import ru.matlog.bool4j.closures.ClosureClasses;
import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.parser.Parser;
import ru.matlog.bool4j.parser.RecursiveParserImpl;

import com.danilov.bool4j.util.Util;


public class Test5 implements ITest {

	@Override
	public void test() {
		Parser p = new RecursiveParserImpl();
		Expression expr = p.parse("(x + y * z)");
		
		HashMap<String, Boolean> mp = new HashMap<>();
		
		mp.put("x", false);
		mp.put("y", false);
		mp.put("z", false);
	
		Util.Log("Expression: " + expr.toString());
		ClosureClass cc = ClosureClasses.getClosureClass("T0");
		Util.Log("Accessory to the " + cc.getStringRepresentation() + " closure class: " + cc.whetherBelongs(expr, mp));
		cc = ClosureClasses.getClosureClass("T1");
		Util.Log("Accessory to the " + cc.getStringRepresentation() + " closure class: " + cc.whetherBelongs(expr, mp));
		cc = ClosureClasses.getClosureClass("S");
		Util.Log("Accessory to the " + cc.getStringRepresentation() + " closure class: " + cc.whetherBelongs(expr, mp));
		cc = ClosureClasses.getClosureClass("M");
		Util.Log("Accessory to the " + cc.getStringRepresentation() + " closure class: " + cc.whetherBelongs(expr, mp));
		
		expr = p.parse("1");
		Util.Log("Expression: " + expr.toString());
		cc = ClosureClasses.getClosureClass("T1");
		Util.Log("Accessory to the " + cc.getStringRepresentation() + " closure class: " + cc.whetherBelongs(expr, null));
		expr = p.parse("0");
		Util.Log("Expression: " + expr.toString());
		cc = ClosureClasses.getClosureClass("T1");
		Util.Log("Accessory to the " + cc.getStringRepresentation() + " closure class: " + cc.whetherBelongs(expr, null));
		expr = p.parse("0");
		Util.Log("Expression: " + expr.toString());
		cc = ClosureClasses.getClosureClass("T0");
		Util.Log("Accessory to the " + cc.getStringRepresentation() + " closure class: " + cc.whetherBelongs(expr, null));
		
		Util.Log("P.S. Real accessory is not tested yet.");
	}

}



