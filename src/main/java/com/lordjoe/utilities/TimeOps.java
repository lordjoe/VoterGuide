package com.lordjoe.utilities;

import com.lordjoe.lang.*;

import java.util.*;
import java.text.*;

/**
 * com.lordjoe.utilities.TimeOps
 *
 * @author Owner
 * @date Mar 28, 2006
 */
public abstract class TimeOps
{
    public static Class THIS_CLASS = TimeOps.class;
    public static final TimeOps[] EMPTY_ARRAY = {};

    private TimeOps()
    {
    } // never build this

    public static final int MAX_TIME_ERROR_MILLIS = 10000;

    private static long gNanoToMillis = 0;
    private static long gLastTime = TimeOps.now();

    /**
     * current time in millisec based on nanotime
     *
     * @return as above
     */
    public static synchronized long now()
    {
        long nano = System.nanoTime();


        long ret = nano / 1000000; // convert to millisec
        if (gNanoToMillis == 0) {
            long sysNow = System.currentTimeMillis();
            gNanoToMillis = sysNow - ret;
        }
        ret += gNanoToMillis; // go to systime
//        long sysTime =  System.currentTimeMillis();
//        if(Math.abs(sysTime - ret) > MAX_TIME_ERROR_MILLIS) {
//            String message = "Timing Error of " + (sysTime - ret) + " millisec";
//             DeviceFactory.getErrorLogger().appendText(message);
//             GeneralUtilities.printString(message);
//
//        }
        if (ret < gLastTime) {
            long del = gLastTime - ret;
            if (del < MAX_TIME_ERROR_MILLIS)
                return gLastTime; // NEVER go backwards
            String message = "Timing Error Backward Step!!!! of " + del + " millisec";
         //   DeviceFactory.getErrorLogger().appendText(message);
            GeneralUtilities.printString(message);
        }
        gLastTime = ret;
        return ret;
    }
	
	/**
     * return an sql Date for the current time
    *
     * @return
     */
    public static java.sql.Date nowSQL()
    {
		return new java.sql.Date(now());
	}

    /**
     * return a string of todays date time suitable
     * this is in more international form that the version above
     *
     * @return
     */
    public static String getNowString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd MMM yyyy HH:mm:ss"); // ->  23 Jan 2006 16:08:56
        long now = now();
        Date date = new Date(now);
        final String dateText = sdf.format(date);
        return dateText;
    }




}

