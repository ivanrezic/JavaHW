package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * <code>PrimListModel</code> is implemetation of list model and provdes model
 * for list that generates prime numbers with each click on button.
 *
 * @author Ivan Rezic
 */
public class PrimListModel implements ListModel<Integer> {

	/** List of prime numbers. */
	private List<Integer> primes = new ArrayList<>();

	/** First prime number generated. */
	private int prime = 2;

	/** List of observers. */
	private List<ListDataListener> observers = new ArrayList<>();

	@Override
	public void addListDataListener(ListDataListener l) {
		observers.add(l);
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		observers.remove(l);
	}

	/**
	 * Adds the element in primes collection.
	 *
	 * @param element
	 *            the element
	 */
	public void addElement(Integer element) {
		primes.add(element);
	}

	/**
	 * Generates next prime number.
	 */
	public void next() {
		int position = primes.size();

		for (int i = 2; i <= prime / 2; i++) {
			if (prime % i == 0) {
				prime++;
				i = 2;
			}
		}
		primes.add(prime++);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
		for (ListDataListener l : observers) {
			l.intervalAdded(event);
		}
	}
}
