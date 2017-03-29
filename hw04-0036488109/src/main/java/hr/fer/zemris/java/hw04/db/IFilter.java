package hr.fer.zemris.java.hw04.db;

/**
 * The Interface IFilter represents strategy used for checking student records.
 * 
 * @author Ivan
 */
public interface IFilter {

	/**
	 * Method checks if given student record satisfies condition given by
	 * implementation.
	 *
	 * @param record
	 *            student record
	 * @return true, if satisfied, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
