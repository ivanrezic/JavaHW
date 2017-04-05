package hr.fer.zemris.java.custom.scripting.exec;

/**
 * <code>EmptyStackException</code> represents exception thrown when there is no
 * more elements on stack.
 *
 * @author Ivan Rezic
 */
public class EmptyStackException extends RuntimeException {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new empty stack exception.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructor which instantiates new empty stack exception.
	 *
	 * @param message
	 *            message that explains why exception occured
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
