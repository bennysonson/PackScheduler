package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * The CourseRoll class establishes an enrollment capacity for its associated Course and stores the
 * Students enrolled in the Course along with the waitlist for the Course.
 * 
 * @author Cameron Dutra
 * @author John Preston
 * @author Pradhan Chetan Venkataramaiah
 */
public class CourseRoll {

	/** the max Students that can join a Course */
	private int enrollmentCap;
	/** the list of Students enrolled in a Course */
	private LinkedAbstractList<Student> roll;
	/** the list of Students waiting to be enrolled in the Course, if there is an opening */
	private ArrayQueue<Student> waitlist;
	/** the Course associated with this CourseRoll */
	private Course c;
	/** the minimum capacity a Course can have */
	private static final int MIN_ENROLLMENT = 10;
	/** the maximum capacity a Course can have */
	private static final int MAX_ENROLLMENT = 250;

	/**
	 * Constructs a CourseRoll object by setting the enrollment capacity and initializes the
	 * roll and waitlist for the associated Course with their corresponding sizes.
	 * 
	 * @param c Course corresponding to CourseRoll
	 * @param enrollmentCap the max Students that can enroll in a Course
	 */
	public CourseRoll(Course c, int enrollmentCap) {
		if (c == null)
			throw new IllegalArgumentException();

		setEnrollmentCap(enrollmentCap);
		roll = new LinkedAbstractList<Student>(this.enrollmentCap);
		waitlist = new ArrayQueue<Student>(MIN_ENROLLMENT);
		this.c = c;

	}
	
	/**
	 * Returns the string array representation of the Students in the roll of a Course.
	 * 
	 * @return the string array representation of the Students in the roll
	 */
	public String[][] getRoll() {
		String[][] courseRoll = new String[roll.size()][3];
		
		for (int i = 0; i < roll.size(); i++) {
			courseRoll[i][0] = roll.get(i).getFirstName();
			courseRoll[i][1] = roll.get(i).getLastName();
			courseRoll[i][2] = roll.get(i).getId();
		}
		
		return courseRoll;
	}

	/**
	 * Sets the enrollment capacity for the Course.
	 * 
	 * @param enrollmentCap the enrollment cap to be set
	 */
	public void setEnrollmentCap(int enrollmentCap) {

		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Enrollment capacity must be a number between 10 and 250");
		}

		if (roll != null) {
			if (enrollmentCap >= roll.size()) {
				this.enrollmentCap = enrollmentCap;
				this.roll.setCapacity(enrollmentCap);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			this.enrollmentCap = enrollmentCap;
		}

	}

	/**
	 * Adds a Student to the Course.
	 * 
	 * @param student the Student to add to the Course
	 */
	public void enroll(Student student) {
		if (student == null) {
			throw new IllegalArgumentException();
		}

//		if (roll.size() >= enrollmentCap) {
//			try {
//				waitlist.enqueue(student);
//			} catch (Exception e) {
//				throw new IllegalArgumentException();
//			}
//
////			throw new IllegalArgumentException();
//		}
//
//		if (roll.contains(student)) {
//			throw new IllegalArgumentException();
//		}
//
//		try {
//			roll.add(student);
//		} catch (Exception e) {
//			throw new IllegalArgumentException();
//		}

		if (canEnroll(student)) {
			if (roll.size() < enrollmentCap) {
				student.getSchedule().addCourseToSchedule(c);
				roll.add(student);
			} else if (waitlist.size() < MIN_ENROLLMENT)
				waitlist.enqueue(student);
			else
				throw new IllegalArgumentException("The course cannot be added");
		} else
			throw new IllegalArgumentException();

	}

	/**
	 * Checks if a Student can join a Course.
	 * 
	 * @param student the Student to check
	 * @return true if the Student can be enrolled, false if not
	 */
	public boolean canEnroll(Student student) {

//		if (student == null) {
//			throw new IllegalArgumentException();
//		}

		if (roll.contains(student)) {
			return false;
		}

		if (waitlist.contains(student))
			return false;

		if (roll.size() >= enrollmentCap && waitlist.size() < MIN_ENROLLMENT || roll.size() < enrollmentCap)
			return true;

//
//		if (waitlist.size() >= MIN_ENROLLMENT)
//			return false;

		return true;
	}

	/**
	 * Removes a Student from the Course and the Course from the Student's schedule if the Student is in the
	 * roll or waitlist for the Course. If a Student was removed from the roll, the Student at the front of the
	 * waitlist should be enrolled in the Course and the Course should be added to their schedule.
	 * 
	 * @param s the Student to be removed
	 */
	public void drop(Student s) {

		if (s == null) {
			throw new IllegalArgumentException();
		}

		else {
			if (roll.contains(s)) {
				try {
					s.getSchedule().removeCourseFromSchedule(c);
					roll.remove(s);
					if (!waitlist.isEmpty()) {
						Student temp = waitlist.dequeue();
						enroll(temp);
					}
				} catch (Exception e) {
					throw new IllegalArgumentException();
				}
			}

			else if (waitlist.contains(s))
				waitlist.remove(s);
//			else
//				throw new IllegalArgumentException();

		}
	}

	/**
	 * Returns the enrollment cap of the Course.
	 * 
	 * @return the enrollment cap of the Course
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Returns the number of open seats in the Course.
	 * 
	 * @return the number of open seats in the Course
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Returns the number of Students on the waitlist for a particular Course.
	 * 
	 * @return number of Students on the waitlist for a Course
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}