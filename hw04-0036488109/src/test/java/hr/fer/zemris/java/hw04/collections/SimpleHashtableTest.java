package hr.fer.zemris.java.hw04.collections;

import static org.junit.Assert.assertEquals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	@Test(expected = IllegalArgumentException.class)
	public void nonPositiveInitialCapacityTest() {
		new SimpleHashtable<String, Integer>(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullKeyEntryTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		// null key not allowed!
		hashtable.put(null, 13);
	}

	@Test
	public void putNewAndExistingEntriesTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);

		// size should now be 3
		assertEquals(3, hashtable.size());

		hashtable.put("key1", 42);

		// size should remain 3 but value assigned to "key1" should be 42
		assertEquals(3, hashtable.size());
		assertEquals(42, hashtable.get("key1").intValue());
	}

	@Test
	public void clearEntriesTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);

		hashtable.clear();

		// size shuold be zero after clearing
		assertEquals(0, hashtable.size());
	}

	@Test
	public void containsKeyMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);

		// should always be false since hashtable does not allow null keys
		assertEquals(false, hashtable.containsKey(null));

		// should be false since key is not the right type
		assertEquals(false, hashtable.containsKey(9));

		// should be false
		assertEquals(false, hashtable.containsKey("key4"));

		// should be true
		assertEquals(true, hashtable.containsKey("key1"));
	}

	@Test
	public void containsValueMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);
		hashtable.put("null", null);

		// should return true - null values are allowed
		assertEquals(true, hashtable.containsValue(null));

		// should return false
		assertEquals(false, hashtable.containsValue(4));

		// should return true
		assertEquals(true, hashtable.containsValue(1));

	}

	@Test
	public void getMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);
		hashtable.put("null", null);

		// get should return 2
		assertEquals(2, hashtable.get("key2").intValue());

		// get should return null because "key4" does not exist
		assertEquals(null, hashtable.get("key4"));

		// get should return null because it is assigned to "null" key
		assertEquals(null, hashtable.get("null"));
	}

	@Test
	public void removeMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

		// many values in 2 bucket table will also make
		// the table dinamically grow so that part will
		// implicitly be tested too

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		hashtable.put("key3", 3);
		hashtable.put("key4", 1);
		hashtable.put("key5", 2);
		hashtable.put("key6", 3);
		hashtable.put("key7", 1);
		hashtable.put("key8", 2);
		hashtable.put("key9", 3);
		hashtable.put("key10", 1);
		hashtable.put("key11", 2);
		hashtable.put("key12", 3);

		assertEquals(12, hashtable.size());

		// these two calls change nothing
		hashtable.remove("abc");
		hashtable.remove(null);

		// size remains the same
		assertEquals(12, hashtable.size());

		// check if there is "key1" in table after its removal
		assertEquals(true, hashtable.containsKey("key8"));
		hashtable.remove("key8");
		assertEquals(false, hashtable.containsKey("key8"));

		// size should now be 2
		assertEquals(11, hashtable.size());
	}

	@Test
	public void emptyMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);

		hashtable.remove("key1");
		hashtable.remove("key2");

		assertEquals(true, hashtable.isEmpty());
	}

	@Test
	public void toStringMethodTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		assertEquals("[]", hashtable.toString());
		hashtable.put("key1", 1);
		assertEquals("[key1=1]", hashtable.toString());
	}

	@Test
	public void iteratorRemoveAllTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);

		Iterator<TableEntry<String, Integer>> it = hashtable.iterator();

		while (it.hasNext()) {
			it.next();
			it.remove();
		}

		assertEquals(true, hashtable.isEmpty());
	}

	@Test(expected = IllegalStateException.class)
	public void iteratorRemoveWithoutCallingNextFirst() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

		hashtable.put("key1", 1);
		hashtable.put("key2", 2);

		Iterator<TableEntry<String, Integer>> it = hashtable.iterator();

		// should trow exception
		it.remove();
	}

	@Test(expected = IllegalStateException.class)
	public void iteratorRemoveTwiceInARowTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = hashtable.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("key1")) {
				iter.remove();
				// should trow exception now!
				iter.remove();
			}
		}
	}

	@Test
	public void iteratorHasNextEmptyTable() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = hashtable.iterator();
		assertEquals(false, iter.hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void iteratorManyNextCallsTest() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = hashtable.iterator();

		// should trow exception after few runs (python-ish)
		while (true) {
			iter.next();
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void iteratorOutsideStructuralModification() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
		hashtable.put("key1", 1);
		hashtable.put("key2", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = hashtable.iterator();

		while (iter.hasNext()) {
			TableEntry<String, Integer> entry = iter.next();
			if (entry.getKey().equals("key1")) {
				// outside modification should cause next iterator call to throw
				// exception
				hashtable.remove("key1");
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorInvalidCapacity() {
		@SuppressWarnings("unused")
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void putInvalidKeyTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put(null, 5);
	}

	@Test
	public void constructorTableSizeTest() {
		SimpleHashtable<String, Integer> table1 = new SimpleHashtable<>(1);
		assertEquals("Expected table length 1.", 1, table1.table.length);

		SimpleHashtable<String, Integer> table2 = new SimpleHashtable<>(3);
		assertEquals("Expected table length 4.", 4, table2.table.length);

		SimpleHashtable<String, Integer> table3 = new SimpleHashtable<>(32);
		assertEquals("Expected table length 32.", 32, table3.table.length);

		assertEquals("Expected table1 to be empty.", true, table1.isEmpty());
		assertEquals("Expected table2 to be empty.", true, table2.isEmpty());
		assertEquals("Expected table3 to be empty.", true, table3.isEmpty());
	}

	@Test
	public void containsFullTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(7);
		assertEquals("Expected size 0.", 0, table.size());
		assertEquals("Expected key 'null' was not in the table.", false, table.containsKey(null));
		assertEquals("Expected value 'null' was not in the table.", false, table.containsValue(null));

		table.put("Ankica", 15);
		table.put("Brankica", 19);
		assertEquals("Expected table size 2.", 2, table.size());
		assertEquals("Expected 'Florami' was not in the table.", false, table.containsKey("Florami"));
		assertEquals("Expected value 15 was in the table.", true, table.containsValue(15));

		table.put("Cvita", 20);
		assertEquals("Expected table size 3.", 3, table.size());
		assertEquals("Expected 'Ankica' was in the table.", true, table.containsKey("Ankica"));
		assertEquals("Expected 'Brankica' was in the table.", true, table.containsKey("Brankica"));

		table.put("Danica", 12);
		table.put("Eugenija", 30);
		table.put("Florami", 14);
		assertEquals("Expected table size 6.", 6, table.size());

		table.put("Florami", 15);
		assertEquals("Expected table size 6.", 6, table.size());

		assertEquals("Expected that value 20 exists.", true, table.containsValue(20));
		assertEquals("Expected 'Florami' was in the table.", true, table.containsKey("Florami"));
		assertEquals("Expected 'Jelenko' was not in the table.", false, table.containsKey("Jelenko"));
		assertEquals("Expected that value 14 does not exist.", false, table.containsValue(14));
	}

	@Test
	public void removeWithNullKeyTest() {
		// No exception should be thrown here!
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(1);
		table.remove(null);
	}

	@Test
	public void removeFullTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(5);

		assertEquals("Expected table size to be 0.", 0, table.size());
		assertEquals("Expected table length to be 8.", 8, table.table.length);

		table.remove("Ankica");
		table.remove("Eugenija");

		table.put("Ankica", 15);
		table.put("Brankica", 19);
		table.put("Cvita", 20);
		table.put("Danica", 12);
		table.put("Eugenija", 30);
		table.put("Florami", 14);

		table.remove("Cvita");
		table.remove("Brankica");
		assertEquals("Expected table size to be 4.", 4, table.size());

		table.remove("Ankica");
		table.remove("Danica");
		assertEquals("Expected table size to be 2.", 2, table.size());

		table.remove("Eugenija");
		table.remove("Florami");
		assertEquals("Expected table size to be 0.", 0, table.size());
		assertEquals("Expected table to be empty.", true, table.isEmpty());
	}

	@Test
	public void clearTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(9);

		assertEquals("Expected table size to be 0.", 0, table.size());
		assertEquals("Expected table length to be 16.", 16, table.table.length);

		table.put("Ankica", 15);
		table.put("Brankica", 19);
		table.put("Cvita", 20);
		table.put("Cvita", 21);

		assertEquals("Expected table size to be 3.", 3, table.size());
		assertEquals("Expected table length to be 16.", 16, table.table.length);

		table.clear();

		assertEquals("Expected table size to be 0.", 0, table.size());
		assertEquals("Expected table to be empty.", true, table.isEmpty());
		assertEquals("Expected table length to be 16.", 16, table.table.length);

	}

	@Test
	public void iteratorSequence() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(8);
		assertEquals("Expected table length to be 8.", 8, table.table.length);

		table.put("Eugenija", 30);
		table.put("Florami", 14);
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		assertEquals("Expected 'Eugenija=30' to appear.", "Eugenija=30", iterator.next().toString());

		Iterator<TableEntry<String, Integer>> iterator2 = table.iterator();
		iterator2.next();
		iterator2.remove();
		assertEquals("Expected 'Florami=14' to appear.", "Florami=14", iterator2.next().toString());
		assertEquals("Excpected table to look like '[Florami=14]'.", "[Florami=14]", table.toString());
	}

	@Test
	public void iteratorSequence2() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<TableEntry<String, Integer>> iterator = examMarks.iterator();

		// This code should pass without a problem.
		while (iterator.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iterator.next();
			if (pair.getKey().equals("Ivana")) {
				iterator.remove();
			}
		}
	}

	// Test from assignment documentation for Iterator class.
	@Test
	public void iteratorSequencePrintChecker() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		assertEquals("Expected table size to be 4.", 4, examMarks.size());
		assertEquals("Expected table length to be 8.", 8, examMarks.table.length);

		StringBuilder sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			sb.append(pair.getKey() + " => " + pair.getValue() + "\n");
		}
		String result = "Ante => 2\nIvana => 5\nJasna => 2\nKristina => 5\n";
		assertEquals("Result of for iteration is different.", result, sb.toString());

		StringBuilder sb2 = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				sb2.append("(" + pair1.getKey() + " => " + pair1.getValue() + ")" + " - " + "(" + pair2.getKey()
						+ " => " + pair2.getValue() + ")" + "\n"

				);
			}
		}
		String result2 = "(Ante => 2) - (Ante => 2)\n" + "(Ante => 2) - (Ivana => 5)\n" + "(Ante => 2) - (Jasna => 2)\n"
				+ "(Ante => 2) - (Kristina => 5)\n" + "(Ivana => 5) - (Ante => 2)\n" + "(Ivana => 5) - (Ivana => 5)\n"
				+ "(Ivana => 5) - (Jasna => 2)\n" + "(Ivana => 5) - (Kristina => 5)\n" + "(Jasna => 2) - (Ante => 2)\n"
				+ "(Jasna => 2) - (Ivana => 5)\n" + "(Jasna => 2) - (Jasna => 2)\n" + "(Jasna => 2) - (Kristina => 5)\n"
				+ "(Kristina => 5) - (Ante => 2)\n" + "(Kristina => 5) - (Ivana => 5)\n"
				+ "(Kristina => 5) - (Jasna => 2)\n" + "(Kristina => 5) - (Kristina => 5)\n";
		assertEquals("Result2 of for iteration is different.", result2, sb2.toString());

	}

	// Test from assignment documentation for Iterator class.
	@Test(expected = IllegalStateException.class)
	public void iteratorSequenceIllegalSecondRemoveAction() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iterator = examMarks.iterator();
		while (iterator.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iterator.next();
			if (pair.getKey().equals("Ivana")) {
				iterator.remove();
				iterator.remove();
			}
		}
	}

	// Test from assignment documentation for Iterator class.
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorSequenceIllegalRemoveActionOutsideOfIterator() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iterator = examMarks.iterator();
		while (iterator.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iterator.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}

	// Trying to iterate over collection which has no more elements should
	// result in exception.
	@Test(expected = NoSuchElementException.class)
	public void iteratorForcingNoSuchElementException1() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		iterator.next();
	}

	// Trying to iterate over collection which has no more elements should
	// result in exception.
	@Test(expected = NoSuchElementException.class)
	public void iteratorForcingNoSuchElementException2() {
		// Must throw!
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(15);
		table.put("Ankica", 15);
		table.put("Brankica", 19);
		Iterator<TableEntry<String, Integer>> iterator = table.iterator();
		iterator.next();
		iterator.next();
		iterator.next();
	}

	// User should be able to invoke hasNext() method as long as he wants,
	// without there being need for an exception.
	@Test
	public void iteratorEmptyCollection() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>(2);
		Iterator<TableEntry<String, String>> iterator = table.iterator();

		assertEquals("Expected collection to be empty.", false, iterator.hasNext());
		assertEquals("Expected collection to be empty, again.", false, iterator.hasNext());
	}

	// Trying to remove element when iterator is not pointing at any, should
	// result in IllegalStateException.
	@Test(expected = IllegalStateException.class)
	public void iteratorForcingIllegalStateException() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>(2);
		Iterator<TableEntry<String, String>> iterator = table.iterator();
		iterator.remove();
	}

	// Each method performed with the instance of object Iterator, when
	// underlying collection was modified, should result in a
	// ConcurrentModificationException.

	// Checking method hasNext.
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorForcingConcurentModificationException1() {
		// Must throw!
		SimpleHashtable<String, String> table = new SimpleHashtable<>(1);
		table.put("Miško", "brod");
		table.put("Mladen", "automobil");
		table.put("Vedran", "zrakoplov");

		Iterator<TableEntry<String, String>> iterator = table.iterator();
		table.remove("Mladen");
		iterator.hasNext();
	}

	// Checking method remove.
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorForcingConcurentModificationException2() {
		// Must throw!
		SimpleHashtable<String, String> table = new SimpleHashtable<>(4);
		table.put("Miško", "brod");
		table.put("Mladen", "automobil");
		table.put("Vedran", "zrakoplov");

		Iterator<TableEntry<String, String>> iterator = table.iterator();
		table.remove("Miško");
		iterator.remove();
	}

	// Checking method next.
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorForcingConcurentModificationException3() {
		// Must throw!
		SimpleHashtable<String, String> table = new SimpleHashtable<>(22);
		table.put("Miško", "brod");
		table.put("Mladen", "automobil");
		table.put("Vedran", "zrakoplov");

		Iterator<TableEntry<String, String>> iterator = table.iterator();
		table.remove("Vedran");
		iterator.next();
	}
}
