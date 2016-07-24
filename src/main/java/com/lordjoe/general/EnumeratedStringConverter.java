package com.lordjoe.general;

import com.lordjoe.utilities.*;

/**
 * com.lordjoe.general.EnumeratedStringConverter
 * @author Steve Lewis
 * @date Jun 2, 2006 
 */
public class EnumeratedStringConverter implements IStringConverter<EnumeratedString>
{
    private final Class m_Class;
    public EnumeratedStringConverter(Class realClass) {
         m_Class = realClass;
    }
    public String convertToString(EnumeratedString obj)
    {
        if (obj == null)
            return "null";
        return obj.toString();
    }

    public EnumeratedString convertFromString(String str)
    {
        if (str == null || "null".equals(str) || str.length() == 0)
            return null;
        else
            return (EnumeratedString)EnumeratedType.stringToValue(m_Class,str);
    }

    public boolean isApplicable(Class test)
    {
        return m_Class.isAssignableFrom(test);
    }
}
