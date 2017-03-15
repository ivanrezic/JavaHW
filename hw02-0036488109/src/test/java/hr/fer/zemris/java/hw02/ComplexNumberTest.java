package hr.fer.zemris.java.hw02;

import static hr.fer.zemris.java.hw02.ComplexNumber.PRECISION;
import static java.lang.Math.PI;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	
	@Test
	public void parseComplexNumber() throws Exception {
		ComplexNumber n1 = ComplexNumber.parse("510");
		ComplexNumber n2 = ComplexNumber.parse("-510");
		ComplexNumber n3 = ComplexNumber.parse("0.510");
		ComplexNumber n4 = ComplexNumber.parse("-0.510");
		ComplexNumber n5 = ComplexNumber.parse("510i");
		ComplexNumber n6 = ComplexNumber.parse("-510i");
		ComplexNumber n7 = ComplexNumber.parse("0.510i");
		ComplexNumber n8 = ComplexNumber.parse("-0.510i");
		ComplexNumber n10 = ComplexNumber.parse("-1321.1122+0.2332i");
		ComplexNumber n11 = ComplexNumber.parse("123213.0001-51i");
		ComplexNumber n12 = ComplexNumber.parse("-1.1231-232322.9i");
		
		assertTrue(n1.equals(new ComplexNumber(510, 0)));
		assertTrue(n2.equals(new ComplexNumber(-510, 0)));
		assertTrue(n3.equals(new ComplexNumber(0.510, 0)));
		assertTrue(n4.equals(new ComplexNumber(-0.510, 0)));
		assertTrue(n5.equals(new ComplexNumber(0, 510)));
		assertTrue(n6.equals(new ComplexNumber(0, -510)));
		assertTrue(n7.equals(new ComplexNumber(0, 0.510)));
		assertTrue(n8.equals(new ComplexNumber(0, -0.510)));
		assertTrue(n10.equals(new ComplexNumber(-1321.1122, 0.2332)));
		assertTrue(n11.equals(new ComplexNumber(123213.0001, -51)));
		assertTrue(n12.equals(new ComplexNumber(-1.1231, -232322.9)));
	}
	
	@Test
	public void parseComplexNumberWithJustIAsImaginaryPart() throws Exception {
		ComplexNumber n1 = ComplexNumber.parse("-1321.1122+i");
		ComplexNumber n2 = ComplexNumber.parse("123213.0001-i");
		ComplexNumber n3 = ComplexNumber.parse("-1.1231-i");
		ComplexNumber n4 = ComplexNumber.parse("+i");
		ComplexNumber n5 = ComplexNumber.parse("-i");
		ComplexNumber n6 = ComplexNumber.parse("-i + 4");
		//ComplexNumber n7 = ComplexNumber.parse("-12.32iiiiii + 4");
		
		assertTrue(n1.equals(new ComplexNumber(-1321.1122, 1)));
		assertTrue(n2.equals(new ComplexNumber(123213.0001, -1)));
		assertTrue(n3.equals(new ComplexNumber(-1.1231, -1)));
		assertTrue(n4.equals(new ComplexNumber(0, 1)));
		assertTrue(n5.equals(new ComplexNumber(0, -1)));
		assertTrue(n6.equals(new ComplexNumber(4, -1)));
		//assertTrue(n7.equals(new ComplexNumber(4, -12.32)));
	}

	@Test
	public void complexNumberMultiplication() throws Exception {
		ComplexNumber first = c1.mul(c2);
		ComplexNumber second = c2.mul(c3);
		ComplexNumber third = c1.mul(c3);

		assertTrue(first.equals(new ComplexNumber(14, 1.5)));
		assertTrue(second.equals(new ComplexNumber(3.83908, -5.02649)));
		assertTrue(third.equals(new ComplexNumber(3.44270, 4.71695)));
	}

	@Test
	public void complexNumberDivision() throws Exception {
		ComplexNumber first = c1.div(c2);
		ComplexNumber second = c2.div(c3);
		ComplexNumber third = c1.div(c3);

		assertTrue(first.equals(new ComplexNumber(-0.2622951, 0.8852459)));
		assertTrue(second.equals(new ComplexNumber(1.6208287, -1.7850415)));
		assertTrue(third.equals(new ComplexNumber(1.15506, 1.90303)));
	}

	@Test
	public void complexNumberPower() throws Exception {
		ComplexNumber first = c3.power(12);
		ComplexNumber second = c3.power(18);
		ComplexNumber third = c1.power(1);

		assertTrue(second.equals(new ComplexNumber(4244.3719538, -4072.0969226)));
		assertTrue(first.equals(new ComplexNumber(284.4129395, -159.0107504)));
		assertTrue(third.equals(c1));
	}

	@Test
	public void complexNumberRoot() throws Exception {
		ComplexNumber[] first = c3.root(2);
		ComplexNumber[] second = c3.root(3);

		ComplexNumber[] expecteds1 = new ComplexNumber[] { new ComplexNumber(-1.27236, +0.02702),
				new ComplexNumber(1.27236, -0.02702) };
		ComplexNumber[] expecteds2 = new ComplexNumber[] { new ComplexNumber(-0.572726, 1.02525),
				new ComplexNumber(-0.601527, -1.00862), new ComplexNumber(1.17425, -0.01662) };

		assertArrayEquals(expecteds1, first);
		assertArrayEquals(expecteds2, second);

	}

}
