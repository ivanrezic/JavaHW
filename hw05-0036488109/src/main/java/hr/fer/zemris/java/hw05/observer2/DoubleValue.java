package hr.fer.zemris.java.hw05.observer2;

public class DoubleValue implements IntegerStorageObserver {
	
	private int counter;

	public DoubleValue(int counter) {
		this.counter = counter;
	}

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if (counter-- == 0) {
			istorage.getiStorage().removeObserver(this);
			return;
		}
		
		System.out.printf("Double value: %d%n", 2 * istorage.getNewValue());
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
