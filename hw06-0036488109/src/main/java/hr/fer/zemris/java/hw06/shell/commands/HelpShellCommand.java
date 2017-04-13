package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * <code>HelpShellCommand</code> takes one or no arguments. Without arguments it
 * writes all commands usable in shell. If one argument passed it must be name
 * of command. In that case console writes selected command description.
 *
 * @author Ivan Rezic
 */
public class HelpShellCommand implements ShellCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.
	 * java.hw06.shell.Environment, java.lang.String)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "help";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("\tWrites command names and descriptions.");
		list.add("\tIf command is invoked with no arguments, all command names will be listed.");
		list.add(
				"\tIf command is invoked with one argument specifying certain command, its description will be displayed.");

		return Collections.unmodifiableList(list);

	}

}
