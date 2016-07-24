/**{ file
    @name AncestorEnumeration.java
    @function - An enumeration of the derivation of an object - starts at the current class and 
       ends with Object
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
    @name AncestorEnumeration
    ENumeration over all elements of the class tree
}*/
public class AncestorEnumeration implements Enumeration {

    //- *******************
    //- Fields
    private Object m_base;
    private Class  m_Current;

    /**{ constructor
        @name AncestorEnumeration
        @function Constructor of AncestorEnumeration
    }*/
    public AncestorEnumeration(Object base) {
        m_base = base;
}

    /**{ method
    * true if not done
    * @return as above
    }*/
    public  boolean hasMoreElements() {
        return(m_Current != java.lang.Object.class);
    }

    /**{ method
    * ancestor of current class
    * @return as above
    }*/
    public  Object nextElement() {
        if(m_Current == null)
            m_Current = m_base.getClass();
        else
            m_Current = m_Current.getSuperclass();
        return(m_Current);
    }


//- *******************
//- End Class AncestorEnumeration
}
