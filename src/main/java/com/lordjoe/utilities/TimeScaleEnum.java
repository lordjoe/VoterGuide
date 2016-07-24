package com.lordjoe.utilities;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

/**
 * java enum derived from TimeScaleEnum
 * representing Time scale Options
 * @author S Lewis
 */
public class TimeScaleEnum extends EnumeratedInt implements Serializable {

    // Map - key is name value ins Enum
    protected static Map gOptions = new HashMap();
    protected static Map gOptionsByValue = new HashMap();

    public static final TimeScaleEnum Millisecond_ENUM = new TimeScaleEnum("Millisec", 1);
    public static final TimeScaleEnum Second_ENUM = new TimeScaleEnum("Sec", 1000);
    public static final TimeScaleEnum Minute_ENUM = new TimeScaleEnum("Min", 60 * 1000);
    public static final TimeScaleEnum Hour_ENUM = new TimeScaleEnum("Hour", 60 * 60 * 1000);
    public static final TimeScaleEnum Day_ENUM = new TimeScaleEnum("Day", 24 * 60 * 60 * 1000);
 //   public static final TimeScaleEnum Week_ENUM = new TimeScaleEnum("Month", 30 * 24 * 60 * 60 * 1000);
 //   public static final TimeScaleEnum Month_ENUM = new TimeScaleEnum("Day", 30L * 24 * 60 * 60 * 1000);
 //   public static final TimeScaleEnum Year_ENUM = new TimeScaleEnum("Year", 365L * 24 * 60 * 60 * 1000);
    // one option exposed for convenience
    protected static final TimeScaleEnum SAMPLE_OPTION = Millisecond_ENUM;

    /**
     * look up one instance by namee
     * @return non-null instance
     * @throws IllegalArgumentException if the name is not a member
     */
    public static TimeScaleEnum getOption(String in) {
        Object ret = gOptions.get(in);
        if (ret == null)
            throw new IllegalArgumentException("Comparison " + in + " not found - valid comparisons are:\n" +
                    Util.buildListString(Util.getKeyStrings(gOptions), 0));
        return ((TimeScaleEnum) ret);
    }

    /**
     * look up one instance by name
     * @return possibly-null instance
     */
    public static TimeScaleEnum getOptionByValue(int in) {
        Object ret = gOptionsByValue.get(new Integer(in));
        return ((TimeScaleEnum) ret);
    }

    /**
     * return the names of all options for this enum type
     * @return non-null array of names
     */
    public static String[] optionNames() {
        return (SAMPLE_OPTION.getOptionNames());
    }

    /**
     * return a random instance
     * @return non-null instance
     */
    public static TimeScaleEnum randomElement() {
        return ((TimeScaleEnum) SAMPLE_OPTION.randomType());
    }

    private TimeScaleEnum(String name, int value) {
        super(name, value);
    }

    /**
     * return a Map of possible values
     * @return non-null name
     */
    protected Map getOptionMap() {
        return (gOptions);
    }

    /**
     * return a Map of possible values
     * @return non-null name
     */
    protected Map getValueMap() {
        return (gOptionsByValue);
    }

}
