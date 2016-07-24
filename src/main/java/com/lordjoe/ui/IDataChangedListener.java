package com.lordjoe.ui;

/**
 * com.lordjoe.ui.IDataChangedListener
 * a fairly non specific notifiaction interface ised by
 * I
 * @author slewis
 * @date Apr 26, 2005
 */
public interface IDataChangedListener
{
    public static final Class THIS_CLASS = IDataChangedListener.class;
    public static final IDataChangedListener EMPTY_ARRAY[] = {};

    /**
     * called before drawing is started
     */
    public void onDataChanged(Object obj);


}