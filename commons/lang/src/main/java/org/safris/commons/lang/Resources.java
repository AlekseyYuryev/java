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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import sun.reflect.Reflection;

public final class Resources {
  public static File getLocationBase(Class clazz) {
    if (clazz == null)
      return null;

    final Resource resource = getResource(clazz.getName().replace('.', '/') + ".class");
    if (resource == null)
      return null;

    final URL url = resource.getURL();
    String classFile = url.getFile();
    final int colon = classFile.indexOf(':');
    final int bang = classFile.indexOf('!');
    if (bang != -1 && colon != -1)
      classFile = classFile.substring(colon + 1, bang);
    else
      classFile = classFile.substring(0, classFile.length() - clazz.getName().length() - 7);

    return new File(classFile);
  }

  public static Resource getResource(String name) {
    if (name == null || name.length() == 0)
      return null;

    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    URL url = classLoader.getResource(name);
    if (url != null)
      return new Resource(url, classLoader);

    classLoader = Thread.currentThread().getContextClassLoader();
    url = classLoader.getResource(name);
    if (url != null)
      return new Resource(url, classLoader);

    final Class callerClass = Reflection.getCallerClass(2);
    url = callerClass.getResource(name);
    if (url != null)
      return new Resource(url, classLoader);

    return null;
  }

  public static Enumeration<Resource> getResources(String name) throws IOException {
    if (name == null || name.length() == 0)
      return null;

    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    final Set<URL> history = new HashSet<URL>();
    Enumeration<URL> urls = classLoader.getResources(name);

    final Vector<Resource> resources = new Vector<Resource>(1, 1);
    combineResources(urls, classLoader, history, resources);
    classLoader = Thread.currentThread().getContextClassLoader();
    urls = classLoader.getResources(name);
    combineResources(urls, classLoader, history, resources);

    final Class callerClass = Reflection.getCallerClass(2);
    classLoader = callerClass.getClassLoader();
    urls = classLoader.getResources(name);
    combineResources(urls, classLoader, history, resources);

    return resources.elements();
  }

  private static void combineResources(Enumeration<URL> urls, ClassLoader classLoader, Set<URL> history, Collection<Resource> resources) {
    if (urls == null)
      return;

    while (urls.hasMoreElements()) {
      final URL url = urls.nextElement();
      if (history.contains(url))
        continue;

      history.add(url);
      resources.add(new Resource(url, classLoader));
    }
  }

  private Resources() {
  }
}
