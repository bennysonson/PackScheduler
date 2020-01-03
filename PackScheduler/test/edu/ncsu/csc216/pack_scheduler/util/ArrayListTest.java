/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for ArrayList class
 * 
 * @author Benson Liu
 *
 */
public class ArrayListTest {

	private ArrayList<String> array;
	private String s1 = "test1";
	private String s2 = "test2";
	private String s3 = "test3";
	private String s4 = "test4";

	/**
	 * Test for add() method
	 */
	@Test
	public void testAdd() {
		array = new ArrayList<String>();
		array.add(0, s1);
		assertEquals("test1", array.get(0));
		array.add(0, s2);
		assertEquals("test2", array.get(0));
		array.add(1, s4);
		assertEquals("test4", array.get(1));

		try {
			array.add(68, s3);
		} catch (IndexOutOfBoundsException e) {
			// skip
		}

		try {
			array.add(s4);
		} catch (IllegalArgumentException e) {
			// skip
		}

		try {
			array.remove(7);
		} catch (IndexOutOfBoundsException e) {
			// skip
		}
	}

	/**
	 * Test for set() method
	 */
	@Test
	public void testSet() {
		array = new ArrayList<String>();
		array.add(s1);
		array.add(s2);
		array.add(s3);
		assertEquals(3, array.size());
		array.set(1, s4);
		assertEquals(s4, array.get(1));
		try {
			array.set(1, null);
		} catch (NullPointerException e) {
			// skip
		}

		try {
			array.set(39, "yes");
		} catch (IndexOutOfBoundsException e) {
			// skip
		}

		try {
			array.set(49, "yes");
		} catch (IndexOutOfBoundsException e) {
			// skip
		}

		try {
			array.get(38);
		} catch (IndexOutOfBoundsException e) {
			// skip
		}
	}
}
