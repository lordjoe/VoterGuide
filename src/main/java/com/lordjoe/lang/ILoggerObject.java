package com.lordjoe.lang;

import com.lordjoe.utilities.*;

/**
 * com.lordjoe.lang.ILoggerObject
 *  interface to a logger
 * @author slewis
 * @date May 2, 2005
 */
public interface ILoggerObject extends INamedObject
{
    public static final Class THIS_CLASS = ILoggerObject.class;
    public static final ILoggerObject EMPTY_ARRAY[] = {};
    public static final int LOG_CLEARED = 1;
    public static final int LOG_APPENDED = 2;
    /**
      * return the current loggers text
      * @return non-null string
      */
     public String getText();

    /**
      * return the current loggers text
      * @return non-null string
      */
     public String getTextFromMark();

    /**
     * mark the text in this logger so only newer text can be
     * retrieved by getTextFromMark
     */
     public void setMark();

    /**
     * return the number of bytes stored
     * @return
     */
    public int getSize();
    /**
      * clear the logger
      */
     public void clear();
    /**
      * flush the logger
      */
     public void flush();

    /**
     * append text to the logger
     * @param text non-null string to add
     */
    public void appendText(String text);

    /**
     * return the number of pages of the given size in the
     * log
     * @param pageSize  positive page size
     * @return number of pages
     */
    public int getNumberPages(int pageSize);


    /**
     * return the page
     * @param pageSize size of the page
     * @return
     */
    public String getPage(int page,int pageSize);

    /**
     * add a listener for changes
     * @param added
     */
    public void addLogChangedListener(LogChangedListener added);

    /**
      * remove a listener for changes
      * @param removed
      */
     public void removeLogChangedListener(LogChangedListener removed);

}