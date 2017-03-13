package hr.fer.zemris.java.custom.collections;

public class ArrayIndexedCollection extends Collection {
	public static final int DEFAULT_CAPACITY = 16;
	public static final int CAPACITY_EXTENDING_COEFFICIENT = 2;

	private int size;
	private int capacity;
	private Object[] elements;

	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity shouldnt be lower than 1");
		}

		capacity = initialCapacity;
		elements = new Object[capacity];

		addAll(other);
	}

	public ArrayIndexedCollection(int initialCapacity) {
		this(null, initialCapacity);
	}

	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	public ArrayIndexedCollection(Collection other) {
		this(other, other.size());
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		} else if (size == capacity) {
			elements = resize(elements, CAPACITY_EXTENDING_COEFFICIENT);
		}

		elements[size++] = value;
	}

	@Override
	public boolean contains(Object value) {
		return indexOf(value) == -1 ? false : true;
	}

	@Override
	public boolean remove(Object value) {
		try {
			remove(indexOf(value));
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		return true;
	}

	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index range should be from 0 to " + (size - 1));
		}
		
		for (int i = index; i < size -1; i++) {
			elements[i] = elements[i+1];
		}
		
		elements[size--] = null;
	}

	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[size];

		for (int i = 0; i < size; i++) {
			newArray[i] = this.get(i);
		}

		return newArray;
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < this.size; i++) {
			processor.process(this.get(i));
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("index should be ranging from 0 to " + (size - 1));
		}
		return elements[index];
	}

	public void insert(Object value, int position) {
		if (position < 0 || position >= size) {
			throw new IndexOutOfBoundsException("Legal postitions are 0 to " + (size - 1));
		} else if (value == null) {
			throw new IllegalArgumentException("Value shouldnt be null");
		} else if (size == capacity) {
			elements = resize(elements, CAPACITY_EXTENDING_COEFFICIENT);
		}

		size++;
		for (int i = size; i > position; i--) {
			elements[i] = elements[i-1];
		}
		
		elements[position] = value;
	}

	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}
	
	public Object[] resize(Object[] array,int resizeCoefficient){
		capacity = array.length * resizeCoefficient;
		Object[] temporary = new Object[capacity];
		
		for (int i = 0; i < array.length; i++) {
			temporary[i] = array[i];
		}
		
		return temporary;
	}
}
