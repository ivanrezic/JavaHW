package hr.fer.zemris.math;

import static java.lang.Math.*;

import java.util.StringJoiner;

/**
 * A vector is a quantity or phenomenon that has two independent properties:
 * magnitude and direction. The term also denotes the mathematical or
 * geometrical representation of such a quantity. This class enapsulates three
 * dimensional vector and basic operations like:
 * <ul>
 * <li>{@link #norm()}</li>
 * <li>{@link #normalized()}</li>
 * <li>{@link #add(Vector3)}</li>
 * <li>{@link #sub(Vector3)}</li>
 * <li>{@link #dot(Vector3)}</li>
 * <li>{@link #cross(Vector3)}</li>
 * <li>{@link #scale(double)}</li>
 * <li>{@link #cosAngle(Vector3)}</li>
 * </ul>
 *
 * @author Ivan Rezic
 */
public class Vector3 {

	/** X component of vector. */
	private final double x;

	/** Y component of vector. */
	private final double y;

	/** Z component of vector. */
	private final double z;

	/**
	 * Constructor which instantiates new vector.
	 *
	 * @param x
	 *            X component of vector.
	 * @param y
	 *            Y component of vector.
	 * @param z
	 *            Z component of vector.
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculates norm of calling vector.
	 *
	 * @return the double
	 */
	public double norm() {
		return sqrt(x * x + y * y + z * z);
	}

	/**
	 * Normalizes calling vector. Vector whose all three components are zero can
	 * not be normalized.
	 *
	 * @return {@linkplain Vector3}
	 */
	public Vector3 normalized() {
		if (x == 0 && y == 0 && z == 0) {
			throw new IllegalArgumentException("Zero vector could not be normalized.");
		}

		double norm = norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Adds given vector to calling one and returns new vector as result of that
	 * addition.
	 *
	 * @param other
	 *            {@linkplain Vector3} to be added.
	 * @return {@linkplain Vector3} as result of addition.
	 */
	public Vector3 add(Vector3 other) {
		checkVectorValidity(other);
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Subtracts given vector from calling one and returns new vector as result of that
	 * operation.
	 *
	 * @param other
	 *            {@linkplain Vector3} to be subtracted.
	 * @return {@linkplain Vector3} as result of subtraction.
	 */
	public Vector3 sub(Vector3 other) {
		checkVectorValidity(other);
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Calculates scalar product between calling and given vector.
	 *
	 * @param other
	 *            {@linkplain Vector3}
	 * @return {@linkplain Vector3} as result of dot product
	 */
	public double dot(Vector3 other) {
		checkVectorValidity(other);
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Calculates cross product between calling and given vector.
	 *
	 * @param other
	 *            {@linkplain Vector3}
	 * @return {@linkplain Vector3} as result of cross product
	 */
	public Vector3 cross(Vector3 other) {
		checkVectorValidity(other);

		double a = y * other.z - z * other.y;
		double b = z * other.x - x * other.z;
		double c = x * other.y - y * other.x;

		return new Vector3(a, b, c);
	}

	/**
	 * Scales calling vector.
	 *
	 * @param s
	 *            Scaling factor.
	 * @return {@linkplain Vector3} scaled.
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}

	/**
	 * Calculates cosines of angle between two vectors.
	 *
	 * @param other
	 *            {@linkplain Vector3}
	 * @return angle in radians
	 */
	public double cosAngle(Vector3 other) {
		checkVectorValidity(other);
		return dot(other) / (norm() * other.norm());
	}

	/**
	 * Helper method which checks if given vector is null.
	 *
	 * @param other
	 *            {@linkplain Vector3}
	 */
	private void checkVectorValidity(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Given vector can not be null.");
		}
	}

	/**
	 * Method used for getting property <code>X</code>.
	 *
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Method used for getting property <code>Y</code>.
	 *
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Method used for getting property <code>Z</code>.
	 *
	 * @return z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Transforms {@linkplain Vector3} to array.
	 *
	 * @return the double[]
	 */
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
