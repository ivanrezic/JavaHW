package hr.fer.zemris.java.hw04.collections;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.log10;
import static java.lang.Math.pow;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic class <code>SimpleHashtable</code> represents implementation of hash
 * table. Each entry is stored in inner key-value pair called TableEntry. Also
 * key and value can be any Object subclass. Every hash table slot is one linked
 * list whose elements are added at the end.This class also uses key hashCode
 * for slot calculation and implements interface <code>Iterable</code> used for
 * retrieving and deleting elements.
 * 
 *
 * @param <K>
 *            key representation of this class
 * @param <V>
 *            value representation of this class
 * 
 * @author Ivan
 */
@SuppressWarnings("all")
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * The Constant LIMIT_FACTOR represents percentage limit when
	 * SimpleHashtable resizes by {@link #RESIZE_FACTOR}
	 */
	private static final double LIMIT_FACTOR = 0.75;

	/** The Constant DEFAULT_CAPACITY. */
	private final static int DEFAULT_CAPACITY = 16;

	/** The Constant RESIZE_FACTOR represents table enlargement coefficient. */
	private final static int RESIZE_FACTOR = 2;

	/** The size. */
	private int size;

	/** The slot limit. */
	private double slotLimit;

	/** The modification count. */
	private int modificationCount;

	/** The table. */
	TableEntry<K, V>[] table;

	/**
	 * Constructor that instantiates a new simple hashtable.
	 */
	public SimpleHashtable() {
		table = new TableEntry[DEFAULT_CAPACITY];
		slotLimit = LIMIT_FACTOR * DEFAULT_CAPACITY;
	}

	/**
	 * Constructor that instantiates a new simple hashtable.
	 *
	 * @param capacity
	 *            wanted capacity
	 */
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity can not be less than 1.");
		} else {
			int temporary = (int) pow(2, ceil(log10(capacity) / log10(2)));
			slotLimit = LIMIT_FACTOR * temporary;
			table = new TableEntry[temporary];
		}
	}

	/**
	 * Inner genneric class which represents object that encapsulates key-value
	 * pair inside SimpleHashtable. Each instance of TableEntry contains 3
	 * properties: key,value and reference to the next TableEntry chained into
	 * list.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 */
	public static class TableEntry<K, V> {

		/** The key. */
		private K key;

		/** The value. */
		private V value;

		/** The next. */
		private TableEntry<K, V> next;

		/**
		 * Instantiates a new table entry.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 * @param next
		 *            the next
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 *
		 * @param value
		 *            the new value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Gets the key.
		 *
		 * @return table entry key
		 */
		public K getKey() {
			return key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return getKey() + "=" + getValue();
		}
	}

	/**
	 * Method puts key-value pair in SimpleHashtable. If pair is already
	 * contained than it is replaced otherwise it is stored at the end of slot.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * 
	 * @throws IllegalArgumentException
	 *             when key is null
	 */
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

	/**
	 * Helper method which calculates if hash table size reached slot limit.
	 */
	private void slotLimitReached() {
		if (size >= slotLimit) {
			resizeTable();
		}
	}

	/**
	 * Helper method which resizes hash table by {@link #RESIZE_FACTOR}
	 */
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

	/**
	 * Helper method which updates wanted pair with new value.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	private void updateTableEntry(K key, V value) {
		int slot = abs(key.hashCode()) % table.length;
		TableEntry<K, V> temporary = table[slot];

		while (!temporary.getKey().equals(key)) {
			temporary = temporary.next;
		}

		temporary.setValue(value);
	}

	/**
	 * Helper method which adds new table entry at the end of slot.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
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

	/**
	 * {@link #addTableEntry(Object, Object)} helper method which finds last
	 * element of slat and adds new one.
	 *
	 * @param slot
	 *            the slot
	 * @param newEntry
	 *            the new entry
	 */
	private void addIntoSlot(int slot, TableEntry<K, V> newEntry) {
		TableEntry<K, V> temporary = table[slot];

		while (temporary.next != null) {
			temporary = temporary.next;
		}

		temporary.next = newEntry;
	}

	/**
	 * Method gets value for given key otherwise null. Also value can be null so
	 * meaning of return value is not straightforward.
	 *
	 * @param key
	 *            the key
	 * @return the value
	 */
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

	/**
	 * Method which gets number of elements stored in hash table.
	 *
	 * @return the number of elements conatined.
	 */
	public int size() {
		return size;
	}

	/**
	 * Method which returns boolean value that tells us if key is contained in
	 * hash table.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		int slot = abs(key.hashCode()) % table.length;
		for (TableEntry<K, V> tracker = table[slot]; tracker != null; tracker = tracker.next) {
			if (tracker.getKey().equals(key)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Method which returns boolean value that tells us if value is contained in
	 * hash table.
	 *
	 * @param value
	 *            the value
	 * @return true, if successful, false otherwise
	 */
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

	/**
	 * Method which removes table entry with given key.
	 *
	 * @param key
	 *            the key
	 */
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

	/**
	 * Checks if hash table is empty.
	 *
	 * @return true, if is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Clears all simple hash table elements.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}

		size = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();

	}

	/**
	 * Private inner class <code>IteratorImpl</code> implements generic
	 * interface <code>Iterator</code> and represents object which iterates
	 * through hash table.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/** Current slot position. */
		private int slotPosition = 0;

		/** Number of elements left for iteration. */
		private int count = size;

		/** The modification check.. */
		private int modificationCheck = modificationCount;

		/** The helper object used for storing current manipulated entry. */
		private TableEntry<K, V> helper = table[slotPosition];

		/**
		 * The current table entry represents last element obtained by
		 * {@link #searchHelper()}.
		 */
		private TableEntry<K, V> curent;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			checkModification();
			return count != 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		public SimpleHashtable.TableEntry next() {
			checkModification();

			if (!searchHelper()) {
				throw new NoSuchElementException("No entries left.");
			}

			count--;
			return curent;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			checkModification();
			if (curent == null || !containsKey(curent.getKey())) {
				throw new IllegalStateException("Can not remove element that does not exist.");
			}
			SimpleHashtable.this.remove(curent.getKey());
			modificationCheck = modificationCount;
		}

		/**
		 * Helper method which checks if any hash table modification occurred.
		 */
		private void checkModification() {
			if (modificationCheck != modificationCount) {
				throw new ConcurrentModificationException();
			}
		}

		/**
		 * {@link #next()} helper method which iterates sequentially through
		 * hash table and returns each entry, one by one.
		 *
		 * @return true, if successful
		 */
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
