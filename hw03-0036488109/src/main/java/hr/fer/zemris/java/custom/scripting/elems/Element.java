package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element class is general class which represents expression given by lexer
 * processing. This class can't be instantiated but it serves as certain
 * contract to subclasses which requires {@link #asText()} implementation.
 * 
 * @author Ivan
 */
public class Element {

	/**
	 * Method which returns its property as string.
	 *
	 * @return element value as string
	 */
	public String asText() {
		return "";
	}

}
