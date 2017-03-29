package hr.fer.zemris.java.hw04.collections;

import java.util.ArrayList;

import hr.fer.zemris.java.hw04.db.StudentRecord;

/**
 * The Class ExtendedArrayList is helper class, used for storing
 * <code>StudentRecord</code> properties max value.
 *
 * @param <E>
 *            the element type
 * 
 * @author Ivan
 */
public class ExtendedArrayList<E> extends ArrayList<E> {
	// this class is used to store max last name and name size for later
	// retrieving in StudentDB
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8328013570454164016L;

	/** StudentRecord last name longest value. */
	private static int lastNameLongestValue;

	/** S first name longest value. */
	private static int firstNameLongestValue;

	/**
	 * Gets the last name longest value.
	 *
	 * @return the last name longest value
	 */
	public static int getLastNameLongestValue() {
		return lastNameLongestValue;
	}

	/**
	 * Gets the first name longest value.
	 *
	 * @return the first name longest value
	 */
	public static int getFirstNameLongestValue() {
		return firstNameLongestValue;
	}

	/**
	 * {@inheritDoc} If last added element has more characters replace current
	 * {@link #lastNameLongestValue} or {@link #firstNameLongestValue}.
	 */
	@Override
	public boolean add(E e) {
		if (e instanceof StudentRecord) {
			String lastName = ((StudentRecord) e).getLastName();
			String firstName = ((StudentRecord) e).getFristName();

			if (lastNameLongestValue < lastName.length()) {
				lastNameLongestValue = lastName.length();
			}
			if (firstNameLongestValue < firstName.length()) {
				firstNameLongestValue = firstName.length();
			}
		}

		return super.add(e);

	}

}
