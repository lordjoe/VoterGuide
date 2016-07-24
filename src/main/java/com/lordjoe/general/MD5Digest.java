package com.lordjoe.general;

import java.security.*;


/**
 * com.lordjoe.general.MD5Digest
 * Class for holding large strings and allowing
 * fast compart using an MD5 Digest
 * @author slewis
 * @date Jan 12, 2005
 */
public class MD5Digest
{
    public static final Class THIS_CLASS = MD5Digest.class;
    public static final MD5Digest EMPTY_ARRAY[] = {};


    private final MessageDigest m_Digester;
    private final byte[] m_Digest;
    private final byte[] m_Data;

    public MD5Digest(byte[] bytes)
    {
        try
        {
            m_Digester = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ex)
        {
            throw new IllegalStateException(ex.getMessage());
        }
        m_Data = bytes;
         MessageDigest digester = getDigester();
        digester.reset();
        m_Digest = digester.digest(m_Data);
    }
    
    public MD5Digest(String s)
    {
        this(s.getBytes());
    }


    public MessageDigest getDigester()
    {
        return m_Digester;
    }

    public byte[] getDigest()
    {
        return m_Digest;
    }

    public byte[] getDataBytes()
     {
         return m_Data;
     }
    public String getData()
     {
         return new String(m_Data);
     }


    public int hashCode()
    {
        String data = getData();
        return data.hashCode();

    }

    public boolean equals(Object test)
    {
        if(test == null)
             return false;
        if (getClass() != test.getClass())
            return false;
        MD5Digest realTest = (MD5Digest) test;
        if (!MessageDigest.isEqual(getDigest(), realTest.getDigest()))
        return false;
        return getData().equals(realTest.getData());
    }

}
