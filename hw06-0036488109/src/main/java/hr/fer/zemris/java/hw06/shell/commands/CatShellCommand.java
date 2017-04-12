package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.matches(Regexes.ONE_ARG_QUOTED)) {
			String argument = arguments.substring(1, arguments.length() - 1);
			writeFile(argument, Charset.defaultCharset().displayName(), env);
		} else if (arguments.matches(Regexes.ONE_ARG_NO_QUOTES)) {
			writeFile(arguments, Charset.defaultCharset().displayName(), env);
		} else if (arguments.matches(Regexes.TWO_ARGS_NO_QUOTES)) {
			String[] parts = arguments.split(" ");
			writeFile(parts[0], parts[1], env);
		} else if (arguments.matches(Regexes.TWO_ARGS_FIRST_QUOTED)) {
			String[] parts = arguments.split("(?<=\") ");
			String firstPart = parts[0].substring(1,parts[0].length()-1);
			writeFile(firstPart, parts[1], env);
		} else {
			env.writeln("Wrong \"cat\" arguments, please check \"help cat\" for usage information.");
		}

		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "cat";

	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("\tCommand takes one or two arguments.");
		list.add("\tThe first argument is path to some file and is mandatory.");
		list.add("\tThe second argument is charset name that should be used to interpret chars from bytes.");
		list.add("\tThe second argument is charset name that should be used to interpret chars from bytes.");
		list.add("\tIf not provided, a default platform charset will be used");
		list.add("\tThis command opens given file and writes its content to console");

		return Collections.unmodifiableList(list);
	}

	private void writeFile(String arguments, String charset, Environment env) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(arguments), Charset.forName(charset)))) {

			String line = reader.readLine();
			while (line != null) {
				env.writeln(line);
				line = reader.readLine();
			}

		} catch (UnsupportedCharsetException e) {
			env.writeln("That charset doesnt exist, for list of available charsets please type \"charsets\".");
		} catch (IOException e) {
			env.writeln("Invalid path to the file.");
		}
	}
}
