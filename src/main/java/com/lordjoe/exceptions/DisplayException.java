package com.lordjoe.exceptions;

import java.util.*;

/**
 * com.lordjoe.exceptions.DisplayException
 *  Base class for all display errors
 * @author slewis
 * @date Dec 9, 2004
 */
public class DisplayException extends ChainedRuntimeException
{
    public static final Class THIS_CLASS = DisplayException.class;
    public static final DisplayException EMPTY_ARRAY[] = {};

    private final Date m_Time;
    public DisplayException() { super(); m_Time = new Date();}
    public DisplayException(String s) { super(s); m_Time = new Date(); }
    public DisplayException(Throwable ex) { super(ex); m_Time = new Date(); }
    public DisplayException(final String message, final Throwable cause)
    {
        super(message, cause);
         m_Time = new Date();
    }
 }
