package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

public class ObjectMultistack {

	private Map<String, MultistackEntry> map;
	
	public ObjectMultistack() {
		map = new HashMap<>();
	}

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


	public ValueWrapper pop(String name) {
		if (!map.containsKey(name)) {
			throw new EmptyStackException("You can't pop from empty stack");
		}
		
		MultistackEntry temporary = map.get(name);
		ValueWrapper value = temporary.value;
		if (temporary.next == null) {
			map.remove(name);
		}else{
			map.remove(name);
			map.put(name, temporary.next);
		}

		return value;
	}

	public ValueWrapper peek(String name) {
		if (!map.containsKey(name)) {
			throw new EmptyStackException("You can't peek from empty stack");
		}

		return map.get(name).value;
	}

	public boolean isEmpty(String name) {
		return !map.containsKey(name);
	}

	private static class MultistackEntry {
		private ValueWrapper value;
		private MultistackEntry next;

		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			if (value == null) {
				throw new IllegalArgumentException("Entry value can not be null.");
			}
			
			this.value = value;
			this.next = next;
		}
	}
}
