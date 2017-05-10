package hr.fer.zemris.java.gui.layouts.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hr.fer.zemris.java.gui.layouts.Calculator;

public class BiOperatorListener implements ActionListener {

	private Calculator calculator;
	private String operator;

	public BiOperatorListener(Calculator calculator, String operator) {
		this.calculator = calculator;
		this.operator = operator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (operator) {
		case "/":
			divide();
			break;
		case "*":
			multiply();
			break;
		case "-":
			sub();
			break;
		case "+":
			add();
			break;
		case "1/x":
			oneXTh();
			break;
		case "x^n":
			toTheNTh();
			break;
		}
	}

	private void divide() {
	}

	private void multiply() {
	}

	private void sub() {
	}

	private void add() {
	}

	private void oneXTh() {
	}

	private void toTheNTh() {
	}

}
