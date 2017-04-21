package hr.fer.zemris.bf.model;

public interface Node {

	void accept(NodeVisitor visitor);
}
