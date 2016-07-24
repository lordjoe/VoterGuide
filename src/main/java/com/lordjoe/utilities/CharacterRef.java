/**{ file
    @name CharacterRef.java
    @function CharacterRef allows a function to return a double
    @author> Steven Lewis
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
    @name CharacterRef
    @function this class allows a double to be passed as a reference
    @see com.lordjoe.IntegerRef
    @see com.lordjoe.BooleanRef
    @see com.lordjoe.StringRef
}*/ 
public class CharacterRef implements Struct {

    //- ******************* 
    //- Fields 
    /**{ field
        @name value
        @function real data
    }*/ 
    public char value;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name CharacterRef
        @function Constructor of CharacterRef
        @UnusedParam> d initial value
        @param c <Add Comment Here>
    }*/ 
    public CharacterRef(char c) {
        value = c;
}

    /**{ constructor
        @name CharacterRef
        @function Constructor of CharacterRef
    }*/ 
    public CharacterRef() {
    }


//- ******************* 
//- End Class CharacterRef
}
