package com.danilov.converter;

import com.danilov.bool4j.util.VariablesSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.matlog.bool4j.expression.Expression;



/**
 * 
 * Занимает очень много памяти: (2^n) * (n + 1)
 *
 */
@Deprecated
public class TruthTable {
	
	public static final String FUNCTION_VALUE_KEY = "FUNC";
	
	public static LinkedList<LinkedList<Boolean>> getVarsDefenitionRange(final List<String> vars) {
		VariablesSet vars_set = new VariablesSet(vars);
		LinkedList<LinkedList<Boolean>> result = new LinkedList<LinkedList<Boolean>>();
		
		while(vars_set.notEnd()) {
			Map<String, Boolean> m = vars_set.get();
			LinkedList<Boolean> current = new LinkedList<Boolean>(); 
			
			for (String key : vars)		
				current.add( m.get(key) );
			
			result.addLast( current );
			
			vars_set.moveToNext();
		}
		
		return result;
	}
	
	public static Map<String, List<Boolean>> getTruthTable(final List<String> vars_lst, final List<Expression> expressions_list) {
		return getTruthTable(new HashSet<String>(vars_lst), expressions_list);
	}
	
	public static Map<String, List<Boolean>> getTruthTable(final Set<String> vars_set, final List<Expression> expressions_list) {
		Map<String, List<Boolean>> truth_table = new HashMap<String, List<Boolean>>();
		List<String> vars_lst = new LinkedList<String>(vars_set);
		Collections.sort(vars_lst);
		LinkedList<LinkedList<Boolean>> vars_definitions = getVarsDefenitionRange(vars_lst);
		
		int i = 0;
		for (String var_name: vars_lst) {
			ArrayList<Boolean> current_col = new ArrayList<Boolean>();
				
			for (int j = 0; j < vars_definitions.size(); j++)
				current_col.add( vars_definitions.get(j).get(i) );
			
			i++;			
			truth_table.put(var_name, current_col);
		}
		
		for (Expression expr: expressions_list) {
			LinkedList<Boolean> expr_results = new LinkedList<Boolean>();
			
			for (int j = 0; j < vars_definitions.size(); j++) {
				// Filling vars_to_calc hash map with generated vars definitions
				HashMap<String, Boolean> vars_to_calc = new HashMap<String, Boolean>();
				
				for (int k = 0; k < vars_lst.size(); k++)
					vars_to_calc.put(vars_lst.get(k), vars_definitions.get(j).get(k));
				
				expr_results.push( expr.calculate(vars_to_calc) );
			}
			
			truth_table.put(FUNCTION_VALUE_KEY + ": " + expr.toString(), expr_results);
		}
		
		return truth_table;
	}
	
	public static String getFuncKey(final Expression expr) {
		return FUNCTION_VALUE_KEY + ": " + expr.toString();
	}
	
	public static String toString(Map<String, List<Boolean>> _truth_table) {
		if (_truth_table == null)
			return "";
		else {
			StringBuilder res = new StringBuilder();
			List<String> col_head = new ArrayList<String>(_truth_table.keySet());
			
			if (col_head.size() == 0)
				return "";
			
			Collections.sort(col_head);
			
			for (String str: col_head) {
				res.append(str);
				res.append(" ");
			}
			
			res.append("\n");
			
			int n = _truth_table.get(col_head.get(0)).size();
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < col_head.size(); j++) {
					boolean b = _truth_table.
							get(col_head.
									get(j)).
										get(i);
					
					if (b)
						res.append("1 ");
					else
						res.append("0 ");
				
					if (j == col_head.size() - 1)
						res.append("\n");
				}
			}
			
			return res.toString();
		}
	}
}
