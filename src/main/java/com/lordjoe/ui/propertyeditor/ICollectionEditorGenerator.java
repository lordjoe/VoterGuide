package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;

/**
 * com.lordjoe.ui.propertyeditor.ICollectionEditorGenerator
 *   factory for collection editors
 * @author Steve Lewis
 * @date Dec 15, 2007
 */
public interface ICollectionEditorGenerator {
    public static ICollectionEditorGenerator[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ICollectionEditorGenerator.class;

    /**
     * this implementation says to nothing
     */
    public static final ICollectionEditorGenerator NULL_GENERATOR =
            new NullCollectionEditorGenerator();

    /**
     * make a suitable property edotir
     * @param p  non-null property
     * @return  non-null editor
     */
    public IGenericPropertyEditor buildEditor(ICollectionWrapper p);

    public static class NullCollectionEditorGenerator implements  ICollectionEditorGenerator
    {
        private NullCollectionEditorGenerator() {}
        public IGenericPropertyEditor buildEditor(ICollectionWrapper p) {
            return null;
        }
    }
}