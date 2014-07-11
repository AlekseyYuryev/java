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

public final class ResultSetProxy implements ResultSet {
  public static void close(final ResultSet resultSet) {
    try {
      if (resultSet != null && !resultSet.isClosed())
        resultSet.close();
    }
    catch (final SQLException e) {
    }
  }

  private final ResultSet resultSet;
  private volatile boolean mutex = false;

  public ResultSetProxy(final ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  private void checkMutex() {
    if (!mutex)
      return;

    try {
      resultSet.wait();
    }
    catch (final InterruptedException e) {
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

  public String getString(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getString(columnIndex);
  }

  public boolean getBoolean(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getBoolean(columnIndex);
  }

  public byte getByte(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getByte(columnIndex);
  }

  public short getShort(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getShort(columnIndex);
  }

  public int getInt(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getInt(columnIndex);
  }

  public long getLong(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getLong(columnIndex);
  }

  public float getFloat(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getFloat(columnIndex);
  }

  public double getDouble(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getDouble(columnIndex);
  }

  /**
   * @deprecated
   */
  public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException {
    checkMutex();
    return resultSet.getBigDecimal(columnIndex, scale);
  }

  public byte[] getBytes(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getBytes(columnIndex);
  }

  public Date getDate(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getDate(columnIndex);
  }

  public Time getTime(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getTime(columnIndex);
  }

  public Timestamp getTimestamp(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getTimestamp(columnIndex);
  }

  public InputStream getAsciiStream(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getAsciiStream(columnIndex);
  }

  /**
   * @deprecated
   */
  public InputStream getUnicodeStream(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getUnicodeStream(columnIndex);
  }

  public InputStream getBinaryStream(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getBinaryStream(columnIndex);
  }

  public String getString(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getString(columnName);
  }

  public boolean getBoolean(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getBoolean(columnName);
  }

  public byte getByte(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getByte(columnName);
  }

  public short getShort(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getShort(columnName);
  }

  public int getInt(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getInt(columnName);
  }

  public long getLong(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getLong(columnName);
  }

  public float getFloat(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getFloat(columnName);
  }

  public double getDouble(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getDouble(columnName);
  }

  /**
   * @deprecated
   */
  public BigDecimal getBigDecimal(final String columnName, final int scale) throws SQLException {
    checkMutex();
    return resultSet.getBigDecimal(columnName, scale);
  }

  public byte[] getBytes(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getBytes(columnName);
  }

  public Date getDate(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getDate(columnName);
  }

  public Time getTime(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getTime(columnName);
  }

  public Timestamp getTimestamp(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getTimestamp(columnName);
  }

  public InputStream getAsciiStream(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getAsciiStream(columnName);
  }

  /**
   * @deprecated
   */
  public InputStream getUnicodeStream(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getUnicodeStream(columnName);
  }

  public InputStream getBinaryStream(final String columnName) throws SQLException {
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

  public Object getObject(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getObject(columnIndex);
  }

  public Object getObject(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getObject(columnName);
  }

  public int findColumn(final String columnName) throws SQLException {
    return resultSet.findColumn(columnName);
  }

  public Reader getCharacterStream(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getCharacterStream(columnIndex);
  }

  public Reader getCharacterStream(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getCharacterStream(columnName);
  }

  public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getBigDecimal(columnIndex);
  }

  public BigDecimal getBigDecimal(final String columnName) throws SQLException {
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

  public boolean absolute(final int row) throws SQLException {
    checkMutex();
    return resultSet.absolute(row);
  }

  public boolean relative(final int rows) throws SQLException {
    checkMutex();
    return resultSet.relative(rows);
  }

  public boolean previous() throws SQLException {
    checkMutex();
    return resultSet.previous();
  }

  public void setFetchDirection(final int direction) throws SQLException {
    checkMutex();
    resultSet.setFetchDirection(direction);
  }

  public int getFetchDirection() throws SQLException {
    checkMutex();
    return resultSet.getFetchDirection();
  }

  public void setFetchSize(final int rows) throws SQLException {
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

  public void updateNull(final int columnIndex) throws SQLException {
    checkMutex();
    resultSet.updateNull(columnIndex);
  }

  public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
    checkMutex();
    resultSet.updateBoolean(columnIndex, x);
  }

  public void updateByte(final int columnIndex, final byte x) throws SQLException {
    checkMutex();
    resultSet.updateByte(columnIndex, x);
  }

  public void updateShort(final int columnIndex, final short x) throws SQLException {
    checkMutex();
    resultSet.updateShort(columnIndex, x);
  }

  public void updateInt(final int columnIndex, final int x) throws SQLException {
    checkMutex();
    resultSet.updateInt(columnIndex, x);
  }

  public void updateLong(final int columnIndex, final long x) throws SQLException {
    checkMutex();
    resultSet.updateLong(columnIndex, x);
  }

  public void updateFloat(final int columnIndex, final float x) throws SQLException {
    checkMutex();
    resultSet.updateFloat(columnIndex, x);
  }

  public void updateDouble(final int columnIndex, final double x) throws SQLException {
    checkMutex();
    resultSet.updateDouble(columnIndex, x);
  }

  public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
    checkMutex();
    resultSet.updateBigDecimal(columnIndex, x);
  }

  public void updateString(final int columnIndex, final String x) throws SQLException {
    checkMutex();
    resultSet.updateString(columnIndex, x);
  }

  public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
    checkMutex();
    resultSet.updateBytes(columnIndex, x);
  }

  public void updateDate(final int columnIndex, final Date x) throws SQLException {
    checkMutex();
    resultSet.updateDate(columnIndex, x);
  }

  public void updateTime(final int columnIndex, final Time x) throws SQLException {
    checkMutex();
    resultSet.updateTime(columnIndex, x);
  }

  public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
    checkMutex();
    resultSet.updateTimestamp(columnIndex, x);
  }

  public void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
    checkMutex();
    resultSet.updateAsciiStream(columnIndex, x, length);
  }

