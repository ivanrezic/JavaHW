package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <code>PrimesCollection</code> represents simple collection which contains
 * prime numbers provided when needed.
 *
 * @author Ivan Rezic
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * It represents how many prime numbers this class object should provide.
	 */
	private int size;

	/**
	 * Constructor which instantiates new primes collection.
	 *
	 * @param size
	 *            {@link #size}
	 */
	public PrimesCollection(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Number of primes requested must be 1 or more.");
		}
		this.size = size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new primeIterator();
	}

	/**
	 * <code>primeIterator</code> is inner class which implements Iterator.
	 *
	 * @author Ivan Rezic
	 */
	private class primeIterator implements Iterator<Integer> {

		/** First prime number in this collection. */
		private int prime = 2;

		/** Used for checking if we have reached max number of primes. */
		private int counter = size;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return counter != 0;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
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

		/**
		 * Checks if number is prime.
		 *
		 * @param num
		 *            the number we want to check
		 * @return true, if it is prime, false otherwise
		 */
		private boolean isPrime(int num) {
			if (num < 2)
				return false;
			if (num == 2)
				return true;
			if (num % 2 == 0)
				return false;
			for (int i = 3; i * i <= num; i += 2)
				if (num % i == 0)
					return false;
			return true;
		}

	}

}
