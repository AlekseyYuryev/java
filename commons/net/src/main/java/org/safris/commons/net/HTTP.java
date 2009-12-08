/*  Copyright 2008 Safris Technologies Inc.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.safris.commons.util.zip.Zips;

public final class HTTP {
    public static String post(URL url, Properties requestProperties, Map<String,String> requestParameters, List<String> cookies) throws IOException {
        if (url == null)
            throw new NullPointerException("url == null");

        final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.addRequestProperty("Referer", url.toExternalForm());

        if (requestProperties != null)
            URLConnections.setRequestProperties(httpURLConnection, requestProperties);

        if (requestParameters != null) {
            final StringBuffer parameterStringBuffer = new StringBuffer();
            for (Map.Entry<String,String> entry : requestParameters.entrySet())
                parameterStringBuffer.append("&").append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));

            setContent(httpURLConnection, parameterStringBuffer.substring(1));
        }

        if (cookies != null)
            setCookies(httpURLConnection, cookies);

        final InputStream input = httpURLConnection.getInputStream();
        String unzipped = null;
        try {
            unzipped = Zips.gunzip(input);
        }
        catch (IOException e) {
            if (!"Not in GZIP format".equals(e.getMessage()))
                throw e;

            while (input.read() != -1);
        }

        input.close();
        return unzipped;
    }

    private static void setCookies(URLConnection urlConnection, List<String> cookies) {
        final StringBuffer cookieStringBuffer = new StringBuffer();
        for (String cookie : cookies)
            cookieStringBuffer.append("; ").append(cookie);

        urlConnection.setRequestProperty("Cookie", cookieStringBuffer.substring(2));
    }

    private static void setContent(URLConnection urlConnection, String content) throws IOException {
        urlConnection.setRequestProperty("Content-Length", String.valueOf(content.length()));

        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);

        final Writer writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
        writer.write(content);
        writer.flush();
        writer.close();
    }

    private HTTP() {
    }
}
