package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * The listener interface for receiving negation events.
 * The class that is interested in processing a negation
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addActionListener<code> method. When
 * the negation event occurs, that object's appropriate
 * method is invoked.
 *
 * @see NegationEvent
 */
public class NegationListener implements ActionListener {

	/** calculator. */
	private Calculator calculator;

	/**
	 * Constructor which instantiates new negation listener.
	 *
	 * @param calculator the calculator
	 */
	public NegationListener(Calculator calculator) {
		this.calculator = calculator;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
