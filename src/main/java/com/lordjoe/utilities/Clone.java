/**{ file
 @name Util.java
 @function this class - a Nullity implements a large number of
 useful functions
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


import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.lang.reflect.Array;

/**{ class
 @name Util
 @function this class - a Nullity implements a large number of
 useful functions
 }*/
abstract public class Clone extends Nulleton {
    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static Rectangle clone(Rectangle r) {
        if (r != null) {
            return (new Rectangle(r.x, r.y, r.width, r.height));
        }
        return (new Rectangle());
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static Point clone(Point r) {
        if (r != null) {
            return (new Point(r.x, r.y));
        }
        return (new Point(0, 0));
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static Color clone(Color r) {
        if (r != null) {
            return (new Color(r.getRGB()));
        }
        return (new Color(0, 0, 0));
    }


    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    /*  public static IRect clone(IRect r) {
          return(new IRect(r.x,r.y,r.width,r.height));
      }
      */

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static FRect clone(FRect r) {
        return (new FRect(r.x, r.y, r.width, r.height));
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static FRectangle clone(FRectangle r) {
        if (r != null) {
            return (new FRectangle(r.x, r.y, r.width, r.height));
        }
        return (new FRectangle());
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static FPoint clone(FPoint r) {
        if (r != null) {
            return (new FPoint(r.x, r.y));
        }
        return (new FPoint(0, 0));
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static Dimension clone(Dimension r) {
        if (r != null) {
            return (new Dimension(r.width, r.height));
        }
        return (new Dimension(0, 0));
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static String[] clone(String[] r) {
        if (r != null) {
            String[] ret = new String[r.length];
            System.arraycopy(r, 0, ret, 0, r.length);
            return (ret);
        }
        return (null);
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static boolean[] clone(boolean[] r) {
        if (r != null) {
            boolean[] ret = new boolean[r.length];
            System.arraycopy(r, 0, ret, 0, r.length);
            return (ret);
        }
        return (null);
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static int[] clone(int[] r) {
        if (r != null) {
            int[] ret = new int[r.length];
            System.arraycopy(r, 0, ret, 0, r.length);
            return (ret);
        }
        return (null);
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static char[] clone(char[] r) {
        if (r != null) {
            char[] ret = new char[r.length];
            System.arraycopy(r, 0, ret, 0, r.length);
            return (ret);
        }
        return (null);
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static double[] clone(double[] r) {
        if (r != null) {
            double[] ret = new double[r.length];
            System.arraycopy(r, 0, ret, 0, r.length);
            return (ret);
        }
        return (null);
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static short[] clone(short[] r) {
        if (r != null) {
            short[] ret = new short[r.length];
            System.arraycopy(r, 0, ret, 0, r.length);
            return (ret);
        }
        return (null);
    }

    /**{ method
     @name clone
     @function copies an object - even if not clonable
     @param r object to clone
     @return the copy
     }*/
    public static Object[] clone(Object[] r) {
        if (r != null) {
            Object[] ret = (Object[]) Array.newInstance(r.getClass().getComponentType(), r.length);
            System.arraycopy(r, 0, ret, 0, r.length);
            return (ret);
        }
        return (null);
    }
}

