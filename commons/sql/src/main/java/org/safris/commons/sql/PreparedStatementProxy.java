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
    private static final NumberFormat numberFormat = new DecimalFormat("###############.###########;-###############.###########");
    private final PreparedStatement preparedStatement;
    private final String sql;
    private final Map<Integer, Object> parameterMap = new HashMap<Integer, Object>();

    protected final Map<Integer, Object> getParameterMap() {
        return parameterMap;
    }

    protected PreparedStatement getStatement() {
        return preparedStatement;
    }

    public PreparedStatementProxy(PreparedStatement preparedStatement, String sql) {
        super(preparedStatement);
        this.preparedStatement = preparedStatement;
        this.sql = sql;
    }

    public ResultSet executeQuery() throws SQLException {
        final PreparedStatement statement = getStatement();
        final long time = System.currentTimeMillis();
        final ResultSet resultSetOriginal = statement.executeQuery();
        final ResultSetProxy resultSet = new ResultSetProxy(resultSetOriginal);

        if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
            final StringBuilder buffer = new StringBuilder("[" + statement.toString() + "].executeQuery() {\n");
            buffer.append(toString());
            buffer.append("\n} -> " + resultSet.getSize() + "\t\t" + (System.currentTimeMillis() - time) + "ms");
            Logger.getAnonymousLogger().fine(buffer.toString());
        }

        return resultSet;
    }

    public int executeUpdate() throws SQLException {
        final StringBuilder buffer = new StringBuilder("[" + getStatement().toString() + "].executeUpdate() {\n");
        buffer.append("  " + toString());

        final long time = System.currentTimeMillis();
        final int count = getStatement().executeUpdate();
        buffer.append("\n} -> " + count + "\t\t" + (System.currentTimeMillis() - time) + "ms");
        Logger.getAnonymousLogger().fine(buffer.toString());
        return count;
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        getStatement().setNull(parameterIndex, sqlType);
        getParameterMap().put(parameterIndex, "NULL");
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        getStatement().setBoolean(parameterIndex, x);
        getParameterMap().put(parameterIndex, x);
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        getStatement().setByte(parameterIndex, x);
        getParameterMap().put(parameterIndex, "'" + x + "'");
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        getStatement().setShort(parameterIndex, x);
        getParameterMap().put(parameterIndex, numberFormat.format(x));
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        getStatement().setInt(parameterIndex, x);
        getParameterMap().put(parameterIndex, numberFormat.format(x));
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        getStatement().setLong(parameterIndex, x);
        getParameterMap().put(parameterIndex, numberFormat.format(x));
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        getStatement().setFloat(parameterIndex, x);
        getParameterMap().put(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        getStatement().setDouble(parameterIndex, x);
        getParameterMap().put(parameterIndex, numberFormat.format(x));
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        getStatement().setBigDecimal(parameterIndex, x);
        getParameterMap().put(parameterIndex, numberFormat.format(x));
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        getStatement().setString(parameterIndex, x);
        getParameterMap().put(parameterIndex, "'" + x + "'");
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        getStatement().setBytes(parameterIndex, x);
        getParameterMap().put(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        getStatement().setDate(parameterIndex, x);
        getParameterMap().put(parameterIndex, "'" + new SimpleDateFormat("yyMMdd HH:mm:ss").format(x) + "'");
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        getStatement().setTime(parameterIndex, x);
        getParameterMap().put(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        getStatement().setTimestamp(parameterIndex, x);
        getParameterMap().put(parameterIndex, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getStatement().setAsciiStream(parameterIndex, x, length);
        getParameterMap().put(parameterIndex, x);
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getStatement().setUnicodeStream(parameterIndex, x, length);
        getParameterMap().put(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getStatement().setBinaryStream(parameterIndex, x, length);
        getParameterMap().put(parameterIndex, x);
    }

    public void clearParameters() throws SQLException {
        getStatement().clearParameters();
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
        getStatement().setObject(parameterIndex, x, targetSqlType, scale);
        getParameterMap().put(parameterIndex, x);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        getStatement().setObject(parameterIndex, x, targetSqlType);
        getParameterMap().put(parameterIndex, x);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        getStatement().setObject(parameterIndex, x);
        getParameterMap().put(parameterIndex, x);
    }

    public boolean execute() throws SQLException {
        final long time = System.currentTimeMillis();
        final boolean result = getStatement().execute();
        if (Logger.getAnonymousLogger().getLevel().equals(Level.FINE)) {
            final StringBuilder buffer = new StringBuilder("[" + getStatement().toString() + "].execute() {\n");
            buffer.append(toString());
            buffer.append("\n} -> " + result + "\t\t" + (System.currentTimeMillis() - time) + "ms");
            Logger.getAnonymousLogger().fine(buffer.toString());
        }

        return result;
    }

    public void addBatch() throws SQLException {
        Logger.getAnonymousLogger().fine(toString());
        getStatement().addBatch();
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        getStatement().setCharacterStream(parameterIndex, reader, length);
    }

    public void setRef(int i, Ref x) throws SQLException {
        getStatement().setRef(i, x);
    }

    public void setBlob(int i, Blob x) throws SQLException {
        getStatement().setBlob(i, x);
    }

    public void setClob(int i, Clob x) throws SQLException {
        getStatement().setClob(i, x);
    }

    public void setArray(int i, Array x) throws SQLException {
        getStatement().setArray(i, x);
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return getStatement().getMetaData();
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        getStatement().setDate(parameterIndex, x, cal);
        getParameterMap().put(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        getStatement().setTime(parameterIndex, x, cal);
        getParameterMap().put(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        getStatement().setTimestamp(parameterIndex, x, cal);
    }

    public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
        getStatement().setNull(paramIndex, sqlType, typeName);
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        getStatement().setURL(parameterIndex, x);
        getParameterMap().put(parameterIndex, x);
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return getStatement().getParameterMetaData();
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

    public String toString() {
        final StringTokenizer tokenizer = new StringTokenizer(sql, "?");
        final StringBuilder buffer = new StringBuilder();
        int index = 0;
        while (tokenizer.hasMoreTokens()) {
            buffer.append(tokenizer.nextToken());
            final Object value = getParameterMap().get(++index);
            if (value != null)
                buffer.append(value);
        }

        final String display = buffer.toString();
//        String display = null;
//        try {
//            display = SQLFormat.format(buffer.toString());
//        }
//        catch (ParseException e) {
//            throw new RuntimeException(buffer.toString(), e);
//        }

        return display;
    }
}
