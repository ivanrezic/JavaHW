package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * <code>SmartHttpServer</code> processes requests via HTTP. The term can refer
 * to the entire system, or specifically to the software that accepts and
 * supervises the HTTP requests. This HTTP server uses TCP transfer protocol
 * that works in a way that it transfers bytes between server and client using
 * {@link Socket}.
 *
 * @author Ivan Rezic
 */
public class SmartHttpServer {

	/** Server ip address. */
	private String address;

	/** Server port. */
	private int port;

	/** Number of paralel worker threads. */
	private int workerThreads;

	/** Server session timeout. */
	private int sessionTimeout;

	/** Server respond mime types. */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/** Represents job done by server. */
	private ServerThread serverThread;

	/** Thread pool used for thread synchronization. */
	private ExecutorService threadPool;

	/** Server documents root folder. */
	private Path documentRoot;

	/** Server workers map. */
	private Map<String, IWebWorker> workersMap;

	/** Currently active sessions. */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();

	/** Session id randomizer. */
	private Random sessionRandom = new Random();

	/**
	 * Constructor which instantiates new smart http server.
	 *
	 * @param configFileName
	 *            the config file name
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		try {
			properties.load(Files.newInputStream(Paths.get(configFileName)));
		} catch (IOException e) {
			throw new IllegalArgumentException("Given config file path does not exist.");
		}

		this.address = properties.getProperty("server.address");
		this.port = Integer.parseInt(properties.getProperty("server.port"));
		this.workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
		this.sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
		this.documentRoot = Paths.get(properties.getProperty("server.documentRoot"));

		Path mimeConfigFile = Paths.get(properties.getProperty("server.mimeConfig"));
		mimeTypes = loadMimeTypes(mimeConfigFile);

		Path workersConfigFile = Paths.get(properties.getProperty("server.workers"));
		workersMap = loadWorkers(workersConfigFile);

	}

	/**
	 * Helper method which loads mime types from configuration file.
	 *
	 * @param mimeConfigFile
	 *            the mime config file
	 * @return Map containing mime types compatible with this server.
	 */
	private Map<String, String> loadMimeTypes(Path mimeConfigFile) {
		Properties properties = new Properties();

		try {
			properties.load(Files.newInputStream(mimeConfigFile));
		} catch (IOException e) {
			throw new IllegalArgumentException("Given mime config file path in main config file does not exist.");
		}

		return properties.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
	}

