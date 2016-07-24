/**{ file
 @name FarTime.java
 @function Plug replacement for date supporting times from 100CE to present at
 1 minute resolution
 @author> Steven M. Lewis
 @copyright>
 ************************
 *  Copyright (c) 1996,97,98
 *  Steven M. Lewis
 *  www.LordJoe.com
 ************************
 @date> Mon Jun 22 21:48:24 PDT 1998
 @version> 1.0
 }*/
package com.lordjoe.utilities;

import java.util.*;

/**{ class
 @name FarTime
 @function Plug replacement for date supporting times from 100CE to present at
 1 minute resolution
 }*/
public class FarTime {
     public static final long DEFAULT_TOLERANCE = 60000; // one minute
    //- *******************
    //- Fields
    /**{ field
     @name m_fartime
     @function - time in minutes since 0 CE
     }*/
    private int m_fartime;

    /**{ field
     @name m_valueValid
     @function flag saying date is valid
     }*/
    private boolean m_valueValid;

    /**{ field
     @name m_expanded
     @function flag saying day, year fields are valid
     }*/
    private boolean m_expanded;

// byte replaced with short for better debugging
// Symantec represents bytes in hex
// fix by replacing (int) with (byte) and setting
// items below to bytes

    /**{ field
     @name tm_min
     @function minutes after the hour - [0, 59]
     }*/
    private int tm_min;

    /**{ field
     @name tm_hour
     @function hour since midnight - [0, 23]
     }*/
    protected int tm_hour;

    /**{ field
     @name tm_mday
     @function day of the month - [1, 31]
     }*/
    private int tm_mday;

    /**{ field
     @name tm_mon
     @function months since January - [0, 11]
     }*/
    private int tm_mon;

    /**{ field
     @name tm_wday
     @function days since Sunday - [0, 6]
     }*/
    private int tm_wday;

    /**{ field
     @name tm_yday
     @function days since January 1 - [0, 365]
     }*/
    private int tm_yday;

    /**{ field
     @name tm_year
     @function years since 0 AD
     }*/
    private int tm_year;

    /**{ field
     @name tm_isdst
     @function flag for alternate daylight savings time
     }*/
    private int tm_isdst;

    /**{ field
     @name MonthDays
     @function days of month in non-leap year
     }*/
    static final public int MonthDays[] = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
            ;

    /**{ field
     @name MonthCumulativeDays
     @function Cummulative days of month in non-leap year
     }*/
    static final public int MonthCumulativeDays[] = {
        0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365}
            ;

    /**{ field
     @name LeapMonthCumulativeDays
     @function Cummulative days of month in leap year
     }*/
    static final public int LeapMonthCumulativeDays[] = {
        0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366}
            ;

    /**{ field
     @name ShortMonth
     @function short month name i.e. "Jan"
     }*/
    static final public String ShortMonth[] = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}
            ;

    /**{ field
     @name LongMonth
     @function long month name i.e. "January"
     }*/
    static final public String LongMonth[] = {
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}
            ;

    /**{ field
     @name TheWeek
     @function short weekday name i.e. "Wed"
     }*/
    static final public String TheWeek[] = {
        "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"}
            ;

    /**{ field
     @name LongWeek
     @function long weekday name i.e. "Wednesday"
     }*/
    static final public String LongWeek[] = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}
            ;

    /**{ field
     @name YearMinutes
     @function munutes per year (non-leap)
     }*/
    static protected int YearMinutes[];

    /**{ field
     @name FarBaseYear
     @function base yead - o AD
     }*/
    protected static final int FarBaseYear = 0;

//{ global ********************************************
//} ***************************************************

    /**{ field
     @name FarTimeToBase
     @function time base
     }*/
    protected static int FarTimeToBase;

//{ global ********************************************
//} ***************************************************

    /**{ field
     @name MinutesPerDay
     @function minutes per day
     }*/
    static public final int MinutesPerDay = 24 * 60;

//{ global ********************************************
//} ***************************************************

    /**{ field
     @name MinutesPerWeek
     @function minutes in a week
     }*/
    static public final int MinutesPerWeek = 7 * 24 * 60;

//{ global ********************************************
//} ***************************************************

    /**{ field
     @name DaysPerFourYears
     @function number days in Four years
     }*/
    static public final int DaysPerFourYears = (4 * 365) + 1;

//{ global ********************************************
//} ***************************************************

    /**{ field
     @name MinutesPerFourYears
     @function minutes in 4 years
     }*/
    static public final int MinutesPerFourYears = MinutesPerDay * DaysPerFourYears;

    /**{ field
     @name MinutesPerYear
     @function number minutes per non-leap year
     }*/
    static public final int MinutesPerYear = 365 * 24 * 60;
    static public final long MillisecPerYear = MinutesPerYear + 60000;


//{ global ********************************************
//} ***************************************************

    /**{ field
     @name DaysPerCentury
     @function number days per Century (24 leap years)
     }*/
    static public final int DaysPerCentury = 25 * DaysPerFourYears - 1;

//{ global ********************************************
//} ***************************************************

    /**{ field
     @name MinutesPerCentury
     @function number minutes per Century (24 leap years)
     }*/
    static public final int MinutesPerCentury = DaysPerCentury * MinutesPerDay;

