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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;

import org.safris.commons.logging.Logger;

public class StatementProxy implements Statement {
  private static final Logger logger = Logger.getLogger(StatementProxy.class.getName());

  public static void close(final Statement statement) {
    try {
      if (statement != null && !statement.isClosed())
        statement.close();
    }
    catch (final SQLException e) {
      logger.throwing(Statement.class.getName(), "close", e);
    }
  }

  protected final Statement statement;

  public StatementProxy(final Statement statement) {
    this.statement = statement;
  }

  public ResultSet executeQuery(final String sql) throws SQLException {
    final long time = System.currentTimeMillis();
    final ResultSetProxy resultSet = new ResultSetProxy(statement.executeQuery(sql));

    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeQuery(\n");
      buffer.append(sql);
      buffer.append("\n) -> " + resultSet.getSize() + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }

    return resultSet;
  }

  public int executeUpdate(final String sql) throws SQLException {
    final long time = System.currentTimeMillis();
    int count = statement.executeUpdate(sql);
    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
      buffer.append(sql);

      buffer.append("\n) -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }

    return count;
  }

  public void close() throws SQLException {
    try {
      statement.close();
    }
    catch (final SQLException e) {
      if (e.getMessage() != null && !"Connection is closed.".equals(e.getMessage()))
        throw e;
    }
  }

  public int getMaxFieldSize() throws SQLException {
    return statement.getMaxFieldSize();
  }

  public void setMaxFieldSize(final int max) throws SQLException {
    statement.setMaxFieldSize(max);
  }

  public int getMaxRows() throws SQLException {
    return statement.getMaxRows();
  }

  public void setMaxRows(final int max) throws SQLException {
    statement.setMaxRows(max);
  }

  public void setEscapeProcessing(final boolean enable) throws SQLException {
    statement.setEscapeProcessing(enable);
  }

  public int getQueryTimeout() throws SQLException {
    return statement.getQueryTimeout();
  }

  public void setQueryTimeout(final int seconds) throws SQLException {
    statement.setQueryTimeout(seconds);
  }

  public void cancel() throws SQLException {
    statement.cancel();
  }

  public SQLWarning getWarnings() throws SQLException {
    return statement.getWarnings();
  }

  public void clearWarnings() throws SQLException {
    statement.clearWarnings();
  }

  public void setCursorName(final String name) throws SQLException {
    statement.setCursorName(name);
  }

  public boolean execute(final String sql) throws SQLException {
    final long time = System.currentTimeMillis();
    final boolean result = statement.execute(sql);
    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
      buffer.append(sql);

      buffer.append("\n) -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }
    
    return result;
  }

  public ResultSet getResultSet() throws SQLException {
    return new ResultSetProxy(statement.getResultSet());
  }

  public int getUpdateCount() throws SQLException {
    return statement.getUpdateCount();
  }

  public boolean getMoreResults() throws SQLException {
    return statement.getMoreResults();
  }

  public void setFetchDirection(final int direction) throws SQLException {
    statement.setFetchDirection(direction);
  }

  public int getFetchDirection() throws SQLException {
    return statement.getFetchDirection();
  }

  public void setFetchSize(final int rows) throws SQLException {
    statement.setFetchSize(rows);
  }

  public int getFetchSize() throws SQLException {
    return statement.getFetchSize();
  }

  public int getResultSetConcurrency() throws SQLException {
    return statement.getResultSetConcurrency();
  }

  public int getResultSetType() throws SQLException {
    return statement.getResultSetType();
  }

  public void addBatch(final String sql) throws SQLException {
    statement.addBatch(sql);
  }

  public void clearBatch() throws SQLException {
    statement.clearBatch();
  }

  public int[] executeBatch() throws SQLException {
    return statement.executeBatch();
  }

  public Connection getConnection() throws SQLException {
    return statement.getConnection();
  }

  public boolean getMoreResults(final int current) throws SQLException {
    return statement.getMoreResults(current);
  }

  public ResultSet getGeneratedKeys() throws SQLException {
    return new ResultSetProxy(statement.getGeneratedKeys());
  }

  public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
    final long time = System.currentTimeMillis();
    final int count = statement.executeUpdate(sql, autoGeneratedKeys);
    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
      buffer.append(sql);

      buffer.append("\n, " + autoGeneratedKeys + ") -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }

    return count;
  }

  public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
    final long time = System.currentTimeMillis();
    final int count = statement.executeUpdate(sql, columnIndexes);
    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
      buffer.append(sql);

      buffer.append("\n, [" + Arrays.toString(columnIndexes) + "]) -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }

    return count;
  }

  public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
    final long time = System.currentTimeMillis();
    final int count = statement.executeUpdate(sql, columnNames);
    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
      buffer.append(sql);

      buffer.append("\n, " + Arrays.toString(columnNames) + ") -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }

    return count;
  }

  public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
    final long time = System.currentTimeMillis();
    final boolean result = statement.execute(sql, autoGeneratedKeys);
    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
      buffer.append(sql);

      buffer.append("\n, " + autoGeneratedKeys + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());

    }

    return result;
  }

  public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
    final long time = System.currentTimeMillis();
    final boolean result = statement.execute(sql, columnIndexes);
    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
      buffer.append(sql);

      buffer.append("\n, " + Arrays.toString(columnIndexes) + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }

    return result;
  }

  public boolean execute(final String sql, final String[] columnNames) throws SQLException {
    final long time = System.currentTimeMillis();
    final boolean result = statement.execute(sql, columnNames);
    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
      buffer.append(sql);

      buffer.append("\n, " + Arrays.toString(columnNames) + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }

    return result;
  }

  public int getResultSetHoldability() throws SQLException {
    return statement.getResultSetHoldability();
  }

  public boolean isClosed() throws SQLException {
    return statement.isClosed();
  }

  public void setPoolable(final boolean poolable) throws SQLException {
    statement.setPoolable(poolable);
  }

  public boolean isPoolable() throws SQLException {
    return statement.isPoolable();
  }

  public <T extends Object>T unwrap(final Class<T> iface) throws SQLException {
    return statement.unwrap(iface);
  }

  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return statement.isWrapperFor(iface);
  }

  public void closeOnCompletion() throws SQLException {
    statement.closeOnCompletion();
  }

  public boolean isCloseOnCompletion() throws SQLException {
    return statement.isCloseOnCompletion();
  }
}