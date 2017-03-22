package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. It inherits from
 * <code>Node</code> class and contains 3 or 4 childrens contained as variable,
 * startExpression, endExpression and stepExpression.
 * 
 * @author Ivan
 */
public class ForLoopNode extends Node {

	/** The variable. */
	private final ElementVariable variable;

	/** The start expression. */
	private final Element startExpression;

	/** The end expression. */
	private final Element endExpression;

	/** The step expression. */
	private final Element stepExpression;

	/**
	 * Instantiates a new for loop node.
	 *
	 * @param variable
	 *            the variable
	 * @param startExpression
	 *            the start expression
	 * @param endExpression
	 *            the end expression
	 * @param stepExpression
	 *            the step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Returns the variable value.
	 *
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the start expression value.
	 *
	 * @return the start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the end expression value.
	 *
	 * @return the end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the step expression value.
	 *
	 * @return the step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		String str = "{$ FOR " + variable.asText() + " " + startExpression.asText() + " " + endExpression.asText();
		if (stepExpression != null) {
			str += " " + stepExpression.asText();
		}
		str += " $}";

		for (int i = 0, children = this.numberOfChildren(); i < children; i++) {
			Node child = this.getChild(i);
			str += child;
		}

		return str + "{$END$}";
	}

}
