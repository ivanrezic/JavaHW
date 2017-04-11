package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		boolean isQuoted = arguments.matches("\".+\"");

		if (arguments.contains(" ") && !isQuoted) {
			env.writeln("\"mkdir\" command takes single argument and it must be a directory");
			return ShellStatus.CONTINUE;
		} else if (isQuoted) {
			arguments = arguments.substring(1, arguments.length()-1);
		}
		
		createDirectory(env, arguments);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";

	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("\tShort for \"make directory\", mkdir is used to create directories on a file system.");
		list.add("\tIf the specified DIRECTORY does not already exist, mkdir creates it.");
		list.add("\tIf the specified DIRECTORY does exist, but wanted subfolders do not, mkdir creates it.");
		list.add("\tIf the specified DIRECTORY contains directories within, they are automatically created.");
		list.add("\tNOTE: if path contains directory with spacing in their name, "
				+ "than argument should be enclosed with quotes.");
		return Collections.unmodifiableList(list);
	}

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
