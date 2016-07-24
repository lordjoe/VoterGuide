package com.lordjoe.ui.propertyeditor;


/**
 * com.lordjoe.ui.propertyeditor.EditableProperty
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class EditableProperty implements IEditableProperty
{
    public static final Class THIS_CLASS = EditableProperty.class;
    public static final EditableProperty EMPTY_ARRAY[] = {};

    private final IObjectProperty m_Description;
    private final IPropertySource m_Holder;
    public EditableProperty(IObjectProperty desc,
                            IPropertySource holder)
    {
        m_Description = desc;
        m_Holder = holder;
    }

    /**
     * get a readable description
     *
     * @param as above
     */
    public String getDescription()
    {
        return m_Description.getDescription();
    }

    /**
     * return the current value - possibly null
     *
     * @return possibly null value
     */
    public Object getValue()
    {
        return m_Holder.getProperty(getName());
    }

    /**
     * possibly null value to set
     *
     * @param value
     */
    public void setValue(Object value)
    {
        m_Holder.setProperty(getName(),value);
    }

    /**
     * property type
     *
     * @return non-null class
     */
    public Class getType()
    {
        return m_Description.getType();
    }

    /**
     * if non-null this will be the default value
     *
     * @return possibly null object
     */
    public Object getDefaultValue()
    {
        return m_Description.getDefaultValue();
    }

    /**
     * if non-null this will ne a set of legal values
     *
     * @return possibly null collection
     */
    public Object[] getOptions()
    {
        return m_Description.getOptions();
     }

    /**
     * for numbers only this is the units
     *
     * @return possibly null String
     */
    public String getUnits()
    {
        return m_Description.getUnits();
     }

    /**
     * can null be used as a value
     *
     * @return as above
     */
    public boolean isNullAllowed()
    {
        return m_Description.isNullAllowed();
    }

    /**
     * return the object's name - frequently this is
     * final
     *
     * @return unsually non-null name
     */
    public String getName()
    {
        return m_Description.getName();
    }

    /**
     * property readable name
     * @return non-null name a user can read
     */
    public String getReadableName()
    {
        return m_Description.getReadableName();

    }

   /**
    * set a readable name
    * @param newName non-null name
    */
    public void setReadableName(String newName)
   {
       m_Description.setReadableName(newName);
   }



}
