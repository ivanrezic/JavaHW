package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * <code>CharsetsShellCommand</code> is used for writing all available charsets
 * to console. This command takes no arguments.
 *
 * @author Ivan Rezic
 */
public class CharsetsShellCommand implements ShellCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.
	 * java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.equals("")) {
			env.writeln("Command \"charsets\" takes no arguments.");
			return ShellStatus.CONTINUE;
		}

		SortedMap<String, Charset> charset = Charset.availableCharsets();
		for (String key : charset.keySet()) {
			env.writeln(charset.get(key).toString());
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
		return "charsets";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("\tCommand which takes no arguments and displays all valid charsets.");

		return Collections.unmodifiableList(list);

	}

}
