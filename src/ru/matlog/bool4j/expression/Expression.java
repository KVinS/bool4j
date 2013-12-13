package ru.matlog.bool4j.expression;

import java.util.Map;
import java.util.Set;

public abstract class Expression {
	
	/**
	 * ���������� �������� ���������
	 * @param variables ����� ����������
	 * @return �������� ���������� � ���������� ����������
	 */
    public abstract Boolean calculate(final Map<String, Boolean> variables);
    
    /**
     * 
     * @return ����� ���� ���������� ������� ���������
     */
    public abstract Set<String> getVariablesNames();
    
    /**
     * 
     * @return A set of Maps of variables, including its name(String) and value(Boolean).
     */
    //public abstract Set<Map<String, Boolean>> getVariables();
    
    /**
     * 
     * @return ��� ��������� (���������, ������, �������� etc)
     */
    public abstract ExpressionType getType();
    
    /**
     * ��������� ������� Calculable (��� ��������������� �� ���������� ���������� ����������)
     * @param calculableFactory ������� �������� ���� Calculable
     * @return ������ Calculable
     */
    public Calculable toCalculable(final CalculableFactory calculableFactory) {
		return calculableFactory.newCalculable(this); 
    }
    
    @Override
    public abstract String toString();
    
    /**
     * Checks if this expression with set of variables(vars) belongs to the closure class(clos_lass).
     * @param clos_lass - closure class to check whether the expression belongs to
     * @param vars - map of variables to be checked with.
     * @return boolean
     */
    // TO BE DONE
    //public abstract boolean belongsTo(ClosureClass clos_lass, Map<String, Boolean> vars);
}
