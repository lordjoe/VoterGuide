package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.IIdentifiedObject
 *
 * @author merdahl
 *         created Jun 12, 2007
 */
public interface IIdentifiedObject extends com.lordjoe.utilities.INamedObject
{
    public static IIdentifiedObject[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IIdentifiedObject.class;
 
    /**
     * get an identifier for a database
     * @return
     */
    public String getId();

    /**
     * set the identifier
     * @param id non-null id
     */
    public void setId(String id);

}