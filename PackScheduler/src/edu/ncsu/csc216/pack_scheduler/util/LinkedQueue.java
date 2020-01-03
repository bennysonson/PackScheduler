package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * The purpose of the customized LinkedQueue is to keep track of a Queue of
 * generic objects and be able to utilize the functionality of a Queue with
 * ease. It implements the Queue in the form of LinkedList. It includes the
 * ability to add to the end of a Queue and remove from the front of a Queue.
 * Additionally, it provides the functionality to check the size of the Queue,
 * check if a Queue is empty, and the ability to set a limit on the size of the
 * Queue.
 * 
 * @author John Preston
 * @author Pradhan Chetan Venkataramaiah
 * @param <E> generic object
 */
public class LinkedQueue<E> implements Queue<E> {

	private LinkedAbstractList<E> list;

	/**
	 * Constructs a LinkedQueue object by initializing list to an empty LinkedAbstractList to the specified capacity.
	 * 
	 * @param capacity Maximum capacity of the LinkedQueue
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}

	/**
	 * Adds the provided element at the back of list (at index size() of the LinkedAbstractList).
	 */
	@Override
	public void enqueue(E element) {
		// capacity check is already implemented in LinkedList list
		list.add(size(), element);
	}

	/**
	 * Removes the element at the front of list (at index 0 of the LinkedAbstractList).
	 * @return the removed element
	 * @throws NoSuchElementException when list is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();

		return list.remove(0);
	}

	/**
	 * Returns if list is empty or not.
	 * @return true if the list is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the size of list.
	 * @return the size of list
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the capacity of the list to the desired capacity.
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}
}