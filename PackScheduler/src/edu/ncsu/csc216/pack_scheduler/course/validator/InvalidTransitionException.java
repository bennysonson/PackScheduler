/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * The purpose of the InvalidTransitionException class is to inform the client that they
 * have made an invalid transition between states. The InvalidTransitionException class has
 * a default Exception message "Invalid FSM Transition" or you can construct it with a custom
 * message that is passed in to the constructor.
 * @author Cameron Dutra
 * @author Benson Liu
 * @author Luis 
 *
 */
public class InvalidTransitionException extends Exception {
	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs an InvalidTransitionException object with a custom error message
	 * @param message String specifying a message for the InvalidTransitionException object
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Constructs an InvalidTransitionException object with the default error message
	 */
	public InvalidTransitionException() {
		super("Invalid FSM Transition.");
	}
}
