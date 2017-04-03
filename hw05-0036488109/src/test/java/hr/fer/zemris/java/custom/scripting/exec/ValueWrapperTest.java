package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValueWrapperTest {
	ValueWrapper first;
	ValueWrapper second;
	ValueWrapper third;
	ValueWrapper fourth;
	ValueWrapper fifth;
	ValueWrapper sixth;
	ValueWrapper seventh;

	@Before
	public void initAll() {
		first = new ValueWrapper(null);
		second = new ValueWrapper(10);
		third = new ValueWrapper(12.0);
		fourth = new ValueWrapper("5");
		fifth = new ValueWrapper("1.2e1");
		sixth = new ValueWrapper("1.3E2");
		seventh = new ValueWrapper("anica");
	}

	@Test(expected = RuntimeException.class)
	public void addIllegalString() throws Exception {
		first.add("-6.33adsd3e1");
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void addNullToNull() throws Exception {
		first.add(first.getValue());
		assertEquals(0, first.getValue());
	}

	@Test
	public void addIntegerToNull() throws Exception {
		first.add(10);
		assertEquals(10, first.getValue());

		first.add(0);
		assertEquals(10, first.getValue());

		first.add(-10);
		assertEquals(0, first.getValue());
	}

	@Test
	public void addDoubleToNull() throws Exception {
		first.add(5.33);
		assertEquals(5.33, first.getValue());

		first.add(0.0);
		assertEquals(5.33, first.getValue());

		first.add(-5.33);
		assertEquals(0.0, first.getValue());

	}

	@Test
	public void addIntegerAsStringToNull() throws Exception {
		first.add("5");
		assertEquals(5, first.getValue());
	}

	@Test
	public void addIntegerAsStringWithEToNull() throws Exception {
		first.add("5E3");
		assertEquals(5000.0, first.getValue());
	}

	@Test
	public void addIntegerAsStringWithLittleEToNull() throws Exception {
		first.add("-5e2");
		assertEquals(-500.0, first.getValue());
	}

	@Test
	public void addDoubleAsStringToNull() throws Exception {
		first.add(-7.0);
		assertEquals(-7.0, first.getValue());
	}

	@Test
	public void addDoubleAsStringWithEToNull() throws Exception {
		first.add("5.33E1");
		assertEquals(53.3, first.getValue());
	}

	@Test
	public void addDoubleAsStringWithLittleEToNull() throws Exception {
		first.add("-6.33e1");
		assertEquals(-63.3, first.getValue());
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void addNullToInteger() throws Exception {
		second.add(null);
		assertEquals(10, second.getValue());
	}

	@Test
	public void addIntegerToInteger() throws Exception {
		second.add(10);
		assertEquals(20, second.getValue());

		second.add(0);
		assertEquals(20, second.getValue());

		second.add(-10);
		assertEquals(10, second.getValue());
	}

	@Test
	public void addDoubleToInteger() throws Exception {
		second.add(5.33);
		assertEquals(15.33, second.getValue());

		second.add(0.0);
		assertEquals(15.33, second.getValue());

		second.add(-5.33);
		assertEquals(10.0, second.getValue());

	}

	@Test
	public void addIntegerAsStringToInteger() throws Exception {
		second.add("5");
		assertEquals(15, second.getValue());
	}

	@Test
	public void addIntegerAsStringWithEToInteger() throws Exception {
		second.add("5E3");
		assertEquals(5010.0, second.getValue());
	}

	@Test
	public void addIntegerAsStringWithLittleEToInteger() throws Exception {
		second.add("-5e2");
		assertEquals(-490.0, second.getValue());
	}

	@Test
	public void addDoubleAsStringToInteger() throws Exception {
		second.add(-7.0);
		assertEquals(3.0, second.getValue());
	}

	@Test
	public void addDoubleAsStringWithEToInteger() throws Exception {
		second.add("5.33E1");
		assertEquals(63.3, second.getValue());
	}

	@Test
	public void addDoubleAsStringWithLittleEToInteger() throws Exception {
		second.add("-6.33e1");
		assertEquals(-53.3, second.getValue());
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void addNullToDouble() throws Exception {
		third.add(null);
		assertEquals(12.0, third.getValue());
	}

	@Test
	public void addIntegerToDouble() throws Exception {
		third.add(10);
		assertEquals(22.0, third.getValue());

		third.add(0);
		assertEquals(22.0, third.getValue());

		third.add(-10);
		assertEquals(12.0, third.getValue());
	}

	@Test
	public void addDoubleToDouble() throws Exception {
		third.add(5.0);
		assertEquals(17.0, third.getValue());

		third.add(0.0);
		assertEquals(17.0, third.getValue());

		third.add(-4.0);
		assertEquals(13.0, third.getValue());

	}

	@Test
	public void addIntegerAsStringToDouble() throws Exception {
		third.add("5");
		assertEquals(17.0, third.getValue());
	}

	@Test
	public void addIntegerAsStringWithEToDouble() throws Exception {
		third.add("5E3");
		assertEquals(5012.0, third.getValue());
	}

	@Test
	public void addIntegerAsStringWithLittleEToDouble() throws Exception {
		third.add("-5e2");
		assertEquals(-488.0, third.getValue());
	}

	@Test
	public void addDoubleAsStringToDouble() throws Exception {
		third.add(-7.0);
		assertEquals(5.0, third.getValue());
	}

	@Test
	public void addDoubleAsStringWithEToDouble() throws Exception {
		third.add("5.33E1");
		assertEquals(65.3, third.getValue());
	}

	@Test
	public void addDoubleAsStringWithLittleEToDouble() throws Exception {
		third.add("-6.33e1");
		assertEquals(-51.3, third.getValue());
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void addNullToIntegrAsString() throws Exception {
		fourth.add(null);
		assertEquals(5, fourth.getValue());
	}

	@Test
	public void addIntegerToIntegrAsString() throws Exception {
		fourth.add(10);
		assertEquals(15, fourth.getValue());

		fourth.add(0);
		assertEquals(15, fourth.getValue());

		fourth.add(-10);
		assertEquals(5, fourth.getValue());
	}

	@Test
	public void addDoubleToIntegrAsString() throws Exception {
		fourth.add(5.33);
		assertEquals(10.33, fourth.getValue());

		fourth.add(0.0);
		assertEquals(10.33, fourth.getValue());

		fourth.add(-5.33);
		assertEquals(5.0, fourth.getValue());

	}

	@Test
	public void addIntegerAsStringWithEToIntegrAsString() throws Exception {
		fourth.add("5E3");
		assertEquals(5005.0, fourth.getValue());
	}

	@Test
	public void addIntegerAsStringWithLittleEToIntegrAsString() throws Exception {
		fourth.add("-5e2");
		assertEquals(-495.0, fourth.getValue());
	}

	@Test
	public void addDoubleAsStringToIntegrAsString() throws Exception {
		fourth.add(-7.0);
		assertEquals(-2.0, fourth.getValue());
	}

	@Test
	public void addDoubleAsStringWithEToIntegrAsString() throws Exception {
		fourth.add("5.33E1");
		assertEquals(58.3, fourth.getValue());
	}

	@Test
	public void addDoubleAsStringWithLittleEToIntegrAsString() throws Exception {
		fourth.add("-6.33e1");
		assertEquals(-58.3, fourth.getValue());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void compareTest() throws Exception {
		assertEquals(0, first.numCompare(null));
		assertEquals(-1, first.numCompare("5e4"));
		assertEquals(1, first.numCompare("-3.2E2"));
		assertEquals(-1, first.numCompare(5));
	}
}
