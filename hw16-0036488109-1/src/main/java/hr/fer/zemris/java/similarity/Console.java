package hr.fer.zemris.java.similarity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * <code>Console</code> is demonstration class which provides, search engine simulation.
 *
 * @author Ivan Rezic
 */
public class Console {
	
	/** Data processing object. */
	private static Processor processor;
	
	/** Data vocabulary. */
	private static List<MyWord> vocabulary;
	
	/** Query result. */
	private static List<Pair> result;
	
	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args the arguments from command line, articles path demanded
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("One argument expected; directory path.");
			System.exit(0);
		}
		
		Path dirPath = Paths.get(args[0]);
		if (!Files.isDirectory(dirPath)) {
			throw new IllegalArgumentException("Provided path is not pointing to directory.");
		}
		
		processor = new Processor(dirPath);
		vocabulary = processor.getVocabulary();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Vocabulary size is: " + vocabulary.size());
		while (true) {
			System.out.print("Enter command > ");
			String line = scanner.nextLine();
			
			try {
				if (line.matches("query .+")) {
					executeQuery(line);
				}else if (line.matches("type [0-9]")) {
					executeType(line);
				}else if (line.startsWith("results")) {
					printResults();
				}else if (line.equals("exit")) {
					break;
				}else {
					System.out.println("Invalid command, please try again.");
				}
			} catch (RuntimeException e) {
				System.out.println("Query first!");
			}
		}
		scanner.close();
	}

	/**
	 * Executes query end retrives top 10 similarity results.
	 *
	 * @param line the line
	 */
	private static void executeQuery(String line) {
		List<String> query = new ArrayList<>(Arrays.asList(line.split(" ",2)[1].split(" ")));
		System.out.println("Query is: " + query);
		
		double[] vector = new double[vocabulary.size()];
		for (int i = 0; i < vector.length; i++) {
			MyWord word = vocabulary.get(i);
			vector[i] = Collections.frequency(query, word.getText()) * word.getIDF() ;
		}
		
		result = new ArrayList<>();
		List<MyFile> files = processor.getFiles();
		for (MyFile myFile : files) {
			result.add(new Pair(myFile.calculateVectorSimilarity(vector), myFile));
		}
		
		result = result.stream()
				.sorted((a,b) -> -Double.compare(a.similarity, b.similarity))
				.filter(a -> a.similarity > 0)
				.limit(10)
				.collect(Collectors.toList());
		
		System.out.println("Top 10 results:");
		printResults();
	}

	/**
	 * Prints the results.
	 */
	private static void printResults() {
		for (int i = 0; i < result.size(); i++) {
			Path filePath = result.get(i).file.getFilePath();
			System.out.printf("[%d] (%.4f) %s%n", i,result.get(i).similarity,filePath);
		}
	}

	/**
	 * Execute type command.
	 *
	 * @param line the line
	 */
	private static void executeType(String line) {
		int index = Integer.valueOf(line.split(" ")[1]);
		Path filePath = result.get(index).file.getFilePath();
		
		System.out.println("------------------------------------------");
		System.out.println("Document: " + filePath);
		System.out.println("------------------------------------------");
		
		try {
			List<String> lines = Files.readAllLines(filePath);
			for (String string : lines) {
				System.out.println(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <code>Pair</code> is class which encapsulates article with its similarity
	 * to provided query.
	 *
	 * @author Ivan Rezic
	 */
	private static class Pair{
		
		/** similarity. */
		private double similarity;
		
		/** file. */
		private MyFile file;
		
		/**
		 * Constructor which instantiates new pair.
		 *
		 * @param similarity the similarity
		 * @param file the file
		 */
		public Pair(double similarity, MyFile file) {
			this.similarity = similarity;
			this.file = file;
		}
	}
}
