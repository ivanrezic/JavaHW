package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementVariable represents string value from lexer processing,
 * encapsulated as variable.
 * 
 * @author Ivan
 */
public class ElementVariable extends Element {

	/** The name of variable. */
	private final String name;

	/**
	 * Instantiates a new element variable.
	 *
	 * @param name
	 *            the name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this variable.
	 *
	 * @return the name
	 */
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
