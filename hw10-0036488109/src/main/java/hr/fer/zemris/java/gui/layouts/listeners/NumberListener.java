package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.layouts.Calculator;

public class NumberListener implements ActionListener {
	
	private Calculator calculator;
	private String number;
	
	public NumberListener(Calculator calculator, String number) {
		this.calculator = calculator;
		this.number = number;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Stack<String> stack = calculator.getStack();
		
		String newNumber = number;
		if (!stack.isEmpty()) {			
			newNumber = stack.pop().concat(number);
		}

		calculator.getScreen().setText(stack.push(newNumber));
	}

}
