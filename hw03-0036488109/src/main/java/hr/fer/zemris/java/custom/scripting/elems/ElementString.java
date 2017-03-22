package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementString which represents string obtained by lexer processing.
 * 
 * @author Ivan
 */
public class ElementString extends Element {

	/** String value. */
	private final String value;

	/**
	 * Instantiates a new element string.
	 *
	 * @param value
	 *            the value
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Returns this string value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		return value;
	}

	@Override
	public String toString() {
		return asText();

	}
}
