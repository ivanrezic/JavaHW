package hr.fer.zemris.java.hw05.demo4;

public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private double midterm;
	private double finalExam;
	private double labs;
	private int mark;

	public StudentRecord(String jmbag, String lastName, String firstName, String midterm, String finalExam, String labs,
			String mark) {

		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midterm = Double.parseDouble(midterm);
		this.finalExam = Double.parseDouble(finalExam);
		this.labs = Double.parseDouble(labs);
		this.mark = Integer.parseInt(mark);
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public double getMidterm() {
		return midterm;
	}

	public double getFinalExam() {
		return finalExam;
	}

	public double getLabs() {
		return labs;
	}

	public int getMark() {
		return mark;
	}
	
	public Double getOverall() {
		return midterm + finalExam + labs;
	}

	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", lastName=" + lastName + ", firstName=" + firstName + ", midterm="
				+ midterm + ", finalExam=" + finalExam + ", labs=" + labs + ", mark=" + mark + "]";
	}
	
	
}
