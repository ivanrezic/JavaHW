package hr.fer.zemris.java.hw05.demo4;

/**
 * <code>StudentRecord</code> represents one student record with its
 * characteristic values.
 *
 * @author Ivan Rezic
 */
public class StudentRecord {

	/** Student's jmbag. */
	private String jmbag;

	/** Student's last name. */
	private String lastName;

	/** Student's first name. */
	private String firstName;

	/** Student's midterm score. */
	private double midterm;

	/** Student's final exam score. */
	private double finalExam;

	/** Student's labs score. */
	private double labs;

	/** Student's final mark. */
	private int mark;

	/**
	 * Constructor which instantiates new student record.
	 *
	 * @param jmbag
	 *            the jmbag
	 * @param lastName
	 *            the last name
	 * @param firstName
	 *            the first name
	 * @param midterm
	 *            midterm score
	 * @param finalExam
	 *            final exam score
	 * @param labs
	 *            labs score
	 * @param mark
	 *            final mark
	 * 
	 * @throws IllegalArgumentException
	 *             if argument type or its value is not correct
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String midterm, String finalExam, String labs,
			String mark) {

		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midterm = Double.parseDouble(midterm);
		this.finalExam = Double.parseDouble(finalExam);
		this.labs = Double.parseDouble(labs);
		this.mark = Integer.parseInt(mark);

		if (getOverall() < 0 || getOverall() > 100) {
			throw new IllegalArgumentException("Wrong cumulative score.");
		}
		if (getMark() < 1 || getMark() > 5) {
			throw new IllegalArgumentException("Mark should be in [1,5] range.");
		}
	}

	/**
	 * Method used for getting property <code>Jmbag</code>.
	 *
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Method used for getting property <code>LastName</code>.
	 *
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method used for getting property <code>FirstName</code>.
	 *
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method used for getting property <code>Midterm</code>.
	 *
	 * @return midterm score
	 */
	public double getMidterm() {
		return midterm;
	}

	/**
	 * Method used for getting property <code>FinalExam</code>.
	 *
	 * @return final exam score
	 */
	public double getFinalExam() {
		return finalExam;
	}

	/**
	 * Method used for getting property <code>Labs</code>.
	 *
	 * @return labs score
	 */
	public double getLabs() {
		return labs;
	}

	/**
	 * Method used for getting property <code>Mark</code>.
	 *
	 * @return passing mark
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * Method used for getting property <code>Overall</code>.
	 *
	 * @return overall
	 */
	public Double getOverall() {
		return midterm + finalExam + labs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

}
