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

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.RuntimeDelegate;

@WebServlet("/*")
public final class RESTServlet extends RegisteringRESTServlet {
  private static final long serialVersionUID = 3700080355780006441L;

  static {
    System.setProperty(RuntimeDelegate.JAXRS_RUNTIME_DELEGATE_PROPERTY, RuntimeDelegateImpl.class.getName());
  }

  private static void serviceREST(final ServiceManifest manifest, final ContainerRequestContext requestContext, final ContainerResponseContext responseContext, final ClientResponse clientResponse, final ContextInjector injectionContext) throws IOException, ServletException {
    final Object content = manifest.service(requestContext, injectionContext);

    if (content != null)
      responseContext.setEntity(content);
  }

  private void wrappedService(final WrappedRequest request, final HttpServletResponse response) throws IOException, ServletException {
    try {
      final ContainerResponseContext containerResponseContext = new ContainerResponseContextImpl(response);

      final ClientResponse clientResponse = new ClientResponse(response, containerResponseContext);
      final ContainerRequestContext containerRequestContext; // NOTE: This weird construct is done this way to at least somehow make the two object cohesive
      final HttpHeaders httpHeaders = new HttpHeadersImpl(request);
      request.setRequestContext(containerRequestContext = new ContainerRequestContextImpl(request, httpHeaders, clientResponse));

      final ContextInjector injectionContext = ContextInjector.createInjectionContext(containerRequestContext, new RequestImpl(request.getMethod()), httpHeaders);

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