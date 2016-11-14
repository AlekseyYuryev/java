/* Copyright (c) 2014 Seva Safris
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

package org.safris.cf.xde.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.safris.cf.xde.DataType;
import org.safris.cf.xde.Entity;
import org.safris.cf.xde.GenerateOn;
import org.safris.cf.xdl.DBVendor;

public final class Long extends DataType<java.lang.Long> {
  protected static final int sqlType = Types.INTEGER;

  protected static java.lang.Long get(final ResultSet resultSet, final int columnIndex) throws SQLException {
    final long value = resultSet.getLong(columnIndex);
    return resultSet.wasNull() ? null : value;
  }

  protected static void set(final PreparedStatement statement, final int parameterIndex, final java.lang.Long value) throws SQLException {
    if (value != null)
      statement.setLong(parameterIndex, value);
    else
      statement.setNull(parameterIndex, sqlType);
  }

  public final int precision;
  public final boolean unsigned;
  public final java.lang.Long min;
  public final java.lang.Long max;

  public Long(final Entity owner, final String specName, final String name, final java.lang.Long _default, final boolean unique, final boolean primary, final boolean nullable, final GenerateOn<? super java.lang.Long> generateOnInsert, final GenerateOn<? super java.lang.Long> generateOnUpdate, final int precision, final boolean unsigned, final java.lang.Long min, final java.lang.Long max) {
    super(sqlType, java.lang.Long.class, owner, specName, name, _default, unique, primary, nullable, generateOnInsert, generateOnUpdate);
    this.precision = precision;
    this.unsigned = unsigned;
    this.min = min;
    this.max = max;
  }

  protected Long(final Long copy) {
    super(copy);
    this.precision = copy.precision;
    this.unsigned = copy.unsigned;
    this.min = copy.min;
    this.max = copy.max;
  }

  @Override
  protected String getPreparedStatementMark(final DBVendor vendor) {
    return "?";
  }

  @Override
  protected void get(final PreparedStatement statement, final int parameterIndex) throws SQLException {
    set(statement, parameterIndex, get());
  }

  @Override
  protected void set(final ResultSet resultSet, final int columnIndex) throws SQLException {
    this.value = get(resultSet, columnIndex);
  }
}