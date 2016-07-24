/**{ file
    @name BeanProperty.java
    @function BeanProperty A BeanProperty holds get and Set Methods
         and can get or set a value
    @author> Steven Lewis
    @copyright> 
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************

    @date> Mon Jun 22 21:48:25 PDT 1998
    @version> 1.0
}*/ 
package com.lordjoe.utilities;

import java.lang.reflect.*;
/**{ class
    @name BeanProperty
    @function A BeanProperty holds get and Set Methods
         and can get or set a value
}*/ 
public class BeanProperty implements Struct {

    //- ******************* 
    //- Fields 
    /**{ field
        @name name
        @function the key
    }*/ 
    public String name;

    /**{ field
        @name type
        @function the value
    }*/ 
    public Class type;

    /**{ field
        @name getMethod
        @function the value
    }*/ 
    public Method getMethod;

    /**{ field
        @name setMethod
        @function the value
    }*/ 
    public Method setMethod;

    /**{ field
        @name isIndexed
        @function <Add Comment Here>
    }*/ 
    public boolean isIndexed = false;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name BeanProperty
        @function Constructor of BeanProperty
        @param aname - property name
        @param set - set method - may be null
        @param get - get method may be null
    }*/ 
    public BeanProperty(String aname,Method set,Method get) {
        name = aname;
        getMethod = get;
        setMethod = set;
        if(get != null) {
            Assertion.validate(ClassUtilities.isGetMethod(get,null));
            type = get.getReturnType();
            Class[] types = set.getParameterTypes();
            isIndexed =(types.length == 1);
            Assertion.validate(set == null || ClassUtilities.isSetMethod(set,type));
        }
        else {
            Assertion.validate(ClassUtilities.isSetMethod(set,null));
            Class[] types = set.getParameterTypes();
            type = types[types.length - 1];
            isIndexed =(types.length == 2);
        }
}

    /**{ constructor
        @name BeanProperty
        @function Constructor of BeanProperty
        @param name the property name
        @param c the Class
    }*/ 
    public BeanProperty(String name,Class c) {
    }

    /**{ constructor
        @name BeanProperty
        @function Constructor of BeanProperty
        @param name the property name
        @param ob object having the property
    }*/ 
    public BeanProperty(String name,Object ob) {
        this(name,ob.getClass());
    }

    /**{ constructor
        @name BeanProperty
        @function Constructor of BeanProperty
    }*/ 
    public BeanProperty() {
    }

    /**{ method
        @name set
        @function set the property for a non-indexed property
        @param target - object holding the property
        @param data - data to set
        @policy <Add Comment Here>
    }*/ 
    public void set(Object target,Object data) {
        // use indexed form if indexed
        Assertion.validate(! isIndexed);
        if(setMethod != null) {
            Object[] args = new Object[1];
            args[0] = data;
            try {
                setMethod.invoke(target,args);
            }
            catch(Exception ex) {
				Assertion.fatalException(ex);
            }
        }
    }

    /**{ method
        @name set
        @function set the property for an indexed property
        @param target - object holding the property
        @param index - index into array
        @param data - data to set
        @policy <Add Comment Here>
    }*/ 
    public void set(Object target,Object data,int index) {
        // use not indexed form if not indexed
        Assertion.validate(isIndexed);
        if(setMethod != null) {
            Object[] args = new Object[2];
            args[0] = data;
            args[1] = new Integer(index);
            try {
                setMethod.invoke(target,args);
            }
            catch(Exception ex) {
				Assertion.fatalException(ex);
            }
        }
    }

    /**{ method
        @name get
        @function get the property for a non- ndexed property
        @UnusedParam> return - value
        @param target - object holding the property
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public Object get(Object target) {
        Assertion.validate(! isIndexed);
        Object ret = null;
        if(getMethod != null) {
            try {
                ret = getMethod.invoke(target,(Object[])null);
            }
            catch(Exception ex) {
 				Assertion.fatalException(ex);
               ret = null;
            }
        }
        return(ret);
    }

    /**{ method
        @name get
        @function get the property for an ndexed property
        @UnusedParam> return - value
        @param target - object holding the property
        @param index - index into array
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public Object get(Object target,int index) {
        // use not indexed form if not indexed
        Assertion.validate(isIndexed);
        Object ret = null;
        if(getMethod != null) {
            Object[] args = new Object[1];
            args[0] = new Integer(index);
            try {
                ret = getMethod.invoke(target,args);
            }
            catch(Exception ex) {
 				Assertion.fatalException(ex);
               ret = null;
            }
        }
        return(ret);
    }

    /**{ method
        @name isReadOnly
        @function - return true is the property is read only i.e.
             has no set method
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public boolean isReadOnly() {
        return(setMethod == null);
    }

    /**{ method
        @name getDisplayName
        @function - return string to display the property - split
             nerd caps
        @return <Add Comment Here>
        @policy <Add Comment Here>
        @see com.lordjoe.Util#displayString
    }*/ 
    public String getDisplayName() {
        return(Util.displayString(name));
    }


//- ******************* 
//- End Class BeanProperty
}
