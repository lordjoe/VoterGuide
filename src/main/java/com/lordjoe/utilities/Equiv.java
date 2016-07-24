package com.lordjoe.utilities;

import java.util.*;

/*
 * com.lordjoe.Utilities.Equiv
 * just one methof equivalent tests equality for all kinds of  date
 * use equivalent(i1,i2) will work even if 1 or both are null
 8 Objects
 * @author smlewis
 * Date: Jun 17, 2002
 */

public abstract class Equiv
{
    // DO not creater this
    private Equiv() {}

    public static boolean equivalent(int i1,int i2) {
        return(i1 == i2);
    }


    public static boolean equivalent(float i1,float i2) {
        return(i1 == i2);
    }

    public static boolean equivalent(double i1,double i2) {
        return(i1 == i2);
    }


    public static boolean equivalent(char i1,char i2) {
        return(i1 == i2);
    }


    public static boolean equivalent(boolean i1,boolean i2) {
        return(i1 == i2);
    }


    public static boolean equivalent(short i1,short i2) {
        return(i1 == i2);
    }


    public static boolean equivalent(long i1,long i2) {
        return(i1 == i2);
    }


    public static boolean equivalent(String i1,String i2) {
        if(i1 == null)
            return(i2 == null);
        else
            return(i1.equals(i2));
    }

    /**
     * Deep comparison
     * @param i1 possibly null list
     * @param i2  possibly null list
     * @return  true if i1 equivalent to i2
     */
    public static boolean equivalent(List i1,List i2) {
        if(i1 == null)
            return(i2 == null);
        if(i2 == null)
            return(false);
        if(i1.size() != i2.size())
            return(false);
        Object[] o1 = i1.toArray();
        Object[] o2 = i2.toArray();
        for (int i = 0; i < o1.length; i++) {
            if(!equivalent(o1[i],o2[i]))
                return(false);
        }
        return(true);
    }
     /**
     * Deep comparison
     * @param i1 possibly null list
     * @param i2  possibly null list
     * @return  true if i1 equivalent to i2
     */
    public static boolean equivalent(Map i1,Map i2) {
        if(i1 == null)
            return(i2 == null);
        if(i2 == null)
            return(false);
        if(i1.size() != i2.size())
            return(false);
        if(!equivalent(i1.keySet(),i2.keySet()))
            return(false);
        return(equivalent(i1.values(),i2.values()));
    }

    public static boolean equivalent(Object i1,Object i2) {
        if(i1 == null)
            return(i2 == null);
        else
            return(i1.equals(i2));
    }

}
