package hr.fer.zemris.java.custom.scripting.parser;

/**
 * The Class SmartScriptParserException represents error that occurs during
 * syntax analysis, processed by {@link SmartScriptParser}
 * 
 * @author Ivan
 */
public class SmartScriptParserException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7146405271722626823L;

	/**
	 * Constructor that instantiates a new smart script parser exception.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Instantiates a new smart script parser exception with give message.
	 *
	 * @param message
	 *            the message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
