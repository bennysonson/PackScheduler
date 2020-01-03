package edu.ncsu.csc216.pack_scheduler.course.validator;
/**
 * The purpose of the CourseNameValidator class is to determine if a course name
 * is valid for application to the PackSchedular Program as whole. The class has
 * multiple inner classes that are used as states for the course name String to be in.
 * This allows for seemless valid transitions between states.
 * @author Cameron Dutra
 * @author Luis
 * @author Benson Liu
 *
 */
public class CourseNameValidator {

	/** Field for object being in a valid final state*/
	private boolean validEndState;
	/** Field to hold the current number of letters*/
	private int letterCount;
	/** Field to hold the current number of digits*/
	private int digitCount;
	/** Holds an instance of the NumberState object*/
	private NumberState stateNumber = new NumberState();
	/** Holds an instance of the LetterState object*/
	private LetterState stateLetter = new LetterState();
	/** Holds an instance of the InitialState object*/
	private InitialState stateInitial = new InitialState();
	/** Holds an instance of the SuffixState object*/
	private SuffixState stateSuffix = new SuffixState();
	/** Field to keep track of the validator's current state*/
	private State currentState;
	
	/**
	 * Runs through inner classes to determine if the course name passed
	 * in is in fact valid
	 * @param courseName the name of the course to determine validity
	 * @return whether or not the course name is valid
	 * @throws InvalidTransitionException if the transition between states cannot be made
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		currentState = stateInitial;
		letterCount = 0;
		digitCount = 0;
		
		for (int i = 0; i < courseName.length(); i++) {
			char temp = courseName.charAt(i);
			if (Character.isDigit(temp)) {
				currentState.onDigit();
			} else if (Character.isLetter(temp)) {
				currentState.onLetter();
			} else {
				currentState.onOther();
			}
		}
		if (currentState == stateSuffix || digitCount == 3) {
			validEndState = true;
		} else {
			validEndState = false;
		}
		return validEndState;
	}
	
	/**
	 * The abstract class state is what is used to give different
	 * functionality to all of the classes that inherit it's methods.
	 * All of which are to determine whether the current character in
	 * the String are a letter, digit, or any other character.
	 * @author Luis 
	 *
	 */
	public abstract class State {
		/** Abstract method used when the character is a letter
		 * @throws InvalidTransitionException if the transition between states cannot be made
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		/** Abstract method used when the character is a digit
		 * @throws InvalidTransitionException if the transition between states cannot be made
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		/** Abstract method used when the character is any other character other than a digit or letter
		 * @throws InvalidTransitionException if the transition between states cannot be made
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
		
	}
	
	/**
	 * The LetterState inner class is used when a String is currently in the
	 * prefix state where up to the first four characters are letters
	 * @author Cameron Dutra
	 *
	 */
	public class LetterState extends State {
		/** Constant for maximum number of prefix letters*/
		private static final int MAX_PREFIX_LETTERS = 4;
		
		@Override
		/**
		 * Method used when a character is a letter and the String is in the Letter State
		 */
		public void onLetter() throws InvalidTransitionException {
			if(letterCount < MAX_PREFIX_LETTERS) {
				letterCount++;
			} else {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}	
		}

		@Override
		/**
		 * Method used when a character is a digit and the String is in the Letter State
		 */
		public void onDigit() {
			currentState = stateNumber;
			digitCount++;
		}
	}
	
	/**
	 * The SuffixState inner class is used when the String is at the end
	 * or suffix where the last character has to be a letter and only one of them
	 * @author Cameron Dutra
	 *
	 */
	public class SuffixState extends State {

		@Override
		/**
		 * Method used when a character is a letter and the String is in the Suffix State
		 */
		public void onLetter() throws InvalidTransitionException {
			letterCount++;
			if (letterCount >= 1) {
				throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
			}
		}

		@Override
		/**
		 * Method used when a character is a digit and the String is in the Suffix State
		 */
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");	
		}
	}
	
	/**
	 * The InitialState inner class is used when the String is first parsed
	 * and will create the correct transition between states based on the character
	 * that is first received
	 * @author Cameron Dutra
	 *
	 */
	public class InitialState extends State {

		@Override
		/**
		 * Method used when a character is a letter and the String is in the Initial State
		 */
		public void onLetter() {
			currentState = stateLetter;
			letterCount++;
		}

		@Override
		/**
		 * Method used when a character is a digit and the String is in the Initial State
		 */
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}
	
	/**
	 * The NumberState inner class is used when the a course name has been
	 * parsed enough to determine that it is past its prefix and is not checked
	 * to determine if it can be passed along to the correct state based on 
	 * the next character that is checked.
	 * @author Cameron Dutra
	 *
	 */
	public class NumberState extends State {
		/** Constant for the maximum length of a course number*/
		private static final int COURSE_NUMBER_LENGTH = 3;
		
		@Override
		/**
		 * Method used when a character is a letter and the String is in the Number State
		 * @throws InvalidTransitionException if the transition between states cannot be made
		 */
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				letterCount = 0;
				currentState = stateSuffix;
			} else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}

		@Override
		/**
		 * Method used when a character is a digit and the String is in the Number State
		 */
		public void onDigit() throws InvalidTransitionException {
			 if (digitCount < COURSE_NUMBER_LENGTH){
				digitCount++;
			 } else {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			 }
		}
	}
}
