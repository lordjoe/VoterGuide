/**{ file
    @name BooleanRef.java
    @function BooleanRef allows a function to return a boolean
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
    @name BooleanRef
    @function this class allows a boolean to be passed as a reference
    @see Overlord.IntegerRef
    @see Overlord.DoubleRef
    @see Overlord.StringRef
}*/ 
public class BooleanRef {

    //- ******************* 
    //- Fields 
    /**{ field
        @name value
        @function - real data
    }*/ 
    public boolean value;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name BooleanRef
        @function Constructor of BooleanRef
        @param d initial value
    }*/ 
    public BooleanRef(boolean d) {
        value = d;
}

    /**{ constructor
        @name BooleanRef
        @function Constructor of BooleanRef
    }*/ 
    public BooleanRef() {
    }


//- ******************* 
//- End Class BooleanRef
}
