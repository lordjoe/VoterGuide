/**{ file
    @name UserCanceledException.java
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
public class UserCanceledException extends UpdaterException {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name UserCanceledException
        @function Constructor of UserCanceledException
    }*/ 
    public UserCanceledException() {
}

    /**{ constructor
        @name UserCanceledException
        @function Constructor of UserCanceledException
        @param message message to add to exception
    }*/ 
    public UserCanceledException(String message) {
        super(message);
    }


//- ******************* 
//- End Class UserCanceledException
}
