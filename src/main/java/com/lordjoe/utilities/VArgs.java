/**{ file
    @name VArgs.java
    @function
         This class converts many forms of argument into an array of objects
         works really will with up to 4 arguments
         in all cases the method is to and the return is of type Object[]
         as in:
         int a;
         boolean b;
         Button c;
         Object[] ret = VArgs.to(a,b,c);
         The usual use is to set up the arguments to a Method as
         Method action = ClassUtilities.findUniqueMethod(java.awt.Button.class,
         "setLabel");
         Button target;
         try {
         action.invoke(target, VArgs.to("New Label"));
         ...
    @copyright> 
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************

    @date> Mon Jun 22 21:48:25 PDT 1998
}*/ 

package com.lordjoe.utilities;


abstract public class VArgs extends Nulleton {

    //- ******************* 
    //- Methods 
    /**{ method
        @name cat
        @function - concatenate two arrays of Objects
        @param arg1 - array1 - may be null
        @param arg2 - array2 - may be null
        @return - combined array
    }*/ 
    public static Object[] cat(Object[] arg1,Object[] arg2) {
        if(arg1 == null) {
            return(arg2);
        }
        if(arg2 == null) {
            return(arg1);
        }
        Object[] ret = new Object[arg1.length + arg2.length];
        System.arraycopy(arg1,0,ret,0,arg1.length);
        System.arraycopy(arg2,0,ret,arg1.length,arg2.length);
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(Object arg0) {
        Object[] ret = {
            arg0 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(int arg0) {
        Object[] ret = {
            new Integer(arg0) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(float arg0) {
        Object[] ret = {
            new Float(arg0) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(boolean arg0) {
        Object[] ret = {
            new Boolean(arg0) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(double arg0) {
        Object[] ret = {
            new Double(arg0) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(byte arg0) {
        Object[] ret = {
            new Byte(arg0) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(char arg0) {
        Object[] ret = {
            new Character(arg0) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(short arg0) {
        Object[] ret = {
            new Short(arg0) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(long arg0) {
        Object[] ret = {
            new Long(arg0) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @param arg1 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(Object arg0,Object arg1) {
        Object[] ret = {
            arg0,arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @param arg1 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(Object arg0,int arg1) {
        Object[] ret = {
            arg0,new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @param arg1 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(Object arg0,float arg1) {
        Object[] ret = {
            arg0,new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @param arg1 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(Object arg0,boolean arg1) {
        Object[] ret = {
            arg0,new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @param arg1 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(Object arg0,double arg1) {
        Object[] ret = {
            arg0,new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @param arg1 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(Object arg0,byte arg1) {
        Object[] ret = {
            arg0,new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 arbitrary argument - if object can be null
        @param arg1 arbitrary argument - if object can be null
        @return non-null array of Objects
    }*/ 
    public static Object[] to(Object arg0,char arg1) {
        Object[] ret = {
            arg0,new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1) {
        Object[] ret = {
            arg0,new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1) {
        Object[] ret = {
            arg0,new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1) {
        Object[] ret = {
            new Integer(arg0),arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1) {
        Object[] ret = {
            new Float(arg0),arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1) {
        Object[] ret = {
            new Float(arg0),new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1) {
        Object[] ret = {
            new Float(arg0),new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1) {
        Object[] ret = {
            new Float(arg0),new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1) {
        Object[] ret = {
            new Float(arg0),new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1) {
        Object[] ret = {
            new Float(arg0),new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1) {
        Object[] ret = {
            new Boolean(arg0),arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1) {
        Object[] ret = {
            new Double(arg0),arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1) {
        Object[] ret = {
            new Double(arg0),new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1) {
        Object[] ret = {
            new Double(arg0),new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1) {
        Object[] ret = {
            new Double(arg0),new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1) {
        Object[] ret = {
            new Double(arg0),new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1) {
        Object[] ret = {
            new Double(arg0),new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1) {
        Object[] ret = {
            new Byte(arg0),arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1) {
        Object[] ret = {
            new Character(arg0),arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1) {
        Object[] ret = {
            new Character(arg0),new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1) {
        Object[] ret = {
            new Character(arg0),new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1) {
        Object[] ret = {
            new Character(arg0),new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1) {
        Object[] ret = {
            new Character(arg0),new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1) {
        Object[] ret = {
            new Character(arg0),new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1) {
        Object[] ret = {
            new Short(arg0),arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1) {
        Object[] ret = {
            new Short(arg0),new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1) {
        Object[] ret = {
            new Short(arg0),new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1) {
        Object[] ret = {
            new Short(arg0),new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1) {
        Object[] ret = {
            new Short(arg0),new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1) {
        Object[] ret = {
            new Short(arg0),new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1) {
        Object[] ret = {
            new Long(arg0),arg1 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1) {
        Object[] ret = {
            new Long(arg0),new Float(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1) {
        Object[] ret = {
            new Long(arg0),new Double(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1) {
        Object[] ret = {
            new Long(arg0),new Character(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1) {
        Object[] ret = {
            new Long(arg0),new Short(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1) {
        Object[] ret = {
            new Long(arg0),new Long(arg1) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,Object arg2) {
        Object[] ret = {
            arg0,arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,int arg2) {
        Object[] ret = {
            arg0,arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,float arg2) {
        Object[] ret = {
            arg0,arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            arg0,arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,double arg2) {
        Object[] ret = {
            arg0,arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,byte arg2) {
        Object[] ret = {
            arg0,arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,char arg2) {
        Object[] ret = {
            arg0,arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,short arg2) {
        Object[] ret = {
            arg0,arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,Object arg1,long arg2) {
        Object[] ret = {
            arg0,arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2 arbitrary argument - if object can be null
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,Object arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg1 - second argument
        @param arg2  - third argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,int arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,float arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,boolean arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,double arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,byte arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,char arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,short arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,int arg1,long arg2) {
        Object[] ret = {
            arg0,new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,Object arg2) {
        Object[] ret = {
            arg0,new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,int arg2) {
        Object[] ret = {
            arg0,new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,float arg2) {
        Object[] ret = {
            arg0,new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,boolean arg2) {
        Object[] ret = {
            arg0,new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,double arg2) {
        Object[] ret = {
            arg0,new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,byte arg2) {
        Object[] ret = {
            arg0,new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,char arg2) {
        Object[] ret = {
            arg0,new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,short arg2) {
        Object[] ret = {
            arg0,new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,float arg1,long arg2) {
        Object[] ret = {
            arg0,new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,int arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,float arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,double arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,char arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,short arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,boolean arg1,long arg2) {
        Object[] ret = {
            arg0,new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,Object arg2) {
        Object[] ret = {
            arg0,new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,int arg2) {
        Object[] ret = {
            arg0,new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,float arg2) {
        Object[] ret = {
            arg0,new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,boolean arg2) {
        Object[] ret = {
            arg0,new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,double arg2) {
        Object[] ret = {
            arg0,new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,byte arg2) {
        Object[] ret = {
            arg0,new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,char arg2) {
        Object[] ret = {
            arg0,new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,short arg2) {
        Object[] ret = {
            arg0,new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,double arg1,long arg2) {
        Object[] ret = {
            arg0,new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,Object arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,int arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,float arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,double arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,byte arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,char arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,short arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,byte arg1,long arg2) {
        Object[] ret = {
            arg0,new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,Object arg2) {
        Object[] ret = {
            arg0,new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,int arg2) {
        Object[] ret = {
            arg0,new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,float arg2) {
        Object[] ret = {
            arg0,new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,boolean arg2) {
        Object[] ret = {
            arg0,new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,double arg2) {
        Object[] ret = {
            arg0,new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,byte arg2) {
        Object[] ret = {
            arg0,new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,char arg2) {
        Object[] ret = {
            arg0,new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,short arg2) {
        Object[] ret = {
            arg0,new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,char arg1,long arg2) {
        Object[] ret = {
            arg0,new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,Object arg2) {
        Object[] ret = {
            arg0,new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,int arg2) {
        Object[] ret = {
            arg0,new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,float arg2) {
        Object[] ret = {
            arg0,new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,boolean arg2) {
        Object[] ret = {
            arg0,new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,double arg2) {
        Object[] ret = {
            arg0,new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,byte arg2) {
        Object[] ret = {
            arg0,new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,char arg2) {
        Object[] ret = {
            arg0,new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,short arg2) {
        Object[] ret = {
            arg0,new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,short arg1,long arg2) {
        Object[] ret = {
            arg0,new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,Object arg2) {
        Object[] ret = {
            arg0,new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,int arg2) {
        Object[] ret = {
            arg0,new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,float arg2) {
        Object[] ret = {
            arg0,new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,boolean arg2) {
        Object[] ret = {
            arg0,new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,double arg2) {
        Object[] ret = {
            arg0,new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,byte arg2) {
        Object[] ret = {
            arg0,new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,char arg2) {
        Object[] ret = {
            arg0,new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,short arg2) {
        Object[] ret = {
            arg0,new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(Object arg0,long arg1,long arg2) {
        Object[] ret = {
            arg0,new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,Object arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,int arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,float arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,boolean arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,double arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,byte arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,char arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,short arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,Object arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,int arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,float arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,boolean arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,double arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,byte arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,char arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,short arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(int arg0,long arg1,long arg2) {
        Object[] ret = {
            new Integer(arg0),new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,Object arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,int arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,float arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,boolean arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,double arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,byte arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,char arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,short arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,Object arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,int arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,float arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,boolean arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,double arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,byte arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,char arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,short arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(float arg0,long arg1,long arg2) {
        Object[] ret = {
            new Float(arg0),new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,Object arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,int arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,float arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,boolean arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,double arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,byte arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,char arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,short arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,Object arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,int arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,float arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,boolean arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,double arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,byte arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,char arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,short arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(boolean arg0,long arg1,long arg2) {
        Object[] ret = {
            new Boolean(arg0),new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,Object arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,int arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,float arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,boolean arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,double arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,byte arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,char arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,short arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,Object arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,int arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,float arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,boolean arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,double arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,byte arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,char arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,short arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(double arg0,long arg1,long arg2) {
        Object[] ret = {
            new Double(arg0),new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,Object arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,int arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,float arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,boolean arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,double arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,byte arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,char arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,short arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,Object arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,int arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,float arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,boolean arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,double arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,byte arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,char arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,short arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(byte arg0,long arg1,long arg2) {
        Object[] ret = {
            new Byte(arg0),new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,Object arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,int arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,float arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,boolean arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,double arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,byte arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,char arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,short arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,Object arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,int arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,float arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,boolean arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,double arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,byte arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,char arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,short arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(char arg0,long arg1,long arg2) {
        Object[] ret = {
            new Character(arg0),new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,Object arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,int arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,float arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,boolean arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,double arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,byte arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,char arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,short arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,Object arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,int arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,float arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,boolean arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,double arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,byte arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,char arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,short arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(short arg0,long arg1,long arg2) {
        Object[] ret = {
            new Short(arg0),new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),arg1,arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),arg1,new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),arg1,new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),arg1,new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),arg1,new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),arg1,new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),arg1,new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),arg1,new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,Object arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),arg1,new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,int arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),new Integer(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,float arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),new Float(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,boolean arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),new Boolean(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,double arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),new Double(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,byte arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),new Byte(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,char arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),new Character(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,short arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),new Short(arg1),new Long(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,Object arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),arg2 }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,int arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),new Integer(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,float arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),new Float(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,boolean arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),new Boolean(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,double arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),new Double(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,byte arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),new Byte(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,char arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),new Character(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,short arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),new Short(arg2) }
        ;
        return(ret);
    }

    /**{ method
        @name to
        @function - turn any collection of parameters into an Object Array
        @param arg0 - first argument
        @param arg2  - third argument
        @param arg1 - second argument
        @return - the array
    }*/ 
    public static Object[] to(long arg0,long arg1,long arg2) {
        Object[] ret = {
            new Long(arg0),new Long(arg1),new Long(arg2) }
        ;
        return(ret);
    }


//- ******************* 
//- End Class VArgs
}
