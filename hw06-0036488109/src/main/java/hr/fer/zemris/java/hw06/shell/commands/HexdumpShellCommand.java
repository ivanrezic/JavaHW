package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * <code>HexdumpShellCommand</code> expects a single argument: file name.
 * Afterwards it produes hex dump and writes it to the console. On the left side
 * of output there is size written, right side represents chars read from file
 * and middle part represents hexadecimal notation of those chars. If text
 * contains chars whose byte value is less than 32 or greater than 127, they are
 * replaced with '.' .
 *
 * @author Ivan Rezic
 */
public class HexdumpShellCommand implements ShellCommand {

	/** HEXDUMP_BUFFER_SIZE used for buffered reader size. */
	private static final int HEXDUMP_BUFFER_SIZE = 16;

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
			env.writeln("\"hexdump\" command expects one argument");
			return ShellStatus.CONTINUE;
		}

		writeHexdump(arguments, env);
		return ShellStatus.CONTINUE;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "hexdump";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("\tCommand expects one argument, file name.");
		list.add("\tAfterwards command produces hexdump of given file.");
		list.add("\tOn the left side of hexdump there is size written in hexadecimal base.");
		list.add("\tOn the right side of hexdump there is original file part written.");
		list.add("\tEach line represents 16 chars from original file in hex base.");
		list.add("\tNOTE: if path contains file with spacing in their name, "
				+ "than argument should be enclosed with quotes.");

		return Collections.unmodifiableList(list);
	}

	/**
	 * Helper method which produces hexdump from given file.
	 *
	 * @param arguments
	 *            given file name
	 * @param env
	 *            the environment used
	 */
	private void writeHexdump(String arguments, Environment env) {

		try (FileInputStream reader = new FileInputStream(arguments)) {
			byte[] buffer = new byte[HEXDUMP_BUFFER_SIZE];
			int size = 0;
			int read = 0;

			while ((read = reader.read(buffer)) != -1) {
				printInHexFormat(buffer, env, size, read);
				size += 16;
			}
		} catch (FileNotFoundException e) {
			env.writeln("File provided is not valid.");
		} catch (IOException e) {
			env.writeln("File couldnt be reached.");
		}
	}

	/**
	 * Helper method which prints formated hexdump to console.
	 *
	 * @param buffer
	 *            byte array which contains produces bytes
	 * @param env
	 *            the environment used
	 * @param size
	 *            the size read so far
	 * @param read
	 *            the size read in last iteration
	 */
	private void printInHexFormat(byte[] buffer, Environment env, int size, int read) {
		StringBuilder line = new StringBuilder();

		line.append(String.format("%08X", size)).append(": ");
		for (int i = 0; i < read; i++) {
			line.append(String.format("%02X", buffer[i]));
			line.append(" ");

			if (buffer[i] < 32 || buffer[i] > 127) {
				buffer[i] = '.';
			}
		}

		if (read < buffer.length) {
			line.append(addEmptyOnes(buffer.length - read));
		}
		line.replace(33, 34, "|").append("| ");
		line.append(new String(buffer), 0, read);

		env.writeln(line.toString());
	}

	/**
	 * Helper method which files last line with blanks if needed.
	 *
	 * @param length
	 *            number of blanks
	 * @return blanks concatenated
	 */
	private String addEmptyOnes(int length) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i++) {
			builder.append("   ");
		}

		return builder.toString();

	}
}
