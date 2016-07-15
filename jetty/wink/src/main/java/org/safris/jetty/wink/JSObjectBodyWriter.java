package org.safris.jetty.wink;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.safris.xrs.xjb.JSObject;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JSObjectBodyWriter implements MessageBodyWriter<JSObject> {
  @Override
  public long getSize(final JSObject t, final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return t.toString().length();
  }

  @Override
  public boolean isWriteable(final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return JSObject.class.isAssignableFrom(rawType);
  }

  @Override
  public void writeTo(final JSObject t, final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String,Object> httpHeaders, final OutputStream entityStream) throws IOException {
    entityStream.write(t.toString().getBytes());
    entityStream.flush();
  }
}