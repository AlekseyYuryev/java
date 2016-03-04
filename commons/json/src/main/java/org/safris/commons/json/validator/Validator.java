package org.safris.commons.json.validator;

public abstract class Validator<T> {
  public static <T>String[] validate(final Validator<T>[] validators, final T value) {
    return validate(validators, value, 0, 0);
  }

  private static <T>String[] validate(final Validator<T>[] validators, final T value, final int index, final int depth) {
    if (index == validators.length)
      return new String[depth];

    final String error = validators[index].validate(value);
    final String[] errors = validate(validators, value, index + 1, error != null ? depth + 1 : depth);
    if (error != null)
      errors[depth] = error;

    return errors;
  }

  public abstract String validate(final T value);
}