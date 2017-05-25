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
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {
	private String address;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String, IWebWorker> workersMap;

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

	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
		}
		if (!serverThread.isAlive()) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();
		}
	}

	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}

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

	private class ClientWorker implements Runnable, IDispatcher {
		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private Map<String, String> params = new HashMap<String, String>();
		private Map<String, String> tempParams = new HashMap<String, String>();
		private Map<String, String> permPrams = new HashMap<String, String>();
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		private String SID;
		private RequestContext context;

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

				String path = null;
				String requestedPath = firstLineParts[1];
				if (requestedPath.contains("?")) {
					String[] parts = requestedPath.split("\\?");
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

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		public void internalDispatchRequest(String path, boolean directCall) throws Exception {
			Path absPath = documentRoot.resolve(path.substring(1));
			if (!absPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden url requested.");
				return;
			}

			if (path.startsWith("/ext/")) {
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
				runScrtipt(path);
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

		private void runScrtipt(String path) throws IOException {
			Path scriptPath = documentRoot.resolve(path.substring(1));
			SmartScriptParser parser = new SmartScriptParser(new String(Files.readAllBytes(scriptPath)));
			SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), getContext());
			engine.execute();
		}

		private RequestContext getContext() {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}

			return context;
		}

		private void parseParameters(String paramString) {
			String[] parts = paramString.split("[&]");
			for (String part : parts) {
				String[] keyValue = part.split("=");
				if (keyValue.length != 2)
					return;
				params.put(keyValue[0], keyValue[1]);
			}
		}

		private void sendError(int statusCode, String statusText) throws IOException {

			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

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
