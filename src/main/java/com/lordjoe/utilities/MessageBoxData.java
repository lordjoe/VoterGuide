/**{ file
    @name MessageBoxData.java
    @function <Add Comment Here>
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
import java.applet.*;
import java.net.*;
import java.util.*;
/**{ class
    @name MessageBoxData
    @function Data describing a message box to display
}*/ 
public class MessageBoxData {

    //- ******************* 
    //- Fields 
    /**{ field
        @name m_Text
        @function text to display
    }*/ 
    public String m_Text;

    /**{ field
        @name m_TitleText
        @function title to display
    }*/ 
    public String m_TitleText;

    /**{ field
        @name m_Type
        @function type - MessageBoxOK or MessageBoxOKCancel
    }*/ 
    public int m_Type;

    /**{ field
        @name MessageBoxOK
        @function <Add Comment Here>
    }*/ 
    static final int MessageBoxOK = 0;

    /**{ field
        @name MessageBoxOKCancel
        @function <Add Comment Here>
    }*/ 
    static final int MessageBoxOKCancel = 1;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name MessageBoxData
        @function Constructor of MessageBoxData
    }*/ 
    public MessageBoxData() {
}

    /**{ constructor
        @name MessageBoxData
        @function Constructor of MessageBoxData
        @param Title <Add Comment Here>
        @param Text <Add Comment Here>
    }*/ 
    public MessageBoxData(String Title,String Text) {
        m_Text = Text;
        m_TitleText = Title;
        m_Type = MessageBoxOK;
    }


//- ******************* 
//- End Class MessageBoxData
}
