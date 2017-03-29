package hr.fer.zemris.java.hw04.db;

import java.util.List;

/**
 * The Class QueryFilter implements interface IFilter and defines one filter
 * used for studnet record filtering.
 * 
 * @author Ivan
 */
public class QueryFilter implements IFilter {

	/** Conditional expression made from query. */
	private List<ConditionalExpression> queries;

	/**
	 * Constructor which instantiates a new query filter.
	 *
	 * @param queries
	 *            the queries
	 */
	public QueryFilter(List<ConditionalExpression> queries) {
		this.queries = queries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw04.db.IFilter#accepts(hr.fer.zemris.java.hw04.db.
	 * StudentRecord)
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		boolean recordSatisfies = true;

		for (ConditionalExpression query : queries) {
			recordSatisfies = recordSatisfies && query.getComparisonOperator()
					.satisfied(query.getFieldValue().get(record), query.getStringLiteral());
		}

		return recordSatisfies;
	}

}
