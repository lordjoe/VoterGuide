/**{ file
 @name FRect.java
 @function like Rectangle but with floating point data
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
 @name FRect
 @function like FRectangle but with floating point data
 and only modifiale on creation
 @see java.awt.Rectangle
 }*/
public class FRect implements ConstStruct {

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

    /**{ field
     @name width
     @function The width of the rectangle.
     }*/
    final public double width;

    /**{ field
     @name height
     @function The height of the rectangle.
     }*/
    final public double height;


    //- *******************
    //- Methods
    /**{ constructor
     @name FRect
     @function Constructor of FRect
     @param x the x coordinate
     @param width the width of the rectangle
     @param height the height of the rectangle
     @param y the y coordinate
     }*/
    public FRect(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**{ constructor
     @name FRect
     @function Constructor of FRect
     @param width the width of the rectangle
     @param height the height of the rectangle
     }*/
    public FRect(double width, double height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
    }

    /**{ constructor
     @name FRect
     @function Constructor of FRect
     @param p the point
     @param d dimension
     }*/
    public FRect(Point p, Dimension d) {
        this.x = p.x;
        this.y = p.y;
        this.width = d.width;
        this.height = d.height;
    }

    /**{ constructor
     @name FRect
     @function Constructor of FRect
     @param d dimension
     }*/
    public FRect(Dimension d) {
        this.x = 0;
        this.y = 0;
        this.width = d.width;
        this.height = d.height;
    }

    /**{ constructor
     @name FRect
     @function Constructor of FRect
     @param r - rectangle to copy
     }*/
    public FRect(Rectangle r) {
        this.x = r.x;
        this.y = r.y;
        this.width = r.width;
        this.height = r.height;
    }

    /**{ constructor
     @name FRect
     @function Constructor of FRect
     @param r - rectangle to copy
     }*/
    public FRect(FRect r) {
        this.x = r.x;
        this.y = r.y;
        this.width = r.width;
        this.height = r.height;
    }

    /**{ method
     @name contains
     @function Checks if the specified point lies inside a rectangle.
     @param x the x coordinate
     @param y the y coordinate
     @return true if x,y is inside
     @policy rarely override
     @primary
     }*/
    public boolean contains(double x, double y) {
        return (x >= this.x) && ((x - this.x) < this.width) && (y >= this.y) && ((y - this.y) < this.height);
    }

    /**{ method
     @name intersects
     @function Computes the intersection of two rectangles.
     @param r Rectangle to intersect
     @return intersecting rect
     @policy <Add Comment Here>
     @primary
     }*/
    public boolean intersects(FRect r) {
        return !((r.x + r.width <= x) || (r.y + r.height <= y) || (r.x >= x + width) || (r.y >= y + height));
    }

    /**{ method
     @name intersection
     @function Computes the intersection of two rectangles.
     @param r rectangle to test
     @return intersection rectangle
     @policy rarely override
     @primary
     }*/
    public FRect intersection(FRect r) {
        double x1 = Math.max(x, r.x);
        double x2 = Math.min(x + width, r.x + r.width);
        double y1 = Math.max(y, r.y);
        double y2 = Math.min(y + height, r.y + r.height);
        return new FRect(x1, y1, x2 - x1, y2 - y1);
    }

    /**{ method
     @name union
     @function Computes the union of two rectangles.
     @param r rectangle to test
     @return union rectangle
     @policy rarely override
     @primary
     }*/
    public FRect union(FRect r) {
        double x1 = Math.min(x, r.x);
        double x2 = Math.max(x + width, r.x + r.width);
        double y1 = Math.min(y, r.y);
        double y2 = Math.max(y + height, r.y + r.height);
        return new FRect(x1, y1, x2 - x1, y2 - y1);
    }

    /**{ method
     @name isEmpty
     @function test is area <= 0
     @return true if area <= 0
     @policy rarely override
     @primary
     }*/
    public boolean isEmpty() {
        return (width <= 0) || (height <= 0);
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
        if (obj instanceof FRect) {
            FRect r = (FRect) obj;
            return (x == r.x) && (y == r.y) && (width == r.width) && (height == r.height);
        }
        if (obj instanceof FRectangle) {
            FRectangle r = (FRectangle) obj;
            return (x == r.x) && (y == r.y) && (width == r.width) && (height == r.height);
        }
        return false;
    }

    /**{ method
     @name toString
     @function
     Returns a String that represents the value of this Object. It is recommended
     that all subclasses override this method.
     @return the string
     @overrideReason> add FRect code
     @policy add to super string
     @primary
     }*/
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
    }

    /**{ method
     @name move
     @function Moves the rectangle.
     @UnusedParam> x the x coordinate
     @UnusedParam> y the y coordinate
     @param ax <Add Comment Here>
     @param ay <Add Comment Here>
     @return <Add Comment Here>
     @policy rarely override
     @primary
     }*/
    public FRect move(double ax, double ay) {
        return (new FRect(ax, ay, width, height));
    }

    /**{ method
     @name translate
     @function Translates the rectangle.
     @UnusedParam> x the x coordinate
     @UnusedParam> y the y coordinate
     @param dx <Add Comment Here>
     @param dy <Add Comment Here>
     @return <Add Comment Here>
     @policy rarely override
     @primary
     }*/
    public FRect translate(double dx, double dy) {
        return (new FRect(x + dx, y + dy, width, height));
    }

    /**{ method
     @name setSize
     @function Resizes the rectangle.
     @UnusedParam> width the width of the rectangle
     @UnusedParam> height the height of the rectangle
     @param awidth <Add Comment Here>
     @param aheight <Add Comment Here>
     @return <Add Comment Here>
     @policy rarely override
     @primary
     }*/
    public FRect setSize(double awidth, double aheight) {
        return (new FRect(x, y, awidth, aheight));
    }


//- *******************
//- End Class FRect
}
