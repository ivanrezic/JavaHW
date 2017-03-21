package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	public static void main(String[] args) {
//		if (args.length != 1) {
//			System.err.println("Invalid number of arugments");
//		}
//
//		String filepath = args[0];
//		String docBody = null;
//		try {
//			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		SmartScriptParser parser = null;
//		try {
//			parser = new SmartScriptParser(docBody);
//		} catch (SmartScriptParserException e) {
//			System.out.println("Unable to parse document!");
//			System.exit(-1);
//		} catch (Exception e) {
//			System.out.println("If this line ever executes, you have failed this class!");
//			System.exit(-1);
//		}
//
//		DocumentNode document = parser.getDocumentNode();
//		String originalDocumentBody = createOriginalDocumentBody(document);
//		System.out.println(originalDocumentBody); // should write something like
//													// original content of
//													// docBody

		String filepath = "C:/Users/Ivan/workspace/JavaHW/hw03-0036488109/Examples/doc1.txt";
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(docBody);
		System.out.println();
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);

		System.out.println(originalDocumentBody);
		System.out.println();
		System.out.println(originalDocumentBody2);
		if(originalDocumentBody.equals(originalDocumentBody2)){
			System.out.println("Bravo, uspješno riješeno");
		}else{
			System.out.println("Padaš");
		}
	}

	private static String createOriginalDocumentBody(DocumentNode document) {
		if (document == null) {
			throw new IllegalArgumentException("Document given shouldnt be null.");
		}

		return document.toString();
	}

}
