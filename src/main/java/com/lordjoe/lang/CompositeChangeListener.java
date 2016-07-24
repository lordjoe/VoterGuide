package com.lordjoe.lang;

/**
 * com.lordjoe.lang.CompositeChangeListener
 *   notification of composite pattern changes
 * @author Steve Lewis
 * @date Nov 26, 2007
 */
public interface CompositeChangeListener {
    public static CompositeChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CompositeChangeListener.class;

    /**
     * notification of a child being added
      * @param parent non-null parent
     * @param child non-null child
     */
    public void onAddChild(IComposite parent, IComposite child);

    /**
     * notification of a child being removec
      * @param parent non-null parent
     * @param child non-null child
     */
    public void onRemoveChild(IComposite parent, IComposite child);
    /**
     * notification removal of all children
      * @param parent non-null parent
     */
    public void onClearChildren(IComposite parent);

    /**
     * notification of bulk add
      * @param parent non-null parent
     * @param child non-null array of added children
     */
    public void onAddChildren(IComposite parent, IComposite[] child);

    public static enum ChangeTypes {
        clear,add,remove,bulkAdd;
    }
}
