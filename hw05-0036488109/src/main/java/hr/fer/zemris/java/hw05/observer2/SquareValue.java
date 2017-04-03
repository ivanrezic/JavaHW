package hr.fer.zemris.java.hw05.observer2;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
