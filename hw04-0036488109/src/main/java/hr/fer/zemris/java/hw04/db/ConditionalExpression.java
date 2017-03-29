package hr.fer.zemris.java.hw04.db;

public class ConditionalExpression {

	private IFieldValueGetter fieldValue;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;

	public ConditionalExpression(IFieldValueGetter fieldValue, String stringLiteral, IComparisonOperator operator) {
		this.fieldValue = fieldValue;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = operator;
	}

	public IFieldValueGetter getFieldValue() {
		return fieldValue;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	@Override
	public String toString() {
		return fieldValueToString() + " " + comparisonOperatorToString() + " \"" + stringLiteral + "\"";
	}

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
