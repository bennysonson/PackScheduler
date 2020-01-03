package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface that will check to see if two Activities are conflicting with one another
 * @author CameronDutra
 */
public interface Conflict {
	/**
	 * Method to check if two Activities are conflicting
	 * @param possibleConflictingActivity the Activity object that is being tested against
     * @throws ConflictException if there is a conflict between two activities
     */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
