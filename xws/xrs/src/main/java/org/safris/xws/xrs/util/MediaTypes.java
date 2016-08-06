/* Copyright (c) 2016 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.xws.xrs.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

public final class MediaTypes {
  // FIXME: Is this correct?
  public static boolean matches(final MediaType[] required, final MediaType[] tests) {
    for (final MediaType req : required)
      if (matches(req, tests))
        return true;

    return false;
  }

  public static boolean matches(final MediaType required, final MediaType[] tests) {
    for (final MediaType test : tests)
      if (matches(required, test))
        return true;

    return false;
  }

  public static boolean matches(final MediaType required, final MediaType test) {
    if (required == null || test == null)
      return true;

    if (!required.isWildcardType() && !test.isWildcardType() && !required.getType().equals(test.getType()))
        return false;

    if (!required.isWildcardSubtype() && !test.isWildcardSubtype() && !required.getSubtype().equals(test.getSubtype()))
        return false;

    for (final Map.Entry<String,String> entry : required.getParameters().entrySet()) {
      final String value = test.getParameters().get(entry.getKey());
      if (value != null && !value.equals(entry.getValue()))
          return false;
    }

    return true;
  }

  public static MediaType[] parse(final Collection<String> strings) {
    if (strings == null)
      return null;

    final MediaType[] mediaTypes = new MediaType[strings.size()];
    int i = 0;
    for (final String string : strings)
      mediaTypes[i++] = parse(string);

    return mediaTypes;
  }

  public static MediaType[] parse(final Enumeration<String> enumeration) {
    return parse(enumeration, 0);
  }

  private static MediaType[] parse(final Enumeration<String> enumeration, final int depth) {
    if (!enumeration.hasMoreElements())
      return new MediaType[depth];

    final MediaType mediaType = parse(enumeration.nextElement());
    final MediaType[] mediaTypes = parse(enumeration, depth + 1);
    mediaTypes[depth] = mediaType;
    return mediaTypes;
  }

  public static MediaType[] parse(final String ... strings) {
    final MediaType[] mediaTypes = new MediaType[strings.length];
    for (int i = 0; i < strings.length; i++)
      mediaTypes[i] = parse(strings[i]);

    return mediaTypes;
  }

  public static MediaType parse(final String string) {
    int start = string.indexOf("/");
    if (start == -1)
      return new MediaType(string.trim(), null);

    int semicolon = string.indexOf(";", start + 1);
    final String type = string.substring(0, start).trim();
    final String subtype = string.substring(start + 1, semicolon > -1 ? semicolon : string.length()).trim();
    if (semicolon < 0)
      return new MediaType(type, subtype);

    start = semicolon;
    final Map<String,String> parameters = new HashMap<String,String>();
    do {
      semicolon = string.indexOf(";", semicolon + 1);
      final String token = string.substring(start + 1, semicolon > 0 ? semicolon : string.length());
      final int eq = token.indexOf('=');
      if (eq >= 0)
        parameters.put(token.substring(0, eq).trim(), token.substring(eq + 1).trim());
    }
    while (semicolon > 0);

    return new MediaType(type, subtype, parameters);
  }

  private MediaTypes() {
  }
}