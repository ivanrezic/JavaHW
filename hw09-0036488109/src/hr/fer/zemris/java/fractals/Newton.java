package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	private static final Pattern NORMAL = Pattern.compile("\\s*(-?.+)\\s*([+|-]\\s*i.*)");

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner scanner = new Scanner(System.in);
		List<Complex> list = new ArrayList<>();

		int i = 1;
		while (true) {
			System.out.printf("Root %d>", i++);

			String line = scanner.nextLine();
			if (line.equals("done"))
				break;
			try {
				list.add(regexMatcher(line));
			} catch (NumberFormatException e) {
				System.out.println("Invalid complex number given.");
				i--;
			}
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		scanner.close();

		ComplexRootedPolynomial roots = new ComplexRootedPolynomial(list.toArray(new Complex[list.size()]));
		ComplexPolynomial poly = roots.toComplexPolynom();
		FractalViewer.show(new FractalProducer(poly, roots));
	}

	private static Complex regexMatcher(String line) throws NumberFormatException {
		Matcher normal = NORMAL.matcher(line);

		double real = 0;
		double imaginary = 0;

		if (normal.matches()) {
			real = Double.parseDouble(normal.group(1));
			imaginary = extractImaginary(normal.group(2));
		} else if (line.contains("i")) {
			imaginary = extractImaginary(line);
		} else {
			real = Double.parseDouble(line);
		}

		return new Complex(real, imaginary);
	}

	private static double extractImaginary(String string) throws NumberFormatException {
		string = string.replaceAll("\\s*", "").replace("+", "");
		
		if (string.equals("i")) {
			return 1;
		}
		if (string.equals("-i")) {
			return -1;
		}

		return Double.parseDouble(string.replace("i", ""));
	}

}
