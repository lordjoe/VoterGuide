package com.lordjoe.ui;

/**
 * com.lordjoe.ui.IArrayDrawer
 *
 * @author slewis
 * @date Apr 26, 2005
 */
public interface IDataArrayDrawer extends IArrayDrawer,IDataChangedListener
{
    public static final Class THIS_CLASS = IDataArrayDrawer.class;
    public static final IDataArrayDrawer EMPTY_ARRAY[] = {};

    /**
     * return the underlying data
     * @return
     */
    public IArrayColorData getData();
    /**
     * add listener
     * @param listener non-null listener
     */
    public void addDataChangedListener(IDataChangedListener listener);

    /**
     * remove listener
     * @param listener non-null listener
     */
     public void removeDataChangedListener(IDataChangedListener listener);


}