package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ComplexRootedPolynomialTest {
	private Complex[] roots1;
	private Complex[] roots2;
	private ComplexRootedPolynomial rooted1;
	private ComplexRootedPolynomial rooted2;

	@Before
	public void init() {
		roots1 = new Complex[] { Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG,
				new Complex(12.2112, 123.333), new Complex(2938.21222, -0.222222), new Complex(-69.69, 0.001),
				new Complex(-123, -123) };
		roots2 = new Complex[] { Complex.ONE, Complex.IM_NEG, new Complex(-2938.21222, -0.222222),
				new Complex(69.69, 0.001), new Complex(-123, 123) };

		rooted1 = new ComplexRootedPolynomial(roots1);
		rooted2 = new ComplexRootedPolynomial(roots2);
	}

	@Test
	public void rootedApply() {
		assertEquals(new Complex(-288.0, 448.0), rooted1.toComplexPolynom().apply(new Complex(12.2112, 123.333)));
		assertEquals(new Complex(241878853279.888820, -805015551437.974100),
				rooted2.apply(new Complex(12.2112, 123.333)));
	}

	@Test
	public void indexOfClosestRoot() throws Exception {
		assertEquals(-1, rooted2.indexOfClosestRootFor(new Complex(2938.21222, -0.222222), 0.001));
		assertEquals(4, rooted2.indexOfClosestRootFor(new Complex(69.69, 0), 0.001));
		assertEquals(1, rooted2.indexOfClosestRootFor(Complex.ONE, 0.001));
	}

	@Test
	public void toComplexPolynom() throws Exception {
		String expected = "(1,000000,i0,000000)z^5 + (2990,522220,i-121,778778)z^4 + (145222,690534,i-349706,146619)z^3 + (-24983634,178253,i25681626,256094)z^2 + (-348286,931221,i-50520037,743813)z^1 + (25183706,896720,i25188239,413116)";
		assertEquals(expected, rooted2.toComplexPolynom().toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullConstructor() throws Exception {
		new ComplexRootedPolynomial(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noRootsGiven() throws Exception {
		new ComplexRootedPolynomial(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentsForIndexOfClosesRoot1() throws Exception {
		rooted1.indexOfClosestRootFor(Complex.IM, -22);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentsForIndexOfClosesRoot2() throws Exception {
		rooted1.indexOfClosestRootFor(null, 22);
	}
}
