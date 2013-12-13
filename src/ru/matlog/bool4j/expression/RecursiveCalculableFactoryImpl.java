package ru.matlog.bool4j.expression;

public class RecursiveCalculableFactoryImpl implements CalculableFactory {
    @Override
    public Calculable newCalculable(final Expression expression) {
        return new CalculableRecursiveImpl(expression);
    }
}