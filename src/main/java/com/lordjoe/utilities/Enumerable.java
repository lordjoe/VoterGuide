/*
 * Enumerable.java
 *
 * Created on August 10, 2000, 11:33 AM
 */

package com.lordjoe.utilities;
import java.util.Iterator;
/**
 * Interface implemented by any object acting as an enumeration
 * @author  SteveLewis
 * @version 
 */
public interface Enumerable {
    /** 
    * return the number of elements
    * @return non-negative count
    */
    public int getOptionCount();
    
    /** 
    * return Iterator over all options
    * @return non-null Iterator
    */
    public Iterator getOptions();
    
    /** 
    * return index of this item - indices go from 0 to getOptionCount() - 1
    * and are guaranteed to be filled
    * @return non-negative index
    */
    public int getIndex();
    
}
