package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

public class BinaryOperatorNode implements Node {

	private String name;
	private List<Node> children;
	private BinaryOperator<Boolean> operator;

	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		super();
		this.name = name;
		this.children = children;
		this.operator = operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}
}
