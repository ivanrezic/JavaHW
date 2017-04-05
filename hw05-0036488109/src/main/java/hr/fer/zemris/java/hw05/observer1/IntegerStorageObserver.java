package hr.fer.zemris.java.hw05.observer1;

/**
 * An asynchronous update interface for receiving notifications
 * about IntegerStorage information as the IntegerStorage is constructed.
 * 
 * @author Ivan Rezic
 */
public interface IntegerStorageObserver {
	
	/**
	 * This method is called when information about an IntegerStorage
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param istorage the subject of out Observer pattern
	 */
	public void valueChanged(IntegerStorage istorage);
}
