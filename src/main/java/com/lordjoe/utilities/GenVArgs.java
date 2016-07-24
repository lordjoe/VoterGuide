/**{ file
    @name GenVArgs.java
    @function
         This generates all the permutations for the VArgs class
         need not ship but can ba useful
    @copyright> 
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************

    @date> Mon Jun 22 21:48:24 PDT 1998
}*/ 
package com.lordjoe.utilities;
import java.io.*;
/**{ class
    @name GenVArgs
    @function
         This generates all the permutations for the VArgs class
         need not ship but can ba useful
    @date> Mon Jun 22 21:48:24 PDT 1998
}*/ 
public class GenVArgs {

    //- ******************* 
    //- Fields 
    /**{ field
        @name gTypes
        @function <Add Comment Here>
    }*/ 
    public final static String[] gTypes = {
        "Object","int","float","boolean","double","byte","char","short","long" }
    ;

    /**{ field
        @name gWrapperTypes
        @function <Add Comment Here>
    }*/ 
    public final static String[] gWrapperTypes = {
        "","Integer","Float","Boolean","Double","Byte","Character","Short","Long" }
    ;

// all permutations covered to this level

    /**{ field
        @name MAX_ARGUMENTS
        @function <Add Comment Here>
    }*/ 
    public final static int MAX_ARGUMENTS = 4;


    //- ******************* 
    //- Methods 
    /**{ method
        @name getVArgsCode
        @function <Add Comment Here>
        @param FileName <Add Comment Here>
    }*/ 
    public static void getVArgsCode(String FileName) {
        PrintWriter out = FileUtilities.openPrintWriter(FileName);
        getVArgsCode(out);
    }

    /**{ method
        @name getVArgsCode
        @function <Add Comment Here>
        @param out <Add Comment Here>
    }*/ 
    public static void getVArgsCode(PrintWriter out) {
        getVArgsCodeHeader(out);
        getVArgsCodeBody(out,MAX_ARGUMENTS);
        // allcases up to 4 args
        getVArgsCodeTailer(out);
        out.flush();
        out.close();
    }

    /**{ method
        @name getVArgsCodeBody
        @function <Add Comment Here>
        @param out <Add Comment Here>
        @param n <Add Comment Here>
    }*/ 
    public static void getVArgsCodeBody(PrintWriter out,int n) {
        int[] permutation = new int[MAX_ARGUMENTS];
        for(int i = 1; i < n; i ++) 
            getVArgsCodeBody(out,permutation,0,i);
    }

    /**{ method
        @name getVArgsCodeBody
        @function <Add Comment Here>
        @param out <Add Comment Here>
        @param index <Add Comment Here>
        @param n <Add Comment Here>
        @param permutation <Add Comment Here>
    }*/ 
    public static void getVArgsCodeBody(PrintWriter out,int[] permutation,int index,int n) {
        if(index == n) {
            genenerateCodeBody(out,permutation,n);
        }
        else {
            for(int i = 0; i < gTypes.length; i ++) {
                permutation[index] = i;
                getVArgsCodeBody(out,permutation,index + 1,n);
            }
        }
    }

    /**{ method
        @name genenerateCodeBody
        @function <Add Comment Here>
        @param out <Add Comment Here>
        @param n <Add Comment Here>
        @param permutation <Add Comment Here>
    }*/ 
    public static void genenerateCodeBody(PrintWriter out,int[] permutation,int n) {
        out.print("    public static Object[] to(");
        int i;
        for(i = 0; i < n; i ++) {
            out.print(gTypes[permutation[i]] + " " + "arg" + i);
            if(i < n - 1) {
                out.print(",");
            }
            else {
                out.println(") {");
            }
        }
        out.print("        Object[] ret = { ");
        for(i = 0; i < n; i ++) {
            int type = permutation[i];
            if(type == 0) {
                out.print("arg" + i);
            }
            else {
                out.print("new " + gWrapperTypes[type] + "(");
                out.print("arg" + i + ")");
            }
            if(i < n - 1) {
                out.print(",");
            }
            else {
                out.println("};");
            }
        }
        out.println("       return(ret);");
        out.println("    }");
    }

    /**{ method
        @name getVArgsCodeHeader
        @function <Add Comment Here>
        @param out <Add Comment Here>
    }*/ 
    public static void getVArgsCodeHeader(PrintWriter out) {
        out.println("package com.lordjoe.Utilities;");
        out.println("/**");
        out.println("* This class converts many forms of argument into an array of objects");
        out.println("* works really will with up to 4 arguments");
        out.println("**/");
        out.println("abstract public class VArgs extends Nulleton {");
        out.println("    public static Object[] cat(Object[] arg1,Object[] arg2) {");
        out.println("        if(arg1 == null) return(arg2);");
        out.println("        if(arg2 == null) return(arg1);");
        out.println("        Object[] ret = new Object[arg1.length + arg2.length];");
        out.println("        System.arraycopy(arg1,0,ret,0,arg1.length);");
        out.println("        System.arraycopy(arg2,0,ret,arg1.length,arg2.length);");
        out.println("        return(ret);");
        out.println("    }");
    }

    /**{ method
        @name getVArgsCodeTailer
        @function <Add Comment Here>
        @param out <Add Comment Here>
    }*/ 
    public static void getVArgsCodeTailer(PrintWriter out) {
        out.println("}");
    }


//- ******************* 
//- End Class GenVArgs
}
