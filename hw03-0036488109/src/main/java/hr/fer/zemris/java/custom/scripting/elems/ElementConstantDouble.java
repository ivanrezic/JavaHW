package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantDouble extends Element {

	private final double value;

	public ElementConstantDouble(Double value) {
		this.value = value;
	}

	public double getValue() {
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
