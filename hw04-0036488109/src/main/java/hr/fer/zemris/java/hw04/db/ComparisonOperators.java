package hr.fer.zemris.java.hw04.db;

/**
 * The Class ComparisonOperators.
 * 
 * @author Ivan
 */
public class ComparisonOperators {

	/** Checks if first value is less than other. */
	public static final IComparisonOperator LESS;

	/** Checks if first value is less than other or equals. */
	public static final IComparisonOperator LESS_OR_EQUALS;

	/** Checks if first value is greater than other. */
	public static final IComparisonOperator GREATER;

	/** Checks if first value is greater than other or equals. */
	public static final IComparisonOperator GREATER_OR_EQUALS;

	/** Checks if first value is equal to the other. */
	public static final IComparisonOperator EQUALS;

	/** Checks if first value is not equal to the other. */
	public static final IComparisonOperator NOT_EQUALS;

	/**
	 * Checks if first value matches second value, for second value we can use
	 * wildcard once.
	 */
	public static final IComparisonOperator LIKE;

	static {
		LESS = (String value1, String value2) -> value1.compareTo(value2) < 0;
		LESS_OR_EQUALS = (String value1, String value2) -> value1.compareTo(value2) <= 0;
		GREATER = (String value1, String value2) -> value1.compareTo(value2) > 0;
		GREATER_OR_EQUALS = (String value1, String value2) -> value1.compareTo(value2) >= 0;
		EQUALS = (String value1, String value2) -> value1.compareTo(value2) == 0;
		NOT_EQUALS = (String value1, String value2) -> value1.compareTo(value2) != 0;

		LIKE = new IComparisonOperator() {

			@Override
			public boolean satisfied(String value1, String value2) {
				/* - check how many * in value2 */
				if ((value2.length() - value2.replace("*", "").length()) > 1) {
					throw new IllegalArgumentException("There should be only one * or none in second argument.");
				}

				return value1.matches(value2.replace("*", ".*"));

			}
		};
	}
}
