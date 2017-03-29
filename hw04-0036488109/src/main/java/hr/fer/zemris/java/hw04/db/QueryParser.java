package hr.fer.zemris.java.hw04.db;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class QueryParser represents simple lexer and parser of given queries.
 * 
 * @author Ivan
 */
public class QueryParser {

	/** Regular expression used for direct queries. */
	protected static final String DIRECT_QUERY = "jmbag\\s*=\\s*\"\\d+\"(?!.)";

	/** Regular expression used for string literals. */
	protected static final String STRING_LITERAL = "(?<=\").+(?=\")";

	/** Regular expression used for operators. */
	protected static final String OPERATOR = "(LIKE|<=|>=|!=|>|<|(?<=)=)";

	/** Regular expression used for field variables. */
	protected static final String FIELD_VARIABLE = "(.+?\\s*(?=!|<|>|=|LIKE))";

	/** String representation of query. */
	private String query;

	/** List containing conditional expressions created from queries. */
	private List<ConditionalExpression> conditionals = new LinkedList<>();

	/**
	 * Constructor that instantiates a new query parser.
	 *
	 * @param query
	 *            the query
	 */
	public QueryParser(String query) {
		this.query = query.trim();
	}

	/**
	 * Method which checks if query is direct query.
	 *
	 * @return true, if is direct query
	 */
	boolean isDirectQuery() {
		return query.matches(DIRECT_QUERY);
	}

	/**
	 * Method which gets direct querry jmbag.
	 *
	 * @return the queried JMBAG
	 */
	String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Query is not direct one.");
		}
		return regexMathcer(query, STRING_LITERAL).get(0);
	}

	/**
	 * Regex matcher is helper method which checks if there is any match between
	 * given regex and query.
	 *
	 * @param query
	 *            the query
	 * @param regex
	 *            regular expression
	 * @return the list containing query parts
	 */
	private List<String> regexMathcer(String query, String regex) {
		List<String> parts = new LinkedList<>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {
			parts.add(matcher.group().trim());
		}

		return parts;
	}

	/**
	 * Method which conducts lexic and syntax analysis and later returns list
	 * containing conditional expressions made from lexic tokens.
	 *
	 * @return the query
	 */
	List<ConditionalExpression> getQuery() {
		if (isDirectQuery()) {
			conditionals.add(
					new ConditionalExpression(FieldValueGetters.JMBAG, getQueriedJMBAG(), ComparisonOperators.EQUALS));
			return conditionals;
		}

		String[] queryParts = query.split("\\s*(?i)AND\\s*");
		for (String part : queryParts) {
			List<String> parts = regexMathcer(part, STRING_LITERAL + "|" + OPERATOR + "|" + FIELD_VARIABLE);

			IFieldValueGetter fieldValue = getProperFieldValue(parts.get(0));
			IComparisonOperator operator = getProperComparisonOperator(parts.get(1));
			String stringLiteral = parts.get(2);

			conditionals.add(new ConditionalExpression(fieldValue, stringLiteral, operator));
		}

		return conditionals;
	}

	/**
	 * Gets the proper comparison operator.
	 *
	 * @param string
	 *            the string
	 * @return the proper comparison operator
	 */
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

	/**
	 * Gets the proper field value.
	 *
	 * @param string
	 *            the string
	 * @return the proper field value
	 */
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
