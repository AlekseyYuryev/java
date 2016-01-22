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

package org.safris.xdb.xdl.spec;

import java.util.ArrayList;
import java.util.List;

import org.safris.xdb.xdl.$xdl_bit;
import org.safris.xdb.xdl.$xdl_blob;
import org.safris.xdb.xdl.$xdl_boolean;
import org.safris.xdb.xdl.$xdl_char;
import org.safris.xdb.xdl.$xdl_column;
import org.safris.xdb.xdl.$xdl_date;
import org.safris.xdb.xdl.$xdl_dateTime;
import org.safris.xdb.xdl.$xdl_decimal;
import org.safris.xdb.xdl.$xdl_enum;
import org.safris.xdb.xdl.$xdl_float;
import org.safris.xdb.xdl.$xdl_index;
import org.safris.xdb.xdl.$xdl_integer;
import org.safris.xdb.xdl.$xdl_table;
import org.safris.xdb.xdl.$xdl_time;
import org.safris.xdb.xdl.SQLDataTypes;

public class PostreSQLSpec extends SQLSpec {
  public List<String> drops(final $xdl_table table) {
    final List<String> statements = super.drops(table);
    if (table._column() != null) {
      for (final $xdl_column column : table._column()) {
        if (column instanceof $xdl_enum) {
          statements.add("DROP TYPE IF EXISTS " + SQLDataTypes.getTypeName(table._name$().text(), (($xdl_enum)column)._name$().text()));
        }
        else if (column instanceof $xdl_integer) {
          final $xdl_integer type = ($xdl_integer)column;
          if (!type._generateOnInsert$().isNull() && $xdl_integer._generateOnInsert$.AUTO_5FINCREMENT.text().equals(type._generateOnInsert$().text()))
            statements.add("DROP SEQUENCE " + SQLDataTypes.getSequenceName(table, type));
        }
      }
    }

    return statements;
  }

  public List<String> types(final $xdl_table table) {
    final List<String> statements = new ArrayList<String>();
    if (table._column() != null) {
      for (final $xdl_column column : table._column()) {
        if (column instanceof $xdl_enum) {
          final $xdl_enum type = ($xdl_enum)column;
          String sql = "CREATE TYPE " + SQLDataTypes.getTypeName(table._name$().text(), type._name$().text()) + " AS ENUM (";
          if (!type._values$().isNull()) {
            String values = "";
            for (final String value : type._values$().text())
              values += ", '" + SQLDataTypes.toEnumValue(value) + "'";

            sql += values.substring(2);
          }

          sql += ")";
          statements.add(0, sql);
        }
        else if (column instanceof $xdl_integer) {
          final $xdl_integer type = ($xdl_integer)column;
          if (!type._generateOnInsert$().isNull() && $xdl_integer._generateOnInsert$.AUTO_5FINCREMENT.text().equals(type._generateOnInsert$().text()))
            statements.add(0, "CREATE SEQUENCE " + SQLDataTypes.getSequenceName(table, type));
        }
      }
    }

    statements.addAll(super.types(table));
    return statements;
  }

  public String type(final $xdl_table table, final $xdl_char type) {
    return (type._variant$().text() ? "VARCHAR" : "CHAR") + "(" + type._length$().text() + ")";
  }

  public String type(final $xdl_table table, final $xdl_bit type) {
    String sql = "BIT";
    if (type._variant$().text())
      sql += " VARYING";

    sql += "(" + type._length$().text() + ")";
    return sql;
  }

  public final String type(final $xdl_table table, final $xdl_blob type) {
    return "BLOB(" + type._length$().text() + ")";
  }

  public String type(final $xdl_table table, final $xdl_integer type) {
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

  public String type(final $xdl_table table, final $xdl_float type) {
    return (type._double$().text() ? "DOUBLE PRECISION" : "REAL") + "(" + type._precision$().text() + ")";
  }

  public String type(final $xdl_table table, final $xdl_decimal type) {
    SQLDataTypes.checkValidNumber(type._name$().text(), type._precision$().text(), type._decimal$().text());
    return "DECIMAL(" + type._precision$().text() + ", " + type._decimal$().text() + ")";
  }

  public String type(final $xdl_table table, final $xdl_date type) {
    return "DATE";
  }

  public String type(final $xdl_table table, final $xdl_time type) {
    return "TIME";
  }

  public String type(final $xdl_table table, final $xdl_dateTime type) {
    return "TIMESTAMP(" + type._precision$().text() + ")";
  }

  public String type(final $xdl_table table, final $xdl_boolean type) {
    return "BOOLEAN";
  }

  public String type(final $xdl_table table, final $xdl_enum type) {
    return SQLDataTypes.getTypeName(table._name$().text(), (($xdl_enum)type)._name$().text());
  }

  public String $null(final $xdl_table table, final $xdl_column column) {
    return !column._null$().isNull() ? !column._null$().text() ? "NOT NULL" : "NULL" : "";
  }

  public String $autoIncrement(final $xdl_table table, final $xdl_integer column) {
    return !column._generateOnInsert$().isNull() && $xdl_integer._generateOnInsert$.AUTO_5FINCREMENT.text().equals(column._generateOnInsert$().text()) ? "DEFAULT nextval('" + SQLDataTypes.getSequenceName(table, column) + "')" : "";
  }

  protected boolean canHaveUniqueIndexes(final $xdl_index._type$ type) {
    return !$xdl_index._type$.HASH.text().equals(type.text());
  }
}