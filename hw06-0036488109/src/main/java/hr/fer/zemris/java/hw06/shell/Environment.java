package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * <code>Environment</code> interface represents shell environment which is
 * passed to each defined command. Each implemented command communicates with
 * user only through this interface.
 *
 * @author Ivan Rezic
 */
public interface Environment {

	/**
	 * Method used for reading one line from standard input.
	 *
	 * @return the string read from standard input
	 * @throws ShellIOException
	 *             if error occurs during reading
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method used for writing one line from standard input.
	 *
	 * @param text
	 *            the text to be written
	 * @throws ShellIOException
	 *             if error occurs during writing
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method used for writing one line from standard input. After writing it
	 * starts a new line.
	 *
	 * @param text
	 *            the text to be written
	 * @throws ShellIOException
	 *             if error occurs during writing
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns map of commands sorted by natural order. Given map can not be
	 * modified.
	 *
	 * @return the sorted map which contains all shell commands paired with
	 *         their name as key
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Method used for getting property <code>MultilineSymbol</code>.
	 *
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Method which sets new value as multiline symbol.
	 *
	 * @param symbol
	 *            the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Method used for getting property <code>PromptSymbol</code>.
	 *
	 * @return prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Method which sets new value as prompt symbol.
	 *
	 * @param symbol
	 *            the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Method used for getting property <code>MorelinesSymbol</code>.
	 *
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Method which sets new value as morelines symbol.
	 *
	 * @param symbol
	 *            the new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
