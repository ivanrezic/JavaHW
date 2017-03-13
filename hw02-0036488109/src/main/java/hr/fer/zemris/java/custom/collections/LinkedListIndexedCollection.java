package hr.fer.zemris.java.custom.collections;

public class LinkedListIndexedCollection extends Collection {
	private int size;
	private ListNode first;
	private ListNode last;

	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;

		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
	}

	public LinkedListIndexedCollection() {
	}

	public LinkedListIndexedCollection(Collection other) {
		this();
		addAll(other);
	}

	@Override
	public int size() {
		return size;
	}

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

		ListNode node = first;
		int counter = 0;
		while (counter != index) {
			node = node.next;
			counter++;
		}

		disconnect(node);
	}

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

	@Override
	public void forEach(Processor processor) {
		ListNode node = first;

		while (node != null) {
			processor.process(node.value);
			node = node.next;
		}
	}

	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

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
