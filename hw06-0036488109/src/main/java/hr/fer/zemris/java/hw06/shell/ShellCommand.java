package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * <code>ShellCommand</code> is interface which encapsulates each shell command.
 * It requires next method implementations:
 * <ul>
 * <li>{@link #executeCommand()}</li>
 * <li>{@link #getCommandName()}</li>
 * <li>{@link #getCommandDescription()}</li>
 * </ul>
 *
 * @author Ivan Rezic
 */
public interface ShellCommand {

	/**
	 * Executes command and returns <code>ShellStatus</code> which defines if
	 * shell continues with its work or it terminates.
	 *
	 * @param env
	 *            the environment used for shell
	 * @param arguments
	 *            the arguments of command
	 * @return the shell status CONTINUE or TERMINATE
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Method used for getting <code>CommandName</code>.
	 *
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Method used for getting <code>CommandDescription</code>.
	 *
	 * @return command description
	 */
	List<String> getCommandDescription();

}
