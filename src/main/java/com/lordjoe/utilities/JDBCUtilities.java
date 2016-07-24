
package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;
import com.lordjoe.lib.xml.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;


public abstract class JDBCUtilities {
    public static final String DEFAULT_DB_PREFIX = "jdbc:cloudscape:";
    public static final String DEFAULT_DRIVER_NAME = "COM.cloudscape.core.JDBCDriver";

    protected static String gDBPrefix = DEFAULT_DB_PREFIX;
    protected static String gDriverName = DEFAULT_DRIVER_NAME;
    protected static Set gOracleTableNames = new HashSet();
    protected static final String[] ORACLE_TABLES = {
            "CATALOG",
            "COL",
            "COLUMN_PRIVILEGES",
            "DATABASE_COMPATIBLE_LEVEL",
            "DEFERRCOUNT",
            "DEACTIVATE",
            "DEACTIVATE",
            "DICTIONARY",
            "DICT_COLUMNS",
            "DUAL",
            "INDEX_STATS",
            "INDEX_HISTOGRAM",
            "JAVASNM",
            "LOADER_CONSTRAINT_INFO",
            "PSTUBTBL",
            "PUBLICSYN",
            "PUBLIC_DEPENDENCY",
            "QUEUE_PRIVILEGES",
            "SESSION_CONTEXT",
            "SYNONYMS",
            "SYSCATALOG",
            "SYSFILES",
            "SYSSEGOBJ",
            "TABQUOTAS",
            "TAB",
            "SYSTEM_PRIVILEGE_MAP",
            "GLOBAL_NAME",
            "HELP",
            "STMT_AUDIT_OPTION_MAP",
            "IMP7UEC",
            "PRODUCT_COMPONENT_VERSION",
            "RESOURCE_COST",
            "PRODUCT_PRIVS"
    };

    public static int SELECT_PRIVILEGE = 1;
    public static int INSERT_PRIVILEGE = 2;

    static {
        gOracleTableNames.addAll(Arrays.asList(ORACLE_TABLES));
    }

    public static void setDBPrefix(String s) {
        gDBPrefix = s;
    }

    public static void setDriverName(String s) {
        gDriverName = s;
    }


    public static void appendReference(StringBuilder sb, IIdentifiedObject s) {
        if(s == null) {
            sb.append("null");
            return;
        }
        appendQoutedString(sb,s.getId());
    }


    public static void appendQoutedString(StringBuilder sb, String s) {
        if(s == null) {
            sb.append("null");
            return;
        }
        sb.append(toSQLInsert(s));
    }

    public static Connection getConnection(String DBName) throws SQLException {
        try {
            Class.forName(gDriverName);
            String ConnectionName = gDBPrefix + DBName;
            //   System.err.println("Trying to open Connection " + ConnectionName);
            Connection connection =
                    DriverManager.getConnection(ConnectionName);
            return (connection);
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException(ex.toString());
        }
        catch (SQLException ex) {
            throw ex; // debugging stop
        }
    }

