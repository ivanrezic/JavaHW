package hr.fer.zemris.math;

import static hr.fer.zemris.math.Complex.PRECISION;
import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ComplexTest {
	Complex c1;
	Complex c2;
	Complex c3;
	Complex c4;
	Complex c5;

	@Before
	public void initializeComplexNumbers() {
		c1 = new Complex(2, 3);
		c2 = new Complex(2.5, -3);
		c3 = c1.add(Complex.fromMagnitudeAndAngle(2, 1.57)).divide(c2).power(3).root(2).get(1);
		c4 = new Complex(0, 345);
		c5 = new Complex(0.12312, 0);
	}

	@Test
	public void ComplexFromMagnitudeAndAngle() throws Exception {
		Complex c6 = Complex.fromMagnitudeAndAngle(c1.module(), c1.angle());
		Complex c7 = Complex.fromMagnitudeAndAngle(c2.module(), c2.angle());
		Complex c8 = Complex.fromMagnitudeAndAngle(c3.module(), c3.angle());
		Complex c9 = Complex.fromMagnitudeAndAngle(c4.module(), c4.angle());

		assertTrue(c6.equals(c1));
		assertTrue(c7.equals(c2));
		assertTrue(c8.equals(c3));
		assertTrue(c9.equals(c4));
	}

	@Test
	public void ComplexMagnitudeTest() {
		assertEquals(3.6055, c1.module(), PRECISION);
		assertEquals(3.90512, c2.module(), PRECISION);
		assertEquals(1.61964, c3.module(), PRECISION);
		assertEquals(345, c4.module(), PRECISION);
		assertEquals(0.12312, c5.module(), PRECISION);
	}

	@Test
	public void ComplexAngleTest() throws Exception {
		assertEquals(0.31283 * PI, c1.angle(), PRECISION);
		assertEquals(-0.27886 * PI + 2 * PI, c2.angle(), PRECISION);
		assertEquals(-0.01352 * PI + 2 * PI, c3.angle(), PRECISION);
		assertEquals(0.5 * PI, c4.angle(), PRECISION);
		assertEquals(0, c5.angle(), PRECISION);
	}

	@Test
	public void ComplexNumberMultiplication() throws Exception {
		Complex first = c1.multiply(c2);
		Complex second = c2.multiply(c3);
		Complex third = c1.multiply(c3);

		assertTrue(first.equals(new Complex(14, 1.5)));
		assertTrue(second.equals(new Complex(3.83908, -5.02649)));
		assertTrue(third.equals(new Complex(3.44270, 4.71695)));
	}

	@Test
	public void ComplexDivision() throws Exception {
		Complex first = c1.divide(c2);
		Complex second = c2.divide(c3);
		Complex third = c1.divide(c3);

		assertTrue(first.equals(new Complex(-0.2622951, 0.8852459)));
		assertTrue(second.equals(new Complex(1.6208287, -1.7850415)));
		assertTrue(third.equals(new Complex(1.15506, 1.90303)));
	}

	@Test
	public void ComplexPower() throws Exception {
		Complex first = c3.power(12);
		Complex second = c3.power(18);
		Complex third = c1.power(1);

		assertTrue(second.equals(new Complex(4244.3719538, -4072.0969226)));
		assertTrue(first.equals(new Complex(284.4129395, -159.0107504)));
		assertTrue(third.equals(c1));
	}

	@Test
	public void ComplexRoot() throws Exception {
		List<Complex> first = c3.root(2);
		List<Complex> second = c3.root(3);

		List<Complex> expecteds1 = new ArrayList<>(
				Arrays.asList(new Complex(-1.27236, +0.02702), new Complex(1.27236, -0.02702)));
		List<Complex> expecteds2 = new ArrayList<>(Arrays.asList(new Complex(-0.572726, 1.02525),
				new Complex(-0.601527, -1.00862), new Complex(1.17425, -0.01662)));

		assertEquals(expecteds1.get(1), first.get(1));
		assertEquals(expecteds2.get(1), second.get(1));
	}
}
