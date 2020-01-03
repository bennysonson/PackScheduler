package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * The purpose of the CourseCatalog object is to create and keep track of a list
 * of Courses that are available at the university. The CourseCatalog method and
 * the newCourseCatalog method both create an empty CourseCatalog that consists
 * of an empty SortedList. There is also a loadCoursesFromFile and
 * saveCourseCatalog which will read in Courses from a file and export them to a
 * file. getCourseFromCatalog will be able to return a Course based on a
 * specified name and section. You can add and remove Courses from a catalog
 * with the addCourseToCatalog and removeCourseFromCatalog method. Finally,
 * getCourseCatalog will return a 2D String array of the current CourseCatalog.
 * 
 * @author Cameron Dutra
 *
 */
public class CourseCatalog {
	/** the catalog of Courses */
	private SortedList<Course> catalog;

	/**
	 * Constructor for the CourseCatalog that will initialize an empty SortedList of Courses, catalog.
	 */
	public CourseCatalog() {
		this.catalog = new SortedList<Course>();
	}

	/**
	 * The newCourseCatalog method will initialize an empty SortedList of Courses, catalog.
	 */
	public void newCourseCatalog() {
		this.catalog = new SortedList<Course>();
	}

	/**
	 * The loadCoursesFromFile method is almost self explanatory. The method will
	 * read in Courses that are read from a file with the help of the CourseRecordIO
	 * class.
	 * 
	 * @param fileName the name of the file to read the files from
	 * @throws IllegalArgumentException if the file could not be found or read in.
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			this.catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}

	/**
	 * The addCourseToCatalog will try to add new Courses to the current
	 * CourseCatalog. It will return true if the Course is able to be added meaning
	 * that it doesn't conflict with another that is currently in the catalog
	 * already. It will return false if the Course could not be added to the catalog
	 * for any reason.
	 * 
	 * @param name          the name of the Course
	 * @param title         the title of the Course
	 * @param section       the section number of the Course
	 * @param credits       the number of credits the class is worth
	 * @param instructorId  the id of the Course instructor
	 * @param enrollmentCap the max students that can be enrolled
	 * @param meetingDays   the days that the class meets
	 * @param startTime     the time that the class begins
	 * @param endTime       the time that the class ends
	 * @return whether or not the Course was able to be added
	 * @throws IllegalArgumentException if there is an issue creating the Course
	 *                                  object or if there is an issue adding the
	 *                                  Course to the catalog.
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		Course courseBeingAdded = null;
		if (meetingDays.equals("A")) {
			courseBeingAdded = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays);
		} else {
			courseBeingAdded = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		}

		if (catalog.size() == 0) {
			catalog.add(courseBeingAdded);
			return true;
		}

		for (int i = 0; i < catalog.size(); i++) {
			boolean duplicate = false;
			for (int j = 0; j < catalog.size(); j++) {
				Course possibleDuplicate = catalog.get(j);
				if (possibleDuplicate.getName().equals(courseBeingAdded.getName())
						&& possibleDuplicate.getSection().equals(courseBeingAdded.getSection())) {
					duplicate = true;
				}
			}
			if (!duplicate) {
				try {
					catalog.add(courseBeingAdded);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException();
				}
				return true;
			}
		}
		return false;

	}

	/**
	 * The removeCourseFromCatalog method will attempt to remove the course in
	 * question given its name and section. If possible to remove, the method will
	 * do so and return true. If there is a complication then the method will just
	 * return false.
	 * 
	 * @param name    the name of the course trying to be removed
	 * @param section the section of the course
	 * @return whether or not the course was able to be removed
	 * @throws IllegalArgumentException if there is an issue removing the course or
	 *                                  if there is an issue creating the Course object
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		Course courseBeingRemoved;
		try {
			courseBeingRemoved = this.getCourseFromCatalog(name, section);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < catalog.size(); i++) {
			Course courseInCatalog = catalog.get(i);
			if (courseInCatalog.isDuplicate(courseBeingRemoved)) {
				try {
					catalog.remove(i);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * The getCourseFromCatalog method returns the Course that is found within the
	 * catalog and if the Course in question is not within the catalog, then null is
	 * returned.
	 * 
	 * @param name    the name of the Course trying to be removed
	 * @param section the section of the Course trying to be removed
	 * @return the Course if it is found in the catalog or null if not found
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}

	/**
	 * The getCourseCatalog method will return a 2 dimensional String array of the
	 * information for each Course that is currently in the CourseCatalog.
	 * 
	 * @return the 2D String array with all the information
	 */
	public String[][] getCourseCatalog() {
		String[][] courseCatalog = new String[catalog.size()][4];
		for (int i = 0; i < catalog.size(); i++) {
			courseCatalog[i] = catalog.get(i).getShortDisplayArray();
//			courseCatalog[i][1] = catalog.get(i).getSection();
//			courseCatalog[i][2] = catalog.get(i).getTitle();
//			courseCatalog[i][3] = catalog.get(i).getMeetingString();
		}
		return courseCatalog;
	}

	/**
	 * The saveCourseCatalog method will save the current CourseCatalog to the
	 * specified file given by the fileName parameter.
	 * 
	 * @param fileName the name of the file that you want the CourseCatalog output
	 *                 to.
	 * @throws IllegalArgumentException if there is a an issue writing to the file
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}
}