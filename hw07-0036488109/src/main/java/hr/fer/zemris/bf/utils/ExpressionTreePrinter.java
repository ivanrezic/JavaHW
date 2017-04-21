package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

public class ExpressionTreePrinter implements NodeVisitor {

	int indentation;

	@Override
	public void visit(ConstantNode node) {
		if (node.getValue()) {
			System.out.printf("1%n");
		}else {
			System.out.printf("0%n");	
		}
	}

	@Override
	public void visit(VariableNode node) {
		System.out.printf("%s%n", node.getName());
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		printUnary(indentation, node);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		printBinary(indentation, node);
	}

	private void printUnary(int indentation, UnaryOperatorNode node) {
		System.out.println(indent(indentation) + node.getName());
		Node child = node.getChild();
		
		if (child instanceof VariableNode || child instanceof ConstantNode) {
			System.out.print(indent(indentation + 2));
			child.accept(this);
		}else if (child instanceof BinaryOperatorNode) {
			printBinary(indentation + 2, (BinaryOperatorNode)child);
		}else {
			printUnary(indentation + 2, (UnaryOperatorNode)child);
		}
	}

	private void printBinary(int indentation, BinaryOperatorNode node) {
		System.out.println(indent(indentation) + node.getName());
		for (Node n : node.getChildren()) {
			if (n instanceof VariableNode || n instanceof ConstantNode) {
				System.out.print(indent(indentation + 2));
				n.accept(this);
			}else if (n instanceof BinaryOperatorNode) {
				printBinary(indentation + 2, (BinaryOperatorNode)n);
			}else {
				printUnary(indentation + 2, (UnaryOperatorNode)n);
			}
		}
	}

	private String indent(int indentation) {
		return new String(new char[indentation]).replace("\0", " ");
	}

}
