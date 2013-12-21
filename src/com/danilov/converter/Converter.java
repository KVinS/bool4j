package com.danilov.converter;

import ru.matlog.bool4j.expression.Expression;

/**
 * Интерфейс конвертирования выражения
 * в разные виды (полином Жегалкина, СДНФ, etc)
 */
public interface Converter {
	
	/**
	 * конвертация
	 * @return 
	 */
	Expression convert(final Expression expression);
	
}
