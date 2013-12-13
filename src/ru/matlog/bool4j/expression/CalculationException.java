package ru.matlog.bool4j.expression;

public class CalculationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CalculationException(final String message) {
		super(message);
	}
}