package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantInteger extends Element {

	private final int value;

	public ElementConstantInteger(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return "" + value;
	}

	@Override
	public String toString() {
		return asText();
	}
}
