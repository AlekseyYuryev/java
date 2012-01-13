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

package org.safris.commons.lang;

import java.net.URL;

public final class Resource {
  private final URL url;
  private final ClassLoader classLoader;

  public Resource(URL url, ClassLoader classLoader) {
    this.url = url;
    this.classLoader = classLoader;
  }

  public URL getURL() {
    return url;
  }

  public ClassLoader getClassLoader() {
    return classLoader;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof Resource))
      return false;

    final Resource resource = (Resource)obj;
    return url != null ? url.equals(resource.url) && (classLoader != null ? classLoader.equals(resource.classLoader) : resource.classLoader == null) : resource.url == null && (classLoader != null ? classLoader.equals(resource.classLoader) : resource.classLoader == null);
  }

  public int hashCode() {
    return url.hashCode() * 3 + classLoader.hashCode();
  }
}
