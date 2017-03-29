package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

/**
 * The Class StudentDatabase represents simple database table containing student
 * records.
 * 
 * @author Ivan
 */
public class StudentDatabase {

	/** The student list contains all student records. */
	List<StudentRecord> studentList = new ArrayList<>();

	/**
	 * The student table is hash table containing all student records. It is
	 * used for direct queries.
	 */
	SimpleHashtable<String, StudentRecord> studentTable = new SimpleHashtable<>();

	/**
	 * Constructor which instantiates a new student database. It gets list of
	 * all student record as text lines which are constructed in
	 * <code>StudentRecords</code>.
	 *
	 * @param data
	 *            the data from database (prva.txt) }
	 */
	public StudentDatabase(List<String> data) {
		for (String dataRow : data) {
			String[] row = dataRow.split("\t");
			StudentRecord studentRecord = new StudentRecord(row[0], row[1], row[2], Integer.parseInt(row[3]));
			studentList.add(studentRecord);
			studentTable.put(row[0], studentRecord);
		}
	}

	/**
	 * StudentRecord getter for given jmbag.
	 *
	 * @param jmbag
	 *            the jmbag
	 * @return the student record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentTable.get(jmbag);
	}

	/**
	 * Method which iterates through all student records and returns only those
	 * who satisfies inner condition defined by IFilter strategy.
	 *
	 * @param filter
	 *            the filter
	 * @return the list containing result elements requested from query, if any
	 */
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
