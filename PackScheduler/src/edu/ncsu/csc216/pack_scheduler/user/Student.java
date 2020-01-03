package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * The purpose of the Student class is to provide a place to store information for an individual
 * student. The class provides functionality to have access to and edit the student's first name,
 * last name, school id, email, password, and the maximum number of credits that they can have. The
 * class also includes equals and hash code methods to determine object equality as well as specific
 * codes to create unique identifiers for the object. Also included is a compareTo method that will
 * compare two Student objects and determine which would come first when you want to put the two in
 * alphabetical order. The Student class implements the Comparable class to be able to compare
 * different states that the Student object has.
 * 
 * @author Cameron Dutra
 * @author Tanmay Jain
 * @author Boxiang Ma
 */
public class Student extends User implements Comparable<Student> {
	
	/**  Student's maximum credits. */
	private int maxCredits;
	/**  The maximum number of credits available to students. */
	public static final int MAX_CREDITS = 18;
	/** The field that holds an instance of a Schedule object*/
	private Schedule schedule;

	/**
	 * Constructs a Student object with all fields.
	 *
	 * @param firstName  Student's first name
	 * @param lastName   Student's last name
	 * @param id         Student's id
	 * @param email      Students email
	 * @param password   Student's password
	 * @param maxCredits Student's maxCredits
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * Constructs a Student object with max credits defaulted to 18.
	 *
	 * @param firstName Student's first name
	 * @param lastName  Student's last name
	 * @param id        Student's id
	 * @param email     Students email
	 * @param password  Student's password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Returns the Student's maxCredits.
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the Student's maxCredits.
	 *
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if maxCredits is less than 3 or larger than
	 *                                  18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}
	
    /**
     * Will generate a string version of the object in the format: first name,last name,id,email,password,maximum credits.
     * This will help when printing the values of the object as well as clarity for Student objects 
     * @return the String of all the information
     */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + "," + this.getPassword() + "," + maxCredits;
	}
    /**
     * Compares two Student objects to one another to assist in alphabetizing them. compareTo will first compare the values
     * of lastName in the Student object and return 1 if the Student being compared comes after this Student and -1 if the Student
     * being compared comes before this Student. If the lastName values are equivalent, then firstName is compared next. If they 
     * are the same the method moves on to checking id and if all three fields are equivalent, will return 0
     * @param s the Student object that is being compared against
     * @return the 1 if this object comes after the Student object being compared against, -1 if this object comes first, and 0 if
     * they are equivalent in position
     * @throws NullPointerException if the specified object being compared is null
     */
	@Override
	public int compareTo(Student s) {
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
	 * Generates a unique code used for each Schedule object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}
	
	/**
	 * Determines equality of two Schedule objects and will return whether
	 * or not they are equivalent.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}
	
	/**
	 * Returns the current Schedule object held within the class
	 * @return the current Schedule
	 */
	public Schedule getSchedule() {
		return this.schedule;
	}
	
	/**
	 * tests if a course can be added to a student's schedule
	 * @param c the course to be checked
	 * @return true if the course can be added, false if not
	 */
	public boolean canAdd(Course c) {
		if (!this.getSchedule().canAdd(c)) {
			return false;
		}
		int enrolledCredits = this.getSchedule().getScheduleCredits();
		if (enrolledCredits + c.getCredits() > maxCredits) {
			return false;
		}
		return true;
	}
}
