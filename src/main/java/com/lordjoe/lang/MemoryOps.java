/**

 *

 * .
 *
 *
 *
 */

package com.lordjoe.lang;


import com.lordjoe.utilities.*;

import java.lang.reflect.*;
import java.text.*;

/**
 * Title:        Miscellaneous
 * Description:
 * Company:      Lordjoe

 */



/***************************************************************************************************
 *
 *  <!-- MemoryOps -->
 *
 *  This class defines static methods that perform various memory related operations.
 *
 ***************************************************************************************************
 */
public class MemoryOps
{

    public static final long MINIMUM_GC_FORCE_INTERVAL = 2000; // every second
    private static long gLastForcedGarbageCollection;

    /** This class should not be instantiated. */
    private MemoryOps()
    {
    }

    public static synchronized void forceGarbageCollection()
    {
        long now = System.currentTimeMillis();
        if((now - gLastForcedGarbageCollection)  < MINIMUM_GC_FORCE_INTERVAL)
             return;
         gLastForcedGarbageCollection = now;
        System.gc();

    }

    /**
     * copy the array as the samer type
     * @param inp non-null array
     * @return  as above
     */
    public static Object[] cloneArray(Object[] inp)
    {
        Object[] ret = (Object[])Array.newInstance(inp.getClass().getComponentType(),inp.length);
        System.arraycopy(inp,0,ret,0,inp.length);
        return ret;
    }


    public static NumberFormat getCommaDelimited()
    {
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(true); // for example, 1,000 instead of 1000
        return nf;
    }

    public static String toStringCommaDelimited(final long value)
    {
        final NumberFormat nf = getCommaDelimited();
        return nf.format(value);
    }

    public static long getFreeMemory()
     {
        // System.gc();
         Runtime runtime = Runtime.getRuntime();
         long freeMemory = runtime.freeMemory();
         long retrievablememory = 0;
         long maxMem = runtime.maxMemory();
         if(maxMem < Long.MAX_VALUE)
            retrievablememory  =  maxMem -  runtime.totalMemory();
         return freeMemory + retrievablememory;
     }
    

    public static long getMemoryUsedMemory()
     {
         System.gc();
         Runtime runtime = Runtime.getRuntime();
         final long totalMemory = runtime.maxMemory();
         final long freeMemory = runtime.freeMemory();
         long used = totalMemory - freeMemory;
         return used;
     }

    public static String getMemoryUsedMemoryString()
     {
         System.gc();
         long used = getMemoryUsedMemory();
         return StringOps.formatMega(used);
     }

    public static String getTotalMemoryString()
     {
         System.gc();
         long used = Runtime.getRuntime().maxMemory();
         return StringOps.formatMega(used);
     }

    public static String getMemoryString()
    {
        final long totalMemory = Runtime.getRuntime().maxMemory();
        final long freeMemory = Runtime.getRuntime().freeMemory();

        final String totalMemoryString = MemoryOps.toStringCommaDelimited(totalMemory);
        final String freeMemoryString  = MemoryOps.toStringCommaDelimited(freeMemory);
        final String usedMemoryString  = MemoryOps.toStringCommaDelimited(totalMemory-freeMemory);

        final String memoryString =
              "totalMemory = " + totalMemoryString + " bytes, "
            + "freeMemory = "  + freeMemoryString  + " bytes, "
            + "usedMemory = "  + usedMemoryString  + " bytes";
        return memoryString;
    }


    public static String getMemoryStringCommaDelimited(final long totalMemory, final long freeMemory)
    {
        final String totalMemoryString = MemoryOps.toStringCommaDelimited(totalMemory);
        final String freeMemoryString  = MemoryOps.toStringCommaDelimited(freeMemory);
        final String usedMemoryString  = MemoryOps.toStringCommaDelimited(totalMemory-freeMemory);
        
        final String memoryString =
              "totalMemory = " + totalMemoryString + " bytes, "
            + "freeMemory = "  + freeMemoryString  + " bytes, "
            + "usedMemory = "  + usedMemoryString  + " bytes";
        return memoryString;
    }

    
    public static void reportMemory(final String prefixLabel)
    {
        final String memoryString = getMemoryString();
     }

    public static void cleanMemory(final int count, final long milliseconds, final String reportLabel)
    {
        for (int i = 0; i < count; i++)
        {
            System.gc();
            try
            {
                final String threadName = Thread.currentThread().getName();
               // logger.debug("cleanMemory() sleep in \"" + threadName + "\" thread");
                Thread.sleep(milliseconds);
            }
            catch(InterruptedException ex)
            {
                throw new WrapperException(ex);
            }
        }
        reportMemory(reportLabel);
    }

} // end MemoryOps
