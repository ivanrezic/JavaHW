package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * <code>ComplexPolynomial</code> represents complex numbers polynomial. This
 * polynomial has next form: <i>f(z) = zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0</i>
 * where z0 to zn are coefficients standing to paired powers of z. This is n-th
 * degree polynomial which we usualy call polinom order. Each coefficient is
 * complex nubaer just like z.
 *
 * @author Ivan Rezic
 */
public class ComplexPolynomial {

	/** Polynomial factors. */
	private Complex[] factors;

	/**
	 * Constructor which instantiates new complex polynomial.
	 *
	 * @param factors
	 *            the factors
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors == null) {
			throw new IllegalArgumentException("Given factors can not be null.");
		}

		this.factors = Arrays.copyOf(factors, factors.length);
	}

	/**
	 * Returns order of polynomial.
	 *
	 * @return order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Multiplies two polynomials.
	 *
	 * @param p
	 *            Polynomial to be multiplied with.
	 * @return Result of multiplication.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null) {
			throw new IllegalArgumentException("Given polynomial can not be null.");
		}

		Complex[] newFactors = new Complex[order() + p.order() + 1];
		initializeArray(newFactors);
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Helper method which initializes given array to zero at each position.
	 *
	 * @param newFactors
	 *            Array which will be initialized.
	 */
	private void initializeArray(Complex[] newFactors) {
		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = Complex.ZERO;
		}
	}

	/**
	 * Derives calling polynomial.
	 *
	 * @return Derived complex polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[order()];

		int size = order();
		while (size > 0) {
			newFactors[size - 1] = factors[size].multiply(new Complex(size, 0));
			size--;
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes polynomial value at given point z.
	 *
	 * @param z
	 *            Wanted point.
	 * @return {@linkplain Complex}
	 */
	public Complex apply(Complex z) {
		if (z == null) {
			throw new IllegalArgumentException("Given complex number can not be null.");
		}

		Complex newComplex = factors[0];
		for (int i = 1; i < factors.length; i++) {
			newComplex = newComplex.add(factors[i].multiply(z.power(i)));
		}

		return newComplex;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(" + ");

		for (int i = factors.length - 1; i > 0; i--) {
			joiner.add(factors[i].toString() + "z^" + i);
		}
		joiner.add(factors[0].toString());

		return joiner.toString();
	}
}
