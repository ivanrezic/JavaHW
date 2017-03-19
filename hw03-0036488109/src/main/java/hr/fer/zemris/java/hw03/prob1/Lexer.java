package hr.fer.zemris.java.hw03.prob1;

public class Lexer {
	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text shouldnt be null!");
		}
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}
	
	public void setState(LexerState state){
		if (state == null) {
			throw new IllegalArgumentException("State shouldnt be null");
		}
		
		this.state = state;
	}

	public Token getToken() {
		return token;
	}

	public Token nextToken() {
		if (token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException("No tokens available.");
		}

		while (currentIndex < data.length) {
			if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				token = new Token(TokenType.WORD, findWord());
				return token;
			} else if (Character.isDigit(data[currentIndex])) {
				token = new Token(TokenType.NUMBER, findNumber());
				return token;
			} else if (skipBlanks() == 0) {
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
				return token;
			}
		}

		token = new Token(TokenType.EOF, null);
		return token;
	}
	
//	private Token basicTokenFinder(){
//		
//	}
//	
//	private Token extendedTokenFinder() {
//		
//	}

	private Long findNumber() {
		String word = "";
		
		while(Character.isDigit(data[currentIndex])){
			word += data[currentIndex++];
			if (currentIndex >= data.length) break;
		}
		
		if (Double.parseDouble(word) - Long.MAX_VALUE  > 0) {
			throw new LexerException("Broje ne smije biti veci od "+ Long.MAX_VALUE);
		}
		
		return Long.parseLong(word);
	}

	private String findWord() {
		String word = "";

		while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			if (data[currentIndex] == '\\') {
				if (currentIndex == data.length - 1 || Character.isLetter(data[currentIndex + 1])) {
					throw new LexerException();
				} else{
					word += data[++currentIndex];
					currentIndex++;
					continue;
				}
			}
			word += data[currentIndex++];
		}
		return word;
	}

	private int skipBlanks() {
		int counter = 0;

		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				counter++;
				continue;
			}
			break;
		}

		return counter;
	}
}
