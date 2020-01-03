/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Tests the functionality of the Student class.
 *
 * @author Cameron Dutra
 * @author Tanmay Jain
 * @author Boxiang Ma
 */
public class StudentTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User s2 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User s3 = new Student("first", "lastname", "id", "email@ncsu.edu", "hashedpassword", 15);
		User s4 = new Student("first", "last", "identifier", "email@ncsu.edu", "hashedpassword", 15);
		User s5 = new Student("first", "last", "id", "unityid@ncsu.edu", "hashedpassword", 15);
		User s6 = new Student("first", "last", "id", "email@ncsu.edu", "normalpassword", 15);
		User s7 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 7);
		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());

	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#Student(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testStudentStringStringStringStringString() {
		User s = null; //Initialize a student reference to null
		//testing for a null first name
		try {
			s = new Student(null, "last", "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for empty first name string
		s = null;
		try {
			s = new Student("", "last", "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for null last name
		s = null;
		try {
			s = new Student("first", null, "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}


		//testing for empty last name string
		s = null;
		try {
			s = new Student("first", "", "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for null id
		s = null;
		try {
			s = new Student("first", "last", null, "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for an empty id String
		s = null;
		try {
			s = new Student("first", "last", "", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for null email
		s = null;
		try {
			s = new Student("first", "last", "id", null, "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for empty email string
		s = null;
		try {
			s = new Student("first", "last", "id", "", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for a missing @ in email String
		s = null;
		try {
			s = new Student("", "last", "id", "emailncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for a missing '.' in email String
		s = null;
		try {
			s = new Student("first", "last", "id", "email@ncsuedu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for '.' coming before @
		s = null;
		try {
			s = new Student("first", "last", "id", "email.ncsu@edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for null password
		s = null;
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for an empty password String
		s = null;
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing a valid case for the constructor
		s = null;
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
			assertEquals("first", s.getFirstName());
			assertEquals("last", s.getLastName());
			assertEquals("id", s.getId());
			assertEquals("email@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#Student(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {
		Student s = null;
		//testing for a maxCredits value that is too small
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing for a maxCredits value that is too large
		s = null;
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

		//testing a valid maxCredits value
		s = null;
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
			assertEquals("first", s.getFirstName());
			assertEquals("last", s.getLastName());
			assertEquals("id", s.getId());
			assertEquals("email@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(15, s.getMaxCredits());
		} catch (IllegalArgumentException e) {
			fail();
		}	
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setFirstName(java.lang.String)}.
	 */
	@Test
	public void testSetFirstName() {
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		//test setting first name to null
		try {
			s.setFirstName(null);
			fail(); 
		} catch (IllegalArgumentException e) {
			assertEquals("first", s.getFirstName());
		}

		//test setting first name to empty String
		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("first", s.getFirstName());
		}

		//test setting first name to valid String
		try {
			s.setFirstName("john");
			assertEquals("john", s.getFirstName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		//test setting last name to null
		try {
			s.setLastName(null);
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			assertEquals("last", s.getLastName());
		}

		//test setting last name to empty String
		try {
			s.setLastName("");
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			assertEquals("last", s.getLastName());
		}

		//test setting last name to a valid string 
		try {
			s.setLastName("john");
			assertEquals("john", s.getLastName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setId(String)}.
	 */
	@Test
	public void testSetId() {
		User s = null;
		//test setting id to a valid id String
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", "password");
			assertEquals("id", s.getId());
		} catch (IllegalArgumentException e) {
			fail();
		}

		//test setting id to null
		s = new Student("first", "last", "id", "email@ncsu.edu", "password");
		try {
			s = new Student("first", "last", null, "email@ncsu.edu", "password");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("id", s.getId());
		}

		//test setting id to an empty String
		s = new Student("first", "last", "id", "email@ncsu.edu", "password");
		try {
			s = new Student("first", "last", "", "email@ncsu.edu", "password");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("id", s.getId());
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setEmail(java.lang.String)}.
	 */
	@Test
	public void testSetEmail() {
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		//test setting email to null
		try {
			s.setEmail(null);
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		//test setting email to an empty String
		try {
			s.setEmail("");
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		//test setting email without @
		try {
			s.setEmail("email.ncsu.edu");
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		//test setting email without '.'
		try {
			s.setEmail("email@ncsu@edu");
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		//test setting email with '.' before @
		try {
			s.setEmail("email.ncsu@edu");
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals("email@ncsu.edu", s.getEmail());
		}

		//test setting email to a valid email
		try {
			s.setEmail("email@ncsu.edu");
			assertEquals("email@ncsu.edu", s.getEmail());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setPassword(java.lang.String)}.
	 */
	@Test
	public void testSetPassword() {
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		//test setting password to null
		try {
			s.setPassword(null);
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals("hashedpassword", s.getPassword());
		}
		//test setting password to empty string
		try {
			s.setPassword("");
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals("hashedpassword", s.getPassword());
		}

		//test setting password to a valid password
		try {
			s.setPassword("john");
			assertEquals("john", s.getPassword());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setMaxCredits(int)}.
	 */
	@Test
	public void testSetMaxCredits() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		//test setting maxCredits value less than 3
		try {
			s.setMaxCredits(2);
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals(15, s.getMaxCredits());
		}

		//test setting maxCredits value greater than 18
		try {
			s.setMaxCredits(20);
			fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			assertEquals(15, s.getMaxCredits());
		}

		//test setting maxCredits to a valid value
		try {
			s.setMaxCredits(14);
			assertEquals(14, s.getMaxCredits());
		} catch (IllegalArgumentException e) {
			//We've caught the exception, now we need to make sure that the field didn't change
			fail();
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User s2 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User s3 = new Student("first", "lastname", "id", "email@ncsu.edu", "hashedpassword", 15);
		User s4 = new Student("first", "last", "identifier", "email@ncsu.edu", "hashedpassword", 15);
		User s5 = new Student("first", "last", "id", "unityid@ncsu.edu", "hashedpassword", 15);
		User s6 = new Student("first", "last", "id", "email@ncsu.edu", "normalpassword", 15);
		User s7 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 7);
		User s8 = new Student("firstname", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		User s9 = null;
		User s10 = new Student("firstname", "lastname", "idnumber", "email@email.com", "normalPassword", 12);


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
		User s1 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		//test to make sure that the output of toString is the same as whats expected
		assertTrue(s1.toString().equals("first,last,id,email@ncsu.edu,hashedpassword,15"));
	}
	/**
	 * Test method for CompareTo.
	 */
	@Test
	public void testCompareTo() {
		Student x = new Student("cameron", "dutra", "csdutra", "email@ncsu.edu", "hashedpassword", 15);
		Student y = new Student("blake", "dutra", "bsdutra", "email@ncsu.edu", "hashedpassword", 15);	
		Student z = new Student("tyler", "chase", "tmchase", "email@ncsu.edu", "hashedpassword", 15);
		//ensure that the method works correctly for specified objects that come before this object
		//Also makes sure that if x.compareTo(y) is 1 and y.compareTo(z) is one then x.compareTo(z) must be true
		assertEquals(1, x.compareTo(y));
		assertEquals(1, y.compareTo(z));
		assertEquals(1, x.compareTo(z));
		//Ensure that the comparison can go both ways
		assertEquals(x.compareTo(y), -(y.compareTo(x)));
		//compare two objects that are equivalent in all three fields being compared
		Student s1 = new Student("tyler", "chase", "tmchase", "email@ncsu.edu", "hashedpassword", 15);
		assertEquals(0, z.compareTo(s1));
		assertEquals(Math.abs(z.compareTo(x)), Math.abs(s1.compareTo(x)));
		//compared the id's of the two objects
		Student s2 = new Student("cameron", "dutra", "acsdutra", "email@ncsu.edu", "hashedpassword", 15);
        assertEquals(1, x.compareTo(s2));
        
        try {
        	x.compareTo(null);
        	fail();
        } catch (NullPointerException e) {
        	assertEquals("Field cannot be null", e.getMessage());
        }
	}
}