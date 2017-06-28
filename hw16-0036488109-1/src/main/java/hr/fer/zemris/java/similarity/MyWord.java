package hr.fer.zemris.java.similarity;

/**
 * <code>MyWord</code> is class which encapsulates one vocabulary word. Each word has its text value and IDF value.
 *
 * @author Ivan Rezic
 */
public class MyWord {

	/** text. */
	private String text;
	
	/** IDF part from TF-IDF vector. */
	private double IDF;

	/**
	 * Constructor which instantiates new my word.
	 *
	 * @param text the text
	 */
	public MyWord(String text) {
		this.text = text;
	}
	
	/**
	 * Method used for getting property <code>Text</code>.
	 *
	 * @return text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Method used for getting property <code>IDF</code>.
	 *
	 * @return idf
	 */
	public double getIDF() {
		return IDF;
	}
	
	/**
	 * Sets the IDF.
	 *
	 * @param count the count
	 * @param filesSize the files size
	 */
	public void setIDF(int count, int filesSize) {
		this.IDF = Math.log((filesSize * 1.0)/count);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MyWord))
			return false;
		MyWord other = (MyWord) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	
}
