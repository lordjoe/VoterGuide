package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.DefaultObjectEditorFactory
 *
 * @author Steve Lewis
 * @date Jan 2, 2008
 */
public class DefaultObjectEditorFactory<T> implements IObjectEditorFactory<T> {
    public static DefaultObjectEditorFactory[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = DefaultObjectEditorFactory.class;
    public static DefaultObjectEditorFactory INSTANCE = new DefaultObjectEditorFactory();

    protected DefaultObjectEditorFactory() {
    }

    public <T> IObjectCreatorDialog<T> buildCreatorDialog(T[] emptyArray, String[] props) {
        return new ReflectionPropertyCreator(emptyArray, props);
    }

    public <T> IObjectCreatorDialog<T> buildCreatorDialog(T[] emptyArray, String[] props, Object... args) {
        return new ReflectionPropertyCreator(emptyArray, props, args);
    }

    public <T> IObjectEditorDialog<T> buildEditorDialog(T target, String... properties) {
        return new ReflectionPropertyEditor(target, properties);
    }
}
