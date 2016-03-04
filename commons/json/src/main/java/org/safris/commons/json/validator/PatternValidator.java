package org.safris.commons.json.validator;

import org.safris.commons.json.JSObject;

public class PatternValidator implements Validator<String> {
  private final String pattern;

  public PatternValidator(final String pattern) {
    this.pattern = pattern;
  }

  @Override
  public String validate(final String value) {
    return value == null || value.matches(pattern) ? null : "does not match pattern \"" + pattern + "\": \"" + value + "\"";
  }
}