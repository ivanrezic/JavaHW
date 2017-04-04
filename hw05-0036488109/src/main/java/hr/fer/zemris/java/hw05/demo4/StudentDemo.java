package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<StudentRecord> records = convert(lines);

		long broj = records.stream().filter(e -> e.getOverall() > 25).count();

		long broj5 = records.stream().filter(e -> e.getMark() == 5).count();

		List<StudentRecord> odlikasi = records.stream().filter(e -> e.getMark() == 5).collect(Collectors.toList());

		List<StudentRecord> odlikasiSortirano = records.stream().filter(e -> e.getMark() == 5)
				.sorted((e1, e2) -> e1.getOverall().compareTo(e2.getOverall())).collect(Collectors.toList());
		
//		List<String> nepolozeniJMBAGovi = records.stream().
	}

	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();

		for (String string : lines) {
			String[] parts = string.split("\t");
			records.add(new StudentRecord(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
		}

		return records;
	}

}
