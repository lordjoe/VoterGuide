package com.lordjoe.runner;


import java.util.*;
import java.io.*;
import java.text.*;


/**
 * com.lordjoe.runner.RunActionUtilities
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public abstract class RunActionUtilities
{
    public static RunActionUtilities[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunActionUtilities.class;

    public static final String HUGE_FILE_NAME = "FriggingHugeFile.zip";
    public static final long HUGE_FILE_SIZE = 7L * 1000L * 1000L * 1000L * 1000L; // 7 TB


    public static final int MAX_POLL_INTERVAL_MILLIS = 15 * 1000;

    public static final String[] FIXED_FILE_NAMES = {"garbage1.txt",
            "garbage2.txt",
            "garbage3.txt",
            "garbage4.txt",
    };
    public static final Set FIXED_FILES = new HashSet(Arrays.asList(FIXED_FILE_NAMES));

    private RunActionUtilities()
    {
    }


    public static long buildRunActionLong(Object item)
    {
        if (item instanceof Number)
            return ((Number) item).longValue();
        return Long.parseLong(item.toString());
    }


    /**
     * return a string describing the elapsed time between
     * start as System.currentTImeMillisec and end
     *
     * @param start as above
     * @return
     */
    public static String getTimingString(long start)
    {
        long end = System.currentTimeMillis();
        long del = end - start;
        int sec = (int) (del / 1000);
        int dec = (int) ((del - 1000 * sec) / 10);
        return Integer.toString(sec) + "." + dec;
    }


    public static String buildDefaultRunActionFileName()
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String s = "RunAction-" + df.format(new Date());
        return s;
    }

    public static String buildExceptionString(Throwable thr)
    {
        String message = thr.getMessage();
        String className = thr.getClass().getName();
        StringBuilder sb = new StringBuilder();
        sb.append("Exception: " + className + "\n");
        if (message != null)
            sb.append("Messsage: " + message + "\n");
        sb.append("\n");
        StackTraceElement[] sts = thr.getStackTrace();
        for (int i = 0; i < sts.length; i++) {
            StackTraceElement st = sts[i];
            sb.append(st.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * these files are automatically generated  and
     * may not be gone after delete
     *
     * @param file
     * @return
     */
    public static boolean isFixedFile(String file)
    {
        return FIXED_FILES.contains(file);
    }


    public static String[] filterFiles(String[] files, String opType)
    {
        if ("DELETE".equals(opType)) {
            List<String> holder = new ArrayList<String>();
            String[] ret = new String[holder.size()];
            holder.toArray(ret);
            return ret;
        }
        if ("DOWNLINK".equals(opType)) {
            List<String> holder = new ArrayList<String>();
            for (int i = 0; i < files.length; i++) {
                String file = files[i];
                if (file.endsWith("CONFIG"))
                    continue;
                if (!file.startsWith("garbage")) {
                    holder.add(file);
                }
            }
            String[] ret = new String[holder.size()];
            holder.toArray(ret);
            return ret;
        }
        throw new RunActionFailureException("Optype nuest be DELETE or DOWNLINK not " + opType);
    }

    public static String buildNewCommandId()
    {
        String s = UUID.randomUUID().toString();
        s = s.replace("-", "");
        return s;
    }

    public static String fileToPartId(File theFile)
    {
        return partIdFromFileName(theFile);
    }

    private static String partIdFromFileName(File theFile)
    {
        String s = theFile.getName();
        s = s.substring(0, s.indexOf(".")); // drop extennsion
        int index = s.indexOf("_");
        if (index > -1)
            s = s.substring(0, index);
        return s;
    }


}
