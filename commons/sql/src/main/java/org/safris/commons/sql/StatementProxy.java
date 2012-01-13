/*  Copyright Safris Software 2009
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
  private final Statement statement;

  public StatementProxy(Statement statement) {
    this.statement = statement;
  }

  public ResultSet executeQuery(String sql) throws SQLException {
    final long time = System.currentTimeMillis();
    final ResultSetProxy resultSet = new ResultSetProxy(statement.executeQuery(sql));

    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeQuery(\n");
      buffer.append(sql);
      buffer.append("\n) -> " + resultSet.getSize() + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());
    }

    return resultSet;
  }

  public int executeUpdate(String sql) throws SQLException {
    final long time = System.currentTimeMillis();
    int count = statement.executeUpdate(sql);
    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
      buffer.append(sql);

      buffer.append("\n) -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());
    }

    return count;
  }

  public void close() throws SQLException {
    try {
      statement.close();
    }
    catch (SQLException e) {
      if (e.getMessage() != null && !"Connection is closed.".equals(e.getMessage()))
        throw e;
    }
  }

  public int getMaxFieldSize() throws SQLException {
    return statement.getMaxFieldSize();
  }

  public void setMaxFieldSize(int max) throws SQLException {
    statement.setMaxFieldSize(max);
  }

  public int getMaxRows() throws SQLException {
    return statement.getMaxRows();
  }

  public void setMaxRows(int max) throws SQLException {
    statement.setMaxRows(max);
  }

  public void setEscapeProcessing(boolean enable) throws SQLException {
    statement.setEscapeProcessing(enable);
  }

  public int getQueryTimeout() throws SQLException {
    return statement.getQueryTimeout();
  }

  public void setQueryTimeout(int seconds) throws SQLException {
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

  public void setCursorName(String name) throws SQLException {
    statement.setCursorName(name);
  }

  public boolean execute(String sql) throws SQLException {
    final long time = System.currentTimeMillis();
    final boolean result = statement.execute(sql);
    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
      buffer.append(sql);

      buffer.append("\n) -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());
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

  public void setFetchDirection(int direction) throws SQLException {
    statement.setFetchDirection(direction);
  }

  public int getFetchDirection() throws SQLException {
    return statement.getFetchDirection();
  }

  public void setFetchSize(int rows) throws SQLException {
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

  public void addBatch(String sql) throws SQLException {
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

  public boolean getMoreResults(int current) throws SQLException {
    return statement.getMoreResults(current);
  }

  public ResultSet getGeneratedKeys() throws SQLException {
    return new ResultSetProxy(statement.getGeneratedKeys());
  }

  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    final long time = System.currentTimeMillis();
    final int count = statement.executeUpdate(sql, autoGeneratedKeys);
    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
      buffer.append(sql);

      buffer.append("\n, " + autoGeneratedKeys + ") -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());
    }

    return count;
  }

  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    final long time = System.currentTimeMillis();
    final int count = statement.executeUpdate(sql, columnIndexes);
    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
      buffer.append(sql);

      buffer.append("\n, [" + Arrays.toString(columnIndexes) + "]) -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());
    }

    return count;
  }

  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    final long time = System.currentTimeMillis();
    final int count = statement.executeUpdate(sql, columnNames);
    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeUpdate(\n");
      buffer.append(sql);

      buffer.append("\n, " + Arrays.toString(columnNames) + ") -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());
    }

    return count;
  }

  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    final long time = System.currentTimeMillis();
    final boolean result = statement.execute(sql, autoGeneratedKeys);
    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
      buffer.append(sql);

      buffer.append("\n, " + autoGeneratedKeys + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());

    }

    return result;
  }

  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    final long time = System.currentTimeMillis();
    final boolean result = statement.execute(sql, columnIndexes);
    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
      buffer.append(sql);

      buffer.append("\n, " + Arrays.toString(columnIndexes) + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());
    }

    return result;
  }

  public boolean execute(String sql, String[] columnNames) throws SQLException {
    final long time = System.currentTimeMillis();
    final boolean result = statement.execute(sql, columnNames);
    if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].execute(\n");
      buffer.append(sql);

      buffer.append("\n, " + Arrays.toString(columnNames) + ") -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      Logger.getAnonymousLogger().fine(buffer.toString());
    }

    return result;
  }

  public int getResultSetHoldability() throws SQLException {
    return statement.getResultSetHoldability();
  }

  public boolean isClosed() throws SQLException {
    return statement.isClosed();
  }

  public void setPoolable(boolean poolable) throws SQLException {
    statement.setPoolable(poolable);
  }

  public boolean isPoolable() throws SQLException {
    return statement.isPoolable();
  }

  public <T extends Object> T unwrap(Class<T> iface) throws SQLException {
    return statement.unwrap(iface);
  }

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return statement.isWrapperFor(iface);
  }
}
