package org.safris.xws.xrs;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

public class ContainerResponseContextImpl extends ContainerContextImpl implements ContainerResponseContext {
  private final HttpServletResponse response;

  public ContainerResponseContextImpl(final HttpServletResponse response) {
    super(response.getLocale());
    this.response = response;
  }

  @Override
  public int getStatus() {
    return response.getStatus();
  }

  @Override
  public void setStatus(final int code) {
    response.setStatus(code);
  }

  @Override
  public StatusType getStatusInfo() {
    return Response.Status.fromStatusCode(response.getStatus());
  }

  @Override
  public void setStatusInfo(final StatusType statusInfo) {
    throw new UnsupportedOperationException();
  }

  private HttpHeaders headers = null;

  @Override
  protected HttpHeaders getHttpHeaders() {
    return headers == null ? headers = new HttpHeadersImpl(response) : headers;
  }

  @Override
  public MultivaluedMap<String,Object> getHeaders() {
    throw new UnsupportedOperationException();
  }

  @Override
  public MultivaluedMap<String,String> getStringHeaders() {
    return getHttpHeaders().getRequestHeaders();
  }

  @Override
  public Set<String> getAllowedMethods() {
    return new HashSet<String>(response.getHeaders(HttpHeaders.ALLOW));
  }

  @Override
  public Map<String,NewCookie> getCookies() {
    throw new UnsupportedOperationException();
  }

  @Override
  public EntityTag getEntityTag() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Date getLastModified() {
    final String date = getHttpHeaders().getHeaderString(HttpHeaders.LAST_MODIFIED);
    try {
      return date == null ? null : dateFormat.get().parse(date);
    }
    catch (final ParseException e) {
      // FIXME!
      throw new RuntimeException(e);
    }
  }

  @Override
  public URI getLocation() {
    final String location = getHttpHeaders().getHeaderString(HttpHeaders.LOCATION);
    return location == null ? null : URI.create(location);
  }

  @Override
  public Set<Link> getLinks() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasLink(final String relation) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Link getLink(final String relation) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Builder getLinkBuilder(final String relation) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasEntity() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object getEntity() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Class<?> getEntityClass() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Type getEntityType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setEntity(final Object entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setEntity(final Object entity, final Annotation[] annotations, final MediaType mediaType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Annotation[] getEntityAnnotations() {
    throw new UnsupportedOperationException();
  }

  @Override
  public OutputStream getEntityStream() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setEntityStream(final OutputStream outputStream) {
    throw new UnsupportedOperationException();
  }
}