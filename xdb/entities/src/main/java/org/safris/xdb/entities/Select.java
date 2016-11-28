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

package org.safris.xdb.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.safris.commons.lang.Pair;
import org.safris.xdb.entities.DML.ALL;
import org.safris.xdb.entities.DML.DISTINCT;
import org.safris.xdb.entities.DML.Direction;
import org.safris.xdb.entities.DML.NATURAL;
import org.safris.xdb.entities.DML.TYPE;
import org.safris.xdb.entities.spec.select;
import org.safris.xdb.schema.DBVendor;

class Select {
  private static void serialize(final List<Pair<DataType<?>,Integer>> dataTypes, final Subject<?> subject) {
    if (subject instanceof Entity) {
      final Entity entity = (Entity)subject;
      for (int i = 0; i < entity.column().length; i++)
        dataTypes.add(new Pair<DataType<?>,Integer>(entity.column()[i], i));
    }
    else if (subject instanceof DataType<?>) {
      final DataType<?> dataType = (DataType<?>)subject;
      dataTypes.add(new Pair<DataType<?>,Integer>(dataType, -1));
    }
    else {
      throw new UnsupportedOperationException("Unknown entity type: " + subject.getClass().getName());
    }
  }

  private static <B extends Subject<?>>RowIterator<B> parseResultSet(final DBVendor vendor, final Connection connection, final Statement statement, final ResultSet resultSet, final SELECT<?> select) throws SQLException {
    final List<Pair<DataType<?>,Integer>> dataTypes = new ArrayList<Pair<DataType<?>,Integer>>();
    for (final Subject<?> entity : select.entities)
      Select.serialize(dataTypes, entity);

    final int noColumns = resultSet.getMetaData().getColumnCount();
    return new RowIterator<B>() {
      private final Map<Class<? extends Entity>,Entity> prototypes = new HashMap<Class<? extends Entity>,Entity>();
      private final Map<Entity,Entity> cache = new HashMap<Entity,Entity>();
      private Entity currentTable = null;

      @Override
      @SuppressWarnings({"rawtypes", "unchecked"})
      public boolean nextRow() throws SQLException {
        if (rowIndex + 1 < rows.size()) {
          ++rowIndex;
          resetEntities();
          return true;
        }

        Subject<?>[] row;
        int index;
        Entity entity;
        try {
          if (!resultSet.next())
            return false;

          row = new Subject[select.entities.length];
          index = 0;
          entity = null;
          for (int i = 0; i < noColumns; i++) {
            final Pair<DataType<?>,Integer> dataTypePrototype = dataTypes.get(i);
            final DataType dataType;
            if (dataTypePrototype.b == -1) {
              dataType = dataTypePrototype.a.clone();
              row[index++] = dataType;
            }
            else {
              if (currentTable != null && currentTable != dataTypePrototype.a.entity) {
                final Entity cached = cache.get(entity);
                if (cached != null) {
                  row[index++] = cached;
                }
                else {
                  row[index++] = entity;
                  cache.put(entity, entity);
                  prototypes.put(entity.getClass(), entity.newInstance());
                }
              }

              currentTable = dataTypePrototype.a.entity;
              entity = prototypes.get(currentTable.getClass());
              if (entity == null)
                prototypes.put(currentTable.getClass(), entity = currentTable.newInstance());

              dataType = entity.column()[dataTypePrototype.b];
            }

            dataType.set(resultSet, i + 1);
          }
        }
        catch (final SQLException e) {
          throw SQLErrorSpecException.lookup(e, vendor);
        }

        if (entity != null) {
          final Entity cached = cache.get(entity);
          row[index++] = cached != null ? cached : entity;
        }

        rows.add((B[])row);
        ++rowIndex;
        resetEntities();
        prototypes.clear();
        currentTable = null;
        return true;
      }

      @Override
      public void close() throws SQLException {
        try {
          resultSet.close();
          statement.close();
          connection.close();
        }
        catch (final SQLException e) {
          throw SQLErrorSpecException.lookup(e, vendor);
        }
        finally {
          prototypes.clear();
          cache.clear();
          currentTable = null;
          dataTypes.clear();
          rows.clear();
        }
      }
    };
  }

