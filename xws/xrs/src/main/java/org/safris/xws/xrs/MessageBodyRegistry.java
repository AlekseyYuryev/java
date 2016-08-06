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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.safris.xws.xrs.util.MediaTypes;

public class MessageBodyRegistry {
  private final List<Entry<? extends MessageBodyReader<?>>> messageBodyReaders = new ArrayList<Entry<? extends MessageBodyReader<?>>>();
  private final List<Entry<? extends MessageBodyWriter<?>>> messageBodyWriters = new ArrayList<Entry<? extends MessageBodyWriter<?>>>();

  private class Entry<T> {
    private final MediaType[] allowedTypes;
    private final T object;

    public Entry(final Class<? extends T> cls) {
      final Consumes consumes = cls.getAnnotation(Consumes.class);
      this.allowedTypes = consumes == null ? new MediaType[] {MediaType.WILDCARD_TYPE} : MediaTypes.parse(consumes.value());
      try {
        this.object = cls.newInstance();
      }
      catch (final InstantiationException | IllegalAccessException e) {
        throw new WebApplicationException(e);
      }
    }

    public boolean matches(final MediaType mediaType) {
      return MediaTypes.matches(mediaType, allowedTypes);
    }

    public T getObject() {
      return object;
    }
  }

  public void addMessageBodyReader(final Class<? extends MessageBodyReader<?>> messageBodyReader) {
    messageBodyReaders.add(new Entry<MessageBodyReader<?>>(messageBodyReader));
  }

  public void addMessageBodyWriter(final Class<? extends MessageBodyWriter<?>> messageBodyWriter) {
    messageBodyWriters.add(new Entry<MessageBodyWriter<?>>(messageBodyWriter));
  }

  public MessageBodyWriter<?> getMessageBodyWriter(final Object object, final MediaType mediaType) {
    for (final Entry<? extends MessageBodyWriter<?>> entry : messageBodyWriters)
      if (entry.matches(mediaType) && entry.getObject().isWriteable(object.getClass(), object.getClass().getGenericSuperclass(), object.getClass().getAnnotations(), mediaType))
        return entry.object;

    return null;
  }

  public MessageBodyReader<?> getMessageBodyReader(final Class<?> parameterType, final MediaType mediaType) {
    for (final Entry<? extends MessageBodyReader<?>> entry : messageBodyReaders)
      if (entry.matches(mediaType) && entry.getObject().isReadable(parameterType, parameterType.getGenericSuperclass(), parameterType.getAnnotations(), mediaType))
        return entry.object;

    return null;
  }
}