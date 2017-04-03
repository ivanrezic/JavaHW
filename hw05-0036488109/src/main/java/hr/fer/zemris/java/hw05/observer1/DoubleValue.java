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

}
