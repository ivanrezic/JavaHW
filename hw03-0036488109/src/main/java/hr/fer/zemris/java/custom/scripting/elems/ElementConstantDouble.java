package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementConstantDouble represents double value given by lexer
 * processing.
 * 
 * @author Ivan
 */
public class ElementConstantDouble extends Element {

	/** The value of lexic unit. */
	private final double value;

	/**
	 * Instantiates a new element constant double.
	 *
	 * @param value
	 *            the value
	 */
	public ElementConstantDouble(Double value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return double value
	 */
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
