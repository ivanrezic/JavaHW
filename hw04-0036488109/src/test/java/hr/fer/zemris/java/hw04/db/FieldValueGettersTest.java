package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldValueGettersTest {
	StudentRecord record = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
	
	@Test
	public void testFirstName() {
		assertEquals("Kristijan", FieldValueGetters.FIRST_NAME.get(record));
	}
	@Test
	public void testLastName() {
		assertEquals("Glavinić Pecotić", FieldValueGetters.LAST_NAME.get(record));
	}

	@Test
	public void testJmbag() {
		assertEquals("0000000015", FieldValueGetters.JMBAG.get(record));
	}

}
