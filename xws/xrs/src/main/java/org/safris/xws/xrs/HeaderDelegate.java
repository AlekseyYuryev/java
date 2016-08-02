package org.safris.xws.xrs;

import java.util.List;

import javax.ws.rs.ext.RuntimeDelegate;

public class HeaderDelegate {
  public static <V,C>C headerDelegateConvert(final Class<V> vc, final Class<C> cc, final V value) {
    return null;
  }

  public <T>RuntimeDelegate.HeaderDelegate<T> getHeader(final List<T> t) {
    return null;
  }
}