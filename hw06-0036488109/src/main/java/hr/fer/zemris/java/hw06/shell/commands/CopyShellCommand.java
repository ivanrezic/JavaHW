package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
 * <code>CopyShellCommand</code> expects two arguments: source file name and
 * destination file name. If destination file exists, it prompts message which
 * asks if file should be overwritten or not. If second file name represents
 * directory then original file is coppied within directory.
 *
 * @author Ivan Rezic
 */
public class CopyShellCommand implements ShellCommand {

	/** Optimal BUFFER_SIZE reading and writing files. */
	private static final int BUFFER_SIZE = 4096;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.
	 * java.hw06.shell.Environment, java.lang.String)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "copy";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("\tCommand expects two arguments.");
		list.add("\tand copies content of specified file to the new loaction or existing file.");
		list.add("\tIf second file exists it user must decide if the file will be overwritten or not.");
		list.add("\tOtherways if second file name represents direcotry then source file will be written within.");
		list.add("\tNOTE: if path contains file with spacing in their name, "
				+ "than argument should be enclosed with quotes.");

		return Collections.unmodifiableList(list);
	}

	/**
	 * Helper method which delegates file reading/writing to {@link #pasteFile},
	 * depending if file path is directory or file and wather it should be
	 * overwritten or not.
	 *
	 * @param first
	 *            source file path
	 * @param second
	 *            destination file/directory path
	 * @param env
	 *            the environment used
	 */
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

	/**
	 * Helper method which uses buffer for reading/writing.
	 *
	 * @param env
	 *            the environment used
	 * @param copy
	 *            source file path
	 * @param paste
	 *            destination file path
	 * @param help
	 *            True if appending to destination, false if overwriting
	 */
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

	/**
	 * Helper method which interacts with user through console and checks
	 * whether file should be overwritten or not.
	 *
	 * @param paste
	 *            destination file path
	 * @param env
	 *            the environment used
	 * @return true, if it is successful, false otherwise
	 */
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
