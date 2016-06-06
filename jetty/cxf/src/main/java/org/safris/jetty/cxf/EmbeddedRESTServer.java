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

import java.util.logging.Logger;

import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.safris.commons.jetty.EmbeddedServletContext;
import org.safris.commons.servlet.xe.$se_realm;

public class EmbeddedRESTServer extends EmbeddedServletContext {
  private static final Logger logger = Logger.getLogger(EmbeddedRESTServer.class.getName());

  public EmbeddedRESTServer(final int port, final String keyStorePath, final String keyStorePassword, final boolean externalResourcesAccess, final $se_realm realm, final Application application) {
    super(port, keyStorePath, keyStorePassword, externalResourcesAccess, addAllServlets(realm, application));
  }

  private static ServletContextHandler addAllServlets(final $se_realm realm, final Application application) {
    try {
      final ServletHolder holder = new ServletHolder(CXFNonSpringJaxrsServlet.class);
      holder.setInitOrder(0);
      final StringBuilder builder = new StringBuilder();
      for (final Class<?> cls : application.getClasses())
        builder.append(',').append(cls.getName());

      holder.setInitParameter("jaxrs.serviceClasses", builder.substring(1));
      ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
      context.setContextPath("/");
      context.addServlet(holder, "/*");
      return context;
    }
    catch (final Exception e) {
      throw new Error(e);
    }
  }
}