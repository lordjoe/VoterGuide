package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.IObjectEditorFactory
 *    interface to create a custom editor for a specific class
 * @author Steve Lewis
 * @date Jan 2, 2008
 */
public interface IObjectEditorFactory<T> {
    public static IObjectEditorFactory[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IObjectEditorFactory.class;

    /**
     * make a creator dialog
     *
     * @param emptyArray empty array to pass in type
     * @return creator dialog
     */
    public  <T> IObjectCreatorDialog<T> buildCreatorDialog(T[] emptyArray, String[] props);

    /**
     * make a creator dialog
     *
     * @param emptyArray empty array to pass in type
     * @return creator dialog
     */
    public  <T> IObjectCreatorDialog<T> buildCreatorDialog(T[] emptyArray, String[] props, Object... args);

    /**
     * make en editor dialog
     *
     * @param target non-null object to edit
     * @return editor dialog
     */
    public  <T> IObjectEditorDialog<T> buildEditorDialog(T target, String... properties);
}
