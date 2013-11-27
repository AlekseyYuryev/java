/*  Copyright Safris Software 2008
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import org.apache.tools.ant.AntClassLoader;
import sun.misc.URLClassPath;
import sun.reflect.Reflection;
import java.net.MalformedURLException;

public final class ClassLoaders {
  public static boolean isClassLoaded(ClassLoader classLoader, String name) {
    if (classLoader == null)
      throw new IllegalArgumentException("classLoader == null");

    try {
      final Method method = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
      method.setAccessible(true);
      return method.invoke(classLoader, name) != null;
    }
    catch (InvocationTargetException e) {
      return false;
    }
    catch (NoSuchMethodException e) {
      return false;
    }
    catch (IllegalAccessException e) {
      throw new SecurityException(e);
    }
  }

  public static URL[] getClassPath() {
    final Collection<URL> urls = new HashSet<URL>();
    urls.addAll(Arrays.asList(((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs()));
    urls.addAll(Arrays.asList(((URLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs()));
    final Class callerClass = Reflection.getCallerClass(2);
    final ClassLoader classLoader = callerClass.getClassLoader();
    try {
      // TODO: I dont know why, but when running forked JUnit tests
      // TODO: the classpath is not available by calling the getURLs
      // TODO: method. Instead, it is hidden deep inside the URLClassPath
      final Field ucpField = URLClassLoader.class.getDeclaredField("ucp");
      ucpField.setAccessible(true);
      final URLClassPath ucp = (URLClassPath)ucpField.get(classLoader);
      final Field lmapField = URLClassPath.class.getDeclaredField("lmap");
      lmapField.setAccessible(true);
      final Map<String,Object> lmap = (Map<String,Object>)lmapField.get(ucp);
      for (final String key : lmap.keySet())
        urls.add(new URL(key));
    }
    catch (Exception e) {
      // TODO: Oh well, try the regular approach
      if (classLoader instanceof AntClassLoader) {
        final String fullClasspath = ((AntClassLoader)classLoader).getClasspath();
        if (fullClasspath != null) {
          final String[] classpaths = fullClasspath.split(File.pathSeparator);
          for (final String classpath : classpaths) {
            try {
              urls.add(new File(classpath).toURL());
            }
            catch (MalformedURLException e2) {
            }
          }
        }
      }
      else if (classLoader instanceof URLClassLoader)
        urls.addAll(Arrays.asList(((URLClassLoader)classLoader).getURLs()));
    }

    return urls.toArray(new URL[urls.size()]);
  }

  private ClassLoaders() {
  }
}
