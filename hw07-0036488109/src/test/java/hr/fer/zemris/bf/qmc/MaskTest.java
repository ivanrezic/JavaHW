package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class MaskTest {
	private Set<Integer> set1;
	private Set<Integer> set2;
	private Set<Integer> set3;

	/** asd 1. */
	private Mask asd1;
	private Mask asd2;

	@Before
	public void init() {
		set1 = new TreeSet<>();
		set1.add(13);
		set1.add(6);

		set2 = new TreeSet<>();
		set2.add(12);
		set2.add(8);

		set3 = new TreeSet<>();

		asd1 = new Mask(new byte[] { 2, 1, 1, 0 }, set1, true);
		asd2 = new Mask(new byte[] { 2, 1, 0, 0 }, set2, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void initMask() {
		new Mask(null, set1, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void initMask2() {
		new Mask(new byte[] { 2, 1, 1, 0 }, set3, true);
	}

	@Test
	public void testIsCombined() throws Exception {
		assertEquals(false, asd1.isCombined());
		assertEquals(false, asd2.isCombined());
	}

	@Test
	public void testIsCombined2() throws Exception {
		asd1.combineWith(asd2);

		assertEquals(false, asd1.isCombined());
		assertEquals(false, asd2.isCombined());
	}

	@Test
	public void maskToString() throws Exception {
		assertEquals("-110 D   [6, 13]", asd1.toString());
		assertEquals("-100 D   [8, 12]", asd2.toString());
	}
}
