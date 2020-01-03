package edu.ncsu.csc216.pack_scheduler.util;

/**
 * The LinkedListRecursive class maintains the same functionality as the custom LinkedList but utilizes recursive
 * private methods inside of the private inner ListNode class to accomplish that functionality. LinkedListRecursive
 * maintains the head of the list and its size contains public methods used to manipulate the list by utilizing the
 * aforementioned private recursive versions of the methods in ListNode.
 * 
 * @author Benson Liu
 * @author John Preston
 * @param <E> generic type
 */
public class LinkedListRecursive<E> {
	/** the front of the list */
	private ListNode front;
	/** the size of the list */
	private int size;
	
	/**
	 * Constructs a LinkedListRecursive object by initializing the size to 0 and making the front ListNode null.
	 */
	public LinkedListRecursive() {
		this.front = null;
		size = 0;
	}
	
	/**
	 * Returns the size of the list.
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Denotes when the list is empty.
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Denotes when a given element is contained within the list.
	 * @param  element the element to be searched for in the list
	 * @return true if the given element is in the list, false otherwise
	 */
	public boolean contains(E element) {
		if (front == null) {
			return false;
		} else if (front.data.equals(element))  {
			return true;
		} else {
			return front.contains(element);
		}
	}
	
	/**
	 * Adds an element to the list and, if successful, return true. An element cannot be added to a list
	 * if it is null or it is within the list already. If the list is not empty, this method utilizes its
	 * recursive counterpart in ListNode to append the element to the end of the list.
	 * 
	 * @param  element the element to add to the list
	 * @return true if the element was successfully added to the list.
	 */
	public boolean add(E element) {
		if (element == null || contains(element)) {
			return false;
		} else if (front == null) {
			front = new ListNode(element, null);
			size++;
			return true;
		} else {
			return front.add(element);
		}
	}
	
