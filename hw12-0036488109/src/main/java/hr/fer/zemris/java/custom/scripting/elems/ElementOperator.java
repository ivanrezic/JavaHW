package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementOperator represents operator obtained by lexer processing.
 * 
 * @author Ivan
 */
public class ElementOperator extends Element {

	/** The symbol value. */
	private final String symbol;

	/**
	 * Instantiates a new element operator.
	 *
	 * @param symbol the symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns this symbol as string.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return asText();
		
	}
}
