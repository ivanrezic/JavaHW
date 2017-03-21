package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * A node representing a command which generates some textual output
 * dynamically. It inherits from <code>Node</code> class.
 */
public class EchoNode extends Node {

	private final Element[] elements;

	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

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
			}
			str += t.asText() + " ";
		}
		str += "$}";
		return str;
	}
	
	
}
