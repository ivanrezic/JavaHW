package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Enumeration which represents token types. There are ten token types:
 * {@link #TEXT}, {@link #TAGSTART}, {@link #TAGEND}, {@link #FUNCTION},
 * {@link #VARIABLE}, {@link #OPERATOR}, {@link #STRING}, {@link #NUMBER},
 * {@link #EOF},{@link #TAGNAME};
 * 
 * @author Ivan
 */
public enum TokenType {

	/** The text. */
	TEXT,
	/** The tagstart. */
	TAGSTART,
	/** The tagend. */
	TAGEND,
	/** The function. */
	FUNCTION,
	/** The variable. */
	VARIABLE,
	/** The operator. */
	OPERATOR,
	/** The string. */
	STRING,
	/** The number. */
	NUMBER,
	/** The eof. */
	EOF,
	/** The tagname. */
	TAGNAME
}
