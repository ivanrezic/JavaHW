package hr.fer.zemris.java.hw04.db;

/**
 * The Class StudentRecord represents one record of student data.
 * 
 * @author Ivan
 */
public class StudentRecord {

	/** Student jmbag. */
	private String jmbag;

	/** Student last name. */
	private String lastName;

	/** Student first name. */
	private String firstName;

	/** Students mark. */
	private int mark;

	/**
	 * Constructor which instantiates a new student record.
	 *
	 * @param jmbag
	 *            the jmbag
	 * @param lastName
	 *            the last name
	 * @param fristName
	 *            the frist name
	 * @param mark
	 *            the mark
	 */
	public StudentRecord(String jmbag, String lastName, String fristName, int mark) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = fristName;
		this.mark = mark;
	}

	/**
	 * Method which returns the jmbag.
	 *
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Method which returns the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method which returns the frist name.
	 *
	 * @return the frist name
	 */
	public String getFristName() {
		return firstName;
	}

	/**
	 * Method which returns the mark.
	 *
	 * @return the mark
	 */
	public int getMark() {
		return mark;
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
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return jmbag + "	" + lastName + "	" + firstName + "	" + mark;
	}

}
