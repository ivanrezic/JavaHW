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

/**
 * <code>Parser</code> represents object which processes text through syntax
 * analysis. After processing text is divided in syntax groups which are later
 * encapsulated in nodes used for construction of syntax tree. There are trees
 * main groups, variables, constants and operators.Parser uses recursive descent
 * to create tree.
 *
 * @author Ivan Rezic
 */
public class Parser {

	/** Lexer used in this parser. */
	private Lexer lexer;

	/** Root node of our tree. */
	private Node node;

	/**
	 * Constructor which instantiates new parser. And call first method of our
	 * recursive descent.
	 *
	 * @param expression
	 *            The epression.
	 */
	public Parser(String expression) {
		if (expression == null) {
			throw new ParserException("Expression can not be null.");
		}

		this.lexer = new Lexer(expression);

		try {
			parse();

		} catch (LexerException e) {
			throw new ParserException("Lexer has thrown exception: " + e.getMessage());
		}
	}

	/**
	 * Checks if current token is of given type.
	 *
	 * @param type
	 *            The type to be matched against.
	 * @return True, if it is token of wanted type, false otherwise.
	 */
	private boolean isTokenOfType(TokenType type) {
		return getCurrentTokenType() == type;
	}

	/**
	 * Method used for getting current token value.
	 *
	 * @return current token value
	 */
	private Object getCurrentTokenValue() {
		return lexer.getCurrentToken().getTokenValue();
	}

	/**
	 * Method used for getting current token type.
	 *
	 * @return current token type
	 */
	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().getTokenType();
	}

	/**
	 * Getter method used for getting the root node.
	 * 
	 * @return root node of the expression
	 */
	public Node getExpression() {
		return node;
	}

	/**
	 * Initialization method which calls {@link #e1()}.
	 * 
	 * @throws ParserException
	 *             if current token is unexpected
	 */
	private void parse() {

		lexer.nextToken();
		node = e1();

		if (!isTokenOfType(TokenType.EOF)) {

			String message = String.format("Unexpected token: Type: %s, Value: %s, Value is instance of: %s",
					getCurrentTokenType(), getCurrentTokenValue(), getCurrentTokenValue().getClass().getName());

			throw new ParserException(message);
		}
	}

	/**
	 * First method of our recursive descent. It creates
	 * {@link BinaryOperatorNode} if there is one or more "or" in the expression
	 * after current node value.
	 *
	 * @return the node
	 */
	private Node e1() {
		Node node = e2();

		if (getCurrentTokenValue() == null) {
			return node;
		}

		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		while (getCurrentTokenValue().equals("or")) {
			if (lexer.nextToken().getTokenType() == TokenType.EOF) {
				String message = String.format("Unexpected token found: {Type: %s, Value: %s}.", getCurrentTokenType(),
						getCurrentTokenValue());

				throw new ParserException(message);
			}
			nodes.add(e2());

			BinaryOperatorNode orNode = new BinaryOperatorNode("or", nodes, (t, s) -> t || s);

			if (isTokenOfType(TokenType.EOF) || !getCurrentTokenValue().equals("or")) {
				return orNode;
			}

		}
		return node;

	}

	/**
	 * Second method of our recursive descent. It creates
	 * {@link BinaryOperatorNode} if there is one or more "xor" in the
	 * expression after current node value.
	 *
	 * @return the node
	 */
	private Node e2() {
		Node node = e3();

		if (getCurrentTokenValue() == null) {
			return node;
		}
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		while (getCurrentTokenValue().equals("xor")) {
			if (lexer.nextToken().getTokenType() == TokenType.EOF) {
				String message = String.format("Unexpected token found: {Type: %s, Value: %s}.", getCurrentTokenType(),
						getCurrentTokenValue());

				throw new ParserException(message);
			}
			nodes.add(e3());

			BinaryOperatorNode xorNode = new BinaryOperatorNode("xor", nodes, (t, s) -> t ^ s);

			if (isTokenOfType(TokenType.EOF) || !getCurrentTokenValue().equals("xor")) {
				return xorNode;
			}
		}
		return node;

	}

	/**
	 * Third method of our recursive descent. It creates
	 * {@link BinaryOperatorNode} if there is one or more "and" in the
	 * expression after current node value.
	 *
	 * @return the node
	 */
	private Node e3() {
		Node node = e4();

		if (getCurrentTokenType() == TokenType.EOF) {
			return node;
		}

		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node);
		while (getCurrentTokenValue().equals("and")) {
			if (lexer.nextToken().getTokenType() == TokenType.EOF) {
				String message = String.format("Unexpected token found: {Type: %s, Value: %s}.", getCurrentTokenType(),
						getCurrentTokenValue());

				throw new ParserException(message);
			}
			nodes.add(e4());
			BinaryOperatorNode andNode = new BinaryOperatorNode("and", nodes, (t, s) -> t && s);

			if (isTokenOfType(TokenType.EOF) || !getCurrentTokenValue().equals("and")) {
				return andNode;
			}
		}
		return node;
	}

	/**
	 * Fourth method of our recursive descent. It creates
	 * {@link UnaryOperatorNode} if there is "not" followd by node value in the
	 * expression.
	 *
	 * @return the node
	 */
	private Node e4() {

		if (getCurrentTokenValue().equals("not")) {
			if (lexer.nextToken().getTokenType() == TokenType.EOF) {
				String message = String.format("Unexpected token found: {Type: %s, Value: %s}.", getCurrentTokenType(),
						getCurrentTokenValue());

				throw new ParserException(message);
			}

			Node node = e4();
			UnaryOperatorNode notNode = new UnaryOperatorNode("not", node, t -> !t);

			return notNode;
		} else {
			return e5();
		}

	}

	/**
	 * Fifth method of our recursive descent. It creates {@link ConstantNode} or
	 * {@link VariableNode} if current token matches expected one. If there is
	 * opened bracket and closed bracket and expression between it starts
	 * recursive descendig all over again just for that expression.
	 *
	 * @return the node
	 */
	private Node e5() {
		Token currentToken = lexer.getCurrentToken();
		Node node;

		if (isTokenOfType(TokenType.VARIABLE)) {
			node = new VariableNode((String) currentToken.getTokenValue());
		} else if (isTokenOfType(TokenType.CONSTANT)) {
			node = new ConstantNode((boolean) currentToken.getTokenValue());
		} else if (isTokenOfType(TokenType.OPEN_BRACKET)) {
			lexer.nextToken();
			node = e1();

			if (getCurrentTokenType() != TokenType.CLOSED_BRACKET) {
				throw new ParserException("Expected ')' but found " + getCurrentTokenType() + ".");
			}
		} else {
			String message = String.format("Unexpected token found: {Type: %s, Value: %s}.", getCurrentTokenType(),
					getCurrentTokenValue());
			throw new ParserException(message);
		}

		lexer.nextToken();
		return node;

	}

}