  public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
    checkMutex();
    resultSet.updateBinaryStream(columnIndex, x, length);
  }

  public void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException {
    checkMutex();
    resultSet.updateCharacterStream(columnIndex, x, length);
  }

  public void updateObject(final int columnIndex, final Object x, final int scale) throws SQLException {
    checkMutex();
    resultSet.updateObject(columnIndex, x, scale);
  }

  public void updateObject(final int columnIndex, final Object x) throws SQLException {
    checkMutex();
    resultSet.updateObject(columnIndex, x);
  }

  public void updateNull(final String columnName) throws SQLException {
    checkMutex();
    resultSet.updateNull(columnName);
  }

  public void updateBoolean(final String columnName, final boolean x) throws SQLException {
    checkMutex();
    resultSet.updateBoolean(columnName, x);
  }

  public void updateByte(final String columnName, final byte x) throws SQLException {
    checkMutex();
    resultSet.updateByte(columnName, x);
  }

  public void updateShort(final String columnName, final short x) throws SQLException {
    checkMutex();
    resultSet.updateShort(columnName, x);
  }

  public void updateInt(final String columnName, final int x) throws SQLException {
    checkMutex();
    resultSet.updateInt(columnName, x);
  }

  public void updateLong(final String columnName, final long x) throws SQLException {
    checkMutex();
    resultSet.updateLong(columnName, x);
  }

  public void updateFloat(final String columnName, final float x) throws SQLException {
    checkMutex();
    resultSet.updateFloat(columnName, x);
  }

  public void updateDouble(final String columnName, final double x) throws SQLException {
    checkMutex();
    resultSet.updateDouble(columnName, x);
  }

  public void updateBigDecimal(final String columnName, final BigDecimal x) throws SQLException {
    checkMutex();
    resultSet.updateBigDecimal(columnName, x);
  }

  public void updateString(final String columnName, final String x) throws SQLException {
    checkMutex();
    resultSet.updateString(columnName, x);
  }

  public void updateBytes(final String columnName, final byte[] x) throws SQLException {
    checkMutex();
    resultSet.updateBytes(columnName, x);
  }

  public void updateDate(final String columnName, final Date x) throws SQLException {
    checkMutex();
    resultSet.updateDate(columnName, x);
  }

  public void updateTime(final String columnName, final Time x) throws SQLException {
    checkMutex();
    resultSet.updateTime(columnName, x);
  }

  public void updateTimestamp(final String columnName, final Timestamp x) throws SQLException {
    checkMutex();
    resultSet.updateTimestamp(columnName, x);
  }

  public void updateAsciiStream(final String columnName, final InputStream x, final int length) throws SQLException {
    checkMutex();
    resultSet.updateAsciiStream(columnName, x, length);
  }

  public void updateBinaryStream(final String columnName, final InputStream x, final int length) throws SQLException {
    checkMutex();
    resultSet.updateBinaryStream(columnName, x, length);
  }

  public void updateCharacterStream(final String columnName, final Reader reader, final int length) throws SQLException {
    checkMutex();
    resultSet.updateCharacterStream(columnName, reader, length);
  }

  public void updateObject(final String columnName, final Object x, final int scale) throws SQLException {
    checkMutex();
    resultSet.updateObject(columnName, x, scale);
  }

  public void updateObject(final String columnName, final Object x) throws SQLException {
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

  public Object getObject(final int i, final Map<String,Class<?>> map) throws SQLException {
    checkMutex();
    return resultSet.getObject(i, map);
  }

  public Ref getRef(final int i) throws SQLException {
    checkMutex();
    return resultSet.getRef(i);
  }

  public Blob getBlob(final int i) throws SQLException {
    checkMutex();
    return resultSet.getBlob(i);
  }

  public Clob getClob(final int i) throws SQLException {
    checkMutex();
    return resultSet.getClob(i);
  }

  public Array getArray(final int i) throws SQLException {
    checkMutex();
    return resultSet.getArray(i);
  }

  public Object getObject(final String colName, final Map<String,Class<?>> map) throws SQLException {
    checkMutex();
    return resultSet.getObject(colName, map);
  }

  public Ref getRef(final String colName) throws SQLException {
    checkMutex();
    return resultSet.getRef(colName);
  }

  public Blob getBlob(final String colName) throws SQLException {
    checkMutex();
    return resultSet.getBlob(colName);
  }

  public Clob getClob(final String colName) throws SQLException {
    checkMutex();
    return resultSet.getClob(colName);
  }

  public Array getArray(final String colName) throws SQLException {
    checkMutex();
    return resultSet.getArray(colName);
  }

  public Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
    checkMutex();
    return resultSet.getDate(columnIndex, cal);
  }

  public Date getDate(final String columnName, final Calendar cal) throws SQLException {
    checkMutex();
    return resultSet.getDate(columnName, cal);
  }

  public Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
    checkMutex();
    return resultSet.getTime(columnIndex, cal);
  }

  public Time getTime(final String columnName, final Calendar cal) throws SQLException {
    checkMutex();
    return resultSet.getTime(columnName, cal);
  }

  public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException {
    checkMutex();
    return resultSet.getTimestamp(columnIndex, cal);
  }

  public Timestamp getTimestamp(final String columnName, final Calendar cal) throws SQLException {
    checkMutex();
    return resultSet.getTimestamp(columnName, cal);
  }

  public URL getURL(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getURL(columnIndex);
  }

  public URL getURL(final String columnName) throws SQLException {
    checkMutex();
    return resultSet.getURL(columnName);
  }

  public void updateRef(final int columnIndex, final Ref x) throws SQLException {
    checkMutex();
    resultSet.updateRef(columnIndex, x);
  }

  public void updateRef(final String columnName, final Ref x) throws SQLException {
    checkMutex();
    resultSet.updateRef(columnName, x);
  }

  public void updateBlob(final int columnIndex, final Blob x) throws SQLException {
    checkMutex();
    resultSet.updateBlob(columnIndex, x);
  }

  public void updateBlob(final String columnName, final Blob x) throws SQLException {
    checkMutex();
    resultSet.updateBlob(columnName, x);
  }

  public void updateClob(final int columnIndex, final Clob x) throws SQLException {
    checkMutex();
    resultSet.updateClob(columnIndex, x);
  }

  public void updateClob(final String columnName, final Clob x) throws SQLException {
    checkMutex();
    resultSet.updateClob(columnName, x);
  }

  public void updateArray(final int columnIndex, final Array x) throws SQLException {
    checkMutex();
    resultSet.updateArray(columnIndex, x);
  }

  public void updateArray(final String columnName, final Array x) throws SQLException {
    checkMutex();
    resultSet.updateArray(columnName, x);
  }

  public RowId getRowId(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getRowId(columnIndex);
  }

  public RowId getRowId(final String columnLabel) throws SQLException {
    checkMutex();
    return resultSet.getRowId(columnLabel);
  }

  public void updateRowId(final int columnIndex, final RowId x) throws SQLException {
    checkMutex();
    resultSet.updateRowId(columnIndex, x);
  }

  public void updateRowId(final String columnLabel, final RowId x) throws SQLException {
    checkMutex();
    resultSet.updateRowId(columnLabel, x);
  }

  public int getHoldability() throws SQLException {
    return resultSet.getHoldability();
  }

  public boolean isClosed() throws SQLException {
    return resultSet.isClosed();
  }

  public void updateNString(final int columnIndex, final String nString) throws SQLException {
    checkMutex();
    resultSet.updateNString(columnIndex, nString);
  }

  public void updateNString(final String columnLabel, final String nString) throws SQLException {
    checkMutex();
    resultSet.updateNString(columnLabel, nString);
  }

  public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException {
    checkMutex();
    resultSet.updateNClob(columnIndex, nClob);
  }

  public void updateNClob(final String columnLabel, final NClob nClob) throws SQLException {
    checkMutex();
    resultSet.updateNClob(columnLabel, nClob);
  }

  public NClob getNClob(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getNClob(columnIndex);
  }

  public NClob getNClob(final String columnLabel) throws SQLException {
    checkMutex();
    return resultSet.getNClob(columnLabel);
  }

  public SQLXML getSQLXML(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getSQLXML(columnIndex);
  }

  public SQLXML getSQLXML(final String columnLabel) throws SQLException {
    checkMutex();
    return resultSet.getSQLXML(columnLabel);
  }

  public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException {
    checkMutex();
    resultSet.updateSQLXML(columnIndex, xmlObject);
  }

  public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException {
    checkMutex();
    resultSet.updateSQLXML(columnLabel, xmlObject);
  }

  public String getNString(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getNString(columnIndex);
  }

  public String getNString(final String columnLabel) throws SQLException {
    checkMutex();
    return resultSet.getNString(columnLabel);
  }

  public Reader getNCharacterStream(final int columnIndex) throws SQLException {
    checkMutex();
    return resultSet.getNCharacterStream(columnIndex);
  }

  public Reader getNCharacterStream(final String columnLabel) throws SQLException {
    checkMutex();
    return resultSet.getNCharacterStream(columnLabel);
  }

  public void updateNCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
    checkMutex();
    resultSet.updateNCharacterStream(columnIndex, x, length);
  }

  public void updateNCharacterStream(final String columnLabel, final Reader reader, final long length) throws SQLException {
    checkMutex();
    resultSet.updateNCharacterStream(columnLabel, reader, length);
  }

  public void updateAsciiStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
    checkMutex();
    resultSet.updateAsciiStream(columnIndex, x);
  }

  public void updateBinaryStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
    checkMutex();
    resultSet.updateBinaryStream(columnIndex, x);
  }

  public void updateCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
    checkMutex();
    resultSet.updateCharacterStream(columnIndex, x);
  }

  public void updateAsciiStream(final String columnLabel, final InputStream x, final long length) throws SQLException {
    checkMutex();
    resultSet.updateAsciiStream(columnLabel, x);
  }

  public void updateBinaryStream(final String columnLabel, final InputStream x, final long length) throws SQLException {
    checkMutex();
    resultSet.updateBinaryStream(columnLabel, x);
  }

  public void updateCharacterStream(final String columnLabel, final Reader reader, final long length) throws SQLException {
    checkMutex();
    resultSet.updateCharacterStream(columnLabel, reader, length);
  }

  public void updateBlob(final int columnIndex, final InputStream inputStream, final long length) throws SQLException {
    checkMutex();
    resultSet.updateBlob(columnIndex, inputStream);
  }

  public void updateBlob(final String columnLabel, final InputStream inputStream, final long length) throws SQLException {
    checkMutex();
    resultSet.updateBlob(columnLabel, inputStream, length);
  }

  public void updateClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
    checkMutex();
    resultSet.updateClob(columnIndex, reader, length);
  }

  public void updateClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
    checkMutex();
    resultSet.updateClob(columnLabel, reader, length);
  }

  public void updateNClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
    checkMutex();
    resultSet.updateNClob(columnIndex, reader, length);
  }

  public void updateNClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
    checkMutex();
    resultSet.updateNClob(columnLabel, reader, length);
  }

  public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException {
    checkMutex();
    resultSet.updateNCharacterStream(columnIndex, x);
  }

  public void updateNCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
    checkMutex();
    resultSet.updateNCharacterStream(columnLabel, reader);
  }

  public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException {
    checkMutex();
    resultSet.updateAsciiStream(columnIndex, x);
  }

  public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException {
    checkMutex();
    resultSet.updateBinaryStream(columnIndex, x);
  }

  public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException {
    checkMutex();
    resultSet.updateCharacterStream(columnIndex, x);
  }

  public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException {
    checkMutex();
    resultSet.updateAsciiStream(columnLabel, x);
  }

  public void updateBinaryStream(final String columnLabel, final InputStream x) throws SQLException {
    checkMutex();
    resultSet.updateBinaryStream(columnLabel, x);
  }

  public void updateCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
    checkMutex();
    resultSet.updateCharacterStream(columnLabel, reader);
  }

  public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException {
    checkMutex();
    resultSet.updateBlob(columnIndex, inputStream);
  }

  public void updateBlob(final String columnLabel, final InputStream inputStream) throws SQLException {
    checkMutex();
    resultSet.updateBlob(columnLabel, inputStream);
  }

  public void updateClob(final int columnIndex, final Reader reader) throws SQLException {
    checkMutex();
    resultSet.updateClob(columnIndex, reader);
  }

  public void updateClob(final String columnLabel, final Reader reader) throws SQLException {
    checkMutex();
    resultSet.updateClob(columnLabel, reader);
  }

  public void updateNClob(final int columnIndex, final Reader reader) throws SQLException {
    checkMutex();
    resultSet.updateNClob(columnIndex, reader);
  }

  public void updateNClob(final String columnLabel, final Reader reader) throws SQLException {
    checkMutex();
    resultSet.updateNClob(columnLabel, reader);
  }

  public <T extends Object>T unwrap(final Class<T> iface) throws SQLException {
    return resultSet.unwrap(iface);
  }

  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return resultSet.isWrapperFor(iface);
  }

  public <T>T getObject(final int columnIndex, final Class<T> type) throws SQLException {
    return resultSet.getObject(columnIndex, type);
  }

  public <T>T getObject(final String columnLabel, final Class<T> type) throws SQLException {
    return resultSet.getObject(columnLabel, type);
  }
}