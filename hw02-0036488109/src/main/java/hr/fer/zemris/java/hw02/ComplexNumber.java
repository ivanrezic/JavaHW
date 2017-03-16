package hr.fer.zemris.java.hw02;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ComplexNumber class represents unmodifiable instance of complex number. This
 * class contains methods getter methods and simple calculation methods.
 * 
 * @author Ivan
 */
public class ComplexNumber {

	/**
	 * The Constant PRECISION which is used as acceptable difference between two
	 * doubles.
	 */
	public static final double PRECISION = 0.0001;

	/** The real part of complex number. */
	private final double real;

	/** The imaginary part of complex number. */
	private final double imaginary;

	/**
	 * Constructor which instantiates a new complex number from given real and
	 * imaginary part.
	 *
	 * @param real
	 *            the real
	 * @param imaginary
	 *            the imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Getter for real part of the complex number.
	 * 
	 * @return Real part of this complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter for imaginary part of the complex number.
	 * 
	 * @return Real part of this complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Getter for magnitude of this complex number.
	 * 
	 * @return Magnitude of this complex number.
	 */
	public double getMagnitude() {
		return sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Getter for angle of this complex number.
	 * 
	 * @return Angle of this complex number.
	 */
	public double getAngle() {
		double angle = atan2(imaginary, real);

		if (angle < 0) {
			return 2 * Math.PI + angle;
		}

		return angle;
	}

	/**
	 * Returns a new ComplexNumber just from given real part of the complex
	 * number.
	 * 
	 * @param real
	 *            Real part of the complex number.
	 * @return ComplexNumber with specified real part and 0 as imaginary part
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Returns a new ComplexNumber just from given imaginary part of the complex
	 * number.
	 * 
	 * @param imaginary
	 *            Imaginary part of the complex number.
	 * @return ComplexNumber with specified imaginary part and 0 as real part
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Returns a new ComplexNumber from given magnitude and angle of this
	 * complex number.
	 * 
	 * @param magnitude
	 *            the magnitude of this complex number
	 * @param angle
	 *            the angle of this complex number
	 * @return the complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	/**
	 * Public static factory method of ComplexNumbers. Method parses given
	 * string input into acceptable complex numbers arguments.
	 *
	 * @param s
	 *            string representation of complex number
	 * @return the complex number
	 */
	public static ComplexNumber parse(String s) {
		String regexReal = "-?(?>\\d*\\.?\\d+)(?!i)";
		String regexImaginary = "-?\\d*(\\.\\d+)?(?=i)";

		return new ComplexNumber(regexMatcher(s, regexReal), regexMatcher(s, regexImaginary));
	}

	/**
	 * Regex matcher is helper method which helps in parsing string.
	 *
	 * @param s
	 *            the string representation of complex number
	 * @param regex
	 *            the regex which will be used as pattern for parsing
	 * @return the double which represents real or imaginary part of complex
	 *         number
	 */
	private static double regexMatcher(String s, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s.replaceAll("\\s+", ""));

		if (!matcher.find()) {
			return 0;
		} else if (matcher.group().equals("")) {
			return 1;
		} else if (matcher.group().equals("-")) {
			return -1;
		}

		return Double.parseDouble(matcher.group());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (real == 0 && imaginary != 0) {
			return imaginary + "i";
		} else if (real != 0 && imaginary == 0) {
			return real + "";
		} else if (real == 0 && imaginary == 0) {
			return "0";
		} else if (imaginary < 0) {
			return real + "" + imaginary + "i";
		} else
			return real + "" + "+" + imaginary + "i";
	}

	/**
	 * Method which adds given complex number as argument to calling complex
	 * number.
	 *
	 * @param c
	 *            added complex number
	 * @return result of addition
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
	}

	/**
	 * Method which subtracts given complex number as argument to calling
	 * complex number.
	 *
	 * @param c
	 *            complex number
	 * @return result of substraction
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}

	/**
	 * Method which multiplies given complex number to calling complex number.
	 *
	 * @param c
	 *            multiplicated complex number
	 * @return result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}

		double newReal = (real * c.getReal() - imaginary * c.getImaginary());
		double newImaginary = (imaginary * c.getReal() + real * c.getImaginary());

		return new ComplexNumber(newReal, newImaginary);
	}

	/**
	 * Method which divides given complex number to calling complex number.
	 *
	 * @param c
	 *            divided complex number
	 * @return result of multiplication
	 * 
	 * @throws NullPointerException if divisor is 0
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}

		double nominatorReal = (real * c.getReal() + imaginary * c.getImaginary());
		double nominatorImaginary = (imaginary * c.getReal() - real * c.getImaginary());
		double denominator = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();

		if (denominator == 0) {
			throw new ArithmeticException("Nedozvoljeno djeljenje s 0");
		}

		return new ComplexNumber(nominatorReal / denominator, nominatorImaginary / denominator);
	}

	/**
	 * Calculates the power of calling ComplexNumber to the power of argument.
	 * 
	 * @param n
	 *            The power we wish to calculate.
	 * @return a <code>ComplexNumber</code> result of powering
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power must be greater or equal to 0");
		}
		double magnitude = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Computes the n roots of this complex number.
	 *
	 * @param n
	 *            Degree of root.
	 * @return a List of all n-th roots of this calling complex number.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("You requested a negative or 0 root. Root must be positive");
		}
		double rootAngle = getAngle() / n;
		double rootMagnitude = Math.pow(getMagnitude(), 1. / n);
		ComplexNumber[] roots = new ComplexNumber[n];

		for (int i = 0; i < n; i++) {
			roots[i] = fromMagnitudeAndAngle(rootMagnitude, rootAngle);
			rootAngle += 2 * Math.PI / n;
		}

		return roots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Math.abs(imaginary - other.imaginary) > PRECISION)
			return false;
		if (Math.abs(real - other.real) > PRECISION)
			return false;
		return true;
	}

}
