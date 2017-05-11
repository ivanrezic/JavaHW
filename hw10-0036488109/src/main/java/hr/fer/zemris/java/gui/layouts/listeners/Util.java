package hr.fer.zemris.java.gui.layouts.listeners;

public class Util {
	public static boolean missingSecondPart(String expression) {
		if (expression.endsWith("n") || expression.endsWith("sqrt") || expression.endsWith("/")
				|| expression.endsWith("*") || expression.endsWith("-") || expression.endsWith("+")) {
			return true;
		}

		return false;
	}

	public static boolean alreadyExpression(String expression) {
		if (expression.contains("x^n") || expression.contains("sqrt") || expression.contains("/")
				|| expression.contains("*") || expression.contains("-") || expression.contains("+")) {
			return true;
		}

		return false;
	}

	public static String solve(String expression, String operator) {
		String spliter = operator;
		
		if (operator.equals("+")) {
			spliter = "\\".concat("+");
		}else if (operator.equals("/")) {
			spliter = "\\".concat("/");
		}else if (operator.equals("+")) {
			spliter = "\\".concat("+");
		}else if (operator.equals("*")) {
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
