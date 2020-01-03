package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.util.ArrayStack;

/**
 * Test for ArrayStack class
 * @author Benson Liu
 *
 */
public class ArrayStackTest {
	private ArrayStack<String> stack;
	private String s1 = "test1";
	private String s2 = "test2";
	private String s3 = "test3";
	private String s4 = "test4";

	/**
	 * Tests methods of ArrayStack
	 */
	@Test
	public void testArrayStack() {
		// Illegal capacity
		try {
			stack = new ArrayStack<String>(-1);
		} catch (IllegalArgumentException e) {
			// skip
		}

		stack = new ArrayStack<String>(3);
		// Test ArrayStack.isEmpty()
		assertTrue(stack.isEmpty());

		// Test ArrayStack.push()
		assertEquals(0, stack.size());
		stack.push(s1);
		stack.push(s2);
		stack.push(s3);
		assertEquals(3, stack.size());
		try {
			stack.push(s4);
		} catch (IllegalArgumentException e) {
			// skip
		}

		// Test ArrayStack.pop()
		String pop3 = stack.pop();
		assertEquals(2, stack.size());
		assertEquals("test3", pop3);
		stack.pop();
		stack.pop();
		try {
			stack.pop();
		} catch (EmptyStackException e) {
			// skip
		}

	}
}
