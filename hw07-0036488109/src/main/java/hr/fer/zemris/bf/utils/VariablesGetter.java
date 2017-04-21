package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

public class VariablesGetter implements NodeVisitor {

	private Set<String> variables = new TreeSet<>();

	public List<String> getVariables() {
		return new ArrayList<String>(variables);
	}

	@Override
	public void visit(ConstantNode node) {
	}

	@Override
	public void visit(VariableNode node) {
		variables.add(node.getName());
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		unaryVariables(node);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		binaryVariables(node);
	}

	private void unaryVariables(UnaryOperatorNode node) {
		Node child = node.getChild();

		if (child instanceof VariableNode) {
			child.accept(this);
		} else if (child instanceof BinaryOperatorNode) {
			binaryVariables((BinaryOperatorNode) child);
		} else {
			unaryVariables((UnaryOperatorNode) child);
		}
	}

	private void binaryVariables(BinaryOperatorNode node) {
		for (Node n : node.getChildren()) {
			if (n instanceof VariableNode) {
				n.accept(this);
			} else if (n instanceof BinaryOperatorNode) {
				binaryVariables((BinaryOperatorNode) n);
			} else {
				unaryVariables((UnaryOperatorNode) n);
			}
		}
	}

}
