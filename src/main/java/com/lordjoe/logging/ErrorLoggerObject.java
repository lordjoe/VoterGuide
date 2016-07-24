package com.lordjoe.logging;

import com.lordjoe.utilities.*;

import java.util.*;
import java.io.*;
import com.lordjoe.lang.*;
/**
 * com.lordjoe.logging.ErrorLoggerObject
 *
 * @author Steve Lewis
 * @date Jun 5, 2008
 */
public class ErrorLoggerObject extends StringLoggerObject
{
    public static ErrorLoggerObject[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ErrorLoggerObject.class;
    public static final LogEnum ERROR_EUNM = LogEnum.getInstance("Error Log");

    public static final int MAX_VISIBLE_TEXT = 120;
//    public static final File ERROR_LOG_FILE = new File("ErrorLog.log");

    private String m_LastText;

    public ErrorLoggerObject()
    {
        super(ERROR_EUNM.getName());
//        DeviceFactory.setExceptionLogger(this);
//        DeviceFactory.registerErrorLogger(this);
 //       ExceptionHandlingManager.addExceptionLogger(this);
    }

    /**
     * clear the logger
     */
    public synchronized void flush()
    {
        super.flush();
    }

    /**
       * perform some exception logging
       *
       * @param ex mom-mull exception
       */
      public synchronized void logException(Throwable ex)
      {
          appendException(ex);
      }

    /**
     * perform some exception logging
     *
     * @param data possibly-mull data to add to log
     * @param ex   mom-mull exception
     */
    public synchronized void logException(Object data, Throwable ex)
    {
        ex.printStackTrace();
        appendText(buildExceptionText(data,ex));
     }

    /**
     * special code to log an exception
     *
     * @param ex non-null exception
     */
    public synchronized void appendException(Throwable ex)
    {
        appendText(buildExceptionText(ex));
    }

    /**
     * special code to log an exception
     *
     * @param ex      non-null exception
     * @param message possibly null message
     */
    public synchronized void appendException(Throwable ex, String message)
    {
        appendText(buildExceptionText(ex,message));
    }

    protected String buildExceptionText(Object data,Throwable ex)
     {
         long elapsedTime = System.currentTimeMillis();
         elapsedTime = Math.max(elapsedTime,0);
         String time = TimeDuration.buildElapsedTimeString(elapsedTime);
         String message = (String) (data != null ? data.toString() : null);
         return buildExceptionText(ex,time + " " + message);
     }

    protected String buildExceptionText(Throwable ex)
     {
        return buildExceptionText(ex,(String)null);
     }

    protected String buildExceptionText(Throwable ex, String message)
     {
         StringBuffer sb = new StringBuffer();
         StringWriter sw = new StringWriter();
         ex.printStackTrace(new PrintWriter(sw));
         sb.append("An error occurred at " + Util.getNowString() + "\n");
         if(!StringOps.isBlank(message))
            sb.append("\n" + message);
         sb.append(sw.getBuffer().toString());
            return sb.toString();
     }

    /**
     * append text to the logger
     * @param text non-null string to add
     */
    public synchronized void appendText(String text)
    {
        String fullText = text;
        String last = m_LastText; // for visualization
        String activeText = buildActiveText(text);
        if(activeText.equals(m_LastText))
            return; // prevent double logging
        m_LastText = activeText;
        String time = TimeDuration.buildElapsedTimeString(System.currentTimeMillis());

        String newText = time + " " + text;
        if(!newText.endsWith("\n"))
              newText += "\n";
        super.appendText(newText);
   //     DeviceUtilities.saveLog(getSynth(), this);
 //       FileUtil.appendText(ERROR_LOG_FILE,fullText);
    }

    protected static String buildActiveText(String text)
    {
        int index = text.indexOf(" ");
        if(index > -1)
            return text.substring(index);
        else
            return text;
    }


    protected static String buildAbbreviatedText(String text)
    {
        String[] lines = text.split("\n");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            String newLine = buildAbbreviatedLine(line);
            sb.append(newLine);
            sb.append("\n");
        }

        return sb.toString();

    }

    protected static String buildAbbreviatedLine(String text)
    {
        if(text.length() > MAX_VISIBLE_TEXT)
          return text.substring(0,MAX_VISIBLE_TEXT) + "...";
        else
           return text;
    }

//    /**
//     * what to do when an unhandled error occurs
//     *
//     * @param evt
//     */
//    public void onError(ErrorEvent evt)
//    {
//       throw new UnsupportedOperationException("Fix This"); // ToDo
//
//    }
}
