/**{ file
    @name UserRestartException.java
    @function - Exception thrown when the user requests a restart
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
    @name UserRestartException
    @function Exception thrown when the user cancels an operation.
}*/ 
public class UserRestartException extends UpdaterException {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name UserRestartException
        @function Constructor of UserRestartException
    }*/ 
    public UserRestartException() {
}

    /**{ constructor
        @name UserRestartException
        @function Constructor of UserRestartException
        @param message message to add to exception
    }*/ 
    public UserRestartException(String message) {
        super(message);
    }


//- ******************* 
//- End Class UserRestartException
}
