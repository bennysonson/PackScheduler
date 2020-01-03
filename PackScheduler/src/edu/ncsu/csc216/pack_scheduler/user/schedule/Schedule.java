/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Maintains the Schedule object for PackScheduler. A Schedule maintains an
 * ArrayList of Courses. A schedule can have courses added or removed, and the
 * user can give a Schedule a title.
 * 
 * @author Cameron Dutra
 *
 */
public class Schedule {
	/** Title of schedule */
	private String title;
	/** ArrayList of Courses */
	private ArrayList<Course> schedule;

	/**
	 * Constructor for Schedule. Sets title to default "My Schedule" and creates an
	 * empty ArrayList of Courses.
	 */
	public Schedule() {
		this.title = "My Schedule";
		schedule = new ArrayList<Course>();
	}

	/**
	 * Adds a course to Schedule. An IllegalArgumentException is thrown if course is
	 * already in the schedule or there is a time conflict.
	 * 
	 * @param course course being added
	 * @return true if schedule was successfully added, false if not
	 */
	public boolean addCourseToSchedule(Course course) {
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getName().equals(course.getName())) {
				throw new IllegalArgumentException("You are already enrolled in " + course.getName());
			} else {
				try {
					course.checkConflict(schedule.get(i));
				} catch (ConflictException e) {
					throw new IllegalArgumentException();
				}
			}
		}
		schedule.add(course);
		return true;

	}

	/**
	 * Removes course from Schedule.
	 * 
	 * @param course course to be removed
	 * @return true if course can be removed, false if cannot
	 */
	public boolean removeCourseFromSchedule(Course course) {
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).equals(course)) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Resets schedule by creating new ArrayList of Courses and setting title back
	 * to default "My Schedule"
	 */
	public void resetSchedule() {
		this.title = "My Schedule";
		schedule = new ArrayList<Course>();
	}

	/**
	 * Returns a 2D array of Courses in Schedule
	 * 
	 * @return 2D array of Courses in Schedule
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduleDisplay = new String[schedule.size()][5];
		for (int i = 0; i < schedule.size(); i++) {
			scheduleDisplay[i] = schedule.get(i).getShortDisplayArray();
//			scheduleDisplay[i][1] = schedule.get(i).getSection();
//			scheduleDisplay[i][2] = schedule.get(i).getTitle();
//			scheduleDisplay[i][3] = schedule.get(i).getMeetingString();
//			scheduleDisplay[i][4] = getScheduleCredits() + "";
		}
		return scheduleDisplay;
	}

	/**
	 * Sets title for Schedule. Throws an IllegalArgumentException is title is null
	 * 
	 * @param title Title to be set for Schedule
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}

		this.title = title;
	}

	/**
	 * Returns title of Schedule
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns number of Courses in Schedule
	 * 
	 * @return number of Courses
	 */
	public int getSize() {
		return schedule.size();
	}
	
	/**
	 * gets the number of schedule credits
	 * @return the schedule credits
	 */
	public int getScheduleCredits() {
		int credits = 0;
		for (int i = 0; i < getSize(); i++) {
			credits += schedule.get(i).getCredits();
		}
		return credits;
	}
	
	/**
	 * tests if a course can be added to a schedule
	 * @param c the course to be checked
	 * @return true if the course can be added, false if not
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getName().equals(c.getName())) {
				return false;
			}
			try {
				schedule.get(i).checkConflict(c);
			} catch (ConflictException e) {
				return false;
			}
		}
		return true;
	}
}
