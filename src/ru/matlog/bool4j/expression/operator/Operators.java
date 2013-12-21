package ru.matlog.bool4j.expression.operator;

import java.util.HashMap;
import java.util.Map;

import ru.matlog.bool4j.expression.ExpressionType;

/**
 * Класс для хранения всех стандартных операторов + есть возможность расширения
 * @author Семён
 *
 */
public  class Operators {

    private static  Map<String, Class> operators;

    //multiply
    public static  class CONJUNCTION extends Operator {
        public static String REPRESENTATION = "*";

        @Override
        public boolean apply(Map<String, Boolean> variables) {
            Boolean first = getFirstOperand().calculate(variables);
            Boolean second = getSecondOperand().calculate(variables);
            return first && second;
        }

        @Override
        public String getStringRepresentation() {
            return REPRESENTATION;
        }

        @Override
        public ExpressionType getType() {
            return ExpressionType.OPERATOR;
        }
    }

    //sum
    public static  class DISJUNCTION extends Operator {
        public static  String REPRESENTATION = "+";

        @Override
        public boolean apply(Map<String, Boolean> variables) {
            Boolean first = getFirstOperand().calculate(variables);
            Boolean second = getSecondOperand().calculate(variables);
            return first || second;
        }

        @Override
        public String getStringRepresentation() {
            return REPRESENTATION;
        }

        @Override
        public ExpressionType getType() {
            return ExpressionType.OPERATOR;
        }
    }

    //->
    public static  class IMPLICATION extends Operator {
        public static  String REPRESENTATION = "->";

        @Override
        public boolean apply(Map<String, Boolean> variables) {
            Boolean first = getFirstOperand().calculate(variables);
            Boolean second = getSecondOperand().calculate(variables);
            return !first || second;
        }

        @Override
        public String getStringRepresentation() {
            return REPRESENTATION;
        }

        @Override
        public ExpressionType getType() {
            return ExpressionType.OPERATOR;
        }
    }

    //xor
    public static  class XOR extends Operator {
        public static  String REPRESENTATION = "xor";

        @Override
        public boolean apply(Map<String, Boolean> variables) {
            Boolean first = getFirstOperand().calculate(variables);
            Boolean second = getSecondOperand().calculate(variables);
            return first ^ second;
        }

        @Override
        public String getStringRepresentation() {
            return REPRESENTATION;
        }

        @Override
        public ExpressionType getType() {
            return ExpressionType.OPERATOR;
        }
    }

    //equ
    public static  class EQUAL extends Operator {
        public static  String REPRESENTATION = "<=>";

        @Override
        public boolean apply(Map<String, Boolean> variables) {
            Boolean first = getFirstOperand().calculate(variables);
            Boolean second = getSecondOperand().calculate(variables);
            return (!first || second) && (first || !second);
        }

        @Override
        public String getStringRepresentation() {
            return REPRESENTATION;
        }

        @Override
        public ExpressionType getType() {
            return ExpressionType.OPERATOR;
        }
    }

    static { refreshOperators();
    }
    
    public static void refreshOperators(){
        operators = new HashMap<String, Class>();
        operators.put(DISJUNCTION.REPRESENTATION, DISJUNCTION.class);
        operators.put(CONJUNCTION.REPRESENTATION, CONJUNCTION.class);
        operators.put(IMPLICATION.REPRESENTATION, IMPLICATION.class);
        operators.put(XOR.REPRESENTATION, XOR.class);
        operators.put(EQUAL.REPRESENTATION, EQUAL.class);
    }

    public static void add( Class operator,  String representation) {
        operators.put(representation, operator);
    }

	public static boolean contains( String str) {
		return operators.containsKey(str);
	}
	
	/**
	 * Получение оператора по его строковому представлению
	 * @param representation строковое представление
	 * @return оператор
	 */
    public static Operator getOperator( String representation) {
        Class clazz = operators.get(representation);
        Operator o = null;
        try {
            o = (Operator) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}
