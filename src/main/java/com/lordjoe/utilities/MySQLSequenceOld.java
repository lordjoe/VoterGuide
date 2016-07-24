package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

import java.sql.*;

/**
 * com.lordjoe.utilities.MySQLSequence
 *
 * @author John Connor
 *         created Jul 30, 2007
 */
public class MySQLSequenceOld implements ISequence {
    public static MySQLSequenceOld[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = MySQLSequenceOld.class;
    public static final String SEQUENCE_SUFFIX =  "_seq";

    private final String m_Name;
    private final String m_Table;
    private final String m_Column;
    private boolean m_Initialized;
    private Connection m_Connection;

    public MySQLSequenceOld(String pName, String pColumn, String pTable) {
        m_Column = pColumn;
        m_Name = pName;
        m_Table = pTable;
    }

    public MySQLSequenceOld(String pColumn, String pTable) {
        this(pTable + SEQUENCE_SUFFIX, pColumn, pTable);
    }

    public MySQLSequenceOld(String pTable) {
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
            int max = JDBCUtilities.generateKey(s, getTable(), getColumn());
            if (!JDBCUtilities.hasTable(getName(), m_Connection)) {
                query = "CREATE TABLE " + getName() + " (id  INT NOT NULL)";
                s.execute(query);
                if (!JDBCUtilities.hasTable(getName(), m_Connection))
                    throw new RuntimeException("Failed to create sequence " + getName());
            }
            query = "INSERT INTO " + getName() + " VALUES (" + max + ")";
            b = s.execute(query);
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
            String query = "UPDATE " + getName() + " SET id=LAST_INSERT_ID(id+1)";
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
            String query = "SELECT * from " + getName();
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
            String query = "SELECT * from " + getName();
            int val = JDBCUtilities.getQueryInt(s, query);
            while (val <= max) {
                query = "UPDATE " + getName() + " SET id=LAST_INSERT_ID(id+1)";
                s.execute(query);
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
