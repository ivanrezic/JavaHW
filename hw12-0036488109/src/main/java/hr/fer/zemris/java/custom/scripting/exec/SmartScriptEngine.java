package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * <code>SmartScriptEngine</code> is a class whose job is to execute document
 * whose parsed three it obtains.
 *
 * @author Ivan Rezic
 */
public class SmartScriptEngine {

	/** {@linkplain DocumentNode} which will be executed. */
	private DocumentNode documentNode;

	/** {@linkplain RequestContext} */
	private RequestContext requestContext;

	/** Stack used for storing values from parsed script. */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Visitor which provides this class functionality.
	 */
	private INodeVisitor visitor = new EngineVisitor();

	/**
	 * Constructor which instantiates new smart script engine.
	 *
	 * @param documentNode
	 *            the document node
	 * @param requestContext
	 *            the request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		Objects.requireNonNull(documentNode, "Document node must not be null");
		Objects.requireNonNull(requestContext, "Request context must not be null");

		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes parsed script.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * <code>EngineVisitor</code> is visitor from Visitor pattern. It provides
	 * external functionalities to implementing classes.
	 *
	 * @author Ivan Rezic
	 */
	private class EngineVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException ignorable) {
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String name = node.getVariable().getName();
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			Object end = new ValueWrapper(node.getEndExpression().asText()).getValue();
			Object step = new ValueWrapper(node.getStepExpression().asText()).getValue();
			multistack.push(name, start);

			int count = node.numberOfChildren();
			while (start.numCompare(end) != 1) {
				for (int j = 0; j < count; j++) {
					node.getChild(j).accept(this);
				}
				multistack.peek(name).add(step);
			}

			multistack.pop(name);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();
			for (Element element : node.getElements()) {
				if (element instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) element).getValue());
				} else if (element instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) element).getValue());
				} else if (element instanceof ElementString) {
					stack.push(((ElementString) element).getValue());
				} else if (element instanceof ElementVariable) {
					String name = ((ElementVariable) element).getName();
					stack.push(multistack.peek(name).getValue());
				} else if (element instanceof ElementOperator) {
					calculate(stack, (ElementOperator) element);
				} else if (element instanceof ElementFunction) {
					execute(stack, (ElementFunction) element);
				}
			}

			for (Object object : stack) {
				try {
					requestContext.write(object.toString());
				} catch (IOException ignorable) {
				}
			}
		}

		/**
		 * Helper method which calculates wanted arithmetic operation within
		 * last two values from stack.
		 *
		 * @param stack
		 *            Stack which stores values loaded from script.
		 * @param element
		 *            Element class which encapsulates wanted operation.
		 */
		private void calculate(Stack<Object> stack, ElementOperator element) {
			ValueWrapper first = new ValueWrapper(stack.pop());
			ValueWrapper second = new ValueWrapper(stack.pop());

			switch (element.getSymbol()) {
			case "+":
				first.add(second.getValue());
				break;
			case "-":
				first.subtract(second.getValue());
				break;
			case "*":
				first.multiply(second.getValue());
				break;
			case "/":
				first.divide(second.getValue());
				break;

			default:
				throw new UnsupportedOperationException("Unsupported operator given.");
			}

			stack.push(first.getValue());
		}

		/**
		 * Executes function invoked by script.
		 *
		 * @param stack
		 *            Stack which stores values loaded from script.
		 * @param element
		 *            Element class which encapsulates wanted function.
		 */
		private void execute(Stack<Object> stack, ElementFunction element) {
			Object first = stack.pop();

			switch (element.getName()) {
			case "sin":
				Double doubleFirst = Double.valueOf(first.toString());
				stack.push(Math.sin(Math.toRadians(doubleFirst)));
				break;
			case "decfmt":
				DecimalFormat r = new DecimalFormat(first.toString());
				stack.push(r.format(stack.pop()));
				break;
			case "dup":
				stack.push(first);
				stack.push(first);
				break;
			case "swap":
				Object second = stack.pop();
				stack.push(first);
				stack.push(second);
				break;
			case "setMimeType":
				requestContext.setMimeType(first.toString());
				break;
			case "paramGet":
				String value = requestContext.getParameter(stack.pop().toString());
				stack.push(value == null ? first : value);
				break;
			case "pparamGet":
				String value2 = requestContext.getPersistentParameter(stack.pop().toString());
				stack.push(value2 == null ? first : value2);
				break;
			case "pparamSet":
				requestContext.setPersistentParameter(first.toString(), stack.pop().toString());
				break;
			case "pparamDel":
				requestContext.getPersistentParameters().remove(first.toString());
				break;
			case "tparamGet":
				String value4 = requestContext.getTemporaryParameter(stack.pop().toString());
				stack.push(value4 == null ? first : value4);
				break;
			case "tparamSet":
				requestContext.setTemporaryParameter(first.toString(), stack.pop().toString());
				break;
			case "tparamDel":
				requestContext.getTemporaryParameters().remove(first.toString());
				break;

			default:
				throw new UnsupportedOperationException("Unsupported funtion given.");
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, children = node.numberOfChildren(); i < children; i++) {
				Node child = node.getChild(i);
				child.accept(this);
			}
		}

	}
}