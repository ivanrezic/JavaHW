package hr.fer.zemris.java.custom.scripting.lexer;

public class Lexer {
	private LexerState state;
	private String[] document;
	private Token token;
	private int currentIndex;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text shouldnt be null!");
		}
		this.state = LexerState.TEXT;
		this.document = text.split("(?=(\\{\\$))|(?<=(\\$\\}))");
	}

	public Token getToken() {
		return token;
	}
	
	
	
}
