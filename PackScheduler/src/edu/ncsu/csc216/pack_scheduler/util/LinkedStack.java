package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * The purpose of the customized LinkedStack is to keep track of a stack of
 * generic objects and be able to utilize the functionality of a stack with
 * ease. It implements the stack in the form of LinkedList. It includes the
 * ability to add to the top of a stack and remove from the top of a stack.
 * Additionally, it provides the functionality to check the size of the stack,
 * check if a stack is empty, and the ability to set a limit on the size of the
 * stack.
 * 
 * @author Pradhan Chetan Venkataramaiah
 *
 * @param <E> generic object
 */
public class LinkedStack<E> implements Stack<E> {

	private LinkedAbstractList<E> list;

	/**
	 * Constructs a LinkedStack object by instantiating list to an empty LinkedAbstractList to the specified capacity.
	 * 
	 * @param capacity maximum capacity of the LinkedStack
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
		setCapacity(capacity);
	}

	/**
	 * Pushes/adds the provided element to the top of list (at index 0 of the LinkedAbstractList).
	 */
	@Override
	public void push(E element) {
		// capacity check is already implemented in LinkedList list
		list.add(0, element);
	}

	/**
	 * Removes the element at the top of list (at index 0 of the LinkedAbstractList).
	 * @return the removed element
	 * @throws EmptyStackException when list is empty
	 */
	@Override
	public E pop() {
		if (isEmpty())
			throw new EmptyStackException();

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
