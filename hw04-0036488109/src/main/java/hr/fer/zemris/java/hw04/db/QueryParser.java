package hr.fer.zemris.java.hw04.db;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
	protected static final String DIRECT_QUERY = "jmbag\\s*=\\s*\"\\d+\"(?!.)";
	protected static final String STRING_LITERAL = "(?<=\").+(?=\")";
	protected static final String OPERATOR = "(LIKE|<=|>=|!=|>|<|(?<=)=)";
	protected static final String FIELD_VARIABLE_CONSTANT = "(.+?\\s*(?=!|<|>|=|LIKE))";
	private String query;
	private List<ConditionalExpression> conditionals = new LinkedList<>();

	public QueryParser(String query) {
		this.query = query.trim();
	}

	boolean isDirectQuery() {
		return query.matches(DIRECT_QUERY);
	}

	String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Query is not direct one.");
		}
		return regexMathcer(query, STRING_LITERAL).get(0);
	}

	private List<String> regexMathcer(String query, String regex) {
		List<String> parts = new LinkedList<>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {
			parts.add(matcher.group().trim());
		}

		return parts;
	}

	List<ConditionalExpression> getQuery() {
		if (isDirectQuery()) {
			conditionals.add(
					new ConditionalExpression(FieldValueGetters.JMBAG, getQueriedJMBAG(), ComparisonOperators.EQUALS));
			return conditionals;
		}

		String[] queryParts = query.split("\\s*(?i)AND\\s*");
		for (String part : queryParts) {
			List<String> parts = regexMathcer(part, STRING_LITERAL + "|" + OPERATOR + "|" + FIELD_VARIABLE_CONSTANT);

			IFieldValueGetter fieldValue = getProperFieldValue(parts.get(0));
			IComparisonOperator operator = getProperComparisonOperator(parts.get(1));
			String stringLiteral = parts.get(2);
			
			conditionals.add(new ConditionalExpression(fieldValue, stringLiteral, operator));
		}
		
		return conditionals;
	}

	private IComparisonOperator getProperComparisonOperator(String string) {
		switch (string) {
		case "<":
			return ComparisonOperators.LESS;
		case ">":
			return ComparisonOperators.GREATER;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		}
		throw new IllegalStateException();
	}

	private IFieldValueGetter getProperFieldValue(String string) {
		switch (string) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		}
		throw new IllegalStateException();
	}
}
