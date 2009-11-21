/*  Copyright 2008 Safris Technologies Inc.
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
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class ResultSetProxy implements ResultSet {
    private final ResultSet resultSet;
	private volatile boolean mutex = false;

    public ResultSetProxy(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

	private void checkMutex() {
		if(!mutex)
			return;

		try {
			resultSet.wait();
		}
		catch(InterruptedException e) {
		}
	}

    protected Integer getSize() throws SQLException {
        if (resultSet == null || resultSet.getType() <= ResultSet.TYPE_FORWARD_ONLY)
            return null;

		mutex = true;
        resultSet.last();
        final int size = resultSet.getRow();
        resultSet.beforeFirst();
		mutex = false;
		resultSet.notifyAll();
        return size;
    }

    public boolean next() throws SQLException {
		checkMutex();
        return resultSet.next();
    }

    public void close() throws SQLException {
        resultSet.close();
    }

    public boolean wasNull() throws SQLException {
		checkMutex();
        return resultSet.wasNull();
    }

    public String getString(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getString(columnIndex);
    }

    public boolean getBoolean(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getBoolean(columnIndex);
    }

    public byte getByte(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getByte(columnIndex);
    }

    public short getShort(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getShort(columnIndex);
    }

    public int getInt(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getInt(columnIndex);
    }

    public long getLong(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getLong(columnIndex);
    }

    public float getFloat(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getFloat(columnIndex);
    }

    public double getDouble(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getDouble(columnIndex);
    }

    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		checkMutex();
        return resultSet.getBigDecimal(columnIndex, scale);
    }

    public byte[] getBytes(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getBytes(columnIndex);
    }

    public Date getDate(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getDate(columnIndex);
    }

    public Time getTime(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getTime(columnIndex);
    }

    public Timestamp getTimestamp(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getTimestamp(columnIndex);
    }

    public InputStream getAsciiStream(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getAsciiStream(columnIndex);
    }

    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getUnicodeStream(columnIndex);
    }

    public InputStream getBinaryStream(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getBinaryStream(columnIndex);
    }

    public String getString(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getString(columnName);
    }

    public boolean getBoolean(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getBoolean(columnName);
    }

    public byte getByte(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getByte(columnName);
    }

    public short getShort(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getShort(columnName);
    }

    public int getInt(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getInt(columnName);
    }

    public long getLong(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getLong(columnName);
    }

    public float getFloat(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getFloat(columnName);
    }

    public double getDouble(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getDouble(columnName);
    }

    public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
		checkMutex();
        return resultSet.getBigDecimal(columnName, scale);
    }

    public byte[] getBytes(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getBytes(columnName);
    }

    public Date getDate(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getDate(columnName);
    }

    public Time getTime(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getTime(columnName);
    }

    public Timestamp getTimestamp(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getTimestamp(columnName);
    }

    public InputStream getAsciiStream(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getAsciiStream(columnName);
    }

    public InputStream getUnicodeStream(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getUnicodeStream(columnName);
    }

    public InputStream getBinaryStream(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getBinaryStream(columnName);
    }

    public SQLWarning getWarnings() throws SQLException {
        return resultSet.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        resultSet.clearWarnings();
    }

    public String getCursorName() throws SQLException {
        return resultSet.getCursorName();
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return resultSet.getMetaData();
    }

    public Object getObject(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getObject(columnIndex);
    }

    public Object getObject(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getObject(columnName);
    }

    public int findColumn(String columnName) throws SQLException {
        return resultSet.findColumn(columnName);
    }

    public Reader getCharacterStream(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getCharacterStream(columnIndex);
    }

    public Reader getCharacterStream(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getCharacterStream(columnName);
    }

    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getBigDecimal(columnIndex);
    }

    public BigDecimal getBigDecimal(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getBigDecimal(columnName);
    }

    public boolean isBeforeFirst() throws SQLException {
		checkMutex();
        return resultSet.isBeforeFirst();
    }

    public boolean isAfterLast() throws SQLException {
		checkMutex();
        return resultSet.isAfterLast();
    }

    public boolean isFirst() throws SQLException {
		checkMutex();
        return resultSet.isFirst();
    }

    public boolean isLast() throws SQLException {
		checkMutex();
        return resultSet.isLast();
    }

    public void beforeFirst() throws SQLException {
		checkMutex();
        resultSet.beforeFirst();
    }

    public void afterLast() throws SQLException {
		checkMutex();
        resultSet.afterLast();
    }

    public boolean first() throws SQLException {
		checkMutex();
        return resultSet.first();
    }

    public boolean last() throws SQLException {
		checkMutex();
        return resultSet.last();
    }

    public int getRow() throws SQLException {
		checkMutex();
        return resultSet.getRow();
    }

    public boolean absolute(int row) throws SQLException {
		checkMutex();
        return resultSet.absolute(row);
    }

    public boolean relative(int rows) throws SQLException {
		checkMutex();
        return resultSet.relative(rows);
    }

    public boolean previous() throws SQLException {
		checkMutex();
        return resultSet.previous();
    }

    public void setFetchDirection(int direction) throws SQLException {
		checkMutex();
        resultSet.setFetchDirection(direction);
    }

    public int getFetchDirection() throws SQLException {
		checkMutex();
        return resultSet.getFetchDirection();
    }

    public void setFetchSize(int rows) throws SQLException {
		checkMutex();
        resultSet.setFetchSize(rows);
    }

    public int getFetchSize() throws SQLException {
		checkMutex();
        return resultSet.getFetchSize();
    }

    public int getType() throws SQLException {
        return resultSet.getType();
    }

    public int getConcurrency() throws SQLException {
        return resultSet.getConcurrency();
    }

    public boolean rowUpdated() throws SQLException {
		checkMutex();
        return resultSet.rowUpdated();
    }

    public boolean rowInserted() throws SQLException {
		checkMutex();
        return resultSet.rowInserted();
    }

    public boolean rowDeleted() throws SQLException {
		checkMutex();
        return resultSet.rowDeleted();
    }

    public void updateNull(int columnIndex) throws SQLException {
		checkMutex();
        resultSet.updateNull(columnIndex);
    }

    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		checkMutex();
        resultSet.updateBoolean(columnIndex, x);
    }

    public void updateByte(int columnIndex, byte x) throws SQLException {
		checkMutex();
        resultSet.updateByte(columnIndex, x);
    }

    public void updateShort(int columnIndex, short x) throws SQLException {
		checkMutex();
        resultSet.updateShort(columnIndex, x);
    }

    public void updateInt(int columnIndex, int x) throws SQLException {
		checkMutex();
        resultSet.updateInt(columnIndex, x);
    }

    public void updateLong(int columnIndex, long x) throws SQLException {
		checkMutex();
        resultSet.updateLong(columnIndex, x);
    }

    public void updateFloat(int columnIndex, float x) throws SQLException {
		checkMutex();
        resultSet.updateFloat(columnIndex, x);
    }

    public void updateDouble(int columnIndex, double x) throws SQLException {
		checkMutex();
        resultSet.updateDouble(columnIndex, x);
    }

    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		checkMutex();
        resultSet.updateBigDecimal(columnIndex, x);
    }

    public void updateString(int columnIndex, String x) throws SQLException {
		checkMutex();
        resultSet.updateString(columnIndex, x);
    }

    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		checkMutex();
        resultSet.updateBytes(columnIndex, x);
    }

    public void updateDate(int columnIndex, Date x) throws SQLException {
		checkMutex();
        resultSet.updateDate(columnIndex, x);
    }

    public void updateTime(int columnIndex, Time x) throws SQLException {
		checkMutex();
        resultSet.updateTime(columnIndex, x);
    }

    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		checkMutex();
        resultSet.updateTimestamp(columnIndex, x);
    }

    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		checkMutex();
        resultSet.updateAsciiStream(columnIndex, x, length);
    }

    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		checkMutex();
        resultSet.updateBinaryStream(columnIndex, x, length);
    }

    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		checkMutex();
        resultSet.updateCharacterStream(columnIndex, x, length);
    }

    public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
		checkMutex();
        resultSet.updateObject(columnIndex, x, scale);
    }

    public void updateObject(int columnIndex, Object x) throws SQLException {
		checkMutex();
        resultSet.updateObject(columnIndex, x);
    }

    public void updateNull(String columnName) throws SQLException {
		checkMutex();
        resultSet.updateNull(columnName);
    }

    public void updateBoolean(String columnName, boolean x) throws SQLException {
		checkMutex();
        resultSet.updateBoolean(columnName, x);
    }

    public void updateByte(String columnName, byte x) throws SQLException {
		checkMutex();
        resultSet.updateByte(columnName, x);
    }

    public void updateShort(String columnName, short x) throws SQLException {
		checkMutex();
        resultSet.updateShort(columnName, x);
    }

    public void updateInt(String columnName, int x) throws SQLException {
		checkMutex();
        resultSet.updateInt(columnName, x);
    }

    public void updateLong(String columnName, long x) throws SQLException {
		checkMutex();
        resultSet.updateLong(columnName, x);
    }

    public void updateFloat(String columnName, float x) throws SQLException {
		checkMutex();
        resultSet.updateFloat(columnName, x);
    }

    public void updateDouble(String columnName, double x) throws SQLException {
		checkMutex();
        resultSet.updateDouble(columnName, x);
    }

    public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
		checkMutex();
        resultSet.updateBigDecimal(columnName, x);
    }

    public void updateString(String columnName, String x) throws SQLException {
		checkMutex();
        resultSet.updateString(columnName, x);
    }

    public void updateBytes(String columnName, byte[] x) throws SQLException {
		checkMutex();
        resultSet.updateBytes(columnName, x);
    }

    public void updateDate(String columnName, Date x) throws SQLException {
		checkMutex();
        resultSet.updateDate(columnName, x);
    }

    public void updateTime(String columnName, Time x) throws SQLException {
		checkMutex();
        resultSet.updateTime(columnName, x);
    }

    public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
		checkMutex();
        resultSet.updateTimestamp(columnName, x);
    }

    public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
		checkMutex();
        resultSet.updateAsciiStream(columnName, x, length);
    }

    public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
		checkMutex();
        resultSet.updateBinaryStream(columnName, x, length);
    }

    public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
		checkMutex();
        resultSet.updateCharacterStream(columnName, reader, length);
    }

    public void updateObject(String columnName, Object x, int scale) throws SQLException {
		checkMutex();
        resultSet.updateObject(columnName, x, scale);
    }

    public void updateObject(String columnName, Object x) throws SQLException {
		checkMutex();
        resultSet.updateObject(columnName, x);
    }

    public void insertRow() throws SQLException {
		checkMutex();
        resultSet.insertRow();
    }

    public void updateRow() throws SQLException {
		checkMutex();
        resultSet.updateRow();
    }

    public void deleteRow() throws SQLException {
		checkMutex();
        resultSet.deleteRow();
    }

    public void refreshRow() throws SQLException {
		checkMutex();
        resultSet.refreshRow();
    }

    public void cancelRowUpdates() throws SQLException {
		checkMutex();
        resultSet.cancelRowUpdates();
    }

    public void moveToInsertRow() throws SQLException {
		checkMutex();
        resultSet.moveToInsertRow();
    }

    public void moveToCurrentRow() throws SQLException {
		checkMutex();
        resultSet.moveToCurrentRow();
    }

    public Statement getStatement() throws SQLException {
        return resultSet.getStatement();
    }

    public Object getObject(int i, Map<String,Class<?>> map) throws SQLException {
		checkMutex();
        return resultSet.getObject(i, map);
    }

    public Ref getRef(int i) throws SQLException {
		checkMutex();
        return resultSet.getRef(i);
    }

    public Blob getBlob(int i) throws SQLException {
		checkMutex();
        return resultSet.getBlob(i);
    }

    public Clob getClob(int i) throws SQLException {
		checkMutex();
        return resultSet.getClob(i);
    }

    public Array getArray(int i) throws SQLException {
		checkMutex();
        return resultSet.getArray(i);
    }

    public Object getObject(String colName, Map<String,Class<?>> map) throws SQLException {
		checkMutex();
        return resultSet.getObject(colName, map);
    }

    public Ref getRef(String colName) throws SQLException {
		checkMutex();
        return resultSet.getRef(colName);
    }

    public Blob getBlob(String colName) throws SQLException {
		checkMutex();
        return resultSet.getBlob(colName);
    }

    public Clob getClob(String colName) throws SQLException {
		checkMutex();
        return resultSet.getClob(colName);
    }

    public Array getArray(String colName) throws SQLException {
		checkMutex();
        return resultSet.getArray(colName);
    }

    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		checkMutex();
        return resultSet.getDate(columnIndex, cal);
    }

    public Date getDate(String columnName, Calendar cal) throws SQLException {
		checkMutex();
        return resultSet.getDate(columnName, cal);
    }

    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		checkMutex();
        return resultSet.getTime(columnIndex, cal);
    }

    public Time getTime(String columnName, Calendar cal) throws SQLException {
		checkMutex();
        return resultSet.getTime(columnName, cal);
    }

    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		checkMutex();
        return resultSet.getTimestamp(columnIndex, cal);
    }

    public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
		checkMutex();
        return resultSet.getTimestamp(columnName, cal);
    }

    public URL getURL(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getURL(columnIndex);
    }

    public URL getURL(String columnName) throws SQLException {
		checkMutex();
        return resultSet.getURL(columnName);
    }

    public void updateRef(int columnIndex, Ref x) throws SQLException {
		checkMutex();
        resultSet.updateRef(columnIndex, x);
    }

    public void updateRef(String columnName, Ref x) throws SQLException {
		checkMutex();
        resultSet.updateRef(columnName, x);
    }

    public void updateBlob(int columnIndex, Blob x) throws SQLException {
		checkMutex();
        resultSet.updateBlob(columnIndex, x);
    }

    public void updateBlob(String columnName, Blob x) throws SQLException {
		checkMutex();
        resultSet.updateBlob(columnName, x);
    }

    public void updateClob(int columnIndex, Clob x) throws SQLException {
		checkMutex();
        resultSet.updateClob(columnIndex, x);
    }

    public void updateClob(String columnName, Clob x) throws SQLException {
		checkMutex();
        resultSet.updateClob(columnName, x);
    }

    public void updateArray(int columnIndex, Array x) throws SQLException {
		checkMutex();
        resultSet.updateArray(columnIndex, x);
    }

    public void updateArray(String columnName, Array x) throws SQLException {
		checkMutex();
        resultSet.updateArray(columnName, x);
    }

    public RowId getRowId(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getRowId(columnIndex);
    }

    public RowId getRowId(String columnLabel) throws SQLException {
		checkMutex();
        return resultSet.getRowId(columnLabel);
    }

    public void updateRowId(int columnIndex, RowId x) throws SQLException {
		checkMutex();
        resultSet.updateRowId(columnIndex, x);
    }

    public void updateRowId(String columnLabel, RowId x) throws SQLException {
		checkMutex();
        resultSet.updateRowId(columnLabel, x);
    }

    public int getHoldability() throws SQLException {
        return resultSet.getHoldability();
    }

    public boolean isClosed() throws SQLException {
        return resultSet.isClosed();
    }

    public void updateNString(int columnIndex, String nString) throws SQLException {
		checkMutex();
        resultSet.updateNString(columnIndex, nString);
    }

    public void updateNString(String columnLabel, String nString) throws SQLException {
		checkMutex();
        resultSet.updateNString(columnLabel, nString);
    }

    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		checkMutex();
        resultSet.updateNClob(columnIndex, nClob);
    }

    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		checkMutex();
        resultSet.updateNClob(columnLabel, nClob);
    }

    public NClob getNClob(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getNClob(columnIndex);
    }

    public NClob getNClob(String columnLabel) throws SQLException {
		checkMutex();
        return resultSet.getNClob(columnLabel);
    }

    public SQLXML getSQLXML(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getSQLXML(columnIndex);
    }

    public SQLXML getSQLXML(String columnLabel) throws SQLException {
		checkMutex();
        return resultSet.getSQLXML(columnLabel);
    }

    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		checkMutex();
        resultSet.updateSQLXML(columnIndex, xmlObject);
    }

    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		checkMutex();
        resultSet.updateSQLXML(columnLabel, xmlObject);
    }

    public String getNString(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getNString(columnIndex);
    }

    public String getNString(String columnLabel) throws SQLException {
		checkMutex();
        return resultSet.getNString(columnLabel);
    }

    public Reader getNCharacterStream(int columnIndex) throws SQLException {
		checkMutex();
        return resultSet.getNCharacterStream(columnIndex);
    }

    public Reader getNCharacterStream(String columnLabel) throws SQLException {
		checkMutex();
        return resultSet.getNCharacterStream(columnLabel);
    }

    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		checkMutex();
        resultSet.updateNCharacterStream(columnIndex, x, length);
    }

    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		checkMutex();
        resultSet.updateNCharacterStream(columnLabel, reader, length);
    }

    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		checkMutex();
        resultSet.updateAsciiStream(columnIndex, x);
    }

    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		checkMutex();
        resultSet.updateBinaryStream(columnIndex, x);
    }

    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		checkMutex();
        resultSet.updateCharacterStream(columnIndex, x);
    }

    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		checkMutex();
        resultSet.updateAsciiStream(columnLabel, x);
    }

    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		checkMutex();
        resultSet.updateBinaryStream(columnLabel, x);
    }

    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		checkMutex();
        resultSet.updateCharacterStream(columnLabel, reader, length);
    }

    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		checkMutex();
        resultSet.updateBlob(columnIndex, inputStream);
    }

    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		checkMutex();
        resultSet.updateBlob(columnLabel, inputStream, length);
    }

    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		checkMutex();
        resultSet.updateClob(columnIndex, reader, length);
    }

    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		checkMutex();
        resultSet.updateClob(columnLabel, reader, length);
    }

    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		checkMutex();
        resultSet.updateNClob(columnIndex, reader, length);
    }

    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		checkMutex();
        resultSet.updateNClob(columnLabel, reader, length);
    }

    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		checkMutex();
        resultSet.updateNCharacterStream(columnIndex, x);
    }

    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		checkMutex();
        resultSet.updateNCharacterStream(columnLabel, reader);
    }

    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		checkMutex();
        resultSet.updateAsciiStream(columnIndex, x);
    }

    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		checkMutex();
        resultSet.updateBinaryStream(columnIndex, x);
    }

    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		checkMutex();
        resultSet.updateCharacterStream(columnIndex, x);
    }

    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		checkMutex();
        resultSet.updateAsciiStream(columnLabel, x);
    }

    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		checkMutex();
        resultSet.updateBinaryStream(columnLabel, x);
    }

    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		checkMutex();
        resultSet.updateCharacterStream(columnLabel, reader);
    }

    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		checkMutex();
        resultSet.updateBlob(columnIndex, inputStream);
    }

    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		checkMutex();
        resultSet.updateBlob(columnLabel, inputStream);
    }

    public void updateClob(int columnIndex, Reader reader) throws SQLException {
		checkMutex();
        resultSet.updateClob(columnIndex, reader);
    }

    public void updateClob(String columnLabel, Reader reader) throws SQLException {
		checkMutex();
        resultSet.updateClob(columnLabel, reader);
    }

    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		checkMutex();
        resultSet.updateNClob(columnIndex, reader);
    }

    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		checkMutex();
        resultSet.updateNClob(columnLabel, reader);
    }

    public <T extends Object> T unwrap(Class<T> iface) throws SQLException {
        return resultSet.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return resultSet.isWrapperFor(iface);
    }
}
