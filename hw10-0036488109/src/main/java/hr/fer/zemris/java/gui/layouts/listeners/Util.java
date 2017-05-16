package hr.fer.zemris.java.gui.layouts.listeners;

/**
 * <code>Util</code> is utility class providing string to double conversions and
 * the other way.That way provides us functionalities needed for implementing
 * calculator.
 *
 * @author Ivan Rezic
 */
public class Util {

	/**
	 * Checks if there is number after operator in expression.
	 *
	 * @param expression
	 *            the expression
	 * @return true, if it is successful, false otherwise
	 */
	public static boolean missingSecondPart(String expression) {
		if (expression.endsWith("n") || expression.endsWith("sqrt") || expression.endsWith("/")
				|| expression.endsWith("*") || expression.endsWith("-") || expression.endsWith("+")) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if expression contains operators
	 *
	 * @param expression
	 *            the expression
	 * @return true, if it is successful, false otherwise
	 */
	public static boolean alreadyExpression(String expression) {
		if (expression.contains("x^n") || expression.contains("sqrt") || expression.contains("/")
				|| expression.contains("*") || expression.contains("-") || expression.contains("+")) {
			return true;
		}

		return false;
	}

	/**
	 * Helper method which computes given expression and returns solution.
	 *
	 * @param expression
	 *            the expression
	 * @param operator
	 *            the operator
	 * @return result as string
	 */
	public static String solve(String expression, String operator) {
		String spliter = operator;

		if (operator.equals("+")) {
			spliter = "\\".concat("+");
		} else if (operator.equals("/")) {
			spliter = "\\".concat("/");
		} else if (operator.equals("+")) {
			spliter = "\\".concat("+");
		} else if (operator.equals("*")) {
			spliter = "\\".concat("*");
		}

		String[] exp = expression.split(spliter);

		switch (operator) {
		case "+":
			return (String.valueOf(Double.parseDouble(exp[0]) + Double.parseDouble(exp[1])));
		case "-":
			return (String.valueOf(Double.parseDouble(exp[0]) - Double.parseDouble(exp[1])));
		case "*":
			return (String.valueOf(Double.parseDouble(exp[0]) * Double.parseDouble(exp[1])));
		case "/":
			return (String.valueOf(Double.parseDouble(exp[0]) / Double.parseDouble(exp[1])));
		case "sqrt":
			return (String.valueOf(Math.pow(Double.parseDouble(exp[1]), 1.0 / Double.parseDouble(exp[0]))));
		case "x^n":
			return (String.valueOf(Math.pow(Double.parseDouble(exp[0]), Double.parseDouble(exp[1]))));
		}

		return null;
	}

	/**
	 * Helper method which etracts operators.
	 *
	 * @param expression
	 *            the expression
	 * @return the string
	 */
	public static String extractOperator(String expression) {
		if (expression.contains("x^n"))
			return "x^n";
		else if (expression.contains("sqrt"))
			return "sqrt";
		else if (expression.contains("/"))
			return "/";
		else if (expression.contains("*"))
			return "*";
		else if (expression.contains("-"))
			return "-";
		else if (expression.contains("+"))
			return "+";

		return null;
	}
}
