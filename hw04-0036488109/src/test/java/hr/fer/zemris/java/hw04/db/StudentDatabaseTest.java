package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudentDatabaseTest {
	List<String> lines;
	StudentDatabase database;

	@Before
	public void initialize() {
		try {
			lines = Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		database = new StudentDatabase(lines);
	}

	@Test
	public void studentDatabaseInstantiation() {
		assertEquals(new StudentRecord("0000000001", "Akšamović", "Marin", 2), database.forJMBAG("0000000001"));
		assertEquals(new StudentRecord("0000000063", "Žabčić", "Željko", 4), database.forJMBAG("0000000063"));
		assertEquals(new StudentRecord("0000000031", "Krušelj Posavec", "Bojan", 4), database.forJMBAG("0000000031"));
	}

//	@Test
//	public void filterTest() throws Exception {
//		database.filter(new IFilter() {
//			
//			@Override
//			public boolean accepts(StudentRecord record) {
//				return true;
//				
//			}
//		});
//	}
	
	@Test
	public void filterTestAlwaysTrue() throws Exception {
		assertEquals(63, database.filter((StudentRecord)->true).size());
	}
	
	@Test
	public void filterTestAlwaysFalse() throws Exception {
		assertEquals(0, database.filter((StudentRecord)->false).size());
	}
}
