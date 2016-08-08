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

package org.safris.xws.xrs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.PackageNotFoundException;
import org.safris.xws.xrs.core.ContextInjector;

public abstract class StartupServlet extends HttpServlet {
  private static final long serialVersionUID = 6825431027711735886L;
  private static final Logger logger = Logger.getLogger(DefaultRESTServlet.class.getName());

  private ExecutionContext executionContext;

  private static void addProvider(final List<MessageBodyReader<?>> entityReaders, final List<MessageBodyWriter<?>> entityWriters, final List<Class<? extends ContainerRequestFilter>> requestFilters, final List<Class<? extends ContainerResponseFilter>> responseFilters, final Object singleton) {
    if (singleton instanceof MessageBodyReader) {
      final MessageBodyReader<?> entityReader = (MessageBodyReader<?>)singleton;
      entityReaders.add(entityReader);
    }
    else if (singleton instanceof MessageBodyWriter) {
      final MessageBodyWriter<?> entityWriter = (MessageBodyWriter<?>)singleton;
      entityWriters.add(entityWriter);
    }
    else {
      throw new UnsupportedOperationException("Unexpected @Provider SINGLETON of type: " + singleton.getClass().getName());
    }
  }

  @SuppressWarnings("unchecked")
  private static void addProvider(final List<MessageBodyReader<?>> entityReaders, final List<MessageBodyWriter<?>> entityWriters, final List<Class<? extends ContainerRequestFilter>> requestFilters, final List<Class<? extends ContainerResponseFilter>> responseFilters, final Class<?> cls) {
    if (ContainerRequestFilter.class.isAssignableFrom(cls)) {
      requestFilters.add((Class<? extends ContainerRequestFilter>)cls);
    }
    else if (ContainerResponseFilter.class.isAssignableFrom(cls)) {
      responseFilters.add((Class<? extends ContainerResponseFilter>)cls);
    }
    else {
      throw new UnsupportedOperationException("Unexpected @Provider CLASS of type: " + cls.getName());
    }
  }

  protected ExecutionContext getExecutionContext() {
    return executionContext;
  }

  @Override
  public void init(final ServletConfig config) throws ServletException {
    super.init(config);
    final MultivaluedMap<String,ResourceManifest> registry = new MultivaluedHashMap<String,ResourceManifest>();

    final List<MessageBodyReader<?>> entityReaders = new ArrayList<MessageBodyReader<?>>();
    final List<MessageBodyWriter<?>> entityWriters = new ArrayList<MessageBodyWriter<?>>();
    final List<Class<? extends ContainerRequestFilter>> requestFilters = new ArrayList<Class<? extends ContainerRequestFilter>>();
    final List<Class<? extends ContainerResponseFilter>> responseFilters = new ArrayList<Class<? extends ContainerResponseFilter>>();

    try {
      for (final Package pkg : Package.getPackages()) {
        final Set<Class<?>> classes;
        try {
          classes = PackageLoader.getSystemPackageLoader().loadPackage(pkg, false);
        }
        catch (final PackageNotFoundException | SecurityException e) {
          continue;
        }

        for (final Class<?> cls : classes) {
          if (Modifier.isAbstract(cls.getModifiers()))
            continue;

          // Add a Class<?> with a @Path annotation
          if (cls.isAnnotationPresent(Path.class)) {
            final Method[] methods = cls.getMethods();
            for (final Method method : methods) {
              final Set<HttpMethod> httpMethodAnnotations = new HashSet<HttpMethod>(); // FIXME: Can this be done without a Collection?
              final Annotation[] annotations = method.getAnnotations();
              for (final Annotation annotation : annotations) {
                final HttpMethod httpMethodAnnotation = annotation.annotationType().getAnnotation(HttpMethod.class);
                if (httpMethodAnnotation != null)
                  httpMethodAnnotations.add(httpMethodAnnotation);
              }

              for (final HttpMethod httpMethodAnnotation : httpMethodAnnotations) {
                ContextInjector.allowsInjectableClass(Field.class, cls);
                final ResourceManifest manifest = new ResourceManifest(httpMethodAnnotation, method);
                logger.info("[XRS] " + manifest.getPathPattern().getPattern().toString() + " " + cls.getSimpleName() + "." + method.getName() + "(): " + httpMethodAnnotation.value());
                registry.add(manifest.getHttpMethod().value().toUpperCase(), manifest);
              }
            }
          }
          else if (cls.isAnnotationPresent(Provider.class)) {
            if (MessageBodyReader.class.isAssignableFrom(cls) || MessageBodyWriter.class.isAssignableFrom(cls))
              addProvider(entityReaders, entityWriters, requestFilters, responseFilters, cls.newInstance());
            else
              addProvider(entityReaders, entityWriters, requestFilters, responseFilters, cls);
          }
        }
      }
    }
    catch (final SecurityException | InstantiationException | IllegalAccessException e1) {
      throw new WebApplicationException(e1);
    }

    final String applicationSpec = getInitParameter("javax.ws.rs.Application");
    if (applicationSpec != null) {
      try {
        final Application application = (Application)Class.forName(applicationSpec).newInstance();
        final Set<?> singletons = application.getSingletons();
        if (singletons != null)
          for (final Object provider : singletons)
            addProvider(entityReaders, entityWriters, requestFilters, responseFilters, provider);

        final Set<Class<?>> classes = application.getClasses();
        if (classes != null)
          for (final Class<?> cls : classes)
            addProvider(entityReaders, entityWriters, requestFilters, responseFilters, cls);
      }
      catch (final InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        throw new WebApplicationException(e);
      }
    }

    this.executionContext = new ExecutionContext(registry, new ContainerFilters(requestFilters, responseFilters), new EntityProviders(entityReaders, entityWriters));
  }
}