    /**
     * get a database Connection object
     *
     * @param driver non-null string JDBCDriver name
     * @param url    non-null string database url
     * @param user   non-null string datbase user name
     * @param passwd non-null string user passwd
     * @return con     Database Connection object
     */
    public static Connection getConnection(String driver, String url,
                                           String user, String passwd)
            throws SQLException {
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, passwd);
            return con;
        }
        catch (ClassNotFoundException ex) {
            throw new WrapperException(ex);

        }
        catch (SQLException ex) {
            throw ex; // debugging stop
        }
    }


    public static boolean hasTable(String TableName, Connection conn) throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        String[] Types = {"TABLE", "VIEW"};
        ResultSet rs = md.getTables(null, null, TableName, null);
        boolean ret = rs.next();
        rs.close();
        if (ret)
            return (ret);

        rs = md.getTables(null, null, TableName.toUpperCase(), null);
        ret = rs.next();
        rs.close();
        return (ret);
    }

    public static String[] listTables(Connection conn, String Schema) throws SQLException {
        String[] Types = {"TABLE", "VIEW"};
        return (listTables(conn, Types, Schema));
    }

    public static String[] listTables(Connection conn) throws SQLException {
        String[] Types = {"TABLE", "VIEW"};
        return (listTables(conn, Types, null));
    }

    public static boolean isOracleTable(String tableName) {
        if (tableName.indexOf("$") > -1)
            return (true);

        if (tableName.startsWith("ALL_")) {
            /*
           if(tableName.startsWith("ALL_CONS"))
                return(false);
           if(tableName.startsWith("ALL_TAB_PRIVS"))
                return(false);
                */
            return (true);
        }
        if (tableName.startsWith("EXU"))
            return (true);
        if (tableName.startsWith("GV_"))
            return (true);
        if (tableName.startsWith("IMP8"))
            return (true);
        if (tableName.startsWith("LOADER_"))
            return (true);
        if (tableName.startsWith("NLS_"))
            return (true);
        if (tableName.startsWith("ORA_"))
            return (true);
        if (tableName.startsWith("ROLE_"))
            return (true);
        if (tableName.startsWith("AUDIT_"))
            return (true);
        if (tableName.startsWith("V_"))
            return (true);
        if (tableName.startsWith("USER_"))
            return (!tableName.equals("USER_PRIVILEGES") && !tableName.equals("USER_TYPE"));
        if (tableName.startsWith("TABLE_"))
            return (true);
        if (tableName.startsWith("SQL_"))
            return (true);
        if (tableName.startsWith("SESSION_"))
            return (true);
        if (tableName.startsWith("TMP_"))
            return (true);
        if (tableName.startsWith("SM_"))
            return (true);
        if (gOracleTableNames.contains(tableName))
            return (true);

        return (false);
    }

    public static String[] listTables(Connection conn, String[] tableTypes, String Schema) throws SQLException {
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, Schema, "%", tableTypes);
            List holder = new ArrayList();
            while (rs.next()) {
                String table = rs.getString(3); // gte the table
                if (isOracleTable(table))
                    continue;
                holder.add(table);
            }
            String[] Tables = (String[]) Util.collectionToArray(holder, String.class);
            // return(accessableTables(conn,Tables));
            return (Tables);
        }
        catch (java.lang.UnsatisfiedLinkError ex) {
            String path = System.getProperty("java.library.path");
            String Message = "Cannot library - path is\n" + path;
            Message += "\nError is:" + ex.getMessage();
            throw new IllegalStateException(Message);
        }
    }

    public static String[] listColumns(Connection conn, String TableName) throws SQLException {
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(null, null, TableName, "%");
            List holder = new ArrayList();

            while (rs.next()) {
                //    String[] items = getResultSetStrings(rs);
                String Table = rs.getString(4); // gte the table
                holder.add(Table);
            }
            String[] Tables = (String[]) Util.collectionToArray(holder, String.class);
            // return(accessableTables(conn,Tables));
            return (Tables);
        }
        catch (java.lang.UnsatisfiedLinkError ex) {
            String path = System.getProperty("java.library.path");
            String Message = "Cannot library - path is\n" + path;
            Message += "\nError is:" + ex.getMessage();
            throw new IllegalStateException(Message);
        }
    }

    /**
     * forgeve exceptions because the requested operation is not supported
     *
     * @param ex non-null exception
     * @throws SQLException otherwie rethrow
     */
    protected static void ForgiveUnsupportedException(SQLException ex) throws SQLException {
        String msg = ex.getMessage();
        if (msg.indexOf("Driver does not support this function") > -1)
            return;
        throw ex;
    }

    public static SQLColumnMetaData[] getColumnMetaData(Connection conn, String TableName) throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        List holder = new ArrayList();
        String PK_Col = null;
        try {
            getPrimaryKeyColumn(md, TableName);
        }
        catch (SQLException ex) {
            ForgiveUnsupportedException(ex); // maybe not supported
        }

        Map fkholder = new HashMap();
        try {
            getForeignKeys(md, TableName);
        }
        catch (SQLException ex) {
            ForgiveUnsupportedException(ex); // maybe not supported
        }
        String[] rowID = Util.EMPTY_STRING_ARRAY;
        try {
            getBestRowIdentifier(md, TableName);
        }
        catch (SQLException ex) {
            ForgiveUnsupportedException(ex); // maybe not supported
        }

        ResultSet rs = md.getColumns(null, null, TableName, "%");
        while (rs.next()) {
            String[] items = getResultSetStrings(rs);
            SQLColumnMetaData item = new SQLColumnMetaData();
            item.setName(items[3]);
            item.setTableName(items[2]);
            item.setTypeName(items[5]);
            item.setSQLType(Integer.parseInt(items[4]));
            item.setWidth(Integer.parseInt(items[6]));
            boolean pk = false;
            ForeignKeyData fd = (ForeignKeyData) fkholder.get(item.getName());
            if (fd == null && PK_Col != null) {
                pk = PK_Col.equalsIgnoreCase(item.getName());
            }
            item.setPrimaryKey(pk);

            boolean nullable = "YES".equals(items[17]);
            item.setNullable(nullable);


            if (fd != null)
                item.setForeignKey(fd.getFKString());

            holder.add(item);
        }
        SQLColumnMetaData[] cols = (SQLColumnMetaData[]) Util.collectionToArray(holder, SQLColumnMetaData.class);
        // return(accessableTables(conn,Tables));
        return (cols);
    }

    private static Map getForeignKeys(DatabaseMetaData md, String TableName) throws SQLException {
        Map fkholder = new HashMap();
        ResultSet rs2;
        rs2 = md.getImportedKeys(null, null, TableName);
        while (rs2.next()) {
            String[] items = getResultSetStrings(rs2);
            String fkTable = rs2.getString("PKTABLE_NAME");
            String fkColumn = rs2.getString("PKCOLUMN_NAME");
            String thisColumn = rs2.getString("FKCOLUMN_NAME");
            ForeignKeyData fd = new ForeignKeyData(thisColumn, fkTable, fkColumn);
            fkholder.put(thisColumn, fd);
        }
        rs2.close();
        return (fkholder);
    }

    public static String[] getBestRowIdentifier(DatabaseMetaData md, String TableName) throws SQLException {
        List fkholder = new ArrayList();
        ResultSet rs2;
        rs2 = md.getBestRowIdentifier(null, null, TableName, DatabaseMetaData.bestRowSession, false);
        while (rs2.next()) {
            String[] items = getResultSetStrings(rs2);
            String col = rs2.getString("COLUMN_NAME");

            fkholder.add(col);
        }
        rs2.close();
        return (Util.collectionToStringArray(fkholder));
    }


    private static String getPrimaryKeyColumn(DatabaseMetaData md, String TableName) throws SQLException {
        String PK_Col = null;
        ResultSet rs2 = md.getPrimaryKeys(null, null, TableName);
        while (rs2.next()) {
            String[] items = getResultSetStrings(rs2);
            if (PK_Col == null) {
                PK_Col = rs2.getString(4);
            } else {
                PK_Col = null;
                break;
            }
        }
        rs2.close();
        return PK_Col;
    }

    public static int getNumberColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int nCols = md.getColumnCount();
        return (nCols);
    }

    public static String[] getResultSetColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int nCols = md.getColumnCount();
        String[] ret = new String[nCols];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = md.getColumnName(i + 1);
        }
        return (ret);
    }

    public static String[] getResultSetStrings(ResultSet rs) throws SQLException {
        int nCols = getNumberColumns(rs);
        String[] ret = new String[nCols];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = rs.getString(i + 1);
        }
        return (ret);
    }

    /**
     * Turn all columns into one ; delimited seting Primarily useful in
     * testing that two rows are equivalent
     *
     * @param rs non-null result set positioned at a valid row
     * @return non-null string concatinating all column data
     * @throws SQLException
     */
    public static String getResultSetRowString(ResultSet rs) throws SQLException {
        int nCols = getNumberColumns(rs);
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < nCols; i++) {
            sb.append(rs.getString(i + 1));
            if (i < nCols - 1)
                sb.append(";");
        }
        return (sb.toString());
    }

    public static void dumpTable(Connection conn, String TableName, PrintWriter out) throws SQLException {
        Statement st = conn.createStatement();
        String Query = "Select * from " + TableName;
        ResultSet rs = st.executeQuery(Query);
        while (rs.next()) {
            String[] items = getResultSetStrings(rs);
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                out.print(item);
                if (i < items.length - 1)
                    out.print(", ");
            }
            out.println();
        }
    }

    public static void dumpTable(Connection conn, String TableName, PrintStream out) throws SQLException {
        Statement st = conn.createStatement();
        String Query = "Select * from " + TableName;
        ResultSet rs = st.executeQuery(Query);
        while (rs.next()) {
            String[] items = getResultSetStrings(rs);
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                out.print(item);
                if (i < items.length - 1)
                    out.print(", ");
            }
            out.println();
        }
    }

    protected static String[] accessableTables(Connection conn, String[] TableNames) throws SQLException {
        List holder = new ArrayList();
        for (int i = 0; i < TableNames.length; i++) {
            if (tableIsAccesable(conn, TableNames[i]))
                holder.add(TableNames[i]);
        }
        return ((String[]) Util.collectionToArray(holder, String.class));
    }

    protected static boolean tableIsAccesable(Connection conn, String TableName) throws SQLException {
        int NeedsPrivilege = SELECT_PRIVILEGE | INSERT_PRIVILEGE;
        DatabaseMetaData md = conn.getMetaData();
        try {
            String me = md.getUserName();
            ResultSet rs = md.getTablePrivileges(null, null, TableName);
            boolean HasResults = false;
            while (rs.next()) {
                HasResults = true;
                String Grantee = rs.getString(5); // gte the table
                String Privilege = rs.getString(6); // gte the table
                if (Privilege.equalsIgnoreCase("public"))
                    NeedsPrivilege = 0;
                if (Privilege.equalsIgnoreCase("insert"))
                    NeedsPrivilege &= ~INSERT_PRIVILEGE;
                if (Privilege.equalsIgnoreCase("select"))
                    NeedsPrivilege &= ~SELECT_PRIVILEGE;
            }
            if (NeedsPrivilege == 0)
                return (true);
            return (!HasResults); // so if no results then is OK I guess
        }
        catch (SQLException ex) {
            return (true); // no way to tell
        }
    }


    public static int generateKey(Statement statement, String TableName, String KeyColumn) throws SQLException {
        String sql = "SELECT MAX(" + KeyColumn + ") FROM  " + TableName;

        return getQueryInt(statement, sql);
    }

    public static int getQueryInt(Statement statement, String pSql) throws SQLException {
        ResultSet rs = statement.executeQuery(pSql);
        int ret = 0;
        if (!rs.next())
            return (1);
        return (rs.getInt(1));
    }

    public static int keyExists(Statement statement, String TableName, String KeyColumn, int theKey) throws SQLException {
        String sql = "select (max(" + KeyColumn + ")+1) from " + TableName + " where EXISTS(select " + KeyColumn + " from " + TableName + " where " + KeyColumn + " = " + theKey + ")";
        ResultSet rs = statement.executeQuery(sql);
        int ret = 0;
        if (!rs.next())
            return (theKey);
        ret = rs.getInt(1);
        if (ret <= 0)
            return theKey;
        return ret;
    }

    /**
     * Columns are designated as Catalog.Schema.Table.Column
     * This returns the Column part of the string
     *
     * @param FullName non-null String as above
     * @return non-null column name
     */
    public static String getColumnNamePart(String FullName) {
        int index = FullName.lastIndexOf('.');
        if (index == -1)
            return (FullName);
        return (FullName.substring(index + 1));
    }

    public static void populateTable(Connection conn, String tableName, Object[] items) {
        if(items.length == 0)
            return;
        String[] props = ClassAnalyzer.getPropertyNames(items[0].getClass());

        populateTable(conn, props, tableName,items);
    }

    public static void populateTable(Connection conn, String[] pProps, String tableName, Object[] items) {
        Statement s = null;
        try {
            s = conn.createStatement();
            for (int i = 0; i < items.length; i++) {
                Object item = items[i];
                populateRow(pProps,s, item, tableName);
            }
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


    private static void populateRow(String[] pProps, Statement pS, Object pItem, String pTableName) throws SQLException {
        StringBuilder itemsString = new StringBuilder();
        StringBuilder valueString = new StringBuilder();
        itemsString.append("(");
        valueString.append("(");
        for (int i = 0; i < pProps.length; i++) {
            String prop = pProps[i];
            Object val = ClassAnalyzer.getProperty(pItem, prop);
            if (val != null) {
                if (itemsString.length() > 1)
                    itemsString.append(",");
                itemsString.append(prop.toLowerCase());
                if (valueString.length() > 1)
                    valueString.append(",");
                String inserted = toSQLInsert(val);
                valueString.append(inserted);
            }
        }
        valueString.append(")");
        itemsString.append(")");
        String query = "INSERT INTO " + pTableName + " " +
                itemsString + " VALUES " + valueString;
        int affected = pS.executeUpdate(query);
        if(affected != 1)
          throw new IllegalStateException("Insert failed " + query);
    }

    /**
     * convert an Object into an insertable value
     * @param val  possibly null  object
     * @return   non-null String
     */
    public static String  toSQLInsert(Object val)
    {
        if(val == null)
            return "null";
        if(val instanceof  Number)
            return val.toString();
        if(val instanceof String )
            return buildStringValue((String)val);
        return buildStringValue(val.toString());
    }

    /**
     * create a string for insertion into the database
     * @param s non-null String
     * @return  non-null String as above
     */
    public static  String buildStringValue(Object s)
    {
        if(s == null)
            return "null";
        return buildStringValue(s.toString());
    }


    /**
     * create a string for insertion into the database
     * @param s non-null String
     * @return  non-null String as above
     */
    public static  String buildStringValue(Date s)
    {
        if(s == null || s.getTime() == 0)
            return "null";
        String dateS = Util.DB_DATE_TIME.format(s);
        return buildStringValue(dateS);
    }

    /**
     * create a string for insertion into the database
     * @param s non-null String
     * @return  non-null String as above
     */
    public static Date parseDateValue(String s)
    {

        if(s == null || "null".equals(s))
            return null;
        try {
            s = s.trim();
            if(s.length() <= Util.DATE_FORMAT_STRING.length())
                return Util.DB_DATE_TIME.parse(s);
            else
                return  Util.DB_TIMESTAMP_TIME.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * create a string for insertion into the database
     * @param s non-null String
     * @return  non-null String as above
     */
    public static  String buildStringValue(IIdentifiedObject s)
    {
        if(s == null)
            return "null";
        return buildStringValue(s.getId());
    }


    /**
     * create a string for insertion into the database
     * @param s non-null String
     * @return  non-null String as above
     */
    public static  String buildStringValue(String s)
    {
        if(s == null)
            return "null";
        StringBuilder sb = new StringBuilder();
        sb.append("\'");
        for(int i = 0; i <s.length(); i++)  {
          char c = s.charAt(i);
           switch(c) {
               case '\'' :
                   sb.append("\'\'");
                   break;
               default :
                   sb.append(c);
                   break;
           }
        }
        sb.append("\'");
          return sb.toString();
    }

    protected static class ForeignKeyData {
        private String m_ColName;
        private String m_ForeignTable;
        private String m_ForeignColumn;

        protected ForeignKeyData(String colName, String foreignTable, String foreignColumn) {
            m_ColName = colName;
            m_ForeignTable = foreignTable;
            m_ForeignColumn = foreignColumn;
        }

        public String getColName() {
            return m_ColName;
        }

        public String getFKString() {
            return (getForeignTable() + ":" + getForeignColumn());
        }

        public String getForeignTable() {
            return m_ForeignTable;
        }

        public String getForeignColumn() {
            return m_ForeignColumn;
        }
    }


}
