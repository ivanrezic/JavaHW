package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import hr.fer.zemris.java.gui.layouts.listeners.BiOperationListener;
import hr.fer.zemris.java.gui.layouts.listeners.CommandListener;
import hr.fer.zemris.java.gui.layouts.listeners.DotListener;
import hr.fer.zemris.java.gui.layouts.listeners.NegationListener;
import hr.fer.zemris.java.gui.layouts.listeners.NumberListener;
import hr.fer.zemris.java.gui.layouts.listeners.OperationListener;

/*
 * README: Krivo sam shvatio zadatak no kako sam u zadnji cas radio, nisam stigao popravit. 
 * Uglavnom, meni kalkulator ne izracuna trenutnu vrijednost prilikom ponovnog pritiska na operator, vec nakon svakok izraza
 * treba stisnuti '='.
 */

/**
 * <code>Calculator</code> is program which interacts with user and calculates
 * wanted operation.It provides simple operations as sin,cos,tan,cot etc.
 *
 * @author Ivan Rezic
 */
public class Calculator extends JFrame {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Conatiner which contains all components. */
	private Container container;

	/** Stack used for storing input numbers. */
	private Stack<String> stack;

	/** Stored number when pressed push. */
	private String push;

	/** Expression built after providing 2 values and operator between. */
	private String expression;

	/** Text displayed on screen. */
	private JLabel screen;

	/** Tells us if calculator is in invert mode */
	private JCheckBox invert;

	/**
	 * Constructor which instantiates new calculator.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("Calculator");

		container = getContentPane();
		stack = new Stack<>();
		initGUI();
	}

	/**
	 * Method used for getting property <code>Screen</code>.
	 *
	 * @return screen
	 */
	public JLabel getScreen() {
		return screen;
	}

	/**
	 * Method used for getting property <code>Push</code>.
	 *
	 * @return push
	 */
	public String getPush() {
		return push;
	}

	/**
	 * Method which sets new value as push.
	 *
	 * @param push
	 *            pushed number
	 */
	public void setPush(String push) {
		this.push = push;
	}

	/**
	 * Method used for getting property <code>Stack</code>.
	 *
	 * @return stack
	 */
	public Stack<String> getStack() {
		return stack;
	}

	/**
	 * Method used for getting property <code>Expression</code>.
	 *
	 * @return expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Method which sets new value as expression.
	 *
	 * @param expression
	 *            Built expression.
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * Method used for getting property <code>CalcContainer</code>.
	 *
	 * @return calc container
	 */
	public Container getCalcContainer() {
		return container;
	}

	/**
	 * Checks if is inverted.
	 *
	 * @return true, if it is inverted, false otherwise
	 */
	public boolean isInverted() {
		return invert.isSelected();
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		container.setLayout(new CalcLayout(1));

		addScreen();
		addNumbers();
		addCommands();
		addOperations();
		addBiOperations();
		addInvert();
	}

	/**
	 * Adds the invert checkbox.
	 */
	private void addInvert() {
		invert = new JCheckBox("Inv");

		invert.setBackground(Color.decode("#729fcf"));
		invert.setHorizontalAlignment(SwingConstants.CENTER);
		invert.addActionListener(new CommandListener(this, "invert"));

		container.add(invert, new RCPosition(5, 7));
	}

	/**
	 * Adds the operations.
	 */
	private void addOperations() {
		createButton("sin", "sin", new RCPosition(2, 2), new OperationListener(this, Math::sin, Math::asin));
		createButton("log", "log", new RCPosition(3, 1),
				new OperationListener(this, Math::log10, (e) -> Math.pow(10, e)));
		createButton("cos", "cos", new RCPosition(3, 2), new OperationListener(this, Math::cos, Math::acos));
		createButton("ln", "ln", new RCPosition(4, 1),
				new OperationListener(this, Math::log, (e) -> Math.pow(Math.E, e)));
		createButton("tan", "tan", new RCPosition(4, 2), new OperationListener(this, Math::tan, Math::atan));
		createButton("ctg", "ctg", new RCPosition(5, 2),
				new OperationListener(this, (e) -> 1.0 / Math.tan(e), (e) -> Math.atan(1.0 / e)));
		createButton("changeSign", "+/-", new RCPosition(5, 4), new NegationListener(this));
		createButton("dot", ".", new RCPosition(5, 5), new DotListener(this));
		createButton("oneXth", "1/x", new RCPosition(2, 1),
				new OperationListener(this, (e) -> 1.0 / e, (e) -> 1.0 / e));
	}

	/**
	 * Adds the commands.
	 */
	private void addCommands() {
		createButton("equals", "=", new RCPosition(1, 6), new CommandListener(this, "equals"));
		createButton("clr", "clr", new RCPosition(1, 7), new CommandListener(this, "clr"));
		createButton("res", "res", new RCPosition(2, 7), new CommandListener(this, "res"));
		createButton("push", "push", new RCPosition(3, 7), new CommandListener(this, "push"));
		createButton("pop", "pop", new RCPosition(4, 7), new CommandListener(this, "pop"));
	}

	/**
	 * Adds the binary operations.
	 */
	private void addBiOperations() {
		createButton("divide", "/", new RCPosition(2, 6), new BiOperationListener(this, "/"));
		createButton("times", "*", new RCPosition(3, 6), new BiOperationListener(this, "*"));
		createButton("minus", "-", new RCPosition(4, 6), new BiOperationListener(this, "-"));
		createButton("plus", "+", new RCPosition(5, 6), new BiOperationListener(this, "+"));
		createButton("toThePowerN", "x^n", new RCPosition(5, 1), new BiOperationListener(this, "x^n"));
	}

	/**
	 * Adds the numbers.
	 */
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

	/**
	 * Adds the screen.
	 */
	private void addScreen() {
		screen = new JLabel();

		screen.setFont(new Font("Lucida Console", Font.BOLD, 20));
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setBackground(Color.decode("#ffd320"));
		screen.setBorder(new LineBorder(Color.decode("#729fcf")));
		screen.setOpaque(true);

		container.add(screen, new RCPosition(1, 1));
	}

	/**
	 * Helper method which for given arguments creates new button and connects
	 * it with listener.
	 *
	 * @param name
	 *            the name
	 * @param text
	 *            the text
	 * @param position
	 *            the position
	 * @param action
	 *            the action listener
	 */
	private void createButton(String name, String text, RCPosition position, ActionListener action) {
		JButton button = new JButton(text);

		button.setBackground(Color.decode("#729fcf"));
		button.addActionListener(action);
		container.add(button, position);
	}

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
}
