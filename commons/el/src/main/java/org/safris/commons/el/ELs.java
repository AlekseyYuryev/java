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

package org.safris.commons.el;

import java.util.Map;

public final class ELs {
    public static String dereference(String string, Map<String,String> variables) throws ExpressionFormatException {
        if (string == null || string.length() == 0 || variables == null)
            return string;

        final StringBuffer buffer = new StringBuffer();
        int i = -1;
        int j = -1;
        while (true) {
            i = string.indexOf("${", i);
            if (i == -1)
                break;

            j = string.indexOf("}", i + 2);
            if (j == -1)
                throw new ExpressionFormatException("There is an error in your expression: " + string);

            final String token = string.substring(i + 2, j);
            final String variable = variables.get(token);
            buffer.append(string.substring(0, i)).append(variable);
            string = string.substring(j + 1);
        }

        buffer.append(string);

        return buffer.toString();
    }

    private ELs() {
    }
}
