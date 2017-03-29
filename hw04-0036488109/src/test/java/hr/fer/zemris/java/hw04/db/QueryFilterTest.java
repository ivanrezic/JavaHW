package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class QueryFilterTest {

	List<String> lines;
	StudentDatabase database;
	QueryParser parser;

	@Before
	public void initialize() {
		try {
			lines = Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		database = new StudentDatabase(lines);
	}

	private List<StudentRecord> getRecords(StudentDatabase database, QueryParser parser) {
		List<StudentRecord> list = new ArrayList<>();

		if (parser.isDirectQuery()) {
			StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
			list.add(r);
		} else {
			for (StudentRecord r : database.filter(new QueryFilter(parser.getQuery()))) {
				list.add(r);
			}
		}

		return list;
	}

	@Test
	public void testSimpleDirectQuerry() {
		parser = new QueryParser("jmbag=\"0000000048\"");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000048");
	}

	@Test
	public void testSimpleLikeQuerry() {
		parser = new QueryParser(" jmbagLIKE\"*48\"");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000048");

		parser = new QueryParser("firstNameLIKE	\"*dis\"");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000005");

		parser = new QueryParser("lastName LIKE\"Glavinić P*\"	");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000015");
	}

	@Test
	public void testComplexQuerry() {
		parser = new QueryParser("jmbag>\"0000000016\" aND jmbag<\"0000000020\"anD lastNameLIKE\"Gv*\" 	");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000019");

		parser = new QueryParser(
				"jmbag>=\"0000000033\" aND jmbag<\"0000000042\"anD lastNameLIKE\"Ma*\"andfirstNameLIKE \"Jo*\" 	");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000038");

		parser = new QueryParser(
				"jmbag>\"0000000047\" aND jmbag<\"0000000051\"AND jmbag!=\"0000000050\"anDfirstNameLIKE \"Br*\" aNdlastNameLIKE\"R*ć\" 	");
		assertEquals(getRecords(database, parser).get(0).getJmbag(), "0000000048");
	}

}
