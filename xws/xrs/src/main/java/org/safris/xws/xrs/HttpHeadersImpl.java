package org.safris.xws.xrs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.safris.commons.lang.Numbers;
import org.safris.commons.util.Collections;

public class HttpHeadersImpl implements HttpHeaders {
  private final MultivaluedMap<String,String> headers;

  public HttpHeadersImpl(final MultivaluedMap<String,String> headers) {
    this.headers = headers;
  }

  public HttpHeadersImpl(final HttpServletRequest request) {
    this(new MultivaluedHashMap<String,String>());
    final Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      final String headerName = headerNames.nextElement();
      final Enumeration<String> enumeration = request.getHeaders(headerName);
      while (enumeration.hasMoreElements())
        headers.add(headerName, enumeration.nextElement());
    }
  }

  public HttpHeadersImpl(final HttpServletResponse response) {
    this(new MultivaluedHashMap<String,String>());
    final Collection<String> headerNames = response.getHeaderNames();
    for (final String headerName : headerNames) {
      final Collection<String> headerValues = response.getHeaders(headerName);
      for (final String headerValue : headerValues)
        headers.add(headerName, headerValue);
    }
  }

  public HttpHeadersImpl(final ContainerRequestContext requestContext) {
    this(requestContext.getHeaders());
  }

  @Override
  public List<String> getRequestHeader(final String name) {
    return headers.get(name);
  }

  @Override
  public String getHeaderString(final String name) {
    return Collections.toString(getRequestHeader(name), ",");
  }

  @Override
  public MultivaluedMap<String,String> getRequestHeaders() {
    return headers;
  }

  @Override
  public List<MediaType> getAcceptableMediaTypes() {
    final List<String> accepts = getRequestHeader("Accept");
    if (accepts == null)
      return java.util.Collections.unmodifiableList(java.util.Collections.singletonList(MediaType.WILDCARD_TYPE));

    final List<MediaType> mediaTypes = new ArrayList<MediaType>();
    // FIXME: MediaType.valueOf(), subtype, charset
    for (final String accept : accepts)
      mediaTypes.add(new MediaType(accept, null));

    return mediaTypes;
  }

  @Override
  public List<Locale> getAcceptableLanguages() {
    throw new UnsupportedOperationException();
  }

  @Override
  public MediaType getMediaType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Locale getLanguage() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String,Cookie> getCookies() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Date getDate() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getLength() {
    final String contentLength = headers.getFirst("Content-Length");
    return contentLength != null && Numbers.isNumber(contentLength) ? Integer.parseInt(contentLength) : null;
  }
}