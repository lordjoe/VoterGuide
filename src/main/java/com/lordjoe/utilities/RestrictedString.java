/**{ file
    @name RestrictedString.java
    @function This is an abstract base class for an analog of
         a c++ enum but for strings - legal values must be in the
         set of strings m_possibilities. Also see EnumException
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
    @name RestrictedString
    @function This is an abstract base class for an analog of
         a c++ enum but for strings - legal values must be in the
         set of strings m_possibilities. Also see EnumException
         Sample Use - overrides to make valid
        
         class JavaFileName extends RestrictedString
         {
         public JavaFileName(String s) {
         super();
         setValue(s);
         }
         public boolean validValue(String test) {
         return(test,endsWith(".java"));
         }
        
         }
    @see EnumString
    @see EnumException
        
}*/ 
public abstract class RestrictedString {

    //- ******************* 
    //- Fields 
    /**{ field
        @name m_value
        @function current value - may be null
    }*/ 
    private String m_value;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name RestrictedString
        @function Constructor of RestrictedString
    }*/ 
    protected RestrictedString() {
    }

    /**{ method
        @name validValue
        @function test of String test in possibility set
        @param test String to test
        @return true if OK
        @policy rarely override
        @primary
    }*/ 
    public abstract boolean validValue(String test);

    /**{ method
        @name setValue
        @function sets value is string OK otherwise throws EnumException
        @param in candidate input string
        @policy rarely override
        @primary
        @see Overlord.EnumException
    }*/ 
    public void setValue(String in) {
        if(! validValue(in)) {
            throw new EnumException();
        }
        m_value = in;
    }

    /**{ method
        @name getValue
        @function returns the current value
        @return the value - may be null
        @policy rarely override
        @primary
    }*/ 
    public String getValue() {
        return(m_value);
    }


//- ******************* 
//- End Class RestrictedString
}
