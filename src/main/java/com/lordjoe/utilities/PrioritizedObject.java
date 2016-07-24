/**{ file
    @name PrioritizedObject.java
    @function - associate an object with a priority - obvious use
         is to sort an array of these
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
    @name PrioritizedObject
    @function - associate an object with a priority - obvious use
         is to sort an array of these
}*/ 
public class PrioritizedObject implements Struct {

    //- ******************* 
    //- Fields 
    /**{ field
        @name data
        @function <Add Comment Here>
    }*/ 
    public Object data;

    /**{ field
        @name priority
        @function <Add Comment Here>
    }*/ 
    public int priority;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name PrioritizedObject
        @function Constructor of PrioritizedObject
    }*/ 
    public PrioritizedObject() {
}

    /**{ constructor
        @name PrioritizedObject
        @function Constructor of PrioritizedObject
        @param o <Add Comment Here>
    }*/ 
    public PrioritizedObject(Object o) {
        data = o;
    }

    /**{ constructor
        @name PrioritizedObject
        @function Constructor of PrioritizedObject
        @param o <Add Comment Here>
        @param i <Add Comment Here>
    }*/ 
    public PrioritizedObject(Object o,int i) {
        data = o;
        priority = i;
    }

    /**{ method
        @name reverseSortDescriptors
        @function <Add Comment Here>
        @param pds <Add Comment Here>
    }*/ 
    public static void reverseSortDescriptors(PrioritizedObject[] pds) {
        if(pds == null || pds.length < 2) {
            return;
        }
        sortDescriptors(pds);
        // sort
        PrioritizedObject[] rev = new PrioritizedObject[pds.length];
        // copy
        for(int i = 0; i < pds.length; i ++) 
            rev[i] = pds[i];
        // reverse
        int l = 0;
        for(int k = pds.length - 1; k >= 0; k --) 
            pds[k] = rev[l ++];
    }

    /**{ method
        @name sortDescriptors
        @function <Add Comment Here>
        @param pds <Add Comment Here>
    }*/ 
    public static void sortDescriptors(PrioritizedObject[] pds) {
        if(pds == null || pds.length < 2) {
            return;
        }
        int l,j,ir,i;
        PrioritizedObject rra;
        PrioritizedObject[] ra = new PrioritizedObject[pds.length + 1];
        for(int ix = 0; ix < pds.length; ix ++) 
            ra[ix + 1] = pds[ix];
        // now run the heap	sort
        l =(pds.length >> 1) + 1;
        ir = pds.length;
        // Look here
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
                if(j < ir &&(ra[j].priority < ra[j + 1].priority)) {
                    ++ j;
                }
                if(rra.priority < ra[j].priority) {
                    ra[i] = ra[j];
                    j +=(i = j);
                }
                else {
                    j = ir + 1;
                }
            }
            ra[i] = rra;
        }
        int NValid = 0;
        for(int ix = 0; ix < pds.length; ix ++) {
            pds[ix] = ra[ix + 1];
        }
    }


//- ******************* 
//- End Class PrioritizedObject
}
