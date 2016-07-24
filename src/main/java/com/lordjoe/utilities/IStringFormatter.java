package com.lordjoe.utilities;

/**
 * com.lordjoe.Utilities.IStringFormatter
 * @2uthor Steve Lewis
 * @date Jun 9, 2003
 */
public interface IStringFormatter
{
    public static Class THIS_CLASS = IStringFormatter.class;
    public static final IStringFormatter[] EMPTY_ARRAY = {};

    /**
     * format the input string according to the rules
     * @param in non-null string
     * @return  non-null formatted String
     */
    public String formatString(String in);

    /**
     * if there is a required length i.e. 10 for us numbers
     * or 5 for zip code return that otherwise return 0
     * @return  as above
     */
    public int getRequiredLength();
}
