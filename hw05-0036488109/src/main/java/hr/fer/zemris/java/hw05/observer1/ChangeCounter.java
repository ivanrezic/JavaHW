package hr.fer.zemris.java.hw05.observer1;

public class ChangeCounter implements IntegerStorageObserver {
	
	private int valueChanged;
	
	public ChangeCounter() {
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++valueChanged);
	}

}
