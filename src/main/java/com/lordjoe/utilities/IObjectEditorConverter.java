package com.lordjoe.utilities;

/**
 * com.lordjoe.Utilities.IObjectEditorConverter
 * @2uthor Steve Lewis
 * @date Aug 8, 2003
 */
public interface IObjectEditorConverter
{
    public static Class THIS_CLASS = IObjectEditorConverter.class;
    public static final IObjectEditorConverter[] EMPTY_ARRAY = {};

    public String convertToString(Object object);

    public Object convertFromString(String in, Class outClass);
}
