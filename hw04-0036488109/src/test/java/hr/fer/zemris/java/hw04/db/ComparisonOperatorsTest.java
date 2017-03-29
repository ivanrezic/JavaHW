package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComparisonOperatorsTest {
	IComparisonOperator less = ComparisonOperators.LESS;
	IComparisonOperator lessOrEquals = ComparisonOperators.LESS_OR_EQUALS;
	IComparisonOperator greater = ComparisonOperators.GREATER;
	IComparisonOperator greaterOrEquals = ComparisonOperators.GREATER_OR_EQUALS;
	IComparisonOperator equals = ComparisonOperators.EQUALS;
	IComparisonOperator notEquals = ComparisonOperators.NOT_EQUALS;
	IComparisonOperator like = ComparisonOperators.LIKE;

	@Test
	public void testLess() {
		assertTrue(less.satisfied("auto", "motor"));
		assertFalse(less.satisfied("motor", "auto"));
		
		//test locale
//		assertTrue(less.satisfied("časopis", "zebra"));
//		assertTrue(less.satisfied("štitnik", "zebra"));
//		assertTrue(less.satisfied("ćevap", "zebra"));
//		assertTrue(less.satisfied("đumbir", "zebra"));
		assertTrue(less.satisfied("džem", "zebra"));
		assertTrue(less.satisfied("zebra", "žir"));
	}
	
	@Test
	public void testLessOrEquals() {
		assertTrue(lessOrEquals.satisfied("auto", "motor"));
		assertTrue(lessOrEquals.satisfied("motor", "motor"));
		assertFalse(lessOrEquals.satisfied("motor", "auto"));
	}

	@Test
	public void testGreater() {
		assertTrue(greater.satisfied("motor", "auto"));
		assertFalse(greater.satisfied("auto", "motor"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		assertTrue(greaterOrEquals.satisfied("motor", "auto"));
		assertTrue(greaterOrEquals.satisfied("motor", "motor"));
		assertFalse(greaterOrEquals.satisfied("auto", "motor"));
	}
	
	@Test
	public void testEquals() {
		assertTrue(equals.satisfied("auto", "auto"));
		assertFalse(equals.satisfied("auto", "motor"));
	}

	@Test
	public void testNotEquals() {
		assertFalse(notEquals.satisfied("auto", "auto"));
		assertTrue(notEquals.satisfied("auto", "motor"));
	}
	
	@Test
	public void testLike() {
		assertEquals(false, like.satisfied("Zagreb", "Aba*"));
		assertEquals(false, like.satisfied("AAA", "AA*AA"));
		assertEquals(true, like.satisfied("AAAA", "AA*AA"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLikeWithMoreThanOneWildcard() {
		assertEquals(true, like.satisfied("AAA", "AA**AA"));
		assertEquals(true, like.satisfied("AAA", "*****AAAA"));
		assertEquals(true, like.satisfied("AAA", "AAAA***"));
	}
}
