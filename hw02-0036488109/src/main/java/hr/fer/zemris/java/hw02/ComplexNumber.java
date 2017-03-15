package hr.fer.zemris.java.hw02;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumber {
	public static final double PRECISION = 0.0001;

	private final double real;
	private final double imaginary;

	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imaginary;
	}

	public double getMagnitude() {
		return sqrt(real * real + imaginary * imaginary);
	}

	public double getAngle() {
		double angle = atan2(imaginary, real);

		if (angle < 0) {
			return 2 * Math.PI + angle;
		}

		return angle;
	}

	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	public static ComplexNumber parse(String s) {
		String regexReal = "-?(?>\\d*\\.?\\d+)(?!i)";
		String regexImaginary = "-?\\d*(\\.\\d+)?(?=i)";

		return new ComplexNumber(regexMatcher(s, regexReal), regexMatcher(s, regexImaginary));
	}

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

	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
	}

	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}

	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}

		double newReal = (real * c.getReal() - imaginary * c.getImaginary());
		double newImaginary = (imaginary * c.getReal() + real * c.getImaginary());

		return new ComplexNumber(newReal, newImaginary);
	}

	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}

		double nominatorReal = (real * c.getReal() + imaginary * c.getImaginary());
		double nominatorImaginary = (imaginary * c.getReal() - real * c.getImaginary());
		double denominator = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();

		return new ComplexNumber(nominatorReal / denominator, nominatorImaginary / denominator);
	}

	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power must be greater or equal to 0");
		}
		double magnitude = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		return fromMagnitudeAndAngle(magnitude, angle);
	}

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
