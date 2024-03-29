package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * The listener interface for receiving command events.
 * The class that is interested in processing a command
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addActionListener<code> method. When
 * the command event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CommandEvent
 */
public class CommandListener implements ActionListener {

	/** calculator. */
	private Calculator calculator;
	
	/** command. */
	private String command;

	/**
	 * Constructor which instantiates new command listener.
	 *
	 * @param calculator the calculator
	 * @param command the command
	 */
	public CommandListener(Calculator calculator, String command) {
		this.calculator = calculator;
		this.command = command;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (command) {
		case "invert":
			invert();
			break;
		case "equals":
			equalsCmd();
			break;
		case "clr":
			clr();
			break;
		case "res":
			res();
			break;
		case "push":
			pushCmd();
			break;
		case "pop":
			popCmd();
			break;
		}
	}

	/**
	 * Inverts text and invertible buttons.
	 */
	private void invert() {
		Component[] components = calculator.getCalcContainer().getComponents();
		for (Component component : components) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;
				changeName(button);
			}
		}
	}

	/**
	 * Displays result of given expression.
	 */
	private void equalsCmd() {
		Stack<String> number = calculator.getStack();
		
		if (number.isEmpty()) {
			return;
		}
		String expression = calculator.getExpression();
		expression = expression.concat(number.pop());
		
		String result = Util.solve(expression, Util.extractOperator(expression));
		calculator.setExpression(null);
		calculator.getScreen().setText(number.push(result));
	}

	/**
	 * Cleares last number imput.
	 */
	private void clr() {
		Stack<String> number = calculator.getStack();

		if (!number.isEmpty()) {
			number.pop();
		}

		calculator.getScreen().setText("");
	}

	/**
	 * Restores caluclator to default.
	 */
	private void res() {
		Stack<String> number = calculator.getStack();

		if (!number.isEmpty()) {
			number.pop();
		}

		calculator.getScreen().setText("");
		calculator.setExpression(null);
	}

	/**
	 * Pushes to stack last number given.
	 */
	private void pushCmd() {
		Stack<String> number = calculator.getStack();
		if(number.isEmpty()) return;
		calculator.setPush(number.peek());
	}

	/**
	 * Pops from stack last number pushed.
	 */
	private void popCmd() {
		Stack<String> number = calculator.getStack();
		if(number.isEmpty()) return;
		number.pop();
		number.push(calculator.getPush());
	}

	/**
	 * Helper method which executes name chaning for {@link #invert()};
	 *
	 * @param button the button
	 */
	private void changeName(JButton button) {
		String text = button.getText();

		switch (text) {
		case "sin":
			button.setText("asin");
			break;
		case "asin":
			button.setText("sin");
			break;
		case "cos":
			button.setText("acos");
			break;
		case "acos":
			button.setText("cos");
			break;
		case "tan":
			button.setText("atan");
			break;
		case "atan":
			button.setText("tan");
			break;
		case "ctg":
			button.setText("actg");
			break;
		case "actg":
			button.setText("ctg");
			break;
		case "log":
			button.setText("10^x");
			break;
		case "10^x":
			button.setText("log");
			break;
		case "ln":
			button.setText("e^x");
			break;
		case "e^x":
			button.setText("ln");
			break;
		case "x^n":
			button.setText("nˇ√");
			break;
		case "nˇ√":
			button.setText("x^n");
			break;
		}
	}
}
