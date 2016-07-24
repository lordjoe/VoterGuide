/**{ file
    @name ApplyList.java
    @function ApplyList is a
    @author> Steven Lewis
    @copyright>
	************************
	*  Copyright (c) 98,99
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************
    @date> Mon Jun 22 21:48:24 PDT 1998
    @version> 1.0
}*/
package com.lordjoe.utilities;
/**{ class
    @name ApplyList
    @function interface implemented by an ApplyList - this 
    can be a seen as an immutable collection API where any change in the 
    collection returns a new collection
}*/
public interface ApplyList
{
    /**
        create a new instance of the collection with 
        o added. 
        @param o - object to add
        @param next 
        @return - the new collection
    */
    ApplyList makeNew(Object o,ApplyList next);
    /**
        return true if the collection is empty
        @return as described above
    */
    boolean isEmpty();
    
    /**
        get the next element in the list or null if there are no further 
        elements
        @return as described above
    */
    ApplyList getNext();
    
    /**
        test an object for validity. This may throw a RuntimeException if 
        @param - the added object
    */
    void   validateDataObject(Object o);
    Object getDataObject();
    
    boolean member(Object test);
    ApplyList add(Object o);
    ApplyList remove(Object target);

    ApplyList makeList(Object[] in);
    Object[] getObjects();
    int getSize();
    void  populate(Object[] data);
    void  populate(Object[] data,int index);
//- *******************
//- End interface ApplyList
}


    
