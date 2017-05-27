package hr.fer.zemris.java.webserver;

/**
 * <code>IDispatcher</code> is interface which enables implementing classes to
 * communicate with server.
 *
 * @author Ivan Rezic
 */
public interface IDispatcher {

	/**
	 * Dispatch request(by given path).
	 *
	 * @param urlPath
	 *            The url path to be dispatched.
	 * @throws Exception
	 *             The exception thrown which occurs while trying to dispatch.
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
