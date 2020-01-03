package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * test methods for the CourseRoll class
 * 
 * @author Luis
 * @author Pradhan Chetan Venkataramaiah
 *
 */
public class CourseRollTest {
	private CourseRoll cr;
	private Student s1;
	private Student s2;
	private Student s3;
	private Student s4;
	private Student s5;
	private Student s6;
	private Student s7;
	private Student s8;
	private Student s9;
	private Student s11;
	private Student s10;
	private Student s12;
	private Student s13;
	private Student s14;
	private Student s15;
	private Student s16;
	private Student s17;
	private Student s18;
	private Student s19;
	private Student s20;
	private Student s21;
	private Student s22;
	private Course c;

	/**
	 * Sets up the private test variables for use in the test cases.
	 * 
	 * @throws Exception when an error occurs in the set up
	 */
	@Before
	public void setUp() throws Exception {
		s1 = new Student("Benson", "Liu", "bliu22", "bliu22@nscu.edu", "123");
		s2 = new Student("LeBron", "James", "LJames", "LJames@la.com", "China");
		s3 = new Student("Benson", "Liu", "bliu22", "bliu22@nscu.edu", "123");
		s4 = new Student("LeBron", "James", "LJamez", "LJames@la.edu", "China");
		s5 = new Student("LeBron", "ja", "bliu23", "LJame@la.eu", "China");
		s6 = new Student("LeBron", "James", "LJez", "LJaes@la.edu", "China");
		s7 = new Student("LeBron", "James", "Lamez", "Lames@la.edu", "China");
		s8 = new Student("LeBron", "James", "Lez", "James@la.edu", "China");
		s9 = new Student("LeBron", "James", "amez", "LJes@la.edu", "China");
		s11 = new Student("LeBron", "James", "Lz", "LJs@la.edu", "China");
		s10 = new Student("LeBron", "James", "LJ", "Ls@la.edu", "China");
		s12 = new Student("LeBron", "James", "J", "Ls@la.edu", "China");
		s13 = new Student("LeBron", "James", "13", "Ls@la.edu", "China");
		s14 = new Student("LeBron", "James", "14", "Ls@la.edu", "China");
		s15 = new Student("LeBron", "James", "15", "Ls@la.edu", "China");
		s16 = new Student("LeBron", "James", "16", "Ls@la.edu", "China");
		s17 = new Student("LeBron", "James", "17", "Ls@la.edu", "China");
		s18 = new Student("LeBron", "James", "18", "Ls@la.edu", "China");
		s19 = new Student("LeBron", "James", "19", "Ls@la.edu", "China");
		s20 = new Student("LeBron", "James", "20", "Ls@la.edu", "China");
		s21 = new Student("LeBron", "James", "21", "Ls@la.edu", "China");
		s22 = new Student("LeBron", "James", "22", "Ls@la.edu", "China");
		c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
	}
	
	/**
	 * test setting the enrollmentcap and construction
	 */
	@Test
	public void testSetEnrollmentCap() {
		try {
			cr = new CourseRoll(null, 4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), null);
		}

		try {
			cr = new CourseRoll(c, 4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment capacity must be a number between 10 and 250", e.getMessage());
		}

		try {
			cr = new CourseRoll(c, 500);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment capacity must be a number between 10 and 250", e.getMessage());
		}

		cr = new CourseRoll(c, 15);
		assertEquals(15, cr.getEnrollmentCap());
		
		cr.enroll(s1);
		cr.enroll(s2);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);
		cr.enroll(s11);

		cr.setEnrollmentCap(10);
		assertEquals(10, cr.getEnrollmentCap());
	}

	/**
	 * tests the enroll method and canEnroll method
	 */
	@Test
	public void testEnroll() {
		cr = new CourseRoll(c, 10);

		cr.enroll(s1);
		assertEquals(cr.getOpenSeats(), 9);
		assertEquals(1, s1.getSchedule().getSize());
		assertFalse(cr.canEnroll(s1));

		try {
			cr.enroll(s3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), null);
		}
		assertFalse(cr.canEnroll(s3));

		try {
			cr.enroll(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(cr.getOpenSeats(), 9);
		}

		try {
			cr.canEnroll(null);
		} catch (IllegalArgumentException e) {
			assertEquals(cr.getOpenSeats(), 9);
		}

		cr.enroll(s2);
		assertEquals(cr.getOpenSeats(), 8);
		assertEquals(1, s2.getSchedule().getSize());
		cr.drop(s2);
		assertEquals(cr.getOpenSeats(), 9);
		assertEquals(0, s2.getSchedule().getSize());
		
		
		try {
			cr.drop(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(cr.getOpenSeats(), 9);

		}

	}

	/**
	 * tests an invalid number of enrolls
	 */
	@Test
	public void testInvalidEnroll() {
		cr = new CourseRoll(c, 10);
		cr.enroll(s1);
		cr.enroll(s2);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);
		cr.enroll(s11);
		cr.enroll(s12);
		cr.enroll(s13);
		cr.enroll(s14);
		cr.enroll(s15);
		cr.enroll(s16);
		cr.enroll(s17);
		cr.enroll(s18);
		cr.enroll(s19);
		cr.enroll(s20);
		cr.enroll(s21);

		try {
			cr.enroll(s22);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "The course cannot be added");
		}
	}

	/**
	 * Tests the getNumberOnWaitlistI() method.
	 */
	@Test
	public void testGetNumberOnWaitlist() {
		cr = new CourseRoll(c, 10);
		cr.enroll(s1);
		cr.enroll(s2);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		cr.enroll(s10);
		cr.enroll(s11);
		cr.enroll(s12);

		Student w1 = new Student("Prada", "Chetan", "pchetan", "pchetan@nscu.edu", "129");
		Student w2 = new Student("Pradhan", "Chetan", "prachetan", "pcan@nscu.edu", "19");

		assertTrue(cr.canEnroll(w1));
		assertEquals(0, cr.getOpenSeats());
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(w1);
		assertEquals(1, cr.getNumberOnWaitlist());

		try {
			cr.enroll(w1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(1, cr.getNumberOnWaitlist());
		}

		cr.enroll(w2);
		assertEquals(2, cr.getNumberOnWaitlist());

	}

	/**
	 * Tests the drop() method.
	 */
	@Test
	public void testDrop() {
		cr = new CourseRoll(c, 10);
		Student w1 = new Student("Prada", "Chetan", "pchetan", "pchetan@nscu.edu", "129");
		Student w2 = new Student("Pradhan", "Chetan", "prachetan", "pcan@nscu.edu", "19");

		// Drop from empty list
		cr.drop(s1);
//		try {
//			cr.drop(s1);
//			fail();
//		} catch (IllegalArgumentException e) {
//			assertEquals(10, cr.getOpenSeats());
//		}

		cr.enroll(s1);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s2);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s5);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s6);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s7);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s8);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s9);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s10);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s11);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(s12);
		assertEquals(0, cr.getNumberOnWaitlist());
		cr.enroll(w1);
		assertEquals(1, cr.getNumberOnWaitlist());
		cr.enroll(w2);
		assertEquals(2, cr.getNumberOnWaitlist());

		assertEquals(0, cr.getOpenSeats());

		try {
			cr.drop(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cr.getOpenSeats());
		}

		cr.drop(s1);
		assertEquals(0, cr.getOpenSeats());
		assertEquals(1, cr.getNumberOnWaitlist());

		cr.drop(w2);
		assertEquals(0, cr.getNumberOnWaitlist());
		assertEquals(0, cr.getOpenSeats());

		cr.drop(s10);
		assertEquals(1, cr.getOpenSeats());

	}
}
