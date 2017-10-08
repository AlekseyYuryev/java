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

package org.lib4j.dbcp;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace="http://lib4j.org/dbcp.xsd")
public class Dbcp {
  @XmlAttribute
  public String name;

  public static class Jdbc {
    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String url;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String driverClassName;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String username;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String password;
  }

  @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
  public Jdbc jdbc;

  public static class Default {
    public static enum TransactionIsolation {
      NONE(java.sql.Connection.TRANSACTION_NONE),
      READ_UNCOMMITTED(java.sql.Connection.TRANSACTION_READ_UNCOMMITTED),
      READ_COMMITTED(java.sql.Connection.TRANSACTION_READ_COMMITTED),
      REPEATABLE_READ(java.sql.Connection.TRANSACTION_REPEATABLE_READ),
      SERIALIZABLE(java.sql.Connection.TRANSACTION_SERIALIZABLE);

      private final int constant;

      TransactionIsolation(final int constant) {
        this.constant = constant;
      }

      public int getConstant() {
        return this.constant;
      }
    }

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String catalog;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean autoCommit;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean readOnly;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Integer queryTimeout;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public TransactionIsolation transactionIsolation;
  }

  @XmlElement(namespace="http://lib4j.org/dbcp.xsd", name="default")
  public Default _default;

  public static class Connection {
    public static class Properties {
      public static class Property {
        public Property(final String name, final String value) {
          this.name = name;
          this.value = value;
        }

        public Property() {
        }

        @XmlAttribute
        public String name;

        @XmlAttribute
        public String value;
      }

      @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
      public List<Property> property;
    }

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Properties properties;

    public static class InitSqls {
      @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
      public List<String> initSql;
    }

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public InitSqls initSqls;
  }

  @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
  public Connection connection;

  public static class Size {
    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Integer initialSize;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String maxTotal;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String maxIdle;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Integer minIdle;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String maxOpenPreparedStatements;
  }

  @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
  public Size size;

  public static class Pool {
    public static enum Queue {
      lifo,
      fifo
    }

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Queue queue;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean cacheState;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String maxWait;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String maxConnectionLifetime;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean enableAutoCommitOnReturn;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean rollbackOnReturn;

    public static class RemoveAbandoned {
      public static enum On {
        borrow,
        maintenance
      }

      @XmlAttribute
      public On on;

      @XmlAttribute
      public Integer timeout;
    }

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public RemoveAbandoned removeAbandoned;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean abandonedUsageTracking;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean allowAccessToUnderlyingConnection;

    public static class Eviction {
      @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
      public Long timeBetweenRuns;

      @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
      public Integer numTestsPerRun;

      @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
      public Long minIdleTime;

      @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
      public String softMinIdleTime;

      @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
      public String policyClassName;
    }

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Eviction eviction;
  }

  @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
  public Pool pool;

  public static class Validation {
    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public String query;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean testOnBorrow;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean testOnReturn;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean testWhileIdle;

    public static class FastFail {
      @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
      public String disconnectionSqlCodes;
    }

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public FastFail fastFail;
  }

  @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
  public Validation validation;

  public static class Logging {
    public static enum Level {
      ERROR,
      WARN,
      INFO,
      DEBUG,
      TRACE
    }

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Level level;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean logExpiredConnections;

    @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
    public Boolean logAbandoned;
  }

  @XmlElement(namespace="http://lib4j.org/dbcp.xsd")
  public Logging logging;
}