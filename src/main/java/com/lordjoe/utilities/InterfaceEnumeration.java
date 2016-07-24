/**{ file
    @name InterfaceEnumeration.java
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
import java.util.Vector;
/**{ class
    @name InterfaceEnumeration
    @function <Add Comment Here>
}*/
public class InterfaceEnumeration implements Enumeration {

    //- *******************
    //- Fields
    /**{ field
        @name Object - base
        @function <Add Comment Here>
    }*/
    private Object   m_base;
    private Class[]  m_Interfaces;
    private int      m_index;

    /**{ constructor
        @name InterfaceEnumeration
        @function Constructor of InterfaceEnumeration
    }*/
    public InterfaceEnumeration(Object base) {
        m_base = base;
        Class test = m_base.getClass();
        Vector interfaces = new Vector();
        Class[]  my_interfaces;
        while(test != null) {
            my_interfaces = test.getInterfaces();
            for(int i = 0; i < my_interfaces.length; i++) {
                if(interfaces.indexOf(my_interfaces[i]) < 0)
                    interfaces.addElement(my_interfaces[i]);
            }
            test = test.getSuperclass();
        }
        m_Interfaces = new Class[interfaces.size()];
        interfaces.copyInto(m_Interfaces);

        m_index = -1;
}

    /**{ method
        @name hasMoreElements
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/
    public  boolean hasMoreElements() {
        return(m_index < m_Interfaces.length - 1);
    }

    /**{ method
        @name nextElement
        @function <Add Comment Here>
        @return <Add Comment Here>
    }*/
    public  Object nextElement() {
        m_index++;
        return(m_Interfaces[m_index]);
    }


//- *******************
//- End Class InterfaceEnumeration
}
