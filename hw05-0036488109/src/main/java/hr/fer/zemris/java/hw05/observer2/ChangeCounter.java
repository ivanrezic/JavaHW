package hr.fer.zemris.java.hw05.observer2;

public class ChangeCounter implements IntegerStorageObserver {
	
	private int valueChanged;
	
	public ChangeCounter() {
	}

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++valueChanged);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + valueChanged;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ChangeCounter))
			return false;
		ChangeCounter other = (ChangeCounter) obj;
		if (valueChanged != other.valueChanged)
			return false;
		return true;
	}

}
