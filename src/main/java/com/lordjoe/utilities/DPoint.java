/**{ file
    @name DPoint.java
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
    @name DPoint
    @function like IPointangle but with floating point data
         and only modifiale on creation
    @see java.awt.Rectangle
}*/ 
public class DPoint implements ConstStruct {

    //- ******************* 
    //- Fields 
    /**{ field
        @name x
        @function The x coordinate of the rectangle.
    }*/ 
    final public double x;

    /**{ field
        @name y
        @function The y coordinate of the rectangle.
    }*/ 
    final public double y;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name DPoint
        @function Constructor of DPoint
        @UnusedParam> height the height of the rectangle
        @UnusedParam> width the width of the rectangle
        @param x the x coordinate
        @param y the y coordinate
    }*/ 
    public DPoint(double x,double y) {
        this.x = x;
        this.y = y;
}

    /**{ constructor
        @name DPoint
        @function Constructor of DPoint
        @UnusedParam> d dimension
        @param p the point
    }*/ 
    public DPoint(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**{ constructor
        @name DPoint
        @function Constructor of DPoint
        @param r - rectangle to copy
    }*/ 
    public DPoint(DPoint r) {
        this.x = r.x;
        this.y = r.y;
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
        if(obj instanceof DPoint) {
            DPoint r =(DPoint) obj;
            return((x == r.x) &&(y == r.y));
        }
        if(obj instanceof FPoint) {
            FPoint r =(FPoint) obj;
            return((x == r.x) &&(y == r.y));
        }
        return false;
    }

    /**{ method
        @name toString
        @function
             Returns a String that represents the value of this Object. It is recommended
             that all subclasses override this method.
        @return the string
        @overrideReason> add DPoint code
        @policy add to super string
        @primary
    }*/ 
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y;
    }


//- ******************* 
//- End Class DPoint
}
