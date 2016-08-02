package org.safris.xws.xrs;

import java.util.List;
import java.util.Map;

import org.safris.commons.util.MirroredArrayList;

public class HeaderMap extends MirroredMultivaluedHashMap<String,Object,String> implements Cloneable {
  private static final long serialVersionUID = -424669813370868690L;

  public HeaderMap() {
    super(new MirroredArrayList.Mirror<Object,String>() {
      @Override
      public String reflect(final Object value) {
        return HeaderDelegate.headerDelegateConvert(Object.class, String.class, value);
      }
    }, new MirroredArrayList.Mirror<String,Object>() {
      @Override
      public Object reflect(final String value) {
        return HeaderDelegate.headerDelegateConvert(String.class, Object.class, value);
      }
    });
  }

  @SuppressWarnings("unchecked")
  public HeaderMap(final HeaderMap copy) {
    super(copy.getMirror(), ((MirroredMultivaluedHashMap<String,String,Object>)copy.getMirroredMap()).getMirror());
    for (final Map.Entry<String,List<Object>> entry : entrySet())
      for (final Object value : entry.getValue())
        add(entry.getKey(), value);
  }

  @Override
  public HeaderMap clone() {
    return new HeaderMap(this);
  }
}