package hr.fer.zemris.java.custom.scripting.elems;

public class ElementFunction extends Element {

	private final String name;

	public ElementFunction(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
	}
	
	@Override
	public String toString() {
		return asText();
		
	}
}
