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

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import sun.reflect.Reflection;

import static org.junit.Assert.*;

public class PackageLoaderTest {
  public static void main(String[] args) throws Exception {
    new PackageLoaderTest().testPackageLoader();
  }

  private static boolean isClassLoaded(String name) {
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    if (ClassLoaders.isClassLoaded(classLoader, name))
      return true;

    classLoader = Thread.currentThread().getContextClassLoader();
    if (ClassLoaders.isClassLoaded(classLoader, name))
      return true;

    final Class callerClass = Reflection.getCallerClass(2);
    classLoader = callerClass.getClassLoader();
    if (ClassLoaders.isClassLoaded(classLoader, name))
      return true;

    return false;
  }

  @Test
  public void testPackageLoader() throws Exception {
    final String[] testClasses = new String[]{
      "org.junit.PackageLoaderClass1",
      "org.junit.PackageLoaderClass2",
      "org.junit.PackageLoaderClass3",
      "org.junit.internal.matchers.Each",
      "org.junit.runners.Suite",
      "org.junit.runners.Parameterized"
    };

    for (String testClass : testClasses)
      assertFalse(isClassLoaded(testClass));

    final Set<Class<?>> loadedClasses = PackageLoader.getSystemPackageLoader().loadPackage("org.junit");
    final Set<String> classNames = new HashSet<String>();
    for (Class<?> loadedClass : loadedClasses)
      classNames.add(loadedClass.getName());

    for (String testClass : testClasses) {
      assertTrue(classNames.contains(testClass));
      assertTrue(isClassLoaded(testClass));
    }

    try {
      PackageLoader.getSystemPackageLoader().loadPackage(null);
      fail("Expected a PackageNotFoundException");
    }
    catch (PackageNotFoundException e) {
    }
  }
}
