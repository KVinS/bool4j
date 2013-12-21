package ru.matlog.bool4j.expression;

import java.util.Map;
import java.util.Set;

/**
 * Интерфейс, реализующий основную логику вычислений, так же хранит переменные
 * @author Семён
 *
 */

public interface Calculable {
	
	/**
	 * Вычисление
	 * @return вычисленное значение
	 */
	public boolean calculate();
	
	/**
	 * Добавить в список значений переменных новую переменную
	 * @param variableName имя переменной
	 * @param variableValue значение переменной
	 * @return 
	 */
	public Calculable with(final String variableName, final Boolean variableValue);
	
	/**
	 * Добавить сразу несколько значений переменных
	 * @param variables список значений
	 * @return
	 */
	public Calculable with(final Map<String, Boolean> variables);
 
	/**
	 * 
	 * @return имена всех переменных
	 */
	public Set<String> getVariableNames();
}
