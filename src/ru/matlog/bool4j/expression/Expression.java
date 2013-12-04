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
}
