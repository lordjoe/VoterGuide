/**{ file
    @name EnumException.java
    @function This is thrown by EnumInt and EnumString when
         an illegal value is set.
    @author> Steven M. Lewis
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
    @name EnumException
    @function This is thrown by EnumInt and EnumString when
         an illegal value is set.
}*/ 
public class EnumException extends RuntimeException {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name EnumException
        @function Constructor of EnumException
    }*/ 
    public EnumException() {
        super();
}

    /**{ constructor
        @name EnumException
        @function Constructor of EnumException
        @param s string to explain
    }*/ 
    public EnumException(String s) {
        super(s);
    }


//- ******************* 
//- End Class EnumException
}
