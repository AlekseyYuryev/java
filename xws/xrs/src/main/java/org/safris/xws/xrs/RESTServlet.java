package org.safris.xws.xrs;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.PackageNotFoundException;
import org.safris.xws.xjb.JSObject;

@WebServlet("/*")
public final class RESTServlet extends RegisteringRESTServlet {
  private static final long serialVersionUID = 3700080355780006441L;
  private static final Logger logger = Logger.getLogger(RESTServlet.class.getName());

  @SuppressWarnings("unchecked")
  public RESTServlet() {
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
          Object object = null;
          if (cls.isAnnotationPresent(Path.class)) {
            final Method[] methods = cls.getMethods();
            for (final Method method : methods) {
              Annotation securityAnnotation = null;
              final Set<HttpMethod> httpMethodAnnotations = new HashSet<HttpMethod>(); // FIXME: Can this be done without a Collection?
              final Annotation[] annotations = method.getAnnotations();
              for (final Annotation annotation : annotations) {
                final HttpMethod httpMethodAnnotation = annotation.annotationType().getAnnotation(HttpMethod.class);
                if (httpMethodAnnotation != null)
                  httpMethodAnnotations.add(httpMethodAnnotation);
                else if (annotation.annotationType() == PermitAll.class || annotation.annotationType() == DenyAll.class || annotation.annotationType() == RolesAllowed.class)
                  securityAnnotation = annotation;
              }

              for (final HttpMethod httpMethodAnnotation : httpMethodAnnotations) {
                final ServiceManifest manifest = new ServiceManifest(httpMethodAnnotation, securityAnnotation, method, (object == null ? object = cls.newInstance() : object));
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
    }
    catch (final InstantiationException | IllegalAccessException e) {
      throw new ServerStartupException(e);
    }
  }

  private void serviceREST(final ServiceManifest manifest, final HttpServletRequest request, final ContainerRequestContext requestContext, final HttpServletResponse response) throws IOException, ServletException {
    final Object content = manifest.service(request, response, requestContext);

    if (content == null)
      return;

    if (content instanceof JSObject) {
      // NOTE: This may throw a EncodeException, and should thus be outside the
      // NOTE: try (response) block, otherwise the response will be committed
      // NOTE: before we can set a HTTP 500
      final String json = content.toString();
      try (final Writer writer = response.getWriter()) {
        writer.write(json);
        writer.flush();
      }

      return;
    }

    if (content instanceof String) {
      try (final Writer writer = response.getWriter()) {
        writer.write((String)content);
      }
    }

    if (content instanceof byte[]) {
      try (final OutputStream out = response.getOutputStream()) {
        out.write((byte[])content);
      }
    }

    throw new WebApplicationException("Unexpected content return type for " + request.getMethod() + " in " + this.getClass());
  }

  @Override
  protected final void service(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
    try {
      final ContainerRequestContext requestContext = new ContainerRequestContextImpl(request);
      final ContainerResponseContextImpl responseContext = new ContainerResponseContextImpl(response);

      runPreMatchRequestFilters(requestContext, request);
      runPreMatchResponseFilters(requestContext, responseContext, request);

      final ServiceManifest manifest = filterAndMatch(requestContext, responseContext);

      runPostMatchRequestFilters(requestContext, request);

      if (manifest == null)
        throw new NotAllowedException();

      serviceREST(manifest, request, requestContext, response);

      final Produces produces = manifest.getMatcher(Produces.class).getAnnotation();
      if (produces != null)
        response.setHeader("Content-Type", org.safris.commons.lang.Arrays.toString(produces.value(), ","));

      runPostMatchResponseFilters(requestContext, responseContext, request);
    }
    catch (final IOException | ServletException e) {
      throw e;
    }
    catch (final Throwable t) {
      if (t.getCause() instanceof IOException) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t.getCause().getMessage());
        throw (IOException)t.getCause();
      }
      else if (t.getCause() instanceof ServletException) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t.getCause().getMessage());
        throw (ServletException)t.getCause();
      }
      else if (t instanceof WebApplicationException) {
        final WebApplicationException e = (WebApplicationException)t;
        response.sendError(e.getStatus().getStatusCode(), t.getMessage());
        if (e.getStatus() == javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
          throw t;
      }
      else {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t.getMessage());
        throw t;
      }
    }
  }
}