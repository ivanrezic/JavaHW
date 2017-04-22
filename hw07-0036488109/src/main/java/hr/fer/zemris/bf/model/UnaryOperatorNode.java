package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

/**
 * <code>UnaryOperatorNode</code> encapsulates negating operator and its child
 * node. This operator negates value of its child.
 * 
 * @author Ivan Rezic
 */
public class UnaryOperatorNode implements Node {

	/** UnaryOperator name. */
	private String name;

	/** Node child of this operation. */
	private Node child;

	/** Unary operator which negates child node value. */
	private UnaryOperator<Boolean> operator;

	/**
	 * Constructor which instantiates new unary operator node.
	 *
	 * @param name
	 *            the name
	 * @param child
	 *            the child
	 * @param operator
	 *            the operator
	 */
	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		super();
		this.name = name;
		this.child = child;
		this.operator = operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Method used for getting property <code>Name</code>.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method used for getting property <code>Child</code>.
	 *
	 * @return child
	 */
	public Node getChild() {
		return child;
	}

	/**
	 * Method used for getting property <code>Operator</code>.
	 *
	 * @return operator
	 */
	public UnaryOperator<Boolean> getOperator() {
		return operator;
	}
}
