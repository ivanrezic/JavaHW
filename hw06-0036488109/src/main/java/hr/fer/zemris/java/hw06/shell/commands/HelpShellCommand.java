package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.equals("")) {
			for (String key : env.commands().keySet()) {
				env.writeln("\t" + key);
			}
		} else if (!arguments.matches(Regexes.ONE_ARG_NO_QUOTES)) {
			if (!env.commands().containsKey(arguments)) {
				env.writeln("If argument is provided,it must be a valid command.");
				return ShellStatus.CONTINUE;
			}
			env.writeln(arguments);
			env.commands().get(arguments).getCommandDescription().forEach(e -> env.writeln(e));
		} else {
			env.writeln("After \"help\" there should one or no arguments.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";

	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("\tWrites command names and descriptions.");
		list.add("\tIf command is invoked with no arguments, all command names will be listed.");
		list.add(
				"\tIf command is invoked with one argument specifying certain command, it's description will be displayed.");

		return Collections.unmodifiableList(list);

	}

}
