//
// Copyright, Eastfork Object Space (C) 1997.
//
// This code is provided as is without any warranty.
// Basically, you can do whatever you want with it,
// except charge money for it or pretend that you
// wrote it.
//
// Description: printf-like formatting for Java
//

/**{ file
    @name Format.java
    @function
         This class provides <code>printf</code>-like formatting.
         Example:
         <xmp>
         out.println(new Format("string: %s, int: %d, float: %2.2f").add("hello").add(123).add(Math.PI));
         </xmp>
         will generate:
         <xmp>
         string: hello, int: 123, float: 3.14
         </xmp>
    @author> Morten Hindsholm (mhi@eos.dk)
    @copyright> 
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************

    @date> Mon Jun 22 21:48:24 PDT 1998
}*/ 
package com.lordjoe.utilities;

/**{ class
    @name Format
    @function
         This class provides <code>printf</code>-like formatting.
         Example:
         <xmp>
         out.println(new Format("string: %s, int: %d, float: %2.2f").add("hello").add(123).add(Math.PI));
         </xmp>
         will generate:
         <xmp>
         string: hello, int: 123, float: 3.14
         </xmp>
    @author> Morten Hindsholm (mhi@eos.dk)
    @date> Mon Jun 22 21:48:24 PDT 1998
}*/ 
public class Format {

    //- ******************* 
    //- Fields 
// flags values

    /**{ field
        @name LEFT_ALIGN
        @function <Add Comment Here>
    }*/ 
    static final int LEFT_ALIGN = 1;

// -

    /**{ field
        @name SIGN_PREFIX
        @function <Add Comment Here>
    }*/ 
    static final int SIGN_PREFIX = 2;

// +

    /**{ field
        @name SPACE_PREFIX
        @function <Add Comment Here>
    }*/ 
    static final int SPACE_PREFIX = 4;

// 

    /**{ field
        @name ZERO_PAD
        @function <Add Comment Here>
    }*/ 
    static final int ZERO_PAD = 8;

// 0  Not supported

    /**{ field
        @name ALTERNATE
        @function <Add Comment Here>
    }*/ 
    static final int ALTERNATE = 16;

// #
// type values

    /**{ field
        @name UNKNOWN
        @function <Add Comment Here>
    }*/ 
    static final int UNKNOWN = 0;

    /**{ field
        @name CHARACTER
        @function <Add Comment Here>
    }*/ 
    static final int CHARACTER = 1;

    /**{ field
        @name DECIMAL
        @function <Add Comment Here>
    }*/ 
    static final int DECIMAL = 2;

    /**{ field
        @name FLOAT
        @function <Add Comment Here>
    }*/ 
    static final int FLOAT = 3;

    /**{ field
        @name LCHEX
        @function <Add Comment Here>
    }*/ 
    static final int LCHEX = 4;

    /**{ field
        @name UCHEX
        @function <Add Comment Here>
    }*/ 
    static final int UCHEX = 5;

    /**{ field
        @name STRING
        @function <Add Comment Here>
    }*/ 
    static final int STRING = 6;

    /**{ field
        @name fmt
        @function <Add Comment Here>
    }*/ 
    char[] fmt;

// format string

    /**{ field
        @name fmtLen
        @function <Add Comment Here>
    }*/ 
    int fmtLen;

// format length

    /**{ field
        @name idx
        @function <Add Comment Here>
    }*/ 
    int idx;

// index into fmt

    /**{ field
        @name flags
        @function <Add Comment Here>
    }*/ 
    int flags;

// formatting flags

    /**{ field
        @name type
        @function <Add Comment Here>
    }*/ 
    int type;

// field type

    /**{ field
        @name width
        @function <Add Comment Here>
    }*/ 
    int width;

// field with

    /**{ field
        @name precision
        @function <Add Comment Here>
    }*/ 
    int precision;

// precision

    /**{ field
        @name result
        @function <Add Comment Here>
    }*/ 
    StringBuffer result;


