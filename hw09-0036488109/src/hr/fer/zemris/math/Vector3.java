package hr.fer.zemris.math;

import static java.lang.Math.*;

import java.util.StringJoiner;

public class Vector3 {

	private final double x;
	private final double y;
	private final double z;

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double norm() {
		return sqrt(x * x + y * y + z * z);
	}

	public Vector3 normalized() {
		if (x == 0 && y == 0 && z == 0) {
			throw new IllegalArgumentException("Zero vector could not be normalized.");
		}

		double norm = norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	public Vector3 add(Vector3 other) {
		checkVectorValidity(other);
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	public Vector3 sub(Vector3 other) {
		checkVectorValidity(other);
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	public double dot(Vector3 other) {
		checkVectorValidity(other);
		return x * other.x + y * other.y + z * other.z;
	}

	public Vector3 cross(Vector3 other) {
		checkVectorValidity(other);

		double a = y * other.z - z * other.y;
		double b = z * other.x - x * other.z;
		double c = x * other.y - y * other.x;

		return new Vector3(a, b, c);
	}

	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}

	public double cosAngle(Vector3 other) {
		checkVectorValidity(other);
		return dot(other) / (norm() * other.norm());
	}

	private void checkVectorValidity(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Given vector can not be null.");
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector3))
			return false;
		Vector3 other = (Vector3) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "(", ")");

		joiner.add(String.format("%f", x));
		joiner.add(String.format("%f", y));
		joiner.add(String.format("%f", z));

		return joiner.toString();
	}
}
