package org.safris.xws.xrs;

import javax.ws.rs.core.Response;

public class NotAllowedException extends WebApplicationException {
  private static final long serialVersionUID = 6038228727480669669L;

  public NotAllowedException() {
    this(null, null);
  }

  public NotAllowedException(final String message) {
    this(message, null);
  }

  public NotAllowedException(final Throwable cause) {
    this(null, cause);
  }

  public NotAllowedException(final String message, final Throwable cause) {
    super(message, Response.Status.METHOD_NOT_ALLOWED, cause);
  }
}
