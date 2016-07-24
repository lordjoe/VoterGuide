package com.lordjoe.utilities;

import java.util.*;
import java.text.*;

/**
 * com.lordjoe.utilities.DateParser
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public abstract class DateParser
{
    public static DateParser[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = DateParser.class;


    public static Date parseDate(String s)
    {
        if(s == null)
            return null;
        if(s.contains("-"))
             return parseDashDate(s);
        if(s.contains("/"))
             return parseSlashDate(s);
        if(s.length() > 7 && s.matches("A..Z"))
            return parseNamedDate(s);
        if(s.length() < 4)
            return null;
        throw new IllegalArgumentException("cannot parse date " + s);

      }
    protected static Date parseDashDate(String s)
    {
        Date d = null;
        d = tryParse(s,"dd-MM-yyyy");
         if(d != null)
             return d;
         d = tryParse(s,"dd-MM-yy");
         if(d != null)
             return d;
        d = tryParse(s,"dd-MMM-yyyy");
         if(d != null)
             return d;
        d = tryParse(s,"dd-MMM-yyyy");
         if(d != null)
             return d;
         d = tryParse(s,"MMM-dd-yyyy");
         if(d != null)
             return d;
          d = tryParse(s,"yyyy-MM-dd");
        if(d != null)
            return d;
        throw new IllegalArgumentException("cannot parse date " + s);
    }
    protected static Date parseNamedDate(String s)
    {
        Date d = null;
        d = tryParse(s,"dd MMM yyyy");
        if(d != null)
            return d;
        d = tryParse(s,"dd MMM yy");
        if(d != null)
            return d;
        d = tryParse(s,"dd-MMM-yyyy");
         if(d != null)
             return d;
         d = tryParse(s,"dd-MMM-yy");
         if(d != null)
             return d;
        throw new IllegalArgumentException("cannot parse date " + s);
    }
    protected static Date parseSlashDate(String s)
    {
        Date d = null;
        d = tryParse(s,"dd/MM/yyyy");
        if(d != null)
            return d;
        d = tryParse(s,"dd/MM/yy");
        if(d != null)
            return d;
        throw new IllegalArgumentException("cannot parse date " + s);
    }

    protected static Date tryParse(String date,String s)
    {
        try {
            return new SimpleDateFormat(s).parse(date);
        }
        catch (ParseException e) {
            return null;
        }
    }
}
