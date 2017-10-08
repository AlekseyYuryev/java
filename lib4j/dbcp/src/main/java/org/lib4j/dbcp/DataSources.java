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

package org.lib4j.dbcp;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.lib4j.lang.Resources;
import org.lib4j.logging.LoggerPrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.xml.sax.SAXException;

public final class DataSources {
  private static final String INDEFINITE = "INDEFINITE";
  private static Schema schema;

  /**
   * Create a <code>BasicDataSource</code> given a dbcp JAXB binding.
   * <code>ClassLoader.getSystemClassLoader()</code> is used as the <code>driverClassLoader</code> parameter.
   *
   * @param dbcpXml URL of dbcp xml resource.
   * @return the <code>BasicDataSource</code> instance.
   * @throws SQLException If a database access error occurs.
   * @throws SAXException If a XML validation error occurs.
   */
  public static BasicDataSource createDataSource(final URL dbcpXml) throws SQLException, SAXException {
    return createDataSource(dbcpXml, ClassLoader.getSystemClassLoader());
  }

  /**
   * Create a <code>BasicDataSource</code> given a dbcp JAXB binding.
   *
   * @param dbcp JAXB dbcp binding.
   * @return the <code>BasicDataSource</code> instance.
   * @throws SQLException If a database access error occurs.
   */
  public static BasicDataSource createDataSource(final Dbcp dbcp) throws SQLException {
    return createDataSource(dbcp, ClassLoader.getSystemClassLoader());
  }

  /**
   * Create a <code>BasicDataSource</code> given a dbcp JAXB binding.
   *
   * @param dbcpXml URL of dbcp xml resource.
   * @param driverClassLoader Class loader to be used to load the JDBC driver.
   * @return the <code>BasicDataSource</code> instance.
   * @throws SQLException If a database access error occurs.
   * @throws SAXException If a XML validation error occurs.
   */
  public static BasicDataSource createDataSource(final URL dbcpXml, final ClassLoader driverClassLoader) throws SQLException, SAXException {
    try {
      final Unmarshaller unmarshaller = JAXBContext.newInstance(Dbcp.class).createUnmarshaller();
      unmarshaller.setSchema(DataSources.schema == null ? DataSources.schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(Resources.getResource("dbcp.xsd").getURL()) : DataSources.schema);
      return createDataSource((Dbcp)unmarshaller.unmarshal(dbcpXml), driverClassLoader);
    }
    catch (final JAXBException e) {
      throw new SAXException(e);
    }
  }

