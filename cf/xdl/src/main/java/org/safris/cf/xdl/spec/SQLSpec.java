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

package org.safris.cf.xdl.spec;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.safris.cf.xdl.SQLDataTypes;
import org.safris.cf.xdl.xe.$xdl_bit;
import org.safris.cf.xdl.xe.$xdl_blob;
import org.safris.cf.xdl.xe.$xdl_boolean;
import org.safris.cf.xdl.xe.$xdl_char;
import org.safris.cf.xdl.xe.$xdl_column;
import org.safris.cf.xdl.xe.$xdl_date;
import org.safris.cf.xdl.xe.$xdl_dateTime;
import org.safris.cf.xdl.xe.$xdl_decimal;
import org.safris.cf.xdl.xe.$xdl_enum;
import org.safris.cf.xdl.xe.$xdl_float;
import org.safris.cf.xdl.xe.$xdl_integer;
import org.safris.cf.xdl.xe.$xdl_named;
import org.safris.cf.xdl.xe.$xdl_table;
import org.safris.cf.xdl.xe.$xdl_time;
import org.w3.x2001.xmlschema.xe.$xs_anySimpleType;

public abstract class SQLSpec {
  protected abstract String createIndex(final boolean unique, final String indexName, final String type, final String tableName, final $xdl_named ... columns);

  public List<String> triggers(final $xdl_table table) {
    return new ArrayList<String>();
  }

  public List<String> indexes(final $xdl_table table) {
    final List<String> statements = new ArrayList<String>();
    if (table._indexes() != null) {
      for (final $xdl_table._indexes._index index : table._indexes(0)._index()) {
        statements.add(createIndex(!index._unique$().isNull() && index._unique$().text(), SQLDataTypes.getIndexName(table, index), index._type$().text(), table._name$().text(), index._column().toArray(new $xdl_named[index._column().size()])));
      }
    }

    if (table._column() != null) {
      for (final $xdl_column column : table._column()) {
        if (column._index() != null) {
          statements.add(createIndex(!column._index(0)._unique$().isNull() && column._index(0)._unique$().text(), SQLDataTypes.getIndexName(table, column._index(0), column), column._index(0)._type$().text(), table._name$().text(), column));
        }
      }
    }

    return statements;
  }

  public List<String> types(final $xdl_table table) {
    return new ArrayList<String>();
  }

  protected abstract String dropIndexOnClause(final $xdl_table table);

  public List<String> drops(final $xdl_table table) {
    final List<String> statements = new ArrayList<String>();
    if (table._indexes() != null)
      for (final $xdl_table._indexes._index index : table._indexes(0)._index())
        statements.add("DROP INDEX IF EXISTS " + SQLDataTypes.getIndexName(table, index) + dropIndexOnClause(table));

    if (table._column() != null)
      for (final $xdl_column column : table._column())
        if (column._index() != null)
          statements.add("DROP INDEX IF EXISTS " + SQLDataTypes.getIndexName(table, column._index(0), column) + dropIndexOnClause(table));

    if (table._triggers() != null)
      for (final $xdl_table._triggers._trigger trigger : table._triggers().get(0)._trigger())
        for (final String action : trigger._actions$().text())
          statements.add("DROP TRIGGER IF EXISTS " + SQLDataTypes.getTriggerName(table._name$().text(), trigger, action));

    statements.add("DROP TABLE IF EXISTS " + table._name$().text());
    return statements;
  }

  public abstract String type(final $xdl_table table, final $xdl_char type);
  public abstract String type(final $xdl_table table, final $xdl_bit type);
  public abstract String type(final $xdl_table table, final $xdl_blob type);
  public abstract String type(final $xdl_table table, final $xdl_integer type);
  public abstract String type(final $xdl_table table, final $xdl_float type);
  public abstract String type(final $xdl_table table, final $xdl_decimal type);
  public abstract String type(final $xdl_table table, final $xdl_date type);
  public abstract String type(final $xdl_table table, final $xdl_time type);
  public abstract String type(final $xdl_table table, final $xdl_dateTime type);
  public abstract String type(final $xdl_table table, final $xdl_boolean type);
  public abstract String type(final $xdl_table table, final $xdl_enum type);

  // this is meant to be abstract and specific to each DB.. it's in here cause all DBs seem to be the same on this fragment
  public static final String $default(final $xdl_table table, final $xdl_column column, final $xs_anySimpleType _default) {
    return !_default.isNull() ? column instanceof $xdl_char || column instanceof $xdl_enum ? "'" + _default.text() + "'" : _default.text().toString() : "";
  }

  public String $default(final $xdl_table table, final $xdl_column column) {
    try {
      final Method method = column.getClass().getMethod("_default$");
      final $xs_anySimpleType _default = ($xs_anySimpleType)method.invoke(column);
      return $default(table, column, _default);
    }
    catch (final NoSuchMethodException e) {
      return null;
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  public abstract String $null(final $xdl_table table, final $xdl_column column);
  public abstract String $autoIncrement(final $xdl_table table, final $xdl_integer column);
}