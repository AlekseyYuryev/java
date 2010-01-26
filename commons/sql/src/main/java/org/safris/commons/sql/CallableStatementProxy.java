/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

public class CallableStatementProxy extends PreparedStatementProxy implements CallableStatement {
    public CallableStatementProxy(CallableStatement callableStatement, String sql) {
        super(callableStatement, sql);
    }

    protected final CallableStatement getStatement() {
        return (CallableStatement)super.getStatement();
    }

    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        getStatement().registerOutParameter(parameterIndex, sqlType);
    }

    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        getStatement().registerOutParameter(parameterIndex, sqlType, scale);
    }

    public boolean wasNull() throws SQLException {
        return getStatement().wasNull();
    }

    public String getString(int parameterIndex) throws SQLException {
        return getStatement().getString(parameterIndex);
    }

    public boolean getBoolean(int parameterIndex) throws SQLException {
        return getStatement().getBoolean(parameterIndex);
    }

    public byte getByte(int parameterIndex) throws SQLException {
        return getStatement().getByte(parameterIndex);
    }

    public short getShort(int parameterIndex) throws SQLException {
        return getStatement().getShort(parameterIndex);
    }

    public int getInt(int parameterIndex) throws SQLException {
        return getStatement().getInt(parameterIndex);
    }

    public long getLong(int parameterIndex) throws SQLException {
        return getStatement().getLong(parameterIndex);
    }

    public float getFloat(int parameterIndex) throws SQLException {
        return getStatement().getFloat(parameterIndex);
    }

    public double getDouble(int parameterIndex) throws SQLException {
        return getStatement().getDouble(parameterIndex);
    }

    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return getStatement().getBigDecimal(parameterIndex);
    }

    public byte[] getBytes(int parameterIndex) throws SQLException {
        return getStatement().getBytes(parameterIndex);
    }

    public Date getDate(int parameterIndex) throws SQLException {
        return getStatement().getDate(parameterIndex);
    }

    public Time getTime(int parameterIndex) throws SQLException {
        return getStatement().getTime(parameterIndex);
    }

    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return getStatement().getTimestamp(parameterIndex);
    }

    public Object getObject(int parameterIndex) throws SQLException {
        return getStatement().getObject(parameterIndex);
    }

    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return getStatement().getBigDecimal(parameterIndex);
    }

    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        return getStatement().getObject(i, map);
    }

    public Ref getRef(int i) throws SQLException {
        return getStatement().getRef(i);
    }

    public Blob getBlob(int i) throws SQLException {
        return getStatement().getBlob(i);
    }

    public Clob getClob(int i) throws SQLException {
        return getStatement().getClob(i);
    }

    public Array getArray(int i) throws SQLException {
        return getStatement().getArray(i);
    }

    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        return getStatement().getDate(parameterIndex, cal);
    }

    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        return getStatement().getTime(parameterIndex, cal);
    }

    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        return getStatement().getTimestamp(parameterIndex, cal);
    }

    public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException {
        getStatement().registerOutParameter(paramIndex, sqlType, typeName);
    }

    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        getStatement().registerOutParameter(parameterName, sqlType);
    }

    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        getStatement().registerOutParameter(parameterName, sqlType, scale);
    }

    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        getStatement().registerOutParameter(parameterName, sqlType, typeName);
    }

    public URL getURL(int parameterIndex) throws SQLException {
        return getStatement().getURL(parameterIndex);
    }

    public void setURL(String parameterName, URL val) throws SQLException {
        getStatement().setURL(parameterName, val);
    }

    public void setNull(String parameterName, int sqlType) throws SQLException {
        getStatement().setNull(parameterName, sqlType);
    }

    public void setBoolean(String parameterName, boolean x) throws SQLException {
        getStatement().setBoolean(parameterName, x);
    }

    public void setByte(String parameterName, byte x) throws SQLException {
        getStatement().setByte(parameterName, x);
    }

    public void setShort(String parameterName, short x) throws SQLException {
        getStatement().setShort(parameterName, x);
    }

    public void setInt(String parameterName, int x) throws SQLException {
        getStatement().setInt(parameterName, x);
    }

    public void setLong(String parameterName, long x) throws SQLException {
        getStatement().setLong(parameterName, x);
    }

    public void setFloat(String parameterName, float x) throws SQLException {
        getStatement().setFloat(parameterName, x);
    }

    public void setDouble(String parameterName, double x) throws SQLException {
        getStatement().setDouble(parameterName, x);
    }

    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        getStatement().setBigDecimal(parameterName, x);
    }

    public void setString(String parameterName, String x) throws SQLException {
        getStatement().setString(parameterName, x);
    }

    public void setBytes(String parameterName, byte[] x) throws SQLException {
        getStatement().setBytes(parameterName, x);
    }

    public void setDate(String parameterName, Date x) throws SQLException {
        getStatement().setDate(parameterName, x);
    }

    public void setTime(String parameterName, Time x) throws SQLException {
        getStatement().setTime(parameterName, x);
    }

    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        getStatement().setTimestamp(parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        getStatement().setAsciiStream(parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        getStatement().setBinaryStream(parameterName, x, length);
    }

    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        getStatement().setObject(parameterName, x, targetSqlType, scale);
    }

    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        getStatement().setObject(parameterName, x, targetSqlType);
    }

    public void setObject(String parameterName, Object x) throws SQLException {
        getStatement().setObject(parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        getStatement().setCharacterStream(parameterName, reader, length);
    }

    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        getStatement().setDate(parameterName, x, cal);
    }

    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        getStatement().setTime(parameterName, x, cal);
    }

    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        getStatement().setTimestamp(parameterName, x, cal);
    }

    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        getStatement().setNull(parameterName, sqlType, typeName);
    }

    public String getString(String parameterName) throws SQLException {
        return getStatement().getString(parameterName);
    }

    public boolean getBoolean(String parameterName) throws SQLException {
        return getStatement().getBoolean(parameterName);
    }

    public byte getByte(String parameterName) throws SQLException {
        return getStatement().getByte(parameterName);
    }

    public short getShort(String parameterName) throws SQLException {
        return getStatement().getShort(parameterName);
    }

    public int getInt(String parameterName) throws SQLException {
        return getStatement().getInt(parameterName);
    }

    public long getLong(String parameterName) throws SQLException {
        return getStatement().getLong(parameterName);
    }

    public float getFloat(String parameterName) throws SQLException {
        return getStatement().getFloat(parameterName);
    }

    public double getDouble(String parameterName) throws SQLException {
        return getStatement().getDouble(parameterName);
    }

    public byte[] getBytes(String parameterName) throws SQLException {
        return getStatement().getBytes(parameterName);
    }

    public Date getDate(String parameterName) throws SQLException {
        return getStatement().getDate(parameterName);
    }

    public Time getTime(String parameterName) throws SQLException {
        return getStatement().getTime(parameterName);
    }

    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return getStatement().getTimestamp(parameterName);
    }

    public Object getObject(String parameterName) throws SQLException {
        return getStatement().getObject(parameterName);
    }

    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return getStatement().getBigDecimal(parameterName);
    }

    public Object getObject(String parameterName, Map<String,Class<?>> map) throws SQLException {
        return getStatement().getObject(parameterName, map);
    }

    public Ref getRef(String parameterName) throws SQLException {
        return getStatement().getRef(parameterName);
    }

    public Blob getBlob(String parameterName) throws SQLException {
        return getStatement().getBlob(parameterName);
    }

    public Clob getClob(String parameterName) throws SQLException {
        return getStatement().getClob(parameterName);
    }

    public Array getArray(String parameterName) throws SQLException {
        return getStatement().getArray(parameterName);
    }

    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return getStatement().getDate(parameterName, cal);
    }

    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return getStatement().getTime(parameterName, cal);
    }

    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return getStatement().getTimestamp(parameterName, cal);
    }

    public URL getURL(String parameterName) throws SQLException {
        return getStatement().getURL(parameterName);
    }

    public RowId getRowId(int parameterIndex) throws SQLException {
        return getStatement().getRowId(parameterIndex);
    }

    public RowId getRowId(String parameterName) throws SQLException {
        return getStatement().getRowId(parameterName);
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        getStatement().setRowId(parameterIndex, x);
    }

    public void setNString(int parameterIndex, String value) throws SQLException {
        getStatement().setNString(parameterIndex, value);
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        getStatement().setNCharacterStream(parameterIndex, value, length);
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        getStatement().setNClob(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getStatement().setClob(parameterIndex, reader, length);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        getStatement().setBlob(parameterIndex, inputStream, length);
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getStatement().setNClob(parameterIndex, reader, length);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        getStatement().setSQLXML(parameterIndex, xmlObject);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        getStatement().setAsciiStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        getStatement().setBinaryStream(parameterIndex, x, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        getStatement().setCharacterStream(parameterIndex, reader, length);
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        getStatement().setAsciiStream(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        getStatement().setBinaryStream(parameterIndex, x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        getStatement().setCharacterStream(parameterIndex, reader);
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        getStatement().setNCharacterStream(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        getStatement().setClob(parameterIndex, reader);
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        getStatement().setBlob(parameterIndex, inputStream);
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        getStatement().setNClob(parameterIndex, reader);
    }

    public boolean isClosed() throws SQLException {
        return getStatement().isClosed();
    }

    public void setPoolable(boolean poolable) throws SQLException {
        getStatement().setPoolable(poolable);
    }

    public boolean isPoolable() throws SQLException {
        return getStatement().isPoolable();
    }

    public <T extends Object> T unwrap(Class<T> iface) throws SQLException {
        return getStatement().unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getStatement().isWrapperFor(iface);
    }

    public void setRowId(String parameterName, RowId x) throws SQLException {
        getStatement().setRowId(parameterName, x);
    }

    public void setNString(String parameterName, String value) throws SQLException {
        getStatement().setNString(parameterName, value);
    }

    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        getStatement().setNCharacterStream(parameterName, value, length);
    }

    public void setNClob(String parameterName, NClob value) throws SQLException {
        getStatement().setNClob(parameterName, value);
    }

    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        getStatement().setClob(parameterName, reader);
    }

    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        getStatement().setBlob(parameterName, inputStream);
    }

    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        getStatement().setNClob(parameterName, reader, length);
    }

    public NClob getNClob(int parameterIndex) throws SQLException {
        return getStatement().getNClob(parameterIndex);
    }

    public NClob getNClob(String parameterName) throws SQLException {
        return getStatement().getNClob(parameterName);
    }

    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        getStatement().setSQLXML(parameterName, xmlObject);
    }

    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return getStatement().getSQLXML(parameterIndex);
    }

    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return getStatement().getSQLXML(parameterName);
    }

    public String getNString(int parameterIndex) throws SQLException {
        return getStatement().getNString(parameterIndex);
    }

    public String getNString(String parameterName) throws SQLException {
        return getStatement().getNString(parameterName);
    }

    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return getStatement().getNCharacterStream(parameterIndex);
    }

    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return getStatement().getNCharacterStream(parameterName);
    }

    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return getStatement().getCharacterStream(parameterIndex);
    }

    public Reader getCharacterStream(String parameterName) throws SQLException {
        return getStatement().getCharacterStream(parameterName);
    }

    public void setBlob(String parameterName, Blob x) throws SQLException {
        getStatement().setBlob(parameterName, x);
    }

    public void setClob(String parameterName, Clob x) throws SQLException {
        getStatement().setClob(parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        getStatement().setAsciiStream(parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        getStatement().setBinaryStream(parameterName, x, length);
    }

    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        getStatement().setCharacterStream(parameterName, reader, length);
    }

    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        getStatement().setAsciiStream(parameterName, x);
    }

    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        getStatement().setBinaryStream(parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        getStatement().setCharacterStream(parameterName, reader);
    }

    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        getStatement().setNCharacterStream(parameterName, value);
    }

    public void setClob(String parameterName, Reader reader) throws SQLException {
        getStatement().setClob(parameterName, reader);
    }

    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        getStatement().setBlob(parameterName, inputStream);
    }

    public void setNClob(String parameterName, Reader reader) throws SQLException {
        getStatement().setNClob(parameterName, reader);
    }
}
