package com.lordjoe.ui;

import java.util.*;

/**
 * com.lordjoe.ui.LocaleChangeEvent
 *  notifies the application of a locale change
 * @author Steve Lewis
 * @date Mar 1, 2005
 */
public class LocaleChangeEvent
{
    public static final Class THIS_CLASS = LocaleChangeEvent.class;
    public static final LocaleChangeEvent[] EMPTY_ARRAY = {};

    private final Locale m_OldLocale;
    private final Locale m_NewLocale;
    public LocaleChangeEvent(Locale oldLoc,Locale newLoc)
    {
        m_OldLocale = oldLoc;
        m_NewLocale = newLoc;
    }

    public Locale getOldLocale()
    {
        return m_OldLocale;
    }

    public Locale getNewLocale()
    {
        return m_NewLocale;
    }

}

