package ru.matlog.bool4j.parser;

import ru.matlog.bool4j.expression.Constant;
import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.Variable;
import ru.matlog.bool4j.expression.function.Function;
import ru.matlog.bool4j.expression.function.Functions;
import ru.matlog.bool4j.expression.operator.Operator;
import ru.matlog.bool4j.expression.operator.Operators;

public class RecursiveParserImpl implements Parser{

	@Override
	public Expression parse(final String string) {
		Counter count = new Counter();
		return parse(string, count);
	}
	
	public Expression parse(final String string, final Counter count) {
		boolean isVariable = false;
		boolean isConstant = false;
		boolean isOperator = false;
		boolean parentheses = false;
		
		StringBuilder tmp = new StringBuilder();
		Expression exp = null;
		while (count.i < string.length()) {
			char c = string.charAt(count.i);
			if (c == ' ' || c == ')') {
				isVariable = true;
				count.i++;
				break;
			}
			if ((c == '1' || c == '0')) { 
				isConstant = true;
				tmp.append(c);
				count.i = count.i + 2;
				break;
			}
			if (c != '(') {
				tmp.append(c);
			} else {
				if (parentheses) {
					exp = parse(string, count);
					break;
				}
				parentheses = true;
			}
			if (Functions.contains(tmp.toString())) {
				Function f = Functions.getFunction(tmp.toString());
				exp = f;
				Expression args[] = parseArgs(string.substring(count.i));
				f.setArguments(args);
			}
			count.i++;
		}
		if (isVariable) {
			Variable var = new Variable();
			var.setVariable(tmp.toString());
			exp = var;
		}
		if (isConstant) {
			Constant constant = new Constant();
			exp = constant;
			int a = Integer.valueOf(tmp.toString());
			if (a == 1) {
				constant.setValue(true);
			} else {
				constant.setValue(false);
			}
		}
		if (!parentheses) {
			return exp;
		}
		while (true) {
			tmp = new StringBuilder();
			while (count.i < string.length()) {
				char c = string.charAt(count.i);
				if (c == ' ' || c == ')') {
					count.i++;
					return exp;
				}
				tmp.append(c);
				if (Operators.contains(tmp.toString())) {
					isOperator = true;
					count.i += 2;
					break;
				}
				count.i++;
			}
			if (isOperator) {
				Operator op = Operators.getOperator(tmp.toString());
				op.setFirstOperand(exp);
				exp = parse(string, count);
				op.setSecondOperand(exp);
				exp = op;
			}
			if (count.i >= string.length()) {
				break;
			}
		}
		return exp;
	}
	
	private Expression[] parseArgs(final String str) {
		return null;
	}
	
	private class Counter {
		int i = 0;
	}

}
