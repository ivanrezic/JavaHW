package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {

	@Test(expected = IllegalArgumentException.class)
	public void zeroAsByteArraySize() {
		Util.indexToByteArray(12, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeNumberAsByteArraySize() {
		Util.indexToByteArray(12, -23);
	}

	@Test
	public void simpleArguments() throws Exception {
		assertArrayEquals(new byte[] { 0, 0 }, Util.indexToByteArray(0, 2));
		assertArrayEquals(new byte[] { 1, 1 }, Util.indexToByteArray(3, 2));
		assertArrayEquals(new byte[] { 0, 0, 1, 1 }, Util.indexToByteArray(3, 4));
		assertArrayEquals(new byte[] { 0, 0, 0, 0, 1, 1 }, Util.indexToByteArray(3, 6));
	}

	@Test
	public void negativeArument() throws Exception {
		assertArrayEquals(new byte[] { 1, 0, 1 }, Util.indexToByteArray(-3, 3));
		assertArrayEquals(new byte[] { 1, 1, 1, 1, 1, 1, 0, 1 }, Util.indexToByteArray(-3, 8));
	}

	@Test
	public void maxIntegerArgument() throws Exception {
		assertArrayEquals(new byte[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1 }, Util.indexToByteArray(2147483647, 32));

	}

	@Test
	public void minIntegerArgument() throws Exception {
		assertArrayEquals(new byte[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 1 }, Util.indexToByteArray(-2147483647, 32));
		assertArrayEquals(new byte[] { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 1 }, Util.indexToByteArray(-2147483647, 33));
	}

	@Test
	public void overflowArguments() throws Exception {
		assertArrayEquals(new byte[] { 0, 0, 1, 1 }, Util.indexToByteArray(19, 4));
		assertArrayEquals(new byte[] { 1, 0, 0, 0 }, Util.indexToByteArray(-120, 4));

	}
}
