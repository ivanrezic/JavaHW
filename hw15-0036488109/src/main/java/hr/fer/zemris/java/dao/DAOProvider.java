package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.dao.jpa.JPADAOImpl;

/**
 * <code>DAOProvider</code> is singleton class which provides Data Access object
 * per request.
 *
 * @author Ivan Rezic
 */
public class DAOProvider {

	/** Data Acces Object. */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Method used for getting <code>DAO</code>.
	 *
	 * @return dao
	 */
	public static DAO getDAO() {
		return dao;
	}

}