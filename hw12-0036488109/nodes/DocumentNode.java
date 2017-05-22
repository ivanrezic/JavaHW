package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing an entire document. It inherits from <code>Node</code>
 * class and provides insight to its child collection and so on.
 * 
 * @author Ivan
 */
public class DocumentNode extends Node {

	@Override
	public String toString() {
		String text = new String();
		
		for (int i = 0, children = this.numberOfChildren(); i < children; i++) {
			Node child = this.getChild(i);
			text += child;
		}
		
		return text;
	}

}
