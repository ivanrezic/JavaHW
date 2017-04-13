package hr.fer.zemris.java.hw06.shell;

/**
 * <code>ShellIOException</code> represents error which occurs during writing or
 * reading from/to standard output..
 *
 * @author Ivan Rezic
 */
public class ShellIOException extends RuntimeException {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = -1491587332828227666L;

	/**
	 * Constructor which instantiates new shell IO exception.
	 */
	public ShellIOException() {
	}

	/**
	 * Constructor which instantiates new shell IO exception.
	 *
	 * @param message
	 *            the message which describes why exception happened
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
