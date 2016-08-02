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
  public <T> T readEntity(final Class<T> entityType) {
    return null;
  }

  @Override
  public <T> T readEntity(final GenericType<T> entityType) {
    return null;
  }

  @Override
  public <T> T readEntity(final Class<T> entityType, final Annotation[] annotations) {
    return null;
  }

  @Override
  public <T> T readEntity(final GenericType<T> entityType, final Annotation[] annotations) {
    return null;
  }

  @Override
  public boolean hasEntity() {
    return entity != null;
  }

  @Override
  public boolean bufferEntity() {
    return false;
  }

  @Override
  public void close() {
  }

  @Override
  public MediaType getMediaType() {
    return (MediaType)getMetadata().getFirst(HttpHeaders.CONTENT_TYPE);
  }

  @Override
  public Locale getLanguage() {
    return null;
  }

  @Override
  public int getLength() {
    return 0;
  }

  @Override
  public Set<String> getAllowedMethods() {
    return null;
  }

  @Override
  public Map<String,NewCookie> getCookies() {
    return null;
  }

  @Override
  public EntityTag getEntityTag() {
    return null;
  }

  @Override
  public Date getDate() {
    return null;
  }

  @Override
  public Date getLastModified() {
    return null;
  }

  @Override
  public URI getLocation() {
    return null;
  }

  @Override
  public Set<Link> getLinks() {
    return null;
  }

  @Override
  public boolean hasLink(final String relation) {
    return false;
  }

  @Override
  public Link getLink(final String relation) {
    return null;
  }

  @Override
  public Builder getLinkBuilder(final String relation) {
    return null;
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
    return null;
  }
}