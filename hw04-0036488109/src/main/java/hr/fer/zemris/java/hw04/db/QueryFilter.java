package hr.fer.zemris.java.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {
	private List<ConditionalExpression> queries;

	public QueryFilter(List<ConditionalExpression> querries) {
		this.queries = querries;
	}

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
