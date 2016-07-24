/**{ file
 @name AssertionFailureException.java
 @function This is thrown by Assertion when an assertion failure occurs
 i.e. Assertion.fatalError or Assertion.validate
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
 @name AssertionFailureException
 @function This is thrown by Assertion when an assertion failure occurs
 i.e. Assertion.fatalError or Assertion.validate
 }*/
public class AssertionFailureException extends WrapperException {

    //- *******************
    //- Methods
    /**{ constructor
     @name AssertionFailureException
     @function Constructor of AssertionFailureException
     }*/
    public AssertionFailureException() {
        super();
    }

    /**{ constructor
     @name AssertionFailureException
     @function Constructor of AssertionFailureException
     @param s string to explain
     }*/
    public AssertionFailureException(String s) {
        super(s);
    }


//- *******************
//- End Class AssertionFailureException
}
