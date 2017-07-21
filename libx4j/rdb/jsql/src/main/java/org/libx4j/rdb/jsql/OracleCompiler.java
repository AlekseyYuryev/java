/* Copyright (c) 2017 lib4j
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

package org.libx4j.rdb.jsql;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.Map;

import org.libx4j.rdb.vendor.DBVendor;
import org.libx4j.rdb.vendor.Dialect;

final class OracleCompiler extends Compiler {
  private static Constructor<?> INTERVALDS;

  private static Object newINTERVALDS(final String s) {
    try {
      return (INTERVALDS == null ? INTERVALDS = Class.forName("oracle.sql.INTERVALDS").getConstructor(String.class) : INTERVALDS).newInstance(s);
    }
    catch (final ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SecurityException e) {
      throw new ExceptionInInitializerError(e);
    }
    catch (final InstantiationException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  protected DBVendor getVendor() {
    return DBVendor.ORACLE;
  }

  @Override
  protected void onRegister(final Connection connection) throws SQLException {
  }

  @Override
  protected String compile(final As<?> as) {
    return null;
  }

  @Override
  protected void compile(final SelectCommand command, final Select.SELECT<?> select, final Compilation compilation) throws IOException {
    if (command.limit() != null) {
      compilation.append("SELECT * FROM (");
      if (command.offset() != null) {
        compilation.append("SELECT ROWNUM rnum3729, r.* FROM (");
        compilation.skipFirstColumn(true);
      }
    }

    super.compile(command, select, compilation);
  }

  @Override
  protected void compile(final Select.LIMIT<?> limit, final Select.OFFSET<?> offset, final Compilation compilation) {
    if (limit != null) {
      compilation.append(") r WHERE ROWNUM <= ");
      if (offset != null)
        compilation.append(String.valueOf(limit.rows + offset.rows)).append(") WHERE rnum3729 > ").append(offset.rows);
      else
        compilation.append(String.valueOf(limit.rows));
    }
  }

  @Override
  protected void compile(final function.Pi function, final Compilation compilation) {
    compilation.append("ACOS(-1)");
  }

  @Override
  protected void compile(final function.Log2 function, final Compilation compilation) throws IOException {
    compilation.append("LOG(2, ");
    function.a.compile(compilation);
    compilation.append(")");
  }

  @Override
  protected void compile(final function.Log10 function, final Compilation compilation) throws IOException {
    compilation.append("LOG(10, ");
    function.a.compile(compilation);
    compilation.append(")");
  }

  @Override
  protected void compile(final expression.Temporal expression, final Compilation compilation) throws IOException {
    expression.a.compile(compilation);
    compilation.append(" ");
    final Interval interval = expression.b;
    if (interval.getUnits().size() == 1) {
      compilation.append(expression.operator.toString());
      compilation.append(" ");
      interval.compile(compilation);
    }
    else {
      for (final TemporalUnit unit : interval.getUnits()) {
        compilation.append(expression.operator.toString());
        compilation.append(" ");
        new Interval(interval.get(unit), (Interval.Unit)unit).compile(compilation);
      }
    }
  }

  @Override
  protected void compile(final Interval interval, final Compilation compilation) {
    if (interval.getUnits().size() > 1)
      throw new UnsupportedOperationException("Interval classes with only 1 Interval.Unit are supported");

    final TemporalUnit unit = interval.getUnits().iterator().next();
    if (unit == Interval.Unit.MICROS) {
      compilation.append("INTERVAL '").append(BigDecimal.valueOf(interval.get(unit)).divide(BigDecimal.valueOf(1000000l))).append("' SECOND");
    }
    else if (unit == Interval.Unit.MILLIS) {
      compilation.append("INTERVAL '").append(BigDecimal.valueOf(interval.get(unit)).divide(BigDecimal.valueOf(1000l))).append("' SECOND");
    }
    else if (unit == Interval.Unit.WEEKS) {
      compilation.append("INTERVAL '").append(interval.get(unit) * 7).append("' DAY");
    }
    else if (unit == Interval.Unit.QUARTERS) {
      compilation.append("NUMTOYMINTERVAL(").append(interval.get(unit) * 3).append(", 'MONTH')");
    }
    else if (unit == Interval.Unit.DECADES) {
      compilation.append("NUMTOYMINTERVAL(").append(interval.get(unit) * 10).append(", 'YEAR')");
    }
    else if (unit == Interval.Unit.CENTURIES) {
      compilation.append("NUMTOYMINTERVAL(").append(interval.get(unit) * 100).append(", 'YEAR')");
    }
    else if (unit == Interval.Unit.MILLENNIA) {
      compilation.append("NUMTOYMINTERVAL(").append(interval.get(unit) * 1000).append(", 'YEAR')");
    }
    else if (unit.toString().endsWith("S")) {
      compilation.append("INTERVAL '").append(interval.get(unit)).append("' ").append(unit.toString().substring(0, unit.toString().length() - 1));
    }
    else {
      throw new UnsupportedOperationException("Unsupported Interval.Unit: " + unit);
    }
  }

  @Override
  protected String compile(final type.CHAR dataType) {
    final String value = dataType.get().replace("'", "''");
    return value.length() == 0 || value.charAt(0) == ' ' ? "' " + value + "'" : "'" + value + "'";
  }

  @Override
  protected void compile(final Cast.AS as, final Compilation compilation) throws IOException {
    if (as.cast instanceof type.BINARY) {
      compilation.append("UTL_RAW.CAST_TO_RAW(");
      as.dataType.compile(compilation);
      compilation.append(")");
    }
    else if (as.cast instanceof type.BLOB) {
      compilation.append("TO_BLOB(");
      as.dataType.compile(compilation);
      compilation.append(")");
    }
    else if (as.cast instanceof type.CLOB) {
      compilation.append("TO_CLOB(");
      as.dataType.compile(compilation);
      compilation.append(")");
    }
    else if (as.cast instanceof type.DATE && !(as.dataType instanceof type.DATETIME)) {
      compilation.append("TO_DATE(");
      as.dataType.compile(compilation);
      compilation.append(", 'YYYY-MM-DD')");
    }
    else if (as.cast instanceof type.DATETIME && !(as.dataType instanceof type.DATETIME)) {
      compilation.append("TO_TIMESTAMP(");
      as.dataType.compile(compilation);
      compilation.append(", 'YYYY-MM-DD HH24:MI:SS.FF')");
    }
    else if (as.dataType instanceof type.DATETIME && as.cast instanceof type.TIME) {
      compilation.append("CAST(CASE WHEN ");
      as.dataType.compile(compilation);
      compilation.append(" IS NULL THEN NULL ELSE '+0 ' || TO_CHAR(");
      as.dataType.compile(compilation);
      compilation.append(", 'HH24:MI:SS.FF') END");
      compilation.append(" AS ").append(as.cast.declare(compilation.vendor)).append(")");
    }
    else if (as.dataType instanceof type.TIME && as.cast instanceof type.CHAR) {
      compilation.append("SUBSTR(CAST(");
      as.dataType.compile(compilation);
      compilation.append(" AS ").append(new type.CHAR(((type.CHAR)as.cast).length(), true).declare(compilation.vendor)).append("), 10, 18)");
    }
    else {
      compilation.append("CAST(");
      if (as.cast instanceof type.TIME && !(as.dataType instanceof type.TIME))
        compilation.append("'+0 ' || ");

      as.dataType.compile(compilation);
      compilation.append(" AS ").append(as.cast.declare(compilation.vendor)).append(")");
    }
  }

  @Override
  protected void setParameter(final type.CHAR dataType, final PreparedStatement statement, final int parameterIndex) throws SQLException {
    final String value = dataType.get();
    if (value != null)
      statement.setString(parameterIndex, value.length() == 0 || value.charAt(0) == ' ' ? " " + value : value);
    else
      statement.setNull(parameterIndex, dataType.sqlType());
  }

  @Override
  protected String getParameter(final type.CHAR dataType, final ResultSet resultSet, final int columnIndex) throws SQLException {
    final String value = resultSet.getString(columnIndex);
    return value != null && value.startsWith(" ") ? value.substring(1) : value;
  }

  @Override
  protected void setParameter(final type.TIME dataType, final PreparedStatement statement, final int parameterIndex) throws SQLException {
    final LocalTime value = dataType.get();
    if (value != null)
      statement.setObject(parameterIndex, newINTERVALDS("+0 " + value.format(Dialect.TIME_FORMAT)));
    else
      statement.setNull(parameterIndex, dataType.sqlType());
  }

  @Override
  protected LocalTime getParameter(final type.TIME dataType, final ResultSet resultSet, final int columnIndex) throws SQLException {
    final Object value = resultSet.getObject(columnIndex);
    return resultSet.wasNull() || value == null ? null : LocalTime.parse(value.toString().substring(value.toString().indexOf(" ") + 1), Dialect.TIME_FORMAT);
  }

  @Override
  protected void compileNextSubject(final Subject<?> subject, final int index, final Keyword<?> source, final Map<Integer,type.ENUM<?>> translateTypes, final Compilation compilation) throws IOException {
    if (source instanceof Select.SELECT && (subject instanceof ComparisonPredicate || subject instanceof BooleanTerm)) {
      compilation.append("CASE WHEN ");
      super.compileNextSubject(subject, index, source, translateTypes, compilation);
      compilation.append(" THEN 1 ELSE 0 END");
    }
    else {
      super.compileNextSubject(subject, index, source, translateTypes, compilation);
    }

    if (!(source instanceof Select.GROUP_BY) && !(subject instanceof Entity) && subject.wrapper() == null)
      compilation.append(" c" + index);
  }
}