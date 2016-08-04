package org.safris.xws.xrs;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

public class ResponseImpl extends Response {
  private final Response.Status status;
  private final Object entity;
  private final HeaderMap headers;
  private boolean closed;

  public ResponseImpl(final Response.Status status, final HeaderMap headers, final Object entity) {
    this.status = status;
    this.headers = headers;
    this.entity = entity;
  }

  public ResponseImpl(final Response.Status status, final HeaderMap headers) {
    this(status, headers, null);
  }

  @Override
  public int getStatus() {
    return status.getStatusCode();
  }

  @Override
  public StatusType getStatusInfo() {
    return status;
  }

  @Override
  public Object getEntity() {
    return entity;
  }

  @Override
  public <T>T readEntity(final Class<T> entityType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T readEntity(final GenericType<T> entityType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T readEntity(final Class<T> entityType, final Annotation[] annotations) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T readEntity(final GenericType<T> entityType, final Annotation[] annotations) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasEntity() {
    return entity != null;
  }

  @Override
  public boolean bufferEntity() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void close() {
    this.closed = true;
  }

  @Override
  public MediaType getMediaType() {
    return (MediaType)getMetadata().getFirst(HttpHeaders.CONTENT_TYPE);
  }

  @Override
  public Locale getLanguage() {
    return headers.getLanguage();
  }

  @Override
  public int getLength() {
    return headers.getLength();
  }

  @Override
  public Set<String> getAllowedMethods() {
    return headers.getAllowedMethods();
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
  public Date getDate() {
    return null;
  }

  @Override
  public Date getLastModified() {
    return headers.getLastModified();
  }

  @Override
  public URI getLocation() {
    return headers.getLocation();
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
  public MultivaluedMap<String,Object> getMetadata() {
    return headers.getMirroredMap();
  }

  @Override
  public MultivaluedMap<String,String> getStringHeaders() {
    return headers;
  }

  @Override
  public String getHeaderString(final String name) {
    return headers.getString(name);
  }
}