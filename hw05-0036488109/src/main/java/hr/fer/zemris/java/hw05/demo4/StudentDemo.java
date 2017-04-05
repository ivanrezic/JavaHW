package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <code>StudentDemo</code> is demonstration class. It shows how to use Java
 * Stream API.
 *
 * @author Ivan Rezic
 */
public class StudentDemo {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./src/main/resources/studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);

		long broj5 = vratiBrojOdlikasa(records);

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);

		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);

		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);

	}

	/**
	 * It returns number of students paired with their marks.
	 *
	 * @param records
	 *            student records
	 * @return Mark -> number of students with that mark
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getMark, e -> 1, (a, b) -> a + b));
	}

	/**
	 * It divides student records in two groups. Ones who passed subject and
	 * those who didn't.
	 *
	 * @param records
	 *            student records
	 * @return true or false -> list of students
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(e -> e.getMark() > 1));
	}

	/**
	 * It shows student groups with their mark paired.
	 *
	 * @param records
	 *            student records
	 * @return Mark -> list of students with that mark
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getMark));
	}

	/**
	 * It shows which students haven't passed the subject.
	 *
	 * @param records
	 *            student records
	 * @return list of jmbags
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getMark() == 1).map(StudentRecord::getJmbag)
				.sorted((e1, e2) -> e1.compareTo(e2)).collect(Collectors.toList());
	}

	/**
	 * It shows list of those students whose subject mark is 5, sorted
	 * descending by overall score.
	 *
	 * @param records
	 *            student records
	 * @return the list of students
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getMark() == 5)
				.sorted((e1, e2) -> -(e1.getOverall().compareTo(e2.getOverall()))).collect(Collectors.toList());
	}

	/**
	 * It shows which students passed their subject with mark excellent.
	 *
	 * @param records
	 *            student records
	 * @return the list of students with mark 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getMark() == 5).collect(Collectors.toList());
	}

	/**
	 * It shows how many student got 5 as their mark.
	 *
	 * @param records
	 *            student records
	 * @return number of excellent students
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getMark() == 5).count();
	}

	/**
	 * It provides insight in number whose value represents students who got
	 * more than 25 points in overall score.
	 *
	 * @param records
	 *            student records
	 * @return number of students
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getOverall() > 25).count();
	}

	/**
	 * Helper method which convert list of string to list of
	 * <code>StudentRecord</code>s, given in argument .
	 *
	 * @param lines
	 *            each lines is one row from studenti.txt as string
	 * @return the list of student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();

		try {
			for (String string : lines) {
				String[] parts = string.split("\\t");
				records.add(new StudentRecord(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
			}
		} catch (IllegalArgumentException e) {
			e.getMessage();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Wrong file or row format.");
		}

		return records;
	}

}
