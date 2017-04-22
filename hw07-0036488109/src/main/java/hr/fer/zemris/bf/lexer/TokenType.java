package hr.fer.zemris.bf.lexer;

/**
 * * The Enumeration which represents token types.
 *
 * @author Ivan Rezic
 */
public enum TokenType {

	/** Represents end of file. */
	EOF,
	/** Represents expression variables. */
	VARIABLE,
	/** Represents expression constants. */
	CONSTANT,
	/** Represents expression operators. */
	OPERATOR,
	/** Represents open bracket. */
	OPEN_BRACKET,
	/** Represents closed bracket. */
	CLOSED_BRACKET
}
