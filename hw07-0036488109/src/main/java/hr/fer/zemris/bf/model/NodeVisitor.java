package hr.fer.zemris.bf.model;

/**
 * <code>NodeVisitor</code> represents visitor from <i>Visitor pattern</i>. It
 * conducts actions based on passed Node object.
 *
 * @author Ivan Rezic
 */
public interface NodeVisitor {

	/**
	 * Method which defines actions that will be performed on {@link ConstantNode}.
	 *
	 * @param node
	 *            {@link Node}
	 */
	void visit(ConstantNode node);

	/**
	 * Method which defines actions that will be performed on {@link VariableNode}.
	 *
	 * @param node
	 *            {@link Node}
	 */
	void visit(VariableNode node);

	/**
	 * Method which defines actions that will be performed on {@link UnaryOperatorNode}.
	 *
	 * @param node
	 *            {@link Node}
	 */
	void visit(UnaryOperatorNode node);

	/**
	 * Method which defines actions that will be performed on {@link BinaryOperatorNode}.
	 *
	 * @param node
	 *            {@link Node}
	 */
	void visit(BinaryOperatorNode node);

}
