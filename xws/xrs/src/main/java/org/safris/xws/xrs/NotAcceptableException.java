package org.safris.xws.xrs;

import javax.ws.rs.core.Response;

public class NotAcceptableException extends WebApplicationException {
  private static final long serialVersionUID = 6038228727480669669L;

  public NotAcceptableException() {
    this(null, null);
  }

  public NotAcceptableException(final String message) {
    this(message, null);
  }

  public NotAcceptableException(final Throwable cause) {
    this(null, cause);
  }

  public NotAcceptableException(final String message, final Throwable cause) {
    super(message, Response.Status.NOT_ACCEPTABLE, cause);
  }
}
