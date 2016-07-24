package com.lordjoe.ui.general;

import com.lordjoe.utilities.*;
import com.lordjoe.general.*;

import java.util.*;

/**
 * com.lordjoe.runner.ui.general.ParameterTypeEnum
 *
 * @author Steve Lewis
 * @date Oct 2, 2007
 */
public class ParameterTypeEnum extends EnumeratedString
{
    public static final Class THIS_CLASS = ParameterTypeEnum.class;
    public static final ParameterTypeEnum EMPTY_ARRAY[] = {};
    // Map - key is name value ins Enum
    protected static Map gOptions = new HashMap();
    protected static Map gOptionsByValue = new HashMap();
    protected static List gAccessors = new ArrayList();

    public static final String UNKNOWN_VALUE = "_";
    public static final ParameterTypeEnum UNKNOWN_ENUM = new ParameterTypeEnum("Unknown",
            UNKNOWN_VALUE);

    public static final String INTEGER_VALUE = "int";
    public static final ParameterTypeEnum INTEGER_ENUM = new ParameterTypeEnum(INTEGER_VALUE,
            INTEGER_VALUE);

    public static final String BOOLEAN_VALUE = "boolean";
    public static final ParameterTypeEnum BOOLEAN_ENUM = new ParameterTypeEnum(BOOLEAN_VALUE,
            BOOLEAN_VALUE);

    public static final String TIME_VALUE = "Time";
    public static final ParameterTypeEnum TIME_ENUM = new ParameterTypeEnum(TIME_VALUE, TIME_VALUE);


    public static final String FACTOR_VALUE = "Factor";
    public static final ParameterTypeEnum FACTOR_ENUM = new ParameterTypeEnum(FACTOR_VALUE,
            FACTOR_VALUE);

    public static final String TEXT_VALUE = "Text";
    public static final ParameterTypeEnum TEXT_ENUM = new ParameterTypeEnum(TEXT_VALUE, TEXT_VALUE);

    public static final String APPLICATION_VALUE = "Application";
    public static final ParameterTypeEnum APPLICATION_ENUM = new ParameterTypeEnum(
            APPLICATION_VALUE, APPLICATION_VALUE);

    public static final String COMMAND_TYPE_VALUE = "CommandType";
    public static final ParameterTypeEnum COMMAND_TYPE_ENUM = new ParameterTypeEnum(
            COMMAND_TYPE_VALUE, COMMAND_TYPE_VALUE);

    public static final String STRING_VALUE = "String";
    public static final ParameterTypeEnum STRING_ENUM = new ParameterTypeEnum(
            STRING_VALUE, STRING_VALUE);



    public static final String FILE_VALUE = "File";
    public static final ParameterTypeEnum FILE_ENUM = new ParameterTypeEnum(FILE_VALUE, FILE_VALUE);


    public static final String PART_VALUE = "Part";
    public static final ParameterTypeEnum PART_ENUM = new ParameterTypeEnum(PART_VALUE, PART_VALUE);

    // Special case tihe is either Fluid or Waste
    public static final String SELECTABLE_VALUE = "Selectable";
    public static final ParameterTypeEnum SELECTABLE_ENUM = new ParameterTypeEnum(SELECTABLE_VALUE,
            SELECTABLE_VALUE);

    public static final ParameterTypeEnum SAMPLE_OPTION = UNKNOWN_ENUM;

    // This helps resolve to ams from strings
    static {
        ImplementationUtilities.addConverter(new EnumeratedStringConverter(THIS_CLASS));
    }

    /**
     * look up one instance by name
     *
     * @return non-null instance
     * @throws IllegalArgumentException if the name is not a member
     */
    public static ParameterTypeEnum getOption(String in)
    {
        Object ret = gOptions.get(in);
        if (ret == null)
            throw new IllegalArgumentException(
                    "Comparison " + in + " not found - valid comparisons are:" +
                            Util.buildListString(Util.getKeyStrings(gOptions), 0));
        return ((ParameterTypeEnum) ret);
    }

    /**
     * look up one instance by name
     *
     * @return possibly-null instance
     */
    public static ParameterTypeEnum getOptionByValue(String in)
    {
        Object ret = gOptionsByValue.get(in);
        return ((ParameterTypeEnum) ret);
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
    public static ParameterTypeEnum[] optionValues()
    {
        return (SAMPLE_OPTION.getOptionValues());
    }

    /**
     * return a random instance
     *
     * @return non-null instance
     */
    public static ParameterTypeEnum randomElement()
    {
        return ((ParameterTypeEnum) SAMPLE_OPTION.randomType());
    }

    private ParameterTypeEnum(String name, String value)
    {
        super(name, value);
    }

    /**
     * true of the value is an unknown state
     *
     * @return
     */
    public boolean isUnknown()
    {
        return this == UNKNOWN_ENUM;
    }


    /**
     * this works better in selectors
     *
     * @return
     */
    public String toString()
    {
        if (UNKNOWN_ENUM == this)
            return "";
        return super.toString();
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
    protected ParameterTypeEnum[] getOptionValues()
    {
        ParameterTypeEnum[] ret = new ParameterTypeEnum[gOptions.size()];
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
