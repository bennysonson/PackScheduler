/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;
/**
 * This test class ensures that the InvalidTransitionException class works
 * properly with both the default message as well as the custom message 
 * @author Cameron Dutra
 * @author Benson Liu
 * @author Luis
 *
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException#InvalidTransitionException(java.lang.String)}.
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
	    InvalidTransitionException ite = new InvalidTransitionException("Custom exception message");
	    assertEquals("Custom exception message", ite.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException#InvalidTransitionException()}.
	 */
	@Test
	public void testInvalidTransitionException() {
	    InvalidTransitionException ite = new InvalidTransitionException();
	    assertEquals("Invalid FSM Transition.", ite.getMessage());
	}
}
