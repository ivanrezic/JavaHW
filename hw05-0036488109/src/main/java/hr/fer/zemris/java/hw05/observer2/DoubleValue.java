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

}
