package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * <code>EngineDemo</code> is demonstration program which shows how each script
 * is parsed and executed.
 *
 * @author Ivan Rezic
 */
@SuppressWarnings("unused")
public class EngineDemo {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            The arguments from command line, not used here.
	 */
	public static void main(String[] args) {
		String documentBody = null;
		try {
			documentBody = new String(Files.readAllBytes(Paths.get("webroot/scripts/osnovni.smscr")),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Wrong file path given.");
			System.exit(-1);
		}

		osnovni(documentBody);
		// zbrajanje(documentBody);
		// brojPoziva(documentBody);
		// fibonacci(documentBody);

	}

	/**
	 * Demonstrates, simple operations provided by
	 * {@linkplain SmartScriptEngine}.
	 *
	 * @param documentBody
	 *            the document body
	 */
	private static void osnovni(String documentBody) {
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

	/**
	 * Demonstrates simple addition of two parameters stored in a parameters
	 * map.
	 *
	 * @param documentBody
	 *            the document body
	 */
	private static void zbrajanje(String documentBody) {
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

	/**
	 * Prints number which is by one larger than number stored in
	 * persistentParameters map. This script is later used to count number of
	 * times client requested context from server.
	 *
	 * @param documentBody
	 *            the document body
	 */
	private static void brojPoziva(String documentBody) {
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

	/**
	 * Prints first ten fibonnaci numbers.
	 *
	 * @param documentBody
	 *            the document body
	 */
	private static void fibonacci(String documentBody) {
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}
}
