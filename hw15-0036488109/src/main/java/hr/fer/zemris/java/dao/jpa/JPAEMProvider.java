package hr.fer.zemris.java.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.dao.DAOException;

/**
 * <code>JPAEMProvider</code> is class which provides EntityManager per request
 * using hash map which stores threads used for communicating with database.
 *
 * @author Ivan Rezic
 */
public class JPAEMProvider {

	/** Thread map. */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Method used for getting <code>EntityManager</code>.
	 *
	 * @return entity manager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Method which closes entity manager and simultaneously commits
	 * transaction.
	 *
	 * @throws DAOException
	 *             the DAO exception
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * <code>LocalData</code> is object which encapsulates EntityManager.
	 *
	 * @author Ivan Rezic
	 */
	private static class LocalData {

		/** EntityManager. */
		EntityManager em;
	}

}