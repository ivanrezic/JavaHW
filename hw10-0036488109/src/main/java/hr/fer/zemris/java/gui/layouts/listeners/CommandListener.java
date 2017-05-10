package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import hr.fer.zemris.java.gui.layouts.Calculator;

public class CommandListener implements ActionListener {

	private Calculator calculator;
	private String command;

	public CommandListener(Calculator calculator, String command) {
		this.calculator = calculator;
		this.command = command;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (command) {
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

	private void equalsCmd() {
	}

	private void clr() {
		Stack<Double> values = calculator.getValues();
		Stack<String> number = calculator.getStack();
		
		if (!values.isEmpty()) {
			values.pop();
		}
		if (!number.isEmpty()) {
			number.pop();
		}
		
		calculator.getScreen().setText("");
	}

	private void res() {
	}

	private void pushCmd() {
	}

	private void popCmd() {
	}

}
