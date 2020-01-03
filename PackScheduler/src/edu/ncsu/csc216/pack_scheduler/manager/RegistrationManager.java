package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * RegistrationManager handles registration for Students and Faculty into their respective directories. Also handles
 * registration of Courses for Students and teaching assignments for Faculty.
 * 
 * @author Cameron Dutra
 */
public class RegistrationManager {
	
	/** the sole instance of the RegistrationManager that is shared across the system */
	private static RegistrationManager instance;
	/** the list of courses that students can enroll in and that faculty can be assigned to */
	private CourseCatalog courseCatalog;
	/** the list of Students */
	private StudentDirectory studentDirectory;
	/** the user in charge of manipulating the Student and Faculty directory as well as the Course catalog */
	private User registrar;
	/** the user that is currently using the PackScheduler system */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** the file containing information that will be used to verify the registrar */
	private static final String PROP_FILE = "registrar.properties";
	/** the list of Faculty */
	private FacultyDirectory facultyDirectory;

	/**
	 * Constructs a RegistrationManager object when one does not already exist. This method cannot be accessed outside
	 * of this class as described by the Singleton Design Pattern.
	 */
	private RegistrationManager() {
		createRegistrar();
		this.courseCatalog = new CourseCatalog();
		this.studentDirectory = new StudentDirectory();
		this.facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Initializes the registrar of the system using the registrar.properties file.
	 * @throws IOException when the method cannot create the registrar
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Uses the SHA-256 hashing algorithm to hash the provided password.
	 * 
	 * @param pw the password to be hashed
	 * @return the hashed password
	 * @throws IllegalArgumentException when pw cannot be hashed
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Gets the single instance of RegistrationManager
	 * 
	 * @return the instance
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Adds a Faculty to a Course. Returns true if successful, false if not.
	 * 
	 * @param c Course that Faculty is being added to
	 * @param f Faculty being added to Course
	 * @return If Faculty was successfully added
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (currentUser != null && currentUser == registrar) {
			f.getSchedule().addCourseToSchedule(c);
			return true;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Removes a Faculty from a Course. Returns true if successful, false if not.
	 * 
	 * @param c Course that Faculty is being removes from
	 * @param f Faculty being removed from Course
	 * @return If Faculty was successfully removed
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if (currentUser != null && currentUser == registrar) {
			f.getSchedule().removeCourseFromSchedule(c);
			return true;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Resets schedule for a Faculty.
	 * @param f Faculty who's schedule is being reset
	 */
	public void resetFacultySchedule(Faculty f) {
		if (currentUser != null && currentUser == registrar) {
			f.getSchedule().resetSchedule();
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns faculty Directory.
	 * 
	 * @return faculty directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Gets the course catalog.
	 * 
	 * @return the catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Gets the student directory.
	 * 
	 * @return the student directory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			CourseRoll roll = c.getCourseRoll();
			int prev = roll.getNumberOnWaitlist() + roll.getOpenSeats();
			roll.drop(s);
			int current = roll.getNumberOnWaitlist() + roll.getOpenSeats();
			if (prev == current - 1) {
				return true;
			}
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * Method for handling login with checking passwords.
	 * 
	 * @param id       of who is trying to login
	 * @param password for the attempt
	 * @return whether the person logged in or not
	 */
	public boolean login(String id, String password) {
		if (currentUser == null) {
			Student s = studentDirectory.getStudentById(id);
			Faculty f = facultyDirectory.getFacultyById(id);
			if (s == null) {
				if (f == null) {
					if (registrar.getId().equals(id)) {
						MessageDigest digest;
						try {
							digest = MessageDigest.getInstance(HASH_ALGORITHM);
							digest.update(password.getBytes());
							String localHashPW = new String(digest.digest());
							// System.out.println("got this far");
							if (registrar.getPassword().equals(localHashPW)) {
								currentUser = registrar;
								return true;
							} else {
								return false;
							}
						} catch (NoSuchAlgorithmException e) {
							throw new IllegalArgumentException();
						}
					}
				} else if (f != null) {
					try {
						MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
						digest.update(password.getBytes());
						String localHashPW = new String(digest.digest());

						if (f != null && f.getPassword().equals(localHashPW)) {
							currentUser = f;
							return true;
						} else {
							return false;
						}
					} catch (NoSuchAlgorithmException e) {
						throw new IllegalArgumentException();
					}
				}
			} else if (s != null) {
				try {
					MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String localHashPW = new String(digest.digest());

					if (s != null && s.getPassword().equals(localHashPW)) {
						currentUser = s;
						return true;
					} else {
						return false;
					}
				} catch (NoSuchAlgorithmException e) {
					throw new IllegalArgumentException();
				}
			}

			throw new IllegalArgumentException("User doesn't exist.");
		} else {
			return false;
		}

	}

	/**
	 * Logs the user out and sets the current user to null.
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Gets the current user.
	 * 
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Clears out all the data in the CourseCatalog and Student Directory.
	 */
	public void clearData() {
		currentUser = null;
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Inner class Register used for creating a Register and register things.
	 * 
	 * @author Cameron Dutra
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user with the user id and password in the
		 * registrar.properties file.
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}

}