package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArrayIndexedCollectionTest {
	ArrayIndexedCollection first;
	ArrayIndexedCollection second;
	ArrayIndexedCollection emptyOne;

	@Before
	public void initializeArrayIndexedCollections() {
		first = new ArrayIndexedCollection();
		second = new ArrayIndexedCollection(5);
		emptyOne = new ArrayIndexedCollection();
		
		first.add("jabuka");
		first.add("tresnja");
		first.add("visnja");
		first.add("lubenica");
		first.add("naranca");
		first.add("banana");
		
		second.add("1");
		second.add("2");
		second.add("3");
		second.add("4");
		second.add("5");
	}

	@Test
	public void emptyArrayIndexedCollectionSize() {
		assertEquals(first.size(), 6);
	}
	
	@Test
	public void callingArrayConstructorWithCollection() throws Exception {
		ArrayIndexedCollection example1 = new ArrayIndexedCollection(first);
		ArrayIndexedCollection example2 = new ArrayIndexedCollection(second);
		ArrayIndexedCollection example3 = new ArrayIndexedCollection(emptyOne);
		
		assertEquals(example1.size(), 6);
		assertEquals(example2.size(), 5);
		assertEquals(example3.size(), 0);
	}
	
	@Test
	public void checkingSizeOfEmptyArray() throws Exception {
		assertEquals(emptyOne.size(), 0);
	}
	
	@Test
	public void addingNullInArray() throws Exception {
		String actual = null;
		
			try {
				first.add(null);
			} catch (IllegalArgumentException e) {
				actual = "uspjesno";
			}
			
			assertEquals("uspjesno", actual);
	}
	
	@Test
	public void addingElementToTheFullArray() throws Exception {
		second.add("breskva");
		assertEquals(second.size(), 6);
	}
	
	@Test
	public void checkIfArrayContainsNull() throws Exception {
		assertEquals(first.contains(null), false);
	}
	
	@Test
	public void checkIfArrayContainsNonExistingElement() throws Exception {
		assertEquals(first.contains("mango"), false);
	}
	
	@Test
	public void checkIfArrayContainsExistingElement() throws Exception {
		assertEquals(first.contains("jabuka"), true);
		assertEquals(first.contains("tresnja"), true);
		assertEquals(first.contains("visnja"), true);
		assertEquals(first.contains("lubenica"), true);
		assertEquals(first.contains("naranca"), true);
		assertEquals(first.contains("banana"), true);
	}
	
	@Test
	public void removingNullFromArray() throws Exception {
		assertEquals(first.remove(null), false);
	}
	
	@Test
	public void removingNonExistantElement() throws Exception {
		assertEquals(first.remove("carrot"), false);
		
		first.remove("banana");
		assertEquals(first.remove("banana"), false);
	}
	
	@Test
	public void removingFirstArrayElement() throws Exception {
		assertEquals(first.remove("jabuka"), true);
		assertEquals(first.get(0), "tresnja");
		assertEquals(first.get(first.size()-1), "banana");
		assertEquals(first.size(), 5);
	}
	
	@Test
	public void removingLastArrayElement() throws Exception {
		assertEquals(first.remove("banana"), true);
		assertEquals(first.get(first.size()-1), "naranca");
		assertEquals(first.size(), 5);
	}
	
	@Test
	public void removingElementOnFirstIndex() throws Exception {
		first.remove(0);
		assertEquals(first.get(0), "tresnja");
	}
	
	@Test
	public void removingElementOnLastIndex() throws Exception {
		first.remove(first.size()-1);
		assertEquals(first.get(first.size()-1), "naranca");
	}
	
	@Test
	public void convertingArrayIndexCollectionToArray() throws Exception {
		Object[] actual = new Object[]{"jabuka","tresnja","visnja","lubenica","naranca", "banana"};
		assertArrayEquals(first.toArray(), actual);
	}
	
	@Test
	public void convertingEmptyArrayIndexColletionToArray() throws Exception {
		Object[] actual = new Object[]{};
		assertArrayEquals(emptyOne.toArray(),actual);
	}
	
	@Test
	public void clearingArrays() throws Exception {
		first.clear();
		second.clear();
		assertEquals(first.size(), 0);
		assertEquals(second.size(), 0);
	}
	
	@Test
	public void getArrayElement() throws Exception {
		assertEquals(second.get(1), "2");
		assertEquals(second.get(second.size()-1), "5");
	}
	
	@Test
	public void inesrtingElementInTheMiddle() throws Exception {
		first.insert("new", 3);
		assertEquals(first.get(3), "new");
		assertEquals(first.size(), 7);
	}
	
	@Test
	public void inesrtingElementOnTheBeginning() throws Exception {
		first.insert("new", 0);
		assertEquals(first.get(0), "new");
		assertEquals(first.size(), 7);
	}
	
	@Test
	public void inesrtingElementAtTheEnd() throws Exception {
		first.insert("new", first.size()-1);
		assertEquals(first.get(first.size()-1), "banana");
		assertEquals(first.get(first.size()-2), "new");
		assertEquals(first.size(), 7);
	}
	
	@Test
	public void indexOfNull() throws Exception {
		assertEquals(first.indexOf(null), -1);
	}
	
	@Test
	public void indexOfFirstAndLastElement() throws Exception {
		assertEquals(first.indexOf("jabuka"), 0);
		assertEquals(first.indexOf("banana"), first.size()-1);
	}
	
	@Test
	public void addingAllElementsFromOtherCollections() throws Exception {
		emptyOne.addAll(first);
		assertEquals(emptyOne.size(), 6);
		
		emptyOne.addAll(second);
		assertEquals(emptyOne.size(), 11);
	}
}