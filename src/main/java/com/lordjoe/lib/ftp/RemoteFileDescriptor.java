/* 
    This is just like a java FIle but may be on a remote
    system - allows comparison of remote and local files
    and dates 
    Any File subclass will implement this interface
*/
package com.lordjoe.lib.ftp;

import com.lordjoe.utilities.*;
import java.util.*;

public class RemoteFileDescriptor implements IFileDescriptor 
{
    public static final int REQUIRED_TOKENS = 8;
    public static final int ACCESS_ITEM = 0;
    public static final int I1_ITEM = 1;
    public static final int I2_ITEM = 2;
    public static final int I3_ITEM = 3;
    public static final int LENGTH_ITEM = 4;
    public static final int MONTH_ITEM = 5;
    public static final int DAY_ITEM = 6;
    public static final int TIME_ITEM = 7;
    public static final int NAME_ITEM = 8;
    
    private String   m_name;
    private String   m_path;
    private long     m_length;
    private String   m_dateString;
    private boolean  m_IsDirectory;
    
    public RemoteFileDescriptor() 
    {
    }
    
    public RemoteFileDescriptor(String path,String[] attributes) 
    {
        try { 
            m_path = path;
            if(attributes[ACCESS_ITEM].indexOf("d") >= 0)
                m_IsDirectory = true;
            
            m_length = Long.parseLong(attributes[LENGTH_ITEM]);
            m_dateString = makeDate(attributes);
            m_name = attributes[NAME_ITEM];
        }
        catch( NumberFormatException ex) {
            throw new TransferFailureException("Bad Remote File Descriptor");
        }
    }
    
    protected String makeDate(String[] attributes) 
    {
     int year = 1999; // fix this
        int month;
        int day;
        int hour;
        int minute;
        
        month = stringToMonth(attributes[MONTH_ITEM]);
        
        day = Integer.parseInt(attributes[DAY_ITEM]);
        
        String[] hourMin = Util.parseTokenDelimited(attributes[TIME_ITEM],':');
        hour = Integer.parseInt(hourMin[0]);
        minute = Integer.parseInt(hourMin[1]);
        
        Calendar cal = new GregorianCalendar(year,month,day,hour,minute);
        return(Manifest.makeTimeString(cal.getTime().getTime()));
        
    }
    
    
    public String getName(){ return(m_name); } 
    public String getPath(){ return(m_path); } 
    public boolean isDirectory() { return(m_IsDirectory); }
    public boolean isFile() { return(!m_IsDirectory); }
    public String getDateString() { return(m_dateString); }
    public long length() { return(m_length); } 
    
    public static String[] MONTH_NAMES = { "jan","feb","mar","apr","may","jun","jul","aug","sep","oct","nov","dec" };
    public static int stringToMonth(String in) 
    {
        for(int i = 0; i < MONTH_NAMES.length; i++) 
        {
            if(MONTH_NAMES[i].equalsIgnoreCase(in)) return(i);
        }
        return(0); // really this is an error
    }
    
}
