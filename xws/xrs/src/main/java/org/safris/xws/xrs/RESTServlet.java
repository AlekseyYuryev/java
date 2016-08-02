package org.safris.xws.xrs;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.PackageNotFoundException;

@WebServlet("/*")
public final class RESTServlet extends RegisteringRESTServlet {
  private static final long serialVersionUID = 3700080355780006441L;
  private static final Logger logger = Logger.getLogger(RESTServlet.class.getName());

  static {
    try {
      Class.forName(RuntimeDelegateImpl.class.getName());
    }
    catch (final ClassNotFoundException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  @SuppressWarnings("unchecked")
  public RESTServlet() {
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
  }

  private static void serviceREST(final ServiceManifest manifest, final ContainerRequestContext requestContext, final ContainerResponseContext responseContext, final ClientResponse clientResponse, final InjectionContext injectionContext) throws IOException, ServletException {
    final Object content = manifest.service(requestContext, injectionContext);

    if (content != null)
      responseContext.setEntity(content);
  }

  private void wrappedService(final WrappedRequest request, final HttpServletResponse response) throws IOException, ServletException {
    try {
      final ContainerResponseContext containerResponseContext = new ContainerResponseContextImpl(response);

      final ClientResponse clientResponse = new ClientResponse(response, containerResponseContext);
      final ContainerRequestContext containerRequestContext; // NOTE: This weird construct is done this way to at least somehow make the two object cohesive
      request.setRequestContext(containerRequestContext = new ContainerRequestContextImpl(request, clientResponse));

      final InjectionContext injectionContext = InjectionContext.createInjectionContext();
      injectionContext.addInjectableObject(request);
      injectionContext.addInjectableObject(response);
      injectionContext.addInjectableObject(containerRequestContext);
      injectionContext.addInjectableObject(containerResponseContext);

      runPreMatchRequestFilters(containerRequestContext, injectionContext);
      runPreMatchResponseFilters(containerRequestContext, containerResponseContext, injectionContext);

      if (clientResponse.getResponse() != null) {
        clientResponse.commit();
        return;
      }

      final ServiceManifest manifest; // NOTE: This weird construct is done this way to at least somehow make the two object cohesive
      request.setServiceManifest(manifest = filterAndMatch(containerRequestContext));

      runPostMatchRequestFilters(containerRequestContext, injectionContext);

      if (manifest == null)
        throw new NotFoundException();

      serviceREST(manifest, containerRequestContext, containerResponseContext, clientResponse, injectionContext);
      final Produces produces = manifest.getMatcher(Produces.class).getAnnotation();
      if (produces != null)
        response.setHeader(HttpHeaders.CONTENT_TYPE, org.safris.commons.lang.Arrays.toString(produces.value(), ","));

      runPostMatchResponseFilters(containerRequestContext, containerResponseContext, injectionContext);
      clientResponse.commit();
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
      else if (t instanceof ClientErrorException) {
        final ClientErrorException e = (ClientErrorException)t;
        response.sendError(e.getResponse().getStatus(), t.getMessage());
      }
      else if (t instanceof WebApplicationException) {
        final WebApplicationException e = (WebApplicationException)t;
        response.sendError(e.getResponse().getStatus(), t.getMessage());
        if (e.getResponse().getStatusInfo() == javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
          throw t;
      }
      else {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t.getMessage());
        throw t;
      }
    }
  }

  @Override
  protected final void service(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
    wrappedService(new WrappedRequest(request) {
      // NOTE: Check for the existence of the @Consumes header, and subsequently the Content-Type header in the request,
      // NOTE: only if data is expected (i.e. GET, HEAD, DELETE, OPTIONS methods will not have a body and should thus not
      // NOTE: expect a Content-Type header from the request)
      private void checkContentType() {
        if (getServiceManifest() != null && !getServiceManifest().checkHeader(HttpHeaders.CONTENT_TYPE, Consumes.class, getRequestContext()))
          throw new BadRequestException("Client call to " + request.getRequestURI() + " has data and is missing Content-Type header");
      }

      @Override
      public ServletInputStream getInputStream() throws IOException {
        checkContentType();
        return request.getInputStream();
      }

      @Override
      public BufferedReader getReader() throws IOException {
        checkContentType();
        return request.getReader();
      }
    }, response);
  }
}