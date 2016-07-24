/**{ file
    @name Executor.java
    @function - like Runnable but with a return
    @author> Steven M. Lewis
    @copyright>
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis smlewis@lordjoe.co,
	*  www.LordJoe.com
	************************
    @date> Mon Jun 22 21:48:24 PDT 1998
    @version> 1.0
}*/
package com.lordjoe.utilities;

/**{ interface
    @name AdjustProperties
    @function - like Runnable but with a return
}*/
public interface Executor {

    //- *******************
    //- Fields
    /**{ field
        @name doExecute
        @function perform any required code
        @return some return
    }*/
    public Object doExecute();


//- *******************
//- End Class Executor
}
