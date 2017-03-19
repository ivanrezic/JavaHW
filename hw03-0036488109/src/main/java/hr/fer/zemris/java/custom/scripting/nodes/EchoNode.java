package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

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
	
	
}
