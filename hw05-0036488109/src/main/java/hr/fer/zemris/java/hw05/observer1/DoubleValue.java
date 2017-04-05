package hr.fer.zemris.java.hw05.observer1;

/**
 * <code>DoubleValue</code> is implementation of observer in 'Observer pattern'.
 * It prints doubled subject value.
 *
 * @author Ivan Rezic
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Private property which specifies how long this object "lives". If it
	 * reaches zero, this object is dereferenced.
	 */
	private int counter;

	/**
	 * Constructor which instantiates new double value.
	 *
	 * @param counter
	 *            the counter
	 */
	public DoubleValue(int counter) {
		this.counter = counter;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Upon calling, this method prints doubled subject value.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (counter-- == 0) {
			istorage.removeObserver(this);
			return;
		}

		System.out.printf("Double value: %d%n", 2 * istorage.getValue());
	}

}
