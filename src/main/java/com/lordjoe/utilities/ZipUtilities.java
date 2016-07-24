package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;


import java.io.*;
import java.util.*;
import java.util.zip.*;



/*
 * com.lordjoe.Utilities.ZipUtilities
 * @author smlewis
 * Date: Dec 3, 2002
 */

public class ZipUtilities 
{
    public static final Class THIS_CLASS = ZipUtilities.class;
    public static final ZipUtilities[] EMPTY_ARRAY = {};

    public static final int BUFFER_SIZE = 1024;


  public static final void copyInputStream(InputStream in, OutputStream out)
  throws IOException
  {
    byte[] buffer = new byte[BUFFER_SIZE];
    int len;

    while((len = in.read(buffer)) >= 0)
      out.write(buffer, 0, len);

    in.close();
    out.close();
  }
    public static void unzip(String source)
      {
           unzip(source,System.getProperty("user.dir"));
      }

    public static void unzip(String source, String targetDirectory)
      {
           unzip(source,new File(targetDirectory));
      }

    public static void unzip(String source, File targetDirectory)
     {
          unzip(new File(source),targetDirectory);
     }


     public static void unzip(File source, File targetDirectory)
    {
       Enumeration entries;
       ZipFile zipFile;


       try {
         zipFile = new ZipFile(source);

         entries = zipFile.entries();

         while(entries.hasMoreElements()) {
           ZipEntry entry = (ZipEntry)entries.nextElement();
           File dest = new File(targetDirectory,entry.getName());
           if(entry.isDirectory()) {
             // Assume directories are stored parents first then children.
              // This is not robust, just for demonstration purposes.
             dest.mkdir();
             continue;
           }
           InputStream inp = zipFile.getInputStream(entry);
           OutputStream out = new FileOutputStream(dest);
           FileUtilities.copyStream(inp,out);
         }

         zipFile.close();
       } catch (IOException ioe) {
         System.err.println("Unhandled exception:");
         ioe.printStackTrace();
         return;
       }
     }

    public static void zip(String source)
      {
          zip(source,source + ".zip");
      }

    /**
     * zip a string to a zip file
     * @param sourceData String data
     * @param entryName  file name - proabaly needs to be top level
     * @param destFileName  zip dile name
     */
    public static void zipString(String sourceData,String entryName,String destFileName)
      {
          InputStream in = new ByteArrayInputStream(sourceData.getBytes());
           zip(entryName,in,new File(destFileName));
      }

    /**
     * make a zip file with one entry - the contents of sourceFileName
     * @param sourceFileName  as above
     * @param destZipFileName  zip file name - must be creatable
     */
    public static void zip(String sourceFileName,String destZipFileName)
      {
          if(!destZipFileName.endsWith(".zip"))
              destZipFileName += ".zip";
           zip(new File(sourceFileName),new File(destZipFileName));
      }

    /**
     * make a zip file with one entry - the contents of source
     * @param source readable file - someday we can support directories
     * @param targetZipFile  writeabler zip file
     */
    public static void zip(File source, File targetZipFile)
      {
          if(!source.exists())
             throw new IllegalArgumentException("File " + source + " does not exist");
          if(source.isDirectory())
                throw new UnsupportedOperationException("Zipping direstories not implemented yet");
          InputStream in = null;
          try {
              in = new FileInputStream(source);
          } catch (FileNotFoundException e) {
              throw new WrappingException(e);
          }
          String fileName = source.getName();
          zip(fileName, in, targetZipFile);
      }

    /**
     * create a zip file with a single entry
     * @param pFileName entry name
     * @param pIn stream to entry data
     * @param targetZipFile writeable file 
     */
    public static void zip(String pFileName, InputStream pIn, File targetZipFile) {
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetZipFile));
            ZipEntry  e = new ZipEntry(pFileName);
            out.putNextEntry(e);
            FileUtilities.copyStream(pIn,out);
      //      out.closeEntry();
            out.close();
        } catch (IOException e1) {
            throw new WrappingException(e1);
        }
    }

//    public static void main(String[] args) {
//        String s = MobyDick.randomText(10000);
//        zipString(s,"MobyDick.txt","MobyDick.zip");
//    }
}