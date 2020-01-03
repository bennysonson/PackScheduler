package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * The LinkedListTest class ensures that the functionality of LinkedList is working correctly.
 * 
 * @author Benson Liu
 * @author John Preston
 */
public class LinkedListTest {

	/** the LinkedList that will used in the test cases */
	private LinkedList<String> ll;
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
	 * Resets the shared LinkedList for use before each test case.
	 * @throws Exception when the tests could not be set up properly
	 */
	@Before
	public void setUp() throws Exception {
		ll = new LinkedList<String>();
	}

	/**
	 * Ensures that a LinkedList object could be constructed with the correct initial state.
	 */
	@Test
	public void testLinkedList() {
		assertNotNull(ll);
		assertTrue(ll.isEmpty());
	}

	/**
	 * Tests that the method returns a valid iterator for the LinkedList that can iterate throughout the list
	 * and manipulate its elements.
	 */
	@Test
	public void testListIteratorInt() {
		ll.add(0, t3);
		ll.add(0, t2);
		ll.add(2, t4);
		// order of list: [0: t2, 1: t3, 2: t4]

		ListIterator<String> li = ll.listIterator(0);
		assertFalse(li.hasPrevious());

		try {
			li.previous();
		} catch (NoSuchElementException e) {
			assertEquals(-1, li.previousIndex());
			assertEquals(0, li.nextIndex());
		}

		try {
			li.set(t1);
		} catch (IllegalStateException e) {
			assertEquals(-1, li.previousIndex());
			assertEquals(0, li.nextIndex());
		}

		try {
			li.remove();
		} catch (IllegalStateException e) {
			assertEquals(-1, li.previousIndex());
			assertEquals(0, li.nextIndex());
		}

		assertEquals(t2, li.next());
		assertTrue(li.hasPrevious());

		assertEquals(t2, li.previous());

		// removing t2
		li.remove();
		assertEquals(-1, li.previousIndex());
		assertEquals(0, li.nextIndex());
		assertEquals(2, ll.size());
		assertEquals(t3, ll.get(0));
	}

	/**
	 * Tests that an element at the provided index can be set to another valid element.
	 */
	@Test
	public void testSetIntE() {
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
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.set(3, t4);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.set(0, null);
		} catch (NullPointerException e) {
			assertEquals(t1, ll.get(0));
		}

		try {
			ll.set(0, t3);
		} catch (IllegalArgumentException e) {
			assertEquals(t1, ll.get(0));
		}
	}

	/**
	 * Tests that elements can be added to the front, middle and back of the LinkedList and that the size
	 * is incremented.
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
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.add(4, t5);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.add(0, t3);
		} catch (IllegalArgumentException e) {
			assertEquals(3, ll.size());
		}

		try {
			ll.add(0, null);
		} catch (NullPointerException e) {
			assertEquals(3, ll.size());
		}
	}

}