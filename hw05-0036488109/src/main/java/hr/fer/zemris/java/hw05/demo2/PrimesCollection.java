package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimesCollection implements Iterable<Integer> {

	private int size;

	public PrimesCollection(int size) {
		super();
		this.size = size;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new primeIterator();
	}

	private class primeIterator implements Iterator<Integer> {

		private int prime = 2;
		private int counter = size;

		@Override
		public boolean hasNext() {
			return counter != 0;

		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			while (!isPrime(prime)) {
				prime++;
			}

			counter--;
			return prime++;
		}

		private boolean isPrime(int num) {
	        if (num < 2) return false;
	        if (num == 2) return true;
	        if (num % 2 == 0) return false;
	        for (int i = 3; i * i <= num; i += 2)
	            if (num % i == 0) return false;
	        return true;
		}

	}

}
