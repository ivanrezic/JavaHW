package hr.fer.zemris.bf.lexer;

/**
 * The Class LexerException represents exception which appears during lexer
 * processing.
 *
 * @author Ivan Rezic
 */
public class LexerException extends RuntimeException {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = -7999076915027103118L;

	/**
	 * Constructor which instantiates new lexer exception.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructor which instantiates new lexer exception with given message.
	 *
	 * @param message
	 *            the message
	 */
	public LexerException(String message) {
		super(message);
	}

}
