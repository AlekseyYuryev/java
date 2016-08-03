package org.safris.xws.xrs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.safris.commons.util.MirroredList;

public class HeaderMap extends MirroredMultivaluedHashMap<String,String,Object> implements Cloneable {
  private static final long serialVersionUID = -424669813370868690L;

  public HeaderMap() {
    super(HashMap.class, ArrayList.class, new MirroredList.Mirror<String,Object>() {
      @Override
      public Object reflect(final String value) {
        return value == null ? null : MediaType.valueOf(value);
      }
    }, new MirroredList.Mirror<Object,String>() {
      @Override
      public String reflect(final Object value) {
        if (value == null)
          return null;

        if (value instanceof MediaType)
          return value.toString();

        throw new IllegalArgumentException("Unexpected type: " + value.getClass());
      }
    });
  }

  @SuppressWarnings("unchecked")
  public HeaderMap(final HeaderMap copy) {
    super(HashMap.class, ArrayList.class, copy.getMirror(), ((MirroredMultivaluedHashMap<String,Object,String>)copy.getMirroredMap()).getMirror());
    for (final Map.Entry<String,List<String>> entry : entrySet())
      for (final String value : entry.getValue())
        add(entry.getKey(), value);
  }

  @Override
  public HeaderMap clone() {
    return new HeaderMap(this);
  }
}