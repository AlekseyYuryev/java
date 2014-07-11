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
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public final class CallableStatementProxy extends PreparedStatementProxy implements CallableStatement {
  public CallableStatementProxy(final CallableStatement callableStatement, final String sql) {
    super(callableStatement, sql);
  }

  protected final CallableStatement getStatement() {
    return (CallableStatement)super.getStatement();
  }

  public void registerOutParameter(final int parameterIndex, final int sqlType) throws SQLException {
    getStatement().registerOutParameter(parameterIndex, sqlType);
  }

  public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale) throws SQLException {
    getStatement().registerOutParameter(parameterIndex, sqlType, scale);
  }

  public boolean wasNull() throws SQLException {
    return getStatement().wasNull();
  }

  public String getString(final int parameterIndex) throws SQLException {
    return getStatement().getString(parameterIndex);
  }

  public boolean getBoolean(final int parameterIndex) throws SQLException {
    return getStatement().getBoolean(parameterIndex);
  }

  public byte getByte(final int parameterIndex) throws SQLException {
    return getStatement().getByte(parameterIndex);
  }

  public short getShort(final int parameterIndex) throws SQLException {
    return getStatement().getShort(parameterIndex);
  }

  public int getInt(final int parameterIndex) throws SQLException {
    return getStatement().getInt(parameterIndex);
  }

  public long getLong(final int parameterIndex) throws SQLException {
    return getStatement().getLong(parameterIndex);
  }

  public float getFloat(final int parameterIndex) throws SQLException {
    return getStatement().getFloat(parameterIndex);
  }

  public double getDouble(final int parameterIndex) throws SQLException {
    return getStatement().getDouble(parameterIndex);
  }

  public BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException {
    return getStatement().getBigDecimal(parameterIndex);
  }

  public byte[] getBytes(final int parameterIndex) throws SQLException {
    return getStatement().getBytes(parameterIndex);
  }

  public Date getDate(final int parameterIndex) throws SQLException {
    return getStatement().getDate(parameterIndex);
  }

  public Time getTime(final int parameterIndex) throws SQLException {
    return getStatement().getTime(parameterIndex);
  }

  public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
    return getStatement().getTimestamp(parameterIndex);
  }

  public Object getObject(final int parameterIndex) throws SQLException {
    return getStatement().getObject(parameterIndex);
  }

  public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
    return getStatement().getBigDecimal(parameterIndex);
  }

  public Object getObject(final int i, final Map<String,Class<?>> map) throws SQLException {
    return getStatement().getObject(i, map);
  }

  public Ref getRef(final int i) throws SQLException {
    return getStatement().getRef(i);
  }

  public Blob getBlob(final int i) throws SQLException {
    return getStatement().getBlob(i);
  }

  public Clob getClob(final int i) throws SQLException {
    return getStatement().getClob(i);
  }

  public Array getArray(final int i) throws SQLException {
    return getStatement().getArray(i);
  }

  public Date getDate(final int parameterIndex, final Calendar cal) throws SQLException {
    return getStatement().getDate(parameterIndex, cal);
  }

  public Time getTime(final int parameterIndex, final Calendar cal) throws SQLException {
    return getStatement().getTime(parameterIndex, cal);
  }

  public Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException {
    return getStatement().getTimestamp(parameterIndex, cal);
  }

  public void registerOutParameter(final int paramIndex, final int sqlType, final String typeName) throws SQLException {
    getStatement().registerOutParameter(paramIndex, sqlType, typeName);
  }

  public void registerOutParameter(final String parameterName, final int sqlType) throws SQLException {
    getStatement().registerOutParameter(parameterName, sqlType);
  }

  public void registerOutParameter(final String parameterName, final int sqlType, final int scale) throws SQLException {
    getStatement().registerOutParameter(parameterName, sqlType, scale);
  }

  public void registerOutParameter(final String parameterName, final int sqlType, final String typeName) throws SQLException {
    getStatement().registerOutParameter(parameterName, sqlType, typeName);
  }

  public URL getURL(final int parameterIndex) throws SQLException {
    return getStatement().getURL(parameterIndex);
  }

  public void setURL(final String parameterName, final URL val) throws SQLException {
    getStatement().setURL(parameterName, val);
  }

  public void setNull(final String parameterName, final int sqlType) throws SQLException {
    getStatement().setNull(parameterName, sqlType);
  }

  public void setBoolean(final String parameterName, final boolean x) throws SQLException {
    getStatement().setBoolean(parameterName, x);
  }

  public void setByte(final String parameterName, final byte x) throws SQLException {
    getStatement().setByte(parameterName, x);
  }

  public void setShort(final String parameterName, final short x) throws SQLException {
    getStatement().setShort(parameterName, x);
  }

  public void setInt(final String parameterName, final int x) throws SQLException {
    getStatement().setInt(parameterName, x);
  }

  public void setLong(final String parameterName, final long x) throws SQLException {
    getStatement().setLong(parameterName, x);
  }

  public void setFloat(final String parameterName, final float x) throws SQLException {
    getStatement().setFloat(parameterName, x);
  }

  public void setDouble(final String parameterName, final double x) throws SQLException {
    getStatement().setDouble(parameterName, x);
  }

  public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
    getStatement().setBigDecimal(parameterName, x);
  }

  public void setString(final String parameterName, final String x) throws SQLException {
    getStatement().setString(parameterName, x);
  }

  public void setBytes(final String parameterName, final byte[] x) throws SQLException {
    getStatement().setBytes(parameterName, x);
  }

  public void setDate(final String parameterName, final Date x) throws SQLException {
    getStatement().setDate(parameterName, x);
  }

  public void setTime(final String parameterName, final Time x) throws SQLException {
    getStatement().setTime(parameterName, x);
  }

  public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
    getStatement().setTimestamp(parameterName, x);
  }

  public void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException {
    getStatement().setAsciiStream(parameterName, x, length);
  }

  public void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException {
    getStatement().setBinaryStream(parameterName, x, length);
  }

  public void setObject(final String parameterName, final Object x, int targetSqlType, final int scale) throws SQLException {
    getStatement().setObject(parameterName, x, targetSqlType, scale);
  }

  public void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException {
    getStatement().setObject(parameterName, x, targetSqlType);
  }

  public void setObject(final String parameterName, final Object x) throws SQLException {
    getStatement().setObject(parameterName, x);
  }

  public void setCharacterStream(final String parameterName, final Reader reader, final int length) throws SQLException {
    getStatement().setCharacterStream(parameterName, reader, length);
  }

  public void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException {
    getStatement().setDate(parameterName, x, cal);
  }

  public void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException {
    getStatement().setTime(parameterName, x, cal);
  }

  public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException {
    getStatement().setTimestamp(parameterName, x, cal);
  }

  public void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException {
    getStatement().setNull(parameterName, sqlType, typeName);
  }

  public String getString(final String parameterName) throws SQLException {
    return getStatement().getString(parameterName);
  }

  public boolean getBoolean(final String parameterName) throws SQLException {
    return getStatement().getBoolean(parameterName);
  }

  public byte getByte(final String parameterName) throws SQLException {
    return getStatement().getByte(parameterName);
  }

  public short getShort(final String parameterName) throws SQLException {
    return getStatement().getShort(parameterName);
  }

  public int getInt(final String parameterName) throws SQLException {
    return getStatement().getInt(parameterName);
  }

  public long getLong(final String parameterName) throws SQLException {
    return getStatement().getLong(parameterName);
  }

  public float getFloat(final String parameterName) throws SQLException {
    return getStatement().getFloat(parameterName);
  }

  public double getDouble(final String parameterName) throws SQLException {
    return getStatement().getDouble(parameterName);
  }

  public byte[] getBytes(final String parameterName) throws SQLException {
    return getStatement().getBytes(parameterName);
  }

  public Date getDate(final String parameterName) throws SQLException {
    return getStatement().getDate(parameterName);
  }

  public Time getTime(final String parameterName) throws SQLException {
    return getStatement().getTime(parameterName);
  }

  public Timestamp getTimestamp(final String parameterName) throws SQLException {
    return getStatement().getTimestamp(parameterName);
  }

  public Object getObject(final String parameterName) throws SQLException {
    return getStatement().getObject(parameterName);
  }

  public BigDecimal getBigDecimal(final String parameterName) throws SQLException {
    return getStatement().getBigDecimal(parameterName);
  }

  public Object getObject(final String parameterName, final Map<String,Class<?>> map) throws SQLException {
    return getStatement().getObject(parameterName, map);
  }

  public Ref getRef(final String parameterName) throws SQLException {
    return getStatement().getRef(parameterName);
  }

  public Blob getBlob(final String parameterName) throws SQLException {
    return getStatement().getBlob(parameterName);
  }

  public Clob getClob(final String parameterName) throws SQLException {
    return getStatement().getClob(parameterName);
  }

  public Array getArray(final String parameterName) throws SQLException {
    return getStatement().getArray(parameterName);
  }

  public Date getDate(final String parameterName, final Calendar cal) throws SQLException {
    return getStatement().getDate(parameterName, cal);
  }

  public Time getTime(final String parameterName, final Calendar cal) throws SQLException {
    return getStatement().getTime(parameterName, cal);
  }

  public Timestamp getTimestamp(final String parameterName, final Calendar cal) throws SQLException {
    return getStatement().getTimestamp(parameterName, cal);
  }

  public URL getURL(final String parameterName) throws SQLException {
    return getStatement().getURL(parameterName);
  }

  public RowId getRowId(final int parameterIndex) throws SQLException {
    return getStatement().getRowId(parameterIndex);
  }

  public RowId getRowId(final String parameterName) throws SQLException {
    return getStatement().getRowId(parameterName);
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

  public <T extends Object> T unwrap(final Class<T> iface) throws SQLException {
    return getStatement().unwrap(iface);
  }

  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return getStatement().isWrapperFor(iface);
  }

  public void setRowId(final String parameterName, final RowId x) throws SQLException {
    getStatement().setRowId(parameterName, x);
  }

  public void setNString(final String parameterName, final String value) throws SQLException {
    getStatement().setNString(parameterName, value);
  }

  public void setNCharacterStream(final String parameterName, final Reader value, final long length) throws SQLException {
    getStatement().setNCharacterStream(parameterName, value, length);
  }

  public void setNClob(final String parameterName, final NClob value) throws SQLException {
    getStatement().setNClob(parameterName, value);
  }

  public void setClob(final String parameterName, final Reader reader, final long length) throws SQLException {
    getStatement().setClob(parameterName, reader);
  }

  public void setBlob(final String parameterName, final InputStream inputStream, final long length) throws SQLException {
    getStatement().setBlob(parameterName, inputStream);
  }

  public void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException {
    getStatement().setNClob(parameterName, reader, length);
  }

  public NClob getNClob(final int parameterIndex) throws SQLException {
    return getStatement().getNClob(parameterIndex);
  }

  public NClob getNClob(final String parameterName) throws SQLException {
    return getStatement().getNClob(parameterName);
  }

  public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
    getStatement().setSQLXML(parameterName, xmlObject);
  }

  public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
    return getStatement().getSQLXML(parameterIndex);
  }

  public SQLXML getSQLXML(final String parameterName) throws SQLException {
    return getStatement().getSQLXML(parameterName);
  }

  public String getNString(final int parameterIndex) throws SQLException {
    return getStatement().getNString(parameterIndex);
  }

  public String getNString(final String parameterName) throws SQLException {
    return getStatement().getNString(parameterName);
  }

  public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
    return getStatement().getNCharacterStream(parameterIndex);
  }

  public Reader getNCharacterStream(final String parameterName) throws SQLException {
    return getStatement().getNCharacterStream(parameterName);
  }

  public Reader getCharacterStream(final int parameterIndex) throws SQLException {
    return getStatement().getCharacterStream(parameterIndex);
  }

  public Reader getCharacterStream(final String parameterName) throws SQLException {
    return getStatement().getCharacterStream(parameterName);
  }

  public void setBlob(final String parameterName, final Blob x) throws SQLException {
    getStatement().setBlob(parameterName, x);
  }

  public void setClob(final String parameterName, final Clob x) throws SQLException {
    getStatement().setClob(parameterName, x);
  }

  public void setAsciiStream(final String parameterName, final InputStream x, final long length) throws SQLException {
    getStatement().setAsciiStream(parameterName, x, length);
  }

  public void setBinaryStream(final String parameterName, final InputStream x, final long length) throws SQLException {
    getStatement().setBinaryStream(parameterName, x, length);
  }

  public void setCharacterStream(final String parameterName, final Reader reader, final long length) throws SQLException {
    getStatement().setCharacterStream(parameterName, reader, length);
  }

  public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
    getStatement().setAsciiStream(parameterName, x);
  }

  public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
    getStatement().setBinaryStream(parameterName, x);
  }

  public void setCharacterStream(final String parameterName, final Reader reader) throws SQLException {
    getStatement().setCharacterStream(parameterName, reader);
  }

  public void setNCharacterStream(final String parameterName, final Reader value) throws SQLException {
    getStatement().setNCharacterStream(parameterName, value);
  }

  public void setClob(final String parameterName, final Reader reader) throws SQLException {
    getStatement().setClob(parameterName, reader);
  }

  public void setBlob(final String parameterName, final InputStream inputStream) throws SQLException {
    getStatement().setBlob(parameterName, inputStream);
  }

  public void setNClob(final String parameterName, final Reader reader) throws SQLException {
    getStatement().setNClob(parameterName, reader);
  }

  public <T>T getObject(final int parameterIndex, final Class<T> type) throws SQLException {
    return getStatement().getObject(parameterIndex, type);
  }

  public <T>T getObject(final String parameterName, final Class<T> type) throws SQLException {
    return getStatement().getObject(parameterName, type);
  }
}