//{ global ********************************************
//} ***************************************************

    /**{ field
     @name MinutesToCurrentBase
     @function ???
     }*/
    static protected int MinutesToCurrentBase;

// minutes from base to 1/1/90
//{ global ********************************************
//} ***************************************************

    /**{ field
     @name WeekDayOfBase
     @function day of week of Jan 1 0 AD
     }*/
    static protected final int WeekDayOfBase = 6;

// this is set to agree with date
//{ global ********************************************
//} ***************************************************

    /**{ field
     @name MinutesToDateBase
     @function ???
     }*/
    static protected int MinutesToDateBase;

// minutes from base to 1/1/90
//{ global ********************************************
//} ***************************************************

    /**{ field
     @name CenturyMinutes
     @function Minutes in each century
     }*/
    static protected int CenturyMinutes[];

    /**{ field
     @name wtb
     @function used in decoing dates
     This code is blatently stolen from java.util.Date
     }*/
    private final static String wtb[] = {
        "am", "pm", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt" // this time zone table needs to be expanded
    }
            ;

    /**{ field
     @name ttb
     @function used in decoing dates
     This code is blatently stolen from java.util.Date
     }*/
    private final static int ttb[] = {
        0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 10000 + 0, 10000 + 0, 10000 + 0, // GMT/UT/UTC
        10000 + 5 * 60, 10000 + 4 * 60, // EST/EDT
        10000 + 6 * 60, 10000 + 5 * 60, 10000 + 7 * 60, 10000 + 6 * 60, 10000 + 8 * 60, 10000 + 7 * 60}
            ;

    /**
     * true if test at or after the present time - Tolerance
     * @param test non=null test date
     * @param Tolerance   - allowse error in millisec
     * @return  true if so
     */
    public static boolean isDateCurrent(java.util.Date test,long Tolerance)
    {
         long testTime = test.getTime();
         long now = new Date().getTime();
         return(testTime + Tolerance >= now);
    }

    /**
      * true if test at or after the present time - Tolerance
      * @param test non=null test date
     * @return  true if so
       */
    public static boolean isDateCurrent(java.util.Date test)
    {
         return(isDateCurrent(test,DEFAULT_TOLERANCE));
    }

    public static FarTime pastTime(int minYears,int maxYears)
    {
         FarTime now = new FarTime();
         int nowTime = now.getTime();
         int start = MinutesPerYear * minYears;
         int interval_minutes = MinutesPerYear * (maxYears - minYears);
         int nMinutes = Util.randomInt(interval_minutes);
         start +=  nMinutes;
         FarTime ret = new FarTime(nowTime - start);
         return(ret);
    }

    public static FarTime futureTime(int minYears,int maxYears)
    {
         FarTime now = new FarTime();
         int nowTime = now.getTime();
         int start = MinutesPerYear * minYears;
         int interval_minutes = MinutesPerYear * (maxYears - minYears);
         int nMinutes = Util.randomInt(interval_minutes);
         start +=  nMinutes;
         FarTime ret = new FarTime(nowTime + start);
         return(ret);
    }
    //- *******************
    //- Methods
    /**{ method
     @name Static Initializer
     @function Code to initialize this class - called when loaded
     }*/
    static {
        int i = 0;
        // minutes per century
        CenturyMinutes = new int[32];
        CenturyMinutes[0] = 0;
        for (i = 1; i < 32; i++) {
            CenturyMinutes[i] = CenturyMinutes[i - 1] + MinutesPerCentury;
            if (((i - 1) % 4) == 0) {
                CenturyMinutes[i] += MinutesPerDay;
            }
            // add leap year each 400 years
        }
        MinutesToCurrentBase = CenturyMinutes[(1900 - FarBaseYear) / 100] + MinutesPerFourYears * 20;
        // to 1980
        YearMinutes = new int[101];
        YearMinutes[0] = 0;
        for (i = 1; i < 101; i++) {
            YearMinutes[i] = MinutesPerYear + YearMinutes[i - 1];
            // add leap year except for year 00
            if ((i > 1) && ((i - 1) % 4) == 0) {
                YearMinutes[i] += MinutesPerDay;
            }
            // add leap year
        }
        Calendar BaseDate = new GregorianCalendar(80, 6, 6);
        FarTime FTBaseDate = new FarTime(1980, 6, 6);
        MinutesToDateBase = (int) (FTBaseDate.getTime() - (BaseDate.getTime().getTime() / 60000));
    }

