/**{ file
    @name ReferenceEnumerator.java
    @function act like an enumerator for a single reference used when
         a reference wants to emulate a collection with one item
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
    @name ReferenceEnumerator
    @function act like an enumerator for a single reference used when
         a reference wants to emulate a collection with one item
}*/ 
public class ReferenceEnumerator implements Enumeration {

    //- ******************* 
    //- Fields 
    /**{ field
        @name m_Target
        @function <Add Comment Here>
    }*/ 
    private Object m_Target;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name ReferenceEnumerator
        @function Constructor of ReferenceEnumerator
    }*/ 
    public ReferenceEnumerator() {
}

    /**{ constructor
        @name ReferenceEnumerator
        @function Constructor of ReferenceEnumerator
        @param Target <Add Comment Here>
    }*/ 
    public ReferenceEnumerator(Object Target) {
        m_Target = Target;
    }

    /**{ method
        @name hasMoreElements
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public boolean hasMoreElements() {
        return(m_Target != null);
    }

    /**{ method
        @name nextElement
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public Object nextElement() {
        Assertion.validate(m_Target != null);
        Object ret = m_Target;
        m_Target = null;
        return(ret);
    }


//- ******************* 
//- End Class ReferenceEnumerator
}
