package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConditionalExpressionTest {

	ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*", ComparisonOperators.LIKE);
	StudentRecord first = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
	StudentRecord second = new StudentRecord("0000000031", "Krušelj Posavec", "Bojan", 4);
	
	@Test
	public void testCorrectMatch() {
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldValue().get(first), // returns lastName from given record
				 expr.getStringLiteral() // returns "Bos*"
				);
		
		assertEquals(true, recordSatisfies);
	}
	
	@Test
	public void testInCorrectMatch() {
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldValue().get(second), // returns lastName from given record
				 expr.getStringLiteral() // returns "Bos*"
				);
		
		assertEquals(false, recordSatisfies);
	}

}
