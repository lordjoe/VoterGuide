/**

 *

 * .
 *
 *
 *
 */

package com.lordjoe.exceptions;

/**
 * Title:        ChainedException
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:      Lordjoe

 */


/***************************************************************************************************
 *
 *  <!-- ChainedException -->
 *
 *  A chained, checked exception class.
 *                                                                                              <p>
 *  Useful chained exception reference:                                                         <br>
 *    http://developer.java.sun.com/developer/technicalArticles/Programming/exceptions2/                 <br>
 *    http://www.javaworld.com/javaworld/jw-09-2001/jw-0914-exceptions.html?                    <br>
 *                                                                                              <p>
 *  Note:  re chained exceptions and getMessage()
 *                                                                                              <p>
 *    <b>The getMessage() method of exception classes should NOT be relied upon
 *    as a mechanism for constructing error messages intended to be displayed
 *    directly to the end user.</b>  In Dave's opinion, this is antithetical to
 *    the idea of a chained exception.  A chained exception, by its very
 *    nature, is intended to allow a low-level exception to be bundled
 *    (chained) into successively higher level exceptions.  A low-level
 *    exception does not have the context to define a message that will
 *    be appropriate in all contexts in which it is used.  
 *                                                                                              <p>
 *    Take for example a low-level exception A.  In one case, A might be 
 *    chained with a higher-level exception B (A causes B), while, in another
 *    case, A might be chained with an exception  (A causes C) in a completely 
 *    different way.  In fact, A and  might end up chained with D (A 
 *    causes  which in turn causes D).  Such complex chaining leaves A 
 *    impotent to define a message that is appropriate in each context in 
 *    which it is used.  Rather than defining A.getMessage(), A should 
 *    instead store appropriate state information in a non-string form
 *    (sometimes the exception itself is sufficient and other times A might
 *    have data members defined to store additional information).  Then, when
 *    A is chained in various contexts (i.e., inside B or inside  and D), a 
 *    message can be constructed that utilizes the state information in A to
 *    generate a message that is appropriate for the ENTIRE CHAIN, not just 
 *    for a restricted scenario in which A exists in isolation.
 *                                                                                              <p>
 *    Suppose A is an exception related to serial communication; suppose  is
 *    a hybreader robot exception (RobotException extends HybreaderException) 
 *    and suppose D is a script exception.  In this example, a serial 
 *    communication failure can lead to a robot movement failure, which 
 *    in turn can lead to a script instruction failure.  That is, A can 
 *    cause  which then causes D.  Since D occurs when a script 
 *    instruction is executed, it might display a message like
 *                                                                                              <p>
 *      "There was a problem communicating to the robot while trying 
 *       to execute a "move arm" instruction at line 23 of the script."
 *                                                                                              <p>
 *    This message derives from state information that spans all 3 exceptions 
 *    in the chain!  In another context, A might occur in response to a 
 *    programmatic request rather than a script request.  In such a 
 *    situation A might NOT be chained with a script instruction (i.e., 
 *    not chained with D).  Hence, the message would be different.  Support 
 *    for varied messages in varied contexts precludes A from defining a 
 *    message for the end user.
 *                                                                                              <p>
 *    It is important to note that A can define a getMessage() for the 
 *    developer!!  It just shouldn't define one for the end user since 
 *    end-user error messages should be imbued with the richness of 
 *    information deriving from the ENTIRE chain, not just the low-level 
 *    exception.
 *                                                                                              <p>
 *    In sum, chained exceptions should NOT define "get message"-type API's 
 *    that are intended to be rendered directly to the end user.  This applies 
 *    regardless of the actual method name -- getMessage(), getUserMessage(), 
 *    getXxxMessage().
 *                                                                                              <p>
 *    TODO -- add more rationale regarding avoiding coupling business logic
 *            and presentation logic (serving a String message presumes the
 *            presentation of an error state...for example, what if you are on
 *            a handheld PDA device and the message needs to be short?  or,
 *            what if the error message is being transduced to the deaf?).
 *    TODO -- add more rationale regarding internationalization (serving a 
 *            String message might not be an optimal solution when supporting
 *            different languages)
 *
 ***************************************************************************************************
 */
public class ChainedException extends Exception
{
    private static final String LINE_SEP = System.getProperty("line.separator");
    private Throwable cause = null;

    public ChainedException()
    {
        super();
    }

    public ChainedException(final Throwable cause)
    {
        super();
        this.cause = cause;
    }

    public ChainedException(String message)
    {
        super(message);
    }

    public ChainedException(String message, final Throwable cause)
    {
        super(message);
        this.cause = cause;
    }

    public Throwable getCause()
    {
        java.rmi.RemoteException x;
        return cause;
    }

                    
                    // tempdt Dave's or Deepak's original code (per Sun's java.rmi.RemoteException)
                    //    public String getMessage()
                    //    {
                    //        if (cause == null)
                    //        {
                    //            return super.getMessage();
                    //        }
                    //        else
                    //        {
                    //            return super.getMessage() + "; nested exception is " + LINE_SEP + "\t" +
                    //                    cause.toString();
                    //        }
                    //    }
                    
                    
                    // tempdt Brooke's change
                        /**
                         * @todo tempdt
                         *   Discuss this change at some future date.  The intent of this
                         *   addition as I understand it was to allow for getMessage() to be
                         *   utilized to render messages directly to the user.  As discussed,
                         *   in this class' prologue comment, I believe this is not a good 
                         *   strategy.  I believe the original design of this class is preferred 
                         *   and would like to discuss returning to the original version at
                         *   some stage.
                         */
                        public String getMessageAndChain()
                        {
                            if (cause == null)
                            {
                                return super.getMessage();
                            }
                            else
                            {
                                return super.getMessage() + "; nested exception is " + LINE_SEP + "\t" +
                                        cause.toString();
                            }
                        }

    public void printStackTrace()
    {
        this.printStackTrace(System.err);
    }

    public void printStackTrace(java.io.PrintStream ps)
    {
        if(cause == null)
        {
            super.printStackTrace(ps);
        }
        else
        {
            synchronized (ps)
            {
                ps.println(this);
                cause.printStackTrace(ps);
            }
        }
    }

    public void printStackTrace(java.io.PrintWriter pw)
    {
        if(cause == null)
        {
            super.printStackTrace(pw);
        }
        else
        {
            synchronized(pw)
            {
                pw.println(this);
                cause.printStackTrace(pw);
            }
        }
    }

} // end ChainedException
