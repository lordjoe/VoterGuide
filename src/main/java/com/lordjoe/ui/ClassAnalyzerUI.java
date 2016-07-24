package com.lordjoe.ui;

import com.lordjoe.lang.*;
import com.lordjoe.lib.xml.*;
import com.lordjoe.ui.propertyeditor.*;

/**
 * com.lordjoe.ui.ClassAnalyzerUI
 *
 * @author Steve Lewis
 * @date Jul 6, 2009
 */
public class ClassAnalyzerUI
{
    public static ClassAnalyzerUI[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ClassAnalyzerUI.class;
    public static Object createSubObject(String collectionName, CompositeIdentifiedObject parent,
                                         Class ofClass)
    {

        String[] createProps = (String[]) ClassAnalyzer.getStaticField("CREATE_PROPERTIES",
                ofClass);
        Object[] emptyArray = (Object[]) ClassAnalyzer.getStaticField("EMPTY_ARRAY", ofClass);
        IObjectCreatorDialog sd = PropertyEditorFactory.buildCreatorDialog(emptyArray, createProps,
                parent);
        AbstractDataObject survey = (AbstractDataObject) sd.createObject();
        if (survey != null) {
            survey.clearDirty();
            parent.addCollectionMember(collectionName, survey);
            if (!survey.isCreateComplete()) {
                editObject(survey);
            }
        }
        return survey;
    }


    public static void editObject(AbstractDataObject site)
    {

        String[] editProps = (String[]) ClassAnalyzer.getStaticField("EDIT_PROPERTIES",
                site.getClass());
        IObjectEditorDialog sd = PropertyEditorFactory.buildEditorDialog(site,
                editProps);
        sd.editObject();
        return;

    }

}
