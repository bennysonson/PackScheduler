package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * The LinkedQueueTest class ensures that the functionality of the LinkedQueue
 * class is working correctly.
 * 
 * @author John Preston
 */
public class LinkedQueueTest {
	/** the LinkedQueue object that is used for testing each method */
	private LinkedQueue<String> lq;

	/**
	 * Initializes the private LinkedQueue lq for use in each test case
	 * 
	 * @throws Exception when test cases are unable to be set up correctly
	 */
	@Before
	public void setUp() throws Exception {
		lq = new LinkedQueue<String>(10);
	}

	/**
	 * Tests that a LinkedQueue object could be created with it initialized to a
	 * size of 0 with no elements.
	 */
	@Test
	public void testLinkedQueue() {
		assertTrue(lq.isEmpty());
	}

	/**
	 * Tests that we are able to add to a LinkedQueue.
	 */
	@Test
	public void testEnqueue() {
		assertTrue(lq.isEmpty());
		assertEquals(0, lq.size());
		lq.enqueue("John");
		assertFalse(lq.isEmpty());
		assertEquals(1, lq.size());
	}

	/**
	 * Tests that we are able to remove an element from the front of the queue
	 * correctly.
	 */
	@Test
	public void testDequeue() {
		lq.enqueue("John");
		lq.enqueue("Benson");
		assertEquals(2, lq.size());
		assertEquals("John", lq.dequeue());
		assertEquals(1, lq.size());
		assertEquals("Benson", lq.dequeue());
		assertTrue(lq.isEmpty());
		try {
			lq.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertTrue(lq.isEmpty());
		}
	}

	/**
	 * Tests that the capacity of the LinkedQueue can be changed to a valid
	 * capacity.
	 * 
	 */
	@Test
	public void testSetCapacity() {
		try {
			lq.setCapacity(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, lq.size());
		}

		for (int i = 0; i < 5; i++) {
			lq.enqueue(i + "");
		}
		try {
			lq.setCapacity(3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(5, lq.size());
		}
	}

}