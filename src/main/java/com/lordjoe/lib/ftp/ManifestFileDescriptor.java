/* 
    This is just like a java FIle but may be on a remote
    system - allows comparison of remote and local files
    and dates 
    Any File subclass will implement this interface
*/
package com.lordjoe.lib.ftp;

import com.lordjoe.utilities.*;
import java.util.*;

public class ManifestFileDescriptor implements IFileDescriptor 
{    
    public static final int REQUIRED_TOKENS = 4;
    public static final int NAME_ITEM = 0;
    public static final int PATH_ITEM = 1;
    public static final int LENGTH_ITEM = 2;
    public static final int DATE_ITEM = 3;

    private String   m_name;
    private String   m_path;
    private long     m_length;
    private String   m_dateString;
    
    public ManifestFileDescriptor() 
    {
    }
    
    public ManifestFileDescriptor(String[] attributes) 
    {
        this();
        try { 
            if(attributes.length < REQUIRED_TOKENS)
                Assertion.validate(attributes.length >= REQUIRED_TOKENS);
            m_path = attributes[PATH_ITEM];
            m_length = Long.parseLong(attributes[LENGTH_ITEM]);
            m_dateString = attributes[DATE_ITEM];
            m_name = attributes[NAME_ITEM];
        }
        catch( NumberFormatException ex) {
            throw new TransferFailureException("Bad Manifest File");
        }
    }
    
    
    public String getName(){ return(m_name); } 
    public String getPath(){ return(m_path); } 
    // cannot handle directories
    public boolean isDirectory() { return(false); }
    public boolean isFile() { return(true); }
    public String getDateString() { return(m_dateString); }
    public long length() { return(m_length); } 
    
    //
    // return MMDDhhmmYYYY.ss assuming Date is seconds 
    //   since 1970
    // 
    protected static FarTime BaseDate = new FarTime(1970,0,1);
    protected static int gOffset = -1; // not initialized
    
    @SuppressWarnings(value = "deprecated")
    protected static int getTimeOffset()
    {
        if(gOffset == -1) { // not initialized
            Date date1 = new Date(0); // 1/1/70 GMT
            Date date2 = new Date(70,0,1,0,0); //  1/1/70 Local
            long testt1 = date1.getTime() / 60000;
            long testt2 = date2.getTime() / 60000;
            gOffset = (int)(testt1 - testt2 );
        }
        return(gOffset);
    }

    
}
