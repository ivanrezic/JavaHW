package hr.fer.zemris.java.custom.collections;

public class ObjectStack {
	private ArrayIndexedCollection elements;

	public ObjectStack() {
		elements = new ArrayIndexedCollection();
	}
	
	public boolean isEmpty(){
		return elements.isEmpty();
	}
	
	public int size(){
		return elements.size();
	}
	
	public void push(Object value) throws IllegalArgumentException{
		elements.add(value);
	}
	
	public Object pop(){
		Object temporary = elements.get(elements.size()-1);
		
		try {
			elements.remove(size()-1);
		} catch (IndexOutOfBoundsException e) {
			throw new EmptyStackException("Can't remove element from empty stack");
		}
		
		return temporary;
	}
	
	public Object peek(){
		try {
			return elements.get(size()-1);
		} catch (IndexOutOfBoundsException e) {
			throw new EmptyStackException("You can't peek from empty stack");
		}
	}
	
	public void clear(){
		elements.clear();
	}
}
