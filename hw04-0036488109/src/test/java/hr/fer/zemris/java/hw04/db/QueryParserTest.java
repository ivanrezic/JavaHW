package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void testComplexQuerry() {
		QueryParser first = new QueryParser(
				"firstName <  \"C\"And jmbag>\"0000000003\" and 		lastName LIKE \"B*ć\"");
		List<ConditionalExpression> list = first.getQuery();
		
		assertEquals("firstName < \"C\"", list.get(0).toString());
		assertEquals("jmbag > \"0000000003\"", list.get(1).toString());
		assertEquals("lastName LIKE \"B*ć\"", list.get(2).toString());
	}

}
