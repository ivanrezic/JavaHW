package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. It inherits from
 * <code>Node</code> class.
 */
public class ForLoopNode extends Node {

	private final ElementVariable variable;
	private final Element startExpression;
	private final Element endExpression;
	private final Element stepExpression;

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

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
