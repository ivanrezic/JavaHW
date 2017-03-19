package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 */
public class Node {

	private ArrayIndexedCollection children;

	/**
	 * Adds given child to an internally managed collection of children.
	 *
	 * @param child
	 *            the child
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}

		children.add(child);
	}

	/**
	 * Returns a number of (direct) children.
	 *
	 * @return the int
	 */
	public int numberOfChildren() {
		return children.size();
	}

	/**
	 * Returns selected child or throws an appropriate exception if the index is
	 * invalid.
	 *
	 * @param index
	 *            the index of wanted child
	 * @return the child
	 * @throws IndexOutOfBoundsException
	 *             if index is out of bounds <code>[0,size-1]</code>
	 */
	public Node getChild(int index) throws IndexOutOfBoundsException {
		return (Node) children.get(index);
	}
}