  private static abstract class Execute<T extends Subject<?>> extends Keyword<T> implements select.SELECT<T> {
    @Override
    public final RowIterator<T> execute() throws SQLException {
      return execute(null);
    }

    @Override
    public RowIterator<T> execute(final Transaction transaction) throws SQLException {
      final SELECT<?> select = (SELECT<?>)getParentRoot(this);
      final Class<? extends Schema> schema = select.from().tables[0].schema();
      DBVendor vendor = null;
      try {
        final Connection connection = transaction != null ? transaction.getConnection() : Schema.getConnection(schema);
        vendor = Schema.getDBVendor(connection);
        final Serialization serialization = new Serialization(Select.class, vendor, EntityRegistry.getStatementType(schema));

        serialize(this, serialization);
        Subject.clearAliases();

        if (serialization.statementType == PreparedStatement.class) {
          final PreparedStatement statement = connection.prepareStatement(serialization.sql.toString());
          serialization.set(statement);
          final ResultSet resultSet = statement.executeQuery();
          return parseResultSet(serialization.vendor, connection, statement, resultSet, select);
        }

        if (serialization.statementType == Statement.class) {
          final Statement statement = connection.createStatement();
          final ResultSet resultSet = statement.executeQuery(serialization.sql.toString());
          return parseResultSet(serialization.vendor, connection, statement, resultSet, select);
        }

        throw new UnsupportedOperationException("Unsupported Statement prototype class: " + serialization.statementType.getName());
      }
      catch (final SQLException e) {
        throw SQLErrorSpecException.lookup(e, vendor);
      }
    }
  }

  protected static abstract class FROM_JOIN_ON<T extends Subject<?>> extends Execute<T> implements select.FROM<T> {
    protected final Keyword<T> parent;

    protected FROM_JOIN_ON(final Keyword<T> parent) {
      this.parent = parent;
    }

    @Override
    public final WHERE<T> WHERE(final Condition<?> condition) {
      return new WHERE<T>(this, condition);
    }

    @Override
    public final JOIN<T> JOIN(final Entity entity) {
      return new JOIN<T>(this, null, null, entity);
    }

    @Override
    public final JOIN<T> JOIN(final TYPE type, final Entity entity) {
      return new JOIN<T>(this, null, type, entity);
    }

    @Override
    public final JOIN<T> JOIN(final NATURAL natural, final Entity entity) {
      return new JOIN<T>(this, natural, null, entity);
    }

    @Override
    public final JOIN<T> JOIN(final NATURAL natural, final TYPE type, final Entity entity) {
      return new JOIN<T>(this, natural, type, entity);
    }

    @Override
    public final LIMIT<T> LIMIT(final int limit) {
      return new LIMIT<T>(this, limit);
    }
  }

  protected final static class FROM<T extends Subject<?>> extends FROM_JOIN_ON<T> implements select.FROM<T> {
    private final Entity[] tables;

    protected FROM(final Keyword<T> parent, final Entity ... tables) {
      super(parent);
      this.tables = tables;
    }

    @Override
    public GROUP_BY<T> GROUP_BY(final Variable<?> variable) {
      return new GROUP_BY<T>(this, variable);
    }

    @Override
    public ORDER_BY<T> ORDER_BY(final Variable<?> ... variables) {
      return new ORDER_BY<T>(this, variables);
    }

