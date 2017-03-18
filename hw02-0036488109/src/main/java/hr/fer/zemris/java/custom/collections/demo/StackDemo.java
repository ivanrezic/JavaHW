package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demonstration class which is used for demonstration of stack implementation.
 * This class represents program which accepts arguments in postfix notation
 * from command line and calculates simple mathematical operations.
 * 
 * @author Ivan
 */
public class StackDemo {

	/**
	 * The main method.
	 *
	 * @param args
	 *            numbers and operators
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("There should be only one argument");
			System.exit(0);
		}

		ObjectStack stack = new ObjectStack();
		String[] elements = args[0].split(" +");

		for (String element : elements) {
			try {
				stack.push(Integer.parseInt(element));
			} catch (NumberFormatException e) {
				checkExpression(stack);

				int second = (int) stack.pop();
				int first = (int) stack.pop();

				switch (element) {
				case "+":
					stack.push(first + second);
					break;
				case "-":
					stack.push(first - second);
					break;
				case "/":
					checkDivisor(second);
					stack.push(first / second);
					break;
				case "*":
					stack.push(first * second);
					break;
				case "%":
					checkDivisor(second);
					stack.push(first % second);
					break;
				}
			}
		}

		if (stack.size() != 1) {
			System.out.println("Error");
		} else {
			System.out.println(stack.pop());
		}

	}

	/**
	 * Helper method which checks if stack size is lower than 2 in each iteration.
	 *
	 * @param stack
	 *            the stack
	 */
	private static void checkExpression(ObjectStack stack) {
		if (stack.size() < 2) {
			System.out.println("There should be at least 2 numbers before first operator");
			System.exit(0);
		}
	}

	/**
	 * Helper method which checks if divisor is 0.
	 *
	 * @param second
	 *            the second
	 */
	private static void checkDivisor(int second) {
		if (second == 0) {
			System.out.println("Divisor can't be 0");
			System.exit(1);
		}

	}

}
