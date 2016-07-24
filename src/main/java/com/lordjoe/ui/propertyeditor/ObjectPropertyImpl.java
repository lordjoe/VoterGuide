package com.lordjoe.ui.propertyeditor;

import com.lordjoe.utilities.*;


/**
 * com.lordjoe.ui.propertyeditor.ObjectPropertyImpl
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class ObjectPropertyImpl implements IObjectProperty
{
    public static final Class THIS_CLASS = ObjectPropertyImpl.class;
    public static final ObjectPropertyImpl EMPTY_ARRAY[] = {};

    private final String m_Name;
    private String m_ReadableName;
    private String m_Description;
    private Object[] m_Options;
    private Object m_DefaultValue;
    private boolean m_NullsAllowed;
    private String m_Units;
    private final Class m_Type;

    public ObjectPropertyImpl(String name,Class type)
    {
        m_Name = name;
        m_Type = type;
        if(type != null && EnumeratedType.class.isAssignableFrom(type)) {
            m_Options = EnumeratedType.getClassOptions(type);
        }
    }

    public String getDescription()
    {
        return m_Description;
    }

    public void setDescription(String pDescription)
    {
        m_Description = pDescription;
    }

    /**
     * return the object's name - frequently this is
     * final
     *
     * @return unsually non-null name
     */
    public String getName()
    {
        return m_Name;
    }

    /**
     * property type
     * @return non-null class
     */
    public Class getType()
    {
        return m_Type;
    }

    /**
     * if non-null this will be the default value
     * @return possibly null object
     */
    public Object getDefaultValue()
    {
        return m_DefaultValue;

    }

    /**
     * if non-null this will ne a set of legal values
     * @return possibly null collection
     */
    public Object[] getOptions()
    {
        return m_Options;

    }

    /**
     * for numbers only this is the units
     * @return possibly null String
     */
    public String getUnits()
    {
        return m_Units;

    }

    /**
     * can null be used as a value
     * @return as above
     */
    public boolean isNullAllowed()
    {
        return m_NullsAllowed;
    }

    public void setOptions(Object[] options)
    {
        m_Options = options;
    }

    public void setDefaultValue(Object defaultValue)
    {
        m_DefaultValue = defaultValue;
    }

    public void setNullsAllowed(boolean nullsAllowed)
    {
        m_NullsAllowed = nullsAllowed;
    }

    public void setUnits(String units)
    {
        m_Units = units;
    }

    /**
     * property readable name
     * @return non-null name a user can read
     */
    public String getReadableName()
    {
        if(m_ReadableName == null)
            return getName();
        return m_ReadableName;

    }

   /**
    * set a readable name
    * @param newName non-null name
    */
    public void setReadableName(String newName)
   {
       m_ReadableName =  newName;
   }



}
