package com.lordjoe.utilities;

import java.sql.*;

import com.lordjoe.exceptions.*;

import java.util.*;

/*
 * com.lordjoe.Utilities.KeyGenerator
 * @author smlewis
 * Date: Jun 14, 2002
 */

public class KeyGenerator
{
    public static final KeyGenerator[] EMPTY_ARRAY = {};


        /**
        * Keys below 1000 are reserved for the system
        */
        public static final int LAST_SYSTEM_KEY = 1;
        protected static KeyGenerator gInstance;


        public static synchronized KeyGenerator instance() {
            if(gInstance == null)
                gInstance = new KeyGenerator();
            return(gInstance);
        }

    public static synchronized Integer generateIntegerKey(Connection conn, String table, String column) throws SQLException
       {
           return(instance().doGenerateKey(conn, table, column));
       }


    public static synchronized Long generateLongKey(Connection conn, String table, String column) throws SQLException
      {
          return(instance().doGenerateLongKey(conn, table, column));
      }
            /**
        * return a Long guaranteed to be unique
        */
        public static synchronized Long generateUniqueLong()
        {
            return(instance().doGenerateUniqueLong());
        }
        /**
        * return a string guaranteed to be printable and unique
        */
        public static synchronized String generateUniqueString()
        {
            return(instance().doGenerateUniqueString());
        }
       /**
        * return a string guaranteed to be printable and unique
        */
        public static synchronized String generateUniqueString(int length)
        {
            return(instance().doGenerateUniqueString(length));
        }

        private Map m_NameToKey;
        private long m_LastStringKey;
        private Map gUniqueStrings = new HashMap();

        private KeyGenerator()
        {
            if(gInstance != null)
                throw new IllegalStateException("Class KeyGenerator is not a singleton as required");
            m_NameToKey = new HashMap();
        }

        public synchronized Long doGenerateUniqueLong()
        {
            long time = System.currentTimeMillis();
            while(time == m_LastStringKey) {
                try {
                     Thread.sleep(10);
                }
                catch(InterruptedException ex) {
                    throw new ThreadInterruptedException();
                }
                time = System.currentTimeMillis();
            }
            m_LastStringKey = time;
            return(new Long(time));
        }

        public synchronized String doGenerateUniqueString()
        {
            long time = System.currentTimeMillis();
            while(time == m_LastStringKey) {
                try {
                     Thread.sleep(10);
                }
                catch(InterruptedException ex) {
                    throw new ThreadInterruptedException();
                }
                time = System.currentTimeMillis();
            }
            m_LastStringKey = time;
            return(Long.toHexString(time));
        }

        public  String doGenerateUniqueString(int length)
        {
            synchronized(gUniqueStrings) {
                int test = (int) ((0x3FFFFFFF) * Math.random());
                String Key = Integer.toHexString(test);
                Key = Key.substring(0,Math.min(Key.length(),length));
                while(gUniqueStrings.get(Key) != null) {
                    test =  (int) ((0x3FFFFFFF) * Math.random());
                    Key = Integer.toHexString(test);
                    Key.substring(0,Math.min(Key.length(),length));
                }
                gUniqueStrings.put(Key,Key);
                return(Key);
            }
        }

        protected synchronized Long doGenerateLongKey(Connection conn, String table, String column) throws SQLException
        {
            Integer theKey;
            Object currentObj = m_NameToKey.get(table);
            if(currentObj == null){
                theKey = readKey(conn,table,column);
                m_NameToKey.put(table,theKey);
            }
            else {
                theKey = setNextKey((Integer)currentObj,table);
                Integer realKey = keyExistsGetNextKey(conn, table, column, theKey);
                if (theKey.intValue()!= realKey.intValue()){
                    theKey = realKey;
                    m_NameToKey.put(table,theKey);
                }
            }
            return new Long(theKey.intValue());
        }

        protected synchronized Integer doGenerateKey(Connection conn, String table, String column) throws SQLException
        {
            Integer theKey;
            Object currentObj = m_NameToKey.get(table);
            if(currentObj == null){
                theKey = readKey(conn,table,column);
                m_NameToKey.put(table,theKey);
            }
            else {
                theKey = setNextKey((Integer)currentObj,table);
                Integer realKey = keyExistsGetNextKey(conn, table, column, theKey);
                if (theKey.intValue()!= realKey.intValue()){
                    theKey = realKey;
                    m_NameToKey.put(table,theKey);
                }
            }
            return theKey;
        }


        protected synchronized Integer setNextKey(Integer CurrentInteger,String Table)
        {
            int value = CurrentInteger.intValue();
            Integer NextInteger = new Integer(value + 1);
            m_NameToKey.put(Table,NextInteger);
            return(NextInteger);
        }

        protected synchronized Integer readKey(Connection conn,String TableName,String KeyColumn) throws SQLException
        {
           Statement TheStatement = null;
            try {
                TheStatement = conn.createStatement();
                int key = JDBCUtilities.generateKey(TheStatement,TableName, KeyColumn);
                return(new Integer(Math.max(key,LAST_SYSTEM_KEY)));
            }
            finally {
                TheStatement.close();
            }
        }



        protected synchronized Integer keyExistsGetNextKey(Connection conn,String TableName,
                                                 String KeyColumn, Integer theKey)
                                                 throws SQLException
        {
           Statement TheStatement = null;
            try {
                TheStatement = conn.createStatement();
                int key =  JDBCUtilities.keyExists(TheStatement,TableName, KeyColumn, theKey.intValue());
                return(new Integer(Math.max(key,LAST_SYSTEM_KEY)));
            }
            finally {
                TheStatement.close();
            }
        }


}
