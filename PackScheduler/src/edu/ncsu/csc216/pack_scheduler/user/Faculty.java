package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * The purpose of the Faculty class is to provide a place to store information
 * for an individual Faculty. The class provides functionality to have access to
 * and edit the Faculty's first name, last name, school id, email, password, and
 * the maximum number of courses that they can have. The class also includes
 * equals and hash code methods to determine object equality as well as specific
 * codes to create unique identifiers for the object. Also included is a
 * compareTo method that will compare two Faculty objects and determine which
 * would come first when you want to put the two in alphabetical order. The
 * Faculty class implements the Comparable class to be able to compare different
 * states that the Faculty object has.
 * 
 * @author Pradhan Chetan Venkataramaiah
 *
 */
public class Faculty extends User implements Comparable<Faculty> {
	/** Faculty's maximum courses */
	private int maxCourses;
	/** The maximum number of courses a Faculty is allowed to take on */
	public static final int MAX_COURSES = 3;
	/** The field that holds an instance of a FacultySchedule object */
	private FacultySchedule schedule;
	

	/**
	 * Constructs a Faculty object with all fields and FacultySchedule object instructorId field.
	 *
	 * @param firstName  Faculty's first name
	 * @param lastName   Faculty's last name
	 * @param id         Faculty's id
	 * @param email      Faculty's email
	 * @param password   Faculty's password
	 * @param maxCourses Faculty's maxCourses
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}

	/**
	 * Constructs a Faculty object with max courses defaulted to 3.
	 *
	 * @param firstName Faculty's first name
	 * @param lastName  Faculty's last name
	 * @param id        Faculty's id
	 * @param email     Faculty's email
	 * @param password  Faculty's password
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_COURSES);
	}

	/**
	 * Sets the Faculty's maxCourses.
	 *
	 * @param maxCourses the maxCourses to set
	 * @throws IllegalArgumentException if maxCourses is less than 1 or larger than 3
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < 1 || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Returns the Faculty's maxCourses.
	 * 
	 * @return the maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Will generate a string version of the object in the format: first name,last
	 * name,id,email,password,maximum courses. This will help when printing the
	 * values of the object as well as clarity for Faculty objects
	 * 
	 * @return the String of all the information
	 */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + ","
				+ this.getPassword() + "," + maxCourses;
	}

	/**
	 * Compares two Faculty objects to one another to assist in alphabetizing them.
	 * compareTo will first compare the values of lastName in the Faculty object and
	 * return 1 if the Faculty being compared comes after this Faculty and -1 if the
	 * Faculty being compared comes before this Faculty. If the lastName values are
	 * equivalent, then firstName is compared next. If they are the same the method
	 * moves on to checking id and if all three fields are equivalent, will return 0.
	 * 
	 * @param s the Faculty object that is being compared against
	 * @return the 1 if this object comes after the Faculty object being compared
	 *         against, -1 if this object comes first, and 0 if they are equivalent
	 *         in position
	 * @throws NullPointerException if the specified object being compared is null
	 */
	@Override
	public int compareTo(Faculty s) {
		if (s == null) {
			throw new NullPointerException("Field cannot be null");
		}
		if (!this.getLastName().equals(s.getLastName())) {
			int smallestLast = s.getLastName().length();
			if (this.getLastName().length() < s.getLastName().length()) {
				smallestLast = this.getLastName().length();
			}
			for (int i = 0; i < smallestLast; i++) {
				if (this.getLastName().charAt(i) < s.getLastName().charAt(i)) {
					return -1;
				} else if (this.getLastName().charAt(i) > s.getLastName().charAt(i)) {
					return 1;
				}
			}
		}
		if (!this.getFirstName().equals(s.getFirstName())) {
			int smallestFirst = s.getFirstName().length();
			if (this.getFirstName().length() < s.getFirstName().length()) {
				smallestFirst = this.getFirstName().length();
			}
			for (int i = 0; i < smallestFirst; i++) {
				if (this.getFirstName().charAt(i) < s.getFirstName().charAt(i)) {
					return -1;
				} else if (this.getFirstName().charAt(i) > s.getFirstName().charAt(i)) {
					return 1;
				}
			}
		}
		if (!this.getId().equals(s.getId())) {
			int smallestId = s.getId().length();
			if (this.getId().length() < s.getId().length()) {
				smallestId = this.getId().length();
			}
			for (int i = 0; i < smallestId; i++) {
				if (this.getId().charAt(i) < s.getId().charAt(i)) {
					return -1;
				} else if (this.getId().charAt(i) > s.getId().charAt(i)) {
					return 1;
				}
			}
		}
		return 0;
	}

	/**
	 * Generates a unique code used for each Schedule object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Determines equality of two Schedule objects and will return whether or not they are equivalent.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}

	/**
	 * Returns the current Schedule object held within the class.
	 * 
	 * @return the current Schedule
	 */
	public FacultySchedule getSchedule() {
		return this.schedule;
	}

	/**
	 * Denotes if a course can be added to a Faculty's schedule.
	 * 
	 * @param c the course to be checked
	 * @return true if the course can be added, false if not
	 */
	public boolean canAdd(Course c) {
		return schedule.getNumScheduledCourses() < maxCourses;
	}

	/**
	 * Denotes if the faculty has more courses than they were allowed.
	 * 
	 * @return true if the course can be added, false if not
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}
}
