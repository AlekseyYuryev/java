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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.PackageNotFoundException;

public abstract class RegisteringRESTServlet extends HttpServlet {
  private static final long serialVersionUID = 6825431027711735886L;
  private static final Logger logger = Logger.getLogger(RESTServlet.class.getName());

  private static final Comparator<Class<?>> priorityComparator = new Comparator<Class<?>>() {
    @Override
    public int compare(final Class<?> o1, final Class<?> o2) {
      final Priority p1 = o1.getAnnotation(Priority.class);
      final Priority p2 = o1.getAnnotation(Priority.class);
      if (p1 == null) {
        if (p2 == null)
          return 0;

        return 1;
      }

      if (p2 == null)
        return -1;

      return Integer.compare(p1.value(), p2.value());
    }
  };

  private final MultivaluedMap<String,ServiceManifest> registry = new MultivaluedHashMap<String,ServiceManifest>();
  private final List<Class<? extends ContainerResponseFilter>> preMatchResponseFilters = new ArrayList<Class<? extends ContainerResponseFilter>>();
  private final List<Class<? extends ContainerResponseFilter>> postMatchResponseFilters = new ArrayList<Class<? extends ContainerResponseFilter>>();
  private final List<Class<? extends ContainerRequestFilter>> preMatchRequestFilters = new ArrayList<Class<? extends ContainerRequestFilter>>();
  private final List<Class<? extends ContainerRequestFilter>> postMatchRequestFilters = new ArrayList<Class<? extends ContainerRequestFilter>>();

  @SuppressWarnings("unchecked")
  public RegisteringRESTServlet() {
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
              InjectionContext.allowsInjectableClass(Field.class, cls);
              final ServiceManifest manifest = new ServiceManifest(httpMethodAnnotation, method);
              logger.info("[JAX-RS] " + manifest.getPathPattern().getPattern().toString() + " " + cls.getSimpleName() + "." + method.getName() + "(): " + httpMethodAnnotation.value());
              register(manifest);
            }
          }
        }
        else if (cls.isAnnotationPresent(Provider.class)) {
          if (ContainerRequestFilter.class.isAssignableFrom(cls)) {
            addRequestFilter((Class<? extends ContainerRequestFilter>)cls);
          }
          else if (ContainerResponseFilter.class.isAssignableFrom(cls)) {
            addResponseFilter((Class<? extends ContainerResponseFilter>)cls);
          }
          else {
            throw new UnsupportedOperationException("Unexpected @Provider class: " + cls.getName());
          }
        }
      }
    }

    preMatchRequestFilters.sort(priorityComparator);
    postMatchRequestFilters.sort(priorityComparator);
    preMatchResponseFilters.sort(priorityComparator);
    postMatchResponseFilters.sort(priorityComparator);
  }

  private void register(final ServiceManifest manifest) {
    registry.add(manifest.getHttpMethod().value().toUpperCase(), manifest);
  }

  private void addResponseFilter(final Class<? extends ContainerResponseFilter> filterClass) {
    (filterClass.isAnnotationPresent(PreMatching.class) ? preMatchResponseFilters : postMatchResponseFilters).add(filterClass);
  }

  private void addRequestFilter(final Class<? extends ContainerRequestFilter> filterClass) {
    (filterClass.isAnnotationPresent(PreMatching.class) ? preMatchRequestFilters : postMatchRequestFilters).add(filterClass);
  }

  protected void runPreMatchRequestFilters(final ContainerRequestContext requestContext, final InjectionContext injectionContext) throws IOException {
    for (final Class<? extends ContainerRequestFilter> preMatchRequestFilter : preMatchRequestFilters) {
      final ContainerRequestFilter filter = injectionContext.inject(preMatchRequestFilter);
      filter.filter(requestContext);
    }
  }

  protected void runPostMatchRequestFilters(final ContainerRequestContext requestContext, final InjectionContext injectionContext) throws IOException {
    for (final Class<? extends ContainerRequestFilter> postMatchRequestFilter : postMatchRequestFilters) {
      final ContainerRequestFilter filter = injectionContext.inject(postMatchRequestFilter);
      filter.filter(requestContext);
    }
  }

  protected void runPreMatchResponseFilters(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext, final InjectionContext injectionContext) throws IOException {
    for (final Class<? extends ContainerResponseFilter> preMatchResponseFilter : preMatchResponseFilters) {
      final ContainerResponseFilter filter = injectionContext.inject(preMatchResponseFilter);
      filter.filter(requestContext, responseContext);
    }
  }

  protected void runPostMatchResponseFilters(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext, final InjectionContext injectionContext) throws IOException {
    for (final Class<? extends ContainerResponseFilter> postMatchResponseFilter : postMatchResponseFilters) {
      final ContainerResponseFilter filter = injectionContext.inject(postMatchResponseFilter);
      filter.filter(requestContext, responseContext);
    }
  }

  public ServiceManifest filterAndMatch(final ContainerRequestContext requestContext) {
    final List<ServiceManifest> manifests = registry.get(requestContext.getMethod().toUpperCase());
    if (manifests == null)
      return null;

    for (final ServiceManifest manifest : manifests)
      if (manifest.matches(requestContext))
        return manifest;

    return null;
  }
}