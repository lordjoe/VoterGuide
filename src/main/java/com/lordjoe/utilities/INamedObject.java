package com.lordjoe.utilities;

import java.util.*;

/**
 * @author slewis
 * an object with a name
 */
public interface INamedObject
{
    public static final Comparator COMPARATOR
        = new NameComparator();
    /**
     * return the object's name - frequently this is
     * final
     * @return unsually non-null name
     */
    public String getName();

    public static class NameComparator implements Comparator
     {
        private NameComparator() {} // make this a singleton
         public int compare(Object o1, Object o2)
         {
             String n1 = ((com.lordjoe.utilities.INamedObject)o1).getName();
             String n2 = ((com.lordjoe.utilities.INamedObject)o2).getName();
             if(n1 == null) {
                 return(n2 == null ? 0 : 1) ;
             }
             return(n1.compareTo(n2));
          }
     }
 }
