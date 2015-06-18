/* Copyright (c) 2008 Seva Safris
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

package org.safris.commons.lang;

public final class Paths {
  public static boolean isAbsolute(final String path) {
    if (path == null)
      throw new NullPointerException();

    return path.charAt(0) == '/' || (Character.isLetter(path.charAt(0)) && path.charAt(1) == ':' && path.charAt(2) == '\\' && Character.isLetter(path.charAt(3)));
  }

  public static String canonicalize(String path) {
    if (path == null)
      return null;

    if (path.endsWith(".."))
      path = path + "/";

    // This removes all redundant "//" sequences.
    if (path.contains("://"))
      path = path.substring(0, 7) + path.substring(7).replace("//", "/");
    else
      path = path.replace("//", "/");

    path = path.replace("/./", "");
    if (path.endsWith("/."))
      path = path.substring(0, path.length() - 2);

    // Process "/../" correctly. This probably isn't very efficient in
    // the general case, but it's probably not bad most of the time.
    int index;
    while ((index = path.indexOf("/../")) != -1) {
      // Strip of the previous directory - if it exists.
      final int previous = path.lastIndexOf('/', index - 1);
      if (previous != -1)
        path = path.substring(0, previous) + path.substring(index + 3);
      else
        return path.substring(index + 4);
    }

    return path;
  }

  public static String relativePath(final String dir, final String file) {
    if (dir == null || file == null)
      return null;

    final String filePath = Paths.canonicalize(file);
    final String dirPath = Paths.canonicalize(dir);

    return !filePath.startsWith(dirPath) ? filePath : filePath.length() == dirPath.length() ? "" : filePath.substring(dirPath.length() + 1);
  }

  public static String getParent(String url) {
    if (url == null)
      return null;

    url = canonicalize(url);
    final int separator = url.lastIndexOf('/');
    return separator > 0 ? url.substring(0, separator) : url;
  }

  public static String getName(String url) {
    if (url == null)
      return null;

    if (url.length() == 0)
      return "";

    if (url.endsWith("/"))
      url = url.substring(0, url.length() - 1);

    final int separator = url.lastIndexOf('/');
    return separator != -1 ? url.substring(separator + 1) : url;
  }

  private Paths() {
  }
}