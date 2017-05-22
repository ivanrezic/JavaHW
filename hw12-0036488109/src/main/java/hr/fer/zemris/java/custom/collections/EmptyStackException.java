package hr.fer.zemris.java.custom.collections;

/**
 * The Class EmptyStackException is subclass of RuntimeException and it
 * describes mistake that can occur while pushing or poping elements from/to
 * stack.
 * 
 * @author Ivan
 */
public class EmptyStackException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new empty stack exception.
	 */
	public EmptyStackException() {
	}

	/**
	 * Instantiates a new empty stack exception with message as only argument.
	 *
	 * @param message
	 *            the message which closely describes occurring situation
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
