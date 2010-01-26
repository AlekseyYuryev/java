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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import org.safris.commons.util.Collections;

public final class Systems {
    public static boolean setenv(String name, String value) {
        Class<?> processEnvironmentClass;
        Field theEnvironmentField;
        Field theUnmodifiableEnvironmentField;
        Map theEnvironment;
        Map theUnmodifiableEnvironment;
        try {
            processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);

            theUnmodifiableEnvironmentField = processEnvironmentClass.getDeclaredField("theUnmodifiableEnvironment");
            theUnmodifiableEnvironmentField.setAccessible(true);

            theEnvironment = (Map)theEnvironmentField.get(null);
            theUnmodifiableEnvironment = (Map)theUnmodifiableEnvironmentField.get(null);
        }
        catch (RuntimeException e) {
            return false;
        }
        catch (Exception e) {
            return false;
        }

        // Versions: 1.7 06/12/05
        if (theEnvironmentField.getType().isAssignableFrom(processEnvironmentClass)) {
            if (Collections.putUnmodifiableMap(theUnmodifiableEnvironment, name, value)) {
                theEnvironment.put(name, value);
                return true;
            }
        }
        // Versions: 1.5 04/04/05, 1.6 05/11/17
        else {
            final Object variableData = createVariable(name);
            if (variableData == null)
                return false;

            final Object valueData = createValue(value);
            if (valueData == null)
                return false;

            Map unmodifiableMap;
            try {
                final Field unmodifiableMapField = theUnmodifiableEnvironment.getClass().getDeclaredField("m");
                unmodifiableMapField.setAccessible(true);
                unmodifiableMap = (Map)unmodifiableMapField.get(theUnmodifiableEnvironment);
            }
            catch (RuntimeException e) {
                return false;
            }
            catch (Exception e) {
                return false;
            }

            if (Collections.putUnmodifiableMap(unmodifiableMap, variableData, valueData)) {
                theEnvironment.put(variableData, valueData);
                return true;
            }
        }

        return false;
    }

    private static Object createVariable(String name) {
        try {
            final Class clazz = Class.forName("java.lang.ProcessEnvironment$Variable");
            final Constructor constructor = clazz.getDeclaredConstructor(String.class, byte[].class);
            constructor.setAccessible(true);
            return constructor.newInstance(name, name.getBytes());
        }
        catch (RuntimeException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    private static Object createValue(String value) {
        try {
            final Class clazz = Class.forName("java.lang.ProcessEnvironment$Value");
            final Constructor constructor = clazz.getDeclaredConstructor(String.class, byte[].class);
            constructor.setAccessible(true);
            return constructor.newInstance(value, value.getBytes());
        }
        catch (RuntimeException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    private Systems() {
    }
}
