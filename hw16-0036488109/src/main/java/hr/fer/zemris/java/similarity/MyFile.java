package hr.fer.zemris.java.similarity;

import java.util.Collections;
import java.util.List;

public class MyFile {
	private List<String> words;
	private double[] vector;

	public MyFile(List<String> words) {
		this.words = words;
	}
	
	public double calculateVectorSimilarity(double[] otherVector){
		double sum1 = 0;
		double sum2 = 0;
		double scalar = 0;
		
		for (int i = 0; i < vector.length; i++) {
			sum1 += vector[i] * vector[i];
			sum2 += otherVector[i] * otherVector[i];
			scalar = vector[i] * otherVector[i];
		}
		
		return scalar / Math.sqrt(sum1 * sum2);
	}

	public void calculateVector(List<MyWord> vocabulary) {
		this.vector = new double[vocabulary.size()];
		
		for (int i = 0; i < vector.length; i++) {
			vector[i] = calculateVectorComponent(vocabulary.get(i));
		}
	}

	private double calculateVectorComponent(MyWord word) {
		return Collections.frequency(words, word.getText()) * word.getIDF() ;
	}

	public boolean contains(String word) {
		return words.contains(word);
	}
	
	
}
