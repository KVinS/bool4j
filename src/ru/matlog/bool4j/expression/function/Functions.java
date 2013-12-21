package ru.matlog.bool4j.expression.function;

import java.util.HashMap;
import java.util.Map;

import ru.matlog.bool4j.expression.Expression;
/**
 * Класс для хранения всех функций
 * @author Семён
 *
 */
public final class Functions {
    private static final Map<String, Class> functions = new HashMap<String, Class>();

	
	public static final class NEGATIVE extends Function {

		@Override
		public boolean apply(Map<String, Boolean> variables) {
			int quantity = getArgumentQuantity();
			Expression arguments[] = getArguments();
			Boolean val = arguments[0].calculate(variables);
			val = !val;
			return val;
		}

		@Override
		public void init() {
			setArgumentQuantity(1);
			setStringRepresentation("neg");
		}
	}; 
	
	static {
		functions.put("neg", NEGATIVE.class);
	}
	
	public static void add(final Class clazz, final String representation) {
		functions.put(representation, clazz);
	}
	
	public static boolean contains(final String str) {
		return functions.containsKey(str);
	}
	
	/**
	 * Получение функции по её строковому представлению
	 * @param representation строковое представление
	 * @return функция
	 */
	public static Function getFunction(final String representation) {
		Class clazz = functions.get(representation);
		Function o = null;
		try {
			o = (Function) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return o;
	}
}
