package org.safris.xws.xrs;

import javax.ws.rs.core.Response;

public class WebApplicationException extends RuntimeException {
  private static final long serialVersionUID = 1244153460891393863L;

  private final Response.Status status;

  public WebApplicationException() {
    this(null, Response.Status.INTERNAL_SERVER_ERROR, null);
  }

  public WebApplicationException(final Response.Status status) {
    this(null, status, null);
  }

  public WebApplicationException(final String message, final Response.Status status) {
    this(message, status, null);
  }

  public WebApplicationException(final String message) {
    this(message, Response.Status.INTERNAL_SERVER_ERROR, null);
  }

  public WebApplicationException(final Throwable cause) {
    this(null, Response.Status.INTERNAL_SERVER_ERROR, cause);
  }

  public WebApplicationException(final String message, final Throwable cause) {
    this(message, Response.Status.INTERNAL_SERVER_ERROR, cause);
  }

  public WebApplicationException(final String message, final Response.Status status, final Throwable cause) {
    super(message, cause);
    this.status = status;
  }

  public Response.Status getStatus() {
    return status;
  }
}
