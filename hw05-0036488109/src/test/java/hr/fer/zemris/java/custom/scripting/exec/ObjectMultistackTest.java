package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObjectMultistackTest {

	@Test
	public void testStackUsualCase() {
		ObjectMultistack stack = new ObjectMultistack();

		stack.push("first", new ValueWrapper("-5.3e2"));
		assertEquals("-5.3e2", stack.peek("first").getValue());
		assertEquals("-5.3e2", stack.pop("first").getValue());

		assertEquals(true, stack.isEmpty("first"));

		stack.push("first", new ValueWrapper(null));
		stack.push("first", new ValueWrapper(11.3));
		stack.push("first", new ValueWrapper(-2));

		assertEquals(-2, stack.peek("first").getValue());
		stack.peek("first").subtract(4);
		ValueWrapper temp = stack.pop("first");
		assertEquals(-6, temp.getValue());

		assertEquals(11.3, stack.peek("first").getValue());

	}

	@Test(expected = EmptyStackException.class)
	public void popEmpty() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.pop("example");
	}

	@Test(expected = EmptyStackException.class)
	public void peekEmpty() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.peek("example");
	}

	@Test
	public void isEmptyAfterPeekAndPop() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push("First", new ValueWrapper(5));

		assertEquals(false, stack.isEmpty("First"));
		stack.peek("First");
		assertEquals(false, stack.isEmpty("First"));
		stack.pop("First");
		assertEquals(true, stack.isEmpty("First"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void addNullToMapAsValue() throws Exception {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push("first", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNullToMapAsKey() throws Exception {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push(null, new ValueWrapper("5"));
	}
}