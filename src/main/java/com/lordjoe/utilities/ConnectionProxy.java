package com.lordjoe.utilities;

import java.sql.*;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

/**
 * <P>A connection (session) with a specific
 * database. Within the context of a Connection, SQL statements are
 * executed and results are returned.
 *
 * <P>A Connection's database is able to provide information
 * describing its tables, its supported SQL grammar, its stored
 * procedures, the capabilities of this connection, and so on. This
 * information is obtained with the <code>getMetaData</code> method.
 *
 * <P><B>Note:</B> By default the Connection automatically commits
 * changes after executing each statement. If auto commit has been
 * disabled, an explicit commit must be done or database changes will
 * not be saved.
 *
 * @see DriverManager#getConnection
 * @see <{Statement}>
 * @see <{ResultSet}>
 * @see <{StatementProxy}>
 * @stereotype container
// */
//public class ConnectionProxy implements Connection
//        // Disable because we cannot keep jbdbc2 and 3 happy
//        //implements Connection
//
//
//{
//
//    private Connection m_Connection;
//
//    public ConnectionProxy()
//    {
//    }
//
//    public void setConnection(Connection c) throws SQLException
//    {
//        m_Connection = c;
//    }
//
//    public Connection getConnection()
//    {
//        return (m_Connection);
//    }
//
//    /**
//     * Creates a <code>Statement</code> object for sending
//     * SQL statements to the database.
//     * SQL statements without parameters are normally
//     * executed using Statement objects. If the same SQL statement
//     * is executed many times, it is more efficient to use a
//     * PreparedStatement
//     *
//     * JDBC 2.0
//     *
//     * Result sets created using the returned Statement will have
//     * forward-only type, and read-only concurrency, by default.
//     *
//     * @return a new Statement object
//     * @exception SQLException if a database access error occurs
//     */
//    public Statement createStatement() throws SQLException
//    {
//        /* StatementProxy ret = buildStatementProxy();
//         ret.setStatement(m_Connection.createStatement());
//         return (ret);
//         */
//        return m_Connection.createStatement();
//    }
//
//    public Statement createStatement(int i1, int i2, int i3) throws SQLException
//    {
//        return m_Connection.createStatement(i1,  i2,  i3);
//    }
//
//    public PreparedStatement prepareStatement(int i1, int i2, int i3) throws SQLException
//    {
//         throw new UnsupportedOperationException("Fix This");
//    }
//
//    public PreparedStatement prepareStatement(String s, int i1, int i2, int i3) throws SQLException
//    {
//        throw new UnsupportedOperationException("Fix This");
//    }
//
//    public PreparedStatement prepareStatement(String s, int i1) throws SQLException
//    {
//        throw new UnsupportedOperationException("Fix This");
//    }
//
//    public PreparedStatement prepareStatement(String s, int[] i1) throws SQLException
//    {
//        throw new UnsupportedOperationException("Fix This");
//    }
//
//    public PreparedStatement prepareStatement(String s, String[] i1) throws SQLException
//    {
//        throw new UnsupportedOperationException("Fix This");
//    }
//
//    public PreparedStatement prepareStatement(String s) throws SQLException
//    {
//        return m_Connection.prepareStatement(s);
//     }
//
//    public CallableStatement prepareCall(String s, int i1, int i2, int i3) throws SQLException
//    {
//        throw new UnsupportedOperationException("Fix This");
//    }
//
//    public CallableStatement prepareCall(String s) throws SQLException
//    {
//        return m_Connection.prepareCall(s);
//      }
//
//    protected StatementProxy buildStatementProxy()
//    {
//        StatementProxy ret = new StatementProxy(this);
//        return (ret);
//    }
//
//
//
//    /**
//     * Creates a <code>PreparedStatement</code> object for sending
//     * parameterized SQL statements to the database.
//     *
//     * A SQL statement with or without IN parameters can be
//     * pre-compiled and stored in a PreparedStatement object. This
//     * object can then be used to efficiently execute this statement
//     * multiple times.
//     *
//     * <P><B>Note:</B> This method is optimized for handling
//     * parametric SQL statements that benefit from precompilation. If
//     * the driver supports precompilation,
//     * the method <code>prepareStatement</code> will send
//     * the statement to the database for precompilation. Some drivers
//     * may not support precompilation. In this case, the statement may
//     * not be sent to the database until the <code>PreparedStatement</code> is
//     * executed.  This has no direct effect on users; however, it does
//     * affect which method throws certain SQLExceptions.
//     *
//     * JDBC 2.0
//     *
//     * Result sets created using the returned PreparedStatement will have
//     * forward-only type and read-only concurrency, by default.
//     *
//     * @param sql a SQL statement that may contain one or more '?' IN
//     * parameter placeholders
//     * @return a new PreparedStatement object containing the
//     * pre-compiled statement
//     * @exception SQLException if a database access error occurs
//     */
//    /*   public PreparedStatement prepareStatement(String sql)
//               throws SQLException {
//           StatementProxy ret = buildStatementProxy();
//           ret.setStatement(m_Connection.prepareStatement(sql));
//           return (ret);
//       }
//       */
//
//    /**
//     * Creates a <code>CallableStatement</code> object for calling
//     * database stored procedures.
//     * The CallableStatement provides
//     * methods for setting up its IN and OUT parameters, and
//     * methods for executing the call to a stored procedure.
//     *
//     * <P><B>Note:</B> This method is optimized for handling stored
//     * procedure call statements. Some drivers may send the call
//     * statement to the database when the method <code>prepareCall</code>
//     * is done; others
//     * may wait until the CallableStatement is executed. This has no
//     * direct effect on users; however, it does affect which method
//     * throws certain SQLExceptions.
//     *
//     * JDBC 2.0
//     *
//     * Result sets created using the returned CallableStatement will have
//     * forward-only type and read-only concurrency, by default.
//     *
//     * @param sql a SQL statement that may contain one or more '?'
//     * parameter placeholders. Typically this  statement is a JDBC
//     * function call escape string.
//     * @return a new CallableStatement object containing the
//     * pre-compiled SQL statement
//     * @exception SQLException if a database access error occurs
//     */
//    /*   public CallableStatement prepareCall(String sql) throws SQLException {
//           StatementProxy ret = buildStatementProxy();
//           ret.setStatement(m_Connection.prepareCall(sql));
//           return (ret);
//       }
//       */
//
//    /**
//     * Converts the given SQL statement into the system's native SQL grammar.
//     * A driver may convert the JDBC sql grammar into its system's
//     * native SQL grammar prior to sending it; this method returns the
//     * native form of the statement that the driver would have sent.
//     *
//     * @param sql a SQL statement that may contain one or more '?'
//     * parameter placeholders
//     * @return the native form of this statement
//     * @exception SQLException if a database access error occurs
//     */
//    public String nativeSQL(String sql) throws SQLException
//    {
//        return (m_Connection.nativeSQL(sql));
//    }
//
//    /**
//     * Sets this connection's auto-commit mode.
//     * If a connection is in auto-commit mode, then all its SQL
//     * statements will be executed and committed as individual
//     * transactions.  Otherwise, its SQL statements are grouped into
//     * transactions that are terminated by a call to either
//     * the method <code>commit</code> or the method <code>rollback</code>.
//     * By default, new connections are in auto-commit
//     * mode.
//     *
//     * The commit occurs when the statement completes or the next
//     * execute occurs, whichever comes first. In the case of
//     * statements returning a ResultSet, the statement completes when
//     * the last row of the ResultSet has been retrieved or the
//     * ResultSet has been closed. In advanced cases, a single
//     * statement may return multiple results as well as output
//     * parameter values. In these cases the commit occurs when all results and
//     * output parameter values have been retrieved.
//     *
//     * @param autoCommit true enables auto-commit; false disables
//     * auto-commit.
//     * @exception SQLException if a database access error occurs
//     */
//    public void setAutoCommit(boolean autoCommit) throws SQLException
//    {
//        m_Connection.setAutoCommit(autoCommit);
//    }
//
//    /**
//     * Gets the current auto-commit state.
//     *
//     * @return the current state of auto-commit mode
//     * @exception SQLException if a database access error occurs
//     * @see #setAutoCommit
//     */
//    public boolean getAutoCommit() throws SQLException
//    {
//        return (m_Connection.getAutoCommit());
//    }
//
//    /**
//     * Makes all changes made since the previous
//     * commit/rollback permanent and releases any database locks
//     * currently held by the Connection. This method should be
//     * used only when auto-commit mode has been disabled.
//     *
//     * @exception SQLException if a database access error occurs
//     * @see #setAutoCommit
//     */
//    public void commit() throws SQLException
//    {
//        m_Connection.commit();
//    }
//
//    /**
//     * Drops all changes made since the previous
//     * commit/rollback and releases any database locks currently held
//     * by this Connection. This method should be used only when auto-
//     * commit has been disabled.
//     *
//     * @exception SQLException if a database access error occurs
//     * @see #setAutoCommit
//     */
//    public void rollback() throws SQLException
//    {
//        m_Connection.rollback();
//    }
//
//    /**
//     * Releases a Connection's database and JDBC resources
//     * immediately instead of waiting for
//     * them to be automatically released.
//     *
//     * <P><B>Note:</B> A Connection is automatically closed when it is
//     * garbage collected. Certain fatal errors also result in a closed
//     * Connection.
//     *
//     * @exception SQLException if a database access error occurs
//     */
//    public void close() throws SQLException
//    {
//        m_Connection.close();
//    }
//
//    /**
//     * Tests to see if a Connection is closed.
//     *
//     * @return true if the connection is closed; false if it's still open
//     * @exception SQLException if a database access error occurs
//     */
//    public boolean isClosed() throws SQLException
//    {
//        return (m_Connection.isClosed());
//    }
//
//    //======================================================================
//    // Advanced features:
//
//    /**
//     * Gets the metadata regarding this connection's database.
//     * A Connection's database is able to provide information
//     * describing its tables, its supported SQL grammar, its stored
//     * procedures, the capabilities of this connection, and so on. This
//     * information is made available through a DatabaseMetaData
//     * object.
//     *
//     * @return a DatabaseMetaData object for this Connection
//     * @exception SQLException if a database access error occurs
//     */
//    public DatabaseMetaData getMetaData() throws SQLException
//    {
//        return (m_Connection.getMetaData());
//    }
//
//    /**
//     * Puts this connection in read-only mode as a hint to enable
//     * database optimizations.
//     *
//     * <P><B>Note:</B> This method cannot be called while in the
//     * middle of a transaction.
//     *
//     * @param readOnly true enables read-only mode; false disables
//     * read-only mode.
//     * @exception SQLException if a database access error occurs
//     */
//    public void setReadOnly(boolean readOnly) throws SQLException
//    {
//        m_Connection.setReadOnly(readOnly);
//    }
//
//    /**
//     * Tests to see if the connection is in read-only mode.
//     *
//     * @return true if connection is read-only and false otherwise
//     * @exception SQLException if a database access error occurs
//     */
//    public boolean isReadOnly() throws SQLException
//    {
//        return (m_Connection.isReadOnly());
//    }
//
//    /**
//     * Sets a catalog name in order to select
//     * a subspace of this Connection's database in which to work.
//     * If the driver does not support catalogs, it will
//     * silently ignore this request.
//     *
//     * @exception SQLException if a database access error occurs
//     */
//    public void setCatalog(String catalog) throws SQLException
//    {
//        m_Connection.setCatalog(catalog);
//    }
//
//    /**
//     * Returns the Connection's current catalog name.
//     *
//     * @return the current catalog name or null
//     * @exception SQLException if a database access error occurs
//     */
//    public String getCatalog() throws SQLException
//    {
//        return (m_Connection.getCatalog());
//    }
//
//    /**
//     * Attempts to change the transaction
//     * isolation level to the one given.
//     * The constants defined in the interface <code>Connection</code>
//     * are the possible transaction isolation levels.
//     *
//     * <P><B>Note:</B> This method cannot be called while
//     * in the middle of a transaction.
//     *
//     * @param level one of the TRANSACTION_* isolation values with the
//     * exception of TRANSACTION_NONE; some databases may not support
//     * other values
//     * @exception SQLException if a database access error occurs
//     * @see DatabaseMetaData#supportsTransactionIsolationLevel
//     */
//    public void setTransactionIsolation(int level) throws SQLException
//    {
//        m_Connection.setTransactionIsolation(level);
//    }
//
//    /**
//     * Gets this Connection's current transaction isolation level.
//     *
//     * @return the current TRANSACTION_* mode value
//     * @exception SQLException if a database access error occurs
//     */
//    public int getTransactionIsolation() throws SQLException
//    {
//        return (m_Connection.getTransactionIsolation());
//    }
//
//    /**
//     * Returns the first warning reported by calls on this Connection.
//     *
//     * <P><B>Note:</B> Subsequent warnings will be chained to this
//     * SQLWarning.
//     *
//     * @return the first SQLWarning or null
//     * @exception SQLException if a database access error occurs
//     */
//    public SQLWarning getWarnings() throws SQLException
//    {
//        return (m_Connection.getWarnings());
//    }
//
//    /**
//     * Clears all warnings reported for this <code>Connection</code> object.
//     * After a call to this method, the method <code>getWarnings</code>
//     * returns null until a new warning is
//     * reported for this Connection.
//     *
//     * @exception SQLException if a database access error occurs
//     */
//    public void clearWarnings() throws SQLException
//    {
//        m_Connection.clearWarnings();
//    }
//
//
//    //--------------------------JDBC 2.0-----------------------------
//
//    /**
//     * JDBC 2.0
//     *
//     * Creates a <code>Statement</code> object that will generate
//     * <code>ResultSet</code> objects with the given type and concurrency.
//     * This method is the same as the <code>createStatement</code> method
//     * above, but it allows the default result set
//     * type and result set concurrency type to be overridden.
//     *
//     * @param resultSetType a result set type; see ResultSet.TYPE_XXX
//     * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
//     * @return a new Statement object
//     * @exception SQLException if a database access error occurs
//     */
//    public Statement createStatement(int resultSetType, int resultSetConcurrency)
//            throws SQLException
//    {
//        return (m_Connection.createStatement(resultSetType, resultSetConcurrency));
//    }
//
//    /**
//     * JDBC 2.0
//     *
//     * Creates a <code>PreparedStatement</code> object that will generate
//     * <code>ResultSet</code> objects with the given type and concurrency.
//     * This method is the same as the <code>prepareStatement</code> method
//     * above, but it allows the default result set
//     * type and result set concurrency type to be overridden.
//     *
//     * @param resultSetType a result set type; see ResultSet.TYPE_XXX
//     * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
//     * @return a new PreparedStatement object containing the
//     * pre-compiled SQL statement
//     * @exception SQLException if a database access error occurs
//     */
//    public PreparedStatement prepareStatement(String sql, int resultSetType,
//                                              int resultSetConcurrency)
//            throws SQLException
//    {
//        return (m_Connection.prepareStatement(sql, resultSetType, resultSetConcurrency));
//    }
//
//    /**
//     * JDBC 2.0
//     *
//     * Creates a <code>CallableStatement</code> object that will generate
//     * <code>ResultSet</code> objects with the given type and concurrency.
//     * This method is the same as the <code>prepareCall</code> method
//     * above, but it allows the default result set
//     * type and result set concurrency type to be overridden.
//     *
//     * @param resultSetType a result set type; see ResultSet.TYPE_XXX
//     * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
//     * @return a new CallableStatement object containing the
//     * pre-compiled SQL statement
//     * @exception SQLException if a database access error occurs
//     */
//    public CallableStatement prepareCall(String sql, int resultSetType,
//                                         int resultSetConcurrency) throws SQLException
//    {
//        return (m_Connection.prepareCall(sql, resultSetType, resultSetConcurrency));
//    }
//
//    /**
//     * JDBC 2.0
//     *
//     * Gets the type map object associated with this connection.
//     * Unless the application has added an entry to the type map,
//     * the map returned will be empty.
//     *
//     * @return the <code>java.util.Map</code> object associated
//     *         with this <code>Connection</code> object
//     */
//    public Map getTypeMap() throws SQLException
//    {
//        return (m_Connection.getTypeMap());
//    }
//
//    /**
//     * JDBC 2.0
//     *
//     * Installs the given type map as the type map for
//     * this connection.  The type map will be used for the
//     * custom mapping of SQL structured types and distinct types.
//     *
//     * @param map <code>java.util.Map</code> object to install
//     *        as the replacement for this <code>Connection</code>
//     *        object's default type map
//     */
//    public void setTypeMap(Map map) throws SQLException
//    {
//        m_Connection.setTypeMap(map);
//    }
//
//    /**
//     * Changes the holdability of <code>ResultSet</code> objects
//     * created using this <code>Connection</code> object to the given
//     * holdability.
//     *
//     * @param holdability a <code>ResultSet</code> holdability constant; one of
//     *        <code>ResultSet.HOLD_CURSORS_OVER_COMMIT</code> or
//     *        <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
//     * @throws SQLException if a database access occurs, the given parameter
//     *         is not a <code>ResultSet</code> constant indicating holdability,
//     *         or the given holdability is not supported
//     * @see #getHoldability
//     * @see ResultSet
//     * @since 1.4
//     */
//
//    public void setHoldability(int holdability) throws SQLException
//    {
//        m_Connection.setHoldability(holdability);
//    }
//
//    public int getHoldability() throws SQLException
//    {
//        return (m_Connection.getHoldability());
//    }
//
//    public Savepoint setSavepoint() throws SQLException
//    {
//        return (m_Connection.setSavepoint());
//    }
//
//    public Savepoint setSavepoint(String s) throws SQLException
//    {
//        return (m_Connection.setSavepoint(s));
//    }
//
//    public void rollback(Savepoint s) throws SQLException
//    {
//        m_Connection.rollback(s);
//    }
//
//    public void releaseSavepoint(Savepoint s) throws SQLException
//    {
//        m_Connection.releaseSavepoint(s);
//    }
//
// /*
//
//    public Clob createClob() throws SQLException {
//       return  m_Connection.createClob();
//    }
//
//    public Blob createBlob() throws SQLException {
//        return  m_Connection.createBlob();
//     }
//
//    public NClob createNClob() throws SQLException {
//        return  m_Connection.createNClob();
//      }
//
//    public SQLXML createSQLXML() throws SQLException {
//        return  m_Connection.createSQLXML();
//      }
//
//    public boolean isValid(int timeout) throws SQLException {
//        return  m_Connection.isValid(timeout);
//       }
//
//    public void setClientInfo(String name, String value) throws SQLClientInfoException {
//        if (true) throw new UnsupportedOperationException("Fix This");
//
//    }
//
//    public void setClientInfo(Properties properties) throws SQLClientInfoException {
//        if (true) throw new UnsupportedOperationException("Fix This");
//
//    }
//
//    public String getClientInfo(String name) throws SQLException {
//        if (true) throw new UnsupportedOperationException("Fix This");
//        return null;
//    }
//
//    public Properties getClientInfo() throws SQLException {
//        if (true) throw new UnsupportedOperationException("Fix This");
//        return null;
//    }
//
//    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
//        if (true) throw new UnsupportedOperationException("Fix This");
//        return null;
//    }
//    */
//
//
//    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
//        if (true) throw new UnsupportedOperationException("Fix This");
//        return null;
//    }
//
//    public <T> T unwrap(Class<T> iface) throws SQLException {
//        if (true) throw new UnsupportedOperationException("Fix This");
//        return null;
//    }
//
//    public boolean isWrapperFor(Class<?> iface) throws SQLException {
//        if (true) throw new UnsupportedOperationException("Fix This");
//        return false;
//    }
//}
