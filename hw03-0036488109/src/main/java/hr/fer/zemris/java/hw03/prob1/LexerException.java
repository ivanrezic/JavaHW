package hr.fer.zemris.java.hw03.prob1;

/**
 * The Class LexerException represents exception which appears during lexer processing.
 */
public class LexerException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1173741942168406063L;

	/**
	 * Instantiates a new lexer exception.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Instantiates a new lexer exception with given message.
	 *
	 * @param message the message
	 */
	public LexerException(String message) {
		super(message);
	}

}
