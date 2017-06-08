package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * Class which encapsulates connection store in ThreadLocal object. ThreadLocal
 * is map whose keys are thread id-s which communicates with database.
 * 
 * @author Ivan Rezic
 *
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Set connection as current thread (or delete value if argument is
	 * <code>null</code>).
	 * 
	 * @param con
	 *            Database connection.
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Fetch connection which can be currently used.
	 * 
	 * @return Database connection.
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}