package org.safris.commons.json.validator;

public interface Validator<T> {
  public String validate(final T value);
}