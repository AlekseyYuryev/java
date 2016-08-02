package org.safris.xws.xrs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.safris.commons.util.Enumerations;

public class ContainerRequestContextImpl extends ContainerContextImpl implements ContainerRequestContext {
  private final HttpServletRequest request;
  private final ClientResponse response;
  private final UriInfo uriInfo;
  private InputStream input;

  public ContainerRequestContextImpl(final HttpServletRequest request, final ClientResponse response) {
    super(request.getLocale());
    this.response = response;
    this.request = request;
    this.uriInfo = new UriInfoImpl(request);
  }

  @Override
  public Object getProperty(final String name) {
    return request.getAttribute(name);
  }

  @Override
  public Collection<String> getPropertyNames() {
    return Enumerations.toList(String.class, request.getAttributeNames());
  }

  @Override
  public void setProperty(final String name, final Object object) {
    request.setAttribute(name, object);
  }

  @Override
  public void removeProperty(final String name) {
    request.removeAttribute(name);
  }

  @Override
  public UriInfo getUriInfo() {
    return uriInfo;
  }

  @Override
  public void setRequestUri(final URI requestUri) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setRequestUri(final URI baseUri, final URI requestUri) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Request getRequest() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getMethod() {
    return request.getMethod();
  }

  @Override
  public void setMethod(final String method) {
    throw new UnsupportedOperationException();
  }

  private HttpHeaders headers = null;

  @Override
  protected HttpHeaders getHttpHeaders() {
    return headers == null ? headers = new HttpHeadersImpl(request) : headers;
  }

  @Override
  public MultivaluedMap<String,String> getHeaders() {
    return getHttpHeaders().getRequestHeaders();
  }

  @Override
  public List<MediaType> getAcceptableMediaTypes() {
    return Arrays.asList(MediaTypeUtil.parse(request.getHeaders(HttpHeaders.ACCEPT)));
  }

  @Override
  public List<Locale> getAcceptableLanguages() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String,Cookie> getCookies() {
    return getHttpHeaders().getCookies();
  }

  @Override
  public boolean hasEntity() {
    throw new UnsupportedOperationException();
  }

  @Override
  public InputStream getEntityStream() {
    if (input != null)
      return input;

    try {
      return request.getInputStream();
    }
    catch (final IOException e) {
      throw new WebApplicationException(e);
    }
  }

  @Override
  public void setEntityStream(final InputStream input) {
    this.input = input;
  }

  private SecurityContext defaultSecurityContext;
  private SecurityContext securityContext;

  @Override
  public SecurityContext getSecurityContext() {
    return securityContext != null ? securityContext : defaultSecurityContext == null ? defaultSecurityContext = new DefaultSecurityContext(request) : defaultSecurityContext;
  }

  @Override
  public void setSecurityContext(final SecurityContext context) {
    this.securityContext = context;
  }

  @Override
  public void abortWith(final Response response) {
    this.response.setResponse(response);
  }
}