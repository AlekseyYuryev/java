package org.safris.commons.servlet;

import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.Resources;

public class EmbeddedServer {
  private static final Logger logger = Logger.getLogger(EmbeddedServer.class.getName());

  private static Connector makeConnector(final org.eclipse.jetty.server.Server server, final int port, final String keyStorePath, final String keyStorePassword) {
    if (keyStorePath == null || keyStorePassword == null) {
      final ServerConnector connector = new ServerConnector(server);
      connector.setPort(port);
      return connector;
    }

    final HttpConfiguration https = new HttpConfiguration();
    https.addCustomizer(new SecureRequestCustomizer());

    final SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setKeyStorePath(Resources.getResource(keyStorePath).getURL().toExternalForm());
    sslContextFactory.setKeyStorePassword(keyStorePassword);

    final ServerConnector connector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https));
    connector.setPort(port);
    return connector;
  }

  private static ServletContextHandler createServletContextHandler(final $se_realm realm) {
    final ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

    final HashLoginService loginService = new HashLoginService(realm._name$().text());
    for (final $se_realm._credential credential : realm._credential())
      for (final String role : credential._roles$().text())
        loginService.putUser(credential._username$().text(), Credential.getCredential(credential._password$().text()), new String[] {role});

    final ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
    securityHandler.setAuthenticator(new BasicAuthenticator());
    securityHandler.setRealmName(realm._name$().text());
    securityHandler.setLoginService(loginService);

    servletContextHandler.setSecurityHandler(securityHandler);
    return servletContextHandler;
  }

  private static ServletContextHandler addAllServlets(final Package[] packages, final $se_realm realm) {
    final ServletContextHandler handler = createServletContextHandler(realm);
    try {
      for (final Package pkg : packages) {
        final Set<Class<?>> classes = PackageLoader.getSystemPackageLoader().loadPackage(pkg, false);
        WebServlet webServlet;
        for (final Class<?> cls : classes) {
          if (!Modifier.isAbstract(cls.getModifiers()) && HttpServlet.class.isAssignableFrom(cls) && (webServlet = cls.getAnnotation(WebServlet.class)) != null && webServlet.urlPatterns() != null && webServlet.urlPatterns().length > 0) {
            final HttpServlet servlet = (HttpServlet)cls.newInstance();
            final ServletSecurity servletSecurity = cls.getAnnotation(ServletSecurity.class);
            HttpConstraint httpConstraint;
            if (servletSecurity != null && (httpConstraint = servletSecurity.value()) != null && httpConstraint.rolesAllowed().length > 0) {
              for (final String urlPattern : webServlet.urlPatterns()) {
                for (final String role : httpConstraint.rolesAllowed()) {
                  final ConstraintMapping constraintMapping = new ConstraintMapping();
                  constraintMapping.setConstraint(getBasicAuthConstraint(role));
                  constraintMapping.setPathSpec(urlPattern);
                  final SecurityHandler securityHandler = handler.getSecurityHandler();
                  if (!(securityHandler instanceof ConstraintSecurityHandler))
                    throw new Error("SecurityHandler of ServletContextHandler must be a ConstraintSecurityHandler, did you call setConstraintSecurityHandler()?");

                  ((ConstraintSecurityHandler)securityHandler).addConstraintMapping(constraintMapping);
                }
              }

              logger.info(servlet.getClass().getSimpleName() + " [" + handler.getSecurityHandler().getLoginService().getName() + "]: " + Arrays.toString(webServlet.urlPatterns()));
            }

            logger.info(servlet.getClass().getName() + " " + Arrays.toString(webServlet.urlPatterns()));
            for (final String urlPattern : webServlet.urlPatterns())
              handler.addServlet(new ServletHolder(servlet), urlPattern);
          }
        }
      }

      return handler;
    }
    catch (final Exception e) {
      throw new Error(e);
    }
  }

  private static final Map<String,Map<String,Constraint>> roleToConstraint = new HashMap<String,Map<String,Constraint>>();

  private static Constraint getConstraint(final Map<String,Constraint> authTypeToConstraint, final String authType, final String role) {
    Constraint constraint = authTypeToConstraint.get(authType);
    if (constraint != null)
      return constraint;

    synchronized (authTypeToConstraint) {
      constraint = authTypeToConstraint.get(authType);
      if (constraint != null)
        return constraint;

      authTypeToConstraint.put(authType, constraint = new Constraint(authType, role));
      constraint.setAuthenticate(true);
      return constraint;
    }
  }

  private static Constraint getBasicAuthConstraint(final String role) {
    Map<String,Constraint> authTypeToConstraint = roleToConstraint.get(role);
    if (authTypeToConstraint == null) {
      synchronized (roleToConstraint) {
        authTypeToConstraint = roleToConstraint.get(role);
        if (authTypeToConstraint == null)
          roleToConstraint.put(role, authTypeToConstraint = new HashMap<String,Constraint>());
      }
    }

    return getConstraint(authTypeToConstraint, Constraint.__BASIC_AUTH, role);
  }

  private final org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server();

  public EmbeddedServer(final int port, final String keyStorePath, final String keyStorePassword, final boolean externalResourcesAccess, final $se_realm realm) {
    server.setConnectors(new Connector[] {makeConnector(server, port, keyStorePath, keyStorePassword)});
    final ServletContextHandler handler = addAllServlets(Package.getPackages(), realm);

    final HandlerList handlerList = new HandlerList();

    if (externalResourcesAccess) {
      // FIXME: HACK: Why cannot I just get the "/" resource? In the IDE it works, but in the stand-alone jar, it does not
      try {
        final String resourceName = getClass().getName().replace('.', '/') + ".class";
        final String configResourcePath = Resources.getResource(resourceName).getURL().toExternalForm();
        final URL rootResourceURL = new URL(configResourcePath.substring(0, configResourcePath.length() - resourceName.length()));

        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setBaseResource(Resource.newResource(rootResourceURL));

        handlerList.addHandler(resourceHandler);
      }
      catch (final MalformedURLException e) {
        throw new Error(e);
      }
    }

    handlerList.addHandler(handler);

    server.setHandler(handlerList);
  }

  public void start() throws Exception {
    server.start();
  }

  public void join() throws InterruptedException {
    server.join();
  }
}