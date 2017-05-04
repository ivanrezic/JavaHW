package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * <code>ComplexRootedPolynomial</code> class represents polynomial with complex
 * number roots. Polynomial has form of: <i>f(z) = (z-z1)*(z-z2)*...*(z-zn)</i>
 * in which z1 to zn are polynomial roots.
 *
 * @author Ivan Rezic
 */
public class ComplexRootedPolynomial {

	/** Polynomial roots. */
	private Complex[] roots;

	/**
	 * Constructor which instantiates new complex rooted polynomial.
	 *
	 * @param roots
	 *            the roots
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null) {
			throw new IllegalArgumentException("Roots can not be null");
		}
		if (roots.length == 0) {
			throw new IllegalArgumentException("No roots given.");
		}

		this.roots = Arrays.copyOf(roots, roots.length);
	}

	/**
	 * Applies given complex number as x to the function.
	 *
	 * @param z
	 *            Complex number to be applied.
	 * @return Complex number as result.
	 * @throws IllegalArgumentException
	 *             if given argument is null.
	 */
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

	/**
	 * Converts this representation to {@linkplain ComplexPolynomial} type.
	 *
	 * @return the complex polynomial
	 */
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

	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1
	 *
	 * @param z
	 *            complex number to be checked
	 * @param treshold
	 *            the treshold
	 * @return result
	 */
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

		return distance > treshold ? -1 : index + 1;
	}
}
