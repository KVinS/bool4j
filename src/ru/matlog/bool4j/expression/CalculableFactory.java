package ru.matlog.bool4j.expression;

/**
 * Просто фабрика Calculable, для абстракции
 * @author Семён
 *
 */
public interface CalculableFactory {
	Calculable newCalculable(final Expression expr);
}
