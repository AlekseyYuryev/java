package org.safris.xws.xrs;

import javax.ws.rs.core.Response;

public class ForbiddenException extends WebApplicationException {
  private static final long serialVersionUID = -8029536221447545798L;

  public ForbiddenException(final String message) {
    super(message, Response.Status.FORBIDDEN, null);
  }
}
