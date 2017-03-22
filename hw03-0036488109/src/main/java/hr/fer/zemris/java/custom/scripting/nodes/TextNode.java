package hr.fer.zemris.java.custom.scripting.nodes;

/**
 *  A node representing a piece of textual data. It inherits from <code>Node</code> class.
 *  
 *  @author Ivan
 */
public class TextNode extends Node {
	
	/** The text obtained by lexic analysis. */
	private final String text;
	
	/**
	 * Instantiates a new text node.
	 *
	 * @param text the text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns its text value.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}

	
}
