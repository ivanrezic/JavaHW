package hr.fer.zemris.bf.model;

/**
 * <code>VariableNode</code> encapsulates expression variables.
 *
 * @author Ivan Rezic
 */
public class VariableNode implements Node {

	/** Variable name. */
	private String name;

	/**
	 * Constructor which instantiates new variable node.
	 *
	 * @param name the name
	 */
	public VariableNode(String name) {
		super();
		this.name = name;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Method used for getting property <code>name</code>.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
