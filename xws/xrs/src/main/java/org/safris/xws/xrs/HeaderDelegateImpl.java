package org.safris.xws.xrs;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

public class HeaderDelegateImpl implements HeaderDelegate<MediaType> {
  @Override
  public MediaType fromString(final String value) {
    return MediaTypes.parse(value);
  }

  @Override
  public String toString(final MediaType value) {
    return value.toString();
  }
}