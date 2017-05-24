package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * <code>ObjectMultistack</code> is map which pairs String key with its stack as
 * value.
 *
 * @author Ivan Rezic
 */
public class ObjectMultistack {

	/** Map which stores key-value pairs of this class. */
	private Map<String, MultistackEntry> map;

	/**
	 * Constructor which instantiates new object multistack.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * Method which pushes element to the stack defined by its key.
	 *
	 * @param name
	 *            map key which is paired to belonging stack
	 * @param valueWrapper
	 *            the value wrapper instance which contains value
	 * @throws IllegalArgumentException
	 *             if key passed is null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry help = new MultistackEntry(valueWrapper, null);

		if (name == null) {
			throw new IllegalArgumentException("Map key can not be null");
		} else if (map.containsKey(name)) {
			MultistackEntry temporary = map.get(name);
			help.next = temporary;
		}

		map.put(name, help);
	}

	/**
	 * Method which returns last element add to stack and removes it.
	 *
	 * @param name
	 *            map key which is paired to belonging stack
	 * @return the value wrapper instance which contains value
	 * 
	 * @throws EmptyStackException
	 *             if trying to pop from empty stack
	 */
	public ValueWrapper pop(String name) {
		if (!map.containsKey(name)) {
			throw new EmptyStackException("You can't pop from empty stack");
		}

		MultistackEntry temporary = map.get(name);
		ValueWrapper value = temporary.value;
		if (temporary.next == null) {
			map.remove(name);
		} else {
			map.put(name, temporary.next);
		}

		return value;
	}

	/**
	 * Method which returns last element added to stack.
	 *
	 * @param name
	 *            map key which is paired to belonging stack
	 * @return the value wrapper instance which contains value
	 * 
	 * @throws EmptyStackException
	 *             if trying to pop from empty stack
	 */
	public ValueWrapper peek(String name) {
		if (!map.containsKey(name)) {
			throw new EmptyStackException("You can't peek from empty stack");
		}

		return map.get(name).value;
	}

	/**
	 * Checks if stack is empty.
	 *
	 * @param name
	 *            map key paired with belonging stack
	 * @return true, if empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		return !map.containsKey(name);
	}

	/**
	 * <code>MultistackEntry</code> is nested class which represets
	 * #{@link ObjectMultistack} entry.
	 *
	 * @author Ivan Rezic
	 */
	private static class MultistackEntry {

		/** Entry value */
		private ValueWrapper value;

		/** Next entry. */
		private MultistackEntry next;

		/**
		 * Constructor which instantiates new multistack entry.
		 *
		 * @param value
		 *            ValueWrapper value
		 * @param next
		 *            next entry
		 * @throws IllegalArgumentException
		 *             if entry value is to be set null
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			if (value == null) {
				throw new IllegalArgumentException("Entry value can not be null.");
			}

			this.value = value;
			this.next = next;
		}
	}
}
