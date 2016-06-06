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