  /**
   * Create a <code>BasicDataSource</code> given a dbcp JAXB binding.
   *
   * @param dbcp JAXB dbcp binding.
   * @param driverClassLoader Class loader to be used to load the JDBC driver.
   * @return the <code>BasicDataSource</code> instance.
   * @throws SQLException If a database access error occurs.
   */
  public static BasicDataSource createDataSource(final Dbcp dbcp, final ClassLoader driverClassLoader) throws SQLException {
    if (dbcp == null)
      throw new NullPointerException("dbcp == null");

    final BasicDataSource dataSource = new BasicDataSource();

    final Dbcp.Jdbc jdbc = dbcp.jdbc;
    dataSource.setDriverClassName(jdbc.driverClassName);
    dataSource.setDriverClassLoader(driverClassLoader);

    dataSource.setUrl(jdbc.url);

    dataSource.setUsername(jdbc.username);
    dataSource.setPassword(jdbc.password);

    final Dbcp.Default _default = dbcp._default;
    if (_default != null && _default.catalog != null)
      dataSource.setDefaultCatalog(_default.catalog);

    dataSource.setDefaultAutoCommit(_default == null || _default.autoCommit == null || _default.autoCommit);
    dataSource.setDefaultReadOnly(_default != null && _default.readOnly != null && _default.readOnly);
    if (_default != null && _default.queryTimeout != null)
      dataSource.setDefaultQueryTimeout(_default.queryTimeout);

    if (_default != null && _default.transactionIsolation != null)
      dataSource.setDefaultTransactionIsolation(_default.transactionIsolation.getConstant());

    final Dbcp.Connection connection = dbcp.connection;
    if (connection != null) {
      if (connection.properties != null)
        for (final Dbcp.Connection.Properties.Property property : connection.properties.property)
          if (property.name != null && property.value != null)
            dataSource.addConnectionProperty(property.name, property.value);

      if (connection.initSqls != null) {
        final List<String> initSqls = new ArrayList<String>();
        for (final String initSql : connection.initSqls.initSql)
          initSqls.add(initSql);

        dataSource.setConnectionInitSqls(initSqls);
      }
    }

    final Dbcp.Size size = dbcp.size;
    dataSource.setInitialSize(size == null || size.initialSize == null ? 0 : size.initialSize);
    dataSource.setMaxTotal(size == null || size.maxTotal == null ? 8 : INDEFINITE.equals(size.maxTotal) ? -1 : Integer.parseInt(size.maxTotal));
    dataSource.setMaxIdle(size == null || size.maxIdle == null ? 8 : INDEFINITE.equals(size.maxIdle) ? -1 : Integer.parseInt(size.maxIdle));
    dataSource.setMinIdle(size == null || size.minIdle == null ? 9 : size.minIdle);
    if (size == null || size.maxOpenPreparedStatements == null || INDEFINITE.equals(size.maxOpenPreparedStatements)) {
      dataSource.setPoolPreparedStatements(false);
    }
    else {
      dataSource.setPoolPreparedStatements(true);
      dataSource.setMaxOpenPreparedStatements(Integer.parseInt(size.maxOpenPreparedStatements));
    }

    final Dbcp.Pool pool = dbcp.pool;
    if (pool == null || pool.queue == null || pool.queue == Dbcp.Pool.Queue.lifo)
      dataSource.setLifo(true);
    else if (pool.queue == Dbcp.Pool.Queue.fifo)
      dataSource.setLifo(false);
    else
      throw new UnsupportedOperationException("Unsupported queue spec: " + pool.queue);

    dataSource.setCacheState(pool != null && pool.cacheState != null && pool.cacheState);
    dataSource.setMaxWaitMillis(pool == null || pool.maxWait != null || INDEFINITE.equals(pool.maxWait) ? -1 : Long.parseLong(pool.maxWait));
    dataSource.setMaxConnLifetimeMillis(pool == null || pool.maxConnectionLifetime == null || INDEFINITE.equals(pool.maxConnectionLifetime) ? 0 : Long.parseLong(pool.maxConnectionLifetime));
    dataSource.setEnableAutoCommitOnReturn(_default == null || pool.enableAutoCommitOnReturn == null || pool.enableAutoCommitOnReturn);
    dataSource.setRollbackOnReturn(pool == null || pool.rollbackOnReturn == null || pool.rollbackOnReturn);
    if (pool != null && pool.removeAbandoned != null) {
      if (pool.removeAbandoned.on == Dbcp.Pool.RemoveAbandoned.On.borrow)
        dataSource.setRemoveAbandonedOnBorrow(true);
      else if (pool.removeAbandoned.on == Dbcp.Pool.RemoveAbandoned.On.maintenance)
        dataSource.setRemoveAbandonedOnMaintenance(true);
      else
        throw new UnsupportedOperationException("Unsupported remove abandoned spec: " + pool.removeAbandoned.on);

      dataSource.setRemoveAbandonedTimeout(pool.removeAbandoned.timeout);
    }

    dataSource.setAbandonedUsageTracking(pool != null && pool.abandonedUsageTracking != null && pool.abandonedUsageTracking);
    dataSource.setAccessToUnderlyingConnectionAllowed(pool != null && pool.allowAccessToUnderlyingConnection != null && pool.allowAccessToUnderlyingConnection);

    final Dbcp.Pool.Eviction evictor = pool != null && pool.eviction != null ? pool.eviction : null;
    if (evictor != null) {
      dataSource.setTimeBetweenEvictionRunsMillis(evictor.timeBetweenRuns);
      dataSource.setNumTestsPerEvictionRun(evictor.numTestsPerRun);
      dataSource.setMinEvictableIdleTimeMillis(evictor.minIdleTime == null ? 1800000 : evictor.minIdleTime);
      dataSource.setSoftMinEvictableIdleTimeMillis(evictor.softMinIdleTime == null || INDEFINITE.equals(evictor.softMinIdleTime) ? -1 : Long.parseLong(evictor.softMinIdleTime));
      if (evictor.policyClassName != null)
        dataSource.setEvictionPolicyClassName(evictor.policyClassName);
    }

    final Dbcp.Validation validation = dbcp.validation;
    if (validation != null && validation.query != null)
      dataSource.setValidationQuery(validation.query);

    dataSource.setTestOnBorrow(validation == null || validation.testOnBorrow == null || validation.testOnBorrow);
    dataSource.setTestOnReturn(validation != null && validation.testOnReturn != null && validation.testOnReturn);
    dataSource.setTestWhileIdle(validation != null && validation.testWhileIdle != null && validation.testWhileIdle);
    if (validation != null && validation.fastFail != null) {
      dataSource.setFastFailValidation(true);
      if (validation.fastFail.disconnectionSqlCodes != null)
        dataSource.setDisconnectionSqlCodes(Arrays.asList(validation.fastFail.disconnectionSqlCodes.split(" ")));
    }

    final Dbcp.Logging logging = dbcp.logging;
    if (logging != null) {
      final Logger logger = LoggerFactory.getLogger(DataSources.class);
      final LoggerPrintWriter loggerPrintWriter = new LoggerPrintWriter(logger, Level.valueOf(logging.level.toString()));
      dataSource.setLogWriter(loggerPrintWriter);
      dataSource.setLogExpiredConnections(logging.logExpiredConnections != null && logging.logExpiredConnections);
      if (logging.logAbandoned != null && logging.logAbandoned) {
        dataSource.setAbandonedLogWriter(loggerPrintWriter);
        dataSource.setLogAbandoned(true);
      }
    }

    return dataSource;
  }

  private DataSources() {
  }
}