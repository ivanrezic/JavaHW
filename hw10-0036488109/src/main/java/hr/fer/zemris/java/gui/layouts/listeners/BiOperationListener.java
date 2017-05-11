package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.layouts.Calculator;

public class BiOperationListener implements ActionListener {

	private Calculator calculator;
	private String operator;

	public BiOperationListener(Calculator calculator, String operator) {
		this.calculator = calculator;
		this.operator = operator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Stack<String> number = calculator.getStack();
		String expression = calculator.getExpression();
		if (number.isEmpty() || number.peek().equals("0.") || expression != null && Util.alreadyExpression(expression))
			return;

		String help = operator;
		if (operator.equals("x^n") && calculator.isInverted()) {
			help = "sqrt";
		}

		calculator.setExpression(number.pop().concat(help));
		calculator.getScreen().setText("");
	}
}
