package hr.fer.zemris.bf.utils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.model.Node;

/**
 * <code>Util</code> class encapsulates all utilities provided.
 *
 * @author Ivan Rezic
 */
public class Util {

	/**
	 * This method creates truth table with all combinations depending on given
	 * variables.
	 *
	 * @param variables
	 *            Variables.
	 * @param consumer
	 *            Defines what will be done with result.
	 */
	public static void forEach(List<String> variables, Consumer<boolean[]> consumer) {
		int count = variables.size();
		int rows = (int) Math.pow(2, count);
		boolean[] array = new boolean[count];

		for (int i = 0; i < rows; i++) {
			for (int j = count - 1, k = 0; j >= 0; j--) {
				array[k++] = (i / (int) Math.pow(2, j)) % 2 == 0 ? false : true;
			}
			consumer.accept(array);
			array = new boolean[count];
		}

	}

	/**
	 * Method which calculates all variable values for given expression and
	 * returns those who match wanted expression value.
	 *
	 * @param variables
	 *            Variables used for calculation.
	 * @param expression
	 *            The expression which defines formula used for evaluating final
	 *            result.
	 * @param expressionValue
	 *            Wanted expression value.
	 * @return The set of combinations who meet wanted expression value.
	 */
	public static Set<boolean[]> filterAssignments(List<String> variables, Node expression, boolean expressionValue) {
		Set<boolean[]> set = new LinkedHashSet<>();
		ExpressionEvaluator eval = new ExpressionEvaluator(variables);

		forEach(variables, values -> {
			eval.setValues(values);
			expression.accept(eval);
			if (eval.getResult() == expressionValue) {
				set.add(values);
			}
		});

		return set;
	}

	/**
	 * Calculates row position of given values in trutha table.
	 *
	 * @param values
	 *            The values we search for in truth table.
	 * @return Row position of given values in truth table.
	 */
	public static int booleanArrayToInt(boolean[] values) {
		int sum = 0;

		for (int k = 0, i = values.length - 1; i >= 0; i--) {
			int help = values[i] ? 1 : 0;
			sum += help * Math.pow(2, k++);
		}

		return sum;
	}

	/**
	 * Method which calculates position for each combination in which given
	 * expression is truth.
	 *
	 * @param variables
	 *            Variables used in expression.
	 * @param expression
	 *            Expression whose minterms we are trying to calculate.
	 * @return The set of integers which represents minterm position.
	 */
	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {
		return getMintermMaxterm(variables, expression, true);
	}

	/**
	 * Method which calculates position for each combination in which given
	 * expression is false.
	 *
	 * @param variables
	 *            Variables used in expression.
	 * @param expression
	 *            Expression whose maxterms we are trying to calculate.
	 * @return The set of integers which represents maxterm position.
	 */
	public static Set<Integer> toProductOfMaxterms(List<String> variables, Node expression) {
		return getMintermMaxterm(variables, expression, false);
	}

	/**
	 * Helper method used for calculating minter or maxterm, depends on last
	 * argument.
	 *
	 * @param variables
	 *            Variables used in expression.
	 * @param expression
	 *            Expression whose minterms or maxterms we are trying to
	 *            calculate.
	 * @param value
	 *            True for minterms, false for maxterms.
	 * @return Minterm or maxterm positions in given expression.
	 */
	private static Set<Integer> getMintermMaxterm(List<String> variables, Node expression, boolean value) {
		Set<boolean[]> set = filterAssignments(variables, expression, value);

		return set.stream().map(Util::booleanArrayToInt).collect(Collectors.toSet());
	}
}
