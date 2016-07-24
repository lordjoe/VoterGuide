package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;
import com.lordjoe.utilities.*;
import com.lordjoe.lib.xml.*;
import com.jgoodies.forms.layout.*;

import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.sql.*;

/**
 * com.lordjoe.ui.propertyeditor.PropertyEditorFactory
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public abstract class PropertyEditorFactory {
    public static PropertyEditorFactory[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = PropertyEditorFactory.class;

    private static JFrame gMainFrame;

    public static JFrame getMainFrame() {
        return gMainFrame;
    }

    public static void setMainFrame(JFrame pMainFrame) {
        gMainFrame = pMainFrame;
    }

    private static Map<Class, IObjectEditorFactory> gRegisteredEditorFactories =
            new HashMap<Class, IObjectEditorFactory>();

    public static void registerEditorFactory(Class cls, IObjectEditorFactory ed) {
        gRegisteredEditorFactories.put(cls, ed);
    }

    /**
     * simple lookup
     *
     * @param cls non-null test class
     * @return possibly null editor
     */
    public static IObjectEditorFactory getRegisteredEditorFactory(Class cls) {
        return gRegisteredEditorFactories.get(cls);
    }


    private static Map<ClassCollection, ICollectionEditorGenerator> gRegisteredCollectionEditors =
            new HashMap<ClassCollection, ICollectionEditorGenerator>();

    public static void registerCollectionEditor(ClassCollection cls, ICollectionEditorGenerator ed) {
        gRegisteredCollectionEditors.put(cls, ed);
    }

    /**
     * simple lookup
     *
     * @param cls non-null test class
     * @return possibly null editor
     */
    public static ICollectionEditorGenerator getRegisteredCollectionEditor(ClassCollection cls) {
        return gRegisteredCollectionEditors.get(cls);
    }

    /**
     * more forgiving lookup
     *
     * @param cls non-null test class
     * @return possibly null editor
     */
    public static ICollectionEditorGenerator findRegisteredCollectionEditor(ClassCollection cls) {
        for (ClassCollection test : gRegisteredCollectionEditors.keySet()) {
            if (test.equals(cls))
                return gRegisteredCollectionEditors.get(test);
        }
        return null;
    }


    private static Map<Class, IPropertyEditorGenerator> gRegisteredPropertyEditors =
            new HashMap<Class, IPropertyEditorGenerator>();

    public static void registerPropertyEditor(Class cls, IPropertyEditorGenerator ed) {
        gRegisteredPropertyEditors.put(cls, ed);
    }

    /**
     * simple lookup
     *
     * @param cls non-null test class
     * @return possibly null editor
     */
    public static IPropertyEditorGenerator getRegisteredPropertyEditor(Class cls) {
        return gRegisteredPropertyEditors.get(cls);
    }

    /**
     * more forgiving lookup
     *
     * @param cls non-null test class
     * @return possibly null editor
     */
    public static IPropertyEditorGenerator findRegisteredPropertyEditor(Class cls) {
        for (Class test : gRegisteredPropertyEditors.keySet()) {
            if (test.isAssignableFrom(cls))
                return gRegisteredPropertyEditors.get(test);
        }
        return null;
    }


    public static IStylizer buildDefaultStylizer() {
        return new FormStylizer(getDefaultFormLayout());
    }

    public static FormLayout getDefaultFormLayout() {
        FormLayout layout = buildLayout1();
        return layout;
    }

    private static FormLayout buildLayout1() {
        FormLayout layout = new FormLayout("5dlu , pref,  4dlu, 300dlu, 4dlu, pref,5dlu", // columns
                "5dlu ,pref, 2dlu,  " +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                        " pref, 2dlu, pref, 2dlu, pref, 5dlu"); // rows
        layout.setRowGroups(new int[][]{{1, 3, 5}});
        return layout;
    }

    /**
     * make a creator dialog
     *
     * @param emptyArray empty array to pass in type
     * @return creator dialog
     */
    public static <T> IObjectCreatorDialog<T> buildCreatorDialog(T[] emptyArray, String[] props) {
        IObjectEditorFactory factory = getRegisteredEditorFactory(emptyArray.getClass().getComponentType());
        if (factory == null)
            factory = DefaultObjectEditorFactory.INSTANCE;
        return factory.buildCreatorDialog(emptyArray, props);
    }

    /**
     * make a creator dialog
     *
     * @param emptyArray empty array to pass in type
     * @return creator dialog
     */
    public static <T> IObjectCreatorDialog<T> buildCreatorDialog(T[] emptyArray, String[] props, Object... args) {
        IObjectEditorFactory factory = getRegisteredEditorFactory(emptyArray.getClass().getComponentType());
        if (factory == null)
            factory = DefaultObjectEditorFactory.INSTANCE;
        return factory.buildCreatorDialog(emptyArray, props, args);
    }

    /**
     * make en editor dialog
     *
     * @param target non-null object to edit
     * @return editor dialog
     */
    public static <T> IObjectEditorDialog<T> buildEditorDialog(T target, String... properties) {
        IObjectEditorFactory factory = getRegisteredEditorFactory(target.getClass());
        if (factory == null)
            factory = DefaultObjectEditorFactory.INSTANCE;
        return factory.buildEditorDialog(target, properties);
    }

    public static IComponentPropertyEditor buildPropertyEditor(IPropertyWrapper p) {
        String propName = p.getPropertyName();
        if(propName.equalsIgnoreCase("name"))
            Util.breakHere();
        Class cls = p.getPropertyClass();
        IPropertyEditorGenerator ret = getRegisteredPropertyEditor(cls);
        if (ret != null)
            return ret.buildEditor(p);

        if (String.class == cls) {
            return new StringReflectionPropertyEditor(p);
        }
        if (Double.class.isAssignableFrom(cls)) {
            return new DoubleReflectionPropertyEditor(p);
        }
        if (Integer.class.isAssignableFrom(cls)) {
            return new IntegerReflectionPropertyEditor(p);
        }
        if (cls == Boolean.TYPE || Boolean.class.isAssignableFrom(cls)) {
            return new BooleanReflectionPropertyEditor(p);
        }
        if (Timestamp.class.isAssignableFrom(cls)) {
            return new TimeStampReflectionPropertyEditor(p);
        }
        if (Date.class.isAssignableFrom(cls)) {
            return new DateReflectionPropertyEditor(p);
        }
        if (PseudoEnum.class.isAssignableFrom(cls)) {
            return new SelectionReflectionPropertyEditor(p);
        }
        if (p.getOptions() != null) {
            return new SelectionReflectionPropertyEditor(p);
        }
        ret = findRegisteredPropertyEditor(cls);
        if (ret != null)
            return ret.buildEditor(p);
        throw new UnsupportedOperationException("Cannot build relection editor for " + cls);

    }

    /**
     * given a collection return a suitable editor
     *
     * @param p non-null collection wrapper
     * @return non-null editor
     */
    public static IGenericPropertyEditor buildCollectionEditor(ICollectionWrapper p) {
        IGenericPropertyEditor ret;
        if (p instanceof ReflectionCollectionWrapper) {
            ReflectionCollectionWrapper rp = (ReflectionCollectionWrapper) p;
            ICollectionEditorGenerator ed = findRegisteredCollectionEditor(rp.getCollection());
            if (ed != null)
                return ed.buildEditor(p);
        }

        ret = new CollectionReflectionPropertyEditor(p);
        return ret;
    }

}
