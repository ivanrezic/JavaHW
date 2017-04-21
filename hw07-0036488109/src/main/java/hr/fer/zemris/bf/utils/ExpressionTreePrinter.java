package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

public class ExpressionTreePrinter implements NodeVisitor{

	@Override
	public void visit(ConstantNode node) {
	}

	@Override
	public void visit(VariableNode node) {
	}

	@Override
	public void visit(UnaryOperatorNode node) {
	}

	@Override
	public void visit(BinaryOperatorNode node) {
	}

}
