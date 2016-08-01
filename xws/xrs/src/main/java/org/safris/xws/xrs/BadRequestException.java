package org.safris.xws.xrs;

import javax.ws.rs.core.Response;

public class BadRequestException extends WebApplicationException {
  private static final long serialVersionUID = 2348747835507134850L;

  public BadRequestException() {
    this(null, null);
  }

  public BadRequestException(final String message) {
    this(message, null);
  }

  public BadRequestException(final Throwable cause) {
    this(null, cause);
  }

  public BadRequestException(final String message, final Throwable cause) {
    super(message, Response.Status.BAD_REQUEST, cause);
  }
}
