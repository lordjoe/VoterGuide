/**{ file
    @name EnumString.java
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
    @name EnumString
    @function This is an abstract base class for an analog of
         a c++ enum but for strings - legal values must be in the
         set of strings m_possibilities. Also see EnumException
         Sample Use - overrides to make valid
        
         class YesNoString extends EnumString
         {
         static String[] m_possibilities = null;
         public YesNoString() {
         super();
         }
         public YesNoString(String s) {
         super();
         setValue(s);
         }
        
         public String[] possibilities() { return(m_possibilities); }
         protected void initValues() {
         m_possibilities = new String[2];
         m_possibilities[0] = "yes";
         m_possibilities[1] = "no";
         }
        
         }
}*/ 
public abstract class EnumString extends RestrictedString {

    //- ******************* 
    //- Methods 
    /**{ constructor
        @name EnumString
        @function Constructor of EnumString
    }*/ 
    protected EnumString() {
        if(possibilies() == null) {
            initValues();
        }
        setValue(possibilies()[0]);
        // default to first option
}

    /**{ method
        @name initValues
        @function this must be called to set up valid values
        @policy - fully override
        @primary
    }*/ 
    protected abstract void initValues();

    /**{ method
        @name possibilies
        @function this returns an array of possible values
        @return the array
        @policy - fully override
        @primary
    }*/ 
    public abstract String[] possibilies();

    /**{ method
        @name validValue
        @function test of String test in possibility set
        @param test String to test
        @return true if OK
        @first_defined RestrictedString#validValue
        @overrideReason> <Add Comment Here>
        @overrides RestrictedString#validValue
        @policy rarely override
    }*/ 
    public boolean validValue(String test) {
        // allow the possibility of a different possibility list
        String[] options = possibilies();
        for(int i = 0; i < options.length; i ++) {
            if(options[i].equals(test)) {
                return(true);
            }
        }
        return(false);
    }


//- ******************* 
//- End Class EnumString
}
