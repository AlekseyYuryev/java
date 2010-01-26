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

package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.Namespace;

public final class Namespace {
    private static final Map<String,Namespace> enums = new HashMap<String,Namespace>();

    public static final Namespace ANY = new Namespace("##any");
    public static final Namespace LOCAL = new Namespace("##local");
    public static final Namespace OTHER = new Namespace("##other");
    public static final Namespace TARGETNAMESPACE = new Namespace("##targetNamespace");

    public static Namespace parseNamespace(String value) {
        final Namespace namespace = enums.get(value);
        if (namespace != null)
            return namespace;

        return new Namespace(value);
    }

    private final String value;

    public Namespace(String value) {
        this.value = value;
        enums.put(value, this);
    }

    public String getValue() {
        return value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Namespace))
            return false;

        return getValue().equals(((Namespace)obj).getValue());
    }

    public int hashCode() {
        return (getClass().getName() + toString()).hashCode();
    }

    public String toString() {
        return getValue();
    }
}
