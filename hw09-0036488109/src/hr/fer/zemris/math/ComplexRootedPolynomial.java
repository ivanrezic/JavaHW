package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.StringJoiner;

public class ComplexRootedPolynomial {

	private Complex[] roots;

	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null) {
			throw new IllegalArgumentException("Roots can not be null");
		}
		if (roots.length == 0) {
			throw new IllegalArgumentException("No roots given.");
		}

		this.roots = Arrays.copyOf(roots, roots.length);
	}

	public Complex apply(Complex z) {
		if (z == null) {
			throw new IllegalArgumentException("Given complex number can not be null.");
		}

		Complex result = Complex.ONE;
		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}

		return result;
	}

	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial newPolynomial = new ComplexPolynomial(roots[0].negate(), Complex.ONE);

		for (int i = 1; i < roots.length; i++) {
			newPolynomial = newPolynomial.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}

		return newPolynomial;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("*");

		for (Complex complex : roots) {
			joiner.add("(z" + complex.negate() + ")");
		}

		return joiner.toString();
	}

	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if (z == null) {
			throw new IllegalArgumentException("Given complex number can not be null.");
		}
		if (treshold < 0) {
			throw new IllegalArgumentException("Treshold can not be less than zero.");
		}

		int index = 0;
		double distance = z.sub(roots[0]).module();
		for (int i = 1; i < roots.length; i++) {
			double help = z.sub(roots[i]).module();

			if (help < distance) {
				distance = help;
				index = i;
			}
		}

		return distance <= treshold ? index + 1 : -1;
	}
}