    @Override
    protected Keyword<T> parent() {
      return parent;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        parent.serialize(this, serialization);
        serialization.sql.append(" FROM ");

        // FIXME: If FROM is followed by a JOIN, then we must see what table the ON clause is
        // FIXME: referring to, because this table must be the last in the table order here
        for (int i = 0; i < tables.length; i++) {
          if (i > 0)
            serialization.sql.append(", ");

          serialization.sql.append(Subject.tableName(tables[i], serialization)).append(" ").append(Subject.tableAlias(tables[i], true));
        }
      }
      else {
        throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
      }
    }
  }

  protected final static class GROUP_BY<T extends Subject<?>> extends Execute<T> implements select.GROUP_BY<T> {
    private final Keyword<T> parent;
    private final Variable<?> variable;

    protected GROUP_BY(final Keyword<T> parent, final Variable<?> variable) {
      this.parent = parent;
      this.variable = variable;
    }

    public ORDER_BY<T> ORDER_BY(final Variable<?> ... columns) {
      return new ORDER_BY<T>(this, columns);
    }

    @Override
    public HAVING<T> HAVING(final Condition<?> condition) {
      return new HAVING<T>(this, condition);
    }

    @Override
    public LIMIT<T> LIMIT(final int limit) {
      return new LIMIT<T>(this, limit);
    }

    @Override
    protected Keyword<T> parent() {
      return parent;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        parent.serialize(this, serialization);
        serialization.sql.append(" GROUP BY ").append(Subject.columnRef(variable));
      }

      throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
    }
  }

  protected final static class HAVING<T extends Subject<?>> extends Execute<T> implements select.HAVING<T> {
    private final Keyword<T> parent;
    private final Condition<?> condition;

    protected HAVING(final Keyword<T> parent, final Condition<?> condition) {
      this.parent = parent;
      this.condition = condition;
    }

    @Override
    public ORDER_BY<T> ORDER_BY(final Variable<?> ... column) {
      return new ORDER_BY<T>(this, column);
    }

    @Override
    public LIMIT<T> LIMIT(final int limit) {
      return new LIMIT<T>(this, limit);
    }

    @Override
    protected Keyword<T> parent() {
      return parent;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        parent.serialize(this, serialization);
        serialization.sql.append(" HAVING ");
        condition.serialize(this, serialization);
        return;
      }

      throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
    }
  }

  protected final static class JOIN<T extends Subject<?>> extends FROM_JOIN_ON<T> implements select.JOIN<T> {
    private final NATURAL natural;
    private final TYPE type;
    private final Entity entity;

    protected JOIN(final Keyword<T> parent, final NATURAL natural, final TYPE type, final Entity entity) {
      super(parent);
      this.natural = natural;
      this.type = type;
      this.entity = entity;
    }

    @Override
    public ON<T> ON(final Condition<?> condition) {
      return new ON<T>(this, condition);
    }

    @Override
    public GROUP_BY<T> GROUP_BY(final Variable<?> variable) {
      return new GROUP_BY<T>(this, variable);
    }

    @Override
    public ORDER_BY<T> ORDER_BY(final Variable<?> ... variables) {
      return new ORDER_BY<T>(this, variables);
    }

    @Override
    protected Keyword<T> parent() {
      return parent;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        // NOTE: JOINed tables must have aliases. So, if the JOINed table is not part of the SELECT,
        // NOTE: it will not have had this assignment made. Therefore, ensure it's been made!
        Subject.tableAlias(entity, true);
        parent.serialize(this, serialization);
        serialization.sql.append(natural != null ? " NATURAL" : "");
        if (type != null) {
          serialization.sql.append(" ");
          type.serialize(this, serialization);
        }

        serialization.sql.append(" JOIN ").append(Subject.tableName(entity, serialization)).append(" ").append(Subject.tableAlias(entity, true));
        return;
      }

      throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
    }
  }

  protected final static class ON<T extends Subject<?>> extends FROM_JOIN_ON<T> implements select.ON<T> {
    private final Condition<?> condition;

    protected ON(final Keyword<T> parent, final Condition<?> condition) {
      super(parent);
      this.condition = condition;
    }

    @Override
    public GROUP_BY<T> GROUP_BY(final Variable<?> variable) {
      return new GROUP_BY<T>(this, variable);
    }

    @Override
    public ORDER_BY<T> ORDER_BY(final Variable<?> ... variables) {
      return new ORDER_BY<T>(this, variables);
    }

    @Override
    protected Keyword<T> parent() {
      return parent;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        parent.serialize(this, serialization);
        serialization.sql.append(" ON (");
        condition.serialize(this, serialization);
        serialization.sql.append(")");
        return;
      }

      throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
    }
  }

  protected final static class ORDER_BY<T extends Subject<?>> extends Execute<T> implements select.ORDER_BY<T> {
    private final Keyword<T> parent;
    private final Variable<?>[] columns;

    protected ORDER_BY(final Keyword<T> parent, final Variable<?> ... columns) {
      this.parent = parent;
      this.columns = columns;
    }

    @Override
    public LIMIT<T> LIMIT(final int limit) {
      return new LIMIT<T>(this, limit);
    }

    @Override
    protected Keyword<T> parent() {
      return parent;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        parent.serialize(this, serialization);
        serialization.sql.append(" ORDER BY ");
        for (int i = 0; i < columns.length; i++) {
          final Variable<?> variable = columns[i];
          if (i > 0)
            serialization.sql.append(", ");

          if (variable instanceof DataType<?>) {
            final DataType<?> dataType = (DataType<?>)variable;
            Subject.tableAlias(dataType.entity, true);
            dataType.serialize(this, serialization);
            serialization.sql.append(" ASC");
          }
          else if (variable instanceof Direction<?>) {
            ((Direction<?>)variable).serialize(this, serialization);
          }
          else {
            throw new UnsupportedOperationException("Unsupported column type: " + variable.getClass().getName());
          }
        }

        return;
      }

      throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
    }
  }

  protected final static class LIMIT<T extends Subject<?>> extends Execute<T> implements select.LIMIT<T> {
    private final Keyword<T> parent;
    private final int limit;

    protected LIMIT(final Keyword<T> parent, final int limit) {
      this.parent = parent;
      this.limit = limit;
    }

    @Override
    protected Keyword<T> parent() {
      return parent;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        parent.serialize(this, serialization);
        serialization.sql.append(" LIMIT " + limit);
        return;
      }

      throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
    }
  }

  protected final static class SELECT<T extends Subject<?>> extends Keyword<T> implements select._SELECT<T> {
    private static final Logger logger = Logger.getLogger(SELECT.class.getName());

    private final ALL all;
    private final DISTINCT distinct;
    protected final T[] entities;
    private FROM<T> from;

    @SafeVarargs
    public SELECT(final ALL all, final DISTINCT distinct, final T ... entities) {
      this.all = all;
      this.distinct = distinct;
      this.entities = entities;
    }

    @Override
    public FROM<T> FROM(final Entity ... table) {
      if (from != null)
        throw new IllegalStateException("FROM() has already been called for this SELECT object.");

      return from = new FROM<T>(this, table);
    }

    @Override
    public LIMIT<T> LIMIT(final int limit) {
      return new LIMIT<T>(this, limit);
    }

    @Override
    protected Keyword<T> parent() {
      return null;
    }

    protected FROM<T> from() {
      return from;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        serialization.sql.append("SELECT ");
        if (all != null) {
          all.serialize(this, serialization);
          serialization.sql.append(" ");
        }

        if (distinct != null) {
          distinct.serialize(this, serialization);
          serialization.sql.append(" ");
        }

        for (int i = 0; i < entities.length; i++) {
          final Subject<?> subject = entities[i];
          if (i > 0)
            serialization.sql.append(", ");

          if (subject instanceof Entity) {
            final Entity entity = (Entity)subject;
            final String alias = Subject.tableAlias(entity, true);
            final DataType<?>[] dataTypes = entity.column();
            for (int j = 0; j < dataTypes.length; j++) {
              final DataType<?> dataType = dataTypes[j];
              if (j > 0)
                serialization.sql.append(", ");

              serialization.sql.append(alias).append(".").append(dataType.name);
            }
          }
          else if (subject instanceof DataType<?>) {
            Subject.tableAlias(((DataType<?>)subject).entity, true);
            final DataType<?> dataType = (DataType<?>)subject;
            dataType.serialize(this, serialization);
          }
          else if (subject instanceof Aggregate<?>) {
            final Aggregate<?> aggregate = (Aggregate<?>)subject;
            aggregate.serialize(this, serialization);
          }
        }

        return;
      }

      throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
    }

    @Override
    public RowIterator<T> execute() throws SQLException {
      return execute(null);
    }

    @Override
    public RowIterator<T> execute(final Transaction transaction) throws SQLException {
      if (entities.length == 1) {
        final Entity entity = (Entity)this.entities[0];
        final Entity out = entity.newInstance();
        final DataType<?>[] dataTypes = entity.column();
        String sql = "SELECT ";
        String select = "";
        String where = "";
        for (final DataType<?> dataType : dataTypes) {
          if (dataType.primary)
            where += " AND " + dataType.name + " = ?";
          else
            select += ", " + dataType.name;
        }

        sql += select.substring(2) + " FROM " + entity.name() + " WHERE " + where.substring(5);
        DBVendor vendor = null;
        try {
          final Connection connection = transaction != null ? transaction.getConnection() : Schema.getConnection(entity.schema());
          vendor = Schema.getDBVendor(connection);
          final DBVendor finalVendor = vendor;
          final PreparedStatement statement = connection.prepareStatement(sql);
          int index = 0;
          for (final DataType<?> dataType : dataTypes)
            if (dataType.primary)
              dataType.get(statement, ++index);

          logger.info(statement.toString());
          try (final ResultSet resultSet = statement.executeQuery()) {
            return new RowIterator<T>() {
              @Override
              @SuppressWarnings({"rawtypes", "unchecked"})
              public boolean nextRow() throws SQLException {
                if (rowIndex + 1 < rows.size()) {
                  ++rowIndex;
                  resetEntities();
                  return true;
                }

                try {
                  if (!resultSet.next())
                    return false;

                  int index = 0;
                  for (final Variable variable : out.column())
                    variable.set(resultSet, ++index);
                }
                catch (final SQLException e) {
                  throw SQLErrorSpecException.lookup(e, finalVendor);
                }

                rows.add((T[])new Entity[] {out});
                ++rowIndex;
                resetEntities();
                return true;
              }

              @Override
              public void close() throws SQLException {
                try {
                  resultSet.close();
                  statement.close();
                  connection.close();
                }
                catch (final SQLException e) {
                  throw SQLErrorSpecException.lookup(e, finalVendor);
                }
                finally {
                  rows.clear();
                }
              }
            };
          }
        }
        catch (final SQLException e) {
          throw SQLErrorSpecException.lookup(e, vendor);
        }
      }

      Subject.clearAliases();
      return null;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
        return true;

      if (!(obj instanceof SELECT<?>))
        return false;

      final SELECT<?> that = (SELECT<?>)obj;
      return all == that.all && distinct == that.distinct && Arrays.equals(entities, that.entities);
    }

    @Override
    public int hashCode() {
      int mask = 0;
      if (all != null)
        mask |= 1;
      if (distinct != null)
        mask |= 2;

      ++mask;
      return Arrays.hashCode(entities) ^ mask;
    }
  }

  protected final static class WHERE<T extends Subject<?>> extends Execute<T> implements select.WHERE<T> {
    private final Keyword<T> parent;
    private final Condition<?> condition;

    protected WHERE(final Keyword<T> parent, final Condition<?> condition) {
      this.parent = parent;
      this.condition = condition;
    }

    @Override
    public GROUP_BY<T> GROUP_BY(final Variable<?> variable) {
      return new GROUP_BY<T>(this, variable);
    }

    @Override
    public ORDER_BY<T> ORDER_BY(final Variable<?> ... variables) {
      return new ORDER_BY<T>(this, variables);
    }

    @Override
    public LIMIT<T> LIMIT(final int limit) {
      return new LIMIT<T>(this, limit);
    }

    @Override
    protected Keyword<T> parent() {
      return parent;
    }

    @Override
    protected void serialize(final Serializable caller, final Serialization serialization) {
      if (serialization.vendor == DBVendor.MY_SQL || serialization.vendor == DBVendor.POSTGRE_SQL) {
        parent.serialize(this, serialization);
        serialization.sql.append(" WHERE ");
        condition.serialize(this, serialization);
      }
      else {
        throw new UnsupportedOperationException(serialization.vendor + " DBVendor is not supported.");
      }
    }
  }
}