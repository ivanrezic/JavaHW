package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.layouts.Calculator;

public class DotListener implements ActionListener {

	private Calculator calculator;

	public DotListener(Calculator calculator) {
		this.calculator = calculator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Stack<String> stack = calculator.getStack();

		String number;
		if (stack.isEmpty()) {
			number = "0.";
		} else {
			number = stack.pop();
			if (!number.contains(".")) {
				number = number.concat(".");
			}
		}

		calculator.getScreen().setText(stack.push(number));
	}

}
