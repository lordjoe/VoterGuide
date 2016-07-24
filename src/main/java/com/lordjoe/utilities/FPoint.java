/**{ file
 @name FPoint.java
 @function Just like Point but x,y are doubles
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
 @name FPoint
 @function Just like Point but x,y are doubles
 }*/
public class FPoint implements Struct {

    //- *******************
    //- Fields
    /**{ field
     @name x
     @function x value
     }*/
    public double x;

    /**{ field
     @name y
     @function y value
     }*/
    public double y;


    //- *******************
    //- Methods
    /**{ constructor
     @name FPoint
     @function Constructor of FPoint
     @param x - x value
     @param y - y value
     }*/
    public FPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**{ method
     @name move
     @function sets x and y
     @param newx - new x
     @param newy - new y
     @policy rarely override
     @primary
     }*/
    public void move(double newx, double newy) {
        this.x = newx;
        this.y = newy;
    }

    /**{ method
     @name translate
     @function adds dx,dy to x and y resp
     @param dx delta x
     @param dy delta y
     @policy rarely override
     @primary
     }*/
    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**{ method
     @name hashCode
     @function - make a hash function
     @return the hash
     @policy rarely override
     @primary
     }*/
    public int hashCode() {
        return (int) x ^ ((int) y * 31);
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
     @overrideReason> - add test for FPoint
     @policy override replaces
     @primary
     @see java.util.Hashtable

     @see java.util.Hashtable
     }*/
    public boolean equals(Object obj) {
        if(obj == null)
             return false;
        if (obj instanceof FPoint) {
            FPoint pt = (FPoint) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return false;
    }

    /**{ method
     @name toString
     @function
     Returns a String that represents the value of this Object. It is recommended
     that all subclasses override this method.
     @return the string
     @overrideReason> - code for new fields
     @policy override adds to base implementation
     @primary
     }*/
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }


//- *******************
//- End Class FPoint
}
