package hr.fer.zemris.bf.lexer;

/**
 * The Class Token represents object which encapsulates input parts processed by
 * <code>lexer</code>.
 * 
 * @author Ivan Rezic
 */
public class Token {

	/** Type of lexer processed part. */
	private TokenType tokenType;

	/** Value of lexer processed part. */
	private Object tokenValue;

	/**
	 * Constructor which instantiates new token.
	 *
	 * @param tokenType
	 *            {@link TokenType}
	 * @param tokenValue
	 *            value of type <code>Object</code>
	 */
	public Token(TokenType tokenType, Object tokenValue) {
		if (tokenType == null) {
			throw new LexerException("Token type can not be null");
		}

		this.tokenType = tokenType;
		this.tokenValue = tokenValue;
	}

	/**
	 * Method used for getting property <code>TokenType</code>.
	 *
	 * @return token type
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Method used for getting property <code>TokenValue</code>.
	 *
	 * @return token value
	 */
	public Object getTokenValue() {
		return tokenValue;
	}

	public String toString() {
		StringBuilder token = new StringBuilder();

		token.append("Type: ").append(tokenType);
		token.append(", Value: ").append(tokenValue);
		if (tokenValue != null) {
			token.append(", Value is instance of: ").append(tokenValue.getClass().getCanonicalName());
		}

		return token.toString();
	}
}
