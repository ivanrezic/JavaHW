package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Class Token represents object which encapsulates input parts processed by
 * <code>lexer</code>.
 * 
 * @author Ivan
 */
public class Token {
	
	/** The type. */
	private TokenType type;
	
	/** The value. */
	private	Object value;

	/**
	 * Constructor instantiates a new token.
	 *
	 * @param type the type
	 * @param value the value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "[type=" + type + ", value=" + value + "]";
	}

	
}
