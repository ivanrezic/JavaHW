package hr.fer.zemris.java.hw04.db;

/**
 * The Interface IFieldValueGetter represents strategy used generic fetching of
 * student records.
 * 
 * @author Ivan
 */
public interface IFieldValueGetter {

	/**
	 * Method which gets <code>StudentRecord</code> property defined by
	 * interface implementation.
	 *
	 * @param record
	 *            student record
	 * @return the string representation of wanted property
	 */
	public String get(StudentRecord record);
}
