package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

public class StudentDatabase {

	List<StudentRecord> studentList = new ArrayList<>();
	SimpleHashtable<String, StudentRecord> studentTable = new SimpleHashtable<>();

	public StudentDatabase(String[] data) {
		for (String dataRow : data) {
			String[] row = dataRow.split("\t");
			StudentRecord studentRecord = new StudentRecord(row[0], row[1], row[2], Integer.parseInt(row[3]));
			studentList.add(studentRecord);
			studentTable.put(row[0], studentRecord);
		}
	}

	public StudentRecord forJMBAG(String jmbag) {
		return studentTable.get(jmbag);
	}

	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> newList = new ArrayList<>();
		
		for (StudentRecord studentRecord : studentList) {
			if (filter.accepts(studentRecord)) {
				newList.add(studentRecord);
			}
		}
		
		return newList;
	}

}
