package edu.ncsu.csc216.pack_scheduler.util;

/**
 * An interface to implement the FIFO functionality of a Queue.
 * 
 * @author John Preston
 * @author Pradhan Chetan Venkataramaiah
 * @param <E> generic object
 */
public interface Queue<E> {
	/**
	 * Functionality to add an element to the end of a queue.
	 * @param element element to be added at the end of the queue
	 */
	void enqueue(E element);

	/**
	 * Functionality to remove an element from the front of a queue.
	 * @return element removed from the queue
	 */
	E dequeue();

	/**
	 * Functionality to check if a queue is empty.
	 * @return whether the queue is empty or not
	 */
	boolean isEmpty();

	/**
	 * Functionality for retrieving the size of a queue.
	 * @return size of the current queue
	 */
	int size();

	/**
	 * Functionality for setting the maximum size of a queue.
	 * @param capacity Maximum capacity of the queue
	 */
	void setCapacity(int capacity);
}
