/* Copyright (c) 2016 Seva Safris
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

package org.safris.jetty.cxf;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;

import org.safris.commons.net.Ports;
import org.safris.commons.servlet.xe.$se_realm;

public class HelloWorldTest {
  public static void main(final String[] args) throws Exception {
    final HelloWorldServer server = new HelloWorldServer(Ports.findRandomOpenPort(), null, null, false, null, new HelloWorldApplication());
    server.start();
    server.join();
  }

  public static class HelloWorldApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
      final Set<Class<?>> classes = new HashSet<Class<?>>();
      classes.add(HelloWorldProvider.class);
      return classes;
    }
  }

  public static class HelloWorldServer extends EmbeddedRESTServer {
    public HelloWorldServer(final int port, final String keyStorePath, final String keyStorePassword, final boolean externalResourcesAccess, final $se_realm realm, final Application application) {
      super(port, keyStorePath, keyStorePassword, externalResourcesAccess, realm, application);
    }
  }

  @Path("/hello/world")
  public static class HelloWorldProvider {
    @GET
    public String get() {
      return "Hello World!";
    }

    @GET
    @Path("{id}")
    public String get(@PathParam("id") final String id) {
      return "Hello World!" + id;
    }
  }
}