package hr.fer.zemris.java.custom.collections;

/**
 * LinkedListIndexedCollection is Collection subclass which represents
 * <code>Object</code> linked list. LinkedListIndexedCollection instance is
 * defined by its size, reference to the first and last element.
 * 
 * @author Ivan
 */
public class LinkedListIndexedCollection extends Collection {

	/** The number of elements in list. */
	private int size;

	/** Reference to the first element. */
	private ListNode first;

	/** Reference to the last element. */
	private ListNode last;

	/**
	 * Class which represents list node.
	 */
	private static class ListNode {

		/** Previous node. */
		ListNode previous;

		/** Next node. */
		ListNode next;

		/** The value. */
		Object value;

		/**
		 * Instantiates a new list node.
		 *
		 * @param previous
		 *            previous node
		 * @param next
		 *            next node
		 * @param value
		 *            the value
		 */
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
	}

	/**
	 * Public constructor which instantiates a new linked list indexed
	 * collection without any elements contained and size 0.
	 */
	public LinkedListIndexedCollection() {
	}

	/**
	 * Public constructor which instantiates a new linked list indexed
	 * collection with elements from collection given as argument.
	 * 
	 * @param other
	 *            collection whose elements will be used for instantiation of
	 *            new LinkedListIndexedCollection.
	 * @throws NullPointerException
	 *             when <code>null</code> is set as argument
	 */
	public LinkedListIndexedCollection(Collection other) throws NullPointerException {
		this();
		addAll(other);
	}

	/**
	 * Method which returns number of elements in collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Method which adds value type <code>Object</code> into calling collection.
	 * Value is stored on last position of collection. Average complexity of
	 * this method is O(1).
	 * 
	 * @param value
	 *            value we want to add
	 * 
	 * @throws NullPointerException
	 *             when given <code>value</code> is <code>null</code>
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Value shouldnt be null");
		} else if (first == null && last == null) {
			first = last = new ListNode(null, null, value);
		} else {
			last.next = new ListNode(last, null, value);
			last = last.next;
		}

		size++;
	}

	/**
	 * Method which tells if value type <code>Object</code> is contained in
	 * calling collection.
	 * 
	 * @param value
	 *            the value
	 * @return <code>true</code>, if successful otherwise <code>false</code>
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Removes unwanted value type <code>Object</code> from calling collection.
	 * 
	 * 
	 * @param value
	 *            the value
	 * @return <code>true</code>, if successful otherwise <code>false</code>
	 */
	@Override
	public boolean remove(Object value) {
		try {
			remove(indexOf(value));
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		return true;
	}

	/**
	 * Removes element at specified <code>index</code> from collection. Element
	 * that was previously at location <code>index+1</code> after this operation
	 * is on location <code>index</code>, etc. Legal indexes are <code>0</code>
	 * to <code>size</code>.
	 *
	 * @param index
	 *            the index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             when index is not from given range
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index range should be from 0 to " + (size - 1));
		}

		ListNode node = first;
		int counter = 0;
		while (counter != index) {
			node = node.next;
			counter++;
		}

		disconnect(node);
	}

	/**
	 * Disconnect is helper method which connects previous and next node from
	 * given node. And that way it disconnects.
	 *
	 * @param node
	 *            the node
	 */
	public void disconnect(ListNode node) {
		if (first.equals(node) && last.equals(node)) {
			first = last = null;
		}
		if (first.equals(node)) {
			first = node.next;
			first.previous = null;
		}
		if (last.equals(node)) {
			last = node.previous;
			last.next = null;
		}
		if (node.previous != null) {
			node.previous.next = node.next;
		}
		if (node.next != null) {
			node.next.previous = node.previous;
		}

		size--;
	}

	/**
	 * Method which returns array containing references to calling collection
	 * elements.
	 * 
	 * @return array of references type <code>Object</code>
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode node = first;

		for (int i = 0; i < size; i++) {
			array[i] = node.value;
			node = node.next;
		}

		return array;
	}

	/**
	 * Returns the object that is stored in linked list at position
	 * <code>index</code>. Valid indexes are <code>0</code> to
	 * <code>size-1</code>. If <code>index</code> is invalid, the implementation
	 * should throw the appropriate exception.Complexity of this method is never
	 * more that n/2 +1.
	 *
	 * @param index
	 *            the index of wanted value
	 * @return Object wanted value at given index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             when index is not in allowed range (<code>0</code> to
	 *             <code>size-1</code>)
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("index should be ranging from 0 to " + (size - 1));
		}
		ListNode finder = null;

		if (index < size / 2) {
			finder = first;

			int counter = 0;
			while (counter != index) {
				finder = finder.next;
				counter++;

			}
		} else {
			finder = last;

			int counter = size - 1;
			while (counter != index) {
				finder = finder.previous;
				counter--;
			}
		}

		return finder.value;
	}

	/**
	 * Method which iterates through each collection element and forvards its
	 * argument to Processor method process.
	 * 
	 * @param processor
	 *            <code>Processor</code> object with processing abilities.
	 * @throws NullPointerException
	 *             if <code>null</code> is given as argument
	 */
	@Override
	public void forEach(Processor processor) {
		if (processor == null) {
			throw new NullPointerException("Processor given can not be null.");
		}
		
		ListNode node = first;

		while (node != null) {
			processor.process(node.value);
			node = node.next;
		}
	}

	/**
	 * Method which removes all elements from calling collection.
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found. Complexity is O(n).
	 *
	 * @param value
	 *            the value
	 * @return <code>int</code> 1 if exists, -1 if not
	 */
	public int indexOf(Object value) {
		ListNode finder = first;

		for (int i = 0; i < size; i++) {
			if (finder.value.equals(value)) {
				return i;
			}
			finder = finder.next;
		}

		return -1;

	}

	/**
	 * Method which inserts given value at wanted position.Allowed position is
	 * (<code>0</code> to <code>size-1</code>). It does not overwrite value at
	 * given position but it removes all elements from that position by one
	 * position to the right.
	 *
	 * @param value
	 *            wanted value to be inserted
	 * @param position
	 *            inserting position
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if position is not in allowed range
	 * @throws IllegalArgumentExeption
	 *             if value is <code>null</code>
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Legal postitions are 0 to " + size);
		} else if (value == null) {
			throw new IllegalArgumentException("Value shouldnt be null");
		} else if (position == size || size == 0) {
			add(value);
		} else if (position == 0) {
			ListNode newNode = new ListNode(null, first, value);
			first = newNode;
			size++;
		} else if (position == size - 1) {
			Object temporary = get(size - 1);
			remove(size - 1);
			add(value);
			add(temporary);

		} else {
			ListNode node = first;

			int counter = 0;
			while (counter != position) {
				node = node.next;
				counter++;
			}

			ListNode newNode = new ListNode(first.previous, node, value);
			node.previous = newNode;
			size++;
		}
	}
}
