/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
