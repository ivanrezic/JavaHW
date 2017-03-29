package hr.fer.zemris.java.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw04.collections.ExtendedArrayList;

/**
 * The Class StudentDB represents program which fetches data from database. All
 * data is fetched from database prva.txt and queries are given thoru standard
 * input.
 * 
 * @author Ivan
 */
public class StudentDB {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments from command line, not used in this method
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {

		StudentDatabase db = new StudentDatabase(Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8));
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.printf("> ");
			String input = scanner.nextLine();
			if (input.equals("exit")) {
				System.out.println("Goodbye!");
				scanner.close();
				System.exit(1);
			}

			String query = null;
			try {
				query = input.split("query\\s*")[1].trim();
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Each query should start with keyword: query");
				continue;
			}

			List<StudentRecord> result = getRecords(db, new QueryParser(query));
			if (query.matches(QueryParser.DIRECT_QUERY)) {
				System.out.println("Using index for record retrieval.");
			}
			if (!result.isEmpty()) {
				printRecords(db, query, result);
			}
			System.out.println("Records selected: " + result.size());
		}

	}

	/**
	 * Helper method which prints the records encapsulated with borders.
	 *
	 * @param db
	 *            the database
	 * @param query
	 *            the query
	 * @param result
	 *            the result
	 */
	private static void printRecords(StudentDatabase db, String query, List<StudentRecord> result) {
		printBorder();
		for (StudentRecord studentRecord : result) {
			int surnameCharsSize = ExtendedArrayList.getLastNameLongestValue();
			int nameCharsSize = ExtendedArrayList.getFirstNameLongestValue();
			String jmbag = studentRecord.getJmbag();
			String lastName = studentRecord.getLastName();
			String firstName = studentRecord.getFristName();
			int mark = studentRecord.getMark();

			System.out.printf("| %s | %" + surnameCharsSize + "s | %" + nameCharsSize + "s | %d |%n", jmbag, lastName,
					firstName, mark);
		}
		printBorder();
	}

	/**
	 * Helper method which prints the border.
	 */
	private static void printBorder() {
		int maxLengthSurname = ExtendedArrayList.getLastNameLongestValue();
		int maxLengthName = ExtendedArrayList.getFirstNameLongestValue();
		char[] chars;

		chars = new char[maxLengthSurname + 2];
		Arrays.fill(chars, '=');
		String surname = new String(chars);

		chars = new char[maxLengthName + 2];
		Arrays.fill(chars, '=');
		String name = new String(chars);

		System.out.printf("+============+%s+%s+===+%n", surname, name);
	}

	/**
	 * Helper method which fetches data requested by query.
	 *
	 * @param database
	 *            the database
	 * @param parser
	 *            the parser
	 * @return the records
	 */
	private static List<StudentRecord> getRecords(StudentDatabase database, QueryParser parser) {
		List<StudentRecord> list = new ExtendedArrayList<>();

		if (parser.isDirectQuery()) {
			StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
			if (r != null) {
				list.add(r);
			}

		} else {
			try {
				for (StudentRecord r : database.filter(new QueryFilter(parser.getQuery()))) {
					list.add(r);
				}
			} catch (IllegalStateException | IndexOutOfBoundsException e) {
				System.out.println("Wrong query input.");
			}
		}

		return list;
	}
}
