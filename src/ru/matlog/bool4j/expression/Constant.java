/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.matlog.bool4j.expression;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Константа
 * @author Max
 */
public class Constant extends Expression {
	
    Boolean value;

    @Override
    public Boolean calculate(Map<String, Boolean> variables) {
        return value;
    }
    
    public void setValue(final Boolean value) {
    	this.value = value;
    }
    
    public Boolean getValue() {
    	return value;
    }
    
    @Override
    public Set<String> getVariablesNames() {
        return new HashSet<String>();
    }

    @Override
    public String toString() {
        if (value) {
            return "1";
        } else {
            return "0";
        }
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.CONSTANT;
    }

	@Override
	public void validate() {
		//всё всегда ок
	}
}
