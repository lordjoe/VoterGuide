package com.lordjoe.lang;




/**
 * com.lordjoe.lang.StringLoggerObject
 *
 * @author slewis
 * @date May 2, 2005
 */
public class StringLoggerObject extends AbstractLoggerObject
{
    public static final Class THIS_CLASS = StringLoggerObject.class;
    public static final StringLoggerObject EMPTY_ARRAY[] = {};

    private final StringBuffer  m_Sb;
    private int  m_Mark;


    public StringLoggerObject(String name)
    {
        super(name);
        m_Sb = new StringBuffer();
    }

    /**
     * return the current loggers text
     *
     * @return non-null string
     */
    public synchronized String getText()
    {
        String s = m_Sb.toString();
        return s;
    }

    /**
     * return the current loggers text
     *
     * @return non-null string
     */
    public synchronized String getTextFromMark()
    {
        return m_Sb.substring(m_Mark);
    }

    /**
     * mark the text in this logger so only newer text can be
     * retrieved by getTextFromMark
     */
    public synchronized void setMark()
    {
        m_Mark = m_Sb.length();
    }

    /**
     * return the number of bytes stored
     *
     * @return
     */
    public synchronized int getSize()
    {
         return (int)m_Sb.length();
    }



    /**
     * clear the logger
     */
    public synchronized void clear()
    {
        m_Sb.setLength(0);
        m_Mark = 0;
        notifyChangeListeners(LOG_CLEARED);
    }

    /**
       * clear the logger
       */
      public synchronized void flush()
      {
      }
    

    /**
     * append text to the logger
     * @param text non-null string to add
     */
    public synchronized void appendText(String text)
    {
       m_Sb.append(text);
        String s = m_Sb.toString();
        notifyChangeListeners(LOG_APPENDED,text);
    }

    /**
     * return the number of pages of the given size in the
     * log
     *
     * @param pageSize positive page size
     * @return number of pages
     */
    public synchronized  int getNumberPages(int pageSize)
    {
       return ((m_Sb.length() + pageSize - 1 )  / pageSize);
    }

    /**
     * return the page
     *
     * @param pageSize size of the page
     * @return
     */
    public synchronized String getPage(int page, int pageSize)
    {
         synchronized(m_Sb) {
             int len = m_Sb.length();
             int start = page * pageSize;
             if(start >= len)
                return "";
             int end = Math.min(len,start + pageSize);
             return m_Sb.substring(start,end);
         }
    }




}
