package ru.matlog.bool4j.closures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.danilov.bool4j.util.VariablesSet;
import com.danilov.converter.TruthTable;

import ru.matlog.bool4j.expression.Expression;
import ru.matlog.bool4j.expression.operator.Operator;

public class ClosureClasses {
	/*
	 * Implements T0, T1, M, S, L closure classes.
	 */

	private static final Map<String, Class> classes = new HashMap<>();

	public static final class T0 extends ClosureClass {
		// T0
		public static final String REPRESENTATION = "T0";

		@Override
		public boolean whetherBelongs(final Expression expr,
				final Map<String, Boolean> vars) {
			HashMap<String, Boolean> variables = new HashMap<>();

			for (String var_name : expr.getVariablesNames()) {
				variables.put(var_name, false);
			}

			return expr.calculate(variables) == false;
		}

		@Override
		public String getStringRepresentation() {
			return REPRESENTATION;
		}
	}

	public static final class T1 extends ClosureClass {
		// T1
		public static final String REPRESENTATION = "T1";

		@Override
		public boolean whetherBelongs(final Expression expr,
				final Map<String, Boolean> vars) {
			HashMap<String, Boolean> variables = new HashMap<>();

			for (String var_name : expr.getVariablesNames()) {
				variables.put(var_name, true);
			}

			return expr.calculate(variables) == true;
		}

		@Override
		public String getStringRepresentation() {
			return REPRESENTATION;
		}
	}

	public static final class S extends ClosureClass {
		// S
		public static final String REPRESENTATION = "S";

		@Override
		public boolean whetherBelongs(final Expression expr,
				final Map<String, Boolean> vars) {
			boolean f_neg_result = !expr.calculate(vars);

			HashMap<String, Boolean> neg_vars = new HashMap<>();

			for (String var_name : vars.keySet()) {
				neg_vars.put(var_name, !vars.get(var_name));
			}

			return f_neg_result == expr.calculate(neg_vars);
		}

		@Override
		public String getStringRepresentation() {
			return REPRESENTATION;
		}
	}

	public static final class M extends ClosureClass {
		// M
		public static final String REPRESENTATION = "M";

		@Override
		public boolean whetherBelongs(final Expression expr,
				final Map<String, Boolean> vars) {
			ArrayList<Expression> expr_lst = new ArrayList<Expression>();
			List<String> head_col = new ArrayList<>(vars.keySet());
			String func_key = "func: " + expr.toString();

			expr_lst.add(expr);

			Map<String, List<Boolean>> truth_table = TruthTable.getTruthTable(
					head_col, expr_lst);

			int n = head_col.size(); // Количество переменных
			int n2 = (int) Math.pow(2, n);

			for (int i = 0; i < n2; i++) {
				for (int j = i; j < n2; j++) {
					if (i != j) {
						ArrayList<Boolean> to_cmp_1 = new ArrayList<Boolean>();
						ArrayList<Boolean> to_cmp_2 = new ArrayList<Boolean>();
						boolean diff = false; // Есть ли различия в двух
												// сравниваемых строках
						boolean brea = false; // Более одного различия в
												// сравниваемых строках -
												// досрочно вышли из цикла

						for (String key : head_col) {
							to_cmp_1.add(truth_table.get(key).get(i));
							to_cmp_2.add(truth_table.get(key).get(j));
						}

						for (int k = 0; k < n; k++) {
							if (diff) {
								brea = true;
								break;
							}

							if (to_cmp_1.get(k) != to_cmp_2.get(k))
								diff = true;
						}

						if (!brea && diff) {
							// Рассматриваемые строки сравнимы - будем
							// сравнивать
							boolean op_i = truth_table.get(func_key).get(i);
							boolean op_j = truth_table.get(func_key).get(j);

							if (op_i != op_j && op_i == true) {
								return false;
							}
						}

					}
				}
			}

			return true;
		}

		@Override
		public String getStringRepresentation() {
			return REPRESENTATION;
		}
	}

	static {
		classes.put(T0.REPRESENTATION, T0.class);
		classes.put(T1.REPRESENTATION, T1.class);
		classes.put(S.REPRESENTATION, S.class);
		classes.put(M.REPRESENTATION, M.class);
	}

	public static boolean contains(final String key) {
		return classes.containsKey(key);
	}

	public static ClosureClass getClosureClass(final String representation) {
		Class clazz = classes.get(representation);
		ClosureClass cc = null;

		try {
			cc = (ClosureClass) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cc;
	}
}
