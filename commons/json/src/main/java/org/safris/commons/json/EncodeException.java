package org.safris.commons.json;

public class EncodeException extends RuntimeException {
  private static final long serialVersionUID = -5907473656780591942L;

  public EncodeException(final JSObject jsObject) {
    this(null, jsObject, null);
  }

  public EncodeException(final String message, final JSObject jsObject) {
    this(message, jsObject, null);
  }

  public EncodeException(final JSObject jsObject, final Throwable cause) {
    this(null, jsObject, cause);
  }

  public EncodeException(final String message, final JSObject jsObject, final Throwable cause) {
    super(message != null ? (jsObject != null ? message + "\n" + jsObject.bundle().getSpec() : message) : (jsObject != null ? jsObject.bundle().getSpec() : null), cause);
  }
}