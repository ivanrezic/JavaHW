package hr.fer.zemris.java.custom.collections;

/**
 * The Class Collection represents general collections object which includes:
 * <ul>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #addAll(Collection)}</li>
 * <li>{@link #clear()}</li>
 * <li>{@link #contains(Object)}</li>
 * <li>{@link #equals(Object)}</li>
 * <li>{@link #forEach(Processor)}</li>
 * <li>{@link #remove(Object)}</li>
 * <li>{@link #size()}</li>
 * <li>{@link #toArray()}</li>
 * </ul>
 * 
 * @author Ivan
 */
public class Collection {

	/**
	 * Protected constructor which instances new collection.
	 */
	protected Collection() {
	}

	/**
	 * Method checks if calling collection is empty.
	 *
	 * @return <code>true</code> if it is empty <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return this.size() == 0 ? true : false;
	}

	/**
	 * Method which checks number of objects in calling collection. Note: It is
	 * not implemented in this class.
	 *
	 * @return number of containing objects
	 */
	public int size() {
		return 0;
	}

	/**
	 * Method which adds value type <code>Object</code> into calling collection.
	 * Note: It is not implemented in this class.
	 *
	 * @param value
	 *            value we want to add
	 */
	public void add(Object value) {
	}

	/**
	 * Method which tells if value type <code>Object</code> is contained in
	 * calling collection. Note: It is not implemented in this class.
	 *
	 * @param value
	 *            the value
	 * @return <code>true</code>, if successful otherwise <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes unwanted value type <code>Object</code> from calling collection.
	 * Note: It is not implemented in this class.
	 * 
	 * @param value
	 *            the value
	 * @return <code>true</code>, if successful otherwise <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method which returns array containing references to calling collection
	 * elements. Note: It is not implemented in this class.
	 * 
	 * @return array of references type <code>Object</code>
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method which iterates through each collection element. Note: It is not
	 * implemented in this class.
	 * 
	 * @param processor
	 *            <code>Processor</code> object with processing abilities.
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Method which adds into itself all elements from given collection. This
	 * other collection remains unchanged.
	 *
	 * @param other
	 *            collection containg wanted elements
	 */
	void addAll(Collection other) {
		class LocalProcessor extends Processor {

			@Override
			public void process(Object value) {
				add(value);
			}

		}

		if (other != null) {
			other.forEach(new LocalProcessor());
		}

	}

	/**
	 * Method which removes all elements from calling collection. Note: It is
	 * not implemented in this class.
	 */
	public void clear() {
	}
}
