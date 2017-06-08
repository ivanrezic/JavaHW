package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * Singleton which knows which object should be returned for database
 * communication.
 * 
 * @author Ivan Rezic
 *
 */
public class DAOProvider {

	/** Data Access Object. */
	private static DAO dao = new SQLDAO();

	/**
	 * Fetch dao instance.
	 * 
	 * @return Object which encapsulates database communication.
	 */
	public static DAO getDao() {
		return dao;
	}

}