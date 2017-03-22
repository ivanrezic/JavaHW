package hr.fer.zemris.java.hw03.prob1;

/**
 * The Class Token represents object which encapsulates input parts processed by
 * <code>lexer</code>.
 * 
 * @author Ivan
 */
public class Token {

	/** Type of lexer processed part. */
	private TokenType type;

	/** Value of lexer processed part. */
	private Object value;

	/**
	 * Instantiates a new token.
	 *
	 * @param type
	 *            the type
	 * @param value
	 *            the value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the token value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets the token type.
	 *
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}

}
