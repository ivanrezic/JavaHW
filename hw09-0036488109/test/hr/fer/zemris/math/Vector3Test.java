package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class Vector3Test {
	private static final double PRECISION = 0.001;

	private Vector3 first;
	private Vector3 second;

	@Before
	public void init() {
		first = new Vector3(12.3, -123, 4);
		second = new Vector3(2.3, 0, -0.124);
	}

	@Test
	public void vectorNorm() {
		assertEquals(123.678, first.norm(), PRECISION);
		assertEquals(2.30334, second.norm(), PRECISION);
	}

	@Test(expected = IllegalArgumentException.class)
	public void normalizeZeroVector() throws Exception {
		new Vector3(0, 0, 0).normalized();
	}
	
	@Test
	public void normalizedVector() throws Exception {
		assertEquals(new Vector3(0.99855, 0, -0.053835).getX(), second.normalized().getX(), PRECISION);
		assertEquals(new Vector3(0.99855, 0, -0.053835).getY(), second.normalized().getY(), PRECISION);
		assertEquals(new Vector3(0.99855, 0, -0.053835).getZ(), second.normalized().getZ(), PRECISION);

		assertEquals(new Vector3(0.0994517, -0.994517, 0.032342).getX(), first.normalized().getX(), PRECISION);
		assertEquals(new Vector3(0.0994517, -0.994517, 0.032342).getY(), first.normalized().getY(), PRECISION);
		assertEquals(new Vector3(0.0994517, -0.994517, 0.032342).getZ(), first.normalized().getZ(), PRECISION);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNullVector() throws Exception {
		first.add(null);
	}

	@Test
	public void addVectors() throws Exception {
		assertEquals(new Vector3(14.6, -123, 3.876).getX(), first.add(second).getX(), PRECISION);
		assertEquals(new Vector3(14.6, -123, 3.876).getY(), first.add(second).getY(), PRECISION);
		assertEquals(new Vector3(14.6, -123, 3.876).getZ(), first.add(second).getZ(), PRECISION);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void subtractNullVector() throws Exception {
		first.sub(null);
	}

	@Test
	public void subVectors() throws Exception {
		assertEquals(new Vector3(10, -123, 4.124).getX(), first.sub(second).getX(), PRECISION);
		assertEquals(new Vector3(10, -123, 4.124).getY(), first.sub(second).getY(), PRECISION);
		assertEquals(new Vector3(10, -123, 4.124).getZ(), first.sub(second).getZ(), PRECISION);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void dotProductNullVector() throws Exception {
		first.dot(null);
	}

	@Test
	public void dotProductOfVectors() throws Exception {
		assertEquals(27.794, first.dot(second), PRECISION);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void crossNullVector() throws Exception {
		first.cross(null);
	}

	@Test
	public void crossVectors() throws Exception {
		assertEquals(new Vector3(15.252, 10.7252, 282.9).getX(), first.cross(second).getX(), PRECISION);
		assertEquals(new Vector3(15.252, 10.7252, 282.9).getY(), first.cross(second).getY(), PRECISION);
		assertEquals(new Vector3(15.252, 10.7252, 282.9).getZ(), first.cross(second).getZ(), PRECISION);
	}
	
	@Test
	public void scaleVector() throws Exception {
		assertEquals(new Vector3(24.6, 10.7252, 282.9).getX(), first.scale(2).getX(), PRECISION);
		assertEquals(new Vector3(15.252, 0, 282.9).getY(), first.scale(0).getY(), PRECISION);
		assertEquals(new Vector3(15.252, 10.7252, -8).getZ(), first.scale(-2).getZ(), PRECISION);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cosAngleNull() throws Exception {
		first.cosAngle(null);
	}

	@Test
	public void cosAngleVectors() throws Exception {
		assertEquals(0.09756631775090004, first.cosAngle(second), PRECISION);
	}
}
