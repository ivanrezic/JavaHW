package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.listeners.BiOperatorListener;
import hr.fer.zemris.java.gui.layouts.listeners.CommandListener;
import hr.fer.zemris.java.gui.layouts.listeners.DotListener;
import hr.fer.zemris.java.gui.layouts.listeners.NegationListener;
import hr.fer.zemris.java.gui.layouts.listeners.NumberListener;
import hr.fer.zemris.java.gui.layouts.listeners.OperationListener;

public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;

	private Container container;
	private Stack<String> stack;
	private Stack<Double> values;
	private JLabel screen;
	private JCheckBox invert;

	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("Calculator");

		container = getContentPane();
		stack = new Stack<>();
		values = new Stack<>();
		initGUI();
	}

	public JLabel getScreen() {
		return screen;
	}

	public Stack<String> getStack() {
		return stack;
	}
	
	public Stack<Double> getValues() {
		return values;
	}
	
	public boolean isInverted(){
		return invert.isSelected();
	}
	
	private void initGUI() {
		container.setLayout(new CalcLayout(1));

		addScreen();
		addNumbers();
		addCommands();
		addOperations();
		addBiOperators();
		addInvert();

	}

	private void addInvert() {
		invert = new JCheckBox("Inv");
		
		invert.setBackground(Color.decode("#8DA336"));
		invert.addActionListener(new CommandListener(this,"invert"));
		
		container.add(invert, new RCPosition(5, 7));
	}

	private void addOperations() {
		createButton("sin", "sin", new RCPosition(2, 2), new OperationListener(this,Math::sin,Math::asin));
		createButton("log", "log", new RCPosition(3, 1), new OperationListener(this,Math::log10,(e)->Math.pow(10, e)));
		createButton("cos", "cos", new RCPosition(3, 2), new OperationListener(this,Math::cos,Math::acos));
		createButton("ln", "ln", new RCPosition(4, 1), new OperationListener(this,Math::log,(e)->Math.pow(Math.E, e)));
		createButton("tan", "tan", new RCPosition(4, 2), new OperationListener(this,Math::tan,Math::atan));
		createButton("ctg", "ctg", new RCPosition(5, 2), new OperationListener(this,Math::tan,Math::atan));//privremenooooooooooooo
		createButton("changeSign", "+/-", new RCPosition(5, 4), new NegationListener(this));
		createButton("dot", ".", new RCPosition(5, 5), new DotListener(this));
	}

	private void addCommands() {
		createButton("equals", "=", new RCPosition(1, 6), new CommandListener(this,"equals"));
		createButton("clr", "clr", new RCPosition(1, 7), new CommandListener(this,"clr"));
		createButton("res", "res", new RCPosition(2, 7), new CommandListener(this,"res"));
		createButton("push", "push", new RCPosition(3, 7), new CommandListener(this,"push"));
		createButton("pop", "pop", new RCPosition(4, 7), new CommandListener(this,"pop"));
	}

	private void addBiOperators() {
		createButton("divide", "/", new RCPosition(2, 6), new BiOperatorListener(this,"/"));
		createButton("times", "*", new RCPosition(3, 6), new BiOperatorListener(this,"*"));
		createButton("minus", "-", new RCPosition(4, 6), new BiOperatorListener(this,"-"));
		createButton("plus", "+", new RCPosition(5, 6), new BiOperatorListener(this,"+"));
		createButton("oneXth", "1/x", new RCPosition(2, 1), new BiOperatorListener(this,"1/x"));
		createButton("toThePowerN", "x^n", new RCPosition(5, 1), new BiOperatorListener(this,"x^n"));
	}

	private void addNumbers() {
		createButton("zero", "0", new RCPosition(5, 3), new NumberListener(this, "0"));
		createButton("one", "1", new RCPosition(4, 3), new NumberListener(this, "1"));
		createButton("two", "2", new RCPosition(4, 4), new NumberListener(this, "2"));
		createButton("three", "3", new RCPosition(4, 5), new NumberListener(this, "3"));
		createButton("four", "4", new RCPosition(3, 3), new NumberListener(this, "4"));
		createButton("five", "5", new RCPosition(3, 4), new NumberListener(this, "5"));
		createButton("six", "6", new RCPosition(3, 5), new NumberListener(this, "6"));
		createButton("seven", "7", new RCPosition(2, 3), new NumberListener(this, "7"));
		createButton("eight", "8", new RCPosition(2, 4), new NumberListener(this, "8"));
		createButton("nine", "9", new RCPosition(2, 5), new NumberListener(this, "9"));
	}

	private void addScreen() {
		screen = new JLabel();

		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setBackground(Color.LIGHT_GRAY);
		screen.setOpaque(true);

		container.add(screen, new RCPosition(1, 1));
	}

	private void createButton(String name, String text, RCPosition position, ActionListener action) {
		JButton button = new JButton(text);

		button.setBackground(Color.decode("#8DA336"));
		button.addActionListener(action);
		container.add(button, position);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
}
