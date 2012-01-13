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
import java.util.Enumeration;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourcesTest {
    private static final File JAVA_HOME = new File(System.getProperty("java.home").replace(" ", "%20"));
    private static final File RT_JAR;

  static {
    try {
      if (System.getProperty("os.name").contains("Mac"))
        RT_JAR = new File(JAVA_HOME, "../Classes/classes.jar").getCanonicalFile();
      else
        RT_JAR = new File(JAVA_HOME, "lib/rt.jar");
    }
    catch(Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }

    public static void main(String[] args) throws Exception {
        final ResourcesTest resourcesTest = new ResourcesTest();
        resourcesTest.testGetLocationBase();
        resourcesTest.testGetResource();
        resourcesTest.testGetResources();
    }

    @Test
    public void testGetLocationBase() {
        assertNull(Resources.getLocationBase(null));
        assertTrue(Resources.getLocationBase(ResourcesTest.class).isDirectory());
        assertEquals(RT_JAR, Resources.getLocationBase(String.class));
    }

    @Test
    public void testGetResource() throws Exception {
        assertNull(Resources.getResource(null));
        assertNull(Resources.getResource(""));
        assertTrue(Resources.getResource("META-INF").getURL().toString().endsWith(".jar!/META-INF"));
    }

    @Test
    public void testGetResources() throws Exception {
        assertNull(Resources.getResources(null));
        assertNull(Resources.getResources(""));
        final Enumeration<Resource> resources = Resources.getResources("META-INF");
        boolean found = false;
        while (resources.hasMoreElements()) {
            final Resource resource = resources.nextElement();
            if (!resource.getURL().toString().endsWith("!/META-INF"))
                continue;

            found = true;
            break;
        }

        assertTrue(found);
    }
}
