package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LinkedListIndexedCollectionTest {

	ArrayIndexedCollection array;
	LinkedListIndexedCollection first;
	LinkedListIndexedCollection second;
	LinkedListIndexedCollection emptyOne;

	@Before
	public void initializeArrayIndexedCollections() {
		array = new ArrayIndexedCollection();
		second = new LinkedListIndexedCollection(first);
		emptyOne = new LinkedListIndexedCollection();

		array.add("jabuka");
		array.add("tresnja");
		array.add("visnja");
		array.add("lubenica");
		array.add("naranca");
		array.add("banana");

		first = new LinkedListIndexedCollection(array);

		second.add("1");
		second.add("2");
		second.add("3");
		second.add("4");
		second.add("5");
	}

	@Test
	public void LinkedListIndexedCollectionSize() {
		assertEquals(first.size(), 6);
	}

	@Test
	public void callingListConstructorWithCollection() throws Exception {
		LinkedListIndexedCollection example1 = new LinkedListIndexedCollection(first);
		LinkedListIndexedCollection example2 = new LinkedListIndexedCollection(second);
		// ArrayIndexedCollection example3 = new
		// ArrayIndexedCollection(emptyOne);

		assertEquals(example1.size(), 6);
		assertEquals(example2.size(), 5);
	}

	@Test
	public void checkingSizeOfEmptyList() throws Exception {
		assertEquals(emptyOne.size(), 0);
	}

	@Test
	public void addingNullInList() throws Exception {
		String actual = null;

		try {
			first.add(null);
		} catch (IllegalArgumentException e) {
			actual = "uspjesno";
		}

		assertEquals("uspjesno", actual);
	}

	@Test
	public void addingElementToTheFullList() throws Exception {
		second.add("breskva");
		assertEquals(second.size(), 6);
	}

	@Test
	public void checkIfListContainsNull() throws Exception {
		assertEquals(first.contains(null), false);
	}

	@Test
	public void checkIfListContainsNonExistingElement() throws Exception {
		assertEquals(first.contains("mango"), false);
	}

	@Test
	public void checkIfListContainsExistingElement() throws Exception {
		assertEquals(first.contains("jabuka"), true);
		assertEquals(first.contains("tresnja"), true);
		assertEquals(first.contains("visnja"), true);
		assertEquals(first.contains("lubenica"), true);
		assertEquals(first.contains("naranca"), true);
		assertEquals(first.contains("banana"), true);
	}

	@Test
	public void removingNullFromList() throws Exception {
		assertEquals(first.remove(null), false);
	}

	@Test
	public void removingNonExistantElement() throws Exception {
		assertEquals(first.remove("carrot"), false);

		first.remove("banana");
		assertEquals(first.remove("banana"), false);
	}

	@Test
	public void removingFirstListElement() throws Exception {
		assertEquals(first.remove("jabuka"), true);
		assertEquals(first.get(0), "tresnja");
		assertEquals(first.get(first.size() - 1), "banana");
		assertEquals(first.size(), 5);
	}

	@Test
	public void removingLastListElement() throws Exception {
		assertEquals(first.remove("banana"), true);
		assertEquals(first.get(first.size() - 1), "naranca");
		assertEquals(first.size(), 5);
	}

	@Test
	public void removingElementOnFirstIndex() throws Exception {
		first.remove(0);
		assertEquals(first.get(0), "tresnja");
	}

	@Test
	public void removingElementOnLastIndex() throws Exception {
		first.remove(first.size() - 1);
		assertEquals(first.get(first.size() - 1), "naranca");
	}

	@Test
	public void convertingLinkedListIndexCollectionToArray() throws Exception {
		Object[] actual = new Object[] { "jabuka", "tresnja", "visnja", "lubenica", "naranca", "banana" };
		assertArrayEquals(first.toArray(), actual);
	}

	@Test
	public void convertingEmptyLinkedListIndexColletionToArray() throws Exception {
		Object[] actual = new Object[] {};
		assertArrayEquals(emptyOne.toArray(), actual);
	}

	@Test
	public void clearingLists() throws Exception {
		first.clear();
		second.clear();
		assertEquals(first.size(), 0);
		assertEquals(second.size(), 0);
	}

	@Test
	public void getListElement() throws Exception {
		assertEquals(second.get(1), "2");
		assertEquals(second.get(second.size() - 1), "5");
	}

	@Test
	public void inesrtingElementInTheMiddle() throws Exception {
		first.insert("new", 3);
		assertEquals(first.get(3), "new");
		assertEquals(first.size(), 7);
	}

	@Test
	public void insertingElementAfterLastElementInList() throws Exception {
		first.insert("new", first.size());
		assertEquals(first.get(first.size() - 1), "new");
		assertEquals(first.size(), 7);
	}

	@Test
	public void inesrtingElementAtTheBeginning() throws Exception {
		first.insert("new", 0);
		assertEquals(first.get(0), "new");
		assertEquals(first.size(), 7);
	}

	@Test
	public void inesrtingElementAtTheEnd() throws Exception {
		first.insert("new", first.size() - 1);
		assertEquals(first.get(first.size() - 1), "banana");
		assertEquals(first.get(first.size() - 2), "new");
		assertEquals(first.size(), 7);
	}

	@Test
	public void indexOfNull() throws Exception {
		assertEquals(first.indexOf(null), -1);
	}

	@Test
	public void indexOfFirstAndLastElement() throws Exception {
		assertEquals(first.indexOf("jabuka"), 0);
		assertEquals(first.indexOf("banana"), first.size() - 1);
	}

	@Test
	public void addingAllElementsFromOtherCollections() throws Exception {
		emptyOne.addAll(first);
		assertEquals(emptyOne.size(), 6);

		emptyOne.addAll(second);
		assertEquals(emptyOne.size(), 11);
	}

}
