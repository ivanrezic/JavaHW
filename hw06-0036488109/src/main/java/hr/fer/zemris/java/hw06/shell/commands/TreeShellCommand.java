package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.contains(" ")) {
			env.writeln("Tree command takes just one argument (path to directory).");
		} else {
			listTree(arguments, env);
		}
		return null;

	}

	@Override
	public String getCommandName() {
		return null;

	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<>(Arrays.asList("temp"));

	}

	private void listTree(String arguments, Environment env) {
		Path directory = Paths.get(arguments);
		if (!Files.isDirectory(directory)) {
			env.writeln("Argument provided must be a directory.");
			return;
		}

		try {
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
				// ovo srediti
			});
		} catch (IOException e) {
			env.writeln("Can not read from directory.");
		}

	}
}
