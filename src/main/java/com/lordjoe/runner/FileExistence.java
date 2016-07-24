package com.lordjoe.runner;

/**
 * com.lordjoe.runner.FileExistence
 *
 * @author Steve Lewis
 * @date Mar 7, 2007
 */
public class FileExistence
{
    public static FileExistence[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = FileExistence.class;

    private final String m_FileName;
    private final boolean m_Exists;
    public FileExistence(String file,boolean exists)
    {
        m_FileName = file;
        m_Exists = exists;
    }

    public String getFileName()
    {
        return m_FileName;
    }

    public boolean isExists()
    {
        return m_Exists;
    }
}
