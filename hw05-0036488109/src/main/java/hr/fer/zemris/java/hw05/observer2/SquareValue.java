package hr.fer.zemris.java.hw05.observer2;

/**
 * Instance of type <code>Class SquareValue</code>.
 *
 * @author Ivan Rezic
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * {@inheritDoc}
	 * Upon calling this method prints squared subject value to standard output.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
