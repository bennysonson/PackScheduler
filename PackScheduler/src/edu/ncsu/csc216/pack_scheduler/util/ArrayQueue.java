package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * The purpose of the customized ArrayQueue is to keep track of a queue of
 * generic objects and be able to utilize the functionality of a queue with
 * ease. It includes the ability to add to the end of a queue and remove from
 * the beginning of a queue. Additionally, it provides the functionality to
 * check the size of the queue, check if a queue is empty, remove an element at
 * a particular index of the queue, check if an element already exists in a
 * queue and the ability to set a limit on the size of the queue.
 * 
 * @author John Preston
 * @author Pradhan Chetan Venkataramaiah
 * @param <E> generic object
 */
public class ArrayQueue<E> implements Queue<E> {
	/** The list of elements contained in this ArrayQueue */
	private ArrayList<E> list;
	/** The capacity of the ArrayQueue */
	private int capacity;

	/**
	 * Instantiates a new queue with the specified capacity.
	 * @param capacity maximum size of the queue
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds the provided element at the back of list (at index size() of the ArrayList).
	 * @throws IllegalArgumentException if the size of the list is greater than equal to capacity
	 */
	@Override
	public void enqueue(E element) {
		if (size() >= capacity)
			throw new IllegalArgumentException();
		list.add(size(), element);
	}

	/**
	 * Removes the element at the front of list (at index 0 of the ArrayList).
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
	 * @throws IllegalArgumentException when capacity is less than 0 or the amount of elements already in list.
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size())
			throw new IllegalArgumentException();

		this.capacity = capacity;
	}

	/**
	 * Checks the current queue for duplicates and returns a boolean value
	 * indicating whether a duplicate exists or not.
	 * 
	 * @param element element to be compared with for duplicity
	 * @return whether a duplicate exists in the queue
	 */
	public boolean contains(E element) {
		for (int i = 0; i < list.size(); i++) {
			if (element.equals(list.get(i)))
				return true;
		}
		return false;

	}

	/**
	 * Removes the parsed element from the queue.
	 * @param element element to be removed
	 */
	public void remove(E element) {
		for (int i = 0; i < list.size(); i++) {
			if (element.equals(list.get(i)))
				list.remove(i);
		}
	}
}
