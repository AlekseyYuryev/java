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

package org.safris.commons.net;

import java.net.URLConnection;
import java.util.Map;
import java.util.Properties;

public final class URLConnections {
    public static void setRequestProperties(URLConnection urlConnection, Properties properties) {
        if (urlConnection == null)
            throw new NullPointerException("urlConnection == null");

        if (properties == null)
            throw new NullPointerException("properties == null");

        for (Map.Entry entry : properties.entrySet())
            urlConnection.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
    }

    private URLConnections() {
    }
}
