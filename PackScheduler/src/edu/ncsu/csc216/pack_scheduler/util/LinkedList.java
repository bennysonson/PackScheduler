package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The custom LinkedList class extends the functionality of AbstractSequentialList and doesn’t allow for 
 * null elements or duplicate elements as defined by the equals() method. The list is doubly linked and
 * contains private inner classes ListNode and LinkedListIterator to clarify the purpose of the set()
 * and add() methods.
 * 
 * @author Benson Liu
 * @author John Preston
 * @param <E> generic type
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/**	the front of the list */
	private ListNode front;
	/** the back of the list */
	private ListNode back;
	/** the size of the list */
	private int size;
	
	/**
	 * Constructs a LinkedList object by initializing the size to 0 and setting front and back to ListNodes 
	 * with null data and references to each other.
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}

	/**
	 * Returns a LinkedListIterator for this LinkedList starting right before the given index or rather
	 * in between the element at index-1 and the element at index.
	 * @return a LinkedListIterator for this LinkedList
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}
	
	/**
	 * Makes sure that element is not contained within the list (a duplicate), then utilizes AbstractSequentialList's
	 * set method to set the previous element at index to the given element.
	 * 
	 * @param  index the index where the element will be set
	 * @param  element the element to set
	 * @return the previous element at index in the list
	 * @throws IllegalArgumentException when element is a duplicate
	 */
	@Override
	public E set(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}

	/**
	 * Makes sure that element is not contained within the list (a duplicate), then utilizes AbstractSequentialList's
	 * add method to add the given element to the list at the index. All elements from index to the end of the list
	 * are right-shifted to make space for the new element.
	 * 
	 * @param  index the index where the element were be added
	 * @param  element the element to add
	 * @throws IllegalArgumentException when element is a duplicate
	 */
	@Override
	public void add(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);
	}

	/**
	 * Returns the size of the list.
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * The private ListNode class contains a reference to the previous and next ListNode in the list and an element
	 * of the list. The LinkedList class heavily relies on the functionality of LinkedNode to work properly.
	 * 
	 * @author Benson Liu
	 * @author John Preston
	 */
	private class ListNode {
		/** the data associated with this ListNode */
		private E data;
		/** the reference to the next ListNode in the list */
		private ListNode next;
		/** the reference to the previous ListNode in the list */
		private ListNode prev;
		
		/**
		 * Constructs a new ListNode by passing the information entered into the default
		 * constructor and sets the ListNode next reference to null.
		 * 
		 * @param data the data passed into the new ListNode
		 */
		public ListNode(E data) {
			this(data, null, null);
		}
		
		/**
		 * Constructs a new ListNode with the data that is entered into the method as
		 * well as a reference to the next ListNode and the previous ListNode that comes after itself.
		 * 
		 * @param data the data passed into the new ListNode
		 * @param prev the ListNode that the current ListNode is referring to as prev
		 * @param next the ListNode that the current ListNode is referring to as next
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	/**
	 * The private LinkedListIterator contains the functionality to iterate through LinkedList and manipulate
	 * the elements of said list. The LinkedList class heavily relies on the functionality of the LinkedListIterator
	 * class to work properly, especially the set() and add() methods.
	 * 
	 * @author Benson Liu
	 * @author John Preston
	 */
	private class LinkedListIterator implements ListIterator<E> {
		/** the reference to the previous element in the list */
		private ListNode prev = front;
		/** the reference to the next element in the list */
		private ListNode next = front.next;
		/** the index of the previous element in the list */
		private int previousIndex = -1;
		/** the index of the next element in the list */
		private int nextIndex;
		/**  the ListNode that was returned on the last call to either previous() or next() */
		private ListNode lastRetrieved;
		
		/**
		 * Constructs a LinkedListIterator object by first initializing prev to the front of the LinkedList
		 * and next to the next element (a LinkedList always contains a least two elements). The iterator then
		 * iterates to the desired index if the index is valid.
		 * 
		 * @param  index the index of where the iterator should start
		 * @throws IndexOutOfBoundsException when index is less than 0 or greater than size
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}			
			
			for (int i = 0; i < index; i++) {
				prev = next;
				next = next.next;
				previousIndex++;
				nextIndex++;
			}
		}

		/**
		 * Returns whether the list has a next element to iterate over.
		 * @return true if the list has a next element, false otherwise
		 */
		@Override
		public boolean hasNext() {
			return nextIndex < size;
		}

		/**
		 * Iterates to the next element if there is one and returns its value (lastRetrieved is also set to next
		 * to indicate that a call to next() has occurred).
		 * 
		 * @return the next element
		 * @throws NoSuchElementException when there is no next element
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			E value = (E) next.data;
			lastRetrieved = next;
			
			// move the iterator over
			prev = next;
			next = next.next;
			previousIndex++;
			nextIndex++;
			
			return value;
		}

		/**
		 * Returns whether the list has a previous element to iterate over.
		 * @return true if the list has a previous element, false otherwise
		 */
		@Override
		public boolean hasPrevious() {
			return previousIndex >= 0;
		}

		/**
		 * Iterates to the previous element if there is one and returns its value (lastRetrieved is also set to previous
		 * to indicate that a call to previous() has occurred).
		 * 
		 * @return the previous element
		 * @throws NoSuchElementException when there is no previous element
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			
			E value = (E) prev.data;
			lastRetrieved = prev;
			
			// move the iterator over
			next = prev;
			prev = prev.prev;
			previousIndex--;
			nextIndex--;
			
			return value;
		}

		/**
		 * Returns the index of the next element.
		 * @return the index of the next element
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns the index of the previous element.
		 * @return the index of the previous element
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * Removes the element that was last returned by next() or previous().
		 * @throws IllegalStateException when lastRetrieved is null
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			if (lastRetrieved == prev) {
				prev = prev.prev;
			} else if (lastRetrieved == next) {
				next = next.next;
			}
			
			prev.next = next;
			next.prev = prev;
			lastRetrieved = null;
			size--;
		}

		/**
		 * Sets the element last returned by next() or previous() to the given element e.
		 * 
		 * @param  e the element to set
		 * @throws IllegalStateException when lastRetrieved is null
		 * @throws NullPointerException when e is null
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			
			lastRetrieved.data = e;
		}

		/**
		 * Adds the given element e to the list at the index of nextIndex.
		 * 
		 * @param  e the element to add to the list
		 * @throws NullPointerException when e is null
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			
			ListNode middle = new LinkedList.ListNode(e);
			// connecting middle to previous and next nodes
			prev.next = middle;
			middle.prev = prev;
			next.prev = middle;
			middle.next = next;
			// replacing next with middle for the list iterator
			next = middle;
			size++;
			lastRetrieved = null;
		}
		
	}

}