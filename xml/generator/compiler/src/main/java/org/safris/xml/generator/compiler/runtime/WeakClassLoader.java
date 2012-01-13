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

public class WeakClassLoader extends SecureClassLoader {
  private final java.lang.ClassLoader parent;

  public WeakClassLoader() {
    super();
    this.parent = null;
  }

  public WeakClassLoader(java.lang.ClassLoader parent) {
    super(parent);
    this.parent = parent;
  }

  public Class loadClass(String name) throws ClassNotFoundException {
    return loadClass(name, false);
  }

  public synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
    final WeakReference<Class<?>> ref = new WeakReference<Class<?>>(findClass(name));
    final Class<?> cls = ref.get();
    if (resolve)
      resolveClass(cls);

    return cls;
  }

  protected Class<?> findClass(String name) throws ClassNotFoundException {
    if (Binding.class.getName().equals(name)) {
      if (parent != null)
        return parent.loadClass(name);
      else
        return WeakClassLoader.getSystemClassLoader().loadClass(name);
    }

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
    catch (UnsupportedEncodingException e) {
      System.err.println("ClassLoader: findClass(" + name + ")\n" + e.getMessage());
    }

    Class<?> bindingClass = null;
    try {
      byte[] bytes = Files.getBytes(new File(decodedUrl + fileName));
      bindingClass = defineClass(name, bytes, 0, bytes.length);
    }
    catch (FileNotFoundException e) {
      if (parent != null)
        return parent.loadClass(name);
      else
        return WeakClassLoader.getSystemClassLoader().loadClass(name);
    }
    catch (IOException e) {
      System.err.println("ClassLoader: findClass(" + name + ")\n" + e.getMessage());
    }

    return bindingClass;
  }
}
