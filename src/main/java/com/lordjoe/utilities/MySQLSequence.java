package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

import java.sql.*;

/**
 * com.lordjoe.utilities.MySQLSequence
 *
 * @author John Connor
 *         created Jul 30, 2007
 */
public class MySQLSequence implements ISequence {
    public static MySQLSequence[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = MySQLSequence.class;
    public static final String SEQUENCE_SUFFIX = "_seq";

    private final String m_Name;
    private final String m_Table;
    private final String m_Column;
    private boolean m_Initialized;
    private Connection m_Connection;

    public MySQLSequence(String pName, String pColumn, String pTable) {
        m_Column = pColumn;
        m_Name = pName;
        m_Table = pTable;
    }

    public MySQLSequence(String pColumn, String pTable) {
        this(pTable + SEQUENCE_SUFFIX, pColumn, pTable);
    }

    public MySQLSequence(String pTable) {
        this("id", pTable);
    }


    public String getColumn() {
        return m_Column;
    }

    public String getName() {
        return m_Name;
    }

    public String getTable() {
        return m_Table;
    }


    public Connection getConnection() {
        return m_Connection;
    }

    public void setConnection(Connection pConnection) {
        m_Connection = pConnection;
    }

    public void guaranteeExistance() {
        try {
            currentValue();
        } catch (Exception e) {
            createSequeuce();
            currentValue();
        }
    }

    protected void createSequeuce() {
        Statement s = null;
        String query = null;
        boolean b = false;
        try {
            s = getConnection().createStatement();
            String table = getTable();
            String col = getColumn();
            int max = JDBCUtilities.generateKey(s, table, col);

            query = "INSERT INTO sequences (name,number)  VALUES (" + JDBCUtilities.buildStringValue(getTable()) + "," + max + ")";
            int n = s.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Failed to create sequence " + getName() + " for table " + getTable());
            throw new WrapperException(e);
        } finally {
            try {
                if (s != null)
                    s.close();
            } catch (SQLException e) {
                throw new WrapperException(e);
            }
        }
    }

    public int nextValue() {
        guaranteeInitialized();
        Statement s = null;
        try {
            s = getConnection().createStatement();
            String query = "UPDATE sequences " + " SET number=LAST_INSERT_ID(number+1) WHERE name=" + JDBCUtilities.buildStringValue(getTable());
            s.execute(query);
            query = "SELECT LAST_INSERT_ID()";
            int val = JDBCUtilities.getQueryInt(s, query);
            return val;
        } catch (SQLException e) {
            throw new WrapperException(e);
        } finally {
            try {
                if (s != null)
                    s.close();
            } catch (SQLException e) {
                throw new WrapperException(e);
            }
        }
    }

    public int currentValue() {
        guaranteeInitialized();
        Statement s = null;
        try {
            s = getConnection().createStatement();
            String query = "SELECT number from sequences where name=" + JDBCUtilities.buildStringValue(getTable());
            int val = JDBCUtilities.getQueryInt(s, query);
            return val;
        } catch (SQLException e) {
            throw new WrapperException(e);
        } finally {
            try {
                if (s != null)
                    s.close();
            } catch (SQLException e) {
                throw new WrapperException(e);
            }
        }
    }

    protected synchronized void guaranteeInitialized() {
        if (m_Initialized)
            return;
        Statement s = null;
        try {
            s = getConnection().createStatement();
            int max = JDBCUtilities.generateKey(s, getTable(), getColumn());
            String query = "SELECT Count(*) from sequences where name=" + JDBCUtilities.buildStringValue(getTable());
            int val = JDBCUtilities.getQueryInt(s, query);
            if (val == 0)
                createSequeuce();
            query = "SELECT number from sequences where name=" + JDBCUtilities.buildStringValue(getTable());
            val = JDBCUtilities.getQueryInt(s, query);
            while (val <= max) {
                query = "UPDATE sequences " + " SET number=LAST_INSERT_ID(number+1) WHERE name=" + JDBCUtilities.buildStringValue(getTable());
                s.executeUpdate(query);
                query = "SELECT LAST_INSERT_ID()";
                val = JDBCUtilities.getQueryInt(s, query);
            }
            m_Initialized = true;
        } catch (SQLException e) {
            throw new WrapperException(e);
        } finally {
            try {
                if (s != null)
                    s.close();
            } catch (SQLException e) {
                // give up
            }
        }
    }
}
