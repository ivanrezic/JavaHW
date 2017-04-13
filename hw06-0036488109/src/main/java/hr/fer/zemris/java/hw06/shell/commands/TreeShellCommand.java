package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class TreeShellCommand implements ShellCommand {

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

	@Override
	public String getCommandName() {
		return "tree";

	}

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

	private String indent(int indentation) {
		StringBuilder space = new StringBuilder();

		for (int i = 0; i < indentation; i++) {
			space.append(" ");
		}

		return space.toString();

	}
}
