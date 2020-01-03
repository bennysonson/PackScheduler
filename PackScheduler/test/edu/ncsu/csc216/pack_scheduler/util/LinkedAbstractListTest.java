/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Ensures proper functionality of the LinkedAbstractList class
 * @author Cameron Dutra
 *
 */
public class LinkedAbstractListTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#size()}.
	 */
	@Test
	public void testSize() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "Cameron");
		list.add(1, "Blake");
		assertEquals(2, list.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#LinkedAbstractList(int)}.
	 */
	@Test
	public void testLinkedAbstractList() {
		try {
			new LinkedAbstractList<String>(10);
		} catch (Exception e) {
			fail();
		}
		
		try {
			new LinkedAbstractList<String>(-1);
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#add(int, java.lang.Object)}.
	 */
	@Test
	public void testAddIntE() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "Cameron");
		list.add(1, "Blake");
		assertEquals(2, list.size());
		list.add(1, "Tyler");
		assertEquals("Blake", list.get(2));
		try {
			list.add(3, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(null, e.getMessage());
		}
		
		try {
			list.add(-1, "Cameron");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
		
		try {
			list.add(1, "Cameron");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#remove(int)}.
	 */
	@Test
	public void testRemoveInt() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "Cameron");
		list.add(1, "Blake");
		assertEquals(2, list.size());
		list.remove(1);
		assertEquals(1, list.size());
		try {
			list.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
		assertEquals("Cameron", list.remove(0));
		assertEquals(0, list.size());
		list.add(0, "Cameron");
		list.add(1, "Blake");
		list.add(2, "Tyler");
		list.add(3, "Chandler");
		list.add(4, "Lauryn");
		assertEquals(5, list.size());
		list.remove(2);
		assertEquals("Lauryn", list.get(3));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#set(int, java.lang.Object)}.
	 */
	@Test
	public void testSetIntE() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "Cameron");
		list.add(1, "Blake");
		assertEquals(2, list.size());
		list.set(1, "Tyler");
		assertEquals("Tyler", list.get(1));
		try {
			list.set(1, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(null, e.getMessage());
		}
		
		try {
			list.add(-1, "Cameron");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList#get(int)}.
	 */
	@Test
	public void testGetInt() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "Cameron");
		list.add(1, "Blake");
		assertEquals(2, list.size());
		try {
			list.get(-1);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
	}

}
