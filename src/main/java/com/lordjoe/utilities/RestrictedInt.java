/**{ file
    @name RestrictedInt.java
    @function This is an abstract base class for an analog of
         a c++ enum. Also see EnumException
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
    @name RestrictedInt
    @function Abstract Restricted integer class - also
         see EnumInt
         Sample Use - overrides to make valid
        
         class EvenOnly extends RestrictedInt
         {
         EvenOnly(int k)
         {
         super();
         setValue(k);
         }
         public boolean validValue(int test) {
         return((test % 2) == 0);
         }
         }
    @see Overlord.EnumException
}*/ 
public abstract class RestrictedInt {

    //- ******************* 
    //- Fields 
    /**{ field
        @name m_value
        @function - the value
    }*/ 
    private int m_value;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name RestrictedInt
        @function Constructor of RestrictedInt
    }*/ 
    public RestrictedInt() {
}

    /**{ method
        @name validValue
        @function tests whether an int is a valid value
        @param test int to test
        @return true if OK
        @policy - fully override to implement
        @primary
    }*/ 
    public abstract boolean validValue(int test);

    /**{ method
        @name setValue
        @function sets the value - throws EnumException if
             value is not valid
        @param in new value
        @policy <Add Comment Here>
        @primary
        @see Overlord.EnumException
    }*/ 
    public void setValue(int in) {
        if(! validValue(in)) {
            throw new EnumException();
        }
        m_value = in;
    }

    /**{ method
        @name getValue
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public int getValue() {
        return(m_value);
    }


//- ******************* 
//- End Class RestrictedInt
}
