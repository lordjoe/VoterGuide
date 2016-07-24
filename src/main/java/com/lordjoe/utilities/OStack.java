/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/*
 * 
 * OStack.java
 * @function Stack representing a collection of objects
 * @author> Steven M. Lewis
 * @copyright>
 * ***********************
 * Copyright (c) 2000
 * Steven M. Lewis
 * www.onvia.com
 * ***********************
 * 
 */
package com.lordjoe.utilities;
import com.lordjoe.exceptions.*;

import java.util.*;
import java.util.Arrays;
/**
 * 
 * OStack
 * Stack representing a collection of objects.
 * critical methods are 
 *   void   push(Object o) 
 *   Object pop()
 *   boolean isEmpty()
 * Stack is first in last out
 * Note - as a collection remove is not implemented
 *        add is push
 *        toArray returns the stack in order it would be popped and
 *           will only work when passed an array if the array is of the
 *           proper size
 * Note - if the stack is empty pop waits for aonther thread to 
 *  push something on it
 *
 * @author  Steven M. Lewis
 * 
 */
public class OStack extends AbstractCollection
{
	public static final int DEFAULT_SIZE = 8;
	private int				m_top; // top index of the stack
	private Object[]		m_TheStack; // Array holding the stack

	// - *******************
	// - Methods

	/**
	 * Constructor
	 */
	public OStack() {
		m_TheStack = new Object[DEFAULT_SIZE];
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
	 * @param Plus non-null Object
	 * @return current stack size
	 */
	public synchronized int push(Object Plus) {
		if (m_top >= m_TheStack.length) 

		/* increase m_queuesize if needed and allowed */
		{
			extend();
		} 

		m_TheStack[m_top++] = Plus;
		// restart other threads
        notifyAll();
		return (m_top);
	} 
	/**
	 *  Add an item to the stack
	 * @param Plus non-null Object
	 * @return current stack size
	 */
	public synchronized boolean add(Object Plus) {
	    push(Plus);
	    return(true);
	}

	/**
	 * Test whether an item is on the stack
	 * @param Plus non-null Object
	 * @return true if a member
	 */
	public boolean contains(Object Plus) {
		for (int i = 0; i < m_top; i++) {
			if (m_TheStack[i].equals(Plus)) {
				return (true); // found
			} 
		} 
		return (false); // not found
	} 

	/**
	 * return the top element
	 * @return non-null unless the stack is empty
	 */
	public Object top() {
		Object theret = null;

		if (!isEmpty()) {

			/* anything in the queue */
			theret = m_TheStack[m_top - 1];
		} 

		return (theret);
	} 

	/**
	 * remove and return the top element
	 * NOTE - this hangs is the stack is empty allowing
	 * other threads to restart
	 * @return non-null unless the stack is empty
	 * @throws OCThreadInterruptedException when interupted
	 *   this should kill the thread
	 */
	public synchronized Object pop() {
	    
	    // !! Here the current thread will 
		while(isEmpty()) {
		    try {
		        wait();
		    }
		    catch(InterruptedException ex) {
		        throw new ThreadInterruptedException();
		    }
		}
		/* pop the top */
		Object theret = theret = m_TheStack[--m_top];
		m_TheStack[m_top] = null;	 // clear out the stack
		return (theret);
	} 

	/**
	 * Internal method to add more space to the stack
	 * @return new size
	 */
	protected synchronized int extend() {
		int		 i;
		int		 TheSize = Math.max(2 * m_TheStack.length, DEFAULT_SIZE);
		Object[] Old = m_TheStack;

		m_TheStack = new Object[TheSize];

		for (i = 0; i < m_top; i++) {
			m_TheStack[i] = Old[i];
		} 

		return (TheSize);
	} 

     /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return non-null iterator over the elements contained in this collection.
     */
    public Iterator iterator()
    {
        return(Arrays.asList(toArray()).iterator());
    }
 
     /**
     * remove the object to the Queue using push
     * @param o - non-null object to add
     * @throws UnsupportedOperationException if the <tt>add</tt> method is not
     *		  supported by this collection.
     */
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
     /**
     * return an array representing the contants
     * @return non-null array
     */
    public synchronized Object[] toArray() {
        Object[] ret = new Object[size()];
        return(toArray(ret));
    }
     /**
     * populate an array with the contents in order to be popped
     * note - 
     * @param added non-null array
     * @return non-null array
     */
    public synchronized Object[] toArray(Object[] added) {
        for(int i = 0; i < m_top; i ++) {
            added[i] = m_TheStack[i];
        }
        return(added);
    }

    /**
    * Test code 
    * @param args non-null array - ignored
    */
    public static void main(String[] args) 
    {
        int MaxValues = 200;
        OStack TheStack = new OStack();
        for(long i = 0; i < MaxValues; i++) {
            String Test = "Item " + i;
            TheStack.push(Test);
            if(!TheStack.top().equals(Test))
                throw new IllegalStateException("Top is wrong");
        }
        for(long i = 0; i < MaxValues; i++) {
            String Expected = "Item " + (MaxValues - 1 - i);
            Object testPop = TheStack.pop();
            if(!testPop.equals(Expected))
                throw new IllegalStateException("Pop is wrong");
        }
        if(!TheStack.isEmpty())
            throw new IllegalStateException("Empty is wrong");
        System.out.println("Done");
    }
	// - *******************
	// - End Class OStack
}



/*--- formatting done in "Onvia Java Convention" style on 04-21-2000 ---*/


