/* Copyright (c) 2008 lib4j
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

package org.libx4j.dbcp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.lib4j.dbcp.Dbcp;
import org.lib4j.dbcp.xe.$dbcp_dbcp;
import org.lib4j.dbcp.xe.dbcp_dbcp;
import org.lib4j.xml.datatypes.xe.$dt_stringNonEmpty;

/**
 * This implementation has been deprecated in favor of its JAXB equivalent.
 * @see org.lib4j.dbcp.DataSources
 */
@Deprecated
public final class DataSources {
  /**
   * Create a <code>BasicDataSource</code> given a list of dbcp XSB bindings.
   * <code>ClassLoader.getSystemClassLoader()</code> is used as the <code>driverClassLoader</code> parameter.
   *
   * @param dbcp The XSB binding.
   * @param name The name of the pool to create. (The name is declared in the list of <code>dbcps</code>).
   * @return the <code>BasicDataSource</code> instance.
   * @throws SQLException If a database access error occurs.
   */
  public static BasicDataSource createDataSource(final List<$dbcp_dbcp> dbcps, final String name) throws SQLException {
    return createDataSource(dbcps, name, ClassLoader.getSystemClassLoader());
  }

  /**
   * Create a <code>BasicDataSource</code> given a list of dbcp XSB bindings.
   *
   * @param dbcp The XSB binding.
   * @param name The name of the pool to create. (The name is declared in the list of <code>dbcps</code>).
   * @param driverClassLoader Class loader to be used to load the JDBC driver.
   * @return the <code>BasicDataSource</code> instance.
   * @throws SQLException If a database access error occurs.
   */
  public static BasicDataSource createDataSource(final List<$dbcp_dbcp> dbcps, final String name, final ClassLoader driverClassLoader) throws SQLException {
    for (final $dbcp_dbcp dbcp : dbcps)
      if (name.equals(dbcp._name$().text()))
        return createDataSource(dbcp, driverClassLoader);

    return null;
  }

  /**
   * Create a <code>BasicDataSource</code> given a dbcp XSB binding.
   * <code>ClassLoader.getSystemClassLoader()</code> is used as the <code>driverClassLoader</code> parameter.
   *
   * @param dbcp The XSB binding.
   * @return the <code>BasicDataSource</code> instance.
   * @throws SQLException If a database access error occurs.
   */
  public static BasicDataSource createDataSource(final $dbcp_dbcp dbcp) throws SQLException {
    return createDataSource(dbcp, ClassLoader.getSystemClassLoader());
  }

