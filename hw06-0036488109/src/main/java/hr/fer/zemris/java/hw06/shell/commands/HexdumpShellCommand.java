package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HexdumpShellCommand implements ShellCommand {

	private static final int HEXDUMP_BUFFER_SIZE = 16;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.matches(Regexes.ONE_ARG_QUOTED)) {
			arguments = arguments.substring(1, arguments.length() - 1);
		} else if (!arguments.matches(Regexes.ONE_ARG_NO_QUOTES)) {
			env.writeln("\"hexdump\" command expects one argument");
			return ShellStatus.CONTINUE;
		}

		writeHexdump(arguments, env, 0);
		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "hexdump";

	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<>(Arrays.asList("temp"));

	}

	private void writeHexdump(String arguments, Environment env, int i) {

		try (FileInputStream reader = new FileInputStream(arguments)) {
			byte[] buffer = new byte[HEXDUMP_BUFFER_SIZE];
			int size = 0;
			int read = 0;

			while ((read = reader.read(buffer)) != -1) {
				printInHexFormat(buffer, env, size, read);
				size = size + 16;
			}
		} catch (FileNotFoundException e) {
			env.writeln("File provided is not valid.");
		} catch (IOException e) {
			env.writeln("File couldnt be reached.");
		}
	}

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
			line.append(addEmptyOnes(read, buffer.length));
		}
		line.replace(33, 34, "|").append("| ");
		line.append(new String(buffer), 0, read);

		env.writeln(line.toString());
	}

	private String addEmptyOnes(int read, int length) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length - read; i++) {
			builder.append("   ");
		}

		return builder.toString();

	}
}
