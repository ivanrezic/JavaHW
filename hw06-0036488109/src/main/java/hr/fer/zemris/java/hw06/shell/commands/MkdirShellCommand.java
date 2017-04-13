package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * <code>MkdirShellCommand</code> takes a single argument and creates new
 * directory with that name. 
 *
 * @author Ivan Rezic
 */
public class MkdirShellCommand implements ShellCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.
	 * java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.matches(Regexes.ONE_ARG_QUOTED)) {
			arguments = arguments.substring(1, arguments.length() - 1);
		} else if (!arguments.matches(Regexes.ONE_ARG_NO_QUOTES)) {
			env.writeln("\"mkdir\" command takes single argument and it must be a directory");
			return ShellStatus.CONTINUE;
		}

		createDirectory(env, arguments);
		return ShellStatus.CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "mkdir";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("\tShort for \"make directory\", mkdir is used to create directories on a file system.");
		list.add("\tIf the specified DIRECTORY does not already exist, mkdir creates it.");
		list.add("\tIf the specified DIRECTORY does exist, but wanted subfolders do not, mkdir adds new subfoldersF.");
		list.add("\tIf the specified DIRECTORY contains directories within, they are automatically created.");
		list.add("\tNOTE: if path contains directory with spacing in their name, "
				+ "than argument should be enclosed with quotes.");
		return Collections.unmodifiableList(list);
	}

	/**
	 * Helper method which creates directory with given name.
	 *
	 * @param env
	 *            the environment
	 * @param arguments
	 *            name of newly made directory
	 */
	private void createDirectory(Environment env, String arguments) {
		Path directory = Paths.get(arguments);
		try {
			Files.createDirectories(directory);
		} catch (IOException e) {
			env.writeln("Directory couldnt be written.");
		}

		env.writeln(arguments + " directory successfuly created!");
	}

}
