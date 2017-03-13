package hr.fer.zemris.java.custom.collections;

import org.junit.Before;

public class ArrayIndexedCollection extends Collection {
	public static final int DEFAULT_CAPACITY = 16;
	public static final int MINIMUM_CAPACITY = 1;
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

		this.addAll(other);
	}

	public ArrayIndexedCollection(int initialCapacity) {
		this(null, initialCapacity);
	}

	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	public ArrayIndexedCollection(Collection other) {
		this(other, MINIMUM_CAPACITY);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		} else if (size < capacity) {
			elements[size++] = value;
		} else {
			capacity *= CAPACITY_EXTENDING_COEFFICIENT;
			Object[] temporary = new Object[capacity];

			for (int i = 0; i < size; i++) {
				temporary[i] = elements[i];
			}
			temporary[size++] = value;
			elements = temporary;
		}
	}

	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				for (int j = i + 1; j < size; j++) {
					elements[j - 1] = elements[j];
				}

				elements[size - 1] = null;
				size--;

				return true;
			}
		}

		return false;
	}

	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index range should be from 0 to " + (size - 1));
		}
		remove(this.get(index));
	}

	@Override
	public Object[] toArray() {
		int length = this.size();
		Object[] newArray = new Object[length];

		for (int i = 0; i < length; i++) {
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
			capacity *= CAPACITY_EXTENDING_COEFFICIENT;
		}

		Object[] temporary = new Object[capacity];

		for (int i = 0; i < position; i++) {
			temporary[i] = elements[i];
		}
		temporary[position] = value;
		for (int i = position; i < size; i++) {
			temporary[i + 1] = elements[i];
		}

		size++;
		elements = temporary;
	}

	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}
}