	/**
	 * Adds the given element at the given index in the list, provided the element and index are valid.
	 * If the index is not zero and valid, this method will utilize its recursive counterpart in ListNode to
	 * add the given element at the given index.
	 * 
	 * @param  index the index of where to add the element
	 * @param  element the element to add to the list
	 * @throws IllegalArgumentException when the element is already in the list
	 * @throws IndexOutOfBoundsException when the index is less than 0 or greater than the size of the list
	 * @throws NullPointerException when the given element is null
	 */
	public void add(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		} else if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		} else if (element == null) {
			throw new NullPointerException();
		} 
		
		if (index == 0) {
			front = new ListNode(element, front);
			size++;
		} else {
			front.add(index - 1, element);
		}
	}
	
	/**
	 * Retrieves the element at the given index in the list. If the index is not zero and valid, this method utilizes its
	 * recursive counterpart in order to accomplish its goal.
	 * 
	 * @param  index the index of the element to retrieve
	 * @return the element at the given index in the list
	 * @throws IndexOutOfBoundsException when index less than 0 or greater than or equal to the size of the list
	 */
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index == 0) {
			return front.data;
		} else {
			return front.get(index - 1);
		}
	}
	
	/**
	 * Removes the given element from the list. If the element is not at the front or next position of the list,
	 * this method utilizes its recursive counterpart in ListNode to accomplish its goal.
	 * 
	 * @param  element the element to remove from the list
	 * @return true if the element was removed successfully
	 */
	public boolean remove(E element) {
		if (element == null || front == null) {
			return false;
		} 
		
		if (front.data.equals(element)) {
			front = front.next;
			size--;
			return true;
		} 
		
		if (front.next == null) {
			return false;
		}
		if (front.next.data.equals(element)) {
			front.next = front.next.next;
			size--;
			return true;
		}
		
		return front.remove(element);
	}
	
	/**
	 * Removes the element from the list at the given index. If the index is not 0, 1, and is valid, this method
	 * utilizes its recursive counterpart in order to accomplish its goal.
	 * 
	 * @param  index the index of the element to remove
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to the size of the list
	 */
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index == 0) {
			E result = front.data;
			front = front.next;
			size--;
			return result;
		} else if (index == 1) { 
			E result = front.next.data;
			front.next = front.next.next;
			size--;
			return result;
		} else {
			return front.remove(index - 1);
		}
	}
	
	/**
	 * Sets the element at the given index in the list to the new given element.
	 * 
	 * @param index the index of the element to set
	 * @param element the new element to replace the old element at index i
	 * @return the old element at index i
	 * @throws IndexOutOfBoundsException when index is less than 0 or greater than or equal to the size of the list
	 * @throws IllegalArgumentException when the element is null or is already within the list
	 * @throws NullPointerException when the given element is null
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		} else if (element == null) {
			throw new NullPointerException();
		} else if (contains(element)) {
			throw new IllegalArgumentException();
		}
		
		if (index == 0) {
			E result = front.data;
			front.data = element;
			return result;
		} else {
			return front.set(index - 1, element);
		}
	}
	
	/**
	 * The private ListNode class contains a reference to the next ListNode in the list and an element
	 * of the list. The LinkedListRecursive class heavily relies on the functionality of LinkedNode to work properly.
	 * 
	 * @author Benson Liu
	 * @author John Preston
	 */
	private class ListNode {
		/** the element of the list associated with this ListNode */
		private E data;
		/** the next ListNode in the list */
		private ListNode next;
		
		/**
		 * Contructs a ListNode object with the provided element and reference to another ListNode.
		 * 
		 * @param data an element of the list now associated with this ListNode
		 * @param next the reference to the next ListNode in the list
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		/**
		 * Utilizes recursion to iterate through the list to see if the given element is contained in the list.
		 * LinkedListRecursive.contains() heavily depends on this method's functionality.
		 * 
		 * @param  element the element to be searched for in the list
	     * @return true if the given element is in the list, false otherwise
		 */
		private boolean contains(E element) {
			if (data.equals(element)) {
				return true;
			} else if (next == null) {
				return false;
			} else {
				return next.contains(element);
			}
		}
		
		/**
		 * Utilizes recursion to iterate through the list until it reaches the end where it adds the given element.
		 * LinkedListRecursive.add(E) heavily depends on this method's functionality.
		 * 
		 * @param  element the element to add to the list
	     * @return true if the element was successfully added to the list
		 */
		private boolean add(E element) {
			if (next == null) {
				next = new ListNode(element, null);
				size++;
				return true;
			} else {
				return next.add(element);
			}
		}
		
		/**
		 * Utilizes recursion to iterate through the list until it reaches the given index where it adds the given element.
		 * LinkedListRecursive.add(int,E) heavily depends on this method's functionality.
		 * 
		 * @param  index the index of where to add the element
	     * @param  element the element to add to the list
	     * @throws IllegalArgumentException when the method reached the end of list before adding the element
		 */
		private void add(int index, E element) {
			if (index == 0) {
				next = new ListNode(element, next);
				size++;
			} else if (next == null) {
				throw new IllegalArgumentException();
			} else {
				next.add(index - 1, element);
			}
		}
		
		/**
		 * Utilizes recursion to iterate through the list until it reaches the given index where it returns the element
		 * at that index. LinkedListRecursive.get() heavily depends on this method's functionality.
		 * 
		 * @param  index the index of the element to retrieve
	     * @return the element at the given index in the list
	     * @throws IllegalArgumentException when the method reached the end of list before retrieving the element
		 */
		private E get(int index) {
			if (next == null) {
				throw new IllegalArgumentException();
			} else if (index == 0) {
				return next.data;
			} else {
				return next.get(index - 1);
			}
		}
		
		/**
		 * Utilizes recursion to iterate through the list until it finds the specified element and removes it.
		 * LinkedListRecursive.remove(E) heavily depends on this method's functionality.
		 * 
		 * @param  element the element to remove from the list
	     * @return true if the element was removed successfully
		 */
		private boolean remove(E element) {
			if (next.next == null) {
				return false;
			} else if (next.next.data.equals(element)) {
				next.next = next.next.next;
				size--;
				return true;
			} else {
				return next.remove(element);
			}
		}
		
		/**
		 * Utilizes recursion to iterate through the list until it reaches the given index and removes the element there.
		 * LinkedListRecursive.remove(int) heavily depends on this method's functionality.
		 * 
		 * @param  index the index of the element to remove
	     * @return the element that was removed
	     * @throws IllegalArgumentException if the method reaches the end of the list before removing the element
		 */
		private E remove(int index) {
			if (index - 1 == 0) {
				E result = next.next.data;
				next.next = next.next.next;
				size--;
				return result;
			} else if (next.next == null) {
				throw new IllegalArgumentException();
			} else {
				return next.remove(index - 1);
			}
		}
		
		/**
		 * Utilizes recursion to iterate through the list to the given index at set the element there to the new given element.
		 * LinkedListRecursize.set() heavily depends on this method's functionality.
		 * 
		 * @param index the index of the element to set
	     * @param element the new element to replace the old element at index i
	     * @return the old element at index i
	     * @throws IllegalArgumentException when the method reached the end of the list before it set anything
		 */
		private E set(int index, E element) {
			if (next == null) {
				throw new IllegalArgumentException();
			} else if (index == 0) {
				E result = next.data;
				next.data = element;
				return result;
			} else {
				return next.set(index - 1, element);
			}
		}
	}
}
