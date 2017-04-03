package hr.fer.zemris.java.hw05.observer1;

import java.util.ArrayList;
import java.util.List;

public class IntegerStorage {
	private int value;
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!
	private List<IntegerStorageObserver> observersSafe;

	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new ArrayList<>();
		}
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void removeObserver(IntegerStorageObserver observer) {
		if (observers != null) {
			observers.remove(observer);
		}
	}

	public void clearObservers() {
		if (observers != null) {
			observers.clear();
		}
	}

	public int getValue() {
		return value;
	}

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