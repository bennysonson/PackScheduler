package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Ensures proper functionality of the ArrayQueue class.
 * 
 * @author Pradhan Chetan Venkataramaiah
 *
 */
public class ArrayQueueTest {

	/**
	 * Tests the setCapacity method.
	 */
	@Test
	public void testSetCapacity() {
		try {
			@SuppressWarnings("unused")
			ArrayQueue<String> q = new ArrayQueue<String>(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Tests the size() method.
	 */
	@Test
	public void testSize() {
		ArrayQueue<String> q = new ArrayQueue<String>(1);

		assertTrue(q.isEmpty());
		q.enqueue("Prada");
		assertEquals(1, q.size());
	}

	/**
	 * Tests construction of ArrayQueue object.
	 */
	@Test
	public void testArrayQueue() {
		try {
			new ArrayQueue<String>(0);
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Tests the enqueue() method which is used to add elements to the queue.
	 */
	@Test
	public void testEnqueue() {
		ArrayQueue<String> q = new ArrayQueue<String>(3);

		assertEquals(0, q.size());
		q.enqueue("Prada");
		q.enqueue("Chetan");
		q.enqueue("Venkataramaiah");
		assertEquals(3, q.size());
		try {
			q.enqueue("No name");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}

	}

	/**
	 * Tests the dequeue() method which is used to remove an element from the queue.
	 */
	@Test
	public void testDequeue() {
		ArrayQueue<String> q = new ArrayQueue<String>(3);

		assertEquals(0, q.size());
		q.enqueue("Prada");
		q.enqueue("Chetan");
		q.enqueue("Venkataramaiah");

		assertEquals(q.dequeue(), "Prada");
		q.dequeue();
		assertEquals(1, q.size());
		q.dequeue();
		assertEquals(0, q.size());

		try {
			q.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(null, e.getMessage());
		}

	}
}
