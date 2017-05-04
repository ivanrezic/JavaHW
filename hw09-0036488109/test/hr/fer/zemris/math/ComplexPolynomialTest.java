package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ComplexPolynomialTest {
	private Complex[] factors1;
	private Complex[] factors2;
	private ComplexPolynomial polynomial1;
	private ComplexPolynomial polynomial2;

	@Before
	public void init() {
		factors1 = new Complex[] { Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG,
				new Complex(12.2112, 123.333), new Complex(2938.21222, -0.222222), new Complex(-69.69, 0.001),
				new Complex(-123, -123) };
		factors2 = new Complex[] { Complex.ONE, Complex.IM_NEG, new Complex(-2938.21222, -0.222222),
				new Complex(69.69, 0.001), new Complex(-123, 123) };

		polynomial1 = new ComplexPolynomial(factors1);
		polynomial2 = new ComplexPolynomial(factors2);
	}

	@Test
	public void polynomialOrder() {
		assertEquals(7, polynomial1.order());
		assertEquals(4, polynomial2.order());
		assertEquals(11, polynomial1.multiply(polynomial2).order());
	}

	@Test
	public void polynomialApply() throws Exception {
		assertEquals(new Complex(-7127982638741417.0, 77712964129057680.0),
				polynomial1.apply(new Complex(12.2112, 123.333)));
	}

	@Test
	public void polynomialDerive() throws Exception {
		assertEquals(new Complex(4327946959927201.0, 835431744117246.8),
				polynomial1.derive().apply(new Complex(12.2112, 123.333)));
	}

	@Test
	public void polynomialMultiply() throws Exception {
		assertEquals(new Complex(-2827187019779307000000000000.0, -1483557994273420600000000000.0),
				polynomial1.multiply(polynomial2).apply(new Complex(12.2112, 123.333)));
	}
	
//	@Test(expected=IllegalArgumentException.class)
//	public void constructorWithNull() throws Exception {
//		new ComplexPolynomial(null);
//	}
	
	@Test(expected=IllegalArgumentException.class)
	public void multupyWithNull() throws Exception {
		polynomial1.multiply(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void aplyWithNull() throws Exception {
		polynomial1.apply(null);
	}
}
