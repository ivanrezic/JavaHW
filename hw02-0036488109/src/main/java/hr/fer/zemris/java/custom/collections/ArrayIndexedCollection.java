package hr.fer.zemris.java.custom.collections;

/**
 * ArrayIndexedCollection is Collection subclass which represents
 * <code>Object</code> array. ArrayIndexedCollection instance is defined by its
 * size, capacity and elements it contains.
 */
public class ArrayIndexedCollection extends Collection {

	/** The Constant DEFAULT_CAPACITY. */
	public static final int DEFAULT_CAPACITY = 16;

	/** The Constant CAPACITY_EXTENDING_COEFFICIENT. */
	public static final int CAPACITY_EXTENDING_COEFFICIENT = 2;

	/** Number of elements in collection. */
	private int size;

	/** The capacity. */
	private int capacity;

	/** Array containing <code>Object</code> instances. */
	private Object[] elements;

	/**
	 * Constructor which instantiates a new array indexed collection from given
	 * collection and wanted capacity. Capacity should be lower than zero.
	 *
	 * @param other
	 *            collection containing wanted elements
	 * @param initialCapacity
	 *            the initial capacity
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity shouldnt be lower than 1");
		}

		capacity = initialCapacity;
		elements = new Object[capacity];

		addAll(other);
	}

	/**
	 * Constructor which instantiates a new array indexed collection with wanted
	 * capacity and zero elements. Capacity should be lower than zero.
	 *
	 * @param initialCapacity
	 *            the initial capacity
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(null, initialCapacity);
	}

	/**
	 * Constructor which instantiates a new array indexed collection with
	 * default size.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor which instantiates a new array indexed collection from given
	 * collection and its size.
	 *
	 * @param other
	 *            collection containing wanted elements
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, other.size());
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
	 * Value is stored on last position of collection. If there is no space for
	 * new element method resizes calling collection. Average complexity of this
	 * method is O(1).
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
		} else if (size == capacity) {
			elements = resize(elements, CAPACITY_EXTENDING_COEFFICIENT);
		}

		elements[size++] = value;
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
	 * to <code>size-1</code>.
	 *
	 * @param index
	 *            the index
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index range should be from 0 to " + (size - 1));
		}

		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
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
		Object[] newArray = new Object[size];

		for (int i = 0; i < size; i++) {
			newArray[i] = this.get(i);
		}

		return newArray;
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

		for (int i = 0; i < this.size; i++) {
			processor.process(this.get(i));
		}
	}

	/**
	 * Method which removes all elements from calling collection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Returns the object that is stored in backing array at position
	 * <code>index</code>. Valid indexes are <code>0</code> to
	 * <code>size-1</code>. If <code>index</code> is invalid, the implementation
	 * should throw the appropriate exception.Complexity of this method is O(1).
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
		return elements[index];
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
			throw new IndexOutOfBoundsException("Legal postitions are 0 to " + (size));
		} else if (value == null) {
			throw new IllegalArgumentException("Value shouldnt be null");
		} else if (size == capacity) {
			elements = resize(elements, CAPACITY_EXTENDING_COEFFICIENT);
		} else if (position == size) {
			add(value);
		} else {
			size++;
			for (int i = size; i > position; i--) {
				elements[i] = elements[i - 1];
			}

			elements[position] = value;
		}
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
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Method resize is helper method which resizes given array by resize
	 * coefficint.
	 *
	 * @param array
	 *            the array we want to resize
	 * @param resizeCoefficient
	 *            the resize coefficient which determines how enlarged will be
	 *            new array
	 * @return the object[]
	 */
	public Object[] resize(Object[] array, int resizeCoefficient) {
		capacity = array.length * resizeCoefficient;
		Object[] temporary = new Object[capacity];

		for (int i = 0; i < array.length; i++) {
			temporary[i] = array[i];
		}

		return temporary;
	}
}
