/**{ file
    @name ConnectionLostException.java
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
    @name ConnectionLostException
    @function Exception thrown when the user cancels an operation.
}*/ 
public class ConnectionLostException extends UpdaterException {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name ConnectionLostException
        @function Constructor of ConnectionLostException
    }*/ 
    public ConnectionLostException() {
}

    /**{ constructor
        @name ConnectionLostException
        @function Constructor of ConnectionLostException
        @param message message to add to exception
    }*/ 
    public ConnectionLostException(String message) {
        super(message);
    }


//- ******************* 
//- End Class ConnectionLostException
}
