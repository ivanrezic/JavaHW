package hr.fer.zemris.bf.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.utils.Util;
import hr.fer.zemris.bf.utils.VariablesGetter;

/**
 * <code>UtilDemo2</code> is demonstration class. It is used to demonstrate how
 * {@link #toSumOfMinterms} works. It calculates all value combinations from
 * truth table and prints those positions whose value combinations are true for
 * given expression.
 *
 * @author Ivan Rezic
 */
public class UtilDemo2 {

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
		System.out.println("Mintermi f(" + variables + "): " + Util.toSumOfMinterms(variables, expression));

		List<String> variables2 = new ArrayList<>(variables);
		Collections.reverse(variables2);
		System.out.println("Mintermi f(" + variables2 + "): " + Util.toSumOfMinterms(variables2, expression));
	}

}
