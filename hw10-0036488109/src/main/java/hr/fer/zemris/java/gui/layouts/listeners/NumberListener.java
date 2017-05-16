package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * The listener interface for receiving number events.
 * The class that is interested in processing a number
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addActionListener<code> method. When
 * the number event occurs, that object's appropriate
 * method is invoked.
 *
 * @see NumberEvent
 */
public class NumberListener implements ActionListener {
	
	/** calculator. */
	private Calculator calculator;
	
	/** number. */
	private String number;
	
	/**
	 * Constructor which instantiates new number listener.
	 *
	 * @param calculator the calculator
	 * @param number the number
	 */
	public NumberListener(Calculator calculator, String number) {
		this.calculator = calculator;
		this.number = number;
	}



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
