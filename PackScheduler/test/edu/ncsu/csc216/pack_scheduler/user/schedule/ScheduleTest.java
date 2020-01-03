/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Ensures proper functionality of the Schedule class
 * 
 * @author Cameron Dutra
 *
 */
public class ScheduleTest {
	/** Enrollment Cap */
	private static final int ENROLLMENT_CAP = 100;


	Course c1 = new Course("CSC216", "Programming Concepts", "001", 4, "JSchmidt", ENROLLMENT_CAP, "TTH", 830, 945);
	Course c2 = new Course("CSC226", "Discreet Math", "002", 3, "TBarnes", ENROLLMENT_CAP, "TTH", 1400, 1515);
	Course c3 = new Course("ST370", "Statistics", "001", 3, "CSmith", ENROLLMENT_CAP, "MW", 1400, 1515);
	Course c4 = new Course("ST371", "Statistics", "001", 3, "CSmith", ENROLLMENT_CAP, "MW", 1400, 1515);


	/**
	 * Test method for Schedule constructor
	 */
	@Test
	public void testSchedule() {
		Schedule s = new Schedule();
		assertEquals("My Schedule", s.getTitle());
	}

	/**
	 * Test method for addCourseToSchedule()
	 */
	@Test
	public void testAddCourseToSchedule() {
		Schedule s = new Schedule();
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		s.addCourseToSchedule(c3);
		assertEquals(3, s.getSize());
		try {
			s.addCourseToSchedule(c2);
		} catch (IllegalArgumentException e) {
			// skip
		}
		try {
			s.addCourseToSchedule(c4);
		} catch (IllegalArgumentException e) {
			//skip
		}
	}

	/**
	 * Test method for removeCourseFromSchedule()
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		Schedule s = new Schedule();
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		assertEquals(2, s.getSize());
		assertTrue(s.removeCourseFromSchedule(c2));
		assertFalse(s.removeCourseFromSchedule(c3));
		assertEquals(1, s.getSize());
	}

	/**
	 * Test method for resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		Schedule s = new Schedule();
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		s.addCourseToSchedule(c3);
		assertEquals(3, s.getSize());
		s.setTitle("YES");
		assertEquals("YES", s.getTitle());
		s.resetSchedule();
		assertEquals(0, s.getSize());
		assertEquals("My Schedule", s.getTitle());
		
	}

	/**
	 * Test method for getScheduledCourses()
	 */
	@Test
	public void testGetScheduledCourses() {
		Schedule s = new Schedule();
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		s.addCourseToSchedule(c3);
		String[][] display = s.getScheduledCourses();
		assertEquals(display.length, 3);
		}

	/**
	 * Test method for setTitle()
	 */
	@Test
	public void testSetTitle() {
		Schedule s = new Schedule();
		assertEquals("My Schedule", s.getTitle());
		try {
			s.setTitle(null);
		} catch (IllegalArgumentException e) {
			//skip
		}
	}

}
