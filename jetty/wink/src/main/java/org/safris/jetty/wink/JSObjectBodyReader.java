package org.safris.jetty.wink;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.safris.commons.json.DecodeException;
import org.safris.commons.json.JSObject;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JSObjectBodyReader implements MessageBodyReader<JSObject> {
  @Override
  public boolean isReadable(final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return JSObject.class.isAssignableFrom(rawType);
  }

  @Override
  public JSObject readFrom(final Class<JSObject> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String,String> httpHeaders, final InputStream entityStream) throws IOException {
    try {
      return JSObject.parse(rawType, entityStream);
    }
    catch (final DecodeException e) {
      throw new BadRequestException(e);
    }
  }
}