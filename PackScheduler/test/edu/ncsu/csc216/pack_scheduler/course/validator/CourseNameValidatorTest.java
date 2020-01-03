package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the CourseNameValidator class for valid and invalid course names
 * @author Benson Liu
 *
 */
public class CourseNameValidatorTest {
	
	/**
	 * Tests a valid course name
	 * @throws InvalidTransitionException for invalid transitions in state machine
	 */
	@Test
	public void testIsValid() throws InvalidTransitionException {
		CourseNameValidator course = new CourseNameValidator();
		assertTrue(course.isValid("CSC216"));
		CourseNameValidator c = new CourseNameValidator();
		assertTrue(c.isValid("CS214A"));
		CourseNameValidator co = new CourseNameValidator();
		assertTrue(co.isValid("HESF101"));
		CourseNameValidator cou = new CourseNameValidator();
		assertTrue(cou.isValid("AB213"));
		CourseNameValidator cour = new CourseNameValidator();
		assertTrue(cour.isValid("C100"));
	}
	
	/**
	 * Tests invalid course name
	 * @throws InvalidTransitionException for invalid transitions in state machine
	 */
	@Test
	public void testIsInvalid() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();
		CourseNameValidator co = new CourseNameValidator();
		CourseNameValidator cou = new CourseNameValidator();
		CourseNameValidator cour = new CourseNameValidator();
		CourseNameValidator cours = new CourseNameValidator();
		CourseNameValidator course = new CourseNameValidator();
		CourseNameValidator test = new CourseNameValidator();

		try {
			test.isValid("CSCSC216");
			fail();
		} catch (InvalidTransitionException e) {
			//invalid
		}
		
		

		try {
			c.isValid("#CSC216");
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		try {
			co.isValid("123");
			fail();
		} catch (InvalidTransitionException e) {
			//invalid
		}
		
		try {
			cou.isValid("CSC216A3");
			fail();
		} catch (InvalidTransitionException e) {
			//invalid
		}
		
		try {
			cour.isValid("CSC215AE");
			fail();
		} catch (InvalidTransitionException e) {
			//invalid
		}
		
		try {
			cours.isValid("CSC2167");
			fail();
		} catch (InvalidTransitionException e) {
			//invalid
		}
		try {
			course.isValid("CSCBJ213");
			fail();
		} catch (InvalidTransitionException e) {
			//invalid
		}
			
	}
}
