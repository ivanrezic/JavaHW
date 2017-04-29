package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.utils.Util;

/**
 * <code>Mask</code> encapsulates all product specifications. Each mask could be
 * combined with other mask.
 *
 * @author Ivan Rezic
 */
public class Mask {

	/** Values. */
	private byte[] values;

	/** Indexes. */
	private final Set<Integer> indexes;

	/** Dont cares. */
	private boolean dontCare;

	/** Is mask combined. */
	private boolean combined;

	/** Mask hash code. */
	private int hashCode;

	/**
	 * Constructor which instantiates new mask.
	 *
	 * @param index
	 *            the index
	 * @param numberOfVariables
	 *            the number of variables
	 * @param dontCare
	 *            the dont care
	 */
	public Mask(int index, int numberOfVariables, boolean dontCare) {
		this(Util.indexToByteArray(index, numberOfVariables), new TreeSet<>(Arrays.asList(index)), dontCare);
	}

	/**
	 * Constructor which instantiates new mask.
	 *
	 * @param values
	 *            the values
	 * @param indexes
	 *            the indexes
	 * @param dontCare
	 *            the dont care
	 */
	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		if (values == null || indexes == null || indexes.size() == 0) {
			throw new IllegalArgumentException("Invalid arguments given.");
		}

		this.values = values;
		this.indexes = indexes;
		this.dontCare = dontCare;

		this.hashCode = Arrays.hashCode(values);
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Mask))
			return false;
		Mask other = (Mask) obj;
		if (hashCode != other.hashCode)
			return false;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}

	/**
	 * Checks if is combined.
	 *
	 * @return true, if it is combined, false otherwise
	 */
	public boolean isCombined() {
		return combined;
	}

	/**
	 * Method which sets new value as combined.
	 *
	 * @param combined
	 *            the new combined
	 */
	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	/**
	 * Checks if is don't care.
	 *
	 * @return true, if it is dontcare, false otherwise
	 */
	public boolean isDontCare() {
		return dontCare;
	}

	/**
	 * Method used for getting property <code>indexes</code>.
	 *
	 * @return indexes
	 */
	public Set<Integer> getIndexes() {
		return indexes;
	}

	/**
	 * Returns size of values byte array stored.
	 *
	 * @return the size
	 */
	public int size() {
		return values.length;
	}

	/**
	 * Method used for getting byte value at given position.
	 *
	 * @param position
	 *            the position
	 * @return value
	 */
	public byte getValueAt(int position) {
		if (position < 0 || position >= size()) {
			throw new IllegalArgumentException("Given position out of bounds.");
		}

		return values[position];
	}

	/**
	 * Method which counts byte values with value equals one.
	 *
	 * @return number of ones
	 */
	public int countOfOnes() {
		int sum = 0;

		for (int i = 0; i < values.length; i++) {
			if (values[i] == 1) {
				sum++;
			}
		}

		return sum;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < values.length; i++) {
			if (values[i] == 2) {
				stringBuilder.append('-');
			} else {
				stringBuilder.append(values[i]);
			}
		}

		if (isDontCare()) {
			stringBuilder.append(" D");
		} else {
			stringBuilder.append(" .");
		}

		if (isCombined()) {
			stringBuilder.append(" * ");
		} else {
			stringBuilder.append("   ");
		}

		stringBuilder.append(indexes.toString());
		return stringBuilder.toString();
	}

	/**
	 * Method which combines two masks if they have corresponding values and size.
	 *
	 * @param other
	 *            mask to be combined with
	 * @return empty if not combined, new mask if combined
	 */
	public Optional<Mask> combineWith(Mask other) {
		if (other == null || values.length != other.values.length) {
			throw new IllegalArgumentException("Second mask is null or does not have correct values length.");
		}

		if (!isCombinable(other.values)) {
			return Optional.empty();
		}

		byte[] help = Arrays.copyOf(values, values.length);
		for (int i = 0; i < values.length; i++) {
			if (values[i] != other.values[i]) {
				help[i] = 2;
				break;
			}
		}

		Set<Integer> newSet = new HashSet<>();
		newSet.addAll(indexes);
		newSet.addAll(other.indexes);

		return Optional.of(new Mask(help, newSet, dontCare && other.dontCare));
	}

	/**
	 * Checks if masks are combinable.
	 *
	 * @param values2
	 *            the values 2
	 * @return true, if it is combinable, false otherwise
	 */
	private boolean isCombinable(byte[] values2) {
		boolean flag = true;
		int count = 0;

		for (int i = 0; i < values.length; i++) {
			if (values[i] == 2) {
				if (values2[i] != 2) {
					flag = false;
					break;
				}
			} else if (values[i] != values2[i]) {
				count++;
			}
		}

		if (count > 1) {
			return false;
		}

		return flag;
	}
}