// end static initialization

    /**{ constructor
     @name FarTime
     @function Constructor of FarTime
     }*/
    public FarTime() {
        GregorianCalendar d = new GregorianCalendar();
        // m_fartime = d.getTime() / 60000; // convert msec to minutes
        m_valueValid = true;
        m_expanded = false;
        tm_min = (int) d.get(Calendar.MINUTE);
        /* minutes after the hour - [0, 59] */
        tm_hour = (int) d.get(Calendar.HOUR);
        /* hour since midnight - [0, 23] */
        tm_mday = (int) d.get(Calendar.DAY_OF_MONTH);
        /* day of the month - [1, 31] */
        tm_mon = (int) d.get(Calendar.MONTH);
        /* months since January - [0, 11] */
        tm_wday = 0;
        /* days since Sunday - [0, 6] */
        tm_year = d.get(Calendar.YEAR);
        if (tm_year < 100) {
            if (tm_year < 35)
                tm_year += 2000;
            else
                tm_year += 1900;
        }

        /* years since 1900 */
        tm_isdst = 0;
        /* flag for alternate daylight savings time */
        if (isLeapYear(tm_year)) {
            tm_yday = (int) ((tm_mday - 1) + LeapMonthCumulativeDays[tm_mon]);
        }
        /* days since January 1 - [0, 365] */
        else {
            tm_yday = (int) ((tm_mday - 1) + MonthCumulativeDays[tm_mon]);
        }
        /* days since January 1 - [0, 365] */
        computeValue();
    }

    /**{ constructor
     @name FarTime
     @function Constructor of FarTime
     @param l time to set - usually from getTime
     @see Overlord.FarTime#getTime
     }*/
    public FarTime(int l) {
        m_fartime = l;
        m_valueValid = true;
        m_expanded = false;
    }

    /**{ constructor
     @name FarTime
     @function Constructor of FarTime
     @param day day of month 1-31
     @param month 0-11
     @param year format 1980 or 80 years 0-49 convert to 2000-2049, 50-99
     become 1950-1999
     }*/
    public FarTime(int year, int month, int day) {
        this(year, month, day, 0, 0);
    }

    /**{ constructor
     @name FarTime
     @function Constructor of FarTime
     @param day day of month 1-31
     @param minutes 0-59
     @param hour 0-23
     @param month 0-11
     @param year format 1980 or 80 years 0-49 convert to 2000-2049, 50-99
     become 1950-1999
     }*/
    public FarTime(int year, int month, int day, int hour, int minutes) {
        m_valueValid = true;
        m_expanded = false;
        // send 05 to 2005, 52 to 1952
        if (year < 100) {
            if (year < 50) {
                year += 2000;
            } else {
                year += 1900;
            }
        }
        tm_min = (int) minutes;
        /* minutes after the hour - [0, 59] */
        tm_hour = (int) hour;
        /* hour since midnight - [0, 23] */
        tm_mday = (int) day;
        /* day of the month - [1, 31] */
        tm_mon = (int) month;
        /* months since January - [0, 11] */
        tm_wday = 0;
        /* days since Sunday - [0, 6] */
        tm_year = year;
        /* years since 1900 */
        tm_isdst = 0;
        /* flag for alternate daylight savings time */
        if (isLeapYear(year)) {
            tm_yday = (int) ((tm_mday - 1) + LeapMonthCumulativeDays[tm_mon]);
        }
        /* days since January 1 - [0, 365] */
        else {
            tm_yday = (int) ((tm_mday - 1) + MonthCumulativeDays[tm_mon]);
        }
        /* days since January 1 - [0, 365] */
        computeValue();
    }

    /**{ constructor
     @name FarTime
     @function Constructor of FarTime
     @UnusedParam> minutes 0-59
     @param day day of month 1-31
     @param month 0-11
     @param year format 1980 or 80 years 0-49 convert to 2000-2049, 50-99
     become 1950-1999
     @param hour 0-23
     @param minute minutes 0-59
     @param sec ignored - here for compatability
     }*/
    public FarTime(int year, int month, int day, int hour, int minute, int sec) {
        this(year, month, day, hour, minute);
    }

    /**{ constructor
     @name FarTime
     @function Constructor of FarTime
     @param d equivalent date
     }*/
    public FarTime(Calendar d) {
        this(1900 + d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH), d.get(Calendar.HOUR), d.get(Calendar.MINUTE));
    }

    /**{ constructor
     @name FarTime
     @function Constructor of FarTime
     @param date - copied time
     }*/
    public FarTime(FarTime date) {
        this(date.getTime());
    }

    /**{ constructor
     @name FarTime
     @function Constructor of FarTime
     @param s parse string into time
     }*/
    public FarTime(String s) {
        this(parse(s));
    }

    /**{ method
     @name toDate
     @function convert a FarTime to a Date if possible - fails if the FarTime
     is earlier than the Dates epoch (time = 0)
     @return constructed Date
     @policy rarely override
     @primary
     }*/
    public Calendar toDate() {
      //  if (m_fartime >= MinutesToDateBase) {
            return (new GregorianCalendar(getYear() - 1900, getMonth(), getDate(), getHours(), getMinutes()));
      //  }
        // cannot represent as Date
    //    return (null);
    }

    /**{ method
     @name getTime
     @function converts FarTime to a computable int
     @return the int - number minutes since 0 AD
     @policy rarely override
     @primary
     @returnnumber> of minutes since 0 AD
     @see Overlord.FarTime#setTime
     }*/
    public int getTime() {
        return (m_fartime);
    }

    /**{ method
     @name setTime
     @function set time from number of minutes
     @param newTime time as number of minutes since 0 AD
     @policy rarely override
     @primary
     @see Overlord.FarTime#getTime
     }*/
    public void setTime(int newTime) {
        if (m_fartime != newTime) {
            m_fartime = newTime;
            m_expanded = false;
        }
    }

    /**{ method
     @name centuryYear
     @function given the number minutes since start of century find
     the year
     @param minutes minutes since start of century
     @param CenturyIsLeap true it year x00 is a leapyear
     @return the year 0-99
     }*/
    static protected int centuryYear(int minutes, boolean CenturyIsLeap) {
        if (minutes <= MinutesPerYear) {
            return (0);
        }
        if (CenturyIsLeap) {
            minutes -= MinutesPerDay;
            // drop a leap day in year 1
        }
        int GuessYear = (int) (minutes / (MinutesPerYear + (MinutesPerDay / 4)));
        while (minutes >= YearMinutes[GuessYear + 1])
            GuessYear++;
        while (minutes < YearMinutes[GuessYear])
            GuessYear--;
        return (GuessYear);
    }

    /**{ method
     @name get
     @function <Add Comment Here>
     @param index <Add Comment Here>
     @return <Add Comment Here>
     @policy <Add Comment Here>
     }*/
    public int get(int index) {
        expand();
        switch (index) {
            case Calendar.HOUR:
                return (tm_hour);
            case Calendar.MINUTE:
                return (tm_min);
            case Calendar.DAY_OF_MONTH:
                return (getDate());
            case Calendar.MONTH:
                return (getMonth());
            case Calendar.YEAR:
                return (getYear());
            case Calendar.DAY_OF_WEEK:
                return (getDay());
            case Calendar.DAY_OF_YEAR:
                return (getYearDay());
            default :
                Assertion.fatalError();
                return (0);
        }
    }

    /**{ method
     @name getYearDay
     @function convert to day of year
     @return the day
     @policy rarely override
     @primary
     }*/
    public int getYearDay() {
        expand();
        return (tm_yday);
    }

    /**{ method
     @name getYear
     @function return the year
     @return the year
     @policy rarely override
     @primary
     }*/
    public int getYear() {
        expand();
        return (tm_year);
    }

    /**{ method
     @name getMonth
     @function Returns the month. This method assigns months with the
     values 0-11, with January beginning at value 0.
     @return the month
     @policy rarely override
     @primary
     }*/
    public int getMonth() {
        expand();
        return (tm_mon);
    }

    /**{ method
     @name getElement
     @function Returns the day of the month. This method assigns days
     with the values of 1 to 31.
     @return the day
     @policy rarely override
     @primary
     }*/
    public int getDate() {
        expand();
        return (tm_mday);
    }

    /**{ method
     @name getMinutes
     @function return minutes 0-59
     @return the minutes
     @policy rarely override
     @primary
     @see #setMinutes
     }*/
    public int getMinutes() {
        expand();
        return (tm_min);
    }

    /**{ method
     @name setMinutes
     @function set minutes
     @param minutes minutes 0-59
     @policy rarely override
     @primary
     @see #getMinutes
     }*/
    public void setMinutes(int minutes) {
        expand();
        tm_min = (int) minutes;
        computeValue();
    }

    /**{ method
     @name getDay
     @function return the day of the week
     @return day 0-6
     @policy rarely override
     @primary
     }*/
    public int getDay() {
        expand();
        return (tm_wday);
    }

    /**{ method
     @name getHours
     @function Returns the hour. This method assigns the value of the
     hours of the day to range from 0 to 23, with midnight equal
     to 0.
     @return the hour 0-23
     @policy rarely override
     @primary
     }*/
    public int getHours() {
        expand();
        return (tm_hour);
    }

    /**{ method
     @name setHours
     @function sets the hours
     @param hours hours the hour value
     @policy rarely override
     @primary
     }*/
    public void setHours(int hours) {
        expand();
        tm_hour = (int) hours;
        computeValue();
    }

    /**{ method
     @name equals
     @function test equality
     @param test date to test
     @return true if equal
     @policy rarely override
     @primary
     }*/
    public boolean equals(FarTime test) {
        if(test == null)
             return false;
        return (m_fartime == test.m_fartime);
    }

    /**{ method
     @name currentTime
     @function return a FarTime representing the durrent time
     @return the object
     }*/
    public static FarTime currentTime() {
        Calendar CurrentDate = new GregorianCalendar();
        return (new FarTime(CurrentDate));
    }

    /**{ method
     @name parseDate
     @function convert a string to a FarTime
     @param in the string
     @return teh Object
     }*/
    static public FarTime parseDate(String in) {
        return (new FarTime(parse(in)));
        // !!! test this
    }

    /**{ method
     @name parseLongDate
     @function convert a string to a date
     @param in the string
     @return the date
     }*/
    static public FarTime parseLongDate(String in) {
        return (new FarTime(parse(in)));
        // !!! test this
    }

    /**{ method
     @name parseShortDate
     @function convert a string to a date
     @param in the string
     @return the date
     }*/
    static public FarTime parseShortDate(String in) {
        return (new FarTime(parse(in)));
        // !!! test this
    }

    /**{ method
     @name stringToMonth
     @function Convert String "Jan" or "March" to month 0-11
     @param TestMonth inpout string
     @return month 0-11
     }*/
    static public int stringToMonth(String TestMonth) {
        for (int i = 0; i < 12; i++) {
            if (ShortMonth[i].equalsIgnoreCase(TestMonth)) {
                return (i);
            }
        }
        for (int i = 0; i < 12; i++) {
            if (LongMonth[i].equalsIgnoreCase(TestMonth)) {
                return (i);
            }
        }
        return (-1);
    }

    /**{ method
     @name stringToLongMonth
     @function Convert String "January" or "March" to month 0-11 only long form
     @param TestMonth inpout string
     @return month 0-11
     }*/
    static public int stringToLongMonth(String TestMonth) {
        for (int i = 0; i < 12; i++) {
            if (LongMonth[i].equalsIgnoreCase(TestMonth)) {
                return (i);
            }
        }
        return (-1);
    }

    /**{ method
     @name TimeSinceDate
     @function get number minutes since that
     @param year year since 0 Ad
     @param day 1-31
     @param month 0-11
     @return number minutes - negative if date in fuure
     }*/
    static public int TimeSinceDate(int year, int month, int day) {
        FarTime test = new FarTime(year, month, day);
        return (currentTime().m_fartime - test.m_fartime);
    }

    /**{ method
     @name YearsSinceTime
     @function return the number of years since the FarTime
     @return number years
     @policy rarely override
     @primary
     }*/
    public int YearsSinceTime() {
        return (currentTime().getYear() - getYear());
    }

    /**{ method
     @name YearsSinceDate
     @function get years since date
     @param year year since 0 Ad
     @param day 1-31
     @param month 0-11
     @return number years - negative if date in fuure
     }*/
    static public int YearsSinceDate(int year, int month, int day) {
        FarTime TheTime = new FarTime(year, month, day);
        return (currentTime().getYear() - TheTime.getYear());
    }

    /**{ method
     @name clone
     @function copy
     @return new Fartime
     @policy rarely override
     @primary
     }*/
    protected Object clone() {
        return (new FarTime(m_fartime));
    }

    /**{ method
     @name thisDay
     @function FarTime for midnight today
     @return FarTime for midnight today
     @policy rarely override
     @primary
     }*/
    public FarTime thisDay() {
        return (new FarTime(getYear(), getMonth(), getDate()));
    }

    /**{ method
     @name thisWeek
     @function FarTime for midnight Sunday this week
     @return the FarTime
     @policy rarely override
     @primary
     }*/
    public FarTime thisWeek() {
        int mon = getMonth();
        int dat = getDate();
        int year = getYear();
        FarTime now = new FarTime(year, mon, dat);
        int wday = getDay();
        if (wday != 0) {
            now.setTime(now.getTime() - wday * MinutesPerDay);
        }
        int test = now.getMonth();
        test = now.getDate();
        test = now.getYear();
        return (now);
    }

    /**{ method
     @name thisMonth
     @function return a FarTime set to the start of the current month
     @return the FarTime
     @policy rarely override
     @primary
     }*/
    public FarTime thisMonth() {
        return (new FarTime(getYear(), getMonth(), 0));
    }

    /**{ method
     @name thisQuarter
     @function return a FarTime set to the start of the current quarter
     1 jan, 1 Apr, 1 jul, 1 oct
     @return the FarTime
     @policy rarely override
     @primary
     }*/
    public FarTime thisQuarter() {
        int nMonth = getMonth();
        switch (nMonth) {
            case 0:
            case 1:
            case 2:
                nMonth = 0;
                break;
            case 3:
            case 4:
            case 5:
                nMonth = 3;
                break;
            case 6:
            case 7:
            case 8:
                nMonth = 6;
                break;
            case 9:
            case 10:
            case 11:
                nMonth = 9;
                break;
        }
        ;
        return (new FarTime(getYear(), nMonth, 0));
    }

    /**{ method
     @name nextMonth
     @function return a FarTime set to the start of the following month
     @return the FarTime
     @policy rarely override
     @primary
     }*/
    public FarTime nextMonth() {
        if (getMonth() < 11) {
            return (new FarTime(getYear(), getMonth() + 1, 0));
        } else {
            return (new FarTime(getYear() + 1, 0, 0));
        }
    }

    /**{ method
     @name thisYear
     @function Return a FarTime set to the start of the following month
     @return the FarTime
     @policy rarely override
     @primary
     }*/
    public FarTime thisYear() {
        return (new FarTime(getYear(), 0, 0));
    }

    /**{ method
     @name isLeapYear
     @function test if the year is a leap year
     @param year test year full i.e. 1945
     @return true if the year is a leap year
     }*/
    static public boolean isLeapYear(int year) {
        if ((year % 4) != 0) {
            return (false);
        }
        // not a multiple of 4
        if ((year % 100) != 0) {
            return (true);
        }
        // multiple of 4 not 100
        if ((year % 400) != 0) {
            return (false);
        }
        // multple of 100 not 400
        return (true);
    }

    /**{ method
     @name before
     @function test input to see if less than this time
     @param when test time
     @return true if when before this
     @policy rarely override
     @primary
     }*/
    public boolean before(FarTime when) {
        return getTime() < when.getTime();
    }

    /**{ method
     @name after
     @function test input to see if greater than this time
     @param when test time
     @return true if when after this
     @policy rarely override
     @primary
     }*/
    public boolean after(FarTime when) {
        return getTime() > when.getTime();
    }

    /**{ method
     @name hashCode
     @function convert to hash
     @return the hash
     @policy rarely override
     @primary
     }*/
    public int hashCode() {
        return (int) m_fartime ^ (int) (m_fartime >> 32);
    }

    /**{ method
     @name getTimezoneOffset
     @function get offset from GMT
     @return the offset in minutes
     @policy rarely override
     @primary
     }*/
    public int getTimezoneOffset() {
        GregorianCalendar d = new GregorianCalendar();
        TimeZone tz = d.getTimeZone();
        return (tz.getRawOffset());
    }

    /**{ method
     @name yearMinutes
     @function get number minutes in any year
     @param year teh year since 0 AD
     @return number minutes
     }*/
    static protected int yearMinutes(int year) {
        int Century = year / 100;
        int CenturyYear = year % 100;
        int ret = CenturyMinutes[Century];
        if (CenturyYear > 0) {
            ret += YearMinutes[CenturyYear];
            if (isLeapYear(100 * Century)) {
                ret += MinutesPerDay;
            }
        }
        return (ret);
    }

    /**{ method
     @name getCentury
     @function find the century 0-19 for this
     @return the century
     @policy rarely override
     @primary
     }*/
    protected int getCentury() {
        for (int i = 0; i < 31; i++) {
            if (m_fartime < CenturyMinutes[i + 1]) {
                return (i);
            }
        }
        return (31);
        // this is bad
    }

    /**{ method
     @name computeYear
     @function find the year for this
     @return the year
     @policy rarely override
     @primary
     }*/
    protected int computeYear() {
        int Century = getCentury();
        int CetYear = 100 * Century;
        int Yr = centuryYear(m_fartime - CenturyMinutes[Century], isLeapYear(CetYear));
        return (CetYear + Yr);
    }

    /**{ method
     @name computeMonth
     @function find the month given a day since jan 1
     @param day the day
     @param LeapYear true if year is leap
     @return month 0-11
     @policy rarely override
     @primary
     }*/
    protected int computeMonth(int day, boolean LeapYear) {
        int i;
        if (LeapYear) {
            for (i = 1; i < 12; i++) {
                if (day < LeapMonthCumulativeDays[i]) {
                    return (i - 1);
                }
            }
        } else {
            for (i = 1; i < 12; i++) {
                if (day < MonthCumulativeDays[i]) {
                    return (i - 1);
                }
            }
            return (11);
        }
        return (11);
    }

    /**{ method
     @name expand
     @function compute year, day ... needs only be done when date
     altered
     @policy rarely override
     @primary
     }*/
    protected void expand() {
        if (!m_expanded) {
            tm_year = computeYear();
            /* years since 00 */
            boolean LeapYear = isLeapYear(tm_year);
            tm_min = (int) (m_fartime % 60);
            while (tm_min < 0) tm_min += 60;

            /* minutes after the hour - [0, 59] */
            tm_hour = (int) ((m_fartime % (MinutesPerDay)) / 60);
            while (tm_hour < 0) tm_hour += 60;

            /* days since January 1 - [0, 365] */
            tm_yday = (int) ((m_fartime - yearMinutes(tm_year)) / MinutesPerDay);


            /* months since January - [0, 11] */
            tm_mon = (int) computeMonth(tm_yday, LeapYear);

            if (LeapYear) {
                tm_mday = (int) (tm_yday - LeapMonthCumulativeDays[tm_mon]);
            }
            /* day of the month - [1, 31] */
            else {
                tm_mday = (int) (tm_yday - MonthCumulativeDays[tm_mon]);
            }
            /* day of the month - [1, 31] */
            tm_mday++;


            // days of month to from 1 to 31
            tm_wday = (int) (((m_fartime / MinutesPerDay) + WeekDayOfBase) % 7);
            // base day was on a Tuesday
            tm_isdst = (int) 0;
            // ????
            m_expanded = true;
        }
    }

    /**{ method
     @name computeValue
     @function used internally by expand
     @policy rarely override
     @primary
     }*/
    protected void computeValue() {
        int YM = yearMinutes(tm_year);
        int DM = tm_yday * MinutesPerDay;
        int MN = tm_hour * 60 + tm_min;
        m_fartime = YM;
        m_fartime += DM;
        m_fartime += MN;
        tm_wday = (int) (((m_fartime / MinutesPerDay) + WeekDayOfBase) % 7);
        // base day was on a Tuesday
        m_expanded = true;
    }

    /**{ method
     @name testFarTime
     @function test code for this class
     @return true if OK
     }*/
    public static boolean testFarTime() {
        FarTime f1;
        FarTime f2;
        int f1time;
        int f2time;
        Calendar Date1;
        Calendar Date2;
        int dyear;
        int fyear;
        int dmonth = 0;
        int fmonth = 0;
        int dday;
        int fday;
        int dweekday = 0;
        int fweekday;
        boolean FarTimeIsValid = true;
        for (int year = 71; year < 120; year += 3) {
            for (int month = 1; month < 12; month++) {
                for (int day = 1; day < 28; day++) {
                    Date1 = new GregorianCalendar(year, month, day);
                    f1 = new FarTime(Date1);
                    f2 = new FarTime(1900 + year, month, day);
                    Date2 = f1.toDate();
                    dyear = 1900 + Date1.get(Calendar.YEAR);
                    fyear = f1.getYear();
                    dmonth = Date1.get(Calendar.MONTH);
                    fmonth = f1.getMonth();
                    dday = Date2.get(Calendar.DAY_OF_MONTH);
                    dday = Date1.get(Calendar.DAY_OF_MONTH);
                    fday = f1.getDate();
                    dweekday = Date1.get(Calendar.DAY_OF_MONTH);
                    fweekday = f1.getDay();
                    if (!Date1.equals(Date2)) {
                        FarTimeIsValid = false;
                    }
                    // break here
                    if (!f1.equals(f2)) {
                        FarTimeIsValid = false;
                    }
                    // break here
                    if (dyear != fyear) {
                        FarTimeIsValid = false;
                    }
                    // break here
                    if (dmonth != fmonth) {
                        FarTimeIsValid = false;
                    }
                    // break here
                    if (dday != fday) {
                        FarTimeIsValid = false;
                    }
                    // break here
                    if (dweekday != fweekday) {
                        FarTimeIsValid = false;
                    }
                    // break here
                }
            }
        }
        return (FarTimeIsValid);
    }

    /**{ method
     @name parse
     @function convert a string to a date
     @param s the string
     @return ???
     }*/
    public static int parse(String s) {
        int year = -1;
        int mon = -1;
        int mday = -1;
        int hour = -1;
        int min = -1;
        int sec = -1;
        int c = -1;
        int i = 0;
        int n = -1;
        int tzoffset = -1;
        int prevc = 0;
        syntax :
        {
            if (s == null)
                break syntax;
            int limit = s.length();
            while (i < limit) {
                c = s.charAt(i);
                i++;
                if (c <= ' ' || c == ',' || c == '-')
                    continue;
                if (c == '(') {
                    // skip comments
                    int depth = 1;
                    while (i < limit) {
                        c = s.charAt(i);
                        i++;
                        if (c == '(')
                            depth++;

                        else if (c == ')')
                            if (--depth <= 0)
                                break;
                    }
                    continue;
                }
                if ('0' <= c && c <= '9') {
                    n = c - '0';
                    while (i < limit && '0' <= (c = s.charAt(i)) && c <= '9') {
                        n = n * 10 + c - '0';
                        i++;
                    }
                    if (prevc == '+' || prevc == '-' && year >= 0) {
                        // timezone offset
                        if (n < 24)
                            n = n * 60;
                        // EG. "GMT-3"

                        else
                            n = n % 100 + n / 100 * 60;
                        // eg "GMT-0430"
                        if (prevc == '+')
                        // plus means east of GMT
                            n = -n;
                        if (tzoffset != 0 && tzoffset != -1)
                            break syntax;
                        tzoffset = n;
                    } else if (n >= 70)
                        if (year >= 0)
                            break syntax;

                        else if (c <= ' ' || c == ',' || c == '/' || i >= limit)
                            year = n;

                        else
                            break syntax;

                    else if (c == ':')
                        if (hour < 0)
                            hour = (int) n;

                        else if (min < 0)
                            min = (int) n;

                        else
                            break syntax;

                    else if (c == '/')
                        if (mon < 0)
                            mon = (int) n;

                        else if (mday < 0)
                            mday = (int) n;

                        else
                            break syntax;

                    else if (i < limit && c != ',' && c > ' ' && c != '-')
                        break syntax;

                    else if (hour >= 0 && min < 0)
                        min = (int) n;

                    else if (min >= 0 && sec < 0)
                        sec = (int) n;

                    else if (mday < 0)
                        mday = (int) n;

                    else
                        break syntax;
                    prevc = 0;
                } else if (c == '/' || c == ':' || c == '+' || c == '-')
                    prevc = c;
                else {
                    int st = i - 1;
                    while (i < limit) {
                        c = s.charAt(i);
                        if (!('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z'))
                            break;
                        i++;
                    }
                    if (i <= st + 1)
                        break syntax;
                    int k;
                    for (k = wtb.length; --k >= 0;)
                        if (wtb[k].regionMatches(true, 0, s, st, i - st)) {
                            int action = ttb[k];
                            if (action != 0)
                                if (action == 1)
                                // pm
                                    if (hour > 12 || hour < 0)
                                        break syntax;

                                    else
                                        hour += 12;

                                else if (action <= 13)
                                // month!
                                    if (mon < 0)
                                        mon = (int) (action - 2);

                                    else
                                        break syntax;

                                else
                                    tzoffset = action - 10000;
                            break;
                        }
                    if (k < 0)
                        break syntax;
                    prevc = 0;
                }
            }
            if (year < 0 || mon < 0 || mday < 0)
                break syntax;
            if (sec < 0)
                sec = 0;
            if (min < 0)
                min = 0;
            if (hour < 0)
                hour = 0;
        }
        FarTime ft = new FarTime(year, mon, mday, hour, min);
        if (tzoffset == -1)
        // no time zone specified, have to use local
            return (ft.getTime());

        else
            return (ft.getTime() + tzoffset);
    }

