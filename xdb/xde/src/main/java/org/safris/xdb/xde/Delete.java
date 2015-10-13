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

package org.safris.xdb.xde;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

class Delete {
  private static abstract class Execute<T> extends cSQL<T> {
    public int execute() throws SQLException {
      final cSQL<?> table = getParentRoot(this);
      final Class<? extends Schema> schema = ((Table)table).schema();
      final Connection connection = Schema.getConnection(schema);
      final Serialization serialization = new Serialization(Schema.getDBVendor(connection), EntityDataSources.getPrototype(schema));
      serialize(serialization);
      clearAliases();
      if (serialization.prototype == PreparedStatement.class) {
        final PreparedStatement statement = connection.prepareStatement(serialization.sql.toString());
        serialization.set(statement);
        return statement.executeUpdate();
      }
      else if (serialization.prototype == Statement.class) {
        final Statement statement = connection.createStatement();
        return statement.executeUpdate(serialization.sql.toString());
      }
      else {
        throw new UnsupportedOperationException("Unsupported Statement prototype class: " + serialization.prototype.getName());
      }
    }
  }

  protected final static class WHERE<T> extends Execute<T> implements org.safris.xdb.xde.csql.delete.DELETE<T> {
    private final cSQL<T> parent;
    private final Condition<?> condition;

    protected WHERE(final cSQL<T> parent, final Condition<?> condition) {
      this.parent = parent;
      this.condition = condition;
    }

    protected cSQL<?> parent() {
      return parent;
    }

    protected void serialize(final Serialization serialization) {
      parent.serialize(serialization);
      serialization.sql.append(" WHERE ");
      condition.serialize(serialization);
    }
  }

  protected final static class DELETE<T> extends Execute<T> implements org.safris.xdb.xde.csql.delete.DELETE_WHERE<T> {
    protected final Table table;

    protected DELETE(final Table table) {
      this.table = table;
    }

    public WHERE<T> WHERE(final Condition<?> condition) {
      return new WHERE<T>(this, condition);
    }

    protected cSQL<?> parent() {
      return table;
    }

    protected void serialize(final Serialization serialization) {
      serialization.sql.append("UPDATE ");
      serialize(table, serialization);
    }

    protected String encodeSingle(final Serialization serialization) {
      if (getClass() != DELETE.class) // means that there are subsequent clauses
        throw new Error("Need to override this");

      String sql = "UPDATE " + table.name() + " SET ";
      String columns = "";
      String where = "";
      for (final Column<?> column : table.column())
        if (column.primary)
          where += " AND " + column.name + " = ?";
        else
          columns += ", " + column.name + " = ?";

      sql += columns.substring(2) + " WHERE " + where.substring(5);
      System.out.println(sql);
      return sql;
    }

    public int execute() throws SQLException {
      if (false) {
        final cSQL<?> table = getParentRoot(this);
        final Class<? extends Schema> schema = ((Table)table).schema();
        final Connection connection = Schema.getConnection(schema);
        final Serialization serialization = new Serialization(Schema.getDBVendor(connection), EntityDataSources.getPrototype(schema));
        final String sql = encodeSingle(serialization);
        System.out.println(sql);
        if (true)
          return 0;

        final PreparedStatement statement = Schema.getConnection(((Table)table).schema()).prepareStatement(sql);
        // set the updated columns first
        int index = 0;
        for (final Column<?> column : ((Table)table).column())
          if (!column.primary)
            column.set(statement, ++index);

        // then the conditional columns
        for (final Column<?> column : ((Table)table).column())
          if (column.primary)
            column.set(statement, ++index);

        System.err.println(statement.toString());
        return statement.executeUpdate();
      }

      clearAliases();
      return super.execute();
    }
  }
}