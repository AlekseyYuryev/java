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
						for (String key : lmap.keySet())
            	urls.add(new URL(key));
        }
        catch (Exception e) {
            // TODO: Oh well, try the regular approach
					if (classLoader instanceof AntClassLoader) {
            final String fullClasspath = ((AntClassLoader)classLoader).getClasspath();
						if (fullClasspath != null) {
							final String[] classpaths = fullClasspath.split(File.pathSeparator);
							for (String classpath : classpaths) {
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
