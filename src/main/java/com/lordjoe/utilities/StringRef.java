/**{ file
    @name StringRef.java
    @function StringRef allows a function to return a String
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
    @name StringRef
    @function StringRef allows a function to return a String
}*/ 
public class StringRef implements Struct {

    //- ******************* 
    //- Fields 
    /**{ field
        @name value
        @function real data
    }*/ 
    public String value;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name StringRef
        @function Constructor of StringRef
        @param d value - can be null
    }*/ 
    public StringRef(String d) {
        value = d;
}

    /**{ constructor
        @name StringRef
        @function Constructor of StringRef
    }*/ 
    public StringRef() {
    }


//- ******************* 
//- End Class StringRef
}
