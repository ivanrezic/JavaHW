package hr.fer.zemris.java.webserver;

/**
 * <code>IWebWorker</code> is implemented by worker classes that are invoked by
 * the server after. Server routes the work to the class that takes the context and
 * executes its job and delivers the result to client.
 *
 * @author Ivan Rezic
 */
public interface IWebWorker {

	/**
	 * Processes wanted functionality and provides result to client through {@linkplain RequestContext}
	 *
	 * @param context Http requst context.
	 * @throws Exception Which occurs while processing data.
	 */
	public void processRequest(RequestContext context) throws Exception;
	
}
