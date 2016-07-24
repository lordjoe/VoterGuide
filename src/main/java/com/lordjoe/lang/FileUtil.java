package com.lordjoe.lang;


import javax.swing.*;
import java.io.*;
import java.util.*;


/**
 * com.lordjoe.lang.FileUtil
 * utility methods for Files
 *
 * @author slewis
 * @date Dec 8, 2004
 */
public abstract class FileUtil
{
    public static final Class THIS_CLASS = FileUtil.class;
    public static final FileUtil EMPTY_ARRAY[] = {};
    public static final int BUFFER_SIZE = 4096;

    /**
     * for ce teh creation of directories for the file
     *
     * @param thefile non-null file
     * @throws IllegalArgumentException on failure
     */
    public static void guaranteeDirectory(File thefile)
            throws IllegalArgumentException
    {
        if (thefile.exists())
            return;
        File parentFile = thefile.getParentFile();
        if(parentFile == null)
            parentFile = new File(System.getProperty("user.dir"));
        if (parentFile.exists() && parentFile.isDirectory())
            return;
        boolean result = parentFile.mkdirs();
        if (!result)
            throw new IllegalArgumentException("Cannot create directoried for file " +
                    thefile.getAbsolutePath());

    }

    public static void writeText(String out, String text)
    {
        try {
            writeText(new FileWriter(out), text);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Cannot create file " + out);
        }
    }

    public static void writeText(File out, String text)
    {
        try {
            writeText(new FileWriter(out), text);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Cannot create file " + out.getAbsolutePath());
        }
    }

    public static void appendText(String out, String text)
    {
        try {
            writeText(new FileWriter(out, true), text);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Cannot append to file " + out);
        }
    }

    public static void appendText(File out, String text)
    {
        if (out.getParentFile() != null)
            guaranteeDirectory(out.getParentFile());
        try {
            FileWriter outWriter = null;
            if (out.exists())
                outWriter = new FileWriter(out, true);
            else
                outWriter = new FileWriter(out);
            writeText(outWriter, text);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Cannot append to file " + out.getAbsolutePath() +
                    " because " + ex.getMessage());
        }
    }


    public static void writeText(Writer out, String text)
    {
        PrintWriter realOut = new PrintWriter(out);
        realOut.print(text);
        realOut.close();
    }

    public static String readInText(File inp)
    {
        try {
            return readInText(new FileInputStream(inp));
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Cannot read file " + inp.getAbsolutePath());
        }
    }


    public static String readInText(InputStream inp)
    {
        byte[] bytes = readInBytes(inp);
        return new String(bytes);
    }


    public static ImageIcon readImage(InputStream inp)
    {
        byte[] bytes = readInBytes(inp);
        return new ImageIcon(bytes);
    }

    public static byte[] readInBytes(InputStream inp, int lenthGuess)
    {
        InputStream TheStream = new BufferedInputStream(inp);
        return readInFile(TheStream, 4096, lenthGuess);
    }

    public static byte[] readInBytes(InputStream inp)
    {
        return readInBytes(inp, 4096);
    }


    public static String readFileText(File inp, int start, int bytes)
    {
        try {
            return readFileText(new FileInputStream(inp), start, bytes);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Cannot read file " + inp.getAbsolutePath());
        }
    }


    public static String readFileText(InputStream inp, int start, int len)
    {
        boolean skipResult = skipTo(inp, start);
        if (!skipResult)
            return "";
        byte[] bytes = readInBytes(inp, len);
        return new String(bytes);
    }

    /**
     * position the stream to read characters starting st
     * len
     *
     * @param theStream
     * @param len
     * @return
     */
    public static boolean skipTo(InputStream theStream, int len)
    {
        if (len == 0)
            return true;
        byte[] buffer = new byte[BUFFER_SIZE];
        return skipTo(theStream, len, buffer);

    }

    protected static boolean skipTo(InputStream theStream, int len, byte[] buffer)
    {
        while (len > BUFFER_SIZE) {
            if (!skipBytes(theStream, BUFFER_SIZE, buffer))
                return false; // at end
            len -= BUFFER_SIZE;
        }
        if (len > 0)
            return skipBytes(theStream, len, buffer);
        else
            return true;
    }

    protected static boolean skipBytes(InputStream theStream, int len, byte[] buffer)
    {
        try {
            int NRead = theStream.read(buffer, 0, len);
            if (NRead == len)
                return true;
            theStream.close();
            return false;
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * { method
     *
     * @param TheStream the file stream
     * @param len       the file length or a good guess
     * @return StringBuilder holding file bytes
     *         }
     * @name readInFile
     * @function reads all the data in a file into a StringBuilder
     */
    public static byte[] readInFile(InputStream TheStream, int len, int chunk)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream(len);
        int NRead = 0;
        byte[] buffer = new byte[chunk];
        try {
            NRead = TheStream.read(buffer, 0, chunk);
            while (NRead != -1) {
                os.write(buffer, 0, NRead);
                NRead = TheStream.read(buffer, 0, chunk);
                // ought to look at non-printing chars
            }
            TheStream.close();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return (os.toByteArray());
    }

    /**
     * { method
     *
     * @param TheStream the file stream
     * @param len       the file length or a good guess
     * @param maxlen    maxlength to read
     * @return file bytes
     *         }
     * @name readInFile
     * @function reads all the data in a file into a StringBuilder
     */
    public static byte[] readInFile(InputStream TheStream, int len, int chunk, int maxlen)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream(len);
        int NRead = 0;
        byte[] buffer = new byte[chunk];
        chunk = Math.min(chunk, maxlen - os.size());
        try {
            NRead = TheStream.read(buffer, 0, chunk);
            while (NRead != -1 && os.size() < maxlen) {
                os.write(buffer, 0, NRead);
                chunk = Math.min(chunk, maxlen - os.size());
                NRead = TheStream.read(buffer, 0, chunk);
                // ought to look at non-printing chars
            }
            TheStream.close();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return (os.toByteArray());
    }


    /**
     * method
     *
     * @param dst destination file name
     * @param src source file name
     * @return true for success
     * @name copyFile
     * @function copy file named src into new file named dst
     */
    public static boolean copyFile(String src, String dst)
    {
        int bufsize = 1024;
        try {
            RandomAccessFile srcFile = new RandomAccessFile(src, "r");
            long len = srcFile.length();
            if (len > 0x7fffffff) {
                return (false);
            }
            // too large
            int l = (int) len;
            if (l == 0) {
                return (false);
            }
            // failure - no data
            RandomAccessFile dstFile = new RandomAccessFile(dst, "rw");

            int bytesRead = 0;
            byte[] buffer = new byte[bufsize];
            while ((bytesRead = srcFile.read(buffer, 0, bufsize)) != -1) {
                dstFile.write(buffer, 0, bytesRead);
            }
            srcFile.close();
            dstFile.close();
            return true;
        }
        catch (IOException ex) {
            return (false);
        }
    }


    public static Properties readProperties(String file)
    {
        return readProperties(new File(file));
    }

    public static Properties readProperties(File file)
    {
        try {
            Properties ret = new Properties();
            FileInputStream fs = new FileInputStream(file);
            ret.load(fs);
            return ret;
        }
        catch (IOException ex) {
            throw new RuntimeException("cannot read file", ex);
        }
    }

}
