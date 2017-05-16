package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.Function;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * The listener interface for receiving operation events.
 * The class that is interested in processing a operation
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addActionListener<code> method. When
 * the operation event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class OperationListener implements ActionListener {

	/** calculator. */
	Calculator calculator;
	
	/** normal. */
	private Function<Double, Double> normal;
	
	/** inverted. */
	private Function<Double, Double> inverted;

	/**
	 * Constructor which instantiates new operation listener.
	 *
	 * @param calculator the calculator
	 * @param normal the normal
	 * @param inverted the inverted
	 */
	public OperationListener(Calculator calculator, Function<Double, Double> normal,
			Function<Double, Double> inverted) {
		this.calculator = calculator;
		this.normal = normal;
		this.inverted = inverted;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
