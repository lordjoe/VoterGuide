package com.lordjoe.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * com.lordjoe.utilities.CollectionUtilities
 * User: Steve
 * Date: 7/1/2016
 */
public class CollectionUtilities {

    public static <T  extends Comparable<? super T> > List<T> sortedCollection(Collection<T> inp)    {
        ArrayList<T>  ret = new ArrayList<T>(inp);
        Collections.sort(ret);
        return ret;
    }

}
