package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * <code>INodeVisitor</code> is interface which encapsulates visitor from
 * Visitor pattern.
 *
 * @author Ivan Rezic
 */
public interface INodeVisitor {

	/**
	 * Visit text node.
	 *
	 * @param node
	 *            {@linkplain TextNode}
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visit for loop node.
	 *
	 * @param node
	 *            {@linkplain ForLoopNode}
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visit echo node.
	 *
	 * @param node
	 *            {@linkplain EchoNode}
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visit document node.
	 *
	 * @param node
	 *            {@linkplain DocumentNode}
	 */
	public void visitDocumentNode(DocumentNode node);

}
