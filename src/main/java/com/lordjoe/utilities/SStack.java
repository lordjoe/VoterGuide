/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/*
 * { file
 * @name SStack.java
 * @function Stack represtting a Set of Strings
 * @author> Steven M. Lewis
 * @copyright>
 * ***********************
 * Copyright (c) 1996,97,98
 * Steven M. Lewis
 * www.LordJoe.com
 * ***********************
 * 
 * @date> Mon Jun 22 21:48:24 PDT 1998
 * @version> 1.0
 * }
 */
package com.lordjoe.utilities;

/**
 * 
 * SStack
 * Stack represtting a Set of Strings
 * @author  Steven M. Lewis
 * 
 */
public class SStack {
	public static final int DEFAULT_SIZE = 8;
	private int				m_top;
	String[]				m_TheStack;

	// - *******************
	// - Methods

	/**
	 * Constructor
	 */
	public SStack() {
		m_TheStack = new String[DEFAULT_SIZE];

		clear();
	}

	/**
	 * Drop all elements from the stack
	 */
	public synchronized void clear() {
		m_top = 0;

		for (int i = 0; i < m_TheStack.length; i++) {
			m_TheStack[i] = null;
		}
	} 

	/**
	 * true if there are no items on the stack
	 * @return true if there are no items on the stack
	 */
	public boolean isEmpty() {
		return (m_top == 0);
	} 

	/**
	 * get the size
	 * @return the stack size
	 */
	public int size() {
		return (m_top);
	} 

	/**
	 *  Add an item to the stack
	 * @param Plus non-null String
	 * @return current stack size
	 * @see
	 */
	public synchronized int push(String Plus) {
		if (m_top >= m_TheStack.length) 

		/* increase m_queuesize if needed and allowed */
		{
			extend();
		} 

		m_TheStack[m_top++] = Plus;

		return (m_top);
	} 

	/**
	 * Test whether an item is on the stack
	 * @param Plus non-null String
	 * @return true if a member
	 * @see
	 */
	boolean member(String Plus) {
		for (int i = 0; i < m_top; i++) {
			if (m_TheStack[i].equals(Plus)) {
				return (true);
			} 
		} 

		return (false);

		// All	OK
	} 

	/**
	 * return the top element
	 * @return non-null unless the stack is empty
	 */
	public String top() {
		String theret = null;

		if (!isEmpty()) {

			/* anything in the queue */
			theret = m_TheStack[m_top - 1];
		} 

		return (theret);
	} 

	/**
	 * remove and return the top element
	 * @return non-null unless the stack is empty
	 */
	public synchronized String pop() {
		String theret = null;

		if (!isEmpty()) {

			/* anything in the queue */
			theret = m_TheStack[--m_top];
			m_TheStack[m_top] = null;	 // clear out the stack


			return (theret);
		} 

		return (theret);
	} 

	/**
	 * Internal method to add more space to the stack
	 * @return new size
	 */
	protected int extend() {
		int		 i;
		int		 TheSize = Math.max(2 * m_TheStack.length, DEFAULT_SIZE);
		String[] Old = m_TheStack;

		m_TheStack = new String[TheSize];

		for (i = 0; i < m_top; i++) {
			m_TheStack[i] = Old[i];
		} 

		return (TheSize);
	} 

	// - *******************
	// - End Class SStack
}



/*--- formatting done in "Onvia Java Convention" style on 04-21-2000 ---*/


