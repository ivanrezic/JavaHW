package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * A node representing a command which generates some textual output
 * dynamically. It inherits from <code>Node</code> class.
 * 
 * @author Ivan
 */
public class EchoNode extends Node {

	/** The elements array. */
	private final Element[] elements;

	/**
	 * Instantiates a new echo node.
	 *
	 * @param elements the elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Retrurns elements array.
	 *
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}

	
	@Override
	public String toString() {
		String str = "{$=";
		for (Element t : elements) {
			if(t instanceof ElementString) {
				if (t.toString().contains("\\r") || t.toString().contains("\\n")) {
					str += "\"" + t.asText().replace("\"", "\\\"") + "\" ";
				}else{
					str += "\"" + t.asText().replace("\\", "\\\\").replace("\"", "\\\"") + "\" ";
				}
				continue;
			}else if (t instanceof ElementFunction) {
				str+="@";
			}
			str += t.asText() + " ";
		}
		str += "$}";
		return str;
	}
	
	
}
