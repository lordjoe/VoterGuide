/**{ file
    @name AdjustProperties.java
    @function - define an interface used to set PropertyDescriptor levels 
      such as hidden and expert
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
import java.beans.PropertyDescriptor;
/**{ interface
    @name AdjustProperties
    @function - define an interface used to set PropertyDescriptor levels 
      such as hidden and expert
}*/
public interface AdjustProperties {

    //- *******************
    //- Fields
    /**{ field
        @name AdjustProperties
        @function perform any required adjustments
    }*/
    public void adjustProperties(PropertyDescriptor[] properties);


//- *******************
//- End Class AdjustProperties
}
