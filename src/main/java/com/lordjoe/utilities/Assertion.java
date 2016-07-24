/**{ file
 @name Assertion.java
 @function * Assertion implements Assertion.validate which functions
 much like a C++ assert statement - this works with
 Deverloper.break which allows developers to trap and trace
 suspisious behavior
 @author> Steven Lewis
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
 @name Assertion
 @function declares static methods for assertions
 }*/
public class Assertion {


    private static ILogger g_Logger = null;

    //- *******************
    //- Methods
    /**{ method
     @name validate
     @function - call to validate that an expression is true
     @param expression must be true
     }*/
    static public final void validate(boolean expression) {
        if (!expression) {
            breakHere();
            Developer.halt("Assertion Failure");
        }
    }

    /**{ method
     @name fatalError
     @function call to say it is wrong to reach this spot in
     the code
     }*/
    static public final void fatalError() {
        fatalError("");
    }

    /**{ method
     @name fatalError
     @function call to say it is wrong to reach this spot in
     the code
     }*/
    static public final void fatalError(String why) {
        breakHere();
        Developer.halt("Assertion Fatal Error " + why);
    }

    /**{ method
     @name fatalException
     @function call say the exception caught is fatal - i.e. we
     do not expect it and have no clue how to handle it
     @param ex - the exception
     }*/
    static public final void fatalException(Exception ex) {
        breakHere();
        Developer.halt("Assertion Fatal Exception", ex);
    }

    /**{ method
     @name handleException
     @function call say the exception caught is handled - this call is
     used to document an empty catch block is proper and the
     code does the correct thing to catch and ignore
     @param ex - the exception
     }*/
    static public final void handleException(Exception ex) {
    }

    /**{ method
     @name ignoreException
     @function call say the exception caught is ignored - this call is
     used to document that the developer does not want to handle the
     exception at this time but inthe future may want to relook at the
     issue as opposed to handleException which says it is the developers
     intention not to execute code in resoinse to the exception
     @param ex - the exception
     }*/
    static public final void ignoreException(Exception ex) {
    }

    /**{ method
     @name unsupported
     @function call to say it the developer does not support calling
     this function or reaching this line of code. The error is fatal
     and is typically used in places where the developer wishes to force users
     to override an implementation but allows subclasses not to implement
     the function as long as they do not call it.
     }*/
    static public final void unsupported() {
        breakHere();
        Developer.halt("This call is not supported");
    }

    /**{ method
     @name fixThis
     @function call to say it the developer needs to work on the code
     typically added to the body of an imcomplete method required
     for some interface. The developers knows he must do something
     but wishes to delay and see when the method is called
     }*/
    static public final void fixThis() {
        breakHere();
        Developer.halt("Code needs to be fixed here");
    }

    /**{ method
     @name forgive
     @function call to say it the developer believes that eventually
     reaching this line (i.e. a catch block) should be a fatal error
     but that for now it is allowed. Probably it is a bad idea to leave
     these in the final shipping product
     }*/
    static public final void forgive() {
        Developer.report("Code needs to be fixed here");
    }

    /**{ method
     @name breakHere
     @function - does nothing but us a good place for a break point
     }*/
    static public final void breakHere() {
        int i = 0;
        i++;
    }

    /**
     * return a runnable which calls breakHere
     */
    public static Runnable getRunAndBreak() {
        return (gRunAndBreak);
    }

    protected static Runnable gRunAndBreak = new RunAndBreak();

    /**
     * Runnable which calls breakHere
     */
    public static class RunAndBreak implements Runnable {
        public void run() {
            breakHere();
        }
    }


//
// This is a nonoptimizable instruction
// which ay be used to set a break point

    /**{ method
     @name doNada
     @function - does nothing but us a good place for a break point
     }*/
    static public final void doNada() {
        int i = 0;
        i++;
    }

    /**{ method
     @name useArg
     @function - does nothing but forces the compiler
     not to optimize out a variable by passing it
     i.e. int foo = functionCall();
     Assertion.useArg(foo);
     forces the compiler not to optimize foo
     @param o possibly null object
     }*/
    static public final void useArg(Object o) {
        int i = 0;
        i++;
    }

    /**{ method
     @name useArg
     @function - does nothing but forces the compiler
     not to optimize out a variable by passing it
     @param o arbitrary value
     }*/
    static public final void useArg(int o) {
        int i = 0;
        i++;
    }

    /**{ method
     @name useArg
     @function - does nothing but forces the compiler
     not to optimize out a variable by passing it
     @param o - argument to use
     }*/
    static public final void useArg(boolean o) {
        int i = 0;
        i++;
    }

    /**{ method
     @name useArg
     @function - does nothing but forces the compiler
     not to optimize out a variable by passing it
     @param o - argument to use
     }*/
    static public final void useArg(double o) {
        int i = 0;
        i++;
    }

    /**{ method
     @name assume
     @function documentation form of validate where
     the expression is assumed to be true - i.e. argument non-null
     @param expression expression to test
     }*/
    static public void assume(boolean expression) {
        Assertion.validate(expression);
    }

    /**{ method
     @name precondition
     @function used to document a precondition - usually commented out
     after early development - left to document the code
     @param expression expression to test
     }*/
    static public void precondition(boolean expression) {
        Assertion.validate(expression);
    }

    /**{ method
     @name postcondition
     @function used to document a precondition - usually commented out
     after early development - left to document the code
     @param expression expression to test
     }*/
    static public void postcondition(boolean expression) {
        Assertion.validate(expression);
    }


    public static void setLogger(ILogger logger) {
        g_Logger = logger;
    }


    /**
     * this puts out an error message - later versions will allow
     * configuration of handling
     * @param in text to print
     */
    public static void logError(String in) {
        if (g_Logger != null)
            g_Logger.error(in);
        else
            System.err.println(in);
    }

    /**
     * this puts out an error message - later versions will allow
     * configuration of handling
     * @param in text to print
     */
    public static void logError(String in, Throwable e) {
        if (g_Logger != null)
            g_Logger.error(in, e);
        else {
            System.err.println(in);
            e.printStackTrace(System.err);
        }
    }

    /**
     * this puts out an debug message - later versions will allow
     * configuration of handling
     * @param in text to print
     */
    public static void logDebug(String in) {
        if (g_Logger != null)
            g_Logger.debug(in);
        else
            System.out.println(in);
    }


//- *******************
//- End Class Assertion
}
