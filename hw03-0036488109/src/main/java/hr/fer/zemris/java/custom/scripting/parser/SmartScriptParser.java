package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

public class SmartScriptParser {

	public SmartScriptParser(String documentBody) {
		if (documentBody == null) {
			throw new SmartScriptParserException("Document body can't be null.");
		}
		
		Lexer lexer = new Lexer(documentBody);
		parse(lexer);
	}

	public void parse(Lexer lexer) {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		
	}
	
	public DocumentNode getDocumentNode(){
		return null;//obraditi
	}
}
