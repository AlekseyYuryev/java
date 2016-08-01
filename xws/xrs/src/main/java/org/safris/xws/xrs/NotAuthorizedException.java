package org.safris.xws.xrs;

import javax.ws.rs.core.Response;

public class NotAuthorizedException extends WebApplicationException {
  private static final long serialVersionUID = -8937017842885716570L;

  public NotAuthorizedException(final String message) {
    super(message, Response.Status.UNAUTHORIZED, null);
  }
}
