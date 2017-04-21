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

public class ExpressionEvaluator implements NodeVisitor {

	private boolean[] values;
	private Map<String, Integer> positions;
	private Stack<Boolean> stack = new Stack<>();

	public ExpressionEvaluator(List<String> variables) {
		values = new boolean[variables.size()];
		positions = variables.stream().collect(Collectors.toMap(a -> a, a -> variables.indexOf(a)));
	}

	public void setValues(boolean[] values) {
		this.values = values;
		start();
	}

	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());
	}

	@Override
	public void visit(VariableNode node) {
		Integer index = positions.get(node.getName());
		if (index == null) {
			throw new IllegalStateException("Wrong values given.");
		}

		stack.push(values[index]);
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		Node child = node.getChild();
		child.accept(this);
		stack.push(node.getOperator().apply(stack.pop()));
	}

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

	public void start() {
		stack.clear();
	}

	public boolean getResult() {
		if (stack.size() != 1) {
			throw new IllegalStateException("Invalid expression given.");
		}

		return stack.peek();
	}

}
