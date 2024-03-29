package hr.fer.zemris.bf.demo;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.utils.Util;
import hr.fer.zemris.bf.utils.VariablesGetter;

/**
 * <code>UtilDemo1</code> is demonstration class. It show
 * {@link #FilterAssigment} method works. It takes list of variables, and prints
 * to standard output all combinations from truth table whose expression value
 * matches wanted value.
 *
 * @author Ivan Rezic
 */
public class UtilDemo1 {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {

		Node expression = new Parser("A and b or C").getExpression();

		VariablesGetter getter = new VariablesGetter();
		expression.accept(getter);

		List<String> variables = getter.getVariables();
		for (boolean[] values : Util.filterAssignments(variables, expression, true)) {
			System.out.println(Arrays.toString(values).replaceAll("true", "1").replaceAll("false", "0"));
		}
	}

}
