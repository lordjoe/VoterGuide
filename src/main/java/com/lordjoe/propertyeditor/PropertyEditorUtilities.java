package com.lordjoe.propertyeditor;

import com.lordjoe.lib.xml.*;
import com.lordjoe.utilities.*;
import com.lordjoe.ui.propertyeditor.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * com.lordjoe.propertyeditor.PropertyEditorUtilities
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public abstract class PropertyEditorUtilities {
    public static PropertyEditorUtilities[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = PropertyEditorUtilities.class;

      
    private static Map<Class, Object[]> EMPTY_ARRAYS = new ConcurrentHashMap<Class, Object[]>();

    public static Object[] getEmptyArray(Class c) {
        if(c.isPrimitive())
            c = ClassUtilities.primitiveToWrapper(c);
        Object[] ret = EMPTY_ARRAYS.get(c);
        try {
            if (ret == null) {
                ret = (Object[]) Array.newInstance(c, 0);
                EMPTY_ARRAYS.put(c, ret);
            }
        } catch (RuntimeException e) {
            throw e;
        }
        return ret;
    }


    private PropertyEditorUtilities() {
    }

    /**
     * given a proposed set of changes to an object's properties return the
     * new value of a property
     * @param target  non-null target object
     * @param property  non-null property name
     * @param proposedChanges  map or name values representing new properties
     * @return
     */
    public static Object getProposedValue(Object target, String property, Map<String,Object> proposedChanges)
    {
        Object ret = proposedChanges.get(property);
        if(ret != null)
            return ret;
        return ClassAnalyzer.getProperty(target,property);
    }

    public static IGenericWrapper[] buildPropertyWrappers(Object o) {
        String[] propNames = ClassAnalyzer.getAllProperties(o.getClass());
        return buildPropertyWrappers(o, propNames);
    }

    /**
     * turn a list of IPropertyWrapper into a map to appow lookup by name
     *
     * @param warppers
     * @return
     */
    public static Map<String, IGenericWrapper> buildNameToPropertyMap(IGenericWrapper[] warppers) {
        Map<String, IGenericWrapper> ret = new HashMap<String, IGenericWrapper>();
        for (int i = 0; i < warppers.length; i++) {
            IGenericWrapper warpper = warppers[i];
            String propertyName = null;
            if(warpper instanceof ICollectionWrapper) {
                propertyName = ((ICollectionWrapper)warpper).getCollectionName();
            }
            else {
              propertyName = ((IPropertyWrapper)warpper).getPropertyName();
            }
            ret.put(propertyName, warpper);
        }
        return ret;
    }

    public static IGenericWrapper[] buildPropertyWrappers(Object o, String[] useProperties) {
        Class<? extends Object> cls = o.getClass();
        if ( 0 <= cls.getName().indexOf( "SamplingUnitLaboratoryFinding" ) ) {
        	Util.breakHere();
        }
        List<IGenericWrapper> holder = new ArrayList<IGenericWrapper>();
        for (int i = 0; i < useProperties.length; i++) {
            String prop = useProperties[i];
            // use the more forgiving version
            ClassProperty cp = ClassAnalyzer.findClassProperty(cls, prop);
            if (cp != null) {
                if (cp.isStatic())
                    continue;
//                if (cp.isReadOnly())
//                    continue;
                IPropertyWrapper pw = buildPropertyWrapper(o, cp);
                if (pw != null)
                    holder.add(pw);
            } else {
                ClassCollection colect = ClassAnalyzer.getCollection(cls, prop);
                if (colect == null)
                    throw new IllegalArgumentException("Cannot find property or collection " + prop +
                            " in class " + cls);
                ICollectionWrapper pw = buildCollectionWrapper(o, colect);
                  if (pw != null)
                      holder.add(pw);
             }
        }

        IGenericWrapper[] ret = new IGenericWrapper[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    protected static ICollectionWrapper buildCollectionWrapper(Object o, ClassCollection cp) {
        Class propClass = cp.getType();
        return new ReflectionCollectionWrapper(o, cp, getEmptyArray(propClass));
    }


    protected static IPropertyWrapper buildPropertyWrapper(Object o, ClassProperty cp) {
        Class propClass = cp.getType();
        return new ReflectionPropertyWrapper(o, cp, getEmptyArray(propClass));
    }

    public static Object fromString(String s, Class c) {
        if (Util.isEmptyString(s))
            return null;
        if (String.class == c)
            return s;
        if (Double.class.isAssignableFrom(c))
            return new Double(s);
        if (Integer.class.isAssignableFrom(c))
            return new Integer(s);
        if (Float.class.isAssignableFrom(c))
            return new Float(s);
        if (Date.class.isAssignableFrom(c))
            return DateParser.parseDate(s);
        if (Boolean.class.isAssignableFrom(c))
            return "true".equalsIgnoreCase(s) ? Boolean.TRUE : Boolean.FALSE;
        Object[] args = {s};
        Constructor cnst = getCompatableConstructor(c, args);
        try {
            return cnst.newInstance(s);
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * if c implements a static method called values
     *
     * @param c non-null class
     * @return possibly null return
     */
    public static Object[] getEnumValues(Class c) {
        try {
            Method m = c.getMethod("values");
            Object[] ret = (Object[]) m.invoke(null);
            return ret;
        }
        catch (NoSuchMethodException e) {

        }
        catch (IllegalAccessException e) {

        }
        catch (InvocationTargetException e) {

        }
        return null;
    }

    /**
     * create an instance of a class indicated by an array
     *
     * @param example non-null array of objects of the proper type
     * @param args    non-null argiments
     * @return non-null created object
     * @throws RuntimeException on failure - cauase is real failure
     */
    public static <T> T createNewInstance(T[] example, Object... args) throws RuntimeException {
        Class cls = example.getClass().getComponentType();
        if (args.length == 0) {
            try {
                return (T) cls.newInstance();
            }
            catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            Constructor c = getCompatableConstructor(cls, args);
            try {
                return (T) c.newInstance(args);
            }
            catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * return a constructor which works with the argument set
     *
     * @param c    non-null class
     * @param args non-null arguments
     * @return non-null Constructor which works with the arguments
     * @throws IllegalArgumentException if there is no such constructor
     */
    protected static Constructor getCompatableConstructor(Class c, Object[] args)
            throws IllegalArgumentException {
        Constructor[] constructors = c.getConstructors();
        for (int i = 0; i < constructors.length; i++) {
            Constructor constructor = constructors[i];
            if (isCompatableConstructor(constructor, args))
                return constructor;
        }
        throw new IllegalArgumentException("No compatable constructor for class " + c.getName());
    }

    /**
     * test can a constructor be called with a set of arguments
     *
     * @param c    non-null constructor
     * @param args non-null arg set
     * @return true of the call works
     */
    protected static boolean isCompatableConstructor(Constructor c, Object[] args) {
        Class[] types = c.getParameterTypes();
        if (types.length != args.length)
            return false;
        for (int i = 0; i < types.length; i++) {
            Class type = types[i];
            if (!type.isInstance(args[i]))
                return false;
        }
        return true;
    }

    public static IPropertyEditor buildPropertyEditor(IEditableProperty prop) {
        Class targetClass = prop.getType();
        Object[] options = prop.getOptions();
//        if (ISelectableFluid.class.isAssignableFrom(targetClass))
//         {
//             if(options == null) {
//                 FluidSourceNameEnum[] names = FluidSourceNameEnum.SELECTABLE_REAGENTS;
//                 IFluid[] flOPts = new IFluid[names.length];
//                 for (int i = 0; i < flOPts.length; i++) {
//                     flOPts[i] = FluidUtilities.getFluid(names[i]);
//                 }
//                 Object[] refinedOptions = new Object[names.length];
//                 for (int i = 0; i < refinedOptions.length; i++) {
//                    refinedOptions[i] = RunnerDisplayProperties.getFluidRepresentation(names[i]);
//
//                 }
//                 options = refinedOptions;
//             }
//         }
//        if (IFluid.class.isAssignableFrom(targetClass))
//        {
//            if(options == null) {
//                IFluidIdentifier[] names = FluidNameEnum.DELIVERABLE_MIXTURES;
//                IFluid[] flOPts = new IFluid[names.length];
//                for (int i = 0; i < flOPts.length; i++) {
//                    flOPts[i] = FluidUtilities.getFluid(names[i]);
//                }
//                Object[] refinedOptions = new Object[names.length];
//                for (int i = 0; i < refinedOptions.length; i++) {
//                   refinedOptions[i] = RunnerDisplayProperties.getFluidRepresentation(names[i]);
//
//                }
//                options = refinedOptions;
//            }
//        }

        if (options != null) {
            return buildPropertyComboBox(prop, options);
        }
        if (Integer.class.isAssignableFrom(targetClass)) {
            return buildIntegerEditor(prop);
        }
        if (Boolean.class.isAssignableFrom(targetClass)) {
            return buildBooleanEditor(prop);
        }
        if (Long.class.isAssignableFrom(targetClass)) {
            return buildLongEditor(prop);
        }
        if (Float.class.isAssignableFrom(targetClass)) {
            return buildFloatEditor(prop);
        }
        if (Double.class.isAssignableFrom(targetClass)) {
            return buildDoubleEditor(prop);
        }
        if (String.class.isAssignableFrom(targetClass)) {
            return buildStringEditor(prop);
        }
        throw new UnsupportedOperationException("Cannot build Property Editor for class " +
                targetClass.getName());
    }

    public static IPropertyEditor buildLayerEditor(IEditableProperty prop) {
        return new JLayerPropertyEditor(prop);
    }

    public static IPropertyEditor buildIntegerEditor(IEditableProperty prop) {
        return new JIntegerPropertyEditor(prop);
    }

    public static IPropertyEditor buildBooleanEditor(IEditableProperty prop) {
        return new JBooleanPropertyEditor(prop);
    }

    public static IPropertyEditor buildDoubleEditor(IEditableProperty prop) {
        return new JDoublePropertyEditor(prop);
    }

    public static IPropertyEditor buildFloatEditor(IEditableProperty prop) {
        return new JFloatPropertyEditor(prop);
    }

    public static IPropertyEditor buildLongEditor(IEditableProperty prop) {
        return new JLongPropertyEditor(prop);
    }

    public static IPropertyEditor buildStringEditor(IEditableProperty prop) {
        return new JStringPropertyEditor(prop);

    }

    public static IPropertyEditor buildPropertyComboBox(IEditableProperty prop, Object[] options) {
        Class target = prop.getType();
        if (options == null)
            options = prop.getOptions();
        if (options != null) {
            return new JComboBoxPropertyEditor(prop, options);
        }
        throw new UnsupportedOperationException("Fix This"); // ToDO Fix Thix
    }

    public static IEditableProperty buildProperty(String name,
                                                  Class cls, Object defaultValue, Object[] options, IPropertySource ps) {
        ObjectPropertyImpl desc = new ObjectPropertyImpl(name, cls);
        desc.setDefaultValue(defaultValue);
        desc.setOptions(options);

        IEditableProperty prop = new EditableProperty(desc, ps);
        if (defaultValue != null)
            prop.setValue(defaultValue);
        return prop;
    }

    public static IEditableProperty buildProperty(String name,
                                                  Class cls, Object defaultValue, Object[] options, IPropertySource ps, String units) {
        ObjectPropertyImpl desc = new ObjectPropertyImpl(name, cls);
        desc.setDefaultValue(defaultValue);
        desc.setOptions(options);
        desc.setUnits(units);

        IEditableProperty prop = new EditableProperty(desc, ps);
        if (defaultValue != null)
            prop.setValue(defaultValue);
        return prop;
    }

}
