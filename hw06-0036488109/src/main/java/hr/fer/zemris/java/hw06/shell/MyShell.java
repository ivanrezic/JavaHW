package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

public class MyShell implements Environment {
	private SortedMap<String, ShellCommand> commands = new TreeMap<>();
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
	private char promptSymbol = '>';
	private char moreLinesSymbol = '\\';
	private char multiLineSymbol = '|';

	{
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("help", new HelpShellCommand());
	}

	@Override
	public String readLine() throws ShellIOException {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			writer.write(text);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;

	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multiLineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;

	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;

	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreLinesSymbol = symbol;
	}

	public static void main(String[] args) {
		Environment env = new MyShell();

		System.out.printf("Welcome to MyShell v 1.0%n");

		while (true) {
			System.out.printf("%c ", env.getPromptSymbol());
			String line = env.readLine();

			while (line.charAt(line.length() - 1) == env.getMorelinesSymbol()) {
				System.out.printf("%c ", env.getMultilineSymbol());
				line = line.replace("\\", env.readLine());
			}

			try {

				ShellStatus status;
				if (line.contains(" ")) {
					String[] parts = line.split(" ", 2);
					status = env.commands().get(parts[0]).executeCommand(env, parts[1].trim());
				} else {
					status = env.commands().get(line).executeCommand(env, "");
				}
				if (status == ShellStatus.TERMINATE)
					break;

			} catch (NullPointerException e) {
				System.out.printf("Type \"help\" for valid commands.%n");
			}

		}

	}

}
