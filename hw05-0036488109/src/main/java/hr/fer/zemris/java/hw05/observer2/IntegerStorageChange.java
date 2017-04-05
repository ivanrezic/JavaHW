package hr.fer.zemris.java.hw05.observer2;

/**
 * <code>IntegerStorageChange</code> represents passing object between subject
 * and observers in 'Observer pattern'. It stores subject itself with its old
 * and new value.
 *
 * @author Ivan Rezic
 */
public class IntegerStorageChange {

	/** Reference to IntegerStorage instance */
	private IntegerStorage iStorage;

	/** Old subject value. */
	private int oldValue;

	/** New subject value. */
	private int newValue;

	/**
	 * Constructor which instantiates new integer storage change.
	 *
	 * @param iStorage
	 *            subject from observer pattern
	 * @param oldValue
	 *            old subject value
	 * @param newValue
	 *            new subject value
	 */
	public IntegerStorageChange(IntegerStorage iStorage, int oldValue, int newValue) {
		this.iStorage = iStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Method used for getting property <code>iStorage</code>.
	 *
	 * @return IntegerStorage
	 */
	public IntegerStorage getiStorage() {
		return iStorage;
	}

	/**
	 * Method used for getting property <code>oldValue</code>.
	 *
	 * @return old value
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Method used for getting property <code>newValue</code>.
	 *
	 * @return new value
	 */
	public int getNewValue() {
		return newValue;
	}

}
