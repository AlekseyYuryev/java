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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreparedStatementProxy extends StatementProxy implements PreparedStatement {
  private static final Logger logger = Logger.getLogger(PreparedStatementProxy.class.getName());
  private static final Level defaultLogLevel = Level.FINE;

  private static final String NULL = "NULL";

  private final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd");
    }
  };

  private final ThreadLocal<DateFormat> timestampFormat = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }
  };

  private final ThreadLocal<NumberFormat> numberFormat = new ThreadLocal<NumberFormat>() {
    @Override
    protected NumberFormat initialValue() {
      return new DecimalFormat("###############.###########;-###############.###########");
    }
  };

  private final Map<Integer,Object> parameterMap = new HashMap<Integer,Object>();
  private final String sql;

  protected PreparedStatement getStatement() {
    return (PreparedStatement)statement;
  }

  public PreparedStatementProxy(final PreparedStatement statement, final String sql) {
    super(statement);
    this.sql = sql;
  }

  @Override
  public ResultSet executeQuery() throws SQLException {
    final PreparedStatement statement = getStatement();
    int size = -1;
    final ResultSetProxy resultSet;
    Level logLevel = defaultLogLevel;
    final long time = System.currentTimeMillis();
    try {
      resultSet = new ResultSetProxy(statement.executeQuery());
      if (logger.isLoggable(logLevel))
        size = resultSet.getSize();
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[").append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append("].executeQuery() {\n  ").append(toString());
        buffer.append("\n} -> ").append(size).append("\t\t").append(System.currentTimeMillis() - time).append("ms");
        logger.log(logLevel, buffer.toString());
      }
    }

    return resultSet;
  }

  @Override
  public int executeUpdate() throws SQLException {
    final long time = System.currentTimeMillis();
    int count = -1;
    Level logLevel = defaultLogLevel;
    try {
      count = getStatement().executeUpdate();
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[").append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append("].executeUpdate() {\n  ").append(toString());
        buffer.append("\n} -> ").append(count).append("\t\t").append((System.currentTimeMillis() - time)).append("ms");
        logger.log(logLevel, buffer.toString());
      }
    }

    return count;
  }

  @Override
  public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
    getStatement().setNull(parameterIndex, sqlType);
    parameterMap.put(parameterIndex, NULL);
  }

  @Override
  public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
    getStatement().setBoolean(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setByte(final int parameterIndex, final byte x) throws SQLException {
    getStatement().setByte(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setShort(final int parameterIndex, final short x) throws SQLException {
    getStatement().setShort(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setInt(final int parameterIndex, final int x) throws SQLException {
    getStatement().setInt(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setLong(final int parameterIndex, final long x) throws SQLException {
    getStatement().setLong(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setFloat(final int parameterIndex, final float x) throws SQLException {
    getStatement().setFloat(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setDouble(final int parameterIndex, final double x) throws SQLException {
    getStatement().setDouble(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
    getStatement().setBigDecimal(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setString(final int parameterIndex, final String x) throws SQLException {
    getStatement().setString(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
    getStatement().setBytes(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setDate(final int parameterIndex, final Date x) throws SQLException {
    getStatement().setDate(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setTime(final int parameterIndex, final Time x) throws SQLException {
    getStatement().setTime(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
    getStatement().setTimestamp(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x, length);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  @Deprecated
  public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
    getStatement().setUnicodeStream(parameterIndex, x, length);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x, length);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void clearParameters() throws SQLException {
    getStatement().clearParameters();
  }

  @Override
  public void setObject(final int parameterIndex, final Object x, int targetSqlType, final int scale) throws SQLException {
    getStatement().setObject(parameterIndex, x, targetSqlType, scale);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
    getStatement().setObject(parameterIndex, x, targetSqlType);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setObject(final int parameterIndex, final Object x) throws SQLException {
    getStatement().setObject(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public boolean execute() throws SQLException {
    final long time = System.currentTimeMillis();
    boolean result = false;
    Level logLevel = defaultLogLevel;
    try {
      result = getStatement().execute();
    }
    catch (final Throwable t) {
      logLevel = Level.SEVERE;
      throw t;
    }
    finally {
      if (logger.isLoggable(logLevel)) {
        final StringBuilder buffer = new StringBuilder("[").append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append("].execute() {\n  ").append(toString());
        buffer.append("} -> ").append(result).append("\t\t").append((System.currentTimeMillis() - time)).append("ms");
        logger.log(logLevel, buffer.toString());
      }
    }

    return result;
  }

  @Override
  public void addBatch() throws SQLException {
    logger.log(defaultLogLevel, toString());
    getStatement().addBatch();
  }

  @Override
  public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader, length);
  }

  @Override
  public void setRef(final int i, final Ref x) throws SQLException {
    getStatement().setRef(i, x);
  }

  @Override
  public void setBlob(final int i, final Blob x) throws SQLException {
    getStatement().setBlob(i, x);
  }

  @Override
  public void setClob(final int i, final Clob x) throws SQLException {
    getStatement().setClob(i, x);
  }

  @Override
  public void setArray(final int i, final Array x) throws SQLException {
    getStatement().setArray(i, x);
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    return getStatement().getMetaData();
  }

  @Override
  public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
    getStatement().setDate(parameterIndex, x, cal);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
    getStatement().setTime(parameterIndex, x, cal);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
    getStatement().setTimestamp(parameterIndex, x, cal);
  }

  @Override
  public void setNull(final int paramIndex, final int sqlType, final String typeName) throws SQLException {
    getStatement().setNull(paramIndex, sqlType, typeName);
  }

  @Override
  public void setURL(final int parameterIndex, final URL x) throws SQLException {
    getStatement().setURL(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  @Override
  public ParameterMetaData getParameterMetaData() throws SQLException {
    return getStatement().getParameterMetaData();
  }

  @Override
  public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
    getStatement().setRowId(parameterIndex, x);
  }

  @Override
  public void setNString(final int parameterIndex, final String value) throws SQLException {
    getStatement().setNString(parameterIndex, value);
  }

  @Override
  public void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException {
    getStatement().setNCharacterStream(parameterIndex, value, length);
  }

  @Override
  public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
    getStatement().setNClob(parameterIndex, value);
  }

  @Override
  public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
    getStatement().setClob(parameterIndex, reader, length);
  }

  @Override
  public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
    getStatement().setBlob(parameterIndex, inputStream, length);
  }

  @Override
  public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
    getStatement().setNClob(parameterIndex, reader, length);
  }

  @Override
  public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
    getStatement().setSQLXML(parameterIndex, xmlObject);
  }

  @Override
  public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x, length);
  }

  @Override
  public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x, length);
  }

  @Override
  public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader, length);
  }

  @Override
  public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x);
  }

  @Override
  public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x);
  }

  @Override
  public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader);
  }

  @Override
  public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
    getStatement().setNCharacterStream(parameterIndex, value);
  }

  @Override
  public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
    getStatement().setClob(parameterIndex, reader);
  }

  @Override
  public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
    getStatement().setBlob(parameterIndex, inputStream);
  }

  @Override
  public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
    getStatement().setNClob(parameterIndex, reader);
  }

  @Override
  public boolean isClosed() throws SQLException {
    return getStatement().isClosed();
  }

  @Override
  public void setPoolable(final boolean poolable) throws SQLException {
    getStatement().setPoolable(poolable);
  }

  @Override
  public boolean isPoolable() throws SQLException {
    return getStatement().isPoolable();
  }

  @Override
  public <T extends Object>T unwrap(final Class<T> iface) throws SQLException {
    return getStatement().unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return getStatement().isWrapperFor(iface);
  }

  @Override
  public String toString() {
    final StringTokenizer tokenizer = new StringTokenizer(sql, "?", true);
    final StringBuilder buffer = new StringBuilder();
    int index = 0;
    while (tokenizer.hasMoreTokens()) {
      final String token = tokenizer.nextToken();
      if ("?".equals(token)) {
        final Object value = parameterMap.get(++index);
        if (value == NULL)
          buffer.append("NULL");
        else if (value instanceof Date)
          buffer.append("'").append(dateFormat.get().format((Date)value)).append("'");
        else if (value instanceof Timestamp)
          buffer.append("'").append(timestampFormat.get().format((Timestamp)value)).append("'");
        else if (value instanceof String || value instanceof Byte)
          buffer.append("'").append(value).append("'");
        else if (value instanceof Short || value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof BigInteger)
          buffer.append(numberFormat.get().format(value));
        else if (value != null)
          buffer.append(value);
        else
          buffer.append("?");
      }
      else {
        buffer.append(token);
      }
    }

    final String display = buffer.toString();
    // String display = null;
    // try {
    // display = SQLFormat.format(buffer.toString());
    // }
    // catch (final ParseException e) {
    // throw new RuntimeException(buffer.toString(), e);
    // }

    return display;
  }
}