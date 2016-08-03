package org.safris.xws.xrs;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Variant;

public class ResponseBuilderImpl extends ResponseBuilder {
  private final HeaderMap3 headers;

  public ResponseBuilderImpl(final ResponseBuilderImpl copy) {
    headers = copy.headers.clone();
  }

  public ResponseBuilderImpl(final HttpServletResponse httpServletResponse) {
    headers = new HeaderMap3(httpServletResponse);
  }

  @Override
  public Response build() {
    return new ResponseImpl(ResponseUtil.fromStatusCode(status), headers);
  }

  @Override
  public ResponseBuilder clone() {
    return new ResponseBuilderImpl(this);
  }

  private int status;

  @Override
  public ResponseBuilder status(final int status) {
    this.status = status;
    return this;
  }

  @Override
  public ResponseBuilder entity(final Object entity) {
    return this;
  }

  @Override
  public ResponseBuilder entity(final Object entity, final Annotation[] annotations) {
    return this;
  }

  @Override
  public ResponseBuilder allow(final String ... methods) {
    return this;
  }

  @Override
  public ResponseBuilder allow(final Set<String> methods) {
    return this;
  }

  @Override
  public ResponseBuilder cacheControl(final CacheControl cacheControl) {
    return this;
  }

  @Override
  public ResponseBuilder encoding(final String encoding) {
    return this;
  }

  @Override
  public ResponseBuilder header(final String name, final Object value) {
    headers.getMirroredMap().add(name, value);
    return this;
  }

  @Override
  public ResponseBuilder replaceAll(final MultivaluedMap<String,Object> headers) {
    final MultivaluedMap<String,Object> mirroredMap = this.headers.getMirroredMap();
    mirroredMap.clear();
    for (final Map.Entry<String,List<Object>> entry : headers.entrySet())
      mirroredMap.addAll(entry.getKey(), entry.getValue());

    return this;
  }

  @Override
  public ResponseBuilder language(final String language) {
    return this;
  }

  @Override
  public ResponseBuilder language(final Locale language) {
    return this;
  }

  @Override
  public ResponseBuilder type(final MediaType type) {
    return this;
  }

  @Override
  public ResponseBuilder type(final String type) {
    return this;
  }

  @Override
  public ResponseBuilder variant(final Variant variant) {
    return this;
  }

  @Override
  public ResponseBuilder contentLocation(final URI location) {
    return this;
  }

  @Override
  public ResponseBuilder cookie(final NewCookie ... cookies) {
    return this;
  }

  @Override
  public ResponseBuilder expires(final Date expires) {
    return this;
  }

  @Override
  public ResponseBuilder lastModified(final Date lastModified) {
    return this;
  }

  @Override
  public ResponseBuilder location(final URI location) {
    return this;
  }

  @Override
  public ResponseBuilder tag(final EntityTag tag) {
    return this;
  }

  @Override
  public ResponseBuilder tag(final String tag) {
    return this;
  }

  @Override
  public ResponseBuilder variants(final Variant ... variants) {
    return this;
  }

  @Override
  public ResponseBuilder variants(final List<Variant> variants) {
    return this;
  }

  @Override
  public ResponseBuilder links(final Link ... links) {
    return this;
  }

  @Override
  public ResponseBuilder link(final URI uri, final String rel) {
    return this;
  }

  @Override
  public ResponseBuilder link(final String uri, final String rel) {
    return this;
  }
}