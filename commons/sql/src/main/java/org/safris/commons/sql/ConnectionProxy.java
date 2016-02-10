/* Copyright (c) 2009 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public final class ConnectionProxy implements Connection {
  public static void close(final Connection connection) {
    try {
      if (connection != null && !connection.isClosed())
        connection.close();
    }
    catch (final SQLException e) {
    }
  }

  private final Connection connection;

  public ConnectionProxy(final Connection connection) {
    this.connection = connection;
  }

  public Statement createStatement() throws SQLException {
    return new StatementProxy(connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
  }

  public PreparedStatement prepareStatement(final String sql) throws SQLException {
    return new PreparedStatementProxy(connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), sql);
  }

  public CallableStatement prepareCall(final String sql) throws SQLException {
    return new CallableStatementProxy(connection.prepareCall(sql), sql);
  }

  public String nativeSQL(final String sql) throws SQLException {
    return connection.nativeSQL(sql);
  }

  public void setAutoCommit(final boolean autoCommit) throws SQLException {
    connection.setAutoCommit(autoCommit);
  }

  public boolean getAutoCommit() throws SQLException {
    return connection.getAutoCommit();
  }

  public void commit() throws SQLException {
    connection.commit();
  }

  public void rollback() throws SQLException {
    connection.rollback();
  }

  public void close() throws SQLException {
    connection.close();
  }

  public boolean isClosed() throws SQLException {
    return connection.isClosed();
  }

  public DatabaseMetaData getMetaData() throws SQLException {
    return connection.getMetaData();
  }

  public void setReadOnly(final boolean readOnly) throws SQLException {
    connection.setReadOnly(readOnly);
  }

  public boolean isReadOnly() throws SQLException {
    return connection.isReadOnly();
  }

  public void setCatalog(final String catalog) throws SQLException {
    connection.setCatalog(catalog);
  }

  public String getCatalog() throws SQLException {
    return connection.getCatalog();
  }

  public void setTransactionIsolation(final int level) throws SQLException {
    connection.setTransactionIsolation(level);
  }

  public int getTransactionIsolation() throws SQLException {
    return connection.getTransactionIsolation();
  }

  public SQLWarning getWarnings() throws SQLException {
    return connection.getWarnings();
  }

  public void clearWarnings() throws SQLException {
    connection.clearWarnings();
  }

  public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
    return new StatementProxy(connection.createStatement(resultSetType > ResultSet.TYPE_FORWARD_ONLY ? resultSetType : ResultSet.TYPE_SCROLL_INSENSITIVE, resultSetConcurrency));
  }

  public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
    return new PreparedStatementProxy(connection.prepareStatement(sql, resultSetType > ResultSet.TYPE_FORWARD_ONLY ? resultSetType : ResultSet.TYPE_SCROLL_INSENSITIVE, resultSetConcurrency), sql);
  }

  public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
    return new CallableStatementProxy(connection.prepareCall(sql, resultSetType > ResultSet.TYPE_FORWARD_ONLY ? resultSetType : ResultSet.TYPE_SCROLL_INSENSITIVE, resultSetConcurrency), sql);
  }

  public Map<String,Class<?>> getTypeMap() throws SQLException {
    return connection.getTypeMap();
  }

  public void setTypeMap(final Map<String,Class<?>> map) throws SQLException {
    connection.setTypeMap(map);
  }

  public void setHoldability(final int holdability) throws SQLException {
    connection.setHoldability(holdability);
  }

  public int getHoldability() throws SQLException {
    return connection.getHoldability();
  }

  public Savepoint setSavepoint() throws SQLException {
    return connection.setSavepoint();
  }

  public Savepoint setSavepoint(final String name) throws SQLException {
    return connection.setSavepoint(name);
  }

  public void rollback(final Savepoint savepoint) throws SQLException {
    connection.rollback(savepoint);
  }

  public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
    connection.releaseSavepoint(savepoint);
  }

  public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
    return new StatementProxy(connection.createStatement(resultSetType > ResultSet.TYPE_FORWARD_ONLY ? resultSetType : ResultSet.TYPE_SCROLL_INSENSITIVE, resultSetConcurrency, resultSetHoldability));
  }

  public PreparedStatement prepareStatement(final String sql, final int resultSetType, int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
    return new PreparedStatementProxy(connection.prepareStatement(sql, resultSetType > ResultSet.TYPE_FORWARD_ONLY ? resultSetType : ResultSet.TYPE_SCROLL_INSENSITIVE, resultSetConcurrency, resultSetHoldability), sql);
  }

  public CallableStatement prepareCall(final String sql, final int resultSetType, int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
    return new CallableStatementProxy(connection.prepareCall(sql, resultSetType > ResultSet.TYPE_FORWARD_ONLY ? resultSetType : ResultSet.TYPE_SCROLL_INSENSITIVE, resultSetConcurrency, resultSetHoldability), sql);
  }

  public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
    return new PreparedStatementProxy(connection.prepareStatement(sql, autoGeneratedKeys), sql);
  }

  public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes) throws SQLException {
    return new PreparedStatementProxy(connection.prepareStatement(sql, columnIndexes), sql);
  }

  public PreparedStatement prepareStatement(final String sql, final String[] columnNames) throws SQLException {
    return new PreparedStatementProxy(connection.prepareStatement(sql, columnNames), sql);
  }

  public Clob createClob() throws SQLException {
    return connection.createClob();
  }

  public Blob createBlob() throws SQLException {
    return connection.createBlob();
  }

  public NClob createNClob() throws SQLException {
    return connection.createNClob();
  }

  public SQLXML createSQLXML() throws SQLException {
    return connection.createSQLXML();
  }

  public boolean isValid(final int timeout) throws SQLException {
    return connection.isValid(timeout);
  }

  public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
    connection.setClientInfo(name, value);
  }

  public void setClientInfo(final Properties properties) throws SQLClientInfoException {
    connection.setClientInfo(properties);
  }

  public String getClientInfo(final String name) throws SQLException {
    return connection.getClientInfo(name);
  }

  public Properties getClientInfo() throws SQLException {
    return connection.getClientInfo();
  }

  public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
    return connection.createArrayOf(typeName, elements);
  }

  public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
    return connection.createStruct(typeName, attributes);
  }

  public <T extends Object>T unwrap(final Class<T> iface) throws SQLException {
    return connection.unwrap(iface);
  }

  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return connection.isWrapperFor(iface);
  }

  public void setSchema(final String schema) throws SQLException {
    connection.setSchema(schema);
  }

  public String getSchema() throws SQLException {
    return connection.getSchema();
  }

  public void abort(final Executor executor) throws SQLException {
    connection.abort(executor);
  }

  public void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
    connection.setNetworkTimeout(executor, milliseconds);
  }

  public int getNetworkTimeout() throws SQLException {
    return connection.getNetworkTimeout();
  }
}