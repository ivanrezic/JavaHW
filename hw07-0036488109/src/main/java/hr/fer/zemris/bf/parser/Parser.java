package hr.fer.zemris.bf.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.Token;
import hr.fer.zemris.bf.lexer.TokenType;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

public class Parser {

	private Lexer lexer;
	private Node parsedTree;

	public Parser(String expression) {
		if (expression == null) {
			throw new ParserException("Expression shouldnt be null.");
		}

		this.lexer = new Lexer(expression);
		this.parsedTree = e1(lexer.nextToken());
	}

	public Node getExpression() {
		return parsedTree;
	}

	private boolean isTokenOfType(TokenType type) {
		return lexer.getCurrentToken().getTokenType() == type;
	}

	private Node e1(Token current) {
		List<Node> nodes = new ArrayList<>();
		nodes.add(e2(current));

		while ("or".equals(lexer.getCurrentToken().getTokenValue())) {
			Node node = e1(lexer.nextToken());

			if (node instanceof BinaryOperatorNode) {
				BinaryOperatorNode help = (BinaryOperatorNode) node;
				if ("or".equals(help.getName())) {
					nodes.addAll(((BinaryOperatorNode) node).getChildren());
				} else {
					nodes.add(node);
				}
			} else {
				nodes.add(node);
			}
		}

		if (nodes.size() > 1) {
			return new BinaryOperatorNode("or", nodes, (a, b) -> a || b);
		}

		return nodes.get(0);
	}

	private Node e2(Token current) {
		List<Node> nodes = new ArrayList<>();
		nodes.add(e3(current));

		while ("xor".equals(lexer.getCurrentToken().getTokenValue())) {
			Node node = e1(lexer.nextToken());

			if (node instanceof BinaryOperatorNode) {
				BinaryOperatorNode help = (BinaryOperatorNode) node;
				if ("xor".equals(help.getName())) {
					nodes.addAll(help.getChildren());
				} else {
					nodes.add(node);
				}
			} else {
				nodes.add(node);
			}
		}

		if (nodes.size() > 1) {
			return new BinaryOperatorNode("xor", nodes, (a, b) -> a ^ b);
		}

		return nodes.get(0);
	}

	private Node e3(Token current) {
		List<Node> nodes = new ArrayList<>();
		nodes.add(e4(current));

		while ("and".equals(lexer.getCurrentToken().getTokenValue())) {
			Node node = e1(lexer.nextToken());

			if (node instanceof BinaryOperatorNode) {
				BinaryOperatorNode help = (BinaryOperatorNode) node;
				if ("and".equals(help.getName())) {
					nodes.addAll(((BinaryOperatorNode) node).getChildren());
				} else {
					nodes.add(node);
				}
			} else {
				nodes.add(node);
			}
		}

		if (nodes.size() > 1) {
			return new BinaryOperatorNode("and", nodes, (a, b) -> a && b);
		}

		return nodes.get(0);

	}

	private Node e4(Token current) {
		Node node;

		if ("not".equals(current.getTokenValue())) {
			node = new UnaryOperatorNode("not", e4(lexer.nextToken()), a -> !a);
		} else {
			node = e5(current);
		}

		return node;
	}

	private Node e5(Token current) {
		Node node;
		String currentTokenValue = current.getTokenValue().toString();

		if (isTokenOfType(TokenType.VARIABLE)) {
			node = new VariableNode(currentTokenValue);
		} else if (isTokenOfType(TokenType.CONSTANT)) {
			node = new ConstantNode(Boolean.parseBoolean(currentTokenValue));
		} else if (isTokenOfType(TokenType.OPEN_BRACKET)) {
			node = e1(lexer.nextToken());
			if (!isTokenOfType(TokenType.CLOSED_BRACKET)) {
				throw new ParserException("Expected ')' but found " + lexer.getCurrentToken() + ".");
			}
		} else {
			throw new ParserException("Unexpected token found: {" + lexer.getCurrentToken() + "}.");
		}

		lexer.nextToken();
		return node;
	}

	public static void main(String[] args) {
		Parser parser = new Parser("(d or b) xor not (a or c)");
		Node list = parser.getExpression();

		System.out.println(list.toString());
	}
}
