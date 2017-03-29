package hr.fer.zemris.java.hw04.db;

public class StudentRecord {
	private String jmbag;
	private String lastName;
	private String fristName;
	private int ocjena;

	public StudentRecord(String jmbag, String lastName, String fristName, int ocjena) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.fristName = fristName;
		this.ocjena = ocjena;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFristName() {
		return fristName;
	}

	public int getOcjena() {
		return ocjena;
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

}
