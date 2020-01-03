package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * The purpose of the customized ArrayStack is to keep track of a stack of
 * generic objects and be able to utilize the functionality of a stack with
 * ease. It includes the ability to add to the top of a stack and remove from
 * the top of a stack. Additionally, it provides the functionality to check the
 * size of the stack, check if a stack is empty, and the ability to set a limit
 * on the size of the stack.
 * 
 * @author Pradhan Chetan Venkataramaiah
 * @param <E> generic object
 */
public class ArrayStack<E> implements Stack<E> {
	/** The list of elements contained in this ArrayStack */
	private ArrayList<E> list;
	/** The capacity of the ArrayQueue */
	private int capacity;

	/**
	 * Contructs an ArrayStack object by instantiating list.
	 * @param capacity Maximum size of the stack
	 */
	ArrayStack(int capacity) {
		list = new ArrayList<E>();
//		this.capacity = capacity;
		setCapacity(capacity);
	}

	/**
	 * Pushes/adds the provided element to the top of list (at index 0 of the ArrayList).
	 * @throws IllegalArgumentException when the size of list is greater than or equal to the capacity of list.
	 */
	@Override
	public void push(E element) {
		if (size() >= capacity)
			throw new IllegalArgumentException();
		list.add(0, element);
	}

	/**
	 * Removes the element at the top of list (at index 0 of the ArrayList).
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
	 * @throws IllegalArgumentException when capacity is less than 0 or the amount of elements already in list.
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size())
			throw new IllegalArgumentException();

		this.capacity = capacity;
	}

}