// New Formatting Code

    /**{ method
     @name hourString
     @function convert time - hour,minutes only into a string
     @return the string
     @policy rarely override
     @primary
     }*/
    public String hourString() {
        StringBuffer buf = new StringBuffer();
        buf.append(Integer.toString(getHours()));
        buf.append(':');
        int min = getMinutes();
        if (min < 10) {
            buf.append('0');
        }
        buf.append(Integer.toString(min));
        return (new String(buf));
    }

    /**{ method
     @name shortDateString
     @function generate a date string i.e. 12/3/97
     @return The String
     @policy rarely override
     @primary
     }*/
    public String shortDateString() {
        StringBuffer buf = new StringBuffer();
        buf.append(Integer.toString(1 + getMonth()));
        buf.append('/');
        buf.append(Integer.toString(getDate()));
        buf.append('/');
        int year = getYear();
        if (year > 1900 && year < 2000) {
            year -= 1900;
        }
        buf.append(Integer.toString(year));
        return (new String(buf));
    }

    /**{ method
     @name longDateString
     @function generate a date string i.e. 12 Dec 97
     @return The String
     @policy rarely override
     @primary
     }*/
    public String longDateString() {
        StringBuffer buf = new StringBuffer();
        buf.append(Integer.toString(getDate()));
        buf.append(' ');
        buf.append(ShortMonth[getMonth()]);
        buf.append(' ');
        int year = getYear() % 100;
        buf.append(Integer.toString(year));
        return (new String(buf));
    }

    /**{ method
     @name fullDateString
     @function generate a date string i.e. 12 December 1997
     @return The String
     @policy rarely override
     @primary
     }*/
    public String fullDateString() {
        StringBuffer buf = new StringBuffer();
        buf.append(LongWeek[getDay()]);
        buf.append(' ');
        buf.append(Integer.toString(getDate()));
        buf.append(' ');
        buf.append(LongMonth[getMonth()]);
        buf.append(' ');
        int year = getYear();
        buf.append(Integer.toString(year));
        return (new String(buf));
    }

    /**{ method
     @name formatShortDate
     @function make shortdate i.e. 6/9/48
     @param time time as given by getTime
     @return the string
     }*/
    public static String formatShortDate(int time) {
        return ((new FarTime(time)).shortDateString());
    }

    /**{ method
     @name formatLongDate
     @function make shortdate i.e. 6 June 1948
     @param time time as given by getTime
     @return the string
     }*/
    public static String formatLongDate(int time) {
        return ((new FarTime(time)).longDateString());
    }

    /**{ method
     @name formatHour
     @function make hour string i.e. 1:42
     @param time time as given by getTime
     @return the string
     }*/
    public static String formatHour(int time) {
        return ((new FarTime(time)).hourString());
    }

    /**{ method
     @name nameTimeInterval
     @function convert an interval to a string
     @param start start time
     @param Precision how many deciman points
     @param end end time
     @return the string
     }*/
    public static String nameTimeInterval(int start, int end, int Precision) {
        return (nameTimeInterval(start, end));
    }

    /**{ method
     @name nameTimeInterval
     @function convert an interval to a string
     @param start start time
     @param end end time
     @return the string
     }*/
    public static String nameTimeInterval(int start, int end) {
        if (start == 0 || end == 0) {
            return ("");
        }
        if ((end - start) < MinutesPerDay) {
            if ((start % MinutesPerDay) < (18L * 60)) {
                return (formatShortDate(start));
            } else {
                return (formatShortDate(end));
            }
        } else {
            FarTime StartTime = new FarTime(start);
            FarTime EndTime = new FarTime(end);
            if (StartTime.getYear() == EndTime.getYear()) {
                String s;
                if (StartTime.getMonth() == EndTime.getMonth()) {
                    s = StartTime.getDate() + " - " + EndTime.getDate() + ShortMonth[EndTime.getMonth()] + " " + (EndTime.getYear() % 100);
                } else {
                    s = (StartTime.getMonth() + 1) + "/" + StartTime.getDate() + " - " + (EndTime.getMonth() + 1) + "/" + EndTime.getDate() + (EndTime.getYear() % 100);
                }
                return (s);
            } else {
                return (formatShortDate(start) + " - " + formatShortDate(end));
            }
        }
    }

    /**{ method
     @name nameValueInTimeInterval
     @function given a time interval make a string representing a time
     @param Precision points to righ tof decimal
     @param value value to mane
     @param end interval end
     @param start interval start
     @return the string
     }*/
    public static String nameValueInTimeInterval(int start, int end, int value, int Precision) {
        // less that one day then do hours
        if ((end - start) < MinutesPerDay) {
            return (formatHour(value));
        } else {
            String s = formatShortDate(value);
            return (s);
        }
    }


//- *******************
//- End Class FarTime
}
