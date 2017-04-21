package hr.fer.zemris.bf.model;

public class ConstantNode implements Node {

	private boolean value;

	public ConstantNode(boolean value) {
		super();
		this.value = value;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
