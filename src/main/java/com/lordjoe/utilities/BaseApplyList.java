/**{ file
    @name BaseApplyList.java
    @function BaseApplyList is a
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
    @name BaseApplyList
    @function this class allows a double to be passed as a reference
    @see com.lordjoe.IntegerRef
    @see com.lordjoe.BooleanRef
    @see com.lordjoe.StringRef
}*/
public abstract class BaseApplyList implements ApplyList
{

    public BaseApplyList() {}

    // Implement these
    public abstract ApplyList makeNew(Object o,ApplyList next);
    public abstract Object getDataObject();
    public abstract ApplyList getNext();


    //
    // run down the list to the terminator
    // used to get the proper class of terminator
    public ApplyList getTerminator() {
        ApplyList ret = this;
        while(!ret.isEmpty())
            ret = ret.getNext();
        return(ret);
    }

    //
    // Mark this as not a terminator
    public boolean isEmpty() { return(false); }



    public ApplyList makeList(Object[] in) {
        ApplyList start = getTerminator();
        for(int i = in.length - 1; i >= 0; i--)
            start = start.makeNew(in[i],start);
        return(start);
    }

    public Object[] getObjects() {
        Object[] ret = new Object[getSize()];
        populate(ret);
        return(ret);
    }

    public int getSize() {
        return(1 + getNext().getSize());
    }

    public void  populate(Object[] data) {
        populate(data,0);
    }

    public void  populate(Object[] data,int index) {
        data[index++] = getDataObject();
        getNext().populate(data,index);
    }


    public  void   validateDataObject(Object o) {}

    public boolean member(Object test) {
        if(test.equals(getDataObject()))
            return(true);
        return(getNext().member(test));
    }



    public ApplyList add(Object o) {
        return(makeNew(o,this));
    }

    public ApplyList remove(Object target) {
        if(target.equals(getDataObject()))
            return(getNext());
        ApplyList newNext = getNext().remove(target);
        if(newNext == getNext())
            return(this); // not a member;
        return(makeNew(target,newNext));
    }

    //
    // Object used to terminate the list
    // needs to implement ApplyList makeNew(Object o,ApplyList next);
    // not done here because we do not know what subclass to return
    public static abstract  class BaseNullApplyList implements ApplyList {
        // Implement these
        public abstract ApplyList makeNew(Object o,ApplyList next);
        protected BaseNullApplyList() { }
        public boolean isEmpty() { return(true); }
        public Object getDataObject() { return(null); }
        public ApplyList getNext() { return(null); }
        public boolean member(Object test) { return(false); }
        public ApplyList remove(Object target) { return(this); }
        public int getSize() { return(0); }
        public void  populate(Object[] data,int index) {}
        public  void   validateDataObject(Object o) {}
        public ApplyList add(Object o) {
            return(makeNew(o,this));
        }
        public Object[] getObjects() { return(new Object[0]); }
        public void  populate(Object[] data) {}
        public ApplyList getTerminator() { return(this); }
        public ApplyList makeList(Object[] in) {
            ApplyList start = getTerminator();
            for(int i = in.length - 1; i >= 0; i--)
                start = start.makeNew(in[i],start);
            return(start);
        }
        
    //- End inner Class BaseNullApplyList
    }


//- *******************
//- End Class BaseApplyList
}



