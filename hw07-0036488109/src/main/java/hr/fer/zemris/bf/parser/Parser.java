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

	private void parse() {

		lexer.nextToken();
		node = startParsing();

		if (lexer.getCurrentToken().getTokenType() != TokenType.EOF) {

			String message = String.format("Unexpected token: Type: %s, Value: %s, Value is instance of: %s",
					lexer.getCurrentToken().getTokenType(), lexer.getCurrentToken().getTokenValue(),
					lexer.getCurrentToken().getTokenValue().getClass().getName());

			throw new ParserException(message);
		}
	}

	private Node startParsing() {

		node = e1();
		return node;
	}

	private Node e1() {
		Node node = e2();

		if (lexer.getCurrentToken().getTokenValue() == null) {
			return node;
		}

		ArrayList<Node> children = new ArrayList<>();
		children.add(node);
		while (lexer.getCurrentToken().getTokenValue().equals("or")) {

			checkIfEOF();
			children.add(e2());

			BinaryOperatorNode orNode = new BinaryOperatorNode("or", children, (t, s) -> Boolean.logicalOr(t, s));

			if (endOfOperator("or")) {
				return orNode;
			}

		}
		return node;

	}

	private Node e2() {
		Node node = e3();

		if (lexer.getCurrentToken().getTokenValue() == null) {
			return node;
		}
		ArrayList<Node> children = new ArrayList<>();
		children.add(node);
		while (lexer.getCurrentToken().getTokenValue().equals("xor")) {

			checkIfEOF();
			children.add(e3());

			BinaryOperatorNode xorNode = new BinaryOperatorNode("xor", children, (t, s) -> Boolean.logicalXor(t, s));

			if (endOfOperator("xor")) {
				return xorNode;
			}
		}
		return node;

	}

	private Node e3() {
		Node node = e4();

		if (lexer.getCurrentToken().getTokenType() == TokenType.EOF) {
			return node;
		}

		ArrayList<Node> children = new ArrayList<>();
		children.add(node);
		while (lexer.getCurrentToken().getTokenValue().equals("and")) {

			checkIfEOF();
			children.add(e4());

			BinaryOperatorNode andNode = new BinaryOperatorNode("and", children, (t, s) -> Boolean.logicalAnd(t, s));

			if (endOfOperator("and")) {
				return andNode;
			}
		}
		return node;
	}

	private Node e4() {

		if (lexer.getCurrentToken().getTokenValue().equals("not")) {

			checkIfEOF();
			Node node = e4();
			UnaryOperatorNode notNode = new UnaryOperatorNode("not", node, t -> Boolean.logicalXor(t, true));

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

			if (lexer.getCurrentToken().getTokenType() != TokenType.CLOSED_BRACKET) {
				throw new ParserException("Expected ')' but found " + lexer.getCurrentToken().getTokenType() + ".");
			}
			break;

		default:
			String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
					lexer.getCurrentToken().getTokenType(), lexer.getCurrentToken().getTokenValue());
			throw new ParserException(message);

		}

		lexer.nextToken();

		return node;

	}

	/**
	 * Private method used for checking if next token is of type EOF, and if it
	 * is, that means that given logical expression was wrong because we
	 * expected more tokens different from EOF.
	 */
	private void checkIfEOF() {

		if (lexer.nextToken().getTokenType() == TokenType.EOF) {
			String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
					lexer.getCurrentToken().getTokenType(), lexer.getCurrentToken().getTokenValue());

			throw new ParserException(message);
		}
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
		return lexer.getCurrentToken().getTokenType() == TokenType.EOF
				|| !lexer.getCurrentToken().getTokenValue().equals(operatorValue);
	}

	/**
	 * Getter method used for getting the base node of expression.
	 * 
	 * @return base node of the expression
	 */
	public Node getExpression() {
		return node;
	}
	
	public static void main(String[] args) {
		Parser parser = new Parser("a xor b or c xor d");
		Node liNode = parser.getExpression();
		System.out.println(liNode);
	}

}