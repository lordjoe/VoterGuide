package com.lordjoe.propertyeditor;

import com.lordjoe.lib.xml.*;
import com.lordjoe.utilities.*;
import com.lordjoe.exceptions.*;

import java.beans.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

/**
 * com.lordjoe.propertyeditor.ReflectionPropertyWrapper
 *   uses reflection to return a propertyWarpper for a specified property
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class ReflectionPropertyWrapper<T> implements IPropertyWrapper<T>
{
    public static ReflectionPropertyWrapper[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ReflectionPropertyWrapper.class;

    private final T[] m_Type;
    private final Object m_Owner;
    private final ClassProperty m_Property;
    private final boolean m_Nullable;
    private final boolean m_ReadOnly;
    private String m_DisplayName;
    private boolean m_TestedOptions;
    private  ReflectiveOptionsGenerator<T,Object> m_Generator;
    private T[] m_Options;
    private IPropertyStringConverter<T> m_StringConverter;

    public ReflectionPropertyWrapper(Object pOwner, ClassProperty pProperty,T[] type)
    {
        m_Type = type;
        m_Owner = pOwner;
        m_Property = pProperty;
        m_Nullable = determineNullable();
        m_ReadOnly = m_Property.isReadOnly();
        Class cls = m_Property.getType();
        if(PseudoEnum.class.isAssignableFrom(cls))  {
            setOptions((T[])PseudoEnum.values(cls));
        }
        else if(Enum.class.isAssignableFrom(cls))  {
            setOptions((T[])Util.getEnumValues(cls));
        }
    }

    /**
     * return the class of the property
     *
     * @return
     */
    public Class getPropertyClass()
    {
        return getProperty().getPropertyType();
    }

    public Class getOwningClass() {
        return m_Property.getOwningClass();
    }

    /**
     * set the current value of the editor
     *
     * @param value
     */
    public void setCurrentValue(T value)
    {
         getProperty().setValue(getOwner(),value);

    }

    protected boolean  determineNullable()
    {
        return m_Property.isNullable();
    }

    public IPropertyStringConverter<T> getStringConverter()
    {
        return m_StringConverter;
    }

    public void setStringConverter(IPropertyStringConverter<T> pStringConverter)
    {
        m_StringConverter = pStringConverter;
    }

    public T[] getType()
    {
        return m_Type;
    }

    public Object getOwner()
    {
        return m_Owner;
    }

    public ClassProperty getProperty()
    {
        return m_Property;
    }

    public boolean isNullable()
    {
        return m_Nullable;
    }

    /**
     * return the name of the property - usually as a java bean name
     *
     * @return non-null String
     */
    public String getPropertyName()
    {
        return getProperty().getName();
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */

    public void propertyChange(PropertyChangeEvent evt)
    {
        if (getPropertyName().equalsIgnoreCase(evt.getPropertyName())) {
            throw new UnsupportedOperationException("Fix This"); // ToDo
        }

    }

    /**
     * if implemented converts a string to a legal value
     *
     * @param s possibly null or empty string - null or empty shoud return null
     * @return possibly null object
     * @throws UnsupportedOperationException this may not be implemented
     * @throws IllegalArgumentException      the String cannot be converted
     */
    public T fromString(String s)
            throws UnsupportedOperationException, IllegalArgumentException
    {
        IPropertyStringConverter<T> sc = getStringConverter();
        if(sc != null)
            return sc.convertString(getOwner(),s);
        Class c = getProperty().getPropertyType();
        return (T)PropertyEditorUtilities.fromString(s,c);
    }

    /**
     * if non-null these are the only acceptabll values
     * for the property
     *
     * @return
     */
    public T[] getOptions()
    {
        if(m_Options != null)
            return m_Options;
        ClassProperty property = getProperty();
        Class c = property.getType();
       if(c.isEnum())  {
           m_Options = (T[]) PropertyEditorUtilities.getEnumValues(c);
           return m_Options;
       }
        if(m_Generator != null) {
            return  m_Generator.generateOptions(getOwner());
        }
       if(m_TestedOptions)
            return null;
        m_TestedOptions = true;
        OptionMethod annotation = (OptionMethod)property.getGetMethod().getAnnotation(OptionMethod.class);
        if(annotation != null)  {
            String optionMethod = annotation.MethodName();
            Method m = null;
            try {
                m = property.getOwningClass().getMethod(optionMethod);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("Method in OptionMethod " + optionMethod + " does not exist"); // ToDo change
            }
            m_Generator = new ReflectiveOptionsGenerator<T,Object>(m);
            T[] ret = m_Generator.generateOptions(getOwner());
            return ret;
         }
       return null;
    }

    public void setOptions(T[] pOptions)
    {
        m_Options = pOptions;
    }

    /**
     * return the value represented by the editor
     *
     * @return
     */
    public T getCurrentValue()
    {
        Method getMethod = getProperty().getGetMethod();
        if(getMethod == null)
            throw new IllegalStateException("Property " + getPropertyName() +
              "is write only");
        try {
            return (T)getMethod.invoke(getOwner());
        }
        catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
        catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }

    }

    /**
     * true of the test value is a legal value
     *
     * @param test possibly null test value
     * @return as above
     */
    public boolean isAcceptable(T test)
    {
        if(test == null)
            return isNullable();
        Class type = getProperty().getPropertyType();
        if(type.isPrimitive())  {
            Class watpperClass = ClassUtilities.primitiveToWrapper(type);
            if(watpperClass.isInstance(test))
                return true;
            if(Number.class.isInstance(test) &&
                    Number.class.isAssignableFrom(watpperClass))
                return true;
        }
        else {
        if(type.isInstance(test))
            return true;
        }
        if(test instanceof String)  {
            throw new UnsupportedOperationException("Fix This"); // ToDo
        }

        return false;
    }

    /**
     * true of a the value is a  collection of
     * values from th eoptions - if options is null
     * this should return false
     *
     * @return as above
     */
    public boolean isMultipleValuesSupported()
    {
        return false;
    }

    /**
     * special set method to set multiple items - only used if
     * isMultipleValuesSupported is true
     *
     * @param items non-null array of items
     * @throws UnsupportedOperationException unless isMultipleValuesSupported is true
     */
    public void setMultipleValues(T[] items) throws UnsupportedOperationException
    {
        if(!isMultipleValuesSupported())
            throw new UnsupportedOperationException("setMultiple values only implemented for multivalues data");

    }

    /**
     * return the values as an array
     *
     * @return non-null array - will be of length > 1 only of
     *         isMultipleValuesSupported is true
     * @throws UnsupportedOperationException unless isMultipleValuesSupported is true
     *                                       in real life it is ok to implement this method to return arrays of length
     *                                       0 or 1 for most objects
     */
    public T[] getMultipleValues() throws UnsupportedOperationException
    {
        T val = getCurrentValue();
        T[] options = getOptions();
        if(options == null)
            throw new UnsupportedOperationException("getMultipleValues values only implemented for items with options");
        if(val == null) {
           return (T[])Array.newInstance(options.getClass().getComponentType(),0);
        }
        else {
           T[] ret = (T[])Array.newInstance(options.getClass().getComponentType(),1);
           ret[0] = val;
           return ret;

        }


    }

    /**
     * if true the property cannor be non-null
     *
     * @return
     */
    public boolean isRequired()
    {
        return !isNullable();
    }

    /**
     * if true the property cannot be changed
     *
     * @return
     */
    public boolean isReadOnly()
    {
        ClassProperty property = getProperty();
        return property.isReadOnly();
    }

    /**
     * return the name of the property as seen by the user -
     *
     * @return non-null String
     */
    public String getDisplayName()
    {
        if (m_DisplayName != null)
            return m_DisplayName;
        String s = getPropertyName();
        return Util.displayString(s);
    }

    public void setDisplayName(String pDisplayName)
    {
        m_DisplayName = pDisplayName;
    }
}
