package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public class Lexer {
	private String[] document;
	private ArrayIndexedCollection helperCollector;
	private Token[] tokens;
	private int currentIndex;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text shouldnt be null!");
		}
		this.document = text.split("(?=(\\{\\$))|(?<=(\\$\\}))");
		helperCollector= new ArrayIndexedCollection();
		tokenizer();
	}

	private void tokenizer() {
		for (String part : document) {
			if (part.startsWith("{")) {
				processTag(part);
			} else {
				processText(part);
			}
		}
	}

	public void processText(String part) {
		char[] charParts = part.toCharArray();
		String accuratePart;

		for (int i = 0; i < charParts.length; i++) {
			if (charParts[i] == '\\' && (charParts[i + 1] != '{' || charParts[i + 1] != '\\')) {
				throw new LexerException("After \\ there should be no characters except { and \\.");
			}
		}

		accuratePart = new String(charParts).replace("\\", "\\\\").replace("{", "\\{");
		helperCollector.add(new Token(TokenType.TEXT, accuratePart));
	}

	public void processTag(String part) {
		part.replace("{$", "").replaceAll("\\s+", "");
		helperCollector.add(new Token(TokenType.TAG, "{$"));
		
		
	}

}
