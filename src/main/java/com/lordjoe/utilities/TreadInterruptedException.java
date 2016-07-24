/**{ file
    @name TreadInterruptedException.java
    @function Thrown when a thread is Interrupted to bounce out of the run method
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

import com.lordjoe.exceptions.*;

/**{ class
    @name TreadInterruptedException
    @function Thrown when a thread is Interrupted to bounce out of the run method
}*/ 
public class TreadInterruptedException extends WrapperException {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name TreadInterruptedException
        @function Constructor of TreadInterruptedException
    }*/ 
    public TreadInterruptedException() {
        super();
}

    /**{ constructor
        @name TreadInterruptedException
        @function Constructor of TreadInterruptedException
        @param s string to explain
    }*/ 
    public TreadInterruptedException(String s) {
        super(s);
    }


//- ******************* 
//- End Class TreadInterruptedException
}
