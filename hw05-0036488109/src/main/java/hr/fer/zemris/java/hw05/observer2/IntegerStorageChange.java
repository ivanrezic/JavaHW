package hr.fer.zemris.java.hw05.observer2;

public class IntegerStorageChange {
	private IntegerStorage iStorage;
	private int oldValue;
	private int newValue;

	public IntegerStorageChange(IntegerStorage iStorage, int oldValue, int newValue) {
		this.iStorage = iStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public IntegerStorage getiStorage() {
		return iStorage;
	}

	public int getOldValue() {
		return oldValue;
	}

	public int getNewValue() {
		return newValue;
	}

}
