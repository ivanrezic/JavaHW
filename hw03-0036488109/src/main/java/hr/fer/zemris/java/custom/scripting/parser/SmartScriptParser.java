package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParser {

	private DocumentNode documentNode;
	private Lexer lexer;

	public SmartScriptParser(String documentBody) {
		if (documentBody == null) {
			throw new SmartScriptParserException("Document body can't be null.");
		}

		lexer = new Lexer(documentBody);
		parse(lexer);
	}

	public void parse(Lexer lexer) {
		ObjectStack stack = new ObjectStack();
		documentNode = new DocumentNode();
		stack.push(documentNode);

		Token token = lexer.nextToken();
		while (token != null && token.getType() != TokenType.EOF) {
			if (token.getType() == TokenType.TEXT) {
				Node parent = (Node) stack.peek();
				parent.addChildNode(new TextNode(token.getValue().toString()));
			} else if (token.getType() == TokenType.TAGSTART) {
				token = lexer.nextToken();

				switch (token.getValue().toString().toLowerCase()) {
				case "for":
					forTag(stack);
					break;

				case "=":
					equalsTag(stack);
					break;

				case "end":
					endTag(stack);
					break;

				default:
					throw new SmartScriptParserException("Tag name invalid");
				}
			}

			token = lexer.nextToken();
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException(" Stack contains more END tags than opened non-empty tags");
		}
	}

	private void endTag(ObjectStack stack) {

		lexer.nextToken();
		stack.pop();
		if (stack.isEmpty()) {
			throw new SmartScriptParserException("After removing END tag stack shouldnt be empty.");
		}
	}

	private void equalsTag(ObjectStack stack) {
		ArrayIndexedCollection tokenElements = new ArrayIndexedCollection();
		Element[] elements;

		Token token = lexer.nextToken();
		while (token.getType() != TokenType.TAGEND) {
			tokenElements.add(token);
			token = lexer.nextToken();
		}

		elements = new Element[tokenElements.size()];
		for (int i = 0, n = tokenElements.size(); i < n; i++) {
			Token temporary = (Token) tokenElements.get(i);

			elements[i] = getCorrectType(temporary);
		}

		Node parent = (Node) stack.peek();
		parent.addChildNode(new EchoNode(elements));
	}

	private Element getCorrectType(Token temporary) {
		TokenType type = temporary.getType();
		String value = temporary.getValue().toString();

		switch (type) {
		case FUNCTION:
			return new ElementFunction(value);
		case OPERATOR:
			return new ElementOperator(value);
		case VARIABLE:
			return new ElementVariable(value);
		case NUMBER:
			if (value.contains(".")) {
				return new ElementConstantDouble(Double.parseDouble(value));
			} else {
				return new ElementConstantInteger(Integer.parseInt(value));
			}
		case STRING:
			return new ElementString(value);
		default:
			throw new SmartScriptParserException("Illegal EchoNode argument!");
		}

	}

	private void forTag(ObjectStack stack) {
		int counter = 1;
		Element startExpression = null, endExpression = null, stepExpression = null;
		ElementVariable variable = null;
		Token token = lexer.nextToken();

		while (token.getType() != TokenType.TAGEND) {
			switch (counter) {
			case 1:
				if (token.getType() != TokenType.VARIABLE) {
					throw new SmartScriptParserException(token.getValue().toString() + "is not variable name.");
				}
				variable = new ElementVariable(token.getValue().toString());
				counter++;
				break;
			case 2:
				if (elementCondition(token)) {
					startExpression = getCorrectType(token);
				}
				counter++;
				break;
			case 3:
				if (elementCondition(token)) {
					endExpression = getCorrectType(token);
				}
				counter++;
				break;
			case 4:
				if (elementCondition(token)) {
					stepExpression = getCorrectType(token);
				}
				counter++;
				break;
			default:
				throw new SmartScriptParserException("ForLoop should have 3 or 4 elements");
			}
			token = lexer.nextToken();
		}

		Node parent = (Node) stack.peek();
		Node foorLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		parent.addChildNode(foorLoopNode);
		stack.push(foorLoopNode);
	}

	private boolean elementCondition(Token token) {
		if (token.getType() == TokenType.OPERATOR || token.getType() == TokenType.FUNCTION) {
			throw new SmartScriptParserException(token.getValue().toString() + "is not legal type.");
		}
		return true;
	}

	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
