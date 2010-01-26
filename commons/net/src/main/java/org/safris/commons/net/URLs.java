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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;
import org.safris.commons.lang.Paths;

public final class URLs {
    private static final Pattern URL_PATTERN = Pattern.compile("(^[a-zA-Z0-9]*://)");

    private static String formatWindowsPath(String absolutePath) {
        return absolutePath.replace('\\', '/');
    }

    public static boolean isAbsolute(String path) {
        if (path == null)
            throw new NullPointerException();

        if (path.charAt(0) == '/' || (Character.isLetter(path.charAt(0)) && path.charAt(1) == ':' && path.charAt(2) == '\\' && Character.isLetter(path.charAt(3))))
            return true;

        return URL_PATTERN.matcher(path).find();
    }

    public static URL makeUrlFromPath(String absolutePath) throws MalformedURLException {
        if (absolutePath == null)
            return null;

        URL url;
        if (absolutePath.contains("://"))
            url = new URL(absolutePath);
        else {
            if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
                absolutePath = formatWindowsPath(absolutePath);

            if (absolutePath.charAt(0) != '/')
                absolutePath = "/" + absolutePath;

            url = new URL("file", "", absolutePath);
        }

        return URLs.canonicalizeURL(url);
    }

    public static URL makeUrlFromPath(String basedir, String path) throws MalformedURLException {
        if (basedir == null || path == null)
            return null;

        if (basedir.length() == 0)
            return makeUrlFromPath(path);

        if (path.length() == 0)
            return makeUrlFromPath(basedir);

        if (basedir.endsWith("/") || basedir.endsWith("\\")) {
            if (path.startsWith(File.separator))
                return makeUrlFromPath(basedir + path.substring(1));

            return makeUrlFromPath(basedir + path);
        }

        return makeUrlFromPath(basedir + File.separator + path);
    }

    public static String toExternalForm(URL url) throws MalformedURLException {
        if (url == null)
            return null;

        try {
            return url.toURI().toASCIIString();
        }
        catch (URISyntaxException e) {
            throw new MalformedURLException(url.toString() + e.getMessage());
        }
    }

    public static boolean exists(URL url) {
        try {
            if ("file".equals(url.getProtocol()))
                return new File(url.getFile()).exists();

            url.openStream().close();
        }
        catch (IOException e) {
            return false;
        }

        return true;
    }

    public static URL canonicalizeURL(URL url) throws MalformedURLException {
        if (url == null)
            return null;

        final String path = Paths.canonicalize(url.toString());
        if (path == null)
            return null;

        return new URL(path);
    }

    public static String getName(URL url) {
        return Paths.getName(url.toString());
    }

    public static URL getParent(URL url) {
        if (url == null)
            return null;

        try {
            return new URL(Paths.getParent(url.toString()));
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private URLs() {
    }
}
