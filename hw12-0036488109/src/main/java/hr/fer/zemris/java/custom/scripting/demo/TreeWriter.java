package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class TreeWriter {
	
	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, children = node.numberOfChildren(); i < children; i++) {
				Node child = node.getChild(i);
				child.accept(this);
			}
		}
		
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Expectd single argument, file name.");
		}
		
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);

	}

}
