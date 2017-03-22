package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The Class SmartScriptTester is class which represents text parsing.
 * 
 * @author Ivan
 */
public class SmartScriptTester {

	/**
	 * The main method which accepts one argument from command line.
	 *
	 * @param args
	 *            one argument from command line
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arugments");
		}

		String filepath = args[0];
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (LexerException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (SmartScriptParserException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like
													// original content of
													// docBody

	}

	/**
	 * Helper method which creates original document body from its child nodes..
	 *
	 * @param document
	 *            output document from parser processing
	 * @return the string representation of DocumentNode
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		if (document == null) {
			throw new IllegalArgumentException("Document given shouldnt be null.");
		}

		return document.toString();
	}

}
