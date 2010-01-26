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
import org.safris.xml.generator.lexer.schema.attribute.Value;

public final class Value {
    private static final Map<String,Value> enums = new HashMap<String,Value>();

    public static final Value COLLAPSE = new Value("collapse");
    public static final Value PRESERVE = new Value("preserve");
    public static final Value REPLACE = new Value("replace");

    public static Value parseValue(String value) {
        return enums.get(value);
    }

    private final String value;

    private Value(String value) {
        this.value = value;
        enums.put(value, this);
    }

    public String getValue() {
        return value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Value))
            return false;

        return getValue().equals(((Value)obj).getValue());
    }

    public int hashCode() {
        return (getClass().getName() + toString()).hashCode();
    }

    public String toString() {
        return getValue();
    }
}
