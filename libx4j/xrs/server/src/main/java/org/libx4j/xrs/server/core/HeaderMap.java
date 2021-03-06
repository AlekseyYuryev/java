/* Copyright (c) 2016 lib4j
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

package org.libx4j.xrs.server.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.lib4j.lang.Numbers;
import org.lib4j.util.Locales;
import org.libx4j.xrs.server.ext.DateHeaderDelegate;
import org.libx4j.xrs.server.util.MediaTypes;
import org.libx4j.xrs.server.util.MirroredMultivaluedHashMap;

public class HeaderMap extends MirroredMultivaluedHashMap<String,String,Object> {
  private static final long serialVersionUID = -424669813370868690L;

  public HeaderMap(final HttpServletResponse response) {
    super(ArrayList.class, new Function<String,Object>() {
      @Override
      public Object apply(final String value) {
        return value == null ? null : MediaType.valueOf(value);
      }
    }, new Function<Object,String>() {
      @Override
      public String apply(final Object value) {
        if (value == null)
          return null;

        if (value instanceof String)
          return (String)value;

        if (value instanceof MediaType)
          return value.toString();

        if (value instanceof Locale)
          return value.toString();

        if (value instanceof Date)
          return DateHeaderDelegate.format((Date)value);

        if (value instanceof URI)
          return value.toString();

        if (value instanceof CacheControl)
          return value.toString();

        throw new UnsupportedOperationException("Unsupported type: " + value.getClass());
      }
    });

    if (response != null)
      for (final String header : response.getHeaders(HttpHeaders.ALLOW))
        add(HttpHeaders.ALLOW, header);
  }

  public HeaderMap() {
    this((HttpServletResponse)null);
  }

  @SuppressWarnings("unchecked")
  public HeaderMap(final HeaderMap copy) {
    super(ArrayList.class, copy.getMirror(), ((MirroredMultivaluedHashMap<String,Object,String>)copy.getMirroredMap()).getMirror());
    for (final Map.Entry<String,List<String>> entry : entrySet())
      for (final String value : entry.getValue())
        add(entry.getKey(), value);
  }

  public String getString(final String header) {
    final List<String> values = get(header);
    if (values == null)
      return null;

    if (values.size() == 0)
      return "";

    final StringBuilder string = new StringBuilder();
    for (final String value : values)
      string.append(",").append(value);

    return string.substring(1);
  }

  public Set<String> getAllowedMethods() {
    return new HashSet<String>(get(HttpHeaders.ALLOW));
  }

  public Date getLastModified() {
    final String date = getFirst(HttpHeaders.LAST_MODIFIED);
    return date == null ? null : DateHeaderDelegate.parse(date);
  }

  public int getLength() {
    final String contentLength = getFirst(HttpHeaders.CONTENT_LENGTH);
    return contentLength != null && Numbers.isNumber(contentLength) ? Integer.parseInt(contentLength) : null;
  }

  public URI getLocation() {
    final String location = getFirst(HttpHeaders.LOCATION);
    return location == null ? null : URI.create(location);
  }

  public Locale getLanguage() {
    final String language = getFirst(HttpHeaders.CONTENT_LANGUAGE);
    return language == null ? null : Locales.parse(language);
  }

  public MediaType getMediaType() {
    return MediaTypes.parse(getFirst(HttpHeaders.CONTENT_TYPE));
  }

  @Override
  public HeaderMap clone() {
    return (HeaderMap)super.clone();
  }
}