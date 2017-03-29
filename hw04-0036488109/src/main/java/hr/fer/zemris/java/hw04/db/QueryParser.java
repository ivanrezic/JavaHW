package hr.fer.zemris.java.hw04.db;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
	private static final String STRING_LITERAL = "(?<=\").*(?=\")";
	private static final String OPERATOR_REGEX = "(LIKE|<=|>=|!=|>|<|(?<=)=)";
	private static final String FIELD_VARIABLE_CONSTANT = "(.+?\\s*(?=!|<|>|=|LIKE))";
	private String querry;
	private List<ConditionalExpression> conditionals = new LinkedList<>();

	public QueryParser(String querry) {
		this.querry = querry.trim();
		parse();
	}

	boolean isDirectQuery() {
		return querry.matches("jmbag\\s*=\\s*\".+\"");
	}

	String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Querry is not direct one.");
		}
		return regexMathcer(querry, "(?<=\").+(?=\")").get(0);
	}

	private List<String> regexMathcer(String querry, String regex) {
		List<String> parts = new LinkedList<>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(querry);

		while (matcher.find()) {
			parts.add(matcher.group().trim());
		}

		return parts;
	}

	List<ConditionalExpression> getQuery() {
		return conditionals;
	}

	private void parse() {
		if (isDirectQuery()) {
			conditionals.add(
					new ConditionalExpression(FieldValueGetters.JMBAG, getQueriedJMBAG(), ComparisonOperators.EQUALS));
			return;
		}

		String[] querryParts = querry.split("\\s*(?i)AND\\s*");
		for (String part : querryParts) {
			if (part.matches("jmbag\\s*=\\s*\".+\"")) {
				throw new IllegalArgumentException("Multiple querries can not contain direct querry.");
			}

			List<String> parts = regexMathcer(part,
					STRING_LITERAL + "|" + OPERATOR_REGEX + "|" + FIELD_VARIABLE_CONSTANT);

			IFieldValueGetter fieldValue = getProperFieldName(parts.get(0));
			IComparisonOperator operator = getProperComparisonOperator(parts.get(1));
			String stringLiteral = parts.get(2);
			conditionals.add(new ConditionalExpression(fieldValue, stringLiteral, operator));
		}
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
		return null;
	}

	private IFieldValueGetter getProperFieldName(String string) {
		switch (string) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		}
		return null;
	}
}
