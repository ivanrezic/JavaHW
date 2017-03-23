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

/**
 * SmartScriptParser represents object which processes text through syntax
 * analysis. After processing text is divided in syntax groups which are later
 * encapsulated in nodes used for construction of syntax tree. There are two
 * main text groups, ones inside {$ ... $} which we call tags, and ones outside
 * tags - text. After that we have 3 tags subgroups which we call: for, echo and
 * end.
 * 
 * @author Ivan
 */
public class SmartScriptParser {

	/**
	 * The document node is root node which we later use for tree construction.
	 */
	private DocumentNode documentNode;

	/** The lexer is reference to object {@link Lexer}. */
	private Lexer lexer;

	/**
	 * Constructor that instantiates a new smart script parser.
	 *
	 * @param documentBody
	 *            text
	 */
	public SmartScriptParser(String documentBody) {
		if (documentBody == null) {
			throw new SmartScriptParserException("Document body can't be null.");
		}

		lexer = new Lexer(documentBody);
		parse(lexer);
	}

	/**
	 * Method which delegates its work to {@link Lexer} for lexic analysis. It
	 * digest tokens and creates nodes using {@link ObjectStack} out of it.
	 *
	 * @param lexer
	 *            instance of {@link Lexer}
	 * 
	 * @throws SmartScriptParserException
	 *             if some error occurs while parsing the tokens
	 */
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

	/**
	 * Represents end tag token.
	 *
	 * @param stack
	 *            the stack
	 * @throws SmartScriptParserException
	 *             if stack is empty after removing END tag
	 */
	private void endTag(ObjectStack stack) {

		lexer.nextToken();
		stack.pop();
		if (stack.isEmpty()) {
			throw new SmartScriptParserException("After removing END tag stack shouldnt be empty.");
		}
	}

	/**
	 * Represents tag type "=". Which we encapsulate in EchoNode.
	 *
	 * @param stack
	 *            instance of <code>ObjectStack</code>
	 */
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

	/**
	 * Returns token that matches input argument.
	 *
	 * @param temporary
	 *            token which we are matching against
	 * @return the correct type of token
	 * @throws SmartScriptParserException
	 *             If EchoNode argument is invalid
	 */
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

	/**
	 * For tag is method which processes tokens that should be encapsulated in
	 * ForLoopNode. ForLoopNode consists of 3 or 4 elements. First one is type
	 * ElementVariable others can be string,variable, or number.
	 *
	 * @param stack
	 *            instance of ObjectStack
	 * @throws SmartScriptParserException
	 *             if there are more than 4 elements
	 */
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

	/**
	 * Helper method which checks if token type matches string,variable or number.
	 *
	 * @param token
	 *            the token we are matching against
	 * @return true, if successful false otherwise
	 * @throws SmartScriptParserException if token is of type OPERATOR or FUNCTION
	 */
	private boolean elementCondition(Token token) {
		if (token.getType() == TokenType.OPERATOR || token.getType() == TokenType.FUNCTION) {
			throw new SmartScriptParserException(token.getValue().toString() + "is not legal type.");
		}
		return true;
	}

	/**
	 * Returns text representation of <code>DocumentNode</code>
	 *
	 * @return the document node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