    //- ******************* 
    //- Methods 
// result of formatting

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString) {
        return(fmtString);
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,Object arg1) {
        Format f = new Format(fmtString);
        f.add(arg1);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,int arg1) {
        Format f = new Format(fmtString);
        f.add(arg1);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,double arg1) {
        Format f = new Format(fmtString);
        f.add(arg1);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,boolean arg1) {
        Format f = new Format(fmtString);
        f.add(new Boolean(arg1));
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg2 <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,double arg1,double arg2) {
        Format f = new Format(fmtString);
        f.add(arg1);
        f.add(arg2);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg2 <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,int arg1,double arg2) {
        Format f = new Format(fmtString);
        f.add(arg1);
        f.add(arg2);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg2 <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,int arg1,int arg2) {
        Format f = new Format(fmtString);
        f.add(arg1);
        f.add(arg2);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg2 <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,Object arg1,Object arg2) {
        Format f = new Format(fmtString);
        f.add(arg1);
        f.add(arg2);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param arg3 <Add Comment Here>
        @param arg2 <Add Comment Here>
        @param arg1 <Add Comment Here>
        @param fmtString <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,Object arg1,Object arg2,Object arg3) {
        Format f = new Format(fmtString);
        f.add(arg1);
        f.add(arg2);
        f.add(arg3);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg2 <Add Comment Here>
        @param arg3 <Add Comment Here>
        @param arg4 <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,Object arg1,Object arg2,Object arg3,Object arg4) {
        Format f = new Format(fmtString);
        f.add(arg1);
        f.add(arg2);
        f.add(arg3);
        f.add(arg4);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg2 <Add Comment Here>
        @param arg3 <Add Comment Here>
        @param arg4 <Add Comment Here>
        @param arg1 <Add Comment Here>
        @param arg5 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,Object arg1,Object arg2,Object arg3,Object arg4,Object arg5) {
        Format f = new Format(fmtString);
        f.add(arg1);
        f.add(arg2);
        f.add(arg3);
        f.add(arg4);
        f.add(arg5);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param arg2 <Add Comment Here>
        @param arg3 <Add Comment Here>
        @param arg4 <Add Comment Here>
        @param arg6 <Add Comment Here>
        @param arg5 <Add Comment Here>
        @param arg1 <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,Object arg1,Object arg2,Object arg3,Object arg4,Object arg5,Object arg6) {
        Format f = new Format(fmtString);
        f.add(arg1);
        f.add(arg2);
        f.add(arg3);
        f.add(arg4);
        f.add(arg5);
        f.add(arg6);
        return(f.toString());
    }

    /**{ method
        @name sprintf
        @function <Add Comment Here>
        @param fmtString <Add Comment Here>
        @param args <Add Comment Here>
        @return <Add Comment Here>
    }*/ 
    public static String sprintf(String fmtString,Object[] args) {
        Format f = new Format(fmtString);
        for(int i = 0; i < args.length; i ++) 
            f = f.add(args[i]);
        return(f.toString());
    }

    /**{ constructor
        @name Format
        @function Constructor of Format
        @param fmtString the format specification in printf style
    }*/ 
    public Format(String fmtString) {
        fmtLen = fmtString.length();
        // put format string into char array for faster access
        fmt = new char[fmtLen];
        fmtString.getChars(0,fmtLen,fmt,0);
        //Developer.report("Format string = |" + fmtString + "|");
        reset();
}

    /**{ method
        @name reset
        @function
             Reset the format so it's ready for reuse.
        @policy <Add Comment Here>
    }*/ 
    public void reset() {
        result = new StringBuffer(fmtLen);
        idx = 0;
        getNextField();
    }

    /**{ method
        @name toString
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public String toString() {
        return result.toString();
    }

    /**{ method
        @name add
        @function
             Add a long as the current field.
             The current field must be of a type that is compatible with long (%d, %x, %X or %c).
        @param i the long to add
        @return this
        @policy <Add Comment Here>
    }*/ 
    public Format add(long i) {
        String field = null;
        switch(type) {
            case CHARACTER :
            result.append((char) i);
            break;
            case DECIMAL :
            if(i > 0) {
                // positive number -- add prefix if necessary
                if((flags & SIGN_PREFIX) > 0) {
                    field = "+" + Long.toString(i,10);
                    break;
                }
                if((flags & SPACE_PREFIX) > 0) {
                    field = " " + Long.toString(i,10);
                    break;
                }
            }
            field = Long.toString(i,10);
            break;
            case LCHEX :
            case UCHEX :
            if((flags & ALTERNATE) > 0) {
                field = "0x" + Long.toString(i,16);
            }
            else {
                field = Long.toString(i,16);
            }
            if(type == UCHEX) {
                field = field.toUpperCase();
            }
            break;
            default :
            throw new IllegalArgumentException("Format type and add() type dont match");
        }
        if(field != null) {
            int len = field.length();
            if(len < width) {
                int missing = width - len;
                if((flags & LEFT_ALIGN) > 0) {
                    // right-pad with spaces
                    padRight(field,' ',missing);
                }
                else {
                    // left-pad with spaces
                    padLeft(field,((flags & ZERO_PAD) > 0) ? '0' :
                    ' ',missing);
                }
            }
            else {
                // no padding needed
                result.append(field);
            }
        }
        getNextField();
        return this;
    }

    /**{ method
        @name add
        @function
             Add a double as the current field.
             The current field must be of a type that is compatible with double (%f).
        @param d the double to add
        @return this
        @policy <Add Comment Here>
    }*/ 
    public Format add(double d) {
        String field = null;
        switch(type) {
            case FLOAT :
            if(d > 0.0) {
                // positive number -- add prefix if necessary
                if((flags & SIGN_PREFIX) > 0) {
                    field = "+" + Double.toString(d);
                    break;
                }
                if((flags & SPACE_PREFIX) > 0) {
                    field = " " + Double.toString(d);
                    break;
                }
            }
            field = Double.toString(d);
            break;
            case DECIMAL :
            case LCHEX :
            case UCHEX :
            add((int) d);
            default :
            throw new IllegalArgumentException("Format type and add() type dont match");
        }
        if(field != null) {
            int len = field.length();
            int fraction_len = 0;
            int point;
            if((point = field.indexOf('.')) > 0) {
                fraction_len = len - point - 1;
            }
            if(precision >= 0 && fraction_len > precision) {
                len -= fraction_len - precision;
                field = field.substring(0,len);
            }
            if(len < width) {
                int missing = width - len;
                if((flags & LEFT_ALIGN) > 0) {
                    // right-pad with spaces
                    padRight(field,' ',missing);
                }
                else {
                    // left-pad with spaces. NB: zeros not supported
                    padLeft(field,' ',missing);
                }
            }
            else {
                // no padding needed
                result.append(field);
            }
        }
        getNextField();
        return this;
    }

    /**{ method
        @name add
        @function
             Add a Number as the current field.
             The current field must be of a type that is compatible with Number
             or one of its derivatives.
        @UnusedParam> d the Number to add
        @param n <Add Comment Here>
        @return this
        @policy <Add Comment Here>
    }*/ 
    public Format add(Number n) {
        switch(type) {
            case CHARACTER :
            case DECIMAL :
            case LCHEX :
            case UCHEX :
            return add(n.longValue());
            case FLOAT :
            return add(n.doubleValue());
            default :
            throw new IllegalArgumentException("Format type and add() type dont match");
        }
    }

    /**{ method
        @name add
        @function
             Add a String as the current field.
             The current field must be of string type (%s).
        @param s the long to add
        @return this
        @policy <Add Comment Here>
    }*/ 
    public Format add(String s) {
        String field = null;
        switch(type) {
            case STRING :
            field = s;
            break;
            default :
            throw new IllegalArgumentException("Format type and add() type dont match");
        }
        if(field != null) {
            int len = field.length();
            if(len < width) {
                int missing = width - len;
                if((flags & LEFT_ALIGN) > 0) {
                    // right-pad with spaces
                    padRight(field,' ',missing);
                }
                else {
                    // left-pad with spaces
                    padLeft(field,' ',missing);
                }
            }
            
                else if(precision > 0 && len > precision) {
                // chop
                result.append(field.substring(0,precision));
            }
            else {
                // no padding needed
                result.append(field);
            }
        }
        getNextField();
        return this;
    }

    /**{ method
        @name add
        @function
             Add a String as the current field.
             The current field must be of string type (%s).
        @param s the long to add
        @return this
        @policy <Add Comment Here>
    }*/ 
    public Format add(Object s) {
        if(s instanceof String) {
            return(add((String) s));
        }
        if(s instanceof Number) {
            return(add((Number) s));
        }
        return(add(s.toString()));
    }

