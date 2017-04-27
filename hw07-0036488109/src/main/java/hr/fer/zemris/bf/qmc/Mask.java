package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.utils.Util;

public class Mask {

	private byte[] values;
	private final Set<Integer> indexes;
	private boolean dontCare;

	private boolean combined;

	private int hashCode;

	// public Mask(int index, int numberOfVariables, boolean dontCare) {
	// if (numberOfVariables < 1 || index < 0 || index >= Math.pow(2,
	// numberOfVariables)) {
	// throw new IllegalArgumentException("Invalid arguments given.");
	// }
	//
	// this.values = Util.indexToByteArray(index, numberOfVariables);
	// this.indexes = new TreeSet<>(Arrays.asList(index));
	// this.dontCare = dontCare;
	//
	// }

	public Mask(int index, int numberOfVariables, boolean dontCare) {
		this(Util.indexToByteArray(index, numberOfVariables), new TreeSet<>(Arrays.asList(index)), dontCare);
	}

	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		if (values == null || indexes == null || indexes.size() == 0) {
			throw new IllegalArgumentException("Invalid arguments given.");
		}

		this.values = values;
		this.indexes = indexes;
		this.dontCare = dontCare;

		this.hashCode = Arrays.hashCode(values); // mozda treba dodati jos nesto
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {// mozda nije ovako
		if (obj instanceof Mask) {
			Mask help = (Mask) obj;
			if (this.hashCode != obj.hashCode()) {
				if (Arrays.equals(this.values, help.values)) {
					return true;
				}
				return false;
			}
		}

		return false;
	}

	public boolean isCombined() {
		return combined;
	}

	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	public boolean isDontCare() {
		return dontCare;
	}

	public Set<Integer> getIndexes() {
		return indexes;
	}

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

	public Optional<Mask> combineWith(Mask other) {
		if (other == null || values.length != other.values.length) {
			throw new IllegalArgumentException("Second mask is null or does not have correct values length.");
		}

		if (!isCombinable(other.values)) {
			return Optional.empty();
		}

		byte[] help = values;
		for (int i = 0; i < values.length; i++) {
			if (values[i] != other.values[i]) {
				help[i] = 2;
				break;
			}
		}

		
		setCombined(true);
		other.setCombined(true);
		Set<Integer> newSet = indexes;
		newSet.addAll(other.indexes);
		
		return Optional.of(new Mask(help, newSet, dontCare == other.dontCare));
	}

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

	public static void main(String[] args) {
		Set<Integer> set1 = new TreeSet<>();
		set1.add(13);
		set1.add(6);
		Set<Integer> set2 = new TreeSet<>();
		set2.add(12);
		set2.add(8);
		Mask asd1 = new Mask(new byte[] { 2, 1, 1, 0 }, set1, true);
		Mask asd2 = new Mask(new byte[] { 2, 1, 0, 0 }, set2, true);
		System.out.println(asd1);
		System.out.println(asd2);
		System.out.println();
		System.out.println(asd2.combineWith(asd1));

	}

}
