package hr.fer.zemris.java.similarity;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * <code>MyFile</code> is object which encapsultes each article. It provides
 * insight to words used in article, path to article and vector used for
 * cumputation.
 *
 * @author Ivan Rezic
 */
public class MyFile {
	
	/** words in article. */
	private List<String> words;
	
	/** file path to article. */
	private Path filePath;
	
	/** file vector. */
	private double[] vector;

	/**
	 * Constructor which instantiates new my file.
	 *
	 * @param words the words
	 * @param filePath the file path
	 */
	public MyFile(List<String> words, Path filePath) {
		this.words = words;
		this.filePath = filePath;
	}
	
	/**
	 * Calculates similarity between two vectors.
	 *
	 * @param otherVector the other vector
	 * @return the double
	 */
	public double calculateVectorSimilarity(double[] otherVector){
		double sum1 = 0;
		double sum2 = 0;
		double scalar = 0;
		
		for (int i = 0; i < vector.length; i++) {
			sum1 += vector[i] * vector[i];
			sum2 += otherVector[i] * otherVector[i];
			scalar += vector[i] * otherVector[i];
		}
		
		if (sum1 == 0 || sum2 == 0) {
			return 0;
		}
		
		return scalar / Math.sqrt(sum1 * sum2);
	}

	/**
	 * Calculates vector for article.
	 *
	 * @param vocabulary the vocabulary
	 */
	public void calculateVector(List<MyWord> vocabulary) {
		this.vector = new double[vocabulary.size()];
		
		for (int i = 0; i < vector.length; i++) {
			vector[i] = calculateVectorComponent(vocabulary.get(i));
		}
	}

	/**
	 * Calculates vector component.
	 *
	 * @param word the word
	 * @return the double
	 */
	private double calculateVectorComponent(MyWord word) {
		return Collections.frequency(words, word.getText()) * word.getIDF() ;
	}

	/**
	 * Checks if article contains given word.
	 *
	 * @param word the word
	 * @return true, if it is successful, false otherwise
	 */
	public boolean contains(String word) {
		return words.contains(word);
	}
	
	/**
	 * Method used for getting property <code>FilePath</code>.
	 *
	 * @return file path
	 */
	public Path getFilePath() {
		return filePath;
	}
}
