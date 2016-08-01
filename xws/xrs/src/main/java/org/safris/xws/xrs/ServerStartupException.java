package org.safris.xws.xrs;

public class ServerStartupException extends RuntimeException {
  private static final long serialVersionUID = 5582110107462054632L;

  public ServerStartupException(final String message) {
    this(message, null);
  }

  public ServerStartupException(final Throwable cause) {
    this(null, cause);
  }

  public ServerStartupException(final String message, final Throwable cause) {
    super(message, cause);
  }
}