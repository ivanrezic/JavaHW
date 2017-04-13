package hr.fer.zemris.java.hw06.shell;

/**
 * <code>ShellStatus</code> represents action that should be conducted after
 * each command execution.
 *
 * @author Ivan Rezic
 */
public enum ShellStatus {

	/** Shell should continue with its work. */
	CONTINUE,
	/** Shell should stop with its work. */
	TERMINATE
}
