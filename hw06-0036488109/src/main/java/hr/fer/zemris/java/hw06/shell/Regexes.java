package hr.fer.zemris.java.hw06.shell;

public class Regexes {

	public static final String TWO_ARGS_FIRST_QUOTED = "\".+\"\\s[^\"\\s]+";
	public static final String TWO_ARGS_NO_QUOTES = "[^\"\\s]+\\s[^\"\\s]+";
	public static final String ONE_ARG_NO_QUOTES = "[^\\\"\\s]+";
	public static final String ONE_ARG_QUOTED = "\".+\"";
	public static final String TWO_ARGS_SECOND_QUOTED = "[^\"]+\\s\".+\"";
	public static final String TWO_ARGS_BOTH_QUOTED = "\".+\"\\s\".+\"";
	
}
