package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.Utilities.PropertyDumper
 * Sort an array of objects based on a property values
 * Works for String, int and double
 * @author Steve Lewis
 */
public class PropertyComparator implements Comparator
{
   private final String m_PropName;
   public PropertyComparator(String propName) {
       m_PropName = propName;
   }
   public int compare(Object o1,Object o2)
    {
         Object prop1 = ClassAnalyzer.getProperty(o1,m_PropName);
         Object prop2 = ClassAnalyzer.getProperty(o2,m_PropName);
         if(prop1 == null) {
             return(prop2 == null ? 0 : 1);
         }
        if(prop1 instanceof Integer)
             return(((Integer)prop1).compareTo((Integer)prop2));
        if(prop1 instanceof String)
              return(((String)prop1).compareTo((String)prop2));
        if(prop1 instanceof Double)
              return(((Double)prop1).compareTo((Double)prop2));
        if(prop1.equals(prop2))
            return(0);
        if(System.identityHashCode(prop1) <  System.identityHashCode(prop2) )
            return(-1);
        return(1);
     }
}
