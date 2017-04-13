package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CopyShellCommand implements ShellCommand {

	private static final int BUFFER_SIZE = 4096;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts;
		String firstPart = null;
		String secondPart = null;
		if (arguments.matches(Regexes.TWO_ARGS_NO_QUOTES)) {
			parts = arguments.split(" ");
			firstPart = parts[0];
			secondPart = parts[1];
		} else if (arguments.matches(Regexes.TWO_ARGS_FIRST_QUOTED)) {
			parts = arguments.split("(?<=\") ");
			firstPart = parts[0].substring(1, parts[0].length() - 1);
			secondPart = parts[1];
		} else if (arguments.matches(Regexes.TWO_ARGS_SECOND_QUOTED)) {
			parts = arguments.split(" (?=\")");
			secondPart = parts[1].substring(1, parts[1].length() - 1);
			firstPart = parts[0];
		} else if (arguments.matches(Regexes.TWO_ARGS_BOTH_QUOTED)) {
			parts = arguments.split("(?<=\") (?=\")");
			firstPart = parts[0].substring(1, parts[0].length() - 1);
			secondPart = parts[1].substring(1, parts[1].length() - 1);
		} else {
			env.writeln("Wrong \"copy\" arguments, please check \"help copy\" for usage information.");
			return ShellStatus.CONTINUE;
		}

		copyFile(firstPart, secondPart, env);
		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "copy";

	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<>(Arrays.asList("temp"));

	}

	private void copyFile(String first, String second, Environment env) {
		Path copy = Paths.get(first);
		Path paste = Paths.get(second);

		if (!Files.isReadable(copy)) {
			env.writeln("Can not copy from " + first + "file or file doesnt exist.");
			return;
		}

		if (Files.exists(paste)) {
			if (Files.isRegularFile(paste) && overwrite(paste, env)) {
				pasteFile(env, copy, paste, false);
			} else if (Files.isDirectory(paste)) {
				File inDirectory = new File(new File(second), first);
				pasteFile(env, copy, inDirectory.toPath(), true);
			} else {
				return;
			}
		} else {
			pasteFile(env, copy, paste, true);
		}

	}

	private void pasteFile(Environment env, Path copy, Path paste, boolean help) {
		try (FileOutputStream writer = new FileOutputStream(paste.toFile(), help);
				FileInputStream reader = new FileInputStream(copy.toFile())) {

			byte[] buffer = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, read);
			}

			env.writeln("DONE.");
		} catch (IOException e) {
			env.writeln("Ups.. something go wrong with input/output.");
		}
	}

	private boolean overwrite(Path paste, Environment env) {
		StringBuilder builder = new StringBuilder();

		builder.append("Do you want to overwrite ").append(paste.getFileName().toString());
		builder.append(" [Y|N]");
		env.writeln(builder.toString());

		while (true) {
			String answer = env.readLine();
			if (answer.equals("Y")) {
				return true;
			} else if (answer.equals("N")) {
				break;
			} else {
				env.writeln("Please choose Y - yes or N - no.");
			}
		}

		return false;

	}

}
