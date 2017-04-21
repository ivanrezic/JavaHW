package hr.fer.zemris.bf.model;

public interface NodeVisitor {

	void visit(ConstantNode node);
	void visit(VariableNode node);
	void visit(UnaryOperatorNode node);
	void visit(BinaryOperatorNode node);

}
