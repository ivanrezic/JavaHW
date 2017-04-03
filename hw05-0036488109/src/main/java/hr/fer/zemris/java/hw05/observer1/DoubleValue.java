package hr.fer.zemris.java.hw05.observer1;

public class DoubleValue implements IntegerStorageObserver {
	
	private int counter;

	public DoubleValue(int counter) {
		this.counter = counter;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (counter-- == 0) {
			istorage.removeObserver(this);
			return;
		}
		
		System.out.printf("Double value: %d%n", 2 * istorage.getValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + counter;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DoubleValue))
			return false;
		DoubleValue other = (DoubleValue) obj;
		if (counter != other.counter)
			return false;
		return true;
	}

}
