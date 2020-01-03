package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The CourseNameValidatorFSMTest determines if a provided course name 
 * is valid based on the criteria given by a valid course name 
 * @author Cameron Dutra
 *
 */
public class CourseNameValidatorFSMTest {
	/**
	 * Creates an finite state machine to determine if a course name is valid
	 */
	private CourseNameValidatorFSM validator = new CourseNameValidatorFSM();
	
	/**
	 * tests method isValid()
	 * @throws InvalidTransitionException if the transition between states cannot be made
	 */
	@Test
	public void testIsValid() throws InvalidTransitionException {
		assertTrue(validator.isValid("C216"));
		assertTrue(validator.isValid("CS216"));
		assertTrue(validator.isValid("CSC216"));
		assertTrue(validator.isValid("CSCA216"));
		assertTrue(validator.isValid("C216F"));
		assertFalse(validator.isValid("CSC21"));

		try {
			validator.isValid(" ");
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		try {
			validator.isValid("2");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
		
		try {
			validator.isValid("CSCSC"); 
			fail();
			} catch (InvalidTransitionException e) {
				assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
			}
		
		try {
			validator.isValid("CSC2C");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
		
		try {
			validator.isValid("CSC22C");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
		
		try {
			validator.isValid("CSC6969");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
		
		try {
			validator.isValid("CSC216FF");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
		
		try {
			validator.isValid("CSC216F9");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
		
		
		}

}
