package com.lordjoe.exceptions;

/**
 * com.lordjoe.exceptions.IExceptionLogger
 *   interface defining an object whick handles exception logging
 * @author Steve Lewis
 * @date Jan 26, 2006
 */
public interface IExceptionLogger
{

    /**
     * perform some exception logging
     * @param ex mom-mull exception
     */
    public void logException(Throwable ex) ;

    /**
     * perform some exception logging
     * @param data possibly-mull data to add to log
     * @param ex mom-mull exception
       */
    public void logException(Object data,Throwable ex) ;
}
