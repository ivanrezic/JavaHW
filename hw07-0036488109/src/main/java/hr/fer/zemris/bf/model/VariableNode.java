package hr.fer.zemris.bf.model;

public class VariableNode implements Node {

	private String name;

	public VariableNode(String name) {
		super();
		this.name = name;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
