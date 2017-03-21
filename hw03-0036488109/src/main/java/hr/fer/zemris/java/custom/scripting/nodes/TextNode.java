package hr.fer.zemris.java.custom.scripting.nodes;

/**
 *  A node representing a piece of textual data. It inherits from <code>Node</code> class.
 */
public class TextNode extends Node {
	
	private final String text;
	
	public TextNode(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}

	
}
