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
import java.util.logging.Logger;

public class StatementProxy implements Statement {
  private static final Logger logger = Logger.getLogger(StatementProxy.class.getName());
  private static final Level defaultLogLevel = Level.FINE;

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

  @Override
  public ResultSet executeQuery(final String sql) throws SQLException {
    Level logLevel = defaultLogLevel;

    int size = -1;
    ResultSetProxy resultSet;
    final long time = System.currentTimeMillis();
    try {
      resultSet = new ResultSetProxy(statement.executeQuery(sql));
      if (logger.isLoggable(logLevel))
        size = resultSet.getSize();
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeQuery(\n");
        buffer.append(sql);
        buffer.append("\n) -> " + size + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return resultSet;
  }

  @Override
  public int executeUpdate(final String sql) throws SQLException {
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    int count = -1;
    try {
      count = statement.executeUpdate(sql);
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
        buffer.append(sql);

        buffer.append("\n) -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return count;
  }

  @Override
  public void close() throws SQLException {
    try {
      statement.close();
    }
    catch (final SQLException e) {
      if (e.getMessage() != null && !"Connection is closed.".equals(e.getMessage()))
        throw e;
    }
  }

  @Override
  public int getMaxFieldSize() throws SQLException {
    return statement.getMaxFieldSize();
  }

  @Override
  public void setMaxFieldSize(final int max) throws SQLException {
    statement.setMaxFieldSize(max);
  }

  @Override
  public int getMaxRows() throws SQLException {
    return statement.getMaxRows();
  }

  @Override
  public void setMaxRows(final int max) throws SQLException {
    statement.setMaxRows(max);
  }

  @Override
  public void setEscapeProcessing(final boolean enable) throws SQLException {
    statement.setEscapeProcessing(enable);
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    return statement.getQueryTimeout();
  }

  @Override
  public void setQueryTimeout(final int seconds) throws SQLException {
    statement.setQueryTimeout(seconds);
  }

  @Override
  public void cancel() throws SQLException {
    statement.cancel();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return statement.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    statement.clearWarnings();
  }

  @Override
  public void setCursorName(final String name) throws SQLException {
    statement.setCursorName(name);
  }

  @Override
  public boolean execute(final String sql) throws SQLException {
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    Boolean result = null;
    try {
      result = statement.execute(sql);
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
        buffer.append(sql);

        buffer.append("\n) -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return result;
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    return new ResultSetProxy(statement.getResultSet());
  }

  @Override
  public int getUpdateCount() throws SQLException {
    return statement.getUpdateCount();
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    return statement.getMoreResults();
  }

  @Override
  public void setFetchDirection(final int direction) throws SQLException {
    statement.setFetchDirection(direction);
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return statement.getFetchDirection();
  }

  @Override
  public void setFetchSize(final int rows) throws SQLException {
    statement.setFetchSize(rows);
  }

  @Override
  public int getFetchSize() throws SQLException {
    return statement.getFetchSize();
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {
    return statement.getResultSetConcurrency();
  }

  @Override
  public int getResultSetType() throws SQLException {
    return statement.getResultSetType();
  }

  @Override
  public void addBatch(final String sql) throws SQLException {
    statement.addBatch(sql);
  }

  @Override
  public void clearBatch() throws SQLException {
    statement.clearBatch();
  }

  @Override
  public int[] executeBatch() throws SQLException {
    return statement.executeBatch();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return statement.getConnection();
  }

  @Override
  public boolean getMoreResults(final int current) throws SQLException {
    return statement.getMoreResults(current);
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    return new ResultSetProxy(statement.getGeneratedKeys());
  }

  @Override
  public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    int count = -1;
    try {
      count = statement.executeUpdate(sql, autoGeneratedKeys);
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
        buffer.append(sql);

        buffer.append("\n, " + autoGeneratedKeys + ") -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return count;
  }

  @Override
  public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    int count = -1;
    try {
      count = statement.executeUpdate(sql, columnIndexes);
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
        buffer.append(sql);

        buffer.append("\n, [" + Arrays.toString(columnIndexes) + "]) -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return count;
  }

  @Override
  public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    int count = -1;
    try {
      count = statement.executeUpdate(sql, columnNames);
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
        buffer.append(sql);

        buffer.append("\n, " + Arrays.toString(columnNames) + ") -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return count;
  }

  @Override
  public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    Boolean result = null;
    try {
      result = statement.execute(sql, autoGeneratedKeys);
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
        buffer.append(sql);

        buffer.append("\n, " + autoGeneratedKeys + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return result;
  }

  @Override
  public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    Boolean result = null;
    try {
      result = statement.execute(sql, columnIndexes);
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
        buffer.append(sql);

        buffer.append("\n, " + Arrays.toString(columnIndexes) + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return result;
  }

  @Override
  public boolean execute(final String sql, final String[] columnNames) throws SQLException {
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    Boolean result = null;
    try {
      result = statement.execute(sql, columnNames);
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
        buffer.append(sql);

        buffer.append("\n, " + Arrays.toString(columnNames) + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        logger.fine(buffer.toString());
      }
    }

    return result;
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    return statement.getResultSetHoldability();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return statement.isClosed();
  }

  @Override
  public void setPoolable(final boolean poolable) throws SQLException {
    statement.setPoolable(poolable);
  }

  @Override
  public boolean isPoolable() throws SQLException {
    return statement.isPoolable();
  }

  @Override
  public <T extends Object>T unwrap(final Class<T> iface) throws SQLException {
    return statement.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return statement.isWrapperFor(iface);
  }

  @Override
  public void closeOnCompletion() throws SQLException {
    statement.closeOnCompletion();
  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    return statement.isCloseOnCompletion();
  }
}