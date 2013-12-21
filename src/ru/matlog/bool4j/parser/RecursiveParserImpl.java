package ru.matlog.bool4j.parser;

import ru.matlog.bool4j.expression.Constant;
import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.ValidationException;
import ru.matlog.bool4j.expression.Variable;
import ru.matlog.bool4j.expression.function.Function;
import ru.matlog.bool4j.expression.function.Functions;
import ru.matlog.bool4j.expression.operator.Operator;
import ru.matlog.bool4j.expression.operator.Operators;

public class RecursiveParserImpl implements Parser{

	@Override
	public Expression parse( String string) {

        
        String string2 = new String (string);
        string2 = string2.replaceAll("\\(", "");
        string2 = string2.replaceAll("\\)", "");
        string2 = string2.replace(Operators.DISJUNCTION.REPRESENTATION, "");
        string2 = string2.replace(Operators.CONJUNCTION.REPRESENTATION, "");
        string2 = string2.replace(Operators.IMPLICATION.REPRESENTATION, "");
        string2 = string2.replace(Operators.EQUAL.REPRESENTATION, "");
        string2 = string2.replace(Operators.XOR.REPRESENTATION, "");
        string2 = string2.replaceAll("   ", "");
        string2 = string2.replaceAll("  ", "");
        string2 = string2.replaceAll("  ", "");
        string2 = string2.trim();
        if(string2.isEmpty()){
    	throw new ValidationException("Отсутствуют переменные");
}
            
            
        string = string.replace("( ", "(");
        string = string.replace(") ", ")");
        string = string.replace(Operators.DISJUNCTION.REPRESENTATION, " "+Operators.DISJUNCTION.REPRESENTATION+" ");
        string = string.replace(Operators.CONJUNCTION.REPRESENTATION, " "+Operators.CONJUNCTION.REPRESENTATION+" ");
        string = string.replace(Operators.IMPLICATION.REPRESENTATION, " "+Operators.IMPLICATION.REPRESENTATION+" ");
        string = string.replace(Operators.EQUAL.REPRESENTATION, " "+Operators.EQUAL.REPRESENTATION+" ");
        string = string.replace(Operators.XOR.REPRESENTATION, " "+Operators.XOR.REPRESENTATION+" ");
        string = string.replace("   ", " ");
        string = string.replace("  ", " ");
        
        
if(string.indexOf("()")!=-1){
    	throw new ValidationException("Пустые скобки");
}
        
 

        
        string = string.trim();


		
		Counter count = new Counter();
		
		

		
		Expression e =  parse('(' + string + ')', count);
                

		if (e == null) {
			throw new ValidationException("Пустое выражение");
		}
                
		e.validate();
		return e;
	}
	
    public Expression parse(final String string, final Counter count) {
                boolean isVariable = false;
                boolean isConstant = false;
                boolean isOperator = false;
                boolean isFunction = false;
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
                                if (tmp.toString().length() > 0) {
                                        //это часть переменной
                                } else {
                                        isConstant = true;
                                        tmp.append(c);
                                        count.i = count.i + 2;
                                        break;
                                }
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
                                isFunction = true;
                                Function f = Functions.getFunction(tmp.toString());
                                exp = f;
                                Expression args[] = parseArgs(string.substring(count.i + 1), count);
                                f.setArguments(args);
                                break;
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
                if (tmp.toString().length() > 0 && !isConstant && !isVariable && !isFunction) {
                        Variable var = new Variable();
                        var.setVariable(tmp.toString());
                        exp = var;
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
        
        private Expression[] parseArgs(final String str, final Counter count) {
                String argsString = extractArgsString(str);
                count.i += argsString.length() + 2;
                argsString = argsString.substring(1, argsString.length() - 1);
                String[] args = argsString.split(",");
                Expression[] arguments = new Expression[args.length];
                for (int i = 0; i < args.length; i++) {
                        String arg = args[i];
                        StringBuilder sb = new StringBuilder();
                        sb.append("(");
                        sb.append(arg);
                        sb.append(")");
                        arg = sb.toString();
                        arguments[i] = parse(arg);
                }
                return arguments;
        }
        
        private String extractArgsString(final String str) {
                int parenthesesCount = 0;
                if (str.charAt(0) != '(') {
                        //throw exception
                } else {
                        parenthesesCount++;
                }
                int i = 1;
                while(parenthesesCount > 0) {
                        if (str.charAt(i) == '(') {
                                parenthesesCount++;
                        } else if (str.charAt(i) == ')') {
                                parenthesesCount--;
                        }
                        i++;
                }
                return str.substring(0, i);
        }
        
        private class Counter {
                int i = 0;
        }
}
