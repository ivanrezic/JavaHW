package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.layouts.Calculator;

public class NegationListener implements ActionListener {

	private Calculator calculator;

	public NegationListener(Calculator calculator) {
		this.calculator = calculator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Stack<String> stack = calculator.getStack();
		if (stack.isEmpty()) return;

		String number = stack.pop();
		if (number.startsWith("-")) {
			number = number.replace("-", "");
		}else {
			number = "-".concat(number);
		}
		
		calculator.getScreen().setText(stack.push(number));
	}

}
