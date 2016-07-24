/**{ file
    @name KeyValue.java
    @function KeyValue passes 2 Strings - key and Value as a single
         object - useful for accummulating data for hash tables or databases
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
/**{ class
    @name KeyValue
    @function KeyValue passes 2 Strings - key and Value as a single
         object - useful for accummulating data for hash tables or databases
}*/ 
public class KeyValue implements Struct, Comparable {

    //- ******************* 
    //- Fields 
    /**{ field
        @name key
        @function the key
    }*/ 
    public String key;

    /**{ field
        @name value
        @function the value
    }*/ 
    public String value;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name KeyValue
        @function Constructor of KeyValue
        @param k the key
        @param v the value
    }*/ 
    public KeyValue(String k,String v) {
        key = k;
        value = v;
}

    /**{ constructor
        @name KeyValue
        @function Constructor of KeyValue
    }*/ 
    public KeyValue() {
    }

    /**{ method
        @name sort
        @function sorts in order of keys
        @param c an array to sort in place
    }*/ 
    static public void sort(KeyValue[] c) {
        sortByKey(c);
    }
    
    public int compareTo(Object test) {
        KeyValue t = (KeyValue)test;
        int ret = Util.caselessCompare(key,t.key);
        if(ret == 0)
            ret = Util.caselessCompare(value,t.value);
        return(ret);
    }

    /**{ method
        @name sortByKey
        @function sorts in order of keys - using heapsort
        @param s an array to sort in place
    }*/ 
    static public void sortByKey(KeyValue[] s) {
        if(s == null || s.length < 2) {
            return;
        }
        Util.ascendingSort((java.lang.Comparable[])s);
    }

//
// Uses heapsort

    /**{ method
        @name sortByValue
        @function sorts in order of values - using heapsort
        @param s an array to sort in place
    }*/ 
    static public void sortByValue(KeyValue[] s) {
        if(s == null || s.length < 2) {
            return;
        }
        int l,j,ir,i;
        KeyValue rra;
        KeyValue[] ra = new KeyValue[s.length + 1];
        for(int ix = 0; ix < s.length; ix ++) 
            ra[ix + 1] = s[ix];
        // now run the heap sort
        l =(s.length >> 1) + 1;
        ir = s.length;
        for(;;) {
            if(l > 1) {
                rra = ra[-- l];
            }
            else {
                rra = ra[ir];
                ra[ir] = ra[1];
                if(-- ir == 1) {
                    ra[1] = rra;
                    break;
                }
            }
            i = l;
            j = l << 1;
            while(j <= ir) {
                if(j < ir && Util.caselessCompare(ra[j].value,ra[j + 1].value) < 0) {
                    ++ j;
                }
                if(Util.caselessCompare(rra.value,ra[j].value) < 0) {
                    ra[i] = ra[j];
                    j +=(i = j);
                }
                else {
                    j = ir + 1;
                }
            }
            ra[i] = rra;
        }
        for(int ix = 0; ix < s.length; ix ++) 
            s[ix] = ra[ix + 1];
    }


//- ******************* 
//- End Class KeyValue
}
