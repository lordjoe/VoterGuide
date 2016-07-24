package com.lordjoe.lang;

/**
 * com.lordjoe.lang.IComposite
 *   interface for composite patters
 * delibartely designed not to
 * interfere with existing code
 * @author Steve Lewis
 * @date Nov 26, 2007
 */
public interface IComposite {
    public static IComposite[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IComposite.class;

    /**
     * return a parent
     * @return possibly null parent
     */
    public IComposite getParentObject();

    /**
     *    return children as an array
     * @return  non-null array
     */
    public IComposite[] getChildObjectItems();

    /**
     *    return number of children
     * @return  as above
     */
    public int getChildObjectCount();

    public void addCompositeChangeListener(CompositeChangeListener l);

    public void removeCompositeChangeListener(CompositeChangeListener l);

}
