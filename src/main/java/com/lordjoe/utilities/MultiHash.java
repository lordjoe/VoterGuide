/**{ file
    @name MultiHash.java
    @function MultiHash is like a hashtsable but one which
        supports multiple matches. get returns an Object array
        and put will add to an existing array. There is a special
        remove taking both key and item which removes the item but
        keeps the key
    @author> Steven Lewis
    @copyright>
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************
    @date> Mon Jun 22 21:48:24 PDT 1998
    @version> 1.0
}*/
package com.lordjoe.utilities;
import java.util.Hashtable;
import java.util.Enumeration;
/**{ class
    @name MultiHash
    @function MultiHash is like a hashtsable but one which
        supports multiple matches. get returns an Object array
        and put will add to an existing array. There is a special
        remove taking both key and item which removes the item but
        keeps the key
    @author> Steven Lewis
}*/
public class MultiHash
{
    private Hashtable m_entries;
    // Implement these
    public MultiHash() {
        m_entries = new Hashtable();
    }
     public  void clear() {
        m_entries.clear();
     }

     public  boolean contains(Object value) {
        Enumeration e = elements();
        while(e.hasMoreElements()) {
            Object[] test = (Object[])e.nextElement();
            // Find the item
            for(int i = 0; i < test.length; i++) {
                if(test[i].equals(value)) {
                    return(true); //  present
                }
            }
        }
        return(false); // not found
     }

     public  boolean containsKey(Object key) {
        return(m_entries.containsKey(key));
     }

     public boolean isEmpty() {
        return(m_entries.isEmpty());
     }

     public  Enumeration elements() {
        return(m_entries.elements());
     }

     public  int size() {
        return(m_entries.size());
     }


    public Object[] get(Object id) {
        return((Object[])m_entries.get(id));
    }

     public void put(Object id,Object item) {
        Object[] old =  get(id);
        if(old == null) { // no entry
            Object[] added = new Object[1];
            added[0] = item;
            m_entries.put(id,added);
        }
        else {
            // Find the item
            for(int i = 0; i < old.length; i++) {
                if(old[i].equals(item)) {
                    return; // already present
                }
            }
            // add as last item in the list
            Object[] added = new Object[old.length + 1];
            System.arraycopy(old,0,added,0,old.length);
            added[old.length] = item;
            m_entries.put(id,added);
        }
     }

     public void remove(Object key) {
        m_entries.remove(key);
     }

     public void remove(Object id,Object item) {
        Object[] old =  get(id);
        if(old == null) {
            return; // not there
        }
        else {
            // if only one item we remove the entry
            if(old.length == 1)  {
                if(old[0].equals(item))
                    remove(id);
                return;
            }
            int index = -1;
            // Find the item
            for(int i = 0; i < old.length; i++) {
                if(old[i].equals(item)) {
                    index = i;
                    break;
                }
            }
            if(index == -1) return; // not found
            // make an array withou the item
            Object[] added = new Object[old.length - 1];
            int k = 0;
            for(int i = 0; i < old.length; i++) {
                if(i != index)
                    added[k++] = old[i];
            }
            m_entries.put(id,added);
        }
    }
//- *******************
//- End interface MultiHash
}



