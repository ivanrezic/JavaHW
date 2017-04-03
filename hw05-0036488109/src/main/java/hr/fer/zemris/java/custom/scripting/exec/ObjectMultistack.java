package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

public class ObjectMultistack {

	private Map<String, MultistackEntry> map = new HashMap<>();

	public void push(String name, ValueWrapper valueWrapper) {
		if (!map.containsKey(name)) {
			map.put(name, new MultistackEntry(valueWrapper, null));
		} else {
			slotAdd(map.get(name), valueWrapper);
		}
	}

	private void slotAdd(MultistackEntry entry, ValueWrapper valueWrapper) {
		while (entry.next != null) {
			entry = entry.next;
		}

		entry.next = new MultistackEntry(valueWrapper, null);
	}

	public ValueWrapper pop(String name) {
		if (!map.containsKey(name)) {
			throw new EmptyStackException("You can't pop from empty stack");
		}

		ValueWrapper temporary;
		MultistackEntry entry = map.get(name);
		if (entry.next == null) {
			temporary = entry.getValue();
			map.remove(name);
		}else{
			while (entry.next != null) {
				entry.next = entry.next.next;
			}
			
			temporary = entry.getValue();
			entry = null;
		}
		
		return temporary;
	}

	public ValueWrapper peek(String name) {
		if (!map.containsKey(name)) {
			throw new EmptyStackException("You can't peek from empty stack");
		}

		MultistackEntry entry = map.get(name);
		while (entry.next != null) {
			entry.next = entry.next.next;
		}
		
		return entry.getValue();
	}

	public boolean isEmpty(String name) {
		return map.isEmpty();
	}

	private static class MultistackEntry {
		private ValueWrapper value;
		private MultistackEntry next;

		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}

		public ValueWrapper getValue() {
			return value;
		}
	}
}
