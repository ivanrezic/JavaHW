package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrimListModelTest {

	public int nextPrime(int prime){
		int newPrime = prime;
		
		for (int i = 2; i <= newPrime / 2; i++) {
			if (newPrime % i == 0) {
				newPrime++;
				i = 2;
			}
		}
		return newPrime;
	}
	
	@Test
	public void testNextPrimeImplementation() {
		assertEquals(11, nextPrime(10));
		assertEquals(13, nextPrime(12));
		assertEquals(17, nextPrime(15));
	}

}
