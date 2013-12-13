package ru.matlog.bool4j.expression.operator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.ExpressionType;

/**
 *
 * @author Max
 */
public abstract class Operator extends Expression {

    private Expression firstOperand;
    private Expression secondOperand;

    public Expression getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(Expression firstOperand) {
        this.firstOperand = firstOperand;
    }

    public Expression getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(Expression secondOperand) {
        this.secondOperand = secondOperand;
    }
    
    public boolean firstOperandSet() {
    	return firstOperand != null;
    }
    
    public boolean secondOperandSet() {
    	return secondOperand != null;
    }

    public abstract String getStringRepresentation();

    public abstract boolean apply(final Map<String, Boolean> variables);

    @Override
    public Set<String> getVariablesNames() {
        Set<String> set = new HashSet<>();
        set.addAll(getFirstOperand().getVariablesNames());
        set.addAll(getSecondOperand().getVariablesNames());
        return set;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(firstOperand.toString());
        builder.append(" ");
        builder.append(getStringRepresentation());
        builder.append(" ");
        builder.append(secondOperand.toString());
        builder.append(")");
        return builder.toString();
    }
    
    public ExpressionType getType() {
        return ExpressionType.OPERATOR;
    }

    @Override
    public Boolean calculate(Map<String, Boolean> variables) {
        return apply(variables);
    }

}
