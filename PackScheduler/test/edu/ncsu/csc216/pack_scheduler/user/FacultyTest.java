/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the functionality of the Faculty class.
 *
 * @author Cameron Dutra
 * @author Tanmay Jain
 * @author Boxiang Ma
 * @author Pradhan Chetan Venkataramaiah
 */
public class FacultyTest {

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		User s1 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 3);
		User s2 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 3);
		User s3 = new Faculty("first", "lastname", "id", "email@ncsu.edu", "hashedpassword", 1);
		User s4 = new Faculty("first", "last", "identifier", "email@ncsu.edu", "hashedpassword", 1);
		User s5 = new Faculty("first", "last", "id", "unityid@ncsu.edu", "hashedpassword", 2);
		User s6 = new Faculty("first", "last", "id", "email@ncsu.edu", "normalpassword", 2);
		User s7 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 1);

		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#Faculty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testFacultyStringStringStringStringString() {
		User s = null; // Initialize a Faculty reference to null

		// testing for a null first name
		try {
			s = new Faculty(null, "last", "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for empty first name string
		s = null;
		try {
			s = new Faculty("", "last", "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for null last name
		s = null;
		try {
			s = new Faculty("first", null, "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for empty last name string
		s = null;
		try {
			s = new Faculty("first", "", "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for null id
		s = null;
		try {
			s = new Faculty("first", "last", null, "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for an empty id String
		s = null;
		try {
			s = new Faculty("first", "last", "", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for null email
		s = null;
		try {
			s = new Faculty("first", "last", "id", null, "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for empty email string
		s = null;
		try {
			s = new Faculty("first", "last", "id", "", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for a missing @ in email String
		s = null;
		try {
			s = new Faculty("", "last", "id", "emailncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for a missing '.' in email String
		s = null;
		try {
			s = new Faculty("first", "last", "id", "email@ncsuedu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for '.' coming before @
		s = null;
		try {
			s = new Faculty("first", "last", "id", "email.ncsu@edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for null password
		s = null;
		try {
			s = new Faculty("first", "last", "id", "email@ncsu.edu", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for an empty password String
		s = null;
		try {
			s = new Faculty("first", "last", "id", "email@ncsu.edu", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing a valid case for the constructor
		s = new Faculty("Prada", "Chetan", "pchetan", "pchetan@ncsu.edu", "goodOne!");
		assertEquals("Prada", s.getFirstName());
		assertEquals("Chetan", s.getLastName());
		assertEquals("pchetan", s.getId());
		assertEquals("pchetan@ncsu.edu", s.getEmail());
		assertEquals("goodOne!", s.getPassword());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#Faculty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testFacultyStringStringStringStringStringInt() {
		Faculty s = null;
		// testing for a maxCourses value that is too small
		try {
			s = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing for a maxCourses value that is too large
		s = null;
		try {
			s = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 5);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		// testing a valid case for the constructor
		s = new Faculty("Prada", "Chetan", "pchetan", "pchetan@ncsu.edu", "goodOne!", 2);
		assertEquals("Prada", s.getFirstName());
		assertEquals("Chetan", s.getLastName());
		assertEquals("pchetan", s.getId());
		assertEquals("pchetan@ncsu.edu", s.getEmail());
		assertEquals("goodOne!", s.getPassword());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setFirstName(java.lang.String)}.
	 */
	@Test
	public void testSetFirstName() {
		User s = new Faculty("Prada", "Chetan", "pchetan", "pchetan@ncsu.edu", "niceTry");
		// test setting first name to null
		try {
			s.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Prada", s.getFirstName());
		}

		// test setting first name to empty String
		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Prada", s.getFirstName());
		}

		// test setting first name to valid String
		try {
			s.setFirstName("john");
			assertEquals("john", s.getFirstName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		User s = new Faculty("Prada", "Chetan", "pchetan", "pchetan@ncsu.edu", "password");
		// test setting last name to null
		try {
			s.setLastName(null);
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			assertEquals("Chetan", s.getLastName());
		}

		// test setting last name to empty String
		try {
			s.setLastName("");
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			assertEquals("Chetan", s.getLastName());
		}

		// test setting last name to a valid string
		try {
			s.setLastName("V.");
			assertEquals("V.", s.getLastName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setId(String)}.
	 */
	@Test
	public void testSetId() {
		User s = null;
		// test setting id to a valid id String
		try {
			s = new Faculty("first", "last", "id", "email@ncsu.edu", "password");
			assertEquals("id", s.getId());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// test setting id to null
		try {
			s = new Faculty("first", "last", null, "email@ncsu.edu", "password");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("id", s.getId());
		}

		// test setting id to an empty String
		try {
			s = new Faculty("first", "last", "", "email@ncsu.edu", "password");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("id", s.getId());
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setEmail(java.lang.String)}.
	 */
	@Test
	public void testSetEmail() {
		User s = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword");

		// test setting email to null
		try {
			s.setEmail(null);
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		// test setting email to an empty String
		try {
			s.setEmail("");
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		// test setting email without @
		try {
			s.setEmail("email.ncsu.edu");
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		// test setting email without '.'
		try {
			s.setEmail("email@ncsu@edu");
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		// test setting email with '.' before @
		try {
			s.setEmail("email.ncsu@edu");
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		// test setting email to a valid email
		try {
			s.setEmail("email@ncsu.edu");
			assertEquals("email@ncsu.edu", s.getEmail());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setPassword(java.lang.String)}.
	 */
	@Test
	public void testSetPassword() {
		User s = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		// test setting password to null
		try {
			s.setPassword(null);
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("hashedpassword", s.getPassword());
		}
		// test setting password to empty string
		try {
			s.setPassword("");
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals("hashedpassword", s.getPassword());
		}

		// test setting password to a valid password
		try {
			s.setPassword("john");
			assertEquals("john", s.getPassword());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setMaxCourses(int)}.
	 */
	@Test
	public void testSetMaxCourses() {
		Faculty s = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 3);
		// test setting maxCourses value less than 1
		try {
			s.setMaxCourses(0);
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals(3, s.getMaxCourses());
		}

		// test setting maxCourses value greater than 3
		try {
			s.setMaxCourses(6);
			fail(); // We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			assertEquals(3, s.getMaxCourses());
		}

		// test setting maxCourses to a valid value
		try {
			s.setMaxCourses(2);
			assertEquals(2, s.getMaxCourses());
		} catch (IllegalArgumentException e) {
			// We've caught the exception, now we need to make sure that the field didn't
			// change
			fail();
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 3);
		User s2 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 3);
		User s3 = new Faculty("first", "lastname", "id", "email@ncsu.edu", "hashedpassword", 3);
		User s4 = new Faculty("first", "last", "identifier", "email@ncsu.edu", "hashedpassword", 3);
		User s5 = new Faculty("first", "last", "id", "unityid@ncsu.edu", "hashedpassword", 3);
		User s6 = new Faculty("first", "last", "id", "email@ncsu.edu", "normalpassword", 3);
		User s7 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 1);
		User s8 = new Faculty("firstname", "last", "id", "email@ncsu.edu", "hashedpassword", 3);
		User s9 = null;
		User s10 = new Faculty("firstname", "lastname", "idnumber", "email@email.com", "normalPassword", 2);

		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));
		assertFalse(s1.equals(s3));
		assertFalse(s1.equals(s4));
		assertFalse(s1.equals(s5));
		assertFalse(s1.equals(s6));
		assertFalse(s1.equals(s7));
		assertFalse(s1.equals(s8));
		assertFalse(s1.equals(s9));
		assertFalse(s1.equals(s10));
	}

	/**
	 * Test method for ToString.
	 */
	@Test
	public void testToString() {
		User s1 = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 1);
		// test to make sure that the output of toString is the same as whats expected
		assertTrue(s1.toString().equals("first,last,id,email@ncsu.edu,hashedpassword,1"));
	}

	/**
	 * Test method for CompareTo.
	 */
	@Test
	public void testCompareTo() {
		Faculty x = new Faculty("cameron", "dutra", "csdutra", "email@ncsu.edu", "hashedpassword", 3);
		Faculty y = new Faculty("blake", "dutra", "bsdutra", "email@ncsu.edu", "hashedpassword", 2);
		Faculty z = new Faculty("tyler", "chase", "tmchase", "email@ncsu.edu", "hashedpassword", 1);
		// ensure that the method works correctly for specified objects that come before
		// this object
		// Also makes sure that if x.compareTo(y) is 1 and y.compareTo(z) is one then
		// x.compareTo(z) must be true
		assertEquals(1, x.compareTo(y));
		assertEquals(1, y.compareTo(z));
		assertEquals(1, x.compareTo(z));
		// Ensure that the comparison can go both ways
		assertEquals(x.compareTo(y), -(y.compareTo(x)));
		// compare two objects that are equivalent in all three fields being compared
		Faculty s1 = new Faculty("tyler", "chase", "tmchase", "email@ncsu.edu", "hashedpassword", 1);
		assertEquals(0, z.compareTo(s1));
		assertEquals(Math.abs(z.compareTo(x)), Math.abs(s1.compareTo(x)));
		// compared the id's of the two objects
		Faculty s2 = new Faculty("cameron", "dutra", "acsdutra", "email@ncsu.edu", "hashedpassword", 3);
		assertEquals(1, x.compareTo(s2));

		try {
			x.compareTo(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals("Field cannot be null", e.getMessage());
		}
	}
}