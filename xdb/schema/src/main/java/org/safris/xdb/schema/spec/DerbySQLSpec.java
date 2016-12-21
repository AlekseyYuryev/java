/* Copyright (c) 2015 Seva Safris
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

package org.safris.xdb.schema.spec;

import org.safris.xdb.schema.SQLDataTypes;
import org.safris.xdb.xds.xe.$xds_binary;
import org.safris.xdb.xds.xe.$xds_blob;
import org.safris.xdb.xds.xe.$xds_boolean;
import org.safris.xdb.xds.xe.$xds_char;
import org.safris.xdb.xds.xe.$xds_clob;
import org.safris.xdb.xds.xe.$xds_columnCommon;
import org.safris.xdb.xds.xe.$xds_date;
import org.safris.xdb.xds.xe.$xds_dateTime;
import org.safris.xdb.xds.xe.$xds_decimal;
import org.safris.xdb.xds.xe.$xds_enum;
import org.safris.xdb.xds.xe.$xds_float;
import org.safris.xdb.xds.xe.$xds_integer;
import org.safris.xdb.xds.xe.$xds_named;
import org.safris.xdb.xds.xe.$xds_table;
import org.safris.xdb.xds.xe.$xds_time;

public final class DerbySQLSpec extends SQLSpec {
  @Override
  public String type(final $xds_table table, final $xds_char type) {
    return (type._national$().text() ? "N" : "") + (type._varying$().text() ? "VARCHAR" : "CHAR") + "(" + type._length$().text() + ")";
  }

  @Override
  public String type(final $xds_table table, final $xds_clob type) {
    return (type._national$().text() ? "NCLOB" : "CLOB") + "(" + type._length$().text() + ")";
  }

  @Override
  public String type(final $xds_table table, final $xds_binary type) {
    return "BIT" + (type._varying$().text() ? " VARYING" : "") + "(" + type._length$().text() + ")";
  }

  @Override
  public String type(final $xds_table table, final $xds_blob type) {
    return "BLOB" + "(" + type._length$().text() + ")";
  }

  @Override
  public String type(final $xds_table table, final $xds_integer type) {
    final int noBytes = SQLDataTypes.getNumericByteCount(type._precision$().text(), false, type._min$().text(), type._max$().text());
    if (noBytes == 1) // 2^8 = 256
      return "SMALLINT";

    if (noBytes == 2) // 2^16 = 65536
      return "SMALLINT";

    if (noBytes == 3) // 2^24 = 16777216
      return "INTEGER";

    if (noBytes == 4) // 2^32 = 4294967296
      return "INTEGER";

    return "BIGINT";
  }

  @Override
  public String type(final $xds_table table, final $xds_float type) {
    return (type._double$().text() ? "DOUBLE" : "FLOAT") + "(" + type._precision$().text() + ")";
  }

  @Override
  public String type(final $xds_table table, final $xds_decimal type) {
    SQLDataTypes.checkValidNumber(type._name$().text(), type._precision$().text(), type._decimal$().text());
    return "DECIMAL" + "(" + type._precision$().text() + ", " + type._decimal$().text() + ")";
  }

  @Override
  public String type(final $xds_table table, final $xds_date type) {
    return "DATE";
  }

  @Override
  public String type(final $xds_table table, final $xds_time type) {
    return "TIME";
  }

  @Override
  public String type(final $xds_table table, final $xds_dateTime type) {
    return "TIMESTAMP";
  }

  @Override
  public String type(final $xds_table table, final $xds_boolean type) {
    return "BOOLEAN";
  }

  @Override
  public String type(final $xds_table table, final $xds_enum type) {
    int maxLength = 0;
    if (!type._values$().isNull())
      for (final String value : type._values$().text())
        maxLength = Math.max(maxLength, value.length());

    return "VARCHAR(" + maxLength + ")";
  }

  @Override
  public String $null(final $xds_table table, final $xds_columnCommon column) {
    return !column._null$().isNull() && !column._null$().text() ? "NOT NULL" : "";
  }

  @Override
  public String $autoIncrement(final $xds_table table, final $xds_integer column) {
    return !column._generateOnInsert$().isNull() && $xds_integer._generateOnInsert$.AUTO_5FINCREMENT.text().equals(column._generateOnInsert$().text()) ? $xds_integer._generateOnInsert$.AUTO_5FINCREMENT.text() : "";
  }

  @Override
  protected String dropIndexOnClause(final $xds_table table) {
    return " ON " + table._name$().text();
  }

  @Override
  protected String createIndex(final boolean unique, final String indexName, final String type, final String tableName, final $xds_named ... columns) {
    return "CREATE " + (unique ? "UNIQUE " : "") + "INDEX " + indexName + " USING " + type + " ON " + tableName + " (" + SQLDataTypes.csvNames(columns) + ")";
  }
}