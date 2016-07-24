/**{ file
 @name Nulleton.java
 @function - classes derived from Nulleton will never be instantiated
 only static methods and members are allowed
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
 @name Nulleton
 @function <Add Comment Here>
 }*/
public abstract  class Nulleton {

    //- *******************
    //- Methods
    /**{ method
     @name neverImplementThis
     @function <Add Comment Here>
     @policy <Add Comment Here>
     }*/
	public abstract  void neverImplementThis();

    protected Nulleton() {
        throw new IllegalStateException("Never Construct This");
    }

//- *******************
//- End Class Nulleton
}
