package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;

/**
 * Course class represents a course offered by the university and contains
 * information about said course.
 * 
 * @author Cameron Dutra
 * 
 */
public class Course extends Activity implements Comparable<Course> {
	/** Course's name */
	private String name;
	/** Course's section */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Constant used for section length */
	public static final int SECTION_LENGTH = 3;
	/** Maximum name length a Course can hold */
	public static final int MAX_NAME_LENGTH = 6;
	/** Minimum name length a Course can hold */
	public static final int MIN_NAME_LENGTH = 4;
	/** The maximum number of credits that a Course can offer */
	public static final int MAX_CREDITS = 5;
	/** The minimum number of credits that a Course can offer */
	public static final int MIN_CREDITS = 1;
	/** Course's roll */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param enrollmentCap the max students that can enroll in a course
	 * @param meetingDays   meeting days for Course as series of chars
	 * @param startTime     start time for Course
	 * @param endTime       end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);

		roll = new CourseRoll(this, enrollmentCap);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param enrollmentCap the max students that can enroll in the course
	 * @param meetingDays   meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 4 or
	 * greater than 6, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null or length is less than 4 or
	 *                                  greater than 6
	 */
	private void setName(String name) {
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException("Invalid course name");
		}
		if (name.length() < 4 || name.length() > 6) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	/**
	 * Returns the Course's section.
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section only if the section number is exactly 3 digits long.
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if the section is not exactly 3 digits long
	 */
	public void setSection(String section) {
		if (section == null || section.length() != 3) {
			throw new IllegalArgumentException("Invalid section number");
		}
		char[] sectionArray = section.toCharArray(); // turns String section into array of chars to parse
		for (int i = 0; i < section.length() - 1; i++) {
			if (!Character.isDigit(sectionArray[i])) {
				throw new IllegalArgumentException("Invalid section number");
			}
		}
		this.section = section;
	}

	/**
	 * Returns the Course's credits.
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits only if credits is between 1 and 5.
	 * 
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < 1 || credits > 5) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}

	/**
	 * Returns the Course's instructorId.
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructorId as long as it isn't null or empty.
	 * 
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null) {
			this.instructorId = null;
		} else if (instructorId.equals("")) {
			throw new IllegalArgumentException("Invalid instructor unity id");
		} else {
			this.instructorId = instructorId;
		}
	}

	/**
	 * Generates a hash code for the Object.
	 * 
	 * @return the hash code for the Object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Determines equality of two Course objects.
	 * 
	 * @return whether or not the two objects are equivalent
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (getMeetingDays().equals("A")) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
				+ roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Returns an array of length 4 containing the Course name, section, title, and
	 * meeting days string.
	 * 
	 * @return the array containing the information
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[5];
		String openSeats = Integer.toString(roll.getOpenSeats());
		shortDisplay[0] = getName();
		shortDisplay[1] = getSection();
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		shortDisplay[4] = openSeats;
		return shortDisplay;
	}

	/**
	 * Returns an array of length 7 containing the Course name, section, title,
	 * credits, instructorId, meeting days string, and an empty string.
	 * 
	 * @return the array containing the information
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		longDisplay[0] = getName();
		longDisplay[1] = getSection();
		longDisplay[2] = getTitle();
		longDisplay[3] = Integer.toString(getCredits());
		longDisplay[4] = getInstructorId();
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";
		return longDisplay;
	}

	/**
	 * Sets the Course meeting days based on the specifications for course.
	 * 
	 * @param meetingDays the meeting days that the user enters
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.equals("")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		char[] meetingDaysArray = meetingDays.toCharArray();
		char[] validDays = { 'M', 'T', 'W', 'H', 'F', 'A' };
		int numValidDays = 0;
		for (int i = 0; i < meetingDays.length(); i++) {
			for (int j = 0; j < 6; j++) {
				if (meetingDaysArray[i] == validDays[j]) {
					numValidDays++;
				}
			}

			if (numValidDays == 0) {
				throw new IllegalArgumentException("Invalid meeting days");
			} else {
				numValidDays = 0;
			}

			if (meetingDaysArray[i] == 'A' && meetingDays.length() != 1) {
				throw new IllegalArgumentException("Invalid meeting days");
			}
		}
		super.setMeetingDays(meetingDays);
	}

	/**
	 * Determines if the given Activity is a duplicate Course.
	 * 
	 * @param activity the Activity you are testing against
	 * @return whether or not they are duplicates
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course testDuplicate = (Course) activity;
			if (this.name.equals(testDuplicate.name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Course c) {
		if (c == null) {
			throw new NullPointerException("Field cannot be null");
		}
		if (!this.getName().equals(c.getName())) {
			int smallestName = c.getName().length();
			if (this.getName().length() < c.getName().length()) {
				smallestName = this.getName().length();
			}
			for (int i = 0; i < smallestName; i++) {
				if (this.getName().charAt(i) < c.getName().charAt(i)) {
					return -1;
				} else if (this.getName().charAt(i) > c.getName().charAt(i)) {
					return 1;
				}
			}
		}
		if (!this.getSection().equals(c.getSection())) {
			int smallestSection = c.getSection().length();
			if (this.getSection().length() < c.getSection().length()) {
				smallestSection = this.getSection().length();
			}
			for (int i = 0; i < smallestSection; i++) {
				if (this.getSection().charAt(i) < c.getSection().charAt(i)) {
					return -1;
				} else if (this.getSection().charAt(i) > c.getSection().charAt(i)) {
					return 1;
				}
			}
		}
		return 0;
	}

	/**
	 * returns the CourseRoll for the Course.
	 * @return the CourseRoll
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
}