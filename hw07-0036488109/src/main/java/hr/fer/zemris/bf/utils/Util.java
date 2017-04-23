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

	/**
	 * Transforms integer number to binary, using wanted byte array size.
	 *
	 * @param x
	 *            integer number, number must be in range [-2147483647,2147483647]
	 * @param n
	 *            wanted size of byte array used for storing number
	 * @return binary number in wanted byte size array, first index represents
	 *         bit with most significance.
	 */
	public static byte[] indexToByteArray(int x, int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Byte array size can not be zero or less.");
		}else if (x < -2147483647) {
			throw new IllegalArgumentException("Integer is out of range.");
		}
		int k = (n <= 32 ? 32 : n);
		byte[] bytes = new byte[k];

		int number = Math.abs(x);
		for (int i = k - 1; number != 0; i--) {
			bytes[i] = (byte) (number % 2);
			number = number / 2;
			if (i < 0)
				break;
		}

		return x < 0 ? saveInNSizeRegister(n, twoComplement(bytes), k) : saveInNSizeRegister(n, bytes, k);
	}

	/**
	 * Helper method which takes binary number and saves first n digits into
	 * byte array, starting from the lowest significance to the most.
	 *
	 * @param n
	 *            byte array size
	 * @param binary
	 *            binary number to be truncated
	 * @param k
	 *            temporary byte size array(before truncating)
	 * @return truncated binary saved in byte array
	 */
	private static byte[] saveInNSizeRegister(int n, byte[] binary, int k) {
		byte[] help = new byte[n];

		for (int i = n - 1; i >= 0; i--) {
			help[i] = binary[--k];
		}

		return help;
	}

	/**
	 * Helper method which transform binary number to two's complement.
	 *
	 * @param bytes
	 *            the bytes
	 * @return binary number in two's complement
	 */
	private static byte[] twoComplement(byte[] bytes) {
		byte[] help = bytes;

		for (int i = 0; i < help.length; i++) {
			help[i] = (byte) (help[i] == 1 ? 0 : 1);
		}

		for (int i = help.length - 1; i >= 0; i--) {
			if (help[i] == 1) {
				help[i] = 0;
			} else {
				help[i] = 1;
				break;
			}
		}

		return help;
	}
}
