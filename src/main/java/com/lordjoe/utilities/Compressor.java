package com.lordjoe.utilities;

import java.io.*;
import java.util.zip.*;


/*
 * com.lordjoe.Utilities.Compressor
 * @author smlewis
 * Date: Mar 4, 2003
 */

public class Compressor
{
    public static final Class THIS_CLASS = Compressor.class;
    public static final Compressor[] EMPTY_ARRAY = {};

  /*
    public static String compress(String in)
    {
        try
        {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            GZIPOutputStream gz = new GZIPOutputStream(bout);
            PrintStream ps = new PrintStream(gz);
            ps.print(in);
            ps.m_Close();
            String ret = bout.toString();
            return (ret);
        }
        catch (IOException ex)
        {
           throw new IllegalStateException("problem");
        }
    }
    */

    /**
     * Compress a string and save as binhex
     * @param in  non-null string
     * @return   non-null compressed string
     * *@see expend
     */
     public static String compress64(String in)
    {
        try
        {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            GZIPOutputStream gz = new GZIPOutputStream(bout);
            PrintStream ps = new PrintStream(gz);
            ps.print(in);
            ps.close();
            String ret = Base64.encode(bout.toByteArray());
            return (ret);
        }
        catch (IOException ex)
        {
           throw new IllegalStateException("problem");
        }
    }
    /**
      * Expand a string compressed as compress64
      * @param in  non-null string
      * @return   non-null compressed string
      * *@see expend
      */
    public static String expand64(String in)
     {
         try
         {
             ByteArrayInputStream bin = new ByteArrayInputStream(Base64.decode(in) );
             GZIPInputStream gz = new GZIPInputStream(bin);
             String out = FileUtilities.readInFile(gz);
             return(out);
         }
         catch (IOException ex)
         {
            throw new IllegalStateException("problem");
         }
     }

    /*
    public static String expand(String in)
    {
        try
        {
            ByteArrayInputStream bin = new ByteArrayInputStream(in.getBytes() );
            GZIPInputStream gz = new GZIPInputStream(bin);
            String out = FileUtilities.readInFile(gz);
            return(out);
        }
        catch (IOException ex)
        {
           throw new IllegalStateException("problem");
        }
    }
    */

    public static void main(String[] args)
    {
         String test = "IUPO&*(*& $E%WS^RTCDXCRTYGTY\"PL\")" +
        "{)(MJN*&U:(NU *()*_{*)(+(H{_+)+{+{_\"NPO\"))" +
        "}POM|OM|+(+|()MJ|)" +
         "+(J_+(H+{_+_))";
     /*   String compressed = compress(test);
        String expand = expand(compressed);
        if(!expand.equals(test))
            throw new IllegalStateException("problem");
      */
        String compressed64 = compress64(test);
        String expand64 = expand64(compressed64);
        if(!expand64.equals(test))
            throw new IllegalStateException("problem");
    }

}