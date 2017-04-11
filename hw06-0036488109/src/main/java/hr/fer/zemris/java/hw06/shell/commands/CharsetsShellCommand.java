package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.equals("")) {
			System.out.printf("Command \"charsets\" takes no arguments.%n");
			return ShellStatus.CONTINUE;
		}
		SortedMap<String, Charset> charset = Charset.availableCharsets();
		
		for (String key : charset.keySet()) {
			env.writeln(charset.get(key).toString());
		}
		
		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "charsets";

	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		
		list.add("\tCommand which displays all valid charsets.");
		
		return Collections.unmodifiableList(list);

	}

}
