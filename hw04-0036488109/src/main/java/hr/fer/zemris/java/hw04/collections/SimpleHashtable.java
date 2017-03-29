package hr.fer.zemris.java.hw04.collections;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.log10;
import static java.lang.Math.pow;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("all")
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	private static final double LIMIT_FACTOR = 0.75;
	private final static int DEFAULT_CAPACITY = 16;
	private final static int RESIZE_FACTOR = 2;
	private int size;
	private double slotLimit;
	private int modificationCount;
	TableEntry<K, V>[] table; // nakon testiranja vratiti na private


	public SimpleHashtable() {
		table = new TableEntry[DEFAULT_CAPACITY];
		slotLimit = LIMIT_FACTOR * DEFAULT_CAPACITY;
	}

	
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity can not be less than 1.");
		} else {
			int temporary = (int) pow(2, ceil(log10(capacity) / log10(2)));
			slotLimit = LIMIT_FACTOR * temporary;
			table = new TableEntry[temporary];
		}
	}

	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;

		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry other = (TableEntry) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (next == null) {
				if (other.next != null)
					return false;
			} else if (!next.equals(other.next))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return getKey() + "=" + getValue();
		}
	}

	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can not be null.");
		} else if (!containsKey(key)) {
			addTableEntry(key, value);
			slotLimitReached();
			modificationCount++;
		} else {
			updateTableEntry(key, value);
		}
	}

	private void slotLimitReached() {
		if (size >= slotLimit) {
			resizeTable();
		}
	}

	private void resizeTable() {
		TableEntry<K, V>[] oldTable = table;
		table = new TableEntry[oldTable.length * RESIZE_FACTOR];
		slotLimit = LIMIT_FACTOR * table.length;
		size = 0;
		for (int i = 0; i < oldTable.length; i++) {
			TableEntry<K, V> tracker = oldTable[i];
			while (tracker != null) {
				put(tracker.key, tracker.value);
				tracker = tracker.next;
			}
		}
	}

	private void updateTableEntry(K key, V value) {
		int slot = abs(key.hashCode()) % table.length;
		TableEntry<K, V> temporary = table[slot];

		while (!temporary.getKey().equals(key)) {
			temporary = temporary.next;
		}

		temporary.setValue(value);
	}

	private void addTableEntry(K key, V value) {
		TableEntry<K, V> newEntry = new TableEntry<K, V>(key, value, null);
		int slot = abs(key.hashCode()) % table.length;

		if (table[slot] == null) {
			table[slot] = newEntry;
		} else {
			addIntoSlot(slot, newEntry);
		}

		size++;
	}

	private void addIntoSlot(int slot, TableEntry<K, V> newEntry) {
		TableEntry<K, V> temporary = table[slot];

		while (temporary.next != null) {
			temporary = temporary.next;
		}

		temporary.next = newEntry;
	}

	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int slot = abs(key.hashCode()) % table.length;
		TableEntry<K, V> temporary = table[slot];

		while (temporary != null) {
			if (temporary.getKey().equals(key)) {
				return temporary.getValue();
			}
			temporary = temporary.next;
		}

		return null;
	}

	public int size() {
		return size;
	}

	public boolean containsKey(Object key) {//popraviti s hash, hash izvuci u metodicu
		for (int i = 0, size = table.length; i < size; i++) {
			for (TableEntry<K, V> tracker = table[i]; tracker != null; tracker = tracker.next) {
				if (tracker.getKey().equals(key)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean containsValue(Object value) {
		for (int i = 0, size = table.length; i < size; i++) {
			for (TableEntry<K, V> tracker = table[i]; tracker != null; tracker = tracker.next) {
				if (tracker.value == value || (tracker.value != null && tracker.value.equals(value))) {
					return true;
				}
			}
		}

		return false;
	}

	public void remove(Object key) {
		if (!containsKey(key)) {
			return;
		}

		int slot = abs(key.hashCode()) % table.length;
		TableEntry<K, V> temporary = table[slot];
		if (temporary.next == null) {
			table[slot] = null;
		} else if (temporary.getKey().equals(key)) {
			table[slot] = temporary.next;
		} else {
			while (temporary.getKey() != key) {
				temporary = temporary.next;
			}
		}

		size--;
		modificationCount++;

	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}

		size = 0;
	}

	@Override
	public String toString() {
		String result = "[]";
		StringBuilder sb = new StringBuilder();

		TableEntry<K, V> help;
		sb.append("[");
		for (int i = 0; i < table.length; i++) {
			help = table[i];
			while (help != null) {
				sb.append(help.toString()).append(", ");
				help = help.next;
			}
			if (i == table.length - 1 && sb.length() > 2) {
				result = sb.substring(0, sb.length() - 2).concat("]");
			}
		}

		return result;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();

	}

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private int slotPosition = 0;
		private int count = size;
		private int modificationCheck = modificationCount;
		private TableEntry<K, V> helper = table[slotPosition];
		private TableEntry<K, V> curent;

		public boolean hasNext() {
			checkModification();
			return count != 0;
		}

		public SimpleHashtable.TableEntry next() {
			checkModification();

			if (!searchHelper()) {
				throw new NoSuchElementException("No entries left.");
			}

			count--;
			return curent;
		}

		public void remove() {
			checkModification();
			if (curent == null || !containsKey(curent.getKey())) {
				throw new IllegalStateException("Can not remove element that does not exist.");
			}
			SimpleHashtable.this.remove(curent.getKey());
			modificationCheck = modificationCount;
		}

		private void checkModification() {
			if (modificationCheck != modificationCount) {
				throw new ConcurrentModificationException();
			}
		}

		private boolean searchHelper() {
			for (int i = slotPosition; i < table.length; i++) {
				if (helper == null && slotPosition < table.length - 1) {
					helper = table[++slotPosition];
					continue;
				}
				while (helper != null) {
					curent = helper;
					helper = helper.next;
					return true;
				}
			}
			return false;
		}
	}
}
