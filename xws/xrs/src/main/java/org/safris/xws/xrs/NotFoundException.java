package org.safris.xws.xrs;

import javax.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {
  private static final long serialVersionUID = -8688087264263607492L;

  public NotFoundException() {
    this(null, null);
  }

  public NotFoundException(final String message) {
    this(message, null);
  }

  public NotFoundException(final Throwable cause) {
    this(null, cause);
  }

  public NotFoundException(final String message, final Throwable cause) {
    super(message, Response.Status.NOT_FOUND, cause);
  }
}
