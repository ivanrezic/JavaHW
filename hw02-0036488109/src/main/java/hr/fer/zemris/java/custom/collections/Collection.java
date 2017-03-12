package hr.fer.zemris.java.custom.collections;

public class Collection {

	protected Collection() {
	}

	public boolean isEmpty() {
		return this.size() == 0 ? false : true;
	}

	public int size() {
		return 0;
	}

	public void add(Object value) {
	}

	// It is OK to ask if collection contains null provjeriti sta s tim kasnije
	public boolean contains(Object value) {
		return false;
	}

	public boolean remove(Object value) {
		return false;
	}

	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	public void forEach(Processor processor) {
	}

	void addAll(Collection other) {
		
		class Processor {

			public void process(Object value) {

			}
		}
	}
	
	public void clear(){
	}
}
