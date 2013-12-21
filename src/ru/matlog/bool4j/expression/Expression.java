package ru.matlog.bool4j.expression;

import java.util.Map;
import java.util.Set;

public abstract class Expression {
	
	/**
	 * Вычисление значения выражения
	 * @param variables список пар "переменная - значение"
	 * @return вычисленное значение
	 */
    public abstract Boolean calculate(final Map<String, Boolean> variables);
    
    /**
     * 
     * @return получение списка переменных
     */
    public abstract Set<String> getVariablesNames();
        
    /**
     * 
     * @return тип выражения (константа, функция, переменная etc)
     */
    public abstract ExpressionType getType();
    
    /**
     * Получение объекта типа calculable для дальнейших вычислений
     * @param calculableFactory фабрика calculable
     * @return calculable-объект
     */
    public Calculable toCalculable(final CalculableFactory calculableFactory) {
		return calculableFactory.newCalculable(this); 
    }
    
    /**
     * 
     * @return является выражение правильно сформированным
     */
    public abstract void validate();
    
    @Override
    public abstract String toString();
    
}
