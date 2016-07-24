package com.lordjoe.utilities;

import java.io.*;
import java.util.*;

/**
 * This is a facade for a read-only Properties object identified with a File
 * Calls to get and getProperty periodically test to see if the file is 
 * updated and will read new data. Because there might be errors in reading
 * if the file is in the process of being updated when the read occurs, there 
 * is a grace period on errors to allow on-going transfers to complete
 * This approach is superior to calling load on every access which can seriously
 * impact performance
 * 
 * @author Steven Lewis
 * @copyright
 * ***********************
 * Copyright (c) 2000
 * Lordjoe Inc.
 * www.lordjoe.com
 * ***********************
 * @version 1.0
 */
public class LeasedProperties
{
	// - *******************
	// - Constants (static final)
	public static final long			PATIENCE_INTERVAL = 30000; // give up after 30 Sec
	
	// - *******************
	// - member variables
	private Properties					m_LeasedItem;     // actual properties
	private File						m_SourceFile;     // FIle representing the location
	                                                    // All times are in currentTimeMillis
	private long						m_ExpirationTime; // when the  lease expires
	private long						m_LeaseInterval;  // length of the lease
	private long						m_SourceFileDate; // date of last read source
	private long						m_WaitingForSource; // when a source read failed


	/**
	 * Default Constructor
	 */
	public LeasedProperties() 
	{
		m_LeasedItem = new Properties();
	}
	
	/**
	 * Constructor 
	 * @param Source existing readable file name
	 * @see
	 */
	public LeasedProperties(String Source,long leaseInterval) {
		this(new File(Source),leaseInterval);
	}
	/**
	 * Constructor 
	 * @param Source existing readable file
	 * throws IllegalArgumentException if the Files does not exist or cannot be read
	 */
	public LeasedProperties(File Source,long leaseInterval) {
		this();
		// Validation
        if(!Source.exists() || !Source.isFile() || !Source.canRead() )
            throw new IllegalArgumentException("Cannot access source file " + 
                Source.getPath());
                
		setSourceFile(Source);
		setLeaseInterval(leaseInterval);
	}
	// - *******************
	// - Methods
	
	/* 
	* The following methods are a facade to the contained properties
	* object - Since Properties in not an interface we do not implement
	* an interface
	*/
	
    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * <code>null</code> if the property is not found.
     *
     * @param   key   the property key.
     * @return  the value in this property list with the specified key value.
     * @see     java.util.Properties#defaults
     */
    public String getProperty(String key) {
        return(getLeasedProperties().getProperty(key));
    }

    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns the
     * default value argument if the property is not found.
     *
     * @param   key            the hashtable key.
     * @param   defaultValue   a default value.
     *
     * @return  the value in this property list with the specified key value.
     * @see     java.util.Properties#defaults
     */
    public String getProperty(String key, String defaultValue) {
        return(getLeasedProperties().getProperty(key,defaultValue));
    }
    
