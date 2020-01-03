/*
 * 
 */
package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The Class SortedListTest.
 * @author Cameron Dutra
 * @author Tanmay Jain
 * @author Boxiang Ma
 */
public class SortedListTest {

	/**
	 * Test sorted list.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		list.add("banana");
		list.add("pear");
		list.add("strawberry");
		list.add("blueberry");
		list.add("grape");
		list.add("apple");
		list.add("watermelon");
		list.add("melon");
		list.add("pineapple");
		list.add("mango");
		list.add("guava");
		assertEquals(11, list.size());
		
		//TODO Test that the list grows by adding at least 11 elements
				//Remember the list's initial capacity is 10
		
		
		SortedList<Integer> list1 = new SortedList<Integer>();
		list1.add(1);
		assertEquals(1, list1.size());
		assertFalse(list1.contains(2));
	}

	/**
	 * Test add.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		list.add("apple");
		assertEquals("apple", list.get(0));
		list.add("mango");
		assertEquals("mango", list.get(2));
		list.add("guava");
		assertEquals("guava", list.get(2));
		try {
			list.add("apple");
			fail();
		} catch (IllegalArgumentException e1) {
			assertEquals("apple", list.get(0));
		}
		try {
			list.add(null);
			fail();
		} catch (NullPointerException e) {
			//null pointer exception caught
		}
		
		
		//TODO Test adding to the front, middle and back of the list
		
		//TODO Test adding a null element
		
		//TODO Test adding a duplicate element
	}
	
	/**
	 * Test get.
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		try {
			list.get(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			//Index out of bound exception caught
			
		}
		
		list.add("apple");
		list.add("banana");
		list.add("mango");
		
		try {
			list.get(-1);
			fail();
		} catch(IndexOutOfBoundsException e) {
			//Index out of bound exception caught
		}
		assertEquals("apple", list.get(0));
		assertEquals(3, list.size());
		
		try {
			list.get(list.size());
			fail();
		} catch(IndexOutOfBoundsException e) {
			//Index out of bound exception caught
		}
		
		
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		//TODO Test getting an element from an empty list
		
		//TODO Add some elements to the list
		
		//TODO Test getting an element at an index < 0
		
		//TODO Test getting an element at size
		
	}
	
	/**
	 * Test remove.
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		try {
			list.remove(0);
			fail();
		} catch(IndexOutOfBoundsException e) {
			//Index out of bound exception caught
		}
		
		list.add("apple");
		list.add("banana");
		list.add("mango");
		list.add("guava");
		list.add("melon");
		list.add("peach");
		
		try {
			list.remove(-1);
			fail();
		} catch(IndexOutOfBoundsException e) {
			//Index out of bound exception caught
		}
		
		try {
			list.remove(list.size());
			fail();
		} catch(IndexOutOfBoundsException e) {
			//Index out of bound exception caught
		}
		
		try {
			list.remove(2);
			assertEquals("mango", list.get(2));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		
		try {
			list.remove(list.size() - 1);
			assertEquals("melon", list.get(list.size() - 1));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		
		try {
			list.remove(0);
			assertEquals("banana", list.get(0));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		try {
			list.remove(list.size() - 1);
			assertEquals("mango", list.get(list.size() - 1));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		//TODO Test removing from an empty list
		
		//TODO Add some elements to the list - at least 4
		
		//TODO Test removing an element at an index < 0
		
		//TODO Test removing an element at size
		
		//TODO Test removing a middle element
		
		//TODO Test removing the last element
		
		//TODO Test removing the first element
		
		//TODO Test removing the last element
	}
	
	/**
	 * Test index of.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		try {
			assertEquals(-1, list.indexOf(""));
		}  catch (Exception e) {
			//Exception caught
		}
		
		list.add("apple");
		list.add("banana");
		list.add("mango");
		list.add("guava");
		list.add("melon");
		list.add("peach");
		list.remove(list.indexOf("mango"));
		
		try {
			assertEquals(0, list.indexOf("apple"));
		}  catch (Exception e) {
			fail();
		}
		try {
			assertEquals(1, list.indexOf("banana"));
		}  catch (Exception e) {
			//Exception caught
		}
		try {
			assertEquals(-1, list.indexOf("pineapple"));
		}  catch (Exception e) {
			//Exception caught
		}
		try {
			assertEquals(-1, list.indexOf("pear"));
		}  catch (Exception e) {
			//Exception caught			
		}
		try {
			assertEquals(-1, list.indexOf("mango"));
		}  catch (Exception e) {
			//Exception caught
		}
		try {
			list.indexOf(null);
			fail();
		}  catch (NullPointerException e) {
			//Null pointer Exception caught
		}
		
		
		//TODO Test indexOf on an empty list
		
		//TODO Add some elements
		
		//TODO Test various calls to indexOf for elements in the list
		//and not in the list
		
		//TODO Test checking the index of null
		
	}
	
	/**
	 * Test clear.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();
		list.add("apple");
		list.add("banana");
		list.add("mango");
		list.add("guava");
		list.add("melon");
		list.add("peach");
		
		list.clear();
		
		assertEquals(0, list.size());

		//TODO Add some elements
		
		//TODO Clear the list
		
		//TODO Test that the list is empty
	}

	/**
	 * Test is empty.
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		assertTrue(list.isEmpty());
		list.add("apple");
		list.add("banana");
		list.add("mango");
		list.add("guava");
		list.add("melon");
		list.add("peach");
		
		assertFalse(list.isEmpty());
		
		
		//TODO Test that the list starts empty
		
		//TODO Add at least one element
		
		//TODO Check that the list is no longer empty
	}

	/**
	 * Test contains.
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		assertFalse(list.contains(""));
		
		list.add("apple");
		list.add("banana");
		list.add("mango");
		list.add("guava");
		list.add("melon");
		list.add("peach");
		
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("banana"));
		assertFalse(list.contains("pineapple"));
		assertFalse(list.contains("pear"));
		
		//TODO Test the empty list case
		
		//TODO Add some elements
		
		//TODO Test some true and false cases
	}
	
	/**
	 * Test equals.
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("apple");
		list1.add("banana");
		list1.add("mango");
		list1.add("guava");
		
		list2.add("apple");
		list2.add("banana");
		list2.add("mango");
		list2.add("guava");
		
		list3.add("guava");
		list3.add("melon");
		list3.add("peach");
		
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		
		assertFalse(list1.equals(list3));
		assertFalse(list2.equals(list3));
		
		//TODO Make two lists the same and one list different
		
		//TODO Test for equality and non-equality
	}
	
	/**
	 * Test hash code.
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		
		list1.add("apple");
		list1.add("banana");
		list1.add("mango");
		list1.add("guava");
		
		list2.add("apple");
		list2.add("banana");
		list2.add("mango");
		list2.add("guava");
		
		list3.add("guava");
		list3.add("melon");
		list3.add("peach");
		
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
		//TODO Make two lists the same and one list different
		
		//TODO Test for the same and different hashCodes
	}
}