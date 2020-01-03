/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * The purpose of the LinkedAbstractList class is to keep track of multiple
 * items in a list and manipulate that list through the use of the methods that
 * are implemented. Those methods include add, remove, set, get, and size which
 * all have a critical function in submitting and retrieving information from
 * the list.
 * 
 * @author Cameron Dutra
 * @author Benson Liu
 * @param <E> generic type
 */
public class LinkedAbstractList<E> extends AbstractList<E> {
	/** the ListNode reference that keeps track of the beginning of the list */
	private ListNode front;
	/** the ListNode reference that keeps track of the back of the list */
	private ListNode back;
	/** the field that holds the size of the LinkedAbstractList */
	private int size;
	/** the capacity of LinkedAbstractList */
	private int capacity;

	/**
	 * Sets the capacity of the list.
	 * @param capacity capacity of the list
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size)
			throw new IllegalArgumentException();
		this.capacity = capacity;

	}

	/**
	 * Constructor for the LinkedAbstractList class that requires a capacity of how
	 * big to set the LinkedAbstractList and will create a new LinkedAbstractList
	 * with said capacity.
	 * 
	 * @param  capacity the capacity of the list
	 * @throws IllegalArgumentException if the capacity is less than size or if capacity is less than zero
	 */
	public LinkedAbstractList(int capacity) {
		super();
		this.size = 0;
		this.front = null;
		setCapacity(capacity);
		this.back = front;
	}

	/**
	 * The add method will add a new element to the LinkedAbstractList as long as
	 * the size doens't equal capacity, the element is not null, or the element that
	 * is trying to be added is a duplicate.
	 * 
	 * @param  index   the index to add at
	 * @param  element the element to add to the list
	 * @throws IllegalArgumentException  if size equals capacity or if the element is duplicate
	 * @throws NullPointerException      if the element is null
	 * @throws IndexOutOfBoundsException if the index is not within the bounds of size
	 */
	@Override
	public void add(int index, E element) {
		// error checking
		if (element == null) {
			throw new NullPointerException();
		}
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		ListNode current = front;
		while (current != null) {
			if (current.data.equals(element)) {
				throw new IllegalArgumentException();
			}
			current = current.next;
		}

		// add to front of list
		if (index == 0) {
			front = new ListNode(element, front);

			// add to end of list
		} else if (index == size) {
			current = front;
			while (current.next != null) {
				current = current.next;
			}
			current.next = new ListNode(element);

			// add to middle of list
		} else {
			current = front;
			for (int i = 1; i < index; i++) {
				current = current.next;
			}
			current.next = new ListNode(element, current.next);
		}
		back = front;
		while (back.next != null) {
			back = back.next;
		}
		size++;
	}

	/**
	 * The remove list will remove an element in a list given its index as long as
	 * the index is greater than zero and the index is less than the size of the
	 * LinkedAbstractList. The method then returns the element that was removed from
	 * the LinkedAbstractList.
	 * 
	 * @param  idx the index to remove at
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if the index is less than zero or the index
	 *                                   is greater than the size
	 */
	@Override
	public E remove(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E elementFound;
		if (idx == 0) {
			elementFound = front.data;
			front = front.next;
		} else if (idx < size - 2) {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			elementFound = current.next.data;
			current.next = current.next.next;
		} else {
			ListNode current = front;
			for (int i = 0; i < idx - 2; i++) {
				current = current.next;
			}
			elementFound = current.next.data;
			current.next = null;
		}
		size--;
		return elementFound;
	}

	/**
	 * The set method will change the element that is located at a given index with
	 * a given element as long as the index is within the bounds of the size and the
	 * element trying to replace the current one is not null. When the method
	 * successfully sets the element at the index it will return the element that
	 * was replaced.
	 * 
	 * @param  idx     the index to set at
	 * @param  element the element to set in the list
	 * @return the element that was overridden
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * @throws NullPointerException      if the element is null
	 */
	@Override
	public E set(int idx, E element) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}

		ListNode current = front;

		for (int i = 0; i < size - 1; i++) {
			if (current.data.equals(element)) {
				throw new IllegalArgumentException();
			}
			current = current.next;
		}

		current = front;

		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		E temp = current.data;
		current.data = element;
		return temp;
	}

	/**
	 * The get method is responsible for retrieving an element at a particular index
	 * in the list. The method will only return the element if the index passed in
	 * is within bounds.
	 * 
	 * @param index the index to retrieve from the list
	 * @return the element that was retrieved
	 * @throws IndexOutOfBoundsException if the index is not within the bounds of size
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		return current.data;
	}

	/**
	 * Returns the current size of the LinkedAbstractList.
	 * 
	 * @return the size of the current LinkedAbstractList
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * The purpose of the ListNode class is to have functionality for the
	 * LinkedAbstractList class. A ListNode is essentially an object that consists
	 * of two parts, one is the value of the current object and the other is the
	 * reference to the next ListNode that follows. This class consists of two
	 * different constructors. One for when the user enters only the element and the
	 * other is for when the user enters the element and the reference to the next
	 * ListNode.
	 * 
	 * @author Cameron Dutra
	 */
	private class ListNode {
		/** The generic for the data stored in the list */
		private E data;
		/** The ListNode that will come after the current ListNode */
		private ListNode next;

		/**
		 * Constructs a new ListNode by passing the information entered into the default
		 * constructor and sets the ListNode next reference to null.
		 * 
		 * @param data the data passed into the new ListNode
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * Constructs a new ListNode with the data that is entered into the method as
		 * well as a reference to the next ListNode that comes after itself.
		 * 
		 * @param data the data passed into the new ListNode
		 * @param next the ListNode that the current ListNode is referring to as next
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}

}
