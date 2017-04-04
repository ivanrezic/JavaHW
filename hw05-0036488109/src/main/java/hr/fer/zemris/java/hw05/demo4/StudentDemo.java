package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);
//		System.out.println(broj);

		long broj5 = vratiBrojOdlikasa(records);
//		System.out.println(broj5);

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
//		odlikasi.forEach(System.out::println);

		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
//		odlikasiSortirano.forEach(System.out::println);

		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
//		nepolozeniJMBAGovi.forEach(System.out::println);

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
//		for (Integer key : mapaPoOcjenama.keySet()) {
//			System.out.println(key + "   " + mapaPoOcjenama.get(key).size());
//		}

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
//		for (Map.Entry<Integer, Integer> entry : mapaPoOcjenama2.entrySet()) {
//			System.out.println("Ocjena: " + entry.getKey() + " broj: " + entry.getValue());
//		}

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
//		for (Map.Entry<Boolean, List<StudentRecord>> entry : prolazNeprolaz.entrySet()) {
//			entry.getValue().forEach(System.out::println);
//		}
	}

	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getMark, e -> 1, (a, b) -> a + b));
	}

	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(e -> e.getMark() > 1));
	}

	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getMark));
	}

	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getMark() == 1).map(StudentRecord::getJmbag)
				.sorted((e1, e2) -> e1.compareTo(e2)).collect(Collectors.toList());
	}

	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getMark() == 5)
				.sorted((e1, e2) -> -(e1.getOverall().compareTo(e2.getOverall()))).collect(Collectors.toList());
	}

	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getMark() == 5).collect(Collectors.toList());
	}

	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getMark() == 5).count();
	}

	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(e -> e.getOverall() > 25).count();
	}

	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();

		for (String string : lines) {
			String[] parts = string.split("\\t");
			records.add(new StudentRecord(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
		}

		return records;
	}

}
