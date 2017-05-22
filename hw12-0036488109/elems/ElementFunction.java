package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementFunction represents function obtained by lexer processing.
 * 
 * @author Ivan
 */
public class ElementFunction extends Element {

	/** The name of this function. */
	private final String name;

	/**
	 * Instantiates a new element function.
	 *
	 * @param name the name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this function as string.
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
