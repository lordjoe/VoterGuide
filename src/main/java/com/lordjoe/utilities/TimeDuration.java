package com.lordjoe.utilities;

import java.text.*;
import java.util.*;
import java.io.Serializable;

/**
 * com.lordjoe.Utilities.TimeDuration
 * class representing a time duration
 * @author Steve Lewis
 */
public class TimeDuration implements Serializable {
    public static final int MILLISEC_RESOLUTION = 1;
    
    public static Calendar thisMonth() {
        Calendar now = new GregorianCalendar();
        return (new GregorianCalendar(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                1
        ));
    }

    public static Calendar thisWeek() {
        Calendar now = new GregorianCalendar();
        Calendar ret = thisDay();
        while (ret.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            ret.add(Calendar.DATE, -1);
        return (ret);
    }


    public static Calendar thisMinute() {
        Calendar now = new GregorianCalendar();
        return (new GregorianCalendar(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DATE),
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE)
        ));
    }

    public static Calendar thisHour() {
        Calendar now = new GregorianCalendar();
        return (new GregorianCalendar(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DATE),
                now.get(Calendar.HOUR_OF_DAY),
                0
        ));
    }

    public static Calendar thisDay() {
        Calendar now = new GregorianCalendar();
        return (new GregorianCalendar(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DATE)
        ));
    }

  
    public static String buildElapsedTimeString(long elapsed)
    {
        StringBuffer sb = new StringBuffer();
        long hour = elapsed / TimeDuration.ONE_HOUR;
        elapsed -= hour * TimeDuration.ONE_HOUR;
        if(hour < 100) {
            sb.append(string02(hour));
        }
        else  {
            if(hour < 1000)
                 sb.append(string03(hour));
            else
                sb.append(Long.toString(hour));
        }


        sb.append(":");
        long min = elapsed / TimeDuration.ONE_MINUTE;
        sb.append(string02(min));
        sb.append(":");
        elapsed -= min * TimeDuration.ONE_MINUTE;

        long sec = elapsed / TimeDuration.ONE_SECOND;
        sb.append(string02(sec));
        sb.append(":");
        elapsed -= sec * TimeDuration.ONE_SECOND;

        long millisec = elapsed;
        sb.append(string03(millisec / MILLISEC_RESOLUTION)); // only sho 0.1 sec
        return sb.toString();
    }


    protected static String string02(long in)
     {
         if (in == 0)
             return "00";
         if (in < 10)
             return "0" + in;
         if (in < 100)
             return Long.toString(in);
         throw new IllegalArgumentException("String02 needs data < 100 not " + in);
      }

    
    protected static String string03(long in)
     {
         if (in == 0)
             return "000";
         if (in < 10)
             return "00" + in;
         if (in < 100)
             return "0" + in;
         if(in < 1000)
            return Long.toString(in);
         throw new IllegalArgumentException("String 03 needs data < 1000 not " + in);
     }

    public static long decodeElapsedTimeString(String time)
    {
        String[] items = time.split(":");
        long ret = 0;
        int index = 0;
        if (items.length == 4)
            ret += TimeDuration.ONE_HOUR * Integer.parseInt(items[index++]);
        ret += TimeDuration.ONE_MINUTE * Integer.parseInt(items[index++]);
        ret += TimeDuration.ONE_SECOND * Integer.parseInt(items[index++]);
        ret += Integer.parseInt(items[index++]) * MILLISEC_RESOLUTION;
        return ret;
    }


    // ========================
    // Time Constants
    public static final long ONE_SECOND = 1000;
    public static final long ONE_MINUTE = 60 * ONE_SECOND;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;
    public static final long ONE_WEEK = 7 * ONE_DAY;
    public static final long ONE_MONTH = 30 * ONE_DAY;
    public static final long ONE_YEAR = 365 * ONE_DAY;


    public static String formatTime(int in) {
        if (in < ONE_SECOND)
            return ("" + in + "millisec");
        if (in < ONE_MINUTE)
            return (Util.formatDouble(in / (1.0 * ONE_SECOND), 2) + " sec");
        if (in < ONE_HOUR)
            return (Util.formatDouble(in / (1.0 * ONE_MINUTE), 2) + " min");
        if (in < ONE_DAY)
            return (Util.formatDouble(in / (1.0 * ONE_HOUR), 2) + " hour");
        return (Util.formatDouble(in / (1.0 * ONE_DAY), 2) + " days");
    }

    private final long m_Interval; // interval in millisec

    public TimeDuration(long Interval) {
        this(Interval, Calendar.MILLISECOND);
    }

    public TimeDuration(long in, TimeScaleEnum units) {
        m_Interval = in * units.getValue();
    }

    public TimeDuration(long in, int units) {
        m_Interval = in * conversionFactor(units);
    }

    public TimeDuration(String in) {
        this(parse(in));
    }

    public boolean equals(Object Test) {
        if (Test == null) return (false);
        if (Test == this) return (true);
        if (Test.getClass() != this.getClass()) return (false);
        return (((TimeDuration) Test).m_Interval == m_Interval);
    }

    public int hashCode() {
        return (int) (m_Interval ^ (m_Interval >> 32)); // what Long Does
    }

    /**
     * Compares two TimeDuration numerically.
     *
     * @param   anotherLong   the <code>TimeDuration</code> to be compared.
     * @return  the value <code>0</code> if the argument Long is equal to
     *          this Long; a value less than <code>0</code> if this Long
     *          is numerically less than the TimeDuration argument; and a
     *          value greater than <code>0</code> if this TimeDuration is
     *          numerically greater than the TimeDuration argument
     *		(signed comparison).
     * @since   1.2
     */
    public int compareTo(TimeDuration anotherLong) {
        long thisVal = this.m_Interval;
        long anotherVal = anotherLong.m_Interval;
        return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
    }

    /**
     * Compares this TimeDuration to another Object.  If the Object is a TimeDuration,
     * this function behaves like <code>compareTo(TimeDuration)</code>.  Otherwise,
     * it throws a <code>ClassCastException</code> (as TimeDurations are comparable
     * only to other TimeDurations).
     *
     * @param   o the <code>Object</code> to be compared.
     * @return  the value <code>0</code> if the argument is a TimeDuration
     *		numerically equal to this TimeDuration; a value less than
     *		<code>0</code> if the argument is a TimeDuration numerically
     *		greater than this TimeDuration; and a value greater than
     *		<code>0</code> if the argument is a TimeDuration numerically
     *		less than this TimeDuration.
     * @exception <code>ClassCastException</code> if the argument is not a
     *		  <code>TimeDuration</code>.
     * @see     java.lang.Comparable
     * @since   1.2
     */
    public int compareTo(Object o) {
        return compareTo((TimeDuration) o);
    }

    public long longValue() {
        return (longValue(Calendar.MILLISECOND));
    }

    public long longValue(int units) {
        return (m_Interval / conversionFactor(units));
    }

    public long longValue(TimeScaleEnum units) {
        return (m_Interval / units.getValue());
    }

    public int intValue() {
        return (intValue(Calendar.MILLISECOND));
    }

    public int intValue(int units) {
        return ((int) longValue(units));
    }

    public int intValue(TimeScaleEnum units) {
        return ((int) longValue(units));
    }

    public static long conversionFactor(int in) {
        switch (in) {
            case Calendar.MILLISECOND:
                return (1);
            case Calendar.SECOND:
                return (ONE_SECOND);
            case Calendar.MINUTE:
                return (ONE_MINUTE);
            case Calendar.HOUR:
                return (ONE_HOUR);
            case Calendar.HOUR_OF_DAY:
                return (ONE_HOUR);
            case Calendar.DATE:
                return (ONE_DAY);
            case Calendar.MONTH:
                return (ONE_MONTH);
        }
        throw new IllegalArgumentException("Value '" + in +
                "' Invalid - use Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE," +
                "Calendar.HOUR,Calendar.Day OR Calendar.MONTH");

    }

    private static final NumberFormat FORMAT_O2 = new DecimalFormat("00");

    protected static long parse(String in) {
        if (in.indexOf(":") == -1)
            return (Long.parseLong(in)); // string is just a number
        String[] items = Util.parseTokenDelimited(in, '-');
        long ret = 0;
        switch (items.length) {
            case 1:
                return (parseHour(items[0]));
            case 2:
                return (parseDay(items[0]) + parseHour(items[1]));
            default:
                throw new IllegalArgumentException("cannot parse " + in);
        }
    }

    protected static long parseDay(String in) {
        return (ONE_DAY * Long.parseLong(in));
    }

    protected static long parseHour(String in) {
        String[] items = Util.parseTokenDelimited(in, ':');
        long ret = 0;
        switch (items.length) {
            case 2:
                return (ONE_HOUR * Integer.parseInt(items[0]) + ONE_MINUTE * Integer.parseInt(items[1]));
            case 3:
                return (ONE_HOUR * Integer.parseInt(items[0]) + ONE_MINUTE * Integer.parseInt(items[1]) + parseSeconds(items[2]));
            default:
                throw new IllegalArgumentException("cannot parse hh:mm:ss.mmm " + in);
        }
    }

    protected static long parseSeconds(String in) {
        String[] items = Util.parseTokenDelimited(in, '.');
        long ret = 0;
        switch (items.length) {
            case 1:
                return (ONE_SECOND * Integer.parseInt(items[0]));
            case 2:
                return (ONE_SECOND * Integer.parseInt(items[0]) + Integer.parseInt(items[1]));
            default:
                throw new IllegalArgumentException("cannot parse seconds as ss.mmm" + in);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        long rem = m_Interval;
        long days = m_Interval / ONE_DAY;
        rem -= days * ONE_DAY;
        if (days != 0) {
            sb.append(Long.toString(days));
            sb.append("-");
        }
        long hours = rem / ONE_HOUR;
        rem -= hours * ONE_HOUR;
        long minutes = rem / ONE_MINUTE;
        rem -= minutes * ONE_MINUTE;
        long seconds = rem / ONE_SECOND;
        rem -= seconds * ONE_SECOND;
        sb.append(FORMAT_O2.format(hours));
        sb.append(":");
        sb.append(FORMAT_O2.format(minutes));
        if (seconds != 0 || rem != 0) {
            sb.append(":");
            sb.append(FORMAT_O2.format(seconds));
        }
        if (rem != 0) {
            sb.append("." + rem);
        }
        return (sb.toString());
    }

    /**
     * A few simple tests
     */
    public static void main(String[] args) {
        TimeDuration test = new TimeDuration(3 * ONE_HOUR + 7 * ONE_MINUTE);
        String str = test.toString();
        TimeDuration test2 = new TimeDuration(str);
        if (!test.equals(test2))
            System.out.println(test + " != " + test2);
        else
            System.out.println(test + " succeeded");

        test = new TimeDuration(3 * ONE_HOUR + 7 * ONE_MINUTE + 23);
        str = test.toString();
        test2 = new TimeDuration(str);
        if (!test.equals(test2))
            System.out.println(test + " != " + test2);
        else
            System.out.println(test + " succeeded");


        test = new TimeDuration(17 * ONE_DAY + 3 * ONE_HOUR + 7 * ONE_MINUTE + 23);
        str = test.toString();
        test2 = new TimeDuration(str);
        if (!test.equals(test2))
            System.out.println(test + " != " + test2);
        else
            System.out.println(test + " succeeded");


        test = new TimeDuration(1 * ONE_YEAR + 3 * ONE_HOUR + 7 * ONE_MINUTE + 23);
        str = test.toString();
        test2 = new TimeDuration(str);
        if (!test.equals(test2))
            System.out.println(test + " != " + test2);
        else
            System.out.println(test + " succeeded");
    }
}
