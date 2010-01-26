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

package org.safris.commons.formatter;

public class MethodModule extends FormatModule {
    String format(String formated, String token) {
        if (token.trim().lastIndexOf(";") != token.trim().length() - 1) {
            if (token.trim().indexOf("else") == -1 && token.trim().indexOf("else ") == -1 && ((token.trim().indexOf("static") == 0 && token.trim().length() == 6) || (token.trim().indexOf(" ") != -1 && token.trim().indexOf(" ") < token.trim().indexOf("(") - 1 && (token.trim().lastIndexOf(")") == token.length() - 1 || token.trim().lastIndexOf(")") < token.trim().lastIndexOf("throws"))))) {
                for (int i = 0; i < getDepth(); i++) {
                    token = "\t" + token;
                }

                if (getLastModule() instanceof CloseBracketModule || getLastModule() instanceof FieldModule) {
                    token = "\n\n" + token;
                }
                else {
                    token = "\n" + token;
                }
            }
        }

        return token;
    }
}
