package hr.fer.zemris.java.similarity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Console {
	
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("One argument expected; directory path.");
			System.exit(0);
		}
		
		Path dirPath = Paths.get(args[0]);
		if (!Files.isDirectory(dirPath)) {
			throw new IllegalArgumentException("Provided path is not pointing to directory.");
		}
		
		
		
		
		
		
		
		
		
		
		
	}

}
