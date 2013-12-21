package ru.matlog.bool4j.expression.function;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.ExpressionType;
import ru.matlog.bool4j.expression.ValidationException;
/**
 * Функция
 * @author Max
 *
 */
public abstract class Function extends Expression {
	private int argumentQuantity;
	private Expression arguments[];
	private String stringRepresentation;
	
	public Function() {
		init();
	}
	
	public void setArgumentQuantity(int argumentQuantity) {
		this.argumentQuantity = argumentQuantity;
	}

	public abstract void init();
	
	public abstract boolean apply(final Map<String, Boolean> variables);

	public Expression[] getArguments() {
		return arguments;
	}

	public void setArguments(Expression... arguments) {
		this.arguments = arguments;
	}

	public int getArgumentQuantity() {
		return argumentQuantity;
	}
	
	public String getStringRepresentation() {
		return stringRepresentation;
	}


	public void setStringRepresentation(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	public Set<String> getVariablesNames() {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < argumentQuantity; i++) {
			set.addAll(arguments[i].getVariablesNames());
		}
		return set;
	}
	
    @Override
    public Boolean calculate(Map<String, Boolean> variables) {
        return apply(variables);
    }
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getStringRepresentation()).append("(");
		Expression exprs[] = getArguments();
		for (int i = 0; i < getArgumentQuantity(); i++) {
			Expression e = exprs[i];
			builder.append(e.toString());
			if (getArgumentQuantity() > 1) {
				builder.append(",");
			}
		}
		builder.append(")");
		return builder.toString();
	}
	
    public ExpressionType getType() {
        return ExpressionType.FUNCTION;
    }

	@Override
	public void validate() {
		for (int i = 0; i < getArgumentQuantity(); i++) {
			Expression e = getArguments()[i];
			if (e == null) {
				throw new ValidationException("Не хватает " + i + "-ого аргумента в функции [" + getStringRepresentation() 
						+ "]");
			}
			getArguments()[i].validate();
		}
	}

}