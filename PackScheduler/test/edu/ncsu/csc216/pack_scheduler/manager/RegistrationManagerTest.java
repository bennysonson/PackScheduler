package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * test classes for the RegistrationManager
 * 
 * @author Cameron, Benson, Luis
 *
 */
public class RegistrationManagerTest {
	private static final String PROP_FILE = "registrar.properties";

	private RegistrationManager manager;

	private String[] createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			String ans[] = { prop.getProperty("id"), prop.getProperty("pw") };
			return ans;
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar");
		}
	}

	/**
	 * Sets up the CourseManager and clears the data.
	 * 
	 * @throws Exception if error
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.clearData();
	}

	/**
	 * Tests the getCourseCatalog() method.
	 */
	@Test
	public void testGetCourseCatalog() {
		manager.getCourseCatalog().loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, manager.getCourseCatalog().getCourseCatalog().length);
		assertEquals("CSC226", manager.getCourseCatalog().getCourseCatalog()[6][0]);
	}

	/**
	 * tests the getStudentDirectory() method
	 */
	@Test
	public void testGetStudentDirectory() {
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		assertEquals(10, manager.getStudentDirectory().getStudentDirectory().length);
		assertEquals("King", manager.getStudentDirectory().getStudentById("zking").getLastName());
	}

	/**
	 * tests the getFacultyDirectory() method
	 */
	@Test
	public void testGetFacultyDirectory() {
		assertEquals(0, manager.getFacultyDirectory().getFacultyDirectory().length);
		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");
		assertEquals(8, manager.getFacultyDirectory().getFacultyDirectory().length);
		assertEquals("Witt", manager.getFacultyDirectory().getFacultyById("awitt").getLastName());
	}

	/**
	 * tests the login() method
	 */
	@Test
	public void testLogin() {
		String info[] = createRegistrar();
		assertFalse(manager.login(info[0], "notRegistrarPW"));
		assertTrue(manager.login(info[0], info[1]));
		assertTrue(manager.getCurrentUser().getClass().getSimpleName().equals("Registrar"));
		assertFalse(manager.login("test", "test"));

		manager.getStudentDirectory().loadStudentsFromFile("test-files/expected_full_student_records.txt");
		manager.logout();
		assertFalse(manager.login("zking", "notpw"));
		assertTrue(manager.login("zking", "pw"));
		assertTrue(manager.getCurrentUser().equals(manager.getStudentDirectory().getStudentById("zking")));

		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");
		manager.logout();
		assertFalse(manager.login("awitt", "notpw"));
		assertTrue(manager.login("awitt", "pw"));
		assertTrue(manager.getCurrentUser().equals(manager.getFacultyDirectory().getFacultyById("awitt")));
	}

	/**
	 * tests the logout() method
	 */
	@Test
	public void testLogout() {
		manager.logout();
		assertNull(manager.getCurrentUser());
	}

	/**
	 * tests the getCurrentUser() method
	 */
	@Test
	public void testGetCurrentUser() {
		assertNull(manager.getCurrentUser());
	}

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(3, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(1, scheduleFrostArray.length);
		assertEquals("CSC226", scheduleFrostArray[0][0]);
		assertEquals("001", scheduleFrostArray[0][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleFrostArray[0][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleFrostArray[0][3]);
		assertEquals("9", scheduleFrostArray[0][4]);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(10, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC226", scheduleHicksArray[1][0]);
		assertEquals("001", scheduleHicksArray[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
		assertEquals("8", scheduleHicksArray[1][4]);
		assertEquals("CSC116", scheduleHicksArray[2][0]);
		assertEquals("003", scheduleHicksArray[2][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
		assertEquals("9", scheduleHicksArray[2][4]);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		assertFalse(
				"Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: False - student not enrolled in the course",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(10, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC226", scheduleHicksArray[1][0]);
		assertEquals("001", scheduleHicksArray[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
		assertEquals("9", scheduleHicksArray[1][4]);
		assertEquals("CSC116", scheduleHicksArray[2][0]);
		assertEquals("003", scheduleHicksArray[2][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
		assertEquals("9", scheduleHicksArray[2][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(7, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(2, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC116", scheduleHicksArray[1][0]);
		assertEquals("003", scheduleHicksArray[1][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3]);
		assertEquals("9", scheduleHicksArray[1][4]);

		assertFalse("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: False - already dropped",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(3, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(1, scheduleHicksArray.length);
		assertEquals("CSC116", scheduleHicksArray[0][0]);
		assertEquals("003", scheduleHicksArray[0][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC116-003\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals("RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests the addFacultyToCOurse method.
	 */
	@Test
	public void testAddFacultyToCourse() {
		String info[] = createRegistrar();

		manager.getCourseCatalog().loadCoursesFromFile("test-files/course_records.txt");
		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");

		try {
			manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC230", "001"),
					manager.getFacultyDirectory().getFacultyById("ebriggs"));
		} catch (IllegalArgumentException e) {
			// skip
		}
		manager.login(info[0], info[1]);

		assertTrue(manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC230", "001"),
				manager.getFacultyDirectory().getFacultyById("ebriggs")));

	}

	/**
	 * Tests the removeFacultyFromCourse method.
	 */
	@Test
	public void testRemoveFacultyFromCourse() {
		String info[] = createRegistrar();

		manager.getCourseCatalog().loadCoursesFromFile("test-files/course_records.txt");
		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");

		try {
			manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC230", "001"),
					manager.getFacultyDirectory().getFacultyById("ebriggs"));
		} catch (IllegalArgumentException e) {
			// skip
		}

		manager.login(info[0], info[1]);

		assertTrue(manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC230", "001"),
				manager.getFacultyDirectory().getFacultyById("ebriggs")));

		assertTrue(manager.removeFacultyFromCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC230", "001"),
				manager.getFacultyDirectory().getFacultyById("ebriggs")));

		manager.logout();

		try {
			manager.removeFacultyFromCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC230", "001"),
					manager.getFacultyDirectory().getFacultyById("ebriggs"));
		} catch (IllegalArgumentException e) {
			// skip
		}

	}

	/**
	 * Tests the resetFacultySchedule method.
	 */
	@Test
	public void testResetFaultySchedule() {
		String info[] = createRegistrar();

		manager.getCourseCatalog().loadCoursesFromFile("test-files/course_records.txt");
		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");

		try {
			manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC230", "001"), manager.getFacultyDirectory().getFacultyById("ebriggs"));
		} catch (IllegalArgumentException e) {
			//skip
		}

		manager.login(info[0], info[1]);

		assertTrue(manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC230", "001"),
				manager.getFacultyDirectory().getFacultyById("ebriggs")));

		assertEquals(1, manager.getFacultyDirectory().getFacultyById("ebriggs").getSchedule().getNumScheduledCourses());

		manager.resetFacultySchedule(manager.getFacultyDirectory().getFacultyById("ebriggs"));

		assertEquals(0, manager.getFacultyDirectory().getFacultyById("ebriggs").getSchedule().getNumScheduledCourses());

	}
}