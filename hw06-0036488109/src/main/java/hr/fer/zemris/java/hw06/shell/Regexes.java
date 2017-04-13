package hr.fer.zemris.java.hw06.shell;

/**
 * <code>Regexes</code> is utility class which defines how each argument should
 * look like. Each constant used in this class is self explanatory. 
 *
 * @author Ivan Rezic
 */
public class Regexes {

	/** Constant TWO_ARGS_FIRST_QUOTED. */
	public static final String TWO_ARGS_FIRST_QUOTED = "\".+\"\\s[^\"\\s]+";

	/** Constant TWO_ARGS_NO_QUOTES. */
	public static final String TWO_ARGS_NO_QUOTES = "[^\"\\s]+\\s[^\"\\s]+";

	/** Constant ONE_ARG_NO_QUOTES. */
	public static final String ONE_ARG_NO_QUOTES = "[^\\\"\\s]+";

	/** Constant ONE_ARG_QUOTED. */
	public static final String ONE_ARG_QUOTED = "\".+\"";

	/** Constant TWO_ARGS_SECOND_QUOTED. */
	public static final String TWO_ARGS_SECOND_QUOTED = "[^\"]+\\s\".+\"";

	/** Constant TWO_ARGS_BOTH_QUOTED. */
	public static final String TWO_ARGS_BOTH_QUOTED = "\".+\"\\s\".+\"";

}
