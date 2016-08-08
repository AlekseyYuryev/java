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

package org.safris.jetty.servlet;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.PackageNotFoundException;
import org.safris.commons.servlet.xe.$se_realm;

public class EmbeddedServletContainer extends EmbeddedServletContext {
  private static final Logger logger = Logger.getLogger(EmbeddedServletContainer.class.getName());
  private static UncaughtServletExceptionHandler uncaughtServletExceptionHandler;

  private static Set<Class<? extends HttpServlet>> addedServletClasses = new HashSet<Class<? extends HttpServlet>>();
  private static Set<Class<? extends Filter>> addedFilterClasses = new HashSet<Class<? extends Filter>>();

  private static void addServlet(final ServletContextHandler context, final Class<? extends HttpServlet> servletClass) {
    if (addedServletClasses.contains(servletClass))
      return;

    final WebServlet webServlet = servletClass.getAnnotation(WebServlet.class);
    if (webServlet == null) {
      logger.warning("HttpServlet class " + servletClass.getName() + " is missing the @WebServlet annotation");
      return;
    }

    final HttpServlet servlet;
    try {
      servlet = servletClass.newInstance();
    }
    catch (final IllegalAccessException | InstantiationException e) {
      logger.warning(e.getMessage());
      return;
    }

    final String[] urlPatterns = webServlet.value().length != 0 ? webServlet.value() : webServlet.urlPatterns();
    if (urlPatterns.length == 0) {
      logger.warning("HttpServlet class " + servletClass.getName() + " is missing an URL pattern on the @WebServlet annotation");
      return;
    }

    Map<String,String> initParams = null;
    final WebInitParam[] webInitParams = webServlet.initParams();
    if (webInitParams != null) {
      initParams = new HashMap<String,String>();
      for (final WebInitParam webInitParam : webInitParams)
        initParams.put(webInitParam.name(), webInitParam.value());
    }

    final String servletName = webServlet.name() != null ? webServlet.name() : servletClass.getName();

    final ServletSecurity servletSecurity = servletClass.getAnnotation(ServletSecurity.class);
    HttpConstraint httpConstraint;
    if (servletSecurity != null && (httpConstraint = servletSecurity.value()) != null && httpConstraint.rolesAllowed().length > 0) {
      for (final String urlPattern : urlPatterns) {
        for (final String role : httpConstraint.rolesAllowed()) {
          final ConstraintMapping constraintMapping = new ConstraintMapping();
          constraintMapping.setConstraint(getBasicAuthConstraint(Constraint.__BASIC_AUTH, role));
          constraintMapping.setPathSpec(urlPattern);
          final SecurityHandler securityHandler = context.getSecurityHandler();
          if (!(securityHandler instanceof ConstraintSecurityHandler))
            throw new Error("SecurityHandler of ServletContextHandler must be a ConstraintSecurityHandler, did you call setConstraintSecurityHandler()?");

          ((ConstraintSecurityHandler)securityHandler).addConstraintMapping(constraintMapping);
        }
      }

      logger.info(servlet.getClass().getSimpleName() + " [" + context.getSecurityHandler().getLoginService().getName() + "]: " + Arrays.toString(urlPatterns));
    }

    logger.info(servletClass.getName() + " " + Arrays.toString(urlPatterns));
    addedServletClasses.add(servletClass);
    for (final String urlPattern : urlPatterns) {
      final ServletHolder servletHolder = new ServletHolder(servlet);
      servletHolder.setName(servletName);
      if (initParams != null)
        servletHolder.getRegistration().setInitParameters(initParams);

      servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement(""));
      context.addServlet(servletHolder, urlPattern);
    }
  }

  private static void addFilter(final ServletContextHandler context, final Class<? extends Filter> filterClass) {
    if (addedFilterClasses.contains(filterClass))
      return;

    final WebFilter webFilter = filterClass.getAnnotation(WebFilter.class);
    if (webFilter == null) {
      logger.warning("WebFilter class " + filterClass.getName() + " is missing the @WebFilter annotation");
      return;
    }

    logger.info(filterClass.getName() + " " + Arrays.toString(webFilter.urlPatterns()));
    addedFilterClasses.add(filterClass);
    for (final String urlPattern : webFilter.urlPatterns()) {
      context.addFilter(filterClass, urlPattern, webFilter.dispatcherTypes().length > 0 ? EnumSet.of(webFilter.dispatcherTypes()[0], webFilter.dispatcherTypes()) : EnumSet.noneOf(DispatcherType.class));
    }
  }

  @SuppressWarnings("unchecked")
  private static ServletContextHandler addAllServlets(final $se_realm realm, final Class<? extends HttpServlet> ... servletClasses) {
    final ServletContextHandler context = createServletContextHandler(realm);
    context.setErrorHandler(new ErrorHandler());
    for (final Class<? extends HttpServlet> servletClass : servletClasses) {
      addServlet(context, servletClass);
    }

    for (final Package pkg : Package.getPackages()) {
      try {
        final Set<Class<?>> classes = PackageLoader.getSystemPackageLoader().loadPackage(pkg, false);
        for (final Class<?> cls : classes) {
          if (!Modifier.isAbstract(cls.getModifiers())) {
            if (HttpServlet.class.isAssignableFrom(cls))
              addServlet(context, (Class<? extends HttpServlet>)cls);
            else if (Filter.class.isAssignableFrom(cls) && cls.isAnnotationPresent(WebFilter.class))
              addFilter(context, (Class<? extends Filter>)cls);
          }
        }
      }
      catch (final PackageNotFoundException | SecurityException e) {
      }
    }

    return context;
  }

  public static void setUncaughtServletExceptionHandler(final UncaughtServletExceptionHandler uncaughtServletExceptionHandler) {
    EmbeddedServletContainer.uncaughtServletExceptionHandler = uncaughtServletExceptionHandler;
  }

  protected static UncaughtServletExceptionHandler getUncaughtServletExceptionHandler() {
    return EmbeddedServletContainer.uncaughtServletExceptionHandler;
  }

  @SafeVarargs
  public EmbeddedServletContainer(final int port, final String keyStorePath, final String keyStorePassword, final boolean externalResourcesAccess, final $se_realm realm, final Class<? extends HttpServlet> ... servletClasses) {
    super(port, keyStorePath, keyStorePassword, externalResourcesAccess, addAllServlets(realm, servletClasses));
  }
}