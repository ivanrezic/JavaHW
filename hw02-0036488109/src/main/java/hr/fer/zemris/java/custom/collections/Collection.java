package hr.fer.zemris.java.custom.collections;

public class Collection {

	protected Collection() {
	}

	public boolean isEmpty() {
		return this.size() == 0 ? true : false;
	}

	public int size() {
		return 0;
	}

	public void add(Object value) {
	}

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

		class LocalProcessor extends Processor {

			@Override
			public void process(Object value) {
				add(value);
			}

		}

		if (other != null) {
			other.forEach(new LocalProcessor());
		}

	}

	public void clear() {
	}
}
