package hr.fer.zemris.java.similarity;

public class MyWord {

	private String text;
	private double IDF;

	public MyWord(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public double getIDF() {
		return IDF;
	}
	
	public void storeIDF(int count, int vocabularySize) {
		this.IDF = Math.log10((vocabularySize * 1.0)/count);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(IDF);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (Double.doubleToLongBits(IDF) != Double.doubleToLongBits(other.IDF))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}
