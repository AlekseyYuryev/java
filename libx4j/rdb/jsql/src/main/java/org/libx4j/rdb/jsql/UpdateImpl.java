/* Copyright (c) 2015 lib4j
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
import java.sql.Connection;
import java.sql.SQLException;

import org.lib4j.sql.exception.SQLExceptionCatalog;
import org.libx4j.rdb.vendor.DBVendor;

final class UpdateImpl {
  private static abstract class Execute extends Keyword<type.DataType<?>> implements Update.UPDATE {
    protected Execute(final Keyword<type.DataType<?>> parent) {
      super(parent);
    }

    /**
     * Executes the SQL statement in this <code>XDE</code> object.
     *
     * @return the row modification count
     * @exception SQLException if a database access error occurs
     */
    @Override
    public int[] execute(final Transaction transaction) throws IOException, SQLException {
      final UpdateCommand command = (UpdateCommand)normalize();
      final Class<? extends Schema> schema = command.update().entities[0].schema();
      Compilation compilation = null;
      try {
        final Connection connection = transaction != null ? transaction.getConnection() : Schema.getConnection(schema);
        final DBVendor vendor = Schema.getDBVendor(connection);

        compilation = new Compilation(command, vendor, Registry.isPrepared(schema), Registry.isBatching(schema));
        command.compile(compilation);
        final int[] count = compilation.execute(connection);
        compilation.afterExecute(true);
        if (transaction == null)
          connection.close();

        return count;
      }
      catch (final SQLException e) {
        if (compilation != null)
          compilation.afterExecute(false);

        throw SQLExceptionCatalog.lookup(e);
      }
    }

    @Override
    public int[] execute() throws IOException, SQLException {
      return execute(null);
    }
  }

  private static abstract class UPDATE_SET extends Execute implements Update._SET {
    protected UPDATE_SET(final Keyword<type.DataType<?>> parent) {
      super(parent);
    }

    @Override
    public final <T>SET SET(final type.DataType<? extends T> column, final type.DataType<? extends T> to) {
      return new SET(this, column, to);
    }

    @Override
    public final <T>SET SET(final type.DataType<T> column, final T to) {
      final type.DataType<T> wrap = type.DataType.wrap(to);
      return new SET(this, column, wrap);
    }
  }

  protected static final class UPDATE extends UPDATE_SET implements Update._SET {
    protected final type.Entity[] entities;

    protected UPDATE(final type.Entity ... entities) {
      super(null);
      this.entities = entities;
    }

    @Override
    protected final Command normalize() {
      return new UpdateCommand(this);
    }
  }

  protected static final class SET extends UPDATE_SET implements Update.SET {
    protected final type.DataType<?> column;
    protected final Compilable to;

    protected <T>SET(final Keyword<type.DataType<?>> parent, final type.DataType<? extends T> column, final Case.CASE<? extends T> to) {
      super(parent);
      this.column = column;
      this.to = (Provision)to;
    }

    protected <T>SET(final Keyword<type.DataType<?>> parent, final type.DataType<? extends T> column, final type.DataType<? extends T> to) {
      super(parent);
      this.column = column;
      this.to = to;
    }

    @Override
    public WHERE WHERE(final Condition<?> condition) {
      return new WHERE(this, condition);
    }

    @Override
    protected final Command normalize() {
      final UpdateCommand command = (UpdateCommand)parent().normalize();
      command.add(this);
      return command;
    }
  }

  protected static final class WHERE extends Execute implements Update.UPDATE {
    protected final Condition<?> condition;

    protected WHERE(final Keyword<type.DataType<?>> parent, final Condition<?> condition) {
      super(parent);
      this.condition = condition;
    }

    @Override
    protected final Command normalize() {
      final UpdateCommand command = (UpdateCommand)parent().normalize();
      command.add(this);
      return command;
    }
  }
}