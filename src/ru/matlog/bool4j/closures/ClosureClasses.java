package ru.matlog.bool4j.closures;

import com.danilov.bool4j.util.VariablesSet;
import com.danilov.converter.JPolynomConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import ru.matlog.bool4j.expression.Calculable;
import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.ExpressionType;
import ru.matlog.bool4j.expression.RecursiveCalculableFactoryImpl;
import ru.matlog.bool4j.expression.operator.Operator;
import ru.matlog.bool4j.expression.operator.Operators.CONJUNCTION;
import ru.matlog.bool4j.expression.operator.Operators.XOR;


/**
 * Класс для хранения всех видов классов замкнутости
 * 
 * @author Max
 *
 */
public class ClosureClasses {

	/**
	 * Список классов
	 */
	private static final Map<String, Class> classes = new HashMap<String, Class>();

	public static final class T0 extends ClosureClass {
		// T0
		public static final String REPRESENTATION = "T0";

		@Override
		public boolean whetherBelongs(final Expression expr) {
			HashMap<String, Boolean> variables = new HashMap<String, Boolean>();

			for (String var_name : expr.getVariablesNames()) {
				variables.put(var_name, false);
			}

			return expr.calculate(variables) == false;
		}

		@Override
		public String getStringRepresentation() {
			return REPRESENTATION;
		}
	}

	public static final class T1 extends ClosureClass {
		// T1
		public static final String REPRESENTATION = "T1";

		@Override
		public boolean whetherBelongs(final Expression expr) {
			HashMap<String, Boolean> variables = new HashMap<String, Boolean>();

			for (String var_name : expr.getVariablesNames()) {
				variables.put(var_name, true);
			}

			return expr.calculate(variables) == true;
		}

		@Override
		public String getStringRepresentation() {
			return REPRESENTATION;
		}
	}

	public static final class S extends ClosureClass {
		// S
		public static final String REPRESENTATION = "S";

		@Override
		public boolean whetherBelongs(final Expression expr) {
			List<Expression> expressionsList = new LinkedList<Expression>();
			expressionsList.add(expr);
			VariablesSet vars = new VariablesSet(expr.getVariablesNames());
			boolean belongs = true;
			int n = vars.getKeySet().size(); // Количество переменных
			int size = (int) Math.pow(2, n);
			Calculable calc = expr.toCalculable(new RecursiveCalculableFactoryImpl());
			for(int i = 0; i < (size / 2); i++) {
				Pair p1 = getVariableSetStringAndValue(calc, i, vars.getKeySet());
				Pair p2 = getVariableSetStringAndValue(calc, size - 1 - i, vars.getKeySet());
				if (p1.val != p2.val) {
					belongs = false;
					break;
				}
			}
			return belongs;
		}

		@Override
		public String getStringRepresentation() {
			return REPRESENTATION;
		}
	}

	public static final class M extends ClosureClass {
		// M
		public static final String REPRESENTATION = "M";

		@Override
		public boolean whetherBelongs(final Expression expr) {
			ArrayList<Expression> expr_lst = new ArrayList<Expression>();
			VariablesSet vars = new VariablesSet(expr.getVariablesNames());
			int n = vars.getKeySet().size(); // Количество переменных
			int n2 = (int) Math.pow(2, n);
			
			Calculable calc = expr.toCalculable(new RecursiveCalculableFactoryImpl());
			for (int i = 0; i < n2; i++) {
				Pair iPair = getVariableSetStringAndValue(calc, i, vars.getKeySet());
				for (int j = i; j < n2; j++) {
					if (i != j) {
						boolean diff = false; // Есть ли различия в двух
												// сравниваемых строках
						boolean brea = false; // Более одного различия в
												// сравниваемых строках -
												// досрочно вышли из цикла

						Pair jPair = getVariableSetStringAndValue(calc, j, vars.getKeySet());
						String str1 = iPair.str;
						String str2 = jPair.str;
						for (int k = 0; k < n; k++) {
							if (str1.charAt(k) != str2.charAt(k)) {
								if (diff) {
									brea = true;
									break;
								}
								
								diff = true;
							}
						}

						if (!brea) {
							// Рассматриваемые строки сравнимы - будем
							// сравнивать
							boolean op_i = iPair.val;
							boolean op_j = jPair.val;

							if (op_i != op_j && op_i == true) {
								return false;
							}
						}

					}
				}
			}

			return true;
		}
		
		

		@Override
		public String getStringRepresentation() {
			return REPRESENTATION;
		}
	}
		
	public static final class L extends ClosureClass {
	    public static final String REPRESENTATION = "L";
	    
	    private boolean isLinear(final Expression expr) {
	            if (expr.getType() == ExpressionType.OPERATOR) {
	                    Expression op1 = ((Operator) expr).getFirstOperand();
	                    Expression op2 = ((Operator) expr).getSecondOperand();
	                    
	                    if (expr instanceof CONJUNCTION)
	                            return false;
	                    else
	                    if (expr instanceof XOR)
	                            return isLinear(op1) && isLinear(op2);
	            }
	            
	            return true;
	    }
	
	    @Override
	    public boolean whetherBelongs(final Expression expr) {
	            JPolynomConverter jcon = new JPolynomConverter();
	            Expression jpol = jcon.convert(expr);
	             
	            if (jpol.getType() == ExpressionType.OPERATOR) {
	                    Expression op1 = ((Operator) jpol).getFirstOperand();
	                    Expression op2 = ((Operator) jpol).getSecondOperand();
	                    
	                    return isLinear(op1) && isLinear(op2);
	            }
	            
	            return true;
	   }
	
       @Override
       public String getStringRepresentation() {
               return REPRESENTATION;
       }
	}

	static {
		classes.put(T0.REPRESENTATION, T0.class);
		classes.put(T1.REPRESENTATION, T1.class);
		classes.put(S.REPRESENTATION, S.class);
		classes.put(M.REPRESENTATION, M.class);
		classes.put(L.REPRESENTATION, L.class);
	}

	public static boolean contains(final String key) {
		return classes.containsKey(key);
	}

	/**
	 * Получение объекта класса замкнутости по названию
	 * @param representation строковое представление
	 * @return класс замкнутости
	 */
	public static ClosureClass getClosureClass(final String representation) {
		Class clazz = classes.get(representation);
		ClosureClass cc = null;

		try {
			cc = (ClosureClass) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cc;
	}
	
	private static class Pair {
		String str;
		Boolean val;
	}
	
	private static Pair getVariableSetStringAndValue(final Calculable calc, final int pos, final List<String> variables) {
		Pair p = new Pair();
		StringBuilder stringBuilder = new StringBuilder();
		Map<String, Boolean> vars = new HashMap<String, Boolean>();
		for (int i = variables.size() - 1; i >= 0 ; i--) {
			int pow = (int) Math.pow(2, i);
			int varVal = (pos / pow) % 2;
			boolean boolVal = varVal == 1 ? true : false;
			stringBuilder.append(varVal);
			vars.put(variables.get(variables.size() - i - 1), boolVal);
		}
		Boolean val = calc.with(vars).calculate();
		p.str = stringBuilder.toString();
		p.val = val;
		return p;
	}
}
