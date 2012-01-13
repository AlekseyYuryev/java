/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.net;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;
import org.safris.commons.lang.Paths;

public final class URLs {
  private static final Pattern URL_PATTERN = Pattern.compile("(^[a-zA-Z0-9]*://)");

  private static String formatWindowsPath(String absolutePath) {
    return absolutePath.replace('\\', '/');
  }

  public static boolean isAbsolute(String path) {
    if (path == null)
      throw new NullPointerException();

    if (path.charAt(0) == '/' || (Character.isLetter(path.charAt(0)) && path.charAt(1) == ':' && path.charAt(2) == '\\' && Character.isLetter(path.charAt(3))))
      return true;

    return URL_PATTERN.matcher(path).find();
  }

  public static URL makeUrlFromPath(String absolutePath) throws MalformedURLException {
    if (absolutePath == null)
      return null;

    URL url;
    if (absolutePath.contains("://"))
      url = new URL(absolutePath);
    else {
      if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
        absolutePath = formatWindowsPath(absolutePath);

      if (absolutePath.charAt(0) != '/')
        absolutePath = "/" + absolutePath;

      url = new URL("file", "", absolutePath);
    }

    return URLs.canonicalizeURL(url);
  }

  public static URL makeUrlFromPath(String basedir, String path) throws MalformedURLException {
    if (basedir == null || path == null)
      return null;

    if (basedir.length() == 0)
      return makeUrlFromPath(path);

    if (path.length() == 0)
      return makeUrlFromPath(basedir);

    if (basedir.endsWith("/") || basedir.endsWith("\\")) {
      if (path.startsWith(File.separator))
        return makeUrlFromPath(basedir + path.substring(1));

      return makeUrlFromPath(basedir + path);
    }

    return makeUrlFromPath(basedir + File.separator + path);
  }

  public static String toExternalForm(URL url) throws MalformedURLException {
    if (url == null)
      return null;

    try {
      return url.toURI().toASCIIString();
    }
    catch (URISyntaxException e) {
      throw new MalformedURLException(url.toString() + e.getMessage());
    }
  }

  public static boolean exists(URL url) {
    try {
      if ("file".equals(url.getProtocol()))
        return new File(url.getFile()).exists();

      url.openStream().close();
    }
    catch (IOException e) {
      return false;
    }

    return true;
  }

  public static URL canonicalizeURL(URL url) throws MalformedURLException {
    if (url == null)
      return null;

    final String path = Paths.canonicalize(url.toString());
    if (path == null)
      return null;

    return new URL(path);
  }

  public static String getName(URL url) {
    return Paths.getName(url.toString());
  }

  public static URL getParent(URL url) {
    if (url == null)
      return null;

    try {
      return new URL(Paths.getParent(url.toString()));
    }
    catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  private URLs() {
  }
}
