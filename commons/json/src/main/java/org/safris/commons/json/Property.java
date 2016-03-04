package org.safris.commons.json;

import org.safris.commons.json.validator.Validator;

public class Property<T> {
  private static <T>String[] validate(final T value, final Validator<T>[] validators, final int index, final int depth) {
    if (index == validators.length)
      return new String[depth];

    final String error = validators[index].validate(value);
    final String[] errors = validate(value, validators, index + 1, error != null ? depth + 1 : depth);
    if (error != null)
      errors[depth] = error;

    return errors;
  }

  private final JSObject jsObject;
  private final String name;
  private final Validator<T>[] validators;
  private T value;

  @SafeVarargs
  public Property(final JSObject jsObject, final String name, final Validator<T> ... validators) {
    this.jsObject = jsObject;
    this.name = name;
    this.validators = validators;
  }

  public T value() {
    return value;
  }

  public void value(final T value) {
    this.value = value;
  }

  private String validate() {
    final String[] errors = validate(value, validators, 0, 0);
    if (errors.length == 0)
      return null;

    String message = "";
    for (final String error : errors)
      message += "\n\"" + name + "\" " + error;

    return message.substring(1);
  }

  @SuppressWarnings("unchecked")
  public T encode() throws EncodeException {
    final String error = validate();
    if (error != null)
      throw new EncodeException(error, jsObject);

    if (value instanceof Number) {
      final Number number = (Number)value;
      return (T)(number.intValue() == number.doubleValue() ? String.valueOf(number.intValue()) : String.valueOf(number.doubleValue()));
    }

    return value;
  }

  public void decode() throws DecodeException {
    final String error = validate();
    if (error != null)
      throw new DecodeException(error, jsObject);
  }
}