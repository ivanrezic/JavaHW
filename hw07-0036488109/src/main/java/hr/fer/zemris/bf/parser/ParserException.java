package hr.fer.zemris.bf.parser;

/**
 * <code>ParserException</code> represents errors which can occur while prsing
 * given expression.
 *
 * @author Ivan Rezic
 */
public class ParserException extends RuntimeException {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = -514563825699125867L;

	/**
	 * Constructor which instantiates new parser exception.
	 */
	public ParserException() {
		super();
	}

	/**
	 * Constructor which instantiates new parser exception with given message.
	 *
	 * @param message
	 *            the message
	 */
	public ParserException(String message) {
		super(message);
	}

}
