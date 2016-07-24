/**{ file
    @name NullEnumeration.java
    @function - An enumeration suitable for a null or empty container
         note there is only one instance of this class - call NullEnumeration.getInstance()
         to return the instance -
    @author> Steven M. Lewis
    @copyright> 
 	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************

    @date> Mon Jun 22 21:48:27 PDT 1998
    @version> 1.0
}*/ 
package com.lordjoe.utilities;
import java.util.Enumeration;
/**{ class
    @name NullEnumeration
    @function <Add Comment Here>
}*/ 
public final class NullEnumeration implements Enumeration {

    //- ******************* 
    //- Fields 
    /**{ field
        @name gOneInstance
        @function <Add Comment Here>
    }*/ 
    private static final NullEnumeration gOneInstance = new NullEnumeration();


    //- ******************* 
    //- Methods 
    /**{ method
        @name getInstance
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public final static NullEnumeration getInstance() {
        return(gOneInstance);
    }

    /**{ constructor
        @name NullEnumeration
        @function Constructor of NullEnumeration
    }*/ 
    private NullEnumeration() {
}

    /**{ method
        @name hasMoreElements
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public final boolean hasMoreElements() {
        return(false);
    }

    /**{ method
        @name nextElement
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public final Object nextElement() {
        return(null);
    }


//- ******************* 
//- End Class NullEnumeration
}
