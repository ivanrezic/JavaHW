package hr.fer.zemris.java.hw04.db;

/**
 * The Class ConditionalExpression represents query expression encapsutlated in
 * two private variables defined by interfaces: {@link #IFieldValueGetter},
 * {@link #IComparisonOperator} and one plain string.
 * 
 * @author Ivan
 */
public class ConditionalExpression {

	/** The field value. */
	private IFieldValueGetter fieldValue;

	/** The string literal. */
	private String stringLiteral;

	/** The comparison operator. */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor that instantiates a new conditional expression.
	 *
	 * @param fieldValue
	 *            the field value
	 * @param stringLiteral
	 *            the string literal
	 * @param operator
	 *            the operator
	 */
	public ConditionalExpression(IFieldValueGetter fieldValue, String stringLiteral, IComparisonOperator operator) {
		this.fieldValue = fieldValue;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = operator;
	}

	/**
	 * Method which returns field value.
	 *
	 * @return the field value
	 */
	public IFieldValueGetter getFieldValue() {
		return fieldValue;
	}

	/**
	 * Method which returns literal.
	 *
	 * @return the string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Method which returns comparison operator.
	 *
	 * @return the comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return fieldValueToString() + " " + comparisonOperatorToString() + " \"" + stringLiteral + "\"";
	}

	/**
	 * Comparison operator helper method which returns <code>String</code>
	 * representation of each operator.
	 *
	 * @return the string
	 */
	private String comparisonOperatorToString() {
		if (comparisonOperator.equals(ComparisonOperators.EQUALS)) {
			return "=";
		} else if (comparisonOperator.equals(ComparisonOperators.NOT_EQUALS)) {
			return "!=";
		} else if (comparisonOperator.equals(ComparisonOperators.LESS)) {
			return "<";
		} else if (comparisonOperator.equals(ComparisonOperators.LESS_OR_EQUALS)) {
			return "<=";
		} else if (comparisonOperator.equals(ComparisonOperators.GREATER)) {
			return ">";
		} else if (comparisonOperator.equals(ComparisonOperators.GREATER_OR_EQUALS)) {
			return ">=";
		} else {
			return "LIKE";
		}
	}

	/**
	 * Field value to string is helper method which returns <code>String</code>
	 * representation of field value..
	 *
	 * @return the string
	 */
	private String fieldValueToString() {
		if (fieldValue.equals(FieldValueGetters.JMBAG)) {
			return "jmbag";
		} else if (fieldValue.equals(FieldValueGetters.FIRST_NAME)) {
			return "firstName";
		} else {
			return "lastName";
		}
	}

}
