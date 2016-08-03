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

public class ContainerResponseContextImpl extends ContainerContextImpl implements ContainerResponseContext {
  private final HttpServletResponse response;
  private final HeaderMap3 headers;
  // FIXME: Link response setHeader() to the httpHeaders
  private final HttpHeaders httpHeaders;

  private OutputStream outputStream;
  private Object entity;

  public ContainerResponseContextImpl(final HttpServletResponse response) {
    super(response.getLocale());
    this.response = response;
    this.headers = new HeaderMap3(response);
    this.httpHeaders = new HttpHeadersImpl(headers);
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
  public Response.StatusType getStatusInfo() {
    return Response.Status.fromStatusCode(response.getStatus());
  }

  @Override
  public void setStatusInfo(final Response.StatusType statusInfo) {
    response.setStatus(statusInfo.getStatusCode());
  }

  @Override
  protected HttpHeaders getHttpHeaders() {
    return httpHeaders;
  }

  @Override
  public MultivaluedMap<String,Object> getHeaders() {
    return headers.getMirroredMap();
  }

  @Override
  public MultivaluedMap<String,String> getStringHeaders() {
    return headers;
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
    return entity != null;
  }

  @Override
  public Object getEntity() {
    return entity;
  }

  @Override
  public Class<?> getEntityClass() {
    return entity == null ? null : entity.getClass();
  }

  @Override
  public Type getEntityType() {
    return getEntityClass().getGenericSuperclass();
  }

  @Override
  public void setEntity(final Object entity) {
    this.setEntity(entity, null, null);
  }

  @Override
  public void setEntity(final Object entity, final Annotation[] annotations, final MediaType mediaType) {
    this.entity = entity;
    getHeaders().putSingle(HttpHeaders.CONTENT_TYPE, mediaType);
  }

  @Override
  public Annotation[] getEntityAnnotations() {
    throw new UnsupportedOperationException();
  }

  @Override
  public OutputStream getEntityStream() {
    return outputStream;
  }

  @Override
  public void setEntityStream(final OutputStream outputStream) {
    this.outputStream = outputStream;
  }
}