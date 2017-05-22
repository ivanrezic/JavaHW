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

public class RequestContext {

	private OutputStream outputStream;
	private Charset charset;
	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters;
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	private boolean headerGenerated;

	public String encoding;
	public int statusCode;
	public String statusText;
	public String mimeType;

	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		Objects.requireNonNull(outputStream, "Output stream must not be null.");
		this.outputStream = outputStream;
		this.parameters = new HashMap<>(parameters);
		this.persistentParameters = new HashMap<>(persistentParameters);
		this.outputCookies = new ArrayList<>(outputCookies);

		this.encoding = "UTF-8";
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
	}

	/**
	 * Method that retrieves value from parameters map (or null if no
	 * association exists).
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map (this set
	 * is read-only).
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists).
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters
	 * map (this set is read-only).
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/** Method that stores a value to persistentParameters map. */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/** Method that removes a value from persistentParameters map. */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists).
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters map
	 * (this set is read-only).
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/** Method that stores a value to temporaryParameters map. */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/** Method that removes a value from temporaryParameters map. */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	public RequestContext write(byte[] data) throws IOException {
		writeHeader();
		outputStream.write(data);
		
		return this;
	}

	public RequestContext write(String text) throws IOException {
		Objects.requireNonNull(text, "Text given can not be null.");
		charset = Charset.forName(encoding);

		return write(text.getBytes(charset));
	}

	private void writeHeader() throws IOException {
		if (headerGenerated) return;
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
			
			header.append(joiner).append("\r\n");
		}
		header.append("\r\n");
		
		headerGenerated = true;
		outputStream.write(header.toString().getBytes(StandardCharsets.ISO_8859_1));
	}

	//////////////////////////////////////////////////////////////////////////

	public Map<String, String> getParameters() {
		return parameters;
	}

	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	//////////////////////////////////////////////////////////////////////////

	public void setEncoding(String encoding) {
		checkIfHeaderIsWritten();
		this.encoding = encoding;
	}

	public void setStatusCode(int statusCode) {
		checkIfHeaderIsWritten();
		this.statusCode = statusCode;
	}

	public void setStatusText(String statusText) {
		checkIfHeaderIsWritten();
		this.statusText = statusText;
	}

	public void setMimeType(String mimeType) {
		checkIfHeaderIsWritten();
		this.mimeType = mimeType;
	}

	private void checkIfHeaderIsWritten() {
		if (headerGenerated) {
			throw new RuntimeException("Header written, changes not permited.");
		}
	}

	public static class RCCookie {

		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;

		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			Objects.requireNonNull(name, "RCCookie name can not be null.");
			Objects.requireNonNull(value, "RCCookie value can not be null.");
			
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getDomain() {
			return domain;
		}

		public String getPath() {
			return path;
		}

		public Integer getMaxAge() {
			return maxAge;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((domain == null) ? 0 : domain.hashCode());
			result = prime * result + ((maxAge == null) ? 0 : maxAge.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((path == null) ? 0 : path.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof RCCookie))
				return false;
			RCCookie other = (RCCookie) obj;
			if (domain == null) {
				if (other.domain != null)
					return false;
			} else if (!domain.equals(other.domain))
				return false;
			if (maxAge == null) {
				if (other.maxAge != null)
					return false;
			} else if (!maxAge.equals(other.maxAge))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (path == null) {
				if (other.path != null)
					return false;
			} else if (!path.equals(other.path))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
	}

	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}
}