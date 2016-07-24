package com.lordjoe.ui;

/**
 * com.lordjoe.ui.LocaleChangeListener
 *
 * @author Steve Lewis
 * @date Mar 1, 2005
 */
public interface LocaleChangeListener
{
    public static final Class THIS_CLASS = LocaleChangeListener.class;
    public static final LocaleChangeListener[] EMPTY_ARRAY = {};

    /**
     * reset based on a new locale
     * @param evt  non-null event
     */
    public void onLocaleChange(LocaleChangeEvent evt);
}
