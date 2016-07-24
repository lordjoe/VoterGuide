/**{ file
 @name FRectangle.java
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
 @name FRectangle
 @function like Rectangle but with floating point data
 @see java.awt.Rectangle
 }*/
public class FRectangle implements Struct {

    //- *******************
    //- Fields
    /**{ field
     @name x
     @function The x coordinate of the rectangle.
     }*/
    public double x;

    /**{ field
     @name y
     @function The y coordinate of the rectangle.
     }*/
    public double y;

    /**{ field
     @name width
     @function The width of the rectangle.
     }*/
    public double width;

    /**{ field
     @name height
     @function The height of the rectangle.
     }*/
    public double height;


    //- *******************
    //- Methods
    /**{ constructor
     @name FRectangle
     @function Constructor of FRectangle
     }*/
    public FRectangle() {
    }

    /**{ constructor
     @name FRectangle
     @function Constructor of FRectangle
     @param x the x coordinate
     @param width the width of the rectangle
     @param height the height of the rectangle
     @param y the y coordinate
     }*/
    public FRectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**{ constructor
     @name FRectangle
     @function Constructor of FRectangle
     @param width the width of the rectangle
     @param height the height of the rectangle
     }*/
    public FRectangle(double width, double height) {
        this(0, 0, width, height);
    }

    /**{ constructor
     @name FRectangle
     @function Constructor of FRectangle
     @param p the point
     @param d dimension
     }*/
    public FRectangle(Point p, Dimension d) {
        this(p.x, p.y, d.width, d.height);
    }

    /**{ constructor
     @name FRectangle
     @function Constructor of FRectangle
     @param p the point
     }*/
    public FRectangle(FPoint p) {
        this(p.x, p.y, 0, 0);
    }

    /**{ constructor
     @name FRectangle
     @function Constructor of FRectangle
     @param d dimension
     }*/
    public FRectangle(Dimension d) {
        this(0.0, 0.0, (double) d.width, (double) d.height);
    }

    /**{ constructor
     @name FRectangle
     @function Constructor of FRectangle
     @param r - rectangle to copy
     }*/
    public FRectangle(Rectangle r) {
        this((double) r.x, (double) r.y, (double) r.width, (double) r.height);
    }

    /**{ method
     @name setBounds
     @function Reshapes the rectangle.
     @param height the height of the rectangle
     @param width the width of the rectangle
     @param y the y coordinate
     @param x the x coordinate
     @policy rarely override
     @primary
     }*/
    public void setBounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**{ method
     @name move
     @function Moves the rectangle.
     @param x the x coordinate
     @param y the y coordinate
     @policy rarely override
     @primary
     }*/
    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**{ method
     @name translate
     @function Translates the rectangle.
     @param x the x coordinate
     @param y the y coordinate
     @policy rarely override
     @primary
     }*/
    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**{ method
     @name setSize
     @function Resizes the rectangle.
     @param width the width of the rectangle
     @param height the height of the rectangle
     @policy rarely override
     @primary
     }*/
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
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
    public boolean intersects(FRectangle r) {
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
    public FRectangle intersection(FRectangle r) {
        double x1 = Math.max(x, r.x);
        double x2 = Math.min(x + width, r.x + r.width);
        double y1 = Math.max(y, r.y);
        double y2 = Math.min(y + height, r.y + r.height);
        return new FRectangle(x1, y1, x2 - x1, y2 - y1);
    }

    /**{ method
     @name union
     @function Computes the union of two rectangles.
     @param r rectangle to test
     @return union rectangle
     @policy rarely override
     @primary
     }*/
    public FRectangle union(FRectangle r) {
        double x1 = Math.min(x, r.x);
        double x2 = Math.max(x + width, r.x + r.width);
        double y1 = Math.min(y, r.y);
        double y2 = Math.max(y + height, r.y + r.height);
        return new FRectangle(x1, y1, x2 - x1, y2 - y1);
    }

    /**{ method
     @name add
     @function Adds a point to a rectangle. This results in the smallest
     rectangle that contains both the rectangle and the point.
     @param newx x value
     @param newy y value
     @policy rarely override
     @primary
     }*/
    public void add(double newx, double newy) {
        double x1 = Math.min(x, newx);
        double x2 = Math.max(x + width, newx);
        double y1 = Math.min(y, newy);
        double y2 = Math.max(y + height, newy);
        x = x1;
        y = y1;
        width = x2 - x1;
        height = y2 - y1;
    }

    /**{ method
     @name add
     @function Adds a point to a rectangle. This results in the smallest
     rectangle that contains both the rectangle and the point.
     @param pt point to add
     @policy rarely override
     @primary
     }*/
    public void add(Point pt) {
        add(pt.x, pt.y);
    }

    /**{ method
     @name add
     @function Adds a rectangle to a rectangle. This results in the union
     of the two rectangles.
     @param r rectangle to add
     @policy rarely override
     @primary
     }*/
    public void add(FRectangle r) {
        double x1 = Math.min(x, r.x);
        double x2 = Math.max(x + width, r.x + r.width);
        double y1 = Math.min(y, r.y);
        double y2 = Math.max(y + height, r.y + r.height);
        x = x1;
        y = y1;
        width = x2 - x1;
        height = y2 - y1;
    }

    /**{ method
     @name grow
     @function Grows the rectangle horizontally and vertically.
     @param h horizontal growth
     @param v vertical growth
     @policy rarely override
     @primary
     }*/
    public void grow(double h, double v) {
        x -= h;
        y -= v;
        width += h * 2;
        height += v * 2;
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
        if (obj instanceof FRectangle) {
            FRectangle r = (FRectangle) obj;
            return (x == r.x) && (y == r.y) && (width == r.width) && (height == r.height);
        }
        if (obj instanceof FRect) {
            FRect r = (FRect) obj;
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
     @overrideReason> add FRectangle code
     @policy add to super string
     @primary
     }*/
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
    }


//- *******************
//- End Class FRectangle
}
