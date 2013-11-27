package org.safris.commons.jci;/*  Copyright Safris Software 2011
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



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureClassLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import org.safris.commons.jci.MemoryJavaFileManager;
import org.safris.commons.jci.MemoryJavaFileObject;

public class MemoryJavaFileManager extends ForwardingJavaFileManager {
  private final Map<String,MemoryJavaFileObject> map = new HashMap<String,MemoryJavaFileObject>();
  /**
   * Instance of JavaClassObject that will store the
   * compiled bytecode of our class
   */
  private MemoryJavaFileObject jclassObject;

  /**
   * Will initialize the manager with the specified
   * standard java file manager
   *
   * @param standardManger
   */
  public MemoryJavaFileManager(final StandardJavaFileManager standardManager) {
    super(standardManager);
  }

  public Collection<MemoryJavaFileObject> outputClasses(final File dir) throws IOException {
    for (final Map.Entry<String,MemoryJavaFileObject> entry : map.entrySet()) {
      final File file = new File(dir, entry.getKey().replace('.', File.separatorChar) + ".class");
      if (!file.getParentFile().exists())
        file.getParentFile().mkdirs();

      final FileOutputStream fos = new FileOutputStream(file);
      fos.write(entry.getValue().getBytes());
      fos.close();
    }

    return map.values();
  }

  /**
   * Will be used by us to get the class loader for our
   * compiled class. It creates an anonymous class
   * extending the SecureClassLoader which uses the
   * byte code created by the compiler and stored in
   * the JavaClassObject, and returns the Class for it
   */
  public ClassLoader getClassLoader(final Location location) {
    return new SecureClassLoader() {
      @Override
      protected Class<?> findClass(final String name) throws ClassNotFoundException {
        synchronized (MemoryJavaFileManager.this.map) {
          MemoryJavaFileObject mc = MemoryJavaFileManager.this.map.remove(name);
          if (mc != null) {
            byte[] array = mc.getBytes();
            return defineClass(name, array, 0, array.length);
          }
        }

        return super.findClass(name);
      }
    };
  }

  /**
   * Gives the compiler an instance of the JavaClassObject
   * so that the compiler can write the byte code into it.
   */
  public JavaFileObject getJavaFileForOutput(final Location location, final String className, final JavaFileObject.Kind kind, final FileObject sibling) throws IOException {
    final MemoryJavaFileObject o = new MemoryJavaFileObject(className, kind);
    map.put(className, o);
    return o;
  }
}
