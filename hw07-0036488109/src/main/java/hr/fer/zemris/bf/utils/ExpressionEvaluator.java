package hr.fer.zemris.bf.utils;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * <code>ExpressionEvaluator</code> represents {@link NodeVisitor}
 * implementation. It goes by the parser tree and in relation to that evaluates
 * sibiling nodes.
 *
 * @author Ivan Rezic
 */
public class ExpressionEvaluator implements NodeVisitor {

	/** Values used for our variables. */
	private boolean[] values;

	/**
	 * Map which points index of value stored in {@link #values} for each
	 * variable.
	 */
	private Map<String, Integer> positions;

	/** Stack used for calculations. */
	private Stack<Boolean> stack = new Stack<>();

	/**
	 * Constructor which instantiates new expression evaluator.
	 *
	 * @param variables
	 *            Expression variables.
	 */
	public ExpressionEvaluator(List<String> variables) {
		values = new boolean[variables.size()];
		positions = variables.stream().collect(Collectors.toMap(a -> a, a -> variables.indexOf(a)));
	}

	/**
	 * Method which sets new value as values.
	 *
	 * @param values
	 *            The new values for each variable.
	 */
	public void setValues(boolean[] values) {
		this.values = values;
		start();
	}

	/**
	 * Pushes constant value to the stack.
	 */
	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());
	}

	/**
	 * Pushes each legal variable value to the stack.
	 */
	@Override
	public void visit(VariableNode node) {
		Integer index = positions.get(node.getName());
		if (index == null) {
			throw new IllegalStateException("Wrong values given.");
		}

		stack.push(values[index]);
	}

	/**
	 * Conducts defined operation on its child then pushes it to to stack.
	 */
	@Override
	public void visit(UnaryOperatorNode node) {
		Node child = node.getChild();
		child.accept(this);
		stack.push(node.getOperator().apply(stack.pop()));
	}

	/**
	 * Conducts defined operation on its children then pushes them to to stack.
	 */
	@Override
	public void visit(BinaryOperatorNode node) {
		List<Node> children = node.getChildren();
		for (Node n : children) {
			n.accept(this);
		}

		int size = node.getChildren().size();
		for (int i = 1; i < size; i++) {
			stack.push(node.getOperator().apply(stack.pop(), stack.pop()));
		}
	}

	/**
	 * Clears the stack, and represents beginning of a new calculation.
	 */
	public void start() {
		stack.clear();
	}

	/**
	 * Gets the last and only element on stack which represents final expression
	 * evaluation.
	 *
	 * @return result
	 * @throws IllegalStateException
	 *             If there is illegal number of elements on stack.
	 */
	public boolean getResult() {
		if (stack.size() != 1) {
			throw new IllegalStateException("Invalid expression given.");
		}

		return stack.peek();
	}

}
