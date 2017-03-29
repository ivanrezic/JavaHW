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

}
