package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;

/**
 * com.lordjoe.ui.propertyeditor.ICollectionPanelBuilder
 *
 * @author Steve Lewis
 * @date Dec 19, 2007
 */
public interface ICollectionPanelBuilder <T> {
    public static ICollectionPanelBuilder[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ICollectionPanelBuilder.class;

    public  <T> ICollectionPropertyPanel<T> buildCollectionPropertyPanel(Object owner,
                                         ICollectionWrapper<T> pWrapper);
}
