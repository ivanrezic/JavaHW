package hr.fer.zemris.java.hw05.observer2;

/**
 * <code>ChangeCounter</code> is implementation of observer in 'Observer
 * pattern'. It tracks how many times, subject notified this object.
 *
 * @author Ivan Rezic
 */
public class ChangeCounter implements IntegerStorageObserver {

	/** Property which check how many time value has changed. */
	private int valueChanged;

	/**
	 * Constructor which instantiates new change counter.
	 */
	public ChangeCounter() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Upon calling,this method prints number of changes to standard output.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++valueChanged);
	}

}
