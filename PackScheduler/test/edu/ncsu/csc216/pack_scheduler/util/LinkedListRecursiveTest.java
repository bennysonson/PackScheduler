package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * The LinkedListRecursiveTest class ensures that the functionality of LinkedListRecursive is working correctly.
 * 
 * @author Benson Liu
 * @author John Preston
 */
public class LinkedListRecursiveTest {
	
	/** the LinkedList that will used in the test cases */
	private LinkedListRecursive<String> ll;
	/** a string used for testing */
	private String t1 = "t1";
	/** a string used for testing */
	private String t2 = "t2";
	/** a string used for testing */
	private String t3 = "t3";
	/** a string used for testing */
	private String t4 = "t4";
	/** a string used for testing */
	private String t5 = "t5";

	
	/**
	 * Resets the shared LinkedListRecursive for use before each test case.
	 * @throws Exception when the tests could not be set up properly
	 */
	@Before
	public void setUp() throws Exception {
		ll = new LinkedListRecursive<String>();
	}

	/**
	 * Tests that a LinkedListRecursive object can be constructed with the correct initial state.
	 */
	@Test
	public void testLinkedListRecursive() {
		assertNotNull(ll);
		assertTrue(ll.isEmpty());
	}

	/**
	 * Tests that this method can correct detect if the list contains the provided element.
	 */
	@Test
	public void testContains() {
		assertFalse(ll.contains(t1));
		ll.add(0, t1);
		assertTrue(ll.contains(t1));
	}

	/**
	 * Tests that add() can append valid elements to the end of the list and refuse to add null and duplicate elements.
	 */
	@Test
	public void testAddE() {
		assertEquals(0, ll.size());
		assertTrue(ll.isEmpty());
		
		assertTrue(ll.add(t5));
		assertEquals(1, ll.size());
		assertEquals(t5, ll.get(0));
		
		assertTrue(ll.add(t4));
		assertEquals(2, ll.size());
		assertEquals(t5, ll.get(0));
		assertEquals(t4, ll.get(1));
		
		assertTrue(ll.add(t3));
		assertEquals(3, ll.size());
		assertEquals(t5, ll.get(0));
		assertEquals(t4, ll.get(1));
		assertEquals(t3, ll.get(2));
		
		assertFalse(ll.add(null));
		assertFalse(ll.add(t3));
		try {
			ll.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}
		try {
			ll.get(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}
	}

	/**
	 * Tests that valid elements can be added at the given index. If the index or element is invalid, the state of
	 * the list will not change.
	 */
	@Test
	public void testAddIntE() {
		assertEquals(0, ll.size());
		assertTrue(ll.isEmpty());

		// valid additions
		ll.add(0, t3);
		assertEquals(1, ll.size());
		ll.add(0, t2);
		assertEquals(2, ll.size());
		ll.add(2, t4);
		assertEquals(3, ll.size());
		assertFalse(ll.isEmpty());

		// invalid additions
		try {
			ll.add(-1, t1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.add(4, t5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.add(0, t3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.add(0, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(3, ll.size());
		}
	}

	/**
	 * Tests that elements that are already in the list can be removed.
	 */
	@Test
	public void testRemoveE() {
		ll.add(t1);
		ll.add(t2);
		ll.add(t3);
		ll.add(t4);
		ll.add(t5);
		assertEquals(5, ll.size());
		// order of list: [0: t1, 1: t2, 2: t3, 3: t4, 4: t5]
		
		assertFalse(ll.remove(null));
		assertFalse(ll.remove("not in list"));
		
		assertTrue(ll.remove(t1));
		assertEquals(4, ll.size());
		assertEquals(t2, ll.get(0));
		// order of list: [0: t2, 1: t3, 2: t4, 3: t5]
		
		assertTrue(ll.remove(t3));
		assertEquals(3, ll.size());
		assertEquals(t2, ll.get(0));
		assertEquals(t4, ll.get(1));
		assertEquals(t5, ll.get(2));
		// order of list: [0: t2, 1: t4, 2: t5]
		
		assertTrue(ll.remove(t5));
		assertEquals(2, ll.size());
		assertEquals(t2, ll.get(0));
		assertEquals(t4, ll.get(1));
		// order of list: [0: t2, 1: t4]
		
		assertTrue(ll.remove(t2));
		assertFalse(ll.remove("not in list"));
		assertTrue(ll.remove(t4));
		assertFalse(ll.remove(t1));
		
	}

	/**
	 * Tests that the element at the specified valid index is removed. If the index is out of the bounds of the list,
	 * the state of the list will not change.
	 */
	@Test
	public void testRemoveInt() {
		ll.add(t1);
		ll.add(t2);
		ll.add(t3);
		ll.add(t4);
		ll.add(t5);
		assertEquals(5, ll.size());
		// order of list: [0: t1, 1: t2, 2: t3, 3: t4, 4: t5]
		
		try {
			ll.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, ll.size());
		}
		
		try {
			ll.remove(5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, ll.size());
		}
		
		ll.remove(0);
		assertEquals(4, ll.size());
		assertEquals(t2, ll.get(0));
		// order of list: [0: t2, 1: t3, 2: t4, 3: t5]
		
		ll.remove(ll.size() - 1);
		assertEquals(3, ll.size());
		assertEquals(t4, ll.get(2));
		// order of list: [0: t2, 1: t3, 2: t4]
		
		ll.remove(1);
		assertEquals(2, ll.size());
		assertEquals(t2, ll.get(0));
		assertEquals(t4, ll.get(1));
		// order of list: [0: t2, 1: t4]
		
		ll.remove(0);
		assertEquals(1, ll.size());
		assertEquals(t4, ll.get(0));
		// order of list: [0: t4]
		
		ll.remove(0);
		assertEquals(0, ll.size());
	}

	/**
	 * Tests that the element at the specified valid index can be changed to a different valid element. If the index
	 * or new element is not valid, the state of the list will not change.
	 */
	@Test
	public void testSet() {
		ll.add(0, t3);
		ll.add(0, t2);
		ll.add(2, t4);
		// order of list: [0: t2, 1: t3, 2: t4]

		// valid sets
		ll.set(0, t1);
		assertEquals(3, ll.size());
		assertEquals(t1, ll.get(0));
		assertEquals(t3, ll.get(1));

		ll.set(1, t2);
		assertEquals(3, ll.size());
		assertEquals(t1, ll.get(0));
		assertEquals(t2, ll.get(1));
		assertEquals(t4, ll.get(2));

		ll.set(2, t3);
		assertEquals(3, ll.size());
		assertEquals(t3, ll.get(2));
		assertEquals(t2, ll.get(1));
		// order of list: [0: t1, 1: t2, 2: t3]

		// invalid sets
		try {
			ll.set(-1, t4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.set(3, t4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.set(0, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(t1, ll.get(0));
		}

		try {
			ll.set(0, t3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(t1, ll.get(0));
		}
	}

}
