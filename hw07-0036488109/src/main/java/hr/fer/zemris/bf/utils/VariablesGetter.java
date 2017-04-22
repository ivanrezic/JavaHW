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

/**
 * <code>VariablesGetter</code> represents {@link NodeVisitor} implementation.
 * It provides us with all variables used in given expression.
 *
 * @author Ivan Rezic
 */
public class VariablesGetter implements NodeVisitor {

	/** All variables used in alphabetic order. */
	private Set<String> variables = new TreeSet<>();

	/**
	 * Method used for getting property <code>variables</code>.
	 *
	 * @return {@link #variables}
	 */
	public List<String> getVariables() {
		return new ArrayList<String>(variables);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * ConstantNode)
	 */
	@Override
	public void visit(ConstantNode node) {
	}

	/**
	 * Gets variable node name which represents variable used in expression.
	 */
	@Override
	public void visit(VariableNode node) {
		variables.add(node.getName());
	}

	/**
	 * Method which gets all variables used in {@link UnaryOperatorNode}.
	 */
	@Override
	public void visit(UnaryOperatorNode node) {
		unaryVariables(node);
	}

	/**
	 * Method which gets all variables used in {@link BinaryOperatorNode}.
	 */
	@Override
	public void visit(BinaryOperatorNode node) {
		binaryVariables(node);
	}

	/**
	 * Helper method, which goes through unary node child and continues
	 * recursively until it reaches variables.
	 *
	 * @param node
	 *            {@link UnaryOperatorNode}
	 */
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

	/**
	 * Helper method which goes recursively through all binary node children
	 * until it reaches variables.
	 *
	 * @param node
	 *            {@link BinaryOperatorNode}
	 */
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
