package hr.fer.zemris.math;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Complex {
	public static final double PRECISION = 0.0001;

	private final double re;
	private final double im;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	public Complex() { // mozda nije ovako
		this(0, 0);
	}

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public double module() {
		return sqrt(re * re + im * im);
	}

	public Complex multiply(Complex c) {
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	public Complex divide(Complex c) {
		if (c.re == 0 && c.im == 0) {
			throw new IllegalArgumentException(
					"Can not divide with complex number whose real and imaginary parts are zero.");
		}

		double real = (re * c.re + im * c.im) / (c.re * c.re + c.im * c.im);
		double imaginary = (im * c.re - re * c.im) / (c.re * c.re + c.im * c.im);

		return new Complex(real, imaginary);
	}

	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}

	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	public Complex negate() {
		return new Complex(-re, -im);
	}

	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Given power should be non-negative integer.");
		}

		double magnitude = Math.pow(module(), n);
		double angle = angle() * n;
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Root factor must be a positive integer.");
		}
		double rootAngle = angle() / n;
		double rootMagnitude = Math.pow(module(), 1. / n);

		List<Complex> roots = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			roots.add(fromMagnitudeAndAngle(rootMagnitude, rootAngle));
			rootAngle += 2 * Math.PI / n;
		}

		return roots;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "(", ")");

		joiner.add(String.format("%f", re));
		joiner.add(String.format("i%f", im));

		return joiner.toString();
	}

	public double angle() { // public just for testing purposes
		double angle = atan2(im, re);

		if (angle < 0) {// mozda ne treba
			return 2 * Math.PI + angle;
		}

		return angle;
	}

	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {/*-public just for testing purposes*/
		return new Complex(magnitude * cos(angle), magnitude * sin(angle));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		if (Math.abs(im - other.im) > PRECISION)
			return false;
		if (Math.abs(re - other.re) > PRECISION)
			return false;
		return true;
	}

}
