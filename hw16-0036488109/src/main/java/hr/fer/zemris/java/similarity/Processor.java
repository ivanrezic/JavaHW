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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {
	
	private List<MyWord> vocabulary;
	private List<MyFile> files;
	
	public Processor(Path dirPath) throws IOException {
		this.vocabulary = new ArrayList<>();
		this.files = new ArrayList<>();
		process(dirPath);
	}

	private void process(Path dirPath) throws IOException {
		List<String> stopWords = Files.readAllLines(Paths.get("src/main/resources/hrvatski_stoprijeci.txt"));
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		Files.walk(dirPath).forEach(e -> extractWords(e,pattern,stopWords));
		
		int length = vocabulary.size();
		for (MyWord word : vocabulary) {
			int count = 0;
			for (MyFile file : files) {
				if (file.contains(word.getText())) {
					count++;
				}
			}
			word.storeIDF(count, length);
		}
		
		files.forEach(e -> e.calculateVector(vocabulary));
	}

	private void extractWords(Path filePath, Pattern pattern, List<String> stopWords) {
		List<String> fileWords = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		
		try(BufferedReader reader = Files.newBufferedReader(filePath)) {
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				builder.append(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Matcher matcher = pattern.matcher(builder);
		while (matcher.find()) {
			String word = matcher.group();
		    fileWords.add(word);
		    if (!stopWords.contains(word)) {				
		    	vocabulary.add(new MyWord(word));
			}
		}
		files.add(new MyFile(fileWords));
	}
	
	
}
