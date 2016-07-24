/**{ file
    @name ArrayEnumerator.java
    @function act like an enumerator for a single reference
    @author> Steven Lewis
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
import java.util.*;
/**{ class
    @name ArrayEnumerator
    @function ArrayEnumerator - enumerates over arrays
}*/ 
public class ArrayEnumerator implements Enumeration {

    //- ******************* 
    //- Fields 
    private Object[] m_Target;
    private int m_index;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name ArrayEnumerator
        @function Constructor of ArrayEnumerator
    }*/ 
    public ArrayEnumerator() {
}

    /**{ constructor
        @name ArrayEnumerator
        @function Constructor of ArrayEnumerator
        @param Target array over which we iterate
    }*/ 
    public ArrayEnumerator(Object[] Target) {
        m_Target = Target;
        m_index = 0;
    }

    /**{ method
    * true if not done
    * @return as above
    }*/
    public boolean hasMoreElements() {
        return(m_Target != null && m_index < m_Target.length);
    }

    /**{ method
    * next item
    * @return possibly null item
    }*/
    public Object nextElement() {
        return(m_Target[m_index ++]);
    }


//- ******************* 
//- End Class ArrayEnumerator
}
