package hr.fer.zemris.bf.model;

/**
 * <code>Node</code> is interface which request accept implementation in its
 * subclasses.
 *
 * @author Ivan Rezic
 */
public interface Node {

	/**
	 * Accept method is used to execute action defined by {@link NodeVisitor}.
	 *
	 * @param visitor
	 *            Visitor used for data manipulation in Node subclasses.
	 */
	void accept(NodeVisitor visitor);
}
