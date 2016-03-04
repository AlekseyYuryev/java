package org.safris.commons.json;

public class Property<T> {
  private final JSObject jsObject;
  private final Binding<T> binding;
  private T value;

  public Property(final JSObject jsObject, final Binding<T> binding) {
    this.jsObject = jsObject;
    this.binding = binding;
  }

  public T value() {
    return value;
  }

  public void value(final T value) {
    this.value = value;
  }

  @SuppressWarnings("unchecked")
  public T encode() throws EncodeException {
    final String error = binding.validate(value);
    if (error != null)
      throw new EncodeException(error, jsObject);

    if (value instanceof Number) {
      final Number number = (Number)value;
      return (T)(number.intValue() == number.doubleValue() ? String.valueOf(number.intValue()) : String.valueOf(number.doubleValue()));
    }

    return value;
  }

  public void decode() throws DecodeException {
    final String error = binding.validate(value);
    if (error != null)
      throw new DecodeException(error, jsObject);
  }
}