/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Specialized exception to be thrown when two Activity objects are conflicting
 * @author CameronDutra
 *
 */
public class ConflictException extends Exception {
	/**
	 * ID used for serialization.
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * Constructs an Exception object with a custom error message
     * @param message String specifying a message for the Exception object
     */
    public ConflictException(String message) {
    	super(message);
    }
    
    /**
     * Constructs an Exception object with the default error message
     */
    public ConflictException() {
    	super("Schedule conflict.");
    }
}
