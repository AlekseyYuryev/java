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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class HTTP {
    /**
     * Invoke a GET request on the given URL with the given parameter map which will be encoded as
     * UTF-8. It is highly recommended to close the obtained inputstream after processing!
     * @param url The URL to be invoked.
     * @param patameters The parameters to be processed as query parameters.
     * @return The result of the GET request as an InputStream.
     * @throws MalformedURLException If the given URL is invalid.
     * @throws IOException If the given URL cannot be connected nor written.
     */
    public static InputStream doGet(String url, Map<String,String[]> parameters) throws MalformedURLException, IOException {
        return doGet(url, parameters, "UTF-8");
    }

    /**
     * Invoke a GET request on the given URL with the given parameter map and the given charset
     * encoding. It is highly recommended to close the obtained inputstream after processing!
     * @param url The URL to be invoked.
     * @param patameters The parameters to be processed as query parameters.
     * @param charset The encoding to be applied.
     * @return The result of the GET request as an InputStream.
     * @throws MalformedURLException If the given URL is invalid.
     * @throws IOException If the given URL cannot be connected nor written.
     * @throws UnsupportedEncodingException If the given charset is not supported.
     */
    public static InputStream doGet(String url, Map<String,String[]> parameters, String charset) throws MalformedURLException, IOException, UnsupportedEncodingException {
        final String query = createQuery(parameters, charset);
        final URLConnection urlConnection = new URL(url + "?" + query).openConnection();
        urlConnection.setUseCaches(false);

        return urlConnection.getInputStream();
    }

    /**
     * Invoke a POST request on the given URL with the given parameter map which will be encoded as
     * UTF-8. It is highly recommended to close the obtained inputstream after processing!
     * @param url The URL to be invoked.
     * @param patameters The parameters to be processed as query parameters.
     * @return The result of the POST request as an InputStream.
     * @throws MalformedURLException If the given URL is invalid.
     * @throws IOException If the given URL cannot be connected nor written.
     */
    public static InputStream doPost(URL url, Map<String,String[]> parameters) throws MalformedURLException, IOException {
        return doPost(url, parameters, null);
    }

    /**
     * Invoke a POST request on the given URL with the given parameter map which will be encoded as
     * UTF-8. It is highly recommended to close the obtained inputstream after processing!
     * @param url The URL to be invoked.
     * @param patameters The parameters to be processed as query parameters.
     * @param properties The request properties to be processed as header properties.
     * @return The result of the POST request as an InputStream.
     * @throws MalformedURLException If the given URL is invalid.
     * @throws IOException If the given URL cannot be connected nor written.
     */
    public static InputStream doPost(URL url, Map<String,String[]> parameters, Properties properties) throws MalformedURLException, IOException {
        return doPost(url, parameters, properties, null);
    }

    /**
     * Invoke a POST request on the given URL with the given parameter map which will be encoded as
     * UTF-8. It is highly recommended to close the obtained inputstream after processing!
     * @param url The URL to be invoked.
     * @param patameters The parameters to be processed as query parameters.
     * @param properties The request properties to be processed as header properties.
     * @param cookies The cookies to be injected into the header.
     * @return The result of the POST request as an InputStream.
     * @throws MalformedURLException If the given URL is invalid.
     * @throws IOException If the given URL cannot be connected nor written.
     */
    public static InputStream doPost(URL url, Map<String,String[]> parameters, Properties properties, List<String> cookies) throws MalformedURLException, IOException {
        String charset = properties != null ? properties.getProperty("accept-charset") : null;
        if (charset == null)
            charset = "UTF-8";

        final String query = createQuery(parameters, charset);
        final URLConnection urlConnection = new URL(url.toExternalForm()).openConnection();
        urlConnection.setUseCaches(false);
        urlConnection.setDoOutput(true); // Triggers POST.
//        urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        if (properties != null)
            for (Map.Entry property : properties.entrySet())
                urlConnection.setRequestProperty((String)property.getKey(), (String)property.getValue());

        if (cookies != null) {
            final Map.Entry<String,String> cookie = Cookies.createCookieHeader(cookies);
            urlConnection.setRequestProperty(cookie.getKey(), cookie.getValue());
        }

        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(query);
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    final String message = "Closing URLConnection#getOutputStream() of " + url + " failed.";
                    // Do your thing with the exception and the message. Print it, log it or mail it.
                    System.err.println(message);
                    e.printStackTrace();
                }
            }
        }

        return urlConnection.getInputStream();
    }

    /**
     * Create a query string based on the given parameter map and the given charset encoding.
     * @param parameters The parameter map to be processed as query parameters.
     * @param charset The encoding to be applied.
     * @return The parameter map as query string.
     * @throws UnsupportedEncodingException If the given charset is not supported.
     */
    public static String createQuery(Map<String,String[]> parameters, String charset) throws UnsupportedEncodingException {
        if (parameters == null)
            return "";

        final StringBuilder query = new StringBuilder();
        for (Map.Entry<String,String[]> entry : parameters.entrySet()) {
            final String name = entry.getKey();
            final String[] values = entry.getValue();
            query.append("&");
            final StringBuilder temp = new StringBuilder();
            for (String value : values) {
                temp.append("&");
                temp.append(URLEncoder.encode(name, charset));
                temp.append("=");
                temp.append(URLEncoder.encode(value, charset));
            }

            query.append(temp.substring(1));
        }

        return query.substring(1);
    }

    private HTTP() {
    }
}
