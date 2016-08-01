package org.safris.xws.xrs;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.safris.commons.lang.reflect.Classes;

public abstract class RegisteringRESTServlet extends HttpServlet {
  private static final long serialVersionUID = 6825431027711735886L;

  private final MultivaluedMap<String,ServiceManifest> registry = new MultivaluedHashMap<String,ServiceManifest>();
  private final List<Class<? extends ContainerResponseFilter>> preMatchResponseFilters = new ArrayList<Class<? extends ContainerResponseFilter>>();
  private final List<Class<? extends ContainerResponseFilter>> postMatchResponseFilters = new ArrayList<Class<? extends ContainerResponseFilter>>();
  private final List<Class<? extends ContainerRequestFilter>> preMatchRequestFilters = new ArrayList<Class<? extends ContainerRequestFilter>>();
  private final List<Class<? extends ContainerRequestFilter>> postMatchRequestFilters = new ArrayList<Class<? extends ContainerRequestFilter>>();

  private static <T>T inject(final Class<T> cls, final HttpServletRequest request) {
    try {
      final T object = cls.newInstance();
      final Field[] fields = Classes.getDeclaredFieldsDeep(object.getClass());
      for (final Field field : fields) {
        if (field.isAnnotationPresent(Context.class)) {
          if (field.getType() == HttpServletRequest.class) {
            field.setAccessible(true);
            field.set(object, request);
          }
          else {
            throw new UnsupportedOperationException("Unsupported @Context type: " + field.getType().getName() + " on: " + object.getClass().getName() + "." + field.getName());
          }
        }
      }

      return object;
    }
    catch (final IllegalArgumentException | ReflectiveOperationException e) {
      throw new WebApplicationException(e);
    }
  }

  protected void runPreMatchRequestFilters(final ContainerRequestContext requestContext, final HttpServletRequest request) throws IOException {
    for (final Class<? extends ContainerRequestFilter> preMatchRequestFilter : preMatchRequestFilters) {
      final ContainerRequestFilter filter = inject(preMatchRequestFilter, request);
      filter.filter(requestContext);
    }
  }

  protected void runPostMatchRequestFilters(final ContainerRequestContext requestContext, final HttpServletRequest request) throws IOException {
    for (final Class<? extends ContainerRequestFilter> postMatchRequestFilter : postMatchRequestFilters) {
      final ContainerRequestFilter filter = inject(postMatchRequestFilter, request);
      filter.filter(requestContext);
    }
  }

  protected void runPreMatchResponseFilters(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext, final HttpServletRequest request) throws IOException {
    for (final Class<? extends ContainerResponseFilter> preMatchResponseFilter : preMatchResponseFilters) {
      final ContainerResponseFilter filter = inject(preMatchResponseFilter, request);
      filter.filter(requestContext, responseContext);
    }
  }

  protected void runPostMatchResponseFilters(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext, final HttpServletRequest request) throws IOException {
    for (final Class<? extends ContainerResponseFilter> postMatchResponseFilter : postMatchResponseFilters) {
      final ContainerResponseFilter filter = inject(postMatchResponseFilter, request);
      filter.filter(requestContext, responseContext);
    }
  }

  public void register(final ServiceManifest manifest) {
    registry.add(manifest.getHttpMethod().value().toUpperCase(), manifest);
  }

  public void addResponseFilter(final Class<? extends ContainerResponseFilter> filterClass) {
    (filterClass.isAnnotationPresent(PreMatching.class) ? preMatchResponseFilters : postMatchResponseFilters).add(filterClass);
  }

  public void addRequestFilter(final Class<? extends ContainerRequestFilter> filterClass) {
    (filterClass.isAnnotationPresent(PreMatching.class) ? preMatchRequestFilters : postMatchRequestFilters).add(filterClass);
  }

  public ServiceManifest filterAndMatch(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
    final List<ServiceManifest> manifests = registry.get(requestContext.getMethod().toUpperCase());
    if (manifests == null)
      return null;

    for (final ServiceManifest manifest : manifests)
      if (manifest.matches(requestContext))
        return manifest;

    return null;
  }
}