package hr.fer.zemris.bf.lexer;

public class Lexer {

	private int index;
	private char[] expression;

	public Lexer(String expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Valid expression must be delivered.");
		}

		this.expression = expression.toCharArray();
	}

	public Token nextToken() {
		return extractToken();
	}

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

	private Token properIdentificator(String word) {
		if (word.equals("and") || word.equals("xor") || word.equals("or") || word.equals("not")) {
			return new Token(TokenType.OPERATOR, word);
		} else if (word.equals("true") || word.equals("false")) {
			return new Token(TokenType.CONSTANT, Boolean.parseBoolean(word));
		} else {
			return new Token(TokenType.VARIABLE, word.toUpperCase());
		}
	}

	private Token number(char current) {
		index++;

		if (index < expression.length && Character.isDigit(expression[index])) {
			throw new LexerException("Unexpected number: " + current + expression[index] + ".");
		} else if (current == '1') {
			return new Token(TokenType.CONSTANT, true);
		}

		return new Token(TokenType.CONSTANT, false);
	}

	private boolean isOperator(char current) {
		if (current == '*' || current == '+' || current == '!' || current == ':') {
			return true;
		}

		return false;
	}

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

	private Token bracket(char current) {
		index++;

		if (current == '(') {
			return new Token(TokenType.OPEN_BRACKET, current);
		}

		return new Token(TokenType.CLOSED_BRACKET, current);
	}

}
