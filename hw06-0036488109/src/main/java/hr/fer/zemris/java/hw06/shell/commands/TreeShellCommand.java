package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * <code>TreeShellCommand</code> expects a single argument: directory name and
 * prints its content recursively as tree structure. Produced tree is depth
 * first. If called without arguments it produces tree out of curent directory.
 *
 * @author Ivan Rezic
 */
public class TreeShellCommand implements ShellCommand {

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
			env.writeln("Tree command takes just one argument (path to directory).");
			return ShellStatus.CONTINUE;
		}

		File directory = new File(arguments);
		if (directory.isDirectory()) {
			listTree(directory, env, 0);
		} else {
			env.writeln("Given argument is not a directory.");
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
		return "tree";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("\tTree is recursive directory listing program that produces a depth "
				+ "indented listing of files and outputs it to the MyShell.");
		list.add("\tWith no arguments, tree lists the files in the current directory.");
		list.add("\tWhen directory arguments are given, tree lists all the files and/or "
				+ "directories found in the given directories each in turn.");
		list.add("\tNOTE: if path contains directory with spacing in their name, "
				+ "than argument should be enclosed with quotes.");

		return Collections.unmodifiableList(list);

	}

	/**
	 * Helper method which writes tree with all listed files/directories.
	 *
	 * @param directory
	 *            the directory whose content will be listed
	 * @param env
	 *            the environment used
	 * @param indentation
	 *            the indentation which will be added with each level
	 */
	private void listTree(File directory, Environment env, int indentation) {
		File[] files = directory.listFiles();
		String indent = indent(indentation);
		env.writeln(indent + directory.getName());

		for (File file : files) {
			if (file.isFile()) {
				env.writeln(indent + file.getName());
			} else if (file.isDirectory()) {
				listTree(file, env, indentation + 2);
			}
		}

	}

	/**
	 * Helper method which adds indentation to each new level.
	 *
	 * @param indentation
	 *            the indentation which will be added in front of listing
	 * @return contatenated blanks which represent indentation
	 */
	private String indent(int indentation) {
		StringBuilder space = new StringBuilder();

		for (int i = 0; i < indentation; i++) {
			space.append(" ");
		}

		return space.toString();

	}
}
