package com.lordjoe.lang;

import com.lordjoe.utilities.*;

import java.util.*;

/**
 * com.lordjoe.lang.OperationStateEnum
 * Enumerated type to descript states of an operation
 * The most obvious use is to allow a business object
 * to communicate to a UI element
 * @see com.nanomaterials.ui.ColorUtilities.operationStateToColor
 * @author Steve Lewis
 * @date Oct 25, 2005
 */
public class OperationStateEnum implements INamedObject
{
    private static final Map gByName = new HashMap();
    public static OperationStateEnum getByName(String s)
    {
        OperationStateEnum ret = (OperationStateEnum)gByName.get(s);
        if(ret == null)
            throw new IllegalArgumentException(s + " is not a defined Operation state ");
        return ret;
    }

    public static final OperationStateEnum UNKNOWN =
            new OperationStateEnum("Unknown");
    public static final OperationStateEnum NORMAL =
            new OperationStateEnum("Normal");
    public static final OperationStateEnum WARNING =
            new OperationStateEnum("Warning");
    public static final OperationStateEnum SEVERE_WARNING =
            new OperationStateEnum("Severe Warning");
    public static final OperationStateEnum ERROR =
            new OperationStateEnum("Error");

    private final String m_Name;
    private OperationStateEnum(String name)
    {
        m_Name = name;
        gByName.put(getName(),this);
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

    public String toString()
    {
        return getName();
    }
}
