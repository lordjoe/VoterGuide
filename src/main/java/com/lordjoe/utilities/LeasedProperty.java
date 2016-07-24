/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

// package com.lordjoe.lib; // original
package com.lordjoe.utilities; // changed, PKJ

// import com.lordjoe.lib.exceptions.*;
import java.io.*;
import java.util.*;

/**
 * Abstract class for leased object -
 * This is an object which after the lease interval must be
 * reloaded
 * Use this when:
 * Objects are accessed much more often than they change
 * The consequenses of an update not occurring immediately are
 * tolerable
 * Loading every time the object is accessed may impact performance
 * 
 * @author Steven Lewis
 * @copyright
 * ***********************
 * Copyright (c) 1999,2000
 * OnVia Inc.
 * www.onvia.com
 * ***********************
 * @version 1.0
 */
public abstract class LeasedProperty
{
	private Object						 m_LeasedItem;
	private long						 m_ExpirationTime;
	private long						 m_LeaseInterval;

	// - *******************
	// - Constants (static final)

	/**
	 * Array of 0 length
	 */
	public final static LeasedProperty[] NULL_ARRAY = new LeasedProperty[0];

	// - *******************
	// - Methods

	/**
	 * return the affected object
	 * @return normally num-null object - null return indicated failure to load
	 * @see loadLeasedObject
	 */
	public Object getLeasedObject() {
		boolean doingReload = false;

		if (m_LeasedItem == null) {
			doingReload = true;
			m_LeasedItem = loadLeasedObject();
		} 

		if (isLeaseExpired()) {
			m_LeasedItem = loadLeasedObject();
			doingReload = true;
		} 

		// Debugging code
		System.out.println("Reloading = " + doingReload);

		return (m_LeasedItem);
	} 

	/**
	 * return the affected object
	 * @return normally num-null object - null return indicated failure to load
	 * @see loadLeasedObject
	 */
	public void setLeasedObject(Object newObject) {
		m_LeasedItem = newObject;

		if (getLeaseInterval() >= 0) {
			setExpirationTime(System.currentTimeMillis() 
							  + getLeaseInterval());
		} 
		else {
			setExpirationTime(Long.MAX_VALUE);	  // never expire
		}
	} 

	/**
	 * return the current leased object - this is used only to
	 * support retries when a resource is temporarily unavailable
	 * @return normally num-null object - null return indicated failure to load
	 * @see getLeasedObject
	 */
	protected Object getRawLeasedObject() {
		return (m_LeasedItem);
	} 

	/**
	 * go to data source and reload the object - this should
	 * reset the lease by adding getLeaseInterval to current time
	 * @return normally num-null object - null return indicated failure to load
	 * @see loadLeasedObject
	 */
	public abstract Object loadLeasedObject();

	/**
	 * test to see if the object needs to reload
	 * @return - true if the lease is expired
	 * @see setLeaseInteraval
	 */
	public boolean isLeaseExpired() {
		return (System.currentTimeMillis() > getExpirationTime());
	} 

	/**
	 * get the interval for the lease to be valid
	 * note interval of 0 say always load
	 * interval of -1 says never reload - infinite lease
	 * @return - interval for lease in millisec
	 * @see setLeaseInteraval
	 */
	public long getLeaseInterval() {
		return (m_LeaseInterval);
	} 

	/**
	 * set the interval for the lease to be valid
	 * note interval of 0 say always load
	 * interval of -1 says never reload - infinite lease
	 * @param newInterval - interval for lease in millisec
	 * @see getLeaseInteraval
	 */
	public synchronized void setLeaseInterval(long newInterval) {
		if (m_LeaseInterval == newInterval) {
			return;
		} 

		m_LeaseInterval = newInterval;

		// reset expiration time
		if (m_LeaseInterval > 0) {
			setExpirationTime(System.currentTimeMillis() + m_LeaseInterval);
		} 
		else {
			if (m_LeaseInterval == 0) {
				setExpirationTime(0);
			} 
			else {
				setExpirationTime(Long.MAX_VALUE);	  // never expire
			}
		} 
	} 

	/**
	 * get time the lease expires
	 * @return expiration time - compare to System.currentTimeMillis()
	 * @see getExpirationTime
	 */
	protected long getExpirationTime() {
		return (m_ExpirationTime);
	} 

	/**
	 * set time the lease expires
	 * @param newInterval - new expiration time
	 * @see getExpirationTime
	 */
	protected void setExpirationTime(long newInterval) {
		m_ExpirationTime = newInterval;
	} 

	// - *******************
	// - End Class OIComposite
}



/*--- formatting done in "Onvia Java Convention" style on 05-19-2000 ---*/


