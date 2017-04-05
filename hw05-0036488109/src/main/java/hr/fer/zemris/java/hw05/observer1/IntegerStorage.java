package hr.fer.zemris.java.hw05.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>IntegerStorage</code> is class which represents subject in 'Observer
 * pattern'.
 *
 * @author Ivan Rezic
 */
public class IntegerStorage {

	/** Value, stored by subject {@link #IntegerStorage(int)}. */
	private int value;

	/** List of observers. */
	private List<IntegerStorageObserver> observers;

	/** Helper list which contains observers, used in foreach for safe remova. */
	private List<IntegerStorageObserver> observersSafe;

	/**
	 * Constructor which instantiates new integer storage.
	 *
	 * @param initialValue
	 *            the initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds the observer.
	 *
	 * @param observer
	 *            instance of class which implements <code>IntegerStorageObserver</code>
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new ArrayList<>();
		}
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes the observer.
	 *
	 * @param observer
	 *            the observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers != null) {
			observers.remove(observer);
		}
	}

	/**
	 * Clear observers list.
	 */
	public void clearObservers() {
		if (observers != null) {
			observers.clear();
		}
	}

	/**
	 * Method used for getting property <code>value</code>.
	 *
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Method which sets new value to value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				observersSafe = new ArrayList<>(observers);
				for (IntegerStorageObserver observer : observersSafe) {
					observer.valueChanged(this);
				}
			}
		}
	}
}