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
		Node node = e2(current);
		List<Node> nodes = new ArrayList<>();
		nodes.add(node);

		if ("or".equals(lexer.getCurrentToken().getTokenValue())) {
			nodes.add(e1(lexer.nextToken()));
			node = new BinaryOperatorNode("or", nodes, (a, b) -> a || b);
		}

		return node;
	}

	private Node e2(Token current) {
		Node node = e3(current);
		List<Node> nodes = new ArrayList<>();
		nodes.add(node);

		if ("xor".equals(lexer.getCurrentToken().getTokenValue())) {
			nodes.add(e2(lexer.nextToken()));
			node = new BinaryOperatorNode("xor", nodes, (a, b) -> a ^ b);
		}

		return node;
	}

	private Node e3(Token current) {
		Node node = e4(current);
		List<Node> nodes = new ArrayList<>();
		nodes.add(node);

		if ("and".equals(lexer.getCurrentToken().getTokenValue())) {
			nodes.add(e3(lexer.nextToken()));
			node = new BinaryOperatorNode("and", nodes, (a, b) -> a && b);
		}

		return node;

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
		}else if (isTokenOfType(TokenType.OPEN_BRACKET)) {
			node = e1(current);
		}else {
			throw new ParserException("Given expression is not valid.");
		}
		
		lexer.nextToken();
		return node;
	}
	
	public static void main(String[] args) {
		Parser parser = new Parser("a or b or c or d");
		Node list = parser.getExpression();
		
		System.out.println(list.toString());
	}
}
