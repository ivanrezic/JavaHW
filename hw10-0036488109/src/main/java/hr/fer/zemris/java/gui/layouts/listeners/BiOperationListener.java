package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * The listener interface for receiving biOperation events.
 * The class that is interested in processing a biOperation
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addListener<code> method. When
 * the biOperation event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class BiOperationListener implements ActionListener {

	/** calculator. */
	private Calculator calculator;
	
	/** operator. */
	private String operator;

	/**
	 * Constructor which instantiates new bi operation listener.
	 *
	 * @param calculator the calculator
	 * @param operator the operator
	 */
	public BiOperationListener(Calculator calculator, String operator) {
		this.calculator = calculator;
		this.operator = operator;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