    /**
     * Returns the value to which this map maps the specified key.  Returns
     * <tt>null</tt> if the map contains no mapping for this key.  A return
     * value of <tt>null</tt> does not <i>necessarily</i> indicate that the
     * map contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to <tt>null</tt>.  The <tt>containsKey</tt>
     * operation may be used to distinguish these two cases.
     *
     * @param key key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or
     *	       <tt>null</tt> if the map contains no mapping for this key.
     * 
     * @throws ClassCastException if the key is of an inappropriate type for
     * 		  this map.
     * @throws NullPointerException key is <tt>null</tt> and this map does not
     *		  not permit <tt>null</tt> keys.
     * 
     * @see #containsKey(Object)
     */
    public Object get(Object key)
    {
        return(getLeasedProperties().get(key));
    }
    

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of key-value mappings in this map.
     */
    public int size()
    {
        return(getLeasedProperties().size());
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings.
     */
    public boolean isEmpty()
    {
        return(getLeasedProperties().isEmpty());
    }
    
    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.
     *
     * @param key key whose presence in this map is to be tested.
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key.
     * 
     * @throws ClassCastException if the key is of an inappropriate type for
     * 		  this map.
     * @throws NullPointerException if the key is <tt>null</tt> and this map
     *            does not not permit <tt>null</tt> keys.
     */
    public boolean containsKey(Object key)
    {
        return(getLeasedProperties().containsKey(key));
    }
    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.  More formally, returns <tt>true</tt> if and only if
     * this map contains at least one mapping to a value <tt>v</tt> such that
     * <tt>(value==null ? v==null : value.equals(v))</tt>.  This operation
     * will probably require time linear in the map size for most
     * implementations of the <tt>Map</tt> interface.
     *
     * @param value value whose presence in this map is to be tested.
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value.
     */
    public boolean containsValue(Object value)
    {
        return(getLeasedProperties().containsValue(value));
    }

    /**
     * Returns a set view of the keys contained in this map.  The set is
     * backed by the map, so changes to the map are reflected in the set, and
     * vice-versa.  If the map is modified while an iteration over the set is
     * in progress, the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding mapping from
     * the map, via the <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt> <tt>retainAll</tt>, and <tt>clear</tt> operations.
     * It does not support the add or <tt>addAll</tt> operations.
     *
     * @return a set view of the keys contained in this map.
     */
    public Set keySet()
    {
        return(getLeasedProperties().keySet());
    }

    /**
     * Returns a collection view of the values contained in this map.  The
     * collection is backed by the map, so changes to the map are reflected in
     * the collection, and vice-versa.  If the map is modified while an
     * iteration over the collection is in progress, the results of the
     * iteration are undefined.  The collection supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt> and <tt>clear</tt> operations.
     * It does not support the add or <tt>addAll</tt> operations.
     *
     * @return a collection view of the values contained in this map.
     */
    public Collection values()
    {
        return(getLeasedProperties().values());
    }
    
    // ===============================
    // Implementing code
    
	/**
	 * return the affected Properties
	 * @return non-null Properties
	 * @see loadLeasedProperties
	 * @throws LeasePropertyException when the properties is inaccessable 
	 * for several lease periods
	 */
	protected synchronized Properties getLeasedProperties() {
		if (isLeaseExpired()) {
		    renewLease();
		} 

		return (m_LeasedItem);
	} 

	/**
	 * set the leased properties
	 * @param newObject   non-null Properties 
	 * @see loadLeasedProperties
	 */
	protected synchronized void setLeasedProperties(Properties newObject) {
		m_LeasedItem = newObject;
        extendLease();
    }
    
    /**
    * extend the lease by getLeaseInterval - of the interval is 
    * <= 0 extend forever
    */
    protected void extendLease()
    {
        // When trying to fix errors allow a shorter lease interval
        if(getWaitingForSource() > 0) {
            setExpirationTime(System.currentTimeMillis() + PATIENCE_INTERVAL);
			return;
        }
        // Otherwise lease intervals > 0 renew for getLeaseInterval()
		if (getLeaseInterval() >= 0) {
			setExpirationTime(System.currentTimeMillis() 
							  + getLeaseInterval());
		} 
		else { // lease intervals < 0 never expire
			setExpirationTime(Long.MAX_VALUE);	  // never expire
		}
	} 

    
	/**
	 * Something has gone wrong so we wait for a solution in th emean time return
	 * the last good data
	 * @return normally num-null properties representing the last good read
	 * @see loadLeasedProperties()
	 * @throws LeasePropertyException after PATIENCE_INTERVAL
	 */
	protected Properties currentStateWhileWaiting() 
	{
		// wait for a file to become available
		// assume this is not long
		if (getWaitingForSource() > 0) {
			if (System.currentTimeMillis() > getWaitingForSource()) {
				throw new LeasePropertyException("Cannot get required File");
			} 
		}
		else {
			// allow PATIENCE_INTERVAL to fix the problem
			setWaitingForSource(System.currentTimeMillis() + PATIENCE_INTERVAL);
		}

		return (m_LeasedItem);	   // return the current state
	}


	/**
	 * test to see if the object needs to reload
	 * @return - true if the lease is expired
	 * @see setLeaseInteraval
	 */
	protected boolean isLeaseExpired() {
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
	protected synchronized void setLeaseInterval(long newInterval) {
		if (m_LeaseInterval == newInterval) {
			return;
		} 

		m_LeaseInterval = newInterval;

		// reset expiration time
		if (m_LeaseInterval > 0) {
			setExpirationTime(System.currentTimeMillis() + m_LeaseInterval);
		} 
		else { // any negative lease means never expire
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
	
	/**
	* here we test to make sure that the file representing our properties has not
	* changed since the last call if so the lease ins renewed for another interval
	*/
	protected synchronized void renewLease()
	{
		File Source = getSourceFile();
        if(Source.exists() && getSourceFileDate() == Source.lastModified()) {
            extendLease(); // no need to test for a while
            return;
        }
		// now we actually need to try to read
		setLeasedProperties(loadLeasedProperties());
	    
	}
	/**
	 * go to data source and reload the object - this should
	 * reset the lease by adding getLeaseInterval to current time
	 * @return normally non-null object - null return indicated failure to load
	 * @see loadLeasedProperties()
	 */
	protected Properties loadLeasedProperties() {
		Properties updated = new Properties();

		try {
			File Source = getSourceFile();
            // problems dealing with the source???
			if (!Source.exists() ||!Source.canRead()) {
			    return(currentStateWhileWaiting());
			} 

			FileInputStream in = new FileInputStream(Source);
			updated.load(in);
            in.close();
            setWaitingForSource(0); // mark a good load
		} 
		catch (IOException ex) {
			return(currentStateWhileWaiting());
		} 
		setSourceFileDate(getSourceFile().lastModified());
		return (updated);
	} 


	/**
	 * get the file we use as a source
	 * @return source file - should not be null
	 */
	protected File getSourceFile() {
		return (m_SourceFile);
	} 

	/**
	 * set time the lease expires
	 * @param newFile - SourceFile - should not be null
	 * @see getExpirationTime
	 */
	protected void setSourceFile(File newFile) {
		m_SourceFile = newFile;
		setLeasedProperties(loadLeasedProperties());
	} 

	/**
	 * set the date for the source file
	 * @param newTime - file date
	 */
	protected void setSourceFileDate(long newTime) {
		m_SourceFileDate = newTime;
	} 
	
	/**
	 * get the date for the source file
	 * @return date file last read
	 */
	protected long getSourceFileDate() {
		return(m_SourceFileDate);
	} 

	/**
	 * get time the lease expires
	 * @return expiration time - compare to System.currentTimeMillis()
	 * @see getExpirationTime
	 */
	protected long getWaitingForSource() {
		return (m_WaitingForSource);
	} 

	/**
	 * set time the lease expires
	 * @param newFile - WaitingForSource
	 * @see getExpirationTime
	 */
	protected void setWaitingForSource(long newFile) {
		m_WaitingForSource = newFile;
	} 

    /**
    * this is thrown when we have made several tries and still 
    * cannot load the leased properties
    * It should never happen
    */
    public class LeasePropertyException extends IllegalStateException
    {
        public LeasePropertyException() { super(); }
        public LeasePropertyException(String s) { super(s); }
	// - End Inner Class LeasePropertyException
    }

	// - *******************
	// - End Class LeasedProperties
}


