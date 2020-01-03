package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Test for LinkedStack class
 * @author Benson Liu
 *
 */
public class LinkedStackTest {

	private LinkedStack<String> stack;
	private String s1 = "test1";
	private String s2 = "test2";
	private String s3 = "test3";
	
	/**
	 * Tests the methods of LinkedStackTest
	 */
	@Test
	public void test() {
		stack = new LinkedStack<String>(3);
		
		//Test LinkedStack.pop() when size is 0
		try {
			stack.pop();
		} catch (EmptyStackException e) {
			//skip
		}
		
		//Test LinkedStack.isEmpty()
		assertTrue(stack.isEmpty());
		
		//Test LinkedStack.push()
		stack.push(s1);
		stack.push(s2);
		stack.push(s3);
		assertEquals(3, stack.size());
		
		//Test LinkedStack.pop()
		String test1 = stack.pop();
		assertEquals("test3", test1);
		
		
	}

}
