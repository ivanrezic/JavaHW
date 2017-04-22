package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * <code>BinaryOperatorNode</code> encapsulates variables and operator which
 * will be used to calculate final variable from initial ones.
 *
 * @author Ivan Rezic
 */
public class BinaryOperatorNode implements Node {

	/** BinaryOperation name. */
	private String name;

	/** Node children on which of this operation. */
	private List<Node> children;

	/** Operator which will be used to calculate final value. */
	private BinaryOperator<Boolean> operator;

	/**
	 * Constructor which instantiates new binary operator node.
	 *
	 * @param name
	 *            The name of binary operation.
	 * @param children
	 *            List of children on which this operation operator will be used on.
	 * @param operator
	 *            The operator used for calculating final variable from initial ones.
	 */
	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		super();
		this.name = name;
		this.children = children;
		this.operator = operator;
	}

	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Used for getting property <code>name</code>.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Used for getting property <code>children</code>.
	 *
	 * @return children
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * Used for getting property <code>operator</code>.
	 *
	 * @return operator
	 */
	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}
}
