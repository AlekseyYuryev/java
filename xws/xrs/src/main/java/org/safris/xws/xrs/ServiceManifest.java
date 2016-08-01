package org.safris.xws.xrs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.safris.commons.io.Streams;
import org.safris.commons.lang.Strings;
import org.safris.xws.xjb.DecodeException;
import org.safris.xws.xjb.JSObject;

public class ServiceManifest {
  private static final Logger logger = Logger.getLogger(ServiceManifest.class.getName());

  private static boolean logMissingHeaderWarning(final String headerName, final String[] annotationValue) {
    logger.warning("Missing expected value for " + headerName + " header: " + Arrays.toString(annotationValue));
    return false;
  }

  private final HttpMethod httpMethod;
  private final Annotation securityAnnotation;
  private final Method method;
  private final Object object;
  private final PathPattern pathPattern;
  private final MediaTypeMatcher<Consumes> consumesMatcher;
  private final MediaTypeMatcher<Produces> producesMatcher;

  public ServiceManifest(final HttpMethod httpMethod, final Annotation securityAnnotation, final Method method, final Object object) {
    this.httpMethod = httpMethod;
    this.securityAnnotation = securityAnnotation != null ? securityAnnotation : new PermitAll() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return getClass();
      }
    };
    this.method = method;
    this.object = object;
    this.pathPattern = new PathPattern(method);
    this.consumesMatcher = new MediaTypeMatcher<Consumes>(method, Consumes.class);
    this.producesMatcher = new MediaTypeMatcher<Produces>(method, Produces.class);
  }

  public boolean matches(final ContainerRequestContext requestContext) {
    if (!httpMethod.value().toUpperCase().equals(requestContext.getMethod().toUpperCase()))
      return false;

    final String path = requestContext.getUriInfo().getPath();
    if (!pathPattern.matches(path))
      return false;

    final MediaType[] accept = MediaTypeUtil.parse(requestContext.getHeaders().get("Accept"));
    if (!producesMatcher.matches(accept))
      return false;

    final MediaType[] contentType = MediaTypeUtil.parse(requestContext.getHeaders().get("Content-Type"));
    if (!consumesMatcher.matches(contentType))
      return false;

    return true;
  }

  private Object[] getParameters(final Method method, final HttpServletRequest request, final HttpServletResponse response, final ContainerRequestContext requestContext) throws IOException {
    final Class<?>[] parameterTypes = method.getParameterTypes();
    final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    if (parameterTypes.length == 0)
      return null;

    final Map<String,String> pathParameters = getPathPattern().getParameters(requestContext.getUriInfo().getPath());
    final Object[] parameters = new Object[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      final Class<?> parameterType = parameterTypes[i];
      final Annotation[] annotations = parameterAnnotations[i];
      if (annotations.length > 0) {
        for (final Annotation annotation : annotations) {
          if (annotation.annotationType() == QueryParam.class) {
            parameters[i] = requestContext.getUriInfo().getQueryParameters().get(((QueryParam)annotation).value());
          }
          else if (annotation.annotationType() == PathParam.class) {
            final String pathParam = ((PathParam)annotation).value();
            parameters[i] = pathParameters.get(pathParam);
          }
          else if (annotation.annotationType() == Context.class) {
            if (parameterType == HttpServletRequest.class)
              parameters[i] = request;
            else if (parameterType == HttpServletResponse.class)
              parameters[i] = response;
            else if (parameterType == HttpHeaders.class)
              parameters[i] = new HttpHeadersImpl(requestContext);
            else
              throw new UnsupportedOperationException("Unsupported @Context type: " + parameterType.getName() + " on: " + method.getDeclaringClass().getName() + "." + method.getName() + "()");
          }
          else {
            throw new UnsupportedOperationException("Unexpected annotation type: " + annotation.annotationType().getName() + " on: " + method.getDeclaringClass().getName() + "." + method.getName() + "()");
          }
        }
      }
      else {
        if (parameterType == String.class) {
          parameters[i] = new String(Streams.getBytes(requestContext.getEntityStream()));
        }
        else if (parameterType == byte[].class) {
          parameters[i] = Streams.getBytes(requestContext.getEntityStream());
        }
        else if (JSObject.class.isAssignableFrom(parameterType)) {
          try {
            parameters[i] = JSObject.parse(parameterType, new InputStreamReader(requestContext.getEntityStream()));
          }
          catch (final DecodeException e) {
            throw new BadRequestException(e);
          }
        }
        else {
          throw new UnsupportedOperationException("Unexpected parameter type: " + parameterType.getName() + " on: " + method.getDeclaringClass().getName() + "." + method.getName() + "()");
        }
      }
    }

    return parameters;
  }

  private boolean checkHeader(final String headerName, final Class<? extends Annotation> annotationClass, final ContainerRequestContext requestContext, final HttpServletResponse response) {
    final Annotation annotation = getMatcher(annotationClass).getAnnotation();
    if (annotation == null) {
      final String message = "@" + annotationClass.getSimpleName() + " annotation missing for " + method.getDeclaringClass().getName() + "." + Strings.toTitleCase(requestContext.getMethod().toLowerCase()) + "()";
      if (annotationClass == Consumes.class)
        throw new RuntimeException(message);

      logger.warning(message);
      return true;
    }

    final String[] annotationValue = annotationClass == Produces.class ? ((Produces)annotation).value() : annotationClass == Consumes.class ? ((Consumes)annotation).value() : null;
    // FIXME: Order matters, and also the q value
    Arrays.sort(annotationValue);

    final String headerValue = requestContext.getHeaderString(headerName);
    if (headerValue == null || headerValue.length() == 0)
      return logMissingHeaderWarning(headerName, annotationValue);

    final String[] headerValueParts = headerValue.split(",");
    for (final String headerValuePart : headerValueParts)
      if (Arrays.binarySearch(annotationValue, headerValuePart) > -1)
        return true;

    return logMissingHeaderWarning(headerName, annotationValue);
  }

  private static void allow(final Annotation securityAnnotation, final ContainerRequestContext requestContext) {
    if (securityAnnotation instanceof DenyAll)
      throw new ForbiddenException("@DenyAll");

    if (securityAnnotation instanceof RolesAllowed)
      for (final String role : ((RolesAllowed)securityAnnotation).value())
        if (requestContext.getSecurityContext().isUserInRole(role))
          return;

    throw new ForbiddenException("@RolesAllowed(" + Arrays.toString(((RolesAllowed)securityAnnotation).value()) + ")");
  }

  public Object service(final HttpServletRequest request, final HttpServletResponse response, final ContainerRequestContext requestContext) throws ServletException, IOException {
    allow(securityAnnotation, requestContext);

    try {
      final Object[] parameters = getParameters(method, new HttpServletRequestWrapper(request) {
        // NOTE: Check for the existence of the @Consumes header, and subsequently the Content-Type header in the request,
        // NOTE: only if data is expected (i.e. GET, HEAD, DELETE, OPTIONS methods will not have a body and should thus not
        // NOTE: expect a Content-Type header from the request)
        private void checkContentType() {
          if (!checkHeader("Content-Type", Consumes.class, requestContext, response))
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
      }, response, requestContext);
      return parameters != null ? method.invoke(object, parameters) : method.invoke(object);
    }
    catch (final IllegalAccessException e) {
      throw new WebApplicationException("Service method for " + httpMethod + " is not accessible", e);
    }
    catch (final InvocationTargetException e) {
      // FIXME: Hmm, this is an interesting idea to help reduce the noise in Exceptions from dynamically invoked methods
      if (e.getCause() instanceof WebApplicationException)
        throw (WebApplicationException)e.getCause();

      if (e.getCause() instanceof ServletException)
        throw (ServletException)e.getCause();

      if (e.getCause() instanceof IOException)
        throw (IOException)e.getCause();

      throw new WebApplicationException(e);
    }
    catch (final IllegalArgumentException | IOException e) {
      throw new WebApplicationException(e);
    }
  }

  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  public PathPattern getPathPattern() {
    return pathPattern;
  }

  @SuppressWarnings("unchecked")
  public <T extends Annotation>MediaTypeMatcher<T> getMatcher(final Class<T> annotationClass) {
    return annotationClass == Consumes.class ? (MediaTypeMatcher<T>)consumesMatcher : annotationClass == Produces.class ? (MediaTypeMatcher<T>)producesMatcher : null;
  }

  public boolean isRestricted() {
    return securityAnnotation instanceof DenyAll || securityAnnotation instanceof RolesAllowed;
  }
}