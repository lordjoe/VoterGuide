package com.lordjoe.applications;

import com.lordjoe.utilities.*;

import java.io.*;

/**
 * com.lordjoe.applications.Comics
 *
 * @author Steve Lewis
 * @date Oct 9, 2006
 */
public class Comics
{
    public static final Comics[] EMPTY_ARRAY = {};
    public static final Class THIS_CLASS = Comics.class;

    public static final String SOURCE_URL = "http://angadi.dnsalias.com:1234/comics.html";
    public static final String TARGET_URL = "file:///C:/Temp/Comics.html";
    public static final String TARGET_FILE = "C:/Temp/Comics.html";
    public static final String TARGET_DIR = "C:/Temp";

    public static String adjustOriginal(String original)
    {
        String search1 = "border=0> Image </a";
        int index = original.indexOf(search1);
        while(index > -1)  {
            original = original.replace(search1,"\"border=0 />");
            original = original.replace(": <a href=",": <img src=");
            index = original.indexOf(search1);
        }
        return original;
    }
    public static void main(String[] args) throws IOException
    {
        String original = FileUtilities.downloadURLText(SOURCE_URL);
        String adjusted = adjustOriginal(original);
        FileUtilities.guaranteeDirectory(TARGET_DIR);
        FileUtilities.writeFile(TARGET_FILE,adjusted);
        BrowserLauncher.openURL(TARGET_URL);
    }
}
