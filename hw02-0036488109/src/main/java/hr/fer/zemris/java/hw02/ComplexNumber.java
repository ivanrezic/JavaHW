package hr.fer.zemris.java.hw02;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumber {
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
		String regexImaginary = "-?\\d*\\.?\\d+(?=i)";

		return new ComplexNumber(regexMatcher(s, regexReal), regexMatcher(s, regexImaginary));
	}

	private static double regexMatcher(String s, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s.replaceAll("\\s+", ""));

		if (!matcher.find()) {
			return 0;
		}

		return Double.parseDouble(matcher.group());

	}

	@Override
	public String toString() {
		return "real=" + real + ", imaginary=" + imaginary;
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

		return mulDivHelper(c, "mul");
	}

	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}

		return mulDivHelper(c, "div");
	}

	private ComplexNumber mulDivHelper(ComplexNumber c, String selected) {
		double newAngle = 0.0;
		double newMagnitude = 0;

		if (selected.equals("mul")) {
			newMagnitude = this.getMagnitude() * c.getMagnitude();
			newAngle = this.getAngle() + c.getAngle();
		} else if (selected.equals("div")) {
			newMagnitude = this.getMagnitude() / c.getMagnitude();
			newAngle = this.getAngle() - c.getAngle();
		}

		double real = newMagnitude * cos(newAngle);
		double imaginary = newMagnitude * sin(newAngle);

		return new ComplexNumber(real, imaginary);
	}

	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
					"Power must be greater or equal to 0");
		}
		double magnitude = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		return fromMagnitudeAndAngle(magnitude, angle);
	}
	
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(
					"You requested a negative or 0 root. Root must be positive");
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
}
