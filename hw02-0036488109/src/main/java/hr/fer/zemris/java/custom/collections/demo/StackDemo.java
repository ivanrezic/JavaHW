package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

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

	private static void checkExpression(ObjectStack stack) {
		if (stack.size() < 2) {
			System.out.println("There should be at least 2 numbers before first operator");
			System.exit(0);
		}
	}

	private static void checkDivisor(int second) {
		if (second == 0) {
			System.out.println("Divisor can't be 0");
			System.exit(1);
		}

	}

}
