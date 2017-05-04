package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.StringJoiner;

public class ComplexPolynomial {

	private Complex[] factors;

	public ComplexPolynomial(Complex... factors) {
		if (factors == null) {
			throw new IllegalArgumentException("Given factors can not be null.");
		}

		this.factors = Arrays.copyOf(factors, factors.length);
	}

	public short order() {
		return (short) (factors.length - 1);
	}

	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null) {
			throw new IllegalArgumentException("Given polynomial can not be null.");
		}

		int size = order() + p.order() + 1;
		Complex[] newFactors = new Complex[size];
		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = Complex.ZERO;
		}
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[order()];

		int size = order();
		while (size > 0) {
			newFactors[size - 1] = factors[size].multiply(new Complex(size, 0));
			size--;
		}

		return new ComplexPolynomial(newFactors);
	}

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
