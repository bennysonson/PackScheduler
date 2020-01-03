package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * The purpose of the ArrayList class is to keep track of a list of generic objects
 * and be able to manipulate and utilize the list with ease. The ArrayList class includes
 * methods that give the ability to add, remove, get, set, the ArrayList as well as a size
 * method that will return the current size of the ArrayList. The constructors of the ArrayList
 * will create an Array originally of capacity 10 and if the client decided to add more than 10
 * elements the Array capacity will double and will continue to double as long as the client would
 * like to go over capacity.
 *
 * @author Cameron Dutra
 * @param <E> generic type
 */
public class ArrayList<E> extends AbstractList<E> {
	
	/** the initial size of the ArrayList */
	private static final int INIT_SIZE = 10;
	
	/** the array of generic objects that will be used to make the ArrayList */
	private E[] list;
	
	/** the field to hold the current size of the ArrayList */
	private int size;
	
	/**
	 * Instantiates a new ArrayList using the ArrayList(int capacity) constructor with default size of 10.
	 */
	public ArrayList() {
		this(INIT_SIZE);
	}
	
	/**
	 * Instantiates a new array list with a given capacity.
	 *
	 * @param capacity the capacity given by the client
	 * @throws IllegalArgumentException if the capacity is less than zero.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		this.list = (E[])(new Object[capacity]);	
	}
	
	/**
	 * Adds an element to the ArrayList if possible and will throw different exceptions for different specific cases.
	 *
	 * @param  index the index of where the client wants the element to go
	 * @param  element the element that is to be added
	 * @throws NullPointerException if the element is null
	 * @throws IndexOutOfBoundsException if index is less than zero or greater than the size of the ArrayList
	 * @throws IllegalArgumentException if the element already exists in the list
	 */
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (this.size > 0) {
			for (int i = 0; i < size; i++) {
				if (this.get(i).equals(element)) {
					throw new IllegalArgumentException();
				}
			}
		}

		
		if (size + 1 >= list.length) {
			this.growArray();
		}
		
		if (index == size) {
			list[index] = element; //adding at the end
			size++;
		} else {
			for (int i = size; i > index; i--) {
				list[i] = list[i - 1];
			}
			list[index] = element;
			size++;
		}
	}

	/**
	 * Removes elements from the ArrayList given the index of the element.
	 *
	 * @param  index the index that the client wants to remove at
	 * @return the element that was found at the index and that was removed
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater than the size of the ArrayList
	 */
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		E elementFound = list[index];

		if (index == size - 1) {
			//removes at end of list
			list[size - 1] = null;
			size--;
		} else {
			for (int i = index; i < size - 1; i++) {
				list[i] = list[i + 1];
			}
			list[size - 1] = null;
			size--;
		}
		
		return elementFound;
	}
	
	/**
	 * Sets the element at a particular index to a given element that is provided by the client.
	 *
	 * @param  index the index that the method will set the current element at
	 * @param  element the element that is being set at the location
	 * @return the previous element in the location
	 * @throws NullPointerException if the element is null
	 * @throws IndexOutOfBoundsException if index is less than zero or greater than the size of the ArrayList
	 * @throws IllegalArgumentException if the element already exists in the list
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (element == null) {
			throw new NullPointerException();
		}
		
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		E temp = list[index];
		list[index] = element;
		return temp;
	}
	
	/**
	 * Gets the element that is at a given index
	 *
	 * @param  index the index that the client is trying to retrieve the element at
	 * @return the element that is at the given index
	 * @throws IndexOutOfBoundsException if index is less than zero or greater than the size of the ArrayList
	 */
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}

	/**
	 * This method will double the size of list when told to do so by the add method.
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] temp = (E[])(new Object[2 * size]);
		for (int i = 0; i < size; i++) {
			temp[i] = list[i];
		}
		this.list = temp;
	}

	/**
	 * Returns the current size of the ArrayList.
	 *
	 * @return the size of the ArrayList
	 */
	public int size() {
		return this.size;
	}
}
