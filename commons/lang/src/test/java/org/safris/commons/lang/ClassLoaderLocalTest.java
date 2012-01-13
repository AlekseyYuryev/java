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

import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassLoaderLocalTest {
  public static void main(String[] args) throws Exception {
    new ClassLoaderLocalTest().testClassLoaderLocal();
  }

  private static String className = "com.sun.jmx.snmp.ThreadContext";

  @Test
  // FIXME
  @Ignore("FIXME")
  public void testClassLoaderLocal() throws Exception {
    final String bootClassPath = System.getProperty("sun.boot.class.path");
    final Collection<URL> urls = new ArrayList<URL>();
    final StringTokenizer tokenizer = new StringTokenizer(bootClassPath, ":");
    while (tokenizer.hasMoreTokens())
      urls.add(new URL("file", null, tokenizer.nextToken()));

    final WeakReference<URLClassLoader> classLoaderReference = new WeakReference<URLClassLoader>(new URLClassLoader(urls.toArray(new URL[urls.size()]), null));
    print(classLoaderReference);
    classLoaderReference.get().loadClass(className).getDeclaredMethod("push", String.class, Object.class).invoke(null, "YES", "ONE");
    classLoaderReference.get().loadClass(className);
    print(classLoaderReference);
    classLoaderReference.enqueue();
    System.gc();

    final Object tc2 = ClassLoader.getSystemClassLoader().loadClass(className).getDeclaredMethod("get", String.class).invoke(null, "YES");
    System.out.println(tc2);
    print(classLoaderReference);
  }

  private void print(WeakReference<URLClassLoader> classLoaderReference) {
    System.out.println("----");
    if (classLoaderReference.get() != null)
      System.out.println("Weak: " + ClassLoaders.isClassLoaded(classLoaderReference.get(), className) + " " + classLoaderReference.get().getClass().getClassLoader());
    System.out.println("Current: " + ClassLoaders.isClassLoaded(getClass().getClassLoader(), className) + " " + getClass().getClassLoader());
    System.out.println("Context: " + ClassLoaders.isClassLoaded(Thread.currentThread().getContextClassLoader(), className) + " " + Thread.currentThread().getClass().getClassLoader());
    System.out.println("System: " + ClassLoaders.isClassLoaded(ClassLoader.getSystemClassLoader(), className) + " " + ClassLoader.getSystemClassLoader().getClass().getClassLoader());
    System.out.println("Bootstrap: " + ClassLoaders.isClassLoaded(ClassLoader.getSystemClassLoader(), className) + " " + ClassLoader.getSystemClassLoader().getParent().getClass().getClassLoader());
  }
}
