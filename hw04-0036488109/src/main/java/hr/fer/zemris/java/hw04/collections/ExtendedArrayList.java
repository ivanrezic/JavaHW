package hr.fer.zemris.java.hw04.collections;

import java.util.ArrayList;

import hr.fer.zemris.java.hw04.db.StudentRecord;

public class ExtendedArrayList<E> extends ArrayList<E> {
	// this class is used to store max last name and name size for later
	// retrieving in StudentDB
	private static final long serialVersionUID = 8328013570454164016L;

	private static int lastNameLongestValue;
	private static int firstNameLongestValue;

	public static int getLastNameLongestValue() {
		return lastNameLongestValue;
	}

	public static int getFirstNameLongestValue() {
		return firstNameLongestValue;
	}

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
