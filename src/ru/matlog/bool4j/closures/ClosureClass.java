package ru.matlog.bool4j.closures;

import ru.matlog.bool4j.expression.Expression;
/**
 * Интерфейс, описывающий работу с классами закнутости
 * T0, T1, S (самодвойственность), L (линейность), M (монотонность)
 * @author Max
 *
 */
public abstract class ClosureClass {
	/**
	 * Принадлежит ли выражение некоему классу
	 * @param expr
	 * @return
	 */
	public abstract boolean whetherBelongs(final Expression expr);
	
	/**
	 * Получение строкового представления класса замкнутости, например "T0"
	 * @return
	 */
	public abstract String getStringRepresentation();
}
