package hr.fer.zemris.math;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * A complex number is a number, but is different from normal numbers in many
 * ways. A complex number is made up using two numbers combined together. The
 * first part is a real number. The second part of a complex number is an
 * imaginary number. This class encapsulates such complex number and provides
 * simple operations like:
 * 
 * <ul>
 * <li>{@link #add(Complex)}</li>
 * <li>{@link #sub(Complex)}</li>
 * <li>{@link #divide(Complex)}</li>
 * <li>{@link #module()}</li>
 * <li>{@link #multiply(Complex)}</li>
 * <li>{@link #negate()}</li>
 * <li>{@link #power(int)}</li>
 * <li>{@link #root(int)}</li>
 * </ul>
 *
 * @author Ivan Rezic
 */
public class Complex {

	/**
	 * Constant which specifies precision used for checking equality of two
	 * doubles.
	 */
	public static final double PRECISION = 0.0001;

	/** Real part of complex number. */
	private final double re;

	/** Imaginary part of complex number. */
	private final double im;

	/** Complex number with zero as real and imaginary part. */
	public static final Complex ZERO = new Complex(0, 0);

	/** Complex number with zero as imaginary part and one as real part. */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Complex number with zero as imaginary part and minus one as real part.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/** Complex number with one as imaginary part and zero as real part. */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * Complex number with minus one as imaginary part and zero as real part.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Constructor which instantiates new complex number.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Constructor which instantiates new complex number.
	 *
	 * @param re
	 *            Real part of complex number.
	 * @param im
	 *            Imaginary part of complex number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Calculates module of complex number.
	 *
	 * @return module
	 */
	public double module() {
		return sqrt(re * re + im * im);
	}

	/**
	 * Multiplies two complex numbers. Given one with calling one.
	 *
	 * @param c
	 *            multiplier
	 * @return result of multiplication
	 */
	public Complex multiply(Complex c) {
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	/**
	 * Divides two complex numbers. Calling one with given one. Given complex
	 * number must not have both real and imaginary part equal to zero.
	 *
	 * @param c
	 *            dividend
	 * @return result of division
	 */
	public Complex divide(Complex c) {
		if (c.re == 0 && c.im == 0) {
			throw new IllegalArgumentException(
					"Can not divide with complex number whose real and imaginary parts are zero.");
		}

		double real = (re * c.re + im * c.im) / (c.re * c.re + c.im * c.im);
		double imaginary = (im * c.re - re * c.im) / (c.re * c.re + c.im * c.im);

		return new Complex(real, imaginary);
	}

	/**
	 * Sums two complex numbers and returns result as new complex number.
	 *
	 * @param c
	 *            to be added
	 * @return result of addition
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * Subtracts given complex number from calling one.
	 *
	 * @param c
	 *            to be subtracted
	 * @return result of subtraction
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * Negates calling complex number.
	 *
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Calculates which complex number is calling one to the power of given
	 * argument.
	 *
	 * @param n
	 *            power
	 * @return power of calling complex number
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Given power should be non-negative integer.");
		}

		double magnitude = Math.pow(module(), n);
		double angle = angle() * n;
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Calculates roots of calling complex number.
	 *
	 * @param n
	 *            Number of roots.
	 * @return list containing all the roots
	 */
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

	/**
	 * Helper method which calculates angle between two complex numbers.
	 *
	 * @return angle in radians
	 */
	public double angle() { // public just for testing purposes
		double angle = atan2(im, re);

		if (angle < 0) {
			return 2 * Math.PI + angle;
		}

		return angle;
	}

	/**
	 * Helper factory method which creates new complex number from given
	 * magnitude and angle.
	 *
	 * @param magnitude
	 *            the magnitude
	 * @param angle
	 *            the angle
	 * @return new complex number
	 */
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
