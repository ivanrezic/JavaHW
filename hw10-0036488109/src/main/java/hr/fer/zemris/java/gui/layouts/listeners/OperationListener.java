package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.Function;

import hr.fer.zemris.java.gui.layouts.Calculator;

public class OperationListener implements ActionListener {

	Calculator calculator;
	private Function<Double, Double> normal;
	private Function<Double, Double> inverted;

	public OperationListener(Calculator calculator, Function<Double, Double> normal,
			Function<Double, Double> inverted) {
		this.calculator = calculator;
		this.normal = normal;
		this.inverted = inverted;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Stack<String> stack = calculator.getStack();
		if (stack.isEmpty()) return;
		
		
		double value = Double.parseDouble(stack.pop());
		if (calculator.isInverted()) {
			value = inverted.apply(value);
		} else {
			value = normal.apply(value);
		}
		
		calculator.getScreen().setText(stack.push(String.valueOf(value)));
	}
}
