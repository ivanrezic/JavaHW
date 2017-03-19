package hr.fer.zemris.java.custom.collections;

/**
 * The Class ObjectStack represents structure of stack and offers all its
 * functionalities.
 * 
 * @author Ivan
 */
public class ObjectStack {

	/** The elements of stack. */
	private ArrayIndexedCollection elements;

	/**
	 * Instantiates a new object stack.
	 */
	public ObjectStack() {
		elements = new ArrayIndexedCollection();
	}

	/**
	 * Method checks if calling collection is empty.
	 *
	 * @return <code>true</code> if it is empty <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/**
	 * Method which returns number of elements in collection.
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Method which adds given value on top of stack. Complexity of this method
	 * is O(1).
	 *
	 * @param value
	 *            the value
	 * @throws IllegalArgumentException
	 *             if null is given as value
	 */
	public void push(Object value) throws IllegalArgumentException {
		elements.add(value);
	}

	/**
	 * Gets the element from top of the stack and removes it from the stack.
	 * Complexity of this method is O(1).
	 * 
	 * 
	 * @return Last added element from stack.
	 * @throws EmptyStackException
	 *             When attempted to pop an element from an empty stack.
	 */
	public Object pop() {
		Object temporary = elements.get(elements.size() - 1);

		try {
			elements.remove(size() - 1);
		} catch (IndexOutOfBoundsException e) {
			throw new EmptyStackException("Can't remove element from empty stack");
		}

		return temporary;
	}

	/**
	 * Method which shows last element pushed to the stack.
	 *
	 * @return the object which is at the top of stack
	 * 
	 * @throws EmptyStackException
	 *             When trying to peek from empty stack.
	 */
	public Object peek() {
		try {
			return elements.get(size() - 1);
		} catch (IndexOutOfBoundsException e) {
			throw new EmptyStackException("You can't peek from empty stack");
		}
	}

	/**
	 * Method which removes all elements from calling collection.
	 */
	public void clear() {
		elements.clear();
	}
}
