package com.lordjoe.utilities;

/*
 * com.lordjoe.Utilities.HardCodedDataSource
 * @author smlewis
 * Date: Jun 13, 2002
 */
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class HardCodedDataSource   implements INameable,DataSource
{
    public static final HardCodedDataSource[] EMPTY_ARRAY = {};

    private String m_Name;
    private String m_Password;
    private String m_URL;
    private String m_Driver;

    public HardCodedDataSource()
    {

    }

    public String getName()
    {
        return m_Name;
    }

    public void setName(String name)
    {
        m_Name = name;
    }
    public String getUser()
    {
        return getName();
    }
    public void setUser(String name)
     {
         setName(name);
     }
    public String getPassword()
    {
        return m_Password;
    }

    public void setPassword(String password)
    {
        m_Password = password;
    }

    public String getURL()
    {
        return m_URL;
    }

    public void setURL(String URL)
    {
        m_URL = URL;
    }

    public String getDriver()
    {
        return m_Driver;
    }

    public void setDriver(String driver)
    {
        m_Driver = driver;
    }
    public String getDriverName()
    {
        return getURL();
    }

    public void setDriverName(String driver)
    {
        setURL(driver);
    }
    public Connection getConnection() throws SQLException
    {
        return(getConnection(getName(),getPassword()));
    }


    public Connection getConnection(String user, String password) throws SQLException
    {
        try {
            // Load the Oracle JDBC driver
           try {
               Class.forName(getDriver());
           }
           catch(ClassNotFoundException ex)  {
               throw new IllegalStateException("Cannnot load Driver Class " + getDriver());
           }
            Connection ret = null;
            String url = getURL();
           if(user == null)
               ret = DriverManager.getConnection (url);  // maybe password is in url
           else
            ret = DriverManager.getConnection (url,user, password);
			return(ret);
       }
        catch(SQLException ex1) {
            ex1.printStackTrace();
            return(null);
        }
    }
    public PrintWriter getLogWriter() throws SQLException
       {
        throw new UnsupportedOperationException("Fix This");
    }
    public int getLoginTimeout() throws SQLException
       {
        throw new UnsupportedOperationException("Fix This");
    }

     public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }


    public void setLogWriter(PrintWriter printWriter) throws SQLException
     {
        throw new UnsupportedOperationException("Fix This");
    }
    public void setLoginTimeout(int i) throws SQLException
    {
        throw new UnsupportedOperationException("Fix This");
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (true) throw new UnsupportedOperationException("Fix This");
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (true) throw new UnsupportedOperationException("Fix This");
        return false;
    }
}
