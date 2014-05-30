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
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

public final class ClassLoaderTest {
  public static void main(final String[] args) throws Exception {
    if (args == null || args.length == 0) {
      new ClassLoaderTest().testClassLoaders();
      return;
    }

    if ("weak".equals(args[0]))
      testClassLoader(new WeakClassLoader());
    else if ("system".equals(args[0]))
      testClassLoader(ClassLoader.getSystemClassLoader());
    else
      throw new Error("Unknown classLoader option");

    Runtime.getRuntime().gc();
  }

  private static float testClassLoader(final java.lang.ClassLoader classLoader) throws Exception {
    long freeMemoryBeforeLoad = Runtime.getRuntime().freeMemory();
//      PackageLoader.getSystemPackageLoader().loadPackage("org.safris.xml.toolkit.component.runtime");
    long freeMemoryAfterLoad = Runtime.getRuntime().freeMemory();

    Runtime.getRuntime().gc();

    long freeMemoryAfterUnload = Runtime.getRuntime().freeMemory();
    float ratio = (float)freeMemoryAfterUnload / (float)freeMemoryAfterLoad;
    System.out.println(classLoader.getClass().getName());
    System.out.println("{");
    System.out.println("\tFree Memory Before Load: " + freeMemoryBeforeLoad);
    System.out.println("\tFree Memory After Load: " + freeMemoryAfterLoad);
    System.out.println("\tFree Memory After Unload: " + freeMemoryAfterUnload);
    System.out.println("\tratio: " + ratio);
    System.out.println("}");
    return ratio;
  }

  @Test
  // FIXME
  @Ignore("FIXME")
  public void testClassLoaders() throws IOException {
    Runtime.getRuntime().gc();
    // FIXME: The scenario that is tested here is the more performant than the other.
    float system = fork("system");
    float weak = fork("weak");

    weak = fork("weak");
    system = fork("system");
    //assertTrue("Custom classLoader is NOT more efficient than System classLoader!", weak < system);

    system = fork("system");
    weak = fork("weak");
    //assertTrue("Custom classLoader is NOT more efficient than System classLoader!", weak < system);
  }

  private static float fork(final String loader) throws IOException {
    String classpath = "";
    String userDir = System.getProperty("user.dir");
    String localRepository = System.getProperty("localRepository");
    if (userDir != null) {
      classpath += File.pathSeparator + userDir + "/target/classes";
      classpath += File.pathSeparator + userDir + "/target/test-classes";
    }

    if (localRepository != null)
      classpath += File.pathSeparator + localRepository + "/junit/junit/4.3/junit-4.3.jar";

    if (classpath.length() != 0)
      System.setProperty("java.class.path", System.getProperty("java.class.path") + classpath);

    /*      Process process = Forks.fork(ClassLoaderTest.class, new String[]{loader});
     InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
     InputStreamReader errorStreamReader = new InputStreamReader(process.getErrorStream());
     BufferedReader bufferedInputReader = new BufferedReader(inputStreamReader);
     BufferedReader bufferedErrorReader = new BufferedReader(errorStreamReader);
     String line = null;
     float ratio = 0;
     while((line = bufferedInputReader.readLine()) != null)
     {
     if(line.contains("ratio: "))
     ratio = Float.parseFloat(line.substring(line.indexOf(": ") + 2));

     System.out.println(line);
     }

     while((line = bufferedErrorReader.readLine()) != null)
     System.out.println(line);

     try
     {
     process.waitFor();
     }
     catch(final InterruptedException e)
     {
     throw new Error(e);
     }

     return ratio;*/ return 0;
  }
}