package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>RequestContext</code> is class which represents information about HTTP
 * request. It contains header and body.
 *
 * @author Ivan Rezic
 */
public class RequestContext {

	/** Output stream. */
	private OutputStream outputStream;

	/** This request charset. */
	private Charset charset;

	/** Http request parameters */
	private Map<String, String> parameters;

	/**Http request temporary parameters. */
	private Map<String, String> temporaryParameters;

	/**Http requst persistent parameters. */
	private Map<String, String> persistentParameters;

	/**Http output cookies. */
	private List<RCCookie> outputCookies;

	/** True if header is already generated, false otherwise. */
	private boolean headerGenerated;

	/** Object which has ability to dispatch. */
	private IDispatcher dispatcher;

	/** Used for charset. */
	public String encoding;

	/** Http request status code. */
	public int statusCode;

	/** Http request status text. */
	public String statusText;

	/** Http request mime type. */
	public String mimeType;

	/**
	 * Constructor which instantiates new request context.
	 *
	 * @param outputStream
	 *            {@link #outputStream}
	 * @param parameters
	 *            {@link #parameters}
	 * @param persistentParameters
	 *            {@link #persistentParameters}
	 * @param outputCookies
	 *            {@link #outputCookies}
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		Objects.requireNonNull(outputStream, "Output stream must not be null.");
		this.outputStream = outputStream;
		this.parameters = (parameters == null) ? new HashMap<>() : parameters;
		this.persistentParameters = (persistentParameters == null) ? new ConcurrentHashMap<>() : persistentParameters;
		this.outputCookies = (outputCookies == null) ? new ArrayList<>() : outputCookies;

		this.encoding = "UTF-8";
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
	}

	/**
	 * Constructor which instantiates new request context.
	 *
	 * @param outputStream
	 *            {@link #outputStream}
	 * @param parameters
	 *            {@link #parameters}
	 * @param persistentParameters
	 *            {@link #persistentParameters}
	 * @param outputCookies
	 *            {@link #outputCookies}
	 * @param temporaryParameters
	 *            {@link #temporaryParameters}
	 * @param dispatcher
	 *            {@link #dispatcher}
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {

		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Method used for getting property <code>Dispatcher</code>.
	 *
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Method that retrieves value from parameters map (or null if no
	 * association exists).
	 *
	 * @param name
	 *            the name
	 * @return parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map (this set
	 * is read-only).
	 *
	 * @return parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists).
	 *
	 * @param name
	 *            the name
	 * @return persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters
	 * map (this set is read-only).
	 *
	 * @return persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Method that stores a value to persistentParameters map.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map.
	 *
	 * @param name
	 *            the name of parameter
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists).
	 *
	 * @param name
	 *            the name
	 * @return temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters map
	 * (this set to read-only).
	 *
	 * @return temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Method that stores a value to temporaryParameters map.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setTemporaryParameter(String name, String value) {
		if (temporaryParameters == null) {
			temporaryParameters = new HashMap<>();
		}

		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map.
	 *
	 * @param name
	 *            the name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Write data to standard output.
	 *
	 * @param data
	 *            the data as byte array
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data) throws IOException {
		writeHeader();
		outputStream.write(data);
		outputStream.flush();

		return this;
	}

	/**
	 * Write data to standard output.
	 *
	 * @param text
	 *            the text as string
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(String text) throws IOException {
		text = text.replace("\\r\\n", "\r\n");
		Objects.requireNonNull(text, "Text given can not be null.");
		charset = Charset.forName(encoding);

		return write(text.getBytes(charset));
	}

	/**
	 * Helper method which writes header to standard output.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeHeader() throws IOException {
		if (headerGenerated)
			return;
		StringBuilder header = new StringBuilder();

		header.append(String.format("HTTP/1.1 %d %s\r\n", statusCode, statusText));

		String help = mimeType;
		if (help.startsWith("text/")) {
			help = help.concat(String.format("; charset= %s", encoding));
		}
		header.append(String.format("Content-Type: %s\r\n", help));

		for (RCCookie rcCookie : outputCookies) {
			StringJoiner joiner = new StringJoiner("; ");
			joiner.add(String.format("Set-Cookie: %s=\"%s\"", rcCookie.name, rcCookie.value));

			if (rcCookie.domain != null) {
				joiner.add(String.format("Domain=%s", rcCookie.domain));
			}
			if (rcCookie.path != null) {
				joiner.add(String.format("Path=%s", rcCookie.path));
			}
			if (rcCookie.maxAge != null) {
				joiner.add(String.format("Max-Age=%s", rcCookie.maxAge));
			}
			if (rcCookie.httpOnly) {
				joiner.add("HttpOnly");
			}

			header.append(joiner).append("\r\n");
		}
		header.append("\r\n");

		headerGenerated = true;
		outputStream.write(header.toString().getBytes(StandardCharsets.ISO_8859_1));
	}

	//////////////////////////////////////////////////////////////////////////

	/**
	 * Method used for getting property <code>Parameters</code>.
	 *
	 * @return parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Method used for getting property <code>TemporaryParameters</code>.
	 *
	 * @return temporary parameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Sets the temporary parameters.
	 *
	 * @param temporaryParameters
	 *            the temporary parameters
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Method used for getting property <code>PersistentParameters</code>.
	 *
	 * @return persistent parameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Sets the persistent parameters.
	 *
	 * @param persistentParameters
	 *            the persistent parameters
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	//////////////////////////////////////////////////////////////////////////

	/**
	 * Method which sets new value as encoding.
	 *
	 * @param encoding
	 *            the new encoding
	 */
	public void setEncoding(String encoding) {
		checkIfHeaderIsWritten();
		this.encoding = encoding;
	}

	/**
	 * Method which sets new value as status code.
	 *
	 * @param statusCode
	 *            the new status code
	 */
	public void setStatusCode(int statusCode) {
		checkIfHeaderIsWritten();
		this.statusCode = statusCode;
	}

	/**
	 * Method which sets new value as status text.
	 *
	 * @param statusText
	 *            the new status text
	 */
	public void setStatusText(String statusText) {
		checkIfHeaderIsWritten();
		this.statusText = statusText;
	}

	/**
	 * Method which sets new value as mime type.
	 *
	 * @param mimeType
	 *            the new mime type
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Check if header is written.
	 */
	private void checkIfHeaderIsWritten() {
		if (headerGenerated) {
			throw new RuntimeException("Header written, changes not permited.");
		}
	}

	/**
	 * <code>RCCookie</code> represents Http cookie.
	 *
	 * @author Ivan Rezic
	 */
	public static class RCCookie {

		/** Cookie name. */
		private String name;

		/**Cookie value. */
		private String value;

		/**Cookie domain. */
		private String domain;

		/**Cookie path. */
		private String path;

		/**Cookie max age. */
		private Integer maxAge;

		/** True if cookie is http only, false otherwise. */
		private boolean httpOnly;

		/**
		 * Constructor which instantiates new RC cookie.
		 *
		 * @param name
		 *            {@link #name}
		 * @param value
		 *            {@link #value}
		 * @param maxAge
		 *            {@link #maxAge}
		 * @param domain
		 *            {@link #domain}
		 * @param path
		 *            {@link #path}
		 * @param httpOnly
		 *            {@link #httpOnly}
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			Objects.requireNonNull(name, "RCCookie name can not be null.");
			Objects.requireNonNull(value, "RCCookie value can not be null.");

			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}
	}

	/**
	 * Adds the RC cookie.
	 *
	 * @param rcCookie
	 *            Cookie.
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}
}
