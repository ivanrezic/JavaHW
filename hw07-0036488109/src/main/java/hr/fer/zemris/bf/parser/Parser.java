package hr.fer.zemris.bf.parser;

import java.util.ArrayList;

import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.lexer.Token;
import hr.fer.zemris.bf.lexer.TokenType;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

public class Parser {

	private Lexer lexer;
	private Node node;

	public Parser(String expression) {
		if (expression == null) {
			throw new ParserException("Expression mustn't be null");
		}

		this.lexer = new Lexer(expression);

		try {
			parse();

		} catch (LexerException e) {
			throw new ParserException("Lexer has thrown exception: " + e.getMessage());
		}
	}

	private boolean isTokenOfType(TokenType type) {
		return getCurrentTokenType() == type;
	}

	private void parse() {

		lexer.nextToken();
		node = e1();

		if (!isTokenOfType(TokenType.EOF)) {

			String message = String.format("Unexpected token: Type: %s, Value: %s, Value is instance of: %s",
					getCurrentTokenType(), getCurrentTokenValue(),
					getCurrentTokenValue().getClass().getName());

			throw new ParserException(message);
		}
	}

	private Node e1() {
		Node node = e2();

		if (getCurrentTokenValue() == null) {
			return node;
		}

		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		while (getCurrentTokenValue().equals("or")) {
			if (lexer.nextToken().getTokenType() == TokenType.EOF) {
				String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
						getCurrentTokenType(), getCurrentTokenValue());

				throw new ParserException(message);
			}
			nodes.add(e2());

			BinaryOperatorNode orNode = new BinaryOperatorNode("or", nodes, (t, s) -> t || s);

			if (endOfOperator("or")) {
				return orNode;
			}

		}
		return node;

	}

	private Node e2() {
		Node node = e3();

		if (getCurrentTokenValue() == null) {
			return node;
		}
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		while (getCurrentTokenValue().equals("xor")) {
			if (lexer.nextToken().getTokenType() == TokenType.EOF) {
				String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
						getCurrentTokenType(), getCurrentTokenValue());

				throw new ParserException(message);
			}
			nodes.add(e3());

			BinaryOperatorNode xorNode = new BinaryOperatorNode("xor", nodes, (t, s) -> t ^ s);

			if (endOfOperator("xor")) {
				return xorNode;
			}
		}
		return node;

	}

	private Node e3() {
		Node node = e4();

		if (getCurrentTokenType() == TokenType.EOF) {
			return node;
		}

		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		while (getCurrentTokenValue().equals("and")) {
			if (lexer.nextToken().getTokenType() == TokenType.EOF) {
				String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
						getCurrentTokenType(), getCurrentTokenValue());

				throw new ParserException(message);
			}
			nodes.add(e4());
			BinaryOperatorNode andNode = new BinaryOperatorNode("and", nodes, (t, s) -> t && s);

			if (endOfOperator("and")) {
				return andNode;
			}
		}
		return node;
	}

	private Node e4() {

		if (getCurrentTokenValue().equals("not")) {
			if (lexer.nextToken().getTokenType() == TokenType.EOF) {
				String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
						getCurrentTokenType(), getCurrentTokenValue());

				throw new ParserException(message);
			}
			
			Node node = e4();
			UnaryOperatorNode notNode = new UnaryOperatorNode("not", node, t -> !t);

			return notNode;
		} else {
			return e5();
		}

	}

	private Node e5() {
		Token currentToken = lexer.getCurrentToken();
		Node node;

		switch (currentToken.getTokenType()) {
		case VARIABLE:
			node = new VariableNode((String) currentToken.getTokenValue());
			break;

		case CONSTANT:
			node = new ConstantNode((boolean) currentToken.getTokenValue());
			break;

		case OPEN_BRACKET:
			lexer.nextToken();
			node = e1();

			if (getCurrentTokenType() != TokenType.CLOSED_BRACKET) {
				throw new ParserException("Expected ')' but found " + getCurrentTokenType() + ".");
			}
			break;

		default:
			String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
					getCurrentTokenType(), getCurrentTokenValue());
			throw new ParserException(message);

		}

		lexer.nextToken();

		return node;

	}

	private Object getCurrentTokenValue() {
		return lexer.getCurrentToken().getTokenValue();
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().getTokenType();
	}

	/**
	 * Private method used in methods {@link Parser#e1()}, {@link Parser#e2()}
	 * and {@link Parser#e3()} for determining if next token is same operator
	 * and should we continue with our loop or return current found node.
	 * 
	 * @param operatorValue
	 *            current operator value, can be only one of those: "or", "and",
	 *            "xor"
	 * @return <code>true</code> if next token is of type EOF or different value
	 *         than operatorValue given as argument of this method,
	 *         <code>false</code> otherwise
	 */
	private boolean endOfOperator(String operatorValue) {
		return getCurrentTokenType() == TokenType.EOF
				|| !getCurrentTokenValue().equals(operatorValue);
	}

	/**
	 * Getter method used for getting the base node of expression.
	 * 
	 * @return base node of the expression
	 */
	public Node getExpression() {
		return node;
	}

}