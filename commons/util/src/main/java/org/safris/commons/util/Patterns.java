package org.safris.commons.util;

import java.util.regex.Pattern;

public class Patterns {
  public static String[] getGroupNames(final Pattern pattern) {
    if (pattern == null)
      return null;

    return getGroupNames(pattern.toString(), 0, 0);
  }

  private static String[] getGroupNames(final String regex, final int index, final int depth) {
    final int start = regex.indexOf("(?<", index);
    if (start < 0)
      return depth == 0 ? null : new String[depth];

    final int end = regex.indexOf('>', start + 3);
    if (end < 0)
      throw new IllegalArgumentException("Malformed pattern after index = " + (start + 3));

    final String name = regex.substring(start + 3, end);
    final String[] names = getGroupNames(regex, end + 1, depth + 1);
    names[depth] = name;
    return names;
  }

  private Patterns() {
  }
}