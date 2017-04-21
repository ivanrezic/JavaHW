package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

public class UnaryOperatorNode implements Node {

	private String name;
	private Node child;
	private UnaryOperator<Boolean> operator;

	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		super();
		this.name = name;
		this.child = child;
		this.operator = operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	public String getName() {
		return name;
	}
	
	public Node getChild() {
		return child;
	}
	
	public UnaryOperator<Boolean> getOperator() {
		return operator;
	}
}
