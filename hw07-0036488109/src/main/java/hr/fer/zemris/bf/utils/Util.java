package hr.fer.zemris.bf.utils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.model.Node;

public class Util {

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

	public static int booleanArrayToInt(boolean[] values) {
		int sum = 0;

		for (int k = 0, i = values.length - 1; i >= 0; i--) {
			int help = values[i] ? 1 : 0;
			sum += help * Math.pow(2, k++);
		}

		return sum;
	}

	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {
		return getMintermMaxterm(variables, expression, true);
	}

	public static Set<Integer> toProductOfMaxterms(List<String> variables, Node expression) {
		return getMintermMaxterm(variables, expression, false);
	}

	private static Set<Integer> getMintermMaxterm(List<String> variables, Node expression, boolean value) {
		Set<boolean[]> set = filterAssignments(variables, expression, value);

		return set.stream().map(Util::booleanArrayToInt).collect(Collectors.toSet());
	}
}
