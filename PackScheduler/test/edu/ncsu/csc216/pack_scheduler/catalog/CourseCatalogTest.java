/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Course Catalog.
 *
 * @author Tanmay Jain
 */
public class CourseCatalogTest {
	
	/**
	 * Test whether course catalog has been initialized.
	 */
	@Test
	public void testCourseCatalog() {
		CourseCatalog c1 = new CourseCatalog();
		assertEquals(0, c1.getCourseCatalog().length);
	}
	
	/**
	 * Test whether courses are loaded from a file.
	 */
	@Test
	public void testLLoadCourseFromFile() {
		CourseCatalog c1 = new CourseCatalog();
		//Checking if the catalog is empty or not
		assertEquals(0, c1.getCourseCatalog().length);
		//Loading from valid file
			c1.loadCoursesFromFile("test-files/course_records.txt");
			assertEquals(8, c1.getCourseCatalog().length);
		//Loading a file that doesn't exist
			try {
				c1.loadCoursesFromFile("");
			} catch (IllegalArgumentException e) {
				assertEquals("Cannot find file.", e.getMessage());
			}

	}

	/**
	 * Test new course catalog.
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog c1 = new CourseCatalog();
		c1.loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, c1.getCourseCatalog().length);
		c1.newCourseCatalog();
		assertEquals(0, c1.getCourseCatalog().length);
	}
	
	/**
	 * Test add course to catalog.
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog c1 = new CourseCatalog();
		//Adding duplicate courses
	    assertTrue(c1.addCourseToCatalog("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 100, "MW", 910, 1100));
		assertTrue(c1.addCourseToCatalog("CSC116", "Intro to Programming - Java", "002", 3, "spbalik", 100, "MW", 1120, 1310));

		//Adding valid courses
		try {
			CourseCatalog c2 = new CourseCatalog();
			c2.addCourseToCatalog("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 100, "MW", 910, 1100);
			c2.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "TH", 1330, 1445);
			assertEquals(2, c2.getCourseCatalog().length);
		} 
		catch(IllegalArgumentException e) {
			fail();
		}
		CourseCatalog c3 = new CourseCatalog();
	    assertTrue(c3.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 3, "jdyoung2", 100, "MW", 1210, 1300));
	    assertTrue(c3.addCourseToCatalog("CSC226", "Discrete Math for Computer Science", "001", 3, "jdyoung2", 100, "MW", 1400, 1500));
	    assertTrue(c3.addCourseToCatalog("CSC116", "Intro to Programming - Java", "002", 3, "jdyoung2", 100, "MW", 910, 1100));
	    assertTrue(c3.addCourseToCatalog("CSC216", "Programming Concepts - Java", "002", 3, "jdyoung2", 100, "MW", 910, 1100));
	    assertFalse(c3.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 3, "jdyoung2", 100, "MW", 910, 1100));
	}
	
	/**
	 * Test remove course from catalog.
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog c1 = new CourseCatalog();
		try {
			c1.addCourseToCatalog("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 100, "MW", 910, 1100);
			c1.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "TH", 1330, 1445);
			assertEquals(2, c1.getCourseCatalog().length);
			c1.removeCourseFromCatalog("CSC116", "001");
			assertEquals(1, c1.getCourseCatalog().length);
		} 
		catch(IllegalArgumentException e) {
			fail();
		}
		try {
			//Test removing a course that doesn't exist
			c1.addCourseToCatalog("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 100, "MW", 910, 1100);
			c1.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "TH", 1330, 1445);
			assertFalse(c1.removeCourseFromCatalog("CSC136", "001"));
		}
		catch(IllegalArgumentException e) {
			//Check if the remove method removed an existing course from catalog
			assertEquals(2, c1.getCourseCatalog().length);
		}
	}
	
	/**
	 * Test get course from catalog.
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog c1 = new CourseCatalog();
		c1.loadCoursesFromFile("test-files/course_records.txt");
		//Loading a course that doesn't exist in catalog
		assertNull(c1.getCourseFromCatalog("CSC116", "008"));
		//Loading a valid course
		assertEquals( "Intro to Programming - Java", c1.getCourseFromCatalog("CSC116", "001").getTitle());
		assertEquals("MW 9:10AM-11:00AM", c1.getCourseFromCatalog("CSC116", "001").getMeetingString());
	}
	/**
	 * Test saving of catalog to the specified file
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog c1 = new CourseCatalog();
		c1.addCourseToCatalog("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 100, "MW", 910, 1100);
		c1.saveCourseCatalog("testing_records.txt");
		//Saving file with invalid name
		try {
			c1.saveCourseCatalog("");
			fail();
		}
		catch(IllegalArgumentException e) {
			assertEquals("Cannot find file.", e.getMessage());
		}
	}
}

