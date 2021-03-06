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

final class DeleteImpl {
  private static abstract class Execute extends Keyword<type.DataType<?>> implements Delete.DELETE {
    protected Execute(final Keyword<type.DataType<?>> parent) {
      super(parent);
    }

    @Override
    public int[] execute(final Transaction transaction) throws IOException, SQLException {
      final DeleteCommand command = (DeleteCommand)normalize();

      final Class<? extends Schema> schema = command.delete().entities[0].schema();
      try {
        final Connection connection = transaction != null ? transaction.getConnection() : Schema.getConnection(schema);
        final DBVendor vendor = Schema.getDBVendor(connection);

        final Compilation compilation = new Compilation(command, vendor, Registry.isPrepared(schema), Registry.isBatching(schema));
        command.compile(compilation);
        final int[] count = compilation.execute(connection);
        if (transaction == null)
          connection.close();

        return count;
      }
      catch (final SQLException e) {
        throw SQLExceptionCatalog.lookup(e);
      }
    }

    @Override
    public int[] execute() throws IOException, SQLException {
      return execute(null);
    }
  }

  protected static final class WHERE extends Execute implements Delete.DELETE {
    protected final Condition<?> condition;

    protected WHERE(final Keyword<type.DataType<?>> parent, final Condition<?> condition) {
      super(parent);
      this.condition = condition;
    }

    @Override
    protected final Command normalize() {
      final DeleteCommand command = (DeleteCommand)parent().normalize();
      command.add(this);
      return command;
    }
  }

  protected static final class DELETE extends Execute implements Delete._DELETE {
    protected final type.Entity[] entities;

    protected DELETE(final type.Entity ... entities) {
      super(null);
      this.entities = entities;
    }

    @Override
    public WHERE WHERE(final Condition<?> condition) {
      return new WHERE(this, condition);
    }

    @Override
    protected final Command normalize() {
      return new DeleteCommand(this);
    }
  }
}