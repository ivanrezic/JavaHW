package hr.fer.zemris.bf.model;

/**
 * <code>ConstantNode</code> represents one expression constant encapsulated in
 * this class.
 *
 * @author Ivan Rezic
 */
public class ConstantNode implements Node {

	/** Constant value. */
	private boolean value;

	/**
	 * Constructor which instantiates new constant node.
	 *
	 * @param value
	 *            {@link #value}
	 */
	public ConstantNode(boolean value) {
		super();
		this.value = value;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Method used for getting property <code>value</code>.
	 *
	 * @return {@link #value}
	 */
	public boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
