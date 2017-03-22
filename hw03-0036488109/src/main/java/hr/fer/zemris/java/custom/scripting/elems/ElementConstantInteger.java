package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementConstantInteger represents double value given by lexer
 * processing.
 * 
 * @author Ivan
 */
public class ElementConstantInteger extends Element {

	/** The value of lexic unit. */
	private final int value;

	/**
	 * Instantiates a new element constant integer.
	 *
	 * @param value
	 *            the value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
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
