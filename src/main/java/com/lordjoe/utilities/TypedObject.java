/**{ file
    @name TypedObject.java
    @function Typed Object
    @author> Steven M. Lewis
    @copyright> 
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************
    @date> Mon Jun 22 21:48:25 PDT 1998
    @file> Overlord\Introspect\LJavaParse.java
    @version> 0.90, 12 Jun 1996
        
         ************************
         Copyright (c) 1996
         Steven M. Lewis
         ************************
        
}*/ 
package com.lordjoe.utilities;
/**{ class
    @name TypedObject
    @function an Object holding an Object,Class Pair - this object
         may be set only with an object of the indicated class
    @see Overlord.Object#m_Owners
}*/ 
public class TypedObject {

    //- ******************* 
    //- Fields 
    /**{ field
        @name m_Object
        @function the object - must be an instance of m_Class
    }*/ 
    private Object m_Object;

    /**{ field
        @name m_Class
        @function required class
    }*/ 
    private Class m_Class;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name TypedObject
        @function Constructor of TypedObject
    }*/ 
    public TypedObject() {
}

    /**{ constructor
        @name TypedObject
        @function Constructor of TypedObject
        @param o initial object - may be null
        @param c initial class - may be null
    }*/ 
    public TypedObject(Object o,Class c) {
        this();
        setRequiredClass(c);
        setObject(o);
    }

    /**{ method
        @name setObject
        @function sets the object - class is tested
        @param o new object - may be null
        @policy call supper version first
        @primary
    }*/ 
    public void setObject(Object o) {
        if(o != null && m_Class != null) {
            if(! ClassUtilities.isInstance(o,m_Class)) {
                badSetObject(o);
            }
            m_Object = o;
        }
    }

    /**{ method
        @name badSetObject
        @function called if the object in setObject is not of the
             proper class
        @param o the bad object
        @policy rarely override
        @primary
    }*/ 
    protected void badSetObject(Object o) {
        String ObjectClass = o.getClass().getName();
        String RequiredClass = m_Class.getName();
        // this will fail
        Assertion.validate(ClassUtilities.isInstance(o,m_Class));
        ObjectClass = null;
        RequiredClass = null;
    }

    /**{ method
        @name setRequiredClass
        @function - sets the required class - validates new state
        @param c the class
        @policy rarely override
        @primary
    }*/ 
    public void setRequiredClass(Class c) {
        if(c != null && m_Object != null) {
            Assertion.validate(ClassUtilities.isInstance(m_Object,c));
        }
        m_Class = c;
    }

    /**{ method
        @name getObject
        @function return the object
        @return the object
        @policy rarely override
        @primary
    }*/ 
    public Object getObject() {
        return(m_Object);
    }

    /**{ method
        @name getRequiredClass
        @function return the class
        @return the class
        @policy rarely override
        @primary
    }*/ 
    public Class getRequiredClass() {
        return(m_Class);
    }


//- ******************* 
//- End Class TypedObject
}