// Utility methods
// Insert 'str' into output with 'num' 'pad' chars to the left

    /**{ method
        @name padLeft
        @function <Add Comment Here>
        @param str <Add Comment Here>
        @param num <Add Comment Here>
        @param pad <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    void padLeft(String str,char pad,int num) {
        for(int i = 0; i < num; ++ i) {
            result.append(pad);
        }
        result.append(str);
    }

// Insert 'str' into output with 'num' 'pad' chars to the right

    /**{ method
        @name padRight
        @function <Add Comment Here>
        @param str <Add Comment Here>
        @param num <Add Comment Here>
        @param pad <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    void padRight(String str,char pad,int num) {
        result.append(str);
        for(int i = 0; i < num; ++ i) {
            result.append(pad);
        }
    }

// Parse the format specification up to the next field

    /**{ method
        @name getNextField
        @function <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    void getNextField() {
        for(; idx < fmtLen; ++ idx) {
            char ch = fmt[idx];
            if(ch == '%' && idx < fmtLen - 1) {
                ch = fmt[++ idx];
                if(ch != '%') {
                    readField();
                    return;
                }
            }
            result.append(ch);
        }
    }

// Parse the next field in the format specification

    /**{ method
        @name readField
        @function <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    void readField() {
        // initialize state
        flags = 0;
        type = UNKNOWN;
        width = 0;
        precision = - 1;
        char ch = 0;
        // flags
        for(; idx < fmtLen; ++ idx) {
            ch = fmt[idx];
            if(! setFlag(ch)) {
                break;
            }
        }
        // width
        while(idx < fmtLen && ch >= '0' && ch <= '9') {
            width = 10 * width +(ch - '0');
            ch = fmt[++ idx];
        }
        // precision
        if(idx < fmtLen && ch == '.') {
            precision = 0;
            ch = fmt[++ idx];
            while(idx < fmtLen && ch >= '0' && ch <= '9') {
                precision = 10 * precision +(ch - '0');
                ch = fmt[++ idx];
            }
        }
        // type
        switch(ch) {
            case 'c' :
            type = CHARACTER;
            break;
            case 'd' :
            type = DECIMAL;
            break;
            case 'f' :
            type = FLOAT;
            break;
            case 's' :
            type = STRING;
            break;
            case 'X' :
            type = UCHEX;
            break;
            case 'x' :
            type = LCHEX;
            break;
            default :
            throw new IllegalArgumentException("Unsupported format type: " + ch);
        }
        // ready for next character
        ++ idx;
    }

// Set the flag according to flag character 'ch'
// Return true if 'ch' was a flag char, false otherwise

    /**{ method
        @name setFlag
        @function <Add Comment Here>
        @param ch <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    boolean setFlag(char ch) {
        switch(ch) {
            case '-' :
            flags |= LEFT_ALIGN;
            return true;
            case '+' :
            flags |= SIGN_PREFIX;
            return true;
            case ' ' :
            flags |= SPACE_PREFIX;
            return true;
            case '0' :
            flags |= ZERO_PAD;
            return true;
            case '#' :
            flags |= ALTERNATE;
            return true;
        }
        return false;
    }

// Test program

    /**{ method
        @name main
        @function <Add Comment Here>
        @param args <Add Comment Here>
    }*/ 
    public static void main(String[] args) {
        Assertion.logDebug("Integers");
        Format fmt = new Format("%% >%2d< >%#10x< >%-010d< >%c<");
        Assertion.logDebug(fmt.add(123).add(456).add(new Integer(789)).add(65).toString());
        fmt.reset();
        Assertion.logDebug(fmt.add(321).add(654).add(new Integer(987)).add(66).toString());
        Assertion.logDebug("Floats");
        Assertion.logDebug(new Format("%% >%.2f< >%f< >%5.0f<").add(Math.PI).add(Math.PI).add(Math.PI).toString());
        Assertion.logDebug("Strings");
        Assertion.logDebug(new Format("%% >%2s< >%-10s< >%3.3s<").add("ABCD").add("EFGH").add("IJKL").toString());
    }


//- ******************* 
//- End Class Format
}
