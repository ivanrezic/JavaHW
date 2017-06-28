package hr.fer.zemris.java.similarity;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <code>Processor</code> is class which enables articles data processing. It
 * calculates TF-IDF vector and collects all words into vocabulary.
 *
 * @author Ivan Rezic
 */
public class Processor {
	
	/** Articles vocabulary. */
	private Set<MyWord> vocabulary;
	
	/** Articles as files. */
	private List<MyFile> files;
	
	/**
	 * Constructor which instantiates new processor.
	 *
	 * @param dirPath the dir path
	 */
	public Processor(Path dirPath) {
		this.vocabulary = new HashSet<>();
		this.files = new ArrayList<>();
		
		try {
			process(dirPath);
		} catch (IOException e) {
			System.out.println("Ups, something went wrong with Input/Output.");
		}
	}

	/**
	 * Goes trough each file within directory and computes its vector as
	 * collects words for vocabulary.
	 *
	 * @param dirPath
	 *            the dir path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void process(Path dirPath) throws IOException {
		List<String> stopWords = Files.readAllLines(Paths.get("src/main/resources/hrvatski_stoprijeci.txt"));
		Files.walk(dirPath).filter(Files::isRegularFile).forEach(e -> extractWords(e,stopWords));
		
		int length = files.size();
		for (MyWord word : vocabulary) {
			int count = 0;
			for (MyFile file : files) {
				if (file.contains(word.getText())) {
					count++;
				}
			}
			word.setIDF(count, length);
		}
		
		files.forEach(e -> e.calculateVector(new ArrayList<>(vocabulary)));
	}

	/**
	 * Helper method which extracts words from current line red.
	 *
	 * @param filePath the file path
	 * @param stopWords the stop words
	 */
	private void extractWords(Path filePath, List<String> stopWords) {
		List<String> fileWords = new ArrayList<>();
		
		try(BufferedReader reader = Files.newBufferedReader(filePath)) {
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				String[] parts = currentLine.split("[^\\p{L}]");
				for (String word : parts) {
					word = word.toLowerCase();
					fileWords.add(word);
					if (stopWords.contains(word) || word.isEmpty()) continue;
					vocabulary.add(new MyWord(word));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		files.add(new MyFile(fileWords, filePath));
	}

	/**
	 * Method used for getting property <code>Vocabulary</code>.
	 *
	 * @return vocabulary
	 */
	public List<MyWord> getVocabulary() {
		return new ArrayList<>(vocabulary);
	}
	
	/**
	 * Method used for getting property <code>Files</code>.
	 *
	 * @return files
	 */
	public List<MyFile> getFiles() {
		return files;
	}
}
