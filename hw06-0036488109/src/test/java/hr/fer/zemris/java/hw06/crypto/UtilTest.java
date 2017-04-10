package hr.fer.zemris.java.hw06.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {

	@Test
	public void hexToByte() {
		assertArrayEquals(new byte[]{1,-82,34}, Util.hextobyte("01aE22"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hexToByteWithOddSize() throws Exception {
		assertArrayEquals(new byte[]{1,-82,34}, Util.hextobyte("01aE2"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hexToByteWithIllegalCharacters() throws Exception {
		assertArrayEquals(new byte[]{1,-82,34}, Util.hextobyte("01až2"));
		assertArrayEquals(new byte[]{1,-82,34}, Util.hextobyte("01ak2"));
		assertArrayEquals(new byte[]{1,-82,34}, Util.hextobyte("01al2"));
		assertArrayEquals(new byte[]{1,-82,34}, Util.hextobyte("01a.2"));
		assertArrayEquals(new byte[]{1,-82,34}, Util.hextobyte("01a*2"));
	}
	
	@Test
	public void byteToHex() throws Exception {
		assertEquals("01ae22", Util.bytetohex(new byte[]{1,-82,34}));
	}
	
	@Test
	public void zeroLengthByteArrayToHex() throws Exception {
		assertEquals("", Util.bytetohex(new byte[]{}));
	}

}
