package hr.fer.zemris.java.hw03.prob1;

/**
 * The Enum LexerState represents states in which lexer could be. Valid states
 * are: {@link #BASIC}, {@link #EXTENDED} .
 * 
 * @author Ivan
 */
public enum LexerState {

	/** State in which lexer classifies tokens by its <code>TokeyType</code>. */
	BASIC,
	/** State in which lexer digests tokens as words. */
	EXTENDED
}
