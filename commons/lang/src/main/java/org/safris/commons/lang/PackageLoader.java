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
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This utility class is for loading classes in a package.
 *
 * @author Seva Safris
 * @version 1.4
 */
public abstract class PackageLoader extends ClassLoader {
  private static final PackageLoader instance = new PackageLoader(){};
  private static final FileFilter classFileFilter = new FileFilter() {
    public boolean accept(final File pathname) {
      return pathname.getName().endsWith(".class");
    }
  };

  public static PackageLoader getSystemPackageLoader() {
    return instance;
  }

  protected PackageLoader() {
  }

  /**
   * This method will call Class.forName() and initialize each class in a
   * given package. This method will search for all existing package resources
   * in all elements of the classpath. If the package exists in multiple
   * classpath locations, such as a couple of jar files and a directory, each
   * of the classpath references will be used to load all classes in each
   * resource. This method will search for all classpath entries in all class
   * loaders.
   *
   * @param       name    The name of the package.
   *
   * @return      A set of all classes for which Class.forName() was called.
   *
   * @exception   PackageNotFoundException    Gets thrown for a package name
   * that cannot be found in any classpath resources.
   */
  public Set<Class<?>> loadPackage(final String name) throws PackageNotFoundException {
    if (name == null || name.length() == 0)
      throw new PackageNotFoundException(name);

    // Translate the package name into an absolute path
    final String path;
    final char firstChar = name.charAt(0);
    if (firstChar == '/' || firstChar == '.')
      path = name.substring(1).replace('.', '/');
    else
      path = name.replace('.', '/');

    Enumeration<Resource> resources = null;
    try {
      resources = Resources.getResources(path);
    }
    catch (IOException e) {
      throw new ResourceException(e.getMessage(), e);
    }

    if (resources == null)
      throw new PackageNotFoundException(name);

    final Set<Class<?>> loadedClasses = new HashSet<Class<?>>();
    while (resources.hasMoreElements()) {
      final Resource resource = resources.nextElement();
      final URL url = resource.getURL();
      final ClassLoader classLoader = resource.getClassLoader();
      synchronized (classLoader) {
        final Map<String,ClassLoader> classesToLoad = new HashMap<String,ClassLoader>();
        String decodedUrl;
        try {
          decodedUrl = URLDecoder.decode(url.getPath(), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
          decodedUrl = url.getPath();
        }

        final File directory = new File(decodedUrl);
        if (directory.exists()) {
          // Get the list of the files contained in the package
          final File[] files = directory.listFiles(classFileFilter);
          String className = null;
          for (File file : files) {
            className = name + "." + file.getName().substring(0, file.getName().length() - 6);
            classesToLoad.put(className, classLoader);
          }
        }
        else {
          final JarURLConnection jarURLConnection;
          final JarFile jarFile;
          try {
            jarURLConnection = (JarURLConnection)url.openConnection();
            jarFile = jarURLConnection.getJarFile();
          }
          catch (IOException e) {
            throw new PackageNotFoundException(name, e);
          }

          final String entryName = jarURLConnection.getEntryName();
          final Enumeration<JarEntry> enumeration = jarFile.entries();
          String zipEntryName;
          String className;
          while (enumeration.hasMoreElements()) {
            zipEntryName = enumeration.nextElement().getName();
            if (!zipEntryName.startsWith(entryName) || zipEntryName.lastIndexOf(entryName + "/") > entryName.length() || !zipEntryName.endsWith(".class"))
              continue;

            className = zipEntryName.substring(0, zipEntryName.length() - 6);
            if (className.charAt(0) == '/')
              className = className.substring(1);

            className = className.replace('/', '.');
            classesToLoad.put(className, classLoader);
          }
        }

        for (final Map.Entry<String,ClassLoader> entry : classesToLoad.entrySet()) {
          try {
            loadedClasses.add(Class.forName(entry.getKey(), true, entry.getValue()));
          }
          catch (ClassNotFoundException e) {
          }
        }
      }
    }

    return loadedClasses;
  }
}
