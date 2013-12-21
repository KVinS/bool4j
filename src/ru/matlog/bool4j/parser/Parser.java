package ru.matlog.bool4j.parser;

import ru.matlog.bool4j.expression.Expression;

/**
 * "Перевод" строки в формат Expression
 * @author Семён
 *
 */
public interface Parser {
	
	/**
	 * Разбор строки
	 * @param input строка, содержащаю выражение
	 * @return выражение в виде Expression
	 */
	public Expression parse(final String input);
	
}
