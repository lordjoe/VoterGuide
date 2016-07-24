/**{ file
    @name IDimension.java
    @function constant Point
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
import java.awt.*;
import java.awt.event.*;
/**{ class
    @name IDimension
    @function like IDimensionangle but with floating point data
         and only modifiale on creation
    @see java.awt.Rectangle
}*/
public class IDimension implements ConstStruct {

    //- *******************
    //- Fields
    /**{ field
        @name width
        @function The width
    }*/
    final public int width;

    /**{ field
        @name height
        @function The height
    }*/
    final public int height;


    //- *******************
    //- Methods
    /**{ constructor
        @name IDimension
        @function Constructor of IDimension
        @UnusedParam> height the height of the rectangle
        @UnusedParam> width the width of the rectangle
        @param x the x coordinate
        @param y the y coordinate
    }*/
    public IDimension(int width,int height) {
        this.width = width;
        this.height = height;
}

    /**{ constructor
        @name IDimension
        @function Constructor of IDimension
        @param p the point
    }*/
    public IDimension(Dimension p) {
        this.width = p.width;
        this.height = p.height;
    }

    /**{ constructor
        @name IDimension
        @function Constructor of IDimension
        @param p the Dimension
    }*/
    public IDimension(IDimension p) {
        this.width = p.width;
        this.height = p.height;
    }


    /**{ method
        @name equals
        @function
             Compares two Objects for equality.
             Returns a boolean that indicates whether this Object is equivalent
             to the specified Object. This method is used when an Object is stored
             in a hashtable.
        @param obj the object to compare with
        @return true if these Objects are equal; false otherwise.
        @overrideReason> add Frectangle test
        @policy add super test
        @primary
        @see java.util.Hashtable

        @see java.util.Hashtable
    }*/
    public boolean equals(Object obj) {
        if(obj == null)
             return false;
        if(obj instanceof IDimension) {
            IDimension r =(IDimension) obj;
            return((width == r.width) &&(height == r.height));
        }
        if(obj instanceof IDimension) {
            IDimension r =(IDimension) obj;
            return((width == r.width) &&(height == r.height));
        }
        return false;
    }

    /**{ method
        @name toString
        @function
             Returns a String that represents the value of this Object. It is recommended
             that all subclasses override this method.
        @return the string
        @overrideReason> add IDimension code
        @policy add to super string
        @primary
    }*/
    public String toString() {
        return getClass().getName() + "[width=" + width + ",height=" + height;
    }

    public Dimension toDimension() {
        return(new Dimension(width,height));
    }

//- *******************
//- End Class IDimension
}
