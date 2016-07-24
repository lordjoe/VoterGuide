/**{ file
    @name IRect.java
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
    @name IRect
    @function like FRectangle but with floating point data
         and only modifiale on creation
    @see java.awt.Rectangle
}*/ 
public class IRect implements ConstStruct {

    //- ******************* 
    //- Fields 
    /**{ field
        @name x
        @function The x coordinate of the rectangle.
    }*/ 
    final public int x;

    /**{ field
        @name y
        @function The y coordinate of the rectangle.
    }*/ 
    final public int y;

    /**{ field
        @name width
        @function The width of the rectangle.
    }*/ 
    final public int width;

    /**{ field
        @name height
        @function The height of the rectangle.
    }*/ 
    final public int height;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name IRect
        @function Constructor of IRect
        @param x the x coordinate
        @param width the width of the rectangle
        @param height the height of the rectangle
        @param y the y coordinate
    }*/ 
    public IRect(int x,int y,int width,int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
}

    /**{ constructor
        @name IRect
        @function Constructor of IRect
        @param width the width of the rectangle
        @param height the height of the rectangle
    }*/ 
    public IRect(int width,int height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
    }

    /**{ constructor
        @name IRect
        @function Constructor of IRect
        @param p the point
        @param d dimension
    }*/ 
    public IRect(Point p,Dimension d) {
        this.x = p.x;
        this.y = p.y;
        this.width = d.width;
        this.height = d.height;
    }

    /**{ constructor
        @name IRect
        @function Constructor of IRect
        @param d dimension
    }*/ 
    public IRect(Dimension d) {
        this.x = 0;
        this.y = 0;
        this.width = d.width;
        this.height = d.height;
    }

    /**{ constructor
        @name IRect
        @function Constructor of IRect
        @param r - rectangle to copy
    }*/ 
    public IRect(Rectangle r) {
        this.x = r.x;
        this.y = r.y;
        this.width = r.width;
        this.height = r.height;
    }

    /**{ constructor
        @name IRect
        @function Constructor of IRect
        @param r - rectangle to copy
    }*/ 
    public IRect(IRect r) {
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
    public boolean contains(int x,int y) {
        return(x >= this.x) &&((x - this.x) < this.width) &&(y >= this.y) &&((y - this.y) < this.height);
    }

    /**{ method
        @name contains
        @function Checks if the specified point lies inside a rectangle.
        @param p the the test Point
        @return true if x,y is inside
        @policy rarely override
        @primary
    }*/ 
    public boolean contains(IRect p) {
        return((p.x >= this.x) &&((p.x + p.width) <(this.x + this.width)) &&(p.y >= this.y) &&((p.y + p.height) <(this.y + this.height)));
    }

    /**{ method
        @name contains
        @function Checks if the specified point lies inside a rectangle.
        @param p the the test Point
        @return true if x,y is inside
        @policy rarely override
        @primary
    }*/ 
    public boolean contains(Point p) {
        return(p.x >= this.x) &&((p.x - this.x) < this.width) &&(p.y >= this.y) &&((p.y - this.y) < this.height);
    }

    /**{ method
        @name contains
        @function Checks if the specified point lies inside a rectangle.
        @param p the the test Point
        @return true if x,y is inside
        @policy rarely override
        @primary
    }*/ 
    public boolean contains(IPoint p) {
        return(p.x >= this.x) &&((p.x - this.x) < this.width) &&(p.y >= this.y) &&((p.y - this.y) < this.height);
    }

    /**{ method
        @name intersects
        @function Computes the intersection of two rectangles.
        @param r Rectangle to intersect
        @return intersecting rect
        @policy <Add Comment Here>
        @primary
    }*/ 
    public boolean intersects(IRect r) {
        return !((r.x + r.width <= x) ||(r.y + r.height <= y) ||(r.x >= x + width) ||(r.y >= y + height));
    }

    /**{ method
        @name intersection
        @function Computes the intersection of two rectangles.
        @param r rectangle to test
        @return intersection rectangle
        @policy rarely override
        @primary
    }*/ 
    public IRect intersection(IRect r) {
        int x1 = Math.max(x,r.x);
        int x2 = Math.min(x + width,r.x + r.width);
        int y1 = Math.max(y,r.y);
        int y2 = Math.min(y + height,r.y + r.height);
        return new IRect(x1,y1,x2 - x1,y2 - y1);
    }

    /**{ method
        @name union
        @function Computes the union of two rectangles.
        @param r rectangle to test
        @return union rectangle
        @policy rarely override
        @primary
    }*/ 
    public IRect union(IRect r) {
        int x1 = Math.min(x,r.x);
        int x2 = Math.max(x + width,r.x + r.width);
        int y1 = Math.min(y,r.y);
        int y2 = Math.max(y + height,r.y + r.height);
        return new IRect(x1,y1,x2 - x1,y2 - y1);
    }

    /**{ method
        @name isEmpty
        @function test is area <= 0
        @return true if area <= 0
        @policy rarely override
        @primary
    }*/ 
    public boolean isEmpty() {
        return(width <= 0) ||(height <= 0);
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
         if(obj instanceof IRect) {
            IRect r =(IRect) obj;
            return(x == r.x) &&(y == r.y) &&(width == r.width) &&(height == r.height);
        }
        if(obj instanceof Rectangle) {
            Rectangle r =(Rectangle) obj;
            return(x == r.x) &&(y == r.y) &&(width == r.width) &&(height == r.height);
        }
        return false;
    }

    /**{ method
        @name toString
        @function
             Returns a String that represents the value of this Object. It is recommended
             that all subclasses override this method.
        @return the string
        @overrideReason> add IRect code
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
    public IRect move(int ax,int ay) {
        return(new IRect(ax,ay,width,height));
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
    public IRect translate(int dx,int dy) {
        return(new IRect(x + dx,y + dy,width,height));
    }

    /**{ method
        @name adds
        @function Translates the rectangle by a Point
        @param p - point to add
        @return <Add Comment Here>
        @policy rarely override
        @primary
    }*/ 
    public IRect add(Point p) {
        return(new IRect(x + p.x,y + p.y,width,height));
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
    public IRect setSize(int awidth,int aheight) {
        return(new IRect(x,y,awidth,aheight));
    }

    /**{ method
        @name toRectangle
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public Rectangle toRectangle() {
        return(new Rectangle(x,y,width,height));
    }


//- ******************* 
//- End Class IRect
}
