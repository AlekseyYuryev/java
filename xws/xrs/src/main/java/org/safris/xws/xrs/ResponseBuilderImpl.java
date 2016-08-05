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

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Variant;

import org.safris.commons.util.Locales;

public class ResponseBuilderImpl extends ResponseBuilder {
  private final HeaderMap headers;
  private Object entity;

  public ResponseBuilderImpl(final ResponseBuilderImpl copy) {
    headers = copy.headers.clone();
  }

  public ResponseBuilderImpl() {
    headers = new HeaderMap();
  }

  @Override
  public Response build() {
    return new ResponseImpl(ResponseUtil.fromStatusCode(status), headers, entity);
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
    this.entity = entity;
    return this;
  }

  @Override
  public ResponseBuilder entity(final Object entity, final Annotation[] annotations) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder allow(final String ... methods) {
    for (final String method : methods)
      headers.add(HttpHeaders.ALLOW, method);

    return this;
  }

  @Override
  public ResponseBuilder allow(final Set<String> methods) {
    for (final String method : methods)
      headers.add(HttpHeaders.ALLOW, method);

    return this;
  }

  @Override
  public ResponseBuilder cacheControl(final CacheControl cacheControl) {
    headers.getMirroredMap().putSingle(HttpHeaders.CACHE_CONTROL, cacheControl);
    return this;
  }

  @Override
  public ResponseBuilder encoding(final String encoding) {
    headers.putSingle(HttpHeaders.CONTENT_ENCODING, encoding);
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
    headers.getMirroredMap().putSingle(HttpHeaders.CONTENT_LANGUAGE, Locales.parse(language));
    headers.putSingle(HttpHeaders.CONTENT_LANGUAGE, language);
    return this;
  }

  @Override
  public ResponseBuilder language(final Locale language) {
    headers.getMirroredMap().putSingle(HttpHeaders.CONTENT_LANGUAGE, language);
    return this;
  }

  @Override
  public ResponseBuilder type(final MediaType type) {
    headers.getMirroredMap().putSingle(HttpHeaders.CONTENT_TYPE, type);
    return this;
  }

  @Override
  public ResponseBuilder type(final String type) {
    headers.putSingle(HttpHeaders.CONTENT_TYPE, type);
    return this;
  }

  @Override
  public ResponseBuilder variant(final Variant variant) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder contentLocation(final URI location) {
    headers.getMirroredMap().putSingle(HttpHeaders.CONTENT_LOCATION, location);
    return this;
  }

  @Override
  public ResponseBuilder cookie(final NewCookie ... cookies) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder expires(final Date expires) {
    headers.getMirroredMap().putSingle(HttpHeaders.EXPIRES, expires);
    return this;
  }

  @Override
  public ResponseBuilder lastModified(final Date lastModified) {
    headers.getMirroredMap().putSingle(HttpHeaders.LAST_MODIFIED, lastModified);
    return this;
  }

  @Override
  public ResponseBuilder location(final URI location) {
    headers.getMirroredMap().putSingle(HttpHeaders.LOCATION, location);
    return this;
  }

  @Override
  public ResponseBuilder tag(final EntityTag tag) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder tag(final String tag) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder variants(final Variant ... variants) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder variants(final List<Variant> variants) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder links(final Link ... links) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder link(final URI uri, final String rel) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ResponseBuilder link(final String uri, final String rel) {
    throw new UnsupportedOperationException();
  }
}