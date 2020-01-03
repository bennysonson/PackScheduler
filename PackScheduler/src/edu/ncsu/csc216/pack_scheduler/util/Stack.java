package edu.ncsu.csc216.pack_scheduler.util;

/**
 * An interface to implement the LIFO functionality of a Stack.
 * 
 * @author John Preston
 * @author Pradhan Chetan Venkataramaiah
 * @param <E> generic object
 */
public interface Stack<E> {
	/**
	 * Functionality for pushing/adding an element to the top of a stack.
	 * @param element element to be added to the top of the stack
	 */
	void push(E element);

	/**
	 * Functionality for popping/removing an element from the top of a stack.
	 * @return element removed from the top of the stack
	 */
	E pop();

	/**
	 * Functionality to check if a stack is empty.
	 * @return whether a stack is empty or not
	 */
	boolean isEmpty();

	/**
	 * Functionality for retrieving the size of a stack.
	 * @return size of the current stack
	 */
	int size();

	/**
	 * Functionality for setting the maximum size of the stack.
	 * @param capacity Maximum capacity of the stack
	 */
	void setCapacity(int capacity);
}
