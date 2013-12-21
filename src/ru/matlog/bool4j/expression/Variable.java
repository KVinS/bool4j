/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.matlog.bool4j.expression;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Переменная
 * @author Max
 */
public class Variable extends Expression {
	
    String var;

    @Override
    public Boolean calculate(Map<String, Boolean> variables) {
        if (!variables.containsKey(var))
                throw new CalculationException("Variable " + var + " is not specified!");
        
        return variables.get(var);
    }
    
    public void setVariable(final String variable) {
    	this.var = variable;
    }
    
    public String getVariable() {
    	return var;
    }

    @Override
    public Set<String> getVariablesNames() {
        Set<String> set = new HashSet<String>();
        set.add(var);
        return set;
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.VARIABLE;
    }

	@Override
	public void validate() {
		if (var.equals(" ")) {
			throw new ValidationException("Переменная пробел - не лучший выбор");
		}
	}
    
}
