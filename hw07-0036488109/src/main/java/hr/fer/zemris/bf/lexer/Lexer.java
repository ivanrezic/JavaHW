package hr.fer.zemris.bf.lexer;

/**
 * The Class Lexer represents subsystem for lexic analysis. Input for this lexer
 * is given expression and output is token extracted from same expression. Token
 * is series of characters grouped in lexic unit. This lexer is classified as
 * lazy because it delivers one token at a time.
 * 
 * @author Ivan Rezic
 */
public class Lexer {

	/** Index of current letter from expression. */
	private int index;

	/** Given expression as char array. */
	private char[] expression;

	/** Last extracted token from expression. */
	private Token currentToken;

	/**
	 * Constructor which instantiates new lexer.
	 *
	 * @param expression
	 *            the expression
	 */
	public Lexer(String expression) {
		if (expression == null) {
			throw new LexerException("Valid expression must be delivered.");
		}

		this.expression = expression.toCharArray();
	}

	/**
	 * Method used for getting property <code>CurrentToken</code>.
	 *
	 * @return {@link #currentToken}
	 */
	public Token getCurrentToken() {
		return currentToken;
	}

	/**
	 * Extracts next token from expression.
	 *
	 * @return {@link Token}
	 */
	public Token nextToken() {
		currentToken = extractToken();
		return currentToken;
	}

	/**
	 * Helper method in which all token extraction logic is contained. It
	 * iterates through expression char by char and delegates its extraction to
	 * other helper methods depending on last char read.
	 *
	 * @return {@link Token}
	 */
	private Token extractToken() {
		while (index < expression.length) {
			char current = expression[index];

			if (current == '(' || current == ')') {
				return bracket(current);
			} else if (Character.isLetter(current)) {
				return identificator();
			} else if (Character.isDigit(current)) {
				return number(current);
			} else if (isOperator(current)) {
				return operator(current);
			} else if (current != ' ') {
				throw new LexerException("Illegal character in given expression");
			}

			index++;
		}

		return new Token(TokenType.EOF, null);
	}

	/**
	 * Helper method called by reading first letter. It groups next sequence of
	 * chars or underscores to one string and delegates its tokenization to
	 * {@link #properIdentificator(String)}.
	 *
	 * @return {@link Token}
	 */
	private Token identificator() {
		StringBuilder identificator = new StringBuilder();

		while (index < expression.length) {
			char current = expression[index];

			if (Character.isLetterOrDigit(current) || current == '_') {
				identificator.append(current);
				index++;
			} else {
				break;
			}
		}

		return properIdentificator(identificator.toString().toLowerCase());
	}

	/**
	 * Helper method which checks if given string matches predefined operators
	 * or constants. If it doesn't match any, it returns given string as new
	 * <code>Token</code> as of type variable.
	 *
	 * @param word
	 *            grouped char sequence
	 * @return {@link Token}
	 */
	private Token properIdentificator(String word) {
		if (word.equals("and") || word.equals("xor") || word.equals("or") || word.equals("not")) {
			return new Token(TokenType.OPERATOR, word);
		} else if (word.equals("true") || word.equals("false")) {
			return new Token(TokenType.CONSTANT, Boolean.parseBoolean(word));
		} else {
			return new Token(TokenType.VARIABLE, word.toUpperCase());
		}
	}

	/**
	 * Helper method called by reading first digit. If it is followed by other
	 * digits, method throws exception.
	 *
	 * @return {@link Token}
	 * @throws LexerException
	 *             if there is one or more digits after current char
	 */
	private Token number(char current) {
		index++;

		if (index < expression.length && Character.isDigit(expression[index])) {
			throw new LexerException("Unexpected number: " + current + expression[index] + ".");
		} else if (current == '1') {
			return new Token(TokenType.CONSTANT, true);
		} else if (current != '0') {
			throw new LexerException("Unexpected number: " + current + ".");
		}

		return new Token(TokenType.CONSTANT, false);
	}

	/**
	 * Helper method which checks if current symbol represents operator.
	 *
	 * @param current
	 *            last read char from expression
	 * @return true, if it is operator, false otherwise
	 */
	private boolean isOperator(char current) {
		if (current == '*' || current == '+' || current == '!' || current == ':') {
			return true;
		}

		return false;
	}

	/**
	 * Helper method which based on read char return proper token of type
	 * operator.
	 *
	 * @param current
	 *            last read char
	 * @return {@link Token}
	 */
	private Token operator(char current) {
		index++;

		switch (current) {
		case '*':
			return new Token(TokenType.OPERATOR, "and");
		case '+':
			return new Token(TokenType.OPERATOR, "or");
		case '!':
			return new Token(TokenType.OPERATOR, "not");
		}
		if (index < expression.length - 1 && expression[index++] == '+' && expression[index++] == ':') {
			return new Token(TokenType.OPERATOR, "xor");
		}

		throw new LexerException("':' should be followed with '+:'");
	}

	/**
	 * Helper method which based on read char return proper token of type
	 * bracket.
	 *
	 * @param current
	 *            last read char
	 * @return {@link Token}
	 */
	private Token bracket(char current) {
		index++;

		if (current == '(') {
			return new Token(TokenType.OPEN_BRACKET, current);
		}

		return new Token(TokenType.CLOSED_BRACKET, current);
	}
}
