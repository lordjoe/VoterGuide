
/* 
    This is just like a java FIle but may be on a remote
    system - allows comparison of remote and local files
    and dates 
    Any File subclass will implement this interface
*/
package com.lordjoe.lib.ftp;
import java.util.Calendar;

public interface IFileDescriptor 
{
    public String getName();
    public String getPath();
    public boolean isDirectory();
    public boolean isFile();
    public String getDateString();
    public long length();
}
