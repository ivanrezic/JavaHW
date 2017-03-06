package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;
import org.junit.Test;

public class FactorialTest {

	@Test
	public void unesenBroj4() {
		long result = Factorial.izracunaj(4);
		assertEquals(result, 24);
	}

	@Test
	public void unesenBroj12() {
		long result = Factorial.izracunaj(12);
		assertEquals(result, 479001600);
	}

	@Test
	public void unesenBroj20() {
		long result = Factorial.izracunaj(20);
		assertEquals(result, 2432902008176640000L);
	}

}
