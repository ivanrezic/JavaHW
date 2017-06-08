package hr.fer.zemris.java.p12.dao;

/**
 * <code>DAOException</code> is exception which may occur while communicating
 * with database.
 *
 * @author Ivan Rezic
 */
public class DAOException extends RuntimeException {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new DAO exception.
	 */
	public DAOException() {
	}

	/**
	 * Constructor which instantiates new DAO exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 * @param enableSuppression
	 *            the enable suppression
	 * @param writableStackTrace
	 *            the writable stack trace
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

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

	/**
	 * Constructor which instantiates new DAO exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}