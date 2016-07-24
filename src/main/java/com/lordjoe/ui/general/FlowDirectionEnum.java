package com.lordjoe.ui.general;

import com.lordjoe.utilities.*;

import java.util.*;


/**
 * com.lordjoe.ui.general.FlowDirectionEnum
 * class repre
 * @author slewis
 * @date Mar 24, 2005
 */
public class FlowDirectionEnum extends EnumeratedString

{
    public static final Class THIS_CLASS = FlowDirectionEnum.class;
    public static final FlowDirectionEnum EMPTY_ARRAY[] = {};

    public static final String PREFIX = "vs";
    // Map - key is name value ins Enum
    protected static Map gOptions = new HashMap();
    protected static Map gOptionsByValue = new HashMap();
    protected static List gAccessors = new ArrayList();

    public static final String UNKNOWN_VALUE = "Unknown";
    public static final FlowDirectionEnum UNKNOWN_ENUM =
            new FlowDirectionEnum("Unknown", UNKNOWN_VALUE);
    public static final FlowDirectionEnum FORWARD_ENUM =
            new FlowDirectionEnum("Forward");
    public static final FlowDirectionEnum BACKWARD_ENUM =
            new FlowDirectionEnum("Backward");
    public static final FlowDirectionEnum STILL_ENUM =
            new FlowDirectionEnum("Still");

    public static final FlowDirectionEnum SAMPLE_OPTION = UNKNOWN_ENUM;


    /**
     * look up one instance by name
     *
     * @return non-null instance
     * @throws IllegalArgumentException if the name is not a member
     */
    public static FlowDirectionEnum getOption(String in)
    {
        Object ret = gOptions.get(in);
        if (ret == null)
            throw new IllegalArgumentException("Comparison " + in + " not found - valid comparisons are:" +
                    Util.buildListString(Util.getKeyStrings(gOptions), 0));
        return ((FlowDirectionEnum) ret);
    }

    /**
     * look up one instance by name
     *
     * @return possibly-null instance
     */
    public static FlowDirectionEnum getOptionByValue(int in)
    {
        Object ret = gOptionsByValue.get(new Integer(in));
        return ((FlowDirectionEnum) ret);
    }

    /**
     * return the names of all options for this enum type
     *
     * @return non-null array of names
     */
    public static String[] optionNames()
    {
        return (SAMPLE_OPTION.getOptionNames());
    }

    /**
     * return the values of all options for this enum type
     *
     * @return non-null array of names
     */
    public static FlowDirectionEnum[] optionValues()
    {
        return (SAMPLE_OPTION.getOptionValues());
    }

    /**
     * return a random instance
     *
     * @return non-null instance
     */
    public static FlowDirectionEnum randomElement()
    {
        return ((FlowDirectionEnum) SAMPLE_OPTION.randomType());
    }

    private FlowDirectionEnum(String name, String value)
    {
        super(name, value);
    }

    private FlowDirectionEnum(String value)
    {
        super(PREFIX + value, PREFIX + value);
    }

    public boolean isUnknown()
    {
        return(UNKNOWN_ENUM == this);
    }

    public boolean isFlowing()
     {
         if(isUnknown())
              return false;
         if(STILL_ENUM == this)
              return false;
           return true;
     }
    public boolean isBackwards()
     {
         return BACKWARD_ENUM == this;

     }
      /**
     * return a Map of possible values
     *
     * @return non-null name
     */
    protected Map getOptionMap()
    {
        return (gOptions);
    }

    /**
     * return an array of possible values
     *
     * @return non-null name
     */
    protected FlowDirectionEnum[] getOptionValues()
    {
        FlowDirectionEnum[] ret = new FlowDirectionEnum[gOptions.size()];
        gOptions.values().toArray(ret);
        Arrays.sort(ret, Util.NAME_COMPARATOR);
        return ret;
    }

    /**
     * return a Map of possible values
     *
     * @return non-null name
     */
    protected Map getValueMap()
    {
        return (gOptionsByValue);
    }

}