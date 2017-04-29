package hr.fer.zemris.bf.demo;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.qmc.Minimizer;
import hr.fer.zemris.bf.utils.Util;

/**
 * <code>QMC</code> is demonstration class used to interact with user and
 * minimize functions per request.
 *
 * @author Ivan Rezic
 */
public class QMC {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.err.print(">");
			String line = sc.nextLine();

			if (line.equals("quit")) {
				break;
			}

			if (!line.contains("=")) {
				System.err.println("Pogreška: funkcija nije ispravno zadana.");
				continue;
			}

			String[] split = line.split("=", 2);
			int leftBracket = split[0].indexOf('(') + 1;
			int rightBracket = split[0].indexOf(')');
			String funcArgs = split[0].substring(leftBracket, rightBracket).trim();

			List<String> variables = Arrays.asList(funcArgs.split(","));
			Set<Integer> minterms = new LinkedHashSet<>();
			Set<Integer> dontCares = new LinkedHashSet<>();

			String afterEquals = split[1].trim();

			if (afterEquals.split("\\|").length == 1) {
				minterms = getValues(afterEquals, variables);
			} else {
				minterms = getValues(afterEquals.split("\\|")[0], variables);
				dontCares = getValues(afterEquals.split("\\|")[1], variables);
			}

			Minimizer minimizer = null;
			try {
				minimizer = new Minimizer(minterms, dontCares, variables);
				printResult(minimizer.getMinimalFormsAsString());
			} catch (IllegalArgumentException e) {
				System.err.println("Pogreška: skup minterma i don't careova nije disjunktan.");
			}

		}
		sc.close();
	}

	/**
	 * Prints the result to the standard output.
	 *
	 * @param minimalForms
	 *            the minimal forms
	 */
	private static void printResult(List<String> minimalForms) {
		int i = 1;
		for (String str : minimalForms) {
			System.err.println(i++ + ". " + str);
		}
	}

	/**
	 * Method used for getting values as set of integers..
	 *
	 * @param string
	 *            string after equation char
	 * @param variables
	 *            the variables
	 * @return values
	 */
	private static Set<Integer> getValues(String string, List<String> variables) {
		Set<Integer> values = new LinkedHashSet<>();

		string = string.trim();
		if (string.matches("\\[.+\\]")) {

			String[] minterms = string.trim().substring(1, string.length() - 1).split(",\\s*");

			for (String integer : minterms) {
				values.add(new Integer(integer));
			}

		} else {
			Parser parser = new Parser(string);
			values = Util.toSumOfMinterms(variables, parser.getExpression());
		}

		return values;
	}

}