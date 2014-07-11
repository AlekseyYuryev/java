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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
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

import org.safris.commons.logging.Logger;

public class PreparedStatementProxy extends StatementProxy implements PreparedStatement {
  private static final Logger logger = Logger.getLogger(PreparedStatementProxy.class.getName());

  private final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
    protected DateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
  };
  
  private final ThreadLocal<NumberFormat> numberFormat = new ThreadLocal<NumberFormat>() {
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

  public ResultSet executeQuery() throws SQLException {
    final PreparedStatement statement = getStatement();
    final long time = System.currentTimeMillis();
    final ResultSet resultSetOriginal = statement.executeQuery();
    final ResultSetProxy resultSet = new ResultSetProxy(resultSetOriginal);

    if (logger.getLevel() == Level.FINE) {
      final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeQuery() {\n");
      buffer.append(toString());
      buffer.append("\n} -> " + resultSet.getSize() + "\t\t" + (System.currentTimeMillis() - time) + "ms");
      logger.fine(buffer.toString());
    }

    return resultSet;
  }

  public int executeUpdate() throws SQLException {
    final StringBuilder buffer = new StringBuilder("[" + getStatement().toString() + "].executeUpdate() {\n");
    buffer.append("  " + toString());

    final long time = System.currentTimeMillis();
    final int count = getStatement().executeUpdate();
    buffer.append("\n} -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
    logger.fine(buffer.toString());
    return count;
  }

  public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
    getStatement().setNull(parameterIndex, sqlType);
    parameterMap.put(parameterIndex, "NULL");
  }

  public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
    getStatement().setBoolean(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setByte(final int parameterIndex, final byte x) throws SQLException {
    getStatement().setByte(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setShort(final int parameterIndex, final short x) throws SQLException {
    getStatement().setShort(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setInt(final int parameterIndex, final int x) throws SQLException {
    getStatement().setInt(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setLong(final int parameterIndex, final long x) throws SQLException {
    getStatement().setLong(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setFloat(final int parameterIndex, final float x) throws SQLException {
    getStatement().setFloat(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setDouble(final int parameterIndex, final double x) throws SQLException {
    getStatement().setDouble(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
    getStatement().setBigDecimal(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setString(final int parameterIndex, final String x) throws SQLException {
    getStatement().setString(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
    getStatement().setBytes(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setDate(final int parameterIndex, final Date x) throws SQLException {
    getStatement().setDate(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setTime(final int parameterIndex, final Time x) throws SQLException {
    getStatement().setTime(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
    getStatement().setTimestamp(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x, length);
    parameterMap.put(parameterIndex, x);
  }

  /**
   * @deprecated
   */
  public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
    getStatement().setUnicodeStream(parameterIndex, x, length);
    parameterMap.put(parameterIndex, x);
  }

  public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x, length);
    parameterMap.put(parameterIndex, x);
  }

  public void clearParameters() throws SQLException {
    getStatement().clearParameters();
  }

  public void setObject(final int parameterIndex, final Object x, int targetSqlType, final int scale) throws SQLException {
    getStatement().setObject(parameterIndex, x, targetSqlType, scale);
    parameterMap.put(parameterIndex, x);
  }

  public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
    getStatement().setObject(parameterIndex, x, targetSqlType);
    parameterMap.put(parameterIndex, x);
  }

  public void setObject(final int parameterIndex, final Object x) throws SQLException {
    getStatement().setObject(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public boolean execute() throws SQLException {
    final long time = System.currentTimeMillis();
    if (logger.getLevel() == Level.FINE)
      logger.fine("[" + getStatement().toString() + "].execute() {\n" + toString());

    final boolean result = getStatement().execute();
    if (logger.getLevel() == Level.FINE)
      logger.fine("} -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");

    return result;
  }

  public void addBatch() throws SQLException {
    logger.fine(toString());
    getStatement().addBatch();
  }

  public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader, length);
  }

  public void setRef(final int i, final Ref x) throws SQLException {
    getStatement().setRef(i, x);
  }

  public void setBlob(final int i, final Blob x) throws SQLException {
    getStatement().setBlob(i, x);
  }

  public void setClob(final int i, final Clob x) throws SQLException {
    getStatement().setClob(i, x);
  }

  public void setArray(final int i, final Array x) throws SQLException {
    getStatement().setArray(i, x);
  }

  public ResultSetMetaData getMetaData() throws SQLException {
    return getStatement().getMetaData();
  }

  public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
    getStatement().setDate(parameterIndex, x, cal);
    parameterMap.put(parameterIndex, x);
  }

  public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
    getStatement().setTime(parameterIndex, x, cal);
    parameterMap.put(parameterIndex, x);
  }

  public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
    getStatement().setTimestamp(parameterIndex, x, cal);
  }

  public void setNull(final int paramIndex, final int sqlType, final String typeName) throws SQLException {
    getStatement().setNull(paramIndex, sqlType, typeName);
  }

  public void setURL(final int parameterIndex, final URL x) throws SQLException {
    getStatement().setURL(parameterIndex, x);
    parameterMap.put(parameterIndex, x);
  }

  public ParameterMetaData getParameterMetaData() throws SQLException {
    return getStatement().getParameterMetaData();
  }

  public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
    getStatement().setRowId(parameterIndex, x);
  }

  public void setNString(final int parameterIndex, final String value) throws SQLException {
    getStatement().setNString(parameterIndex, value);
  }

  public void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException {
    getStatement().setNCharacterStream(parameterIndex, value, length);
  }

  public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
    getStatement().setNClob(parameterIndex, value);
  }

  public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
    getStatement().setClob(parameterIndex, reader, length);
  }

  public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
    getStatement().setBlob(parameterIndex, inputStream, length);
  }

  public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
    getStatement().setNClob(parameterIndex, reader, length);
  }

  public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
    getStatement().setSQLXML(parameterIndex, xmlObject);
  }

  public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x, length);
  }

  public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x, length);
  }

  public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader, length);
  }

  public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x);
  }

  public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x);
  }

  public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader);
  }

  public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
    getStatement().setNCharacterStream(parameterIndex, value);
  }

  public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
    getStatement().setClob(parameterIndex, reader);
  }

  public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
    getStatement().setBlob(parameterIndex, inputStream);
  }

  public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
    getStatement().setNClob(parameterIndex, reader);
  }

  public boolean isClosed() throws SQLException {
    return getStatement().isClosed();
  }

  public void setPoolable(final boolean poolable) throws SQLException {
    getStatement().setPoolable(poolable);
  }

  public boolean isPoolable() throws SQLException {
    return getStatement().isPoolable();
  }

  public <T extends Object>T unwrap(final Class<T> iface) throws SQLException {
    return getStatement().unwrap(iface);
  }

  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return getStatement().isWrapperFor(iface);
  }

  public String toString() {
    final StringTokenizer tokenizer = new StringTokenizer(sql, "?");
    final StringBuilder buffer = new StringBuilder();
    int index = 0;
    while (tokenizer.hasMoreTokens()) {
      buffer.append(tokenizer.nextToken());
      final Object value = parameterMap.get(++index);
      if (value instanceof Date)
        buffer.append("'" + dateFormat.get().format((Date)value) + "'");
      else if (value instanceof String || value instanceof Byte)
        buffer.append("'" + value + "'");
      else if (value instanceof Short || value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof BigInteger)
        buffer.append(numberFormat.get().format(value));
      else if (value != null)
        buffer.append(value);
      else
        buffer.append("?");
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