package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.equals("")) {
			for (String key : env.commands().keySet()) {
				env.writeln("\t" + key);
			}
		} else if (!arguments.contains(" ") && env.commands().containsKey(arguments)) {
			env.writeln(arguments);
			env.commands().get(arguments).getCommandDescription().forEach(e -> env.writeln(e));
		} else {
			env.writeln("After \"help\" there should be one valid command.");
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
