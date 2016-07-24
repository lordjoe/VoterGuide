/**{ file
    @name TransferFailureException.java
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
    @name com.lordjoe.lib.ftp.TransferFailureException
    @function Exception thrown when the user cancels an operation.
}*/ 
public class TransferFailureException extends UpdaterException {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name TransferFailureException
        @function Constructor of TransferFailureException
    }*/ 
    public TransferFailureException() {
}

    /**{ constructor
        @name TransferFailureException
        @function Constructor of TransferFailureException
        @param message message to add to exception
    }*/ 
    public TransferFailureException(String message) {
        super(message);
    }


//- ******************* 
//- End Class TransferFailureException
}
