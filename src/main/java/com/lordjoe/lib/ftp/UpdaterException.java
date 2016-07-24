/**{ file
    @name UpdaterException.java
    @function - Exception thrown when the user cancels an operation.
    @author> Steven M. Lewis
    @copyright> 
	************************
	*  Copyright (c) 1999
	*  Steven M. Lewis
	************************
}*/ 
package com.lordjoe.lib.ftp;
import com.lordjoe.utilities.*;
import java.util.*;
import java.io.*;
/**{ class
    @name UserCanceledException
    @function Exception thrown when the user cancels an operation.
}*/ 
public abstract class UpdaterException extends RuntimeException {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name UserCanceledException
        @function Constructor of UserCanceledException
    }*/ 
    public UpdaterException() {
        Assertion.doNada(); // break to track exceptions
    }

    /**{ constructor
        @name UserCanceledException
        @function Constructor of UserCanceledException
        @param message message to add to exception
    }*/ 
    public UpdaterException(String message) {
        super(message);
        Assertion.doNada(); // break to track exceptions
    }


//- ******************* 
//- End Class UserCanceledException
}
