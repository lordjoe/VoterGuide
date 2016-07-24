/**{ file
    @name DoubleRef.java
    @function DoubleRef allows a function to return a double
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
    @name DoubleRef
    @function this class allows a double to be passed as a reference
    @see com.lordjoe.IntegerRef
    @see com.lordjoe.BooleanRef
    @see com.lordjoe.StringRef
}*/ 
public class DoubleRef implements Struct {

    //- ******************* 
    //- Fields 
    /**{ field
        @name value
        @function real data
    }*/ 
    public double value;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name DoubleRef
        @function Constructor of DoubleRef
        @param d initial value
    }*/ 
    public DoubleRef(double d) {
        value = d;
}

    /**{ constructor
        @name DoubleRef
        @function Constructor of DoubleRef
    }*/ 
    public DoubleRef() {
    }


//- ******************* 
//- End Class DoubleRef
}
