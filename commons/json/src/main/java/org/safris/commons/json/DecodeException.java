package org.safris.commons.json;

public class DecodeException extends Exception {
  private static final long serialVersionUID = -1234230677110958751L;

  public DecodeException(final JSObject jsObject) {
    this(null, jsObject, null);
  }

  public DecodeException(final String message, final JSObject jsObject) {
    this(message, jsObject, null);
  }

  public DecodeException(final JSObject jsObject, final Throwable cause) {
    this(null, jsObject, cause);
  }

  public DecodeException(final String message, final JSObject jsObject, final Throwable cause) {
    super(message != null ? (jsObject != null ? message + "\n" + jsObject._bundle().getSpec() : message) : (jsObject != null ? jsObject._bundle().getSpec() : null), cause);
  }
}