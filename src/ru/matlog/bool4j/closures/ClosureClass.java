package ru.matlog.bool4j.closures;

import java.util.Map;

import ru.matlog.bool4j.expression.Expression;

public abstract class ClosureClass {
	public abstract boolean whetherBelongs(final Expression expr, final Map<String, Boolean> vars);
	public abstract String getStringRepresentation();
}
