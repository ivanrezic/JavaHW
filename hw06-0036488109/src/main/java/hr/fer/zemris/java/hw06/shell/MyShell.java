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

/**
 * <code>MyShell</code> represents program that acts as a simple shell that can
 * execute following built-in commands: cat, charsets, copy, exit, help,
 * hexdump, ls, mkdir, symbol, tree.
 * 
 * @author Ivan Rezic
 */
public class MyShell implements Environment {

	/**
	 * commands is map which contains each shell command paired with its name
	 * (key of each pair).
	 */
	private SortedMap<String, ShellCommand> commands = new TreeMap<>();

	/** reader used for reading text from standard input. */
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/** writer used for writing text to standard output. */
	private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

	/**
	 * prompt symbol, symbol which occurs at the beginning of each command line.
	 */
	private char promptSymbol = '>';

	/** more lines symbol which represents multiline input. */
	private char moreLinesSymbol = '\\';

	/** multiline symbol which occurs with each new input line. */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.Environment#readLine()
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.Environment#write(java.lang.String)
	 */
	@Override
	public void write(String text) throws ShellIOException {
		try {
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.Environment#writeln(java.lang.String)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.Environment#commands()
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.Environment#getMultilineSymbol()
	 */
	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.Environment#setMultilineSymbol(java.lang.
	 * Character)
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multiLineSymbol = symbol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.Environment#getPromptSymbol()
	 */
	@Override
	public Character getPromptSymbol() {
		return promptSymbol;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.Environment#setPromptSymbol(java.lang.
	 * Character)
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.Environment#getMorelinesSymbol()
	 */
	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.Environment#setMorelinesSymbol(java.lang.
	 * Character)
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreLinesSymbol = symbol;
	}

	/**
	 * The main method of this class, used for user interaction.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		Environment env = new MyShell();

		env.writeln("Welcome to MyShell v 1.0");

		while (true) {
			String line = "";
			while (line.length() < 1) {
				env.write(env.getPromptSymbol().toString() + " ");
				line = env.readLine();
			}

			while (line.charAt(line.length() - 1) == env.getMorelinesSymbol()) {
				env.write(env.getMultilineSymbol().toString());
				line = line.substring(0,line.length()-1).concat(env.readLine());
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
				env.writeln("Type \"help\" for valid commands.");
			}

		}

	}

}
