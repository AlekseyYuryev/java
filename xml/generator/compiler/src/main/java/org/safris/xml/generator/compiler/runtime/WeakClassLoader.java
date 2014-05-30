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

package org.safris.xml.generator.compiler.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureClassLoader;

import org.safris.commons.io.Files;

public final class WeakClassLoader extends SecureClassLoader {
  private final java.lang.ClassLoader parent;

  public WeakClassLoader() {
    super();
    this.parent = null;
  }

  public WeakClassLoader(final java.lang.ClassLoader parent) {
    super(parent);
    this.parent = parent;
  }

  public final Class<?> loadClass(final String name) throws ClassNotFoundException {
    return loadClass(name, false);
  }

  public synchronized final Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
    final WeakReference<Class<?>> ref = new WeakReference<Class<?>>(findClass(name));
    final Class<?> cls = ref.get();
    if (resolve)
      resolveClass(cls);

    return cls;
  }

  protected Class<?> findClass(final String name) throws ClassNotFoundException {
    if (Binding.class.getName().equals(name))
      return parent != null ? parent.loadClass(name) : WeakClassLoader.getSystemClassLoader().loadClass(name);

    String fileName = name;
    fileName = fileName.replace('.', '/');
    if (!fileName.startsWith("/"))
      fileName = '/' + fileName;

    fileName += ".class";
    URL url = WeakClassLoader.class.getResource("/");
    String decodedUrl = null;
    try {
      decodedUrl = URLDecoder.decode(url.getFile(), "UTF-8");
    }
    catch (final UnsupportedEncodingException e) {
      System.err.println("ClassLoader: findClass(" + name + ")\n" + e.getMessage());
    }

    Class<?> bindingClass = null;
    try {
      byte[] bytes = Files.getBytes(new File(decodedUrl + fileName));
      bindingClass = defineClass(name, bytes, 0, bytes.length);
    }
    catch (final FileNotFoundException e) {
      return parent != null ? parent.loadClass(name) : WeakClassLoader.getSystemClassLoader().loadClass(name);
    }
    catch (final IOException e) {
      System.err.println("ClassLoader: findClass(" + name + ")\n" + e.getMessage());
    }

    return bindingClass;
  }
}