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

package org.libx4j.rdb.ddlx.runner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.lib4j.sql.ConnectionProxy;
import org.libx4j.rdb.vendor.DBVendor;

import com.mysql.cj.jdbc.Driver;

@SuppressWarnings("unused")
public class MySQL implements Vendor {
  @Override
  public DBVendor getDBVendor() {
    return DBVendor.MY_SQL;
  }

  @Override
  public synchronized void init() throws IOException, SQLException {
//  CREATE USER rdb;
//  CREATE DATABASE rdb;
//  GRANT ALL ON rdb.* TO 'rdb'@'localhost' IDENTIFIED BY 'rdb';
    new Driver();
  }

  @Override
  public Connection getConnection() throws SQLException {
    // NOTE: for some reason, "127.0.0.1" works if you tunnel the local 3306 port to a remote machine, and "localhost" fails to connect
    return new ConnectionProxy(DriverManager.getConnection("jdbc:mysql://127.0.0.1/rdb?user=rdb&password=rdb&useSSL=false&serverTimezone=UTC"));
  }

  @Override
  public void destroy() throws SQLException {
  }
}