/**{ file
    @name EnumInt.java
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
    @name EnumInt
    @function Abstract enumerated integer class
         Sample Use - overrides to make valid
        
         class ZerotoTwo extends EnumString
         {
         protected static int[] m_possibilies = null;
         public ZerotoTwo() {
         super();
         }
         public ZerotoTwo(int s) {
         super();
         setValue(s);
         }
        
         public int[] possibilies() { return(m_possibilies); }
         protected void initValues() {
         if(m_possibilities != null)
         return;
         m_possibilities = new int[3];
         int n = 0;
         m_possibilities[n++] = 0;
         m_possibilities[n++] = 1;
         m_possibilities[n++] = 2;
         }
        
         }
    @see Overlord.EnumException
}*/ 
public abstract class EnumInt extends RestrictedInt {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name EnumInt
        @function Constructor of EnumInt
    }*/ 
    protected EnumInt() {
        if(possibilies() == null) {
            initValues();
        }
        setValue(possibilies()[0]);
        // default to first option
}

    /**{ method
        @name validValue
        @function tests whether an int is a valid value
        @param test int to test
        @return true if OK
        @first_defined RestrictedInt#validValue
        @overrides RestrictedInt#validValue
        @policy - fully override to implement
    }*/ 
    public boolean validValue(int test) {
        // allow the possibility of a different possibility list
        int[] options = possibilies();
        for(int i = 0; i < options.length; i ++) {
            if(options[i] == test) {
                return(true);
            }
        }
        return(false);
    }

    /**{ method
        @name initValues
        @function sets up valid values
        @policy - override to create possibilities
        @primary
    }*/ 
    protected abstract void initValues();

    /**{ method
        @name possibilies
        @function get possible values - set up with initValues
        @return possible values
        @policy - override to return possibilities
        @primary
    }*/ 
    public abstract int[] possibilies();


//- ******************* 
//- End Class EnumInt
}
