package hr.fer.zemris.java.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * <code>JPAEMFProvider</code> is singleton class which provides
 * EntitiyManagerFactory per request.
 *
 * @author Ivan Rezic
 */
public class JPAEMFProvider {

	/** EntityManagerFactory */
	public static EntityManagerFactory emf;

	/**
	 * Method used for getting property <code>Emf</code>.
	 *
	 * @return emf
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Method which sets new value as emf.
	 *
	 * @param emf
	 *            new EntityManagerFactory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}