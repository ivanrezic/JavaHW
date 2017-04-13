package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * <code>SymbolShellCommand</code> displays and modifies current console symbols
 * used based on number of arguments. If one argument passed then console
 * displays wanted symbol. If two arguments passed then wanted symbol is
 * replaced with new one.
 *
 * @author Ivan Rezic
 */
public class SymbolShellCommand implements ShellCommand {

	/** New symbol ALLOWED_SIZE. */
	private static final int ALLOWED_SIZE = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.
	 * java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.matches(Regexes.ONE_ARG_NO_QUOTES)) {
			writeSymbol(env, arguments);
		} else if (arguments.matches(Regexes.TWO_ARGS_NO_QUOTES)) {
			String[] parts = arguments.split(" ", 2);
			if (parts[1].length() != ALLOWED_SIZE) {
				env.writeln("New symbol must be of a size " + ALLOWED_SIZE + ".");
			} else {
				changeSymbol(parts[0], parts[1].charAt(0), env);
			}
		} else {
			env.writeln("\"symbol\" command takes one or two arguments, for more information check \"help symbol\"");
		}

		return ShellStatus.CONTINUE;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "symbol";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("\tPROMPT symbol is displayed at the beginning of each command.");
		list.add("\tMORELINES symbol informs shell that more lines of command are expected.");
		list.add("\tMULTILINE symbol is displayed for each line that is part of multi-line command ");
		list.add("\t(except for the first one) at the beginning, followed by a single whitespace.");

		return Collections.unmodifiableList(list);
	}

	/**
	 * Helper method which writes symbol to console based on passed argument.
	 *
	 * @param env
	 *            the environment
	 * @param arguments
	 *            "PROMPT" or "MORELINES" or "MULTILINE"
	 */
	private void writeSymbol(Environment env, String arguments) {
		switch (arguments) {
		case "PROMPT":
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			break;
		case "MORELINES":
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			break;
		case "MULTILINE":
			env.writeln("Symbol for PROMPT is '" + env.getMultilineSymbol() + "'");
			break;
		default:
			env.writeln("Invalid symbol type given.");
			break;
		}
	}

	/**
	 * Helper method which changes old symbol to a new one.
	 *
	 * @param symbol
	 *            "PROMPT" or "MORELINES" or "MULTILINE"
	 * @param charAt
	 *            new symbol
	 * @param env
	 *            the environment
	 */
	private void changeSymbol(String symbol, char charAt, Environment env) {
		switch (symbol) {
		case "PROMPT":
			env.writeln("Symbol for " + symbol + " changed from '" + env.getPromptSymbol() + "' to '" + charAt + "'");
			env.setPromptSymbol(charAt);
			break;
		case "MORELINES":
			env.writeln(
					"Symbol for " + symbol + " changed from '" + env.getMorelinesSymbol() + "' to '" + charAt + "'");
			env.setMorelinesSymbol(charAt);
			break;
		case "MULTILINE":
			env.writeln(
					"Symbol for " + symbol + " changed from '" + env.getMultilineSymbol() + "' to '" + charAt + "'");
			env.setMultilineSymbol(charAt);
			break;
		default:
			env.writeln("Invalid symbol type given.");
			break;

		}
	}

}
