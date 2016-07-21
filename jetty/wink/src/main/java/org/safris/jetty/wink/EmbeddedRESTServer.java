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

package org.safris.jetty.wink;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;

import org.apache.wink.server.handlers.HandlersFactory;
import org.apache.wink.server.handlers.RequestHandler;
import org.apache.wink.server.handlers.ResponseHandler;
import org.apache.wink.server.internal.servlet.RestServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.safris.commons.lang.PackageLoader;
import org.safris.commons.servlet.xe.$se_realm;
import org.safris.jetty.servlet.EmbeddedServletContext;

public class EmbeddedRESTServer extends EmbeddedServletContext {
  public EmbeddedRESTServer(final int port, final String keyStorePath, final String keyStorePassword, final boolean externalResourcesAccess, final $se_realm realm) {
    super(port, keyStorePath, keyStorePassword, externalResourcesAccess, createContext(realm));
  }

  public static class EmbeddedApplication extends Application {
    private static Boolean mutex = false;

    protected static final Set<Class<?>> rsClasses = new HashSet<Class<?>>();
    protected static final Set<Class<? extends RequestHandler>> requestHandlers = new HashSet<Class<? extends RequestHandler>>();
    protected static final Set<Class<? extends ResponseHandler>> responseHandlers = new HashSet<Class<? extends ResponseHandler>>();

    @SuppressWarnings("unchecked")
    private static void init() {
      if (mutex)
        return;

      synchronized (mutex) {
        if (mutex)
          return;

      try {
          for (final Package pkg : Package.getPackages()) {
            Set<Class<?>> classes;
            try {
              classes = PackageLoader.getSystemPackageLoader().loadPackage(pkg, false);
            }
            catch (final SecurityException e) {
              continue;
            }

            for (final Class<?> cls : classes) {
              if (Modifier.isAbstract(cls.getModifiers()))
                continue;

              if (cls.getName().startsWith("org.apache.wink.common.internal") || cls.getName().startsWith("org.apache.wink.server.internal"))
                continue;

              if (cls.isAnnotationPresent(Path.class))
                rsClasses.add(cls);
              else if (cls.isAnnotationPresent(Provider.class))
                rsClasses.add(cls);
              else if (RequestHandler.class.isAssignableFrom(cls))
                requestHandlers.add((Class<? extends RequestHandler>)cls);
              else if (ResponseHandler.class.isAssignableFrom(cls))
                responseHandlers.add((Class<? extends ResponseHandler>)cls);
            }
          }
        }
        catch (final Exception e) {
          throw new Error(e);
        }
      }
    }

    public static class EmbeddedHandlersFactory extends HandlersFactory {
      @Override
      @SuppressWarnings("cast")
      public List<? extends RequestHandler> getRequestHandlers() {
        init();
        final List<RequestHandler> instances = new ArrayList<RequestHandler>();
        try {
          for (final Class<? extends RequestHandler> cls : requestHandlers)
            instances.add((RequestHandler)cls.newInstance());

          return instances;
        }
        catch (final ReflectiveOperationException e) {
          throw new Error(e);
        }
      }

      @Override
      @SuppressWarnings("cast")
      public List<? extends ResponseHandler> getResponseHandlers() {
        init();
        final List<ResponseHandler> instances = new ArrayList<ResponseHandler>();
        try {
          for (final Class<? extends ResponseHandler> cls : responseHandlers)
            instances.add((ResponseHandler)cls.newInstance());

          return instances;
        }
        catch (final ReflectiveOperationException e) {
          throw new Error(e);
        }
      }
    }

    @Override
    public Set<Class<?>> getClasses() {
      init();
      return rsClasses;
    }
  }

  public static class EmbeddedRestServlet extends RestServlet {
    private static final long serialVersionUID = 3943438134973343952L;

    @Override
    protected Properties getProperties() throws IOException {
      final Properties properties = super.getProperties();
      properties.setProperty("wink.handlersFactoryClass", EmbeddedApplication.EmbeddedHandlersFactory.class.getName());
      return properties;
    }
  }

  // FIXME: realm is misplaced .. it needs to be integrated
  private static ServletContextHandler createContext(final $se_realm realm) {
    try {
      final ServletHolder holder = new ServletHolder(EmbeddedRestServlet.class);
      holder.setInitOrder(0);
      holder.setInitParameter("javax.ws.rs.Application", EmbeddedApplication.class.getName());
      final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
      context.setContextPath("/");
      context.addServlet(holder, "/*");
      return context;
    }
    catch (final Exception e) {
      throw new Error(e);
    }
  }
}