package hr.fer.zemris.java.dao;

/**
 * <code>DAOException</code> is exception which occurs while communicating with
 * database.s.
 *
 * @author Ivan Rezic
 */
public class DAOException extends RuntimeException {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new DAO exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor which instantiates new DAO exception.
	 *
	 * @param message
	 *            the message
	 */
	public DAOException(String message) {
		super(message);
	}
}