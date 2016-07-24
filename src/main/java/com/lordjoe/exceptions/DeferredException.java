package com.lordjoe.exceptions;


/**
 * com.lordjoe.exceptions.DeferredException
 * simple class to defer excception handling as a runtime
 *  throw  DeferredException.deferHandling( ex); is the only ure
 * @author slewis
 * @date Jan 13, 2005
 */
public class DeferredException extends ChainedRuntimeException
{
    public static final Class THIS_CLASS = DeferredException.class;
    public static final DeferredException EMPTY_ARRAY[] = {};

    /**
     * use as follows
     * try { ... }
     * catch(Exception ex ) {
     *   throw DeferredException.deferHandling(ex);
     * }
     *
     * @param ex
     * @return
     */
    public static RuntimeException deferHandling(Throwable ex)
    {
        if(ex instanceof RuntimeException)
            return (RuntimeException)ex;
       return new DeferredException(ex);
    }

    private DeferredException(Throwable ex) {
        super(ex);
    }

}
