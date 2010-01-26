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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Cookies {
    /**
     * Retrieve the cookie value from the given servlet request based on the given
     * cookie name.
     * @param request The HttpServletRequest to be used.
     * @param name The cookie name to retrieve the value for.
     * @return The cookie value associated with the given cookie name.
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;

        for (Cookie cookie : cookies)
            if (cookie != null && name.equals(cookie.getName()))
                return cookie.getValue();

        return null;
    }

    /**
     * Set the cookie value in the given servlet response based on the given cookie
     * name and expiration interval.
     * @param response The HttpServletResponse to be used.
     * @param name The cookie name to associate the cookie value with.
     * @param value The actual cookie value to be set in the given servlet response.
     * @param maxAge The expiration interval in seconds. If this is set to 0,
     * then the cookie will immediately expire.
     */
    public static void setCookieValue(HttpServletResponse response, String name, String value, int maxAge) {
        final Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * Remove the cookie from the given servlet response based on the given cookie
     * name. It actually sets the cookie expiration interval to zero, resulting the
     * cookie being expired immediately.
     * @param response The HttpServletResponse to be used.
     * @param name The cookie name of the cookie to be removed.
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        setCookieValue(response, name, null, 0);
    }

    public static Map.Entry<String,String> createCookieHeader(Collection<String> cookies) {
        final StringBuffer cookieStringBuffer = new StringBuffer();
        for (String cookie : cookies)
            cookieStringBuffer.append("; ").append(cookie);

        final String value = cookieStringBuffer.length() != 0 ? cookieStringBuffer.substring(2) : "";
        return Collections.<String,String>singletonMap("Cookie", value).entrySet().iterator().next();
    }

    private Cookies() {
    }
}
