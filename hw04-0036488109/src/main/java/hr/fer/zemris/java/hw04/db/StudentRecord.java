package hr.fer.zemris.java.hw04.db;

public class StudentRecord {
	private String jmbag;
	private String lastName;
	private String firstName;
	private int mark;

	public StudentRecord(String jmbag, String lastName, String fristName, int mark) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = fristName;
		this.mark = mark;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFristName() {
		return firstName;
	}

	public int getMark() {
		return mark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

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

	@Override
	public String toString() {
		return jmbag + "	" + lastName + "	" + firstName + "	" + mark;
	}

}