	/**
	 * Helper method which loads workers configuration file.
	 *
	 * @param workersConfigFile
	 *            the workers config file
	 * @return Map containing all workers instantiated objects.
	 */
	private Map<String, IWebWorker> loadWorkers(Path workersConfigFile) {
		Properties properties = new Properties();

		try {
			properties.load(Files.newInputStream(workersConfigFile));
		} catch (IOException e) {
			throw new IllegalArgumentException("Given workers config file path in main config file does not exist.");
		}
		Map<String, String> workersConfig = new HashMap<>();
		workersConfig = properties.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
		if (workersConfig.size() != new HashSet<>(workersConfig.values()).size()) {
			throw new IllegalArgumentException(
					"Multiple keys with same values(class path) in same workers configuration.");
		}

		return workersConfig.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> JVMCreateNewInstance(e.getValue())));
	}

	/**
	 * Helper method which instantiates objects from fully qualified class
	 * names, if they exist.
	 *
	 * @param fqcn
	 *            fully qualified class name
	 * @return Web worker instance.
	 */
	private IWebWorker JVMCreateNewInstance(String fqcn) {
		Object newObject = null;

		try {
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			newObject = referenceToClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return (IWebWorker) newObject;
	}

	/**
	 * Method which initializes server processing.
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
		}
		if (!serverThread.isAlive()) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();
			initSessionCleaner();
		}
	}

	/**
	 * Helper method which initializes session cleaner, sessions are cleaned
	 * from session after 5 minutes of inactivity.
	 */
	private void initSessionCleaner() {
		Timer cleanSessions = new Timer(true);
		cleanSessions.schedule(new TimerTask() {
			@Override
			public void run() {
				Map<String, SessionMapEntry> newMap = new HashMap<>(sessions);
				for (Map.Entry<String, SessionMapEntry> entry : newMap.entrySet()) {
					if (entry.getValue().validUntil > System.currentTimeMillis() / 1000) {
						sessions.remove(entry.getKey());
					}
				}
			}
		}, 0, 5 * 60 * 1000);
	}

	/**
	 * Method which terminates server processing.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}

	/**
	 * <code>ServerThread</code> is thread which does al server processing.
	 *
	 * @author Ivan Rezic
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				while (isAlive()) {
					Socket client = null;
					client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <code>ClientWorker</code> represents client in this server-client model.
	 *
	 * @author Ivan Rezic
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/** Session id length. */
		private static final int SID_LENGTH = 20;

		/** Chars used for creation of session id. */
		private static final String SID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		/** Server socket which is used for client connection. */
		private Socket csocket;

		/** Client input stream. */
		private PushbackInputStream istream;

		/** Client output stream. */
		private OutputStream ostream;

		/** Http request version. */
		private String version;

		/** Http method. */
		private String method;

		/** Http parameters given. */
		private Map<String, String> params = new HashMap<String, String>();

		/** Http temporary parameters given. */
		private Map<String, String> tempParams = new HashMap<String, String>();

		/** Http persistent parameters given. */
		private Map<String, String> permPrams = new HashMap<String, String>();

		/** Output cookies. */
		private List<RCCookie> outputCookies = new ArrayList<RCCookie>();

		/** Session id. */
		private String SID;

		/** Http request context. */
		private RequestContext context;

		/**
		 * Constructor which instantiates new client worker.
		 *
		 * @param csocket
		 *            the csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				List<String> request = extractHeaders();
				if (request.size() < 1) {
					sendError(400, "Header size less than one.");
					return;
				}

				String firstLine = request.get(0);
				String[] firstLineParts = firstLine.split(" ");
				method = firstLineParts[0];
				version = firstLineParts[2];
				if (!"GET".equals(method.toUpperCase())) {
					sendError(400, "Expected method: GET.");
					return;
				} else if (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
					sendError(400, "Expected HTTP version: 1.0 or 1.1.");
					return;
				}

				checkSession(request);
				outputCookies.add(new RCCookie("sid", SID, null, correctDomain(request), "/", true));

				String path = null;
				String requestedPath = firstLineParts[1];
				if (requestedPath.contains("?")) {
					String[] parts = requestedPath.split("\\?");
					if (parts.length != 2)
						return;
					path = parts[0];
					String paramString = parts[1];
					parseParameters(paramString);
				} else {
					path = requestedPath;
				}

				internalDispatchRequest(path, true);
				csocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Helper method which checks if current request is made within same
		 * session. If not it creates new ssesion with unique session id.
		 *
		 * @param headerLines
		 *            Http header.
		 */
		private synchronized void checkSession(List<String> headerLines) {
			String sidCandidate = null;

			for (String line : headerLines) {
				if (line.startsWith("Cookie: ")) {
					String[] cookies = line.substring("Cookie: ".length()).split(";");
					for (String cookie : cookies) {
						String[] parts = cookie.split("=");
						if ("sid".equals(parts[0])) {
							sidCandidate = parts[1].replace("\"", "");
						}
					}
				}
			}

			if (sessions.containsKey(sidCandidate)) {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if (entry.validUntil <= System.currentTimeMillis() / 1000) {
					sessions.remove(entry);
				} else {
					entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
					this.SID = entry.sid;
					permPrams = entry.map;
					return;
				}
			}

			initSession();
		}

		/**
		 * Helper method which initializes new session.
		 */
		private void initSession() {
			this.SID = generateSID();
			SessionMapEntry session = new SessionMapEntry();

			session.sid = SID;
			session.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			session.map = new ConcurrentHashMap<>();

			permPrams = session.map;
			sessions.put(session.sid, session);
		}

		/**
		 * Helper method which generates new session id out of given
		 * charracters. New session id is 20 character in length.
		 *
		 * @return Session id as string.
		 */
		private String generateSID() {
			char[] text = new char[SID_LENGTH];

			for (int i = 0; i < SID_LENGTH; i++) {
				text[i] = SID_CHARACTERS.charAt(sessionRandom.nextInt(SID_CHARACTERS.length()));
			}

			return new String(text);
		}

		/**
		 * Helper method which extracts correct domain out of http header.
		 *
		 * @param request
		 *            Http header.
		 * @return Extracted domain.
		 */
		private String correctDomain(List<String> request) {
			for (String line : request) {
				if (line.startsWith("Host")) {
					return line.substring(6, line.indexOf(":", 6));
				}
			}

			return address;
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * It dispatches request to given path.
		 *
		 * @param path
		 *            Path to be dispathced on.
		 * @param directCall
		 *            Is this method direct call,true if it is, false otherwise.
		 * @throws Exception
		 *             The exception which occurs during dispatching.
		 */
		public void internalDispatchRequest(String path, boolean directCall) throws Exception {
			Path absPath = documentRoot.resolve(path.substring(1));
			if (!absPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden url requested.");
				return;
			}

			if (directCall && (path.startsWith("/private") || path.startsWith("/private/"))) {
				sendError(404, "Denied by request filtering configuration.");
			} else if (path.startsWith("/ext/")) {
				try {
					String fqdn = "hr.fer.zemris.java.webserver.workers." + path.substring(5);
					Class.forName(fqdn);
					JVMCreateNewInstance(fqdn).processRequest(getContext());
				} catch (ClassNotFoundException e) {
					System.out.println("Given worker doesnt exist.");
				}
				return;
			}

			IWebWorker worker = workersMap.get(path);
			if (worker != null) {
				worker.processRequest(getContext());
				return;
			}

			String extension = "";
			int i = path.lastIndexOf('.');
			if (i > 0) {
				extension = path.substring(i + 1);
			}
			if (extension.equals("smscr")) {
				runScript(path);
				return;
			}
			String mimeType = mimeTypes.getOrDefault(extension, "application/octet-stream");

			getContext().setMimeType(mimeType);
			if (!Files.exists(absPath) || !Files.isRegularFile(absPath) || !Files.isReadable(absPath)) {
				sendError(404, "Given file path is not valid.");
				return;
			}

			context.write(Files.readAllBytes(absPath));
		}

		/**
		 * Helper method which executes wanted script.
		 *
		 * @param path
		 *            Path to wanted script.
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private void runScript(String path) throws IOException {
			Path scriptPath = documentRoot.resolve(path.substring(1));
			SmartScriptParser parser = new SmartScriptParser(new String(Files.readAllBytes(scriptPath)));
			SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), getContext());
			engine.execute();
		}

		/**
		 * Method used for getting property <code>Context</code>.
		 *
		 * @return context
		 */
		private RequestContext getContext() {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}

			return context;
		}

		/**
		 * Parses the parameters, and extrace parameter key and value.
		 *
		 * @param paramString
		 *            Parameter from http.
		 */
		private void parseParameters(String paramString) {
			String[] parts = paramString.split("[&]");
			for (String part : parts) {
				String[] keyValue = part.split("=");
				if (keyValue.length != 2)
					return;
				params.put(keyValue[0], keyValue[1]);
			}
		}

		/**
		 * Helper method which prints error as respond request.
		 *
		 * @param statusCode
		 *            the status code
		 * @param statusText
		 *            the status text
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {

			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		/**
		 * Helper method which extracts header from input stream data.
		 *
		 * @return The list, each string represents one line of header.
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private List<String> extractHeaders() throws IOException {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			byte[] request = readRequest(istream);
			String requestHeader = new String(request, StandardCharsets.US_ASCII);
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Helper method used in {@link #extractHeaders()}. It is simple
		 * automata which parses data from input stream.
		 *
		 * @param is
		 *            the is
		 * @return the byte[]
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private byte[] readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}
	}

	/**
	 * <code>SessionMapEntry</code> represents each session.
	 *
	 * @author Ivan Rezic
	 */
	private static class SessionMapEntry {

		/** Session id. */
		String sid;

		/** Session life time. */
		long validUntil;

		/** Session persistent parameters */
		Map<String, String> map;
	}

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("config/server.properties");
		server.start();

		Scanner sc = new Scanner(System.in);
		while (!sc.nextLine().equals("stop")) {
		}
		server.stop();
		sc.close();
	}
}
