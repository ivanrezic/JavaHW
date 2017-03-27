package hr.fer.zemris.java.hw04.collections;

import static java.lang.Math.ceil;
import static java.lang.Math.pow;
import static java.lang.Math.abs;

import java.util.Arrays;

import static java.lang.Math.log10;

public class SimpleHashtable<K, V> {
	private final static int DEFAULT_CAPACITY = 16;
	private int size;
	private TableEntry<K, V>[] table;

	public SimpleHashtable() {
		table = new TableEntry[DEFAULT_CAPACITY];
	}

	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity can not be less than 1.");
		} else {
			double temporary = ceil(log10(capacity) / log10(2));
			table = new TableEntry[(int) pow(capacity, temporary)];
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
		} else {
			updateTableEntry(key, value);
		}
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

	private void updateTableEntry(K key, V value) {
		int slot = abs(key.hashCode()) % table.length;
		TableEntry<K, V> temporary = table[slot];

		while (temporary.getKey() != key) {
			temporary = temporary.next;
		}

		temporary.setValue(value);
	}

	public V get(Object key) {
		if (key == null) {
			return null;
		}
		
		int slot = abs(key.hashCode()) % table.length;
		TableEntry<K, V> temporary = table[slot];
		
		while (temporary != null) {
			if (temporary.getKey() == key) {
				return temporary.getValue();
			}
			temporary = temporary.next;
		}

		return null;
	}

	public int size() {
		return size;
	}

	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	public boolean containsValue(Object value) {
		for (int i = 0, size = table.length; i < size; i++) {
			for (TableEntry<K, V> tracker = table[i]; tracker != null; tracker = tracker.next) {
				if (tracker.getValue().equals(value)) {
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

		while (temporary.next.getKey() != key) {
			temporary = temporary.next;
		}

		temporary.next = temporary.next.next;
		size--;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder("[");

		for (int i = 0; i < table.length; i++) {
			for (TableEntry<K, V> j = table[i]; j != null; j = j.next) {
				if (j.next != null || j == table[i]) {
					sBuilder.append(j.toString() + ", ");
				} else {
					sBuilder.append(j.toString());
				}
			}
		}

		return sBuilder.append("]").toString();
	}
}
