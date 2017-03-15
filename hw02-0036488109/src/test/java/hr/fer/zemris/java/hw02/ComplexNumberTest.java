package hr.fer.zemris.java.hw02;

import static java.lang.Math.PI;
import static hr.fer.zemris.java.hw02.ComplexNumber.PRECISION;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ComplexNumberTest {
	ComplexNumber c1;
	ComplexNumber c2;
	ComplexNumber c3;
	ComplexNumber c4;
	ComplexNumber c5;

	@Before
	public void initializeComplexNumbers() {
		c1 = new ComplexNumber(2, 3);
		c2 = ComplexNumber.parse("2.5-3i");
		c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		c4 = new ComplexNumber(0, 345);
		c5 = new ComplexNumber(0.12312, 0);
	}

	@Test
	public void complexNumberFromReal() throws Exception {
		ComplexNumber c6 = ComplexNumber.fromReal(24512231);
		ComplexNumber c7 = ComplexNumber.fromReal(0.245123123123123);
		ComplexNumber c8 = ComplexNumber.fromReal(-0.245);
		ComplexNumber c9 = ComplexNumber.fromReal(0);

		assertTrue(c6.equals(new ComplexNumber(24512231, 0)));
		assertTrue(c7.equals(new ComplexNumber(0.245123123123123, 0)));
		assertTrue(c8.equals(new ComplexNumber(-0.245, 0)));
		assertTrue(c9.equals(new ComplexNumber(0, 0)));
	}

	@Test
	public void complexNumberFromImaginary() throws Exception {
		ComplexNumber c6 = ComplexNumber.fromImaginary(24512231);
		ComplexNumber c7 = ComplexNumber.fromImaginary(0.245123123123123);
		ComplexNumber c8 = ComplexNumber.fromImaginary(-0.245);
		ComplexNumber c9 = ComplexNumber.fromImaginary(0);

		assertTrue(c6.equals(new ComplexNumber(0, 24512231)));
		assertTrue(c7.equals(new ComplexNumber(0, 0.245123123123123)));
		assertTrue(c8.equals(new ComplexNumber(0, -0.245)));
		assertTrue(c9.equals(new ComplexNumber(0, 0)));
	}
	
	@Test
	public void complexNumberFromMagnitudeAndAngle() throws Exception {
		ComplexNumber c6 = ComplexNumber.fromMagnitudeAndAngle(c1.getMagnitude(), c1.getAngle());
		ComplexNumber c7 = ComplexNumber.fromMagnitudeAndAngle(c2.getMagnitude(), c2.getAngle());
		ComplexNumber c8 = ComplexNumber.fromMagnitudeAndAngle(c3.getMagnitude(), c3.getAngle());
		ComplexNumber c9 = ComplexNumber.fromMagnitudeAndAngle(c4.getMagnitude(), c4.getAngle());
		

		assertTrue(c6.equals(c1));
		assertTrue(c7.equals(c2));
		assertTrue(c8.equals(c3));
		assertTrue(c9.equals(c4));
	}

	@Test
	public void complexNumberMagnitudeTest() {
		assertEquals(3.6055, c1.getMagnitude(), PRECISION);
		assertEquals(3.90512, c2.getMagnitude(), PRECISION);
		assertEquals(1.61964, c3.getMagnitude(), PRECISION);
		assertEquals(345, c4.getMagnitude(), PRECISION);
		assertEquals(0.12312, c5.getMagnitude(), PRECISION);
	}

	@Test
	public void complexNumberAngleTest() throws Exception {
		assertEquals(0.31283 * PI, c1.getAngle(), PRECISION);
		assertEquals(-0.27886 * PI + 2 * PI, c2.getAngle(), PRECISION);
		assertEquals(-0.01352 * PI + 2 * PI, c3.getAngle(), PRECISION);
		assertEquals(0.5 * PI, c4.getAngle(), PRECISION);
		assertEquals(0, c5.getAngle(), PRECISION);
	}

}
