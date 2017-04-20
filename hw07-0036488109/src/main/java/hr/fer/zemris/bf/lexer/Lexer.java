package hr.fer.zemris.bf.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

	private static final String OPERATORS_WITHOUT_XOR = "\\*|\\+|\\!|,";
	private int index;
	private int tokenIndex;
	private char[] expression;
	private List<Token> tokens;

	public Lexer(String expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Valid expression must be delivered.");
		}

		this.expression = expression.toCharArray();
		this.tokens = new ArrayList<>();
		extractTokens();
	}

	public Token nextToken() {

		return null;
	}

	private void extractTokens() {
		while (index < expression.length) {
			char current = expression[index];

			try {
				checkCurrent(current);
			} catch (RuntimeException e) {
				throw new LexerException(e.getMessage());
			}
		}

		tokens.add(new Token(TokenType.EOF, null));
	}

	private void checkCurrent(char current) {
		if (current == '(') {
			tokens.add(new Token(TokenType.OPEN_BRACKET, current));
		} else if (current == ')') {
			tokens.add(new Token(TokenType.CLOSED_BRACKET, current));
		} else if (Character.isLetter(current)) {
			extractIdentificator();
		} else if (Character.isDigit(current)) {
			extractNumberArray();
		} else if (currentIsOperator(current)) {
			extractOperator();
		} else {
			throw new IllegalArgumentException("Illegal character in given expression");
		}
	}

	private void extractIdentificator() {

	}

	private void extractNumberArray() {
		if (index < expression.length - 1 && Character.isDigit(expression[index + 1])) {
			throw new IllegalArgumentException("Only one digit is allowed per number");
		}
		
		char current = expression[index];
		if (current == '1') {
			tokens.add(new Token(TokenType.CONSTANT, true));
		}else if (current == '0') {
			tokens.add(new Token(TokenType.CONSTANT, false));
		}else {
			throw new IllegalArgumentException("Allowed numbers are '1' and '0'.");
		}
		
		index++;
	}

	private void extractOperator() {
		char current = expression[index];

		switch (current) {
		case '*':
			tokens.add(new Token(TokenType.OPERATOR, "and"));
			break;
		case '+':
			tokens.add(new Token(TokenType.OPERATOR, "or"));
			break;
		case '!':
			tokens.add(new Token(TokenType.OPERATOR, "not"));
			break;
		case ':':
			if (expression[index + 1] == '+' && expression[index + 2] == ':') {
				tokens.add(new Token(TokenType.OPERATOR, "xor"));
				index += 2;
			} else {
				throw new IllegalArgumentException("':' should be followed with '+:'");
			}
			break;
		}

		index++;
	}

	private boolean currentIsOperator(char current) {
		if (current == '*' || current == '+' || current == '!' || current == ':') {
			return true;
		}

		return false;
	}

	// private boolean regexMatcher(String string, String pattern) {
	// Pattern p = Pattern.compile(pattern);
	// Matcher m = p.matcher(string);
	//
	// if (m.find()) {
	// return true;
	// }
	//
	// return false;
	// }

}
