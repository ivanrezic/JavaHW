package hr.fer.zemris.java.hw03.prob1;

/**
 * The Class Lexer represents subsystem for lexic analasys. Input for this lexer
 * is given document and output is array of tokens. Token is series of
 * characters grouped in lexic unit. This lexer is classified as lazy because it
 * delivers one token at a time.
 * 
 * @author Ivan
 */
public class Lexer {

	/** Array of characters which represent input text. */
	private char[] data;

	/** Last grouped series of characters as token. */
	private Token token;

	/** Index of current token. */
	private int currentIndex;

	/** State in which lexer processes text. */
	private LexerState state;

	/**
	 * Constructor which instantiates a new lexer.
	 *
	 * @param text
	 *            Input text.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text shouldnt be null!");
		}
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}

	/**
	 * Sets the state. Possile set states are: <code>basic</code> and
	 * <code>extended</code>.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State shouldnt be null");
		}
		this.state = state;
	}

	/**
	 * Returns last processed token.
	 *
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Public factory method which extracts tokens from imput text. It delegates
	 * processing to other helper methods({@link #findWord()},
	 * {@link #findNumber()}, {@link #skipBlanks()},
	 * {@link #extenedStateSearch()} ).
	 *
	 * @return char series grouped as <code>Token</code>
	 * @throws LexerException
	 *             if it reaches end of file
	 */
	public Token nextToken() {
		if (token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException("No tokens available.");
		}

		while (currentIndex < data.length) {
			if (state == LexerState.BASIC) {
				if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
					token = new Token(TokenType.WORD, findWord());
					return token;
				} else if (Character.isDigit(data[currentIndex])) {
					token = new Token(TokenType.NUMBER, findNumber());
					return token;
				} else if (skipBlanks() == 0) {
					token = new Token(TokenType.SYMBOL, data[currentIndex++]);
					if (token.getValue() == "#") {
						if (state == LexerState.BASIC) {
							setState(LexerState.EXTENDED);
						} else {
							setState(LexerState.BASIC);
						}
					}
					return token;
				}
			} else {
				return extenedStateSearch();
			}
		}

		token = new Token(TokenType.EOF, null);
		return token;
	}

	/**
	 * Helper method which represents lexer processing while in extended state.
	 *
	 * @return char series grouped as <code>Token</code>
	 */
	private Token extenedStateSearch() {
		String word = "";

		if (skipBlanks() == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		while (data[currentIndex] != '#' && data[currentIndex] != ' ') {
			word += data[currentIndex++];
		}

		if (data[currentIndex] == '#') {
			setState(LexerState.BASIC);
		} else {
			skipBlanks();
		}

		token = new Token(TokenType.WORD, word);
		return token;
	}

	/**
	 * Helper method which creates long number from given char sequence. Maximum
	 * number value is: <code>Long.MAX_VALUE</code>.
	 *
	 * @return the long number
	 * @throws LexerException
	 *             if given char sequence represnts number larger than maximum
	 *             value.
	 */
	private Long findNumber() {
		StringBuilder word = new StringBuilder();

		while (Character.isDigit(data[currentIndex])) {
			word.append(data[currentIndex++]);
			if (currentIndex >= data.length)
				break;
		}

		if (Double.parseDouble(word.toString()) - Long.MAX_VALUE > 0) {
			throw new LexerException("Broje ne smije biti veci od " + Long.MAX_VALUE);
		}

		return Long.parseLong(word.toString());
	}

	/**
	 * Helper method which creates words from given char sequence.
	 * 
	 * @return the string built from char sequence
	 */
	private String findWord() {
		StringBuilder word = new StringBuilder();

		while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			if (data[currentIndex] == '\\') {
				if (currentIndex == data.length - 1 || Character.isLetter(data[currentIndex + 1])) {
					throw new LexerException();
				} else {
					word.append(data[++currentIndex]);
					currentIndex++;
					continue;
				}
			}
			word.append(data[currentIndex++]);
			if (currentIndex >= data.length)
				break;
		}
		return word.toString();
	}

	/**
	 * Helper method which skips blanks in given text.
	 *
	 * @return number of skipped spaces
	 */
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
