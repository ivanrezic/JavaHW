package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * The listener interface for receiving dot events.
 * The class that is interested in processing a dot
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addActionListener<code> method. When
 * the dot event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DotEvent
 */
public class DotListener implements ActionListener {

	/** calculator. */
	private Calculator calculator;

	/**
	 * Constructor which instantiates new dot listener.
	 *
	 * @param calculator the calculator
	 */
	public DotListener(Calculator calculator) {
		this.calculator = calculator;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
