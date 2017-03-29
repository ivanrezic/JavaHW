package hr.fer.zemris.java.hw04.db;

/**
 * The Interface IComparisonOperator represents strategy used for encapsulating
 * conditions that define values relation .
 * 
 * @author Ivan
 */
public interface IComparisonOperator {

	/**
	 * Method which checks if condition is satisfied.
	 *
	 * @param value1
	 *            first value
	 * @param value2
	 *            second value
	 * @return true, if successful, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
