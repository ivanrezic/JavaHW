package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * <code>ExpressionTreePrinter</code> is {@link NodeVisitor} implementation. It
 * prints parsed tree to the standard output. Each next level of tree is
 * indented by two spaces.
 *
 * @author Ivan Rezic
 */
public class ExpressionTreePrinter implements NodeVisitor {

	/** Indentation used for each new level. */
	int indentation;

	/**
	 * Prints constants to the standard output.
	 */
	@Override
	public void visit(ConstantNode node) {
		if (node.getValue()) {
			System.out.printf("1%n");
		} else {
			System.out.printf("0%n");
		}
	}

	/**
	 * Prints variables to the standard output.
	 */
	@Override
	public void visit(VariableNode node) {
		System.out.printf("%s%n", node.getName());
	}

	/**
	 * Prints unary operator and child in next level on who given operator will
	 * be used.
	 */
	@Override
	public void visit(UnaryOperatorNode node) {
		printUnary(indentation, node);
	}

	/**
	 * Prints binary operator and its children on who given operator will be
	 * used.
	 */
	@Override
	public void visit(BinaryOperatorNode node) {
		printBinary(indentation, node);
	}

	/**
	 * Helper method which goes recursively to children of this operation child
	 * and prints each with two blanks indented at the beginning.
	 *
	 * @param indentation
	 *            Indentation used for each level.
	 * @param node
	 *            {@link UnaryOperatorNode}
	 */
	private void printUnary(int indentation, UnaryOperatorNode node) {
		System.out.println(indent(indentation) + node.getName());
		Node child = node.getChild();

		if (child instanceof VariableNode || child instanceof ConstantNode) {
			System.out.print(indent(indentation + 2));
			child.accept(this);
		} else if (child instanceof BinaryOperatorNode) {
			printBinary(indentation + 2, (BinaryOperatorNode) child);
		} else {
			printUnary(indentation + 2, (UnaryOperatorNode) child);
		}
	}

	/**
	 * Helper method which goes recursively to children of this operation
	 * and prints each with two blanks indented at the beginning.
	 *
	 * @param indentation
	 *            Indentation used for each level.
	 * @param node
	 *            {@link UnaryOperatorNode}
	 */
	private void printBinary(int indentation, BinaryOperatorNode node) {
		System.out.println(indent(indentation) + node.getName());
		for (Node n : node.getChildren()) {
			if (n instanceof VariableNode || n instanceof ConstantNode) {
				System.out.print(indent(indentation + 2));
				n.accept(this);
			} else if (n instanceof BinaryOperatorNode) {
				printBinary(indentation + 2, (BinaryOperatorNode) n);
			} else {
				printUnary(indentation + 2, (UnaryOperatorNode) n);
			}
		}
	}

	/**
	 * Indent.
	 *
	 * @param indentation
	 *            the indentation
	 * @return the string
	 */
	private String indent(int indentation) {
		return new String(new char[indentation]).replace("\0", " ");
	}

}