  /**
   * Create a <code>BasicDataSource</code> given a dbcp XSB binding.
   *
   * @param dbcp The XSB binding.
   * @param driverClassLoader Class loader to be used to load the JDBC driver.
   * @return the <code>BasicDataSource</code> instance.
   * @throws SQLException If a database access error occurs.
   */
  public static BasicDataSource createDataSource(final $dbcp_dbcp dbcp, final ClassLoader driverClassLoader) throws SQLException {
    if (dbcp.isNull())
      throw new IllegalArgumentException("dbcp.isNull() == true");

    final dbcp_dbcp._jdbc jdbc = dbcp._jdbc(0);
    if (jdbc.isNull())
      throw new IllegalArgumentException("Missing required value for '/dbcp:jdbc'");

    if (jdbc._driverClassName(0).isNull())
      throw new IllegalArgumentException("Missing required value for '/dbcp:jdbc/dbcp:driverClassName'");

    final Dbcp jaxbDbcp = new Dbcp();
    jaxbDbcp.jdbc = new Dbcp.Jdbc();
    jaxbDbcp.jdbc.driverClassName = jdbc._driverClassName(0).text();
    jaxbDbcp.jdbc.url = jdbc._url(0).text();
    jaxbDbcp.jdbc.username = jdbc._username(0).text();
    jaxbDbcp.jdbc.password = jdbc._password(0).text();

    final dbcp_dbcp._default _default = dbcp._default(0);
    if (!_default.isNull()) {
      jaxbDbcp._default = new Dbcp.Default();
      if (!_default._catalog(0).isNull())
        jaxbDbcp._default.catalog = _default._catalog(0).text();

      if (!_default._autoCommit(0).isNull())
        jaxbDbcp._default.autoCommit = _default._autoCommit(0).text();

      if (!_default._readOnly(0).isNull())
        jaxbDbcp._default.readOnly = _default._readOnly(0).text();

      if (!_default._queryTimeout(0).isNull())
        jaxbDbcp._default.queryTimeout = _default._queryTimeout(0).text();

      if (!_default._transactionIsolation(0).isNull()) {
        if (dbcp_dbcp._default._transactionIsolation.NONE.text().equals(_default._transactionIsolation(0).text()))
          jaxbDbcp._default.transactionIsolation = Dbcp.Default.TransactionIsolation.NONE;
        else if (dbcp_dbcp._default._transactionIsolation.READ_5FCOMMITTED.text().equals(_default._transactionIsolation(0).text()))
          jaxbDbcp._default.transactionIsolation = Dbcp.Default.TransactionIsolation.READ_COMMITTED;
        else if (dbcp_dbcp._default._transactionIsolation.READ_5FUNCOMMITTED.text().equals(_default._transactionIsolation(0).text()))
          jaxbDbcp._default.transactionIsolation = Dbcp.Default.TransactionIsolation.READ_UNCOMMITTED;
        else if (dbcp_dbcp._default._transactionIsolation.REPEATABLE_5FREAD.text().equals(_default._transactionIsolation(0).text()))
          jaxbDbcp._default.transactionIsolation = Dbcp.Default.TransactionIsolation.REPEATABLE_READ;
        else if (dbcp_dbcp._default._transactionIsolation.SERIALIZABLE.text().equals(_default._transactionIsolation(0).text()))
          jaxbDbcp._default.transactionIsolation = Dbcp.Default.TransactionIsolation.SERIALIZABLE;
        else
          throw new UnsupportedOperationException("Unsupported transaction isolation: " + _default._transactionIsolation(0).text());
      }
    }

    final dbcp_dbcp._connection connection = dbcp._connection(0);
    if (!connection.isNull()) {
      jaxbDbcp.connection = new Dbcp.Connection();
      if (!connection._properties(0).isNull()) {
        jaxbDbcp.connection.properties = new Dbcp.Connection.Properties();
        jaxbDbcp.connection.properties.property = new ArrayList<Dbcp.Connection.Properties.Property>();
        for (final dbcp_dbcp._connection._properties._property property : connection._properties(0)._property())
          if (property._name$() != null && property._name$().text() != null && property._value$() != null && property._value$().text() != null)
            jaxbDbcp.connection.properties.property.add(new Dbcp.Connection.Properties.Property(property._name$().text(), property._value$().text()));
      }

      if (!connection._initSqls(0).isNull()) {
        jaxbDbcp.connection.initSqls = new Dbcp.Connection.InitSqls();
        jaxbDbcp.connection.initSqls.initSql = new ArrayList<String>();
        for (final $dt_stringNonEmpty initSql : connection._initSqls(0)._initSql())
          jaxbDbcp.connection.initSqls.initSql.add(initSql.text());
      }
    }

    final dbcp_dbcp._size size = dbcp._size(0);
    if (!size.isNull()) {
      jaxbDbcp.size = new Dbcp.Size();
      if (!size._initialSize(0).isNull())
        jaxbDbcp.size.initialSize = size._initialSize(0).text().intValue();

      if (!size._maxTotal(0).isNull())
        jaxbDbcp.size.maxTotal = size._maxTotal(0).text();

      if (!size._maxIdle(0).isNull())
        jaxbDbcp.size.maxIdle = size._maxIdle(0).text();

      if (!size._minIdle(0).isNull())
        jaxbDbcp.size.minIdle = size._minIdle(0).text();

      if (!size._maxOpenPreparedStatements(0).isNull())
        jaxbDbcp.size.maxOpenPreparedStatements = size._maxOpenPreparedStatements(0).text();
    }

    final dbcp_dbcp._pool pool = dbcp._pool(0);
    if (!pool.isNull()) {
      jaxbDbcp.pool = new Dbcp.Pool();
      if (!pool._queue(0).isNull())
        jaxbDbcp.pool.queue = Dbcp.Pool.Queue.valueOf(pool._queue(0).text());

      if (!pool._cacheState(0).isNull())
        jaxbDbcp.pool.cacheState = pool._cacheState(0).text();

      if (!pool._maxWait(0).isNull())
        jaxbDbcp.pool.maxWait = pool._maxWait(0).text();

      if (!pool._maxConnectionLifetime(0).isNull())
        jaxbDbcp.pool.maxConnectionLifetime = pool._maxConnectionLifetime(0).text();

      if (!pool._enableAutoCommitOnReturn(0).isNull())
        jaxbDbcp.pool.enableAutoCommitOnReturn = pool._enableAutoCommitOnReturn(0).text();

      if (!pool._rollbackOnReturn(0).isNull())
        jaxbDbcp.pool.rollbackOnReturn = pool._rollbackOnReturn(0).text();

      if (!pool._enableAutoCommitOnReturn(0).isNull())
        jaxbDbcp.pool.enableAutoCommitOnReturn = pool._enableAutoCommitOnReturn(0).text();

      if (!pool._removeAbandoned(0).isNull()) {
        jaxbDbcp.pool.removeAbandoned = new Dbcp.Pool.RemoveAbandoned();
        jaxbDbcp.pool.removeAbandoned.on = Dbcp.Pool.RemoveAbandoned.On.valueOf(pool._removeAbandoned(0)._on$().text());
        jaxbDbcp.pool.removeAbandoned.timeout = pool._removeAbandoned(0)._timeout$().text();
      }

      if (!pool._abandonedUsageTracking(0).isNull())
        jaxbDbcp.pool.abandonedUsageTracking = pool._abandonedUsageTracking(0).text();

      if (!pool._allowAccessToUnderlyingConnection(0).isNull())
        jaxbDbcp.pool.allowAccessToUnderlyingConnection = pool._allowAccessToUnderlyingConnection(0).text();

      final dbcp_dbcp._pool._eviction eviction = pool._eviction(0);
      if (!eviction.isNull()) {
        jaxbDbcp.pool.eviction = new Dbcp.Pool.Eviction();
        if (!eviction._timeBetweenRuns(0).isNull())
          jaxbDbcp.pool.eviction.timeBetweenRuns = eviction._timeBetweenRuns(0).text();

        if (!eviction._numTestsPerRun(0).isNull())
          jaxbDbcp.pool.eviction.numTestsPerRun = eviction._numTestsPerRun(0).text();

        if (!eviction._minIdleTime(0).isNull())
          jaxbDbcp.pool.eviction.minIdleTime = eviction._minIdleTime(0).text();

        if (!eviction._softMinIdleTime(0).isNull())
          jaxbDbcp.pool.eviction.softMinIdleTime = eviction._softMinIdleTime(0).text();

        if (!eviction._policyClassName(0).isNull())
          jaxbDbcp.pool.eviction.policyClassName = eviction._policyClassName(0).text();
      }
    }

    final dbcp_dbcp._validation validation = dbcp._validation(0);
    if (!validation.isNull()) {
      jaxbDbcp.validation = new Dbcp.Validation();
      if (!validation._query(0).isNull())
        jaxbDbcp.validation.query = validation._query(0).text();

      if (!validation._testOnBorrow(0).isNull())
        jaxbDbcp.validation.testOnBorrow = validation._testOnBorrow(0).text();

      if (!validation._testOnReturn(0).isNull())
        jaxbDbcp.validation.testOnReturn = validation._testOnReturn(0).text();

      if (!validation._testWhileIdle(0).isNull())
        jaxbDbcp.validation.testWhileIdle = validation._testWhileIdle(0).text();

      if (!validation._fastFail(0).isNull()) {
        jaxbDbcp.validation.fastFail = new Dbcp.Validation.FastFail();
        if (!validation._fastFail(0)._disconnectionSqlCodes(0).isNull())
          jaxbDbcp.validation.fastFail.disconnectionSqlCodes = validation._fastFail(0)._disconnectionSqlCodes(0).text();
      }
    }

    final dbcp_dbcp._logging logging = dbcp._logging(0);
    if (!logging.isNull()) {
      jaxbDbcp.logging = new Dbcp.Logging();
      jaxbDbcp.logging.level = Dbcp.Logging.Level.valueOf(logging._level(0).text());
      if (!logging._logExpiredConnections(0).isNull())
        jaxbDbcp.logging.logExpiredConnections = logging._logExpiredConnections(0).text();

      if (!logging._logAbandoned(0).isNull())
        jaxbDbcp.logging.logAbandoned = logging._logAbandoned(0).text();
    }

    return org.lib4j.dbcp.DataSources.createDataSource(jaxbDbcp);
  }

  private DataSources() {
  }
}