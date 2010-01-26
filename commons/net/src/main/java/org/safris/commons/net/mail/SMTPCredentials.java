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

package org.safris.commons.net.mail;

public class SMTPCredentials {
    private final String hostname;
    private final String username;
    private final String password;

    public SMTPCredentials(String hostname, String username, String password) {
        this.hostname = hostname;
        if (hostname == null)
            throw new NullPointerException("hostname == null");

        this.username = username;
        if (username == null)
            throw new NullPointerException("username == null");

        this.password = password;
        if (password == null)
            throw new NullPointerException("password == null");
    }

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof SMTPCredentials))
            return false;

        final SMTPCredentials that = (SMTPCredentials)obj;
        return hostname.equals(that.hostname) && username.equals(that.username) && password.equals(that.password);
    }

    public int hashCode() {
        int hashCode = 0;
        hashCode += 2 * hostname.hashCode();
        hashCode += 3 * username.hashCode();
        hashCode += 5 * password.hashCode();
        return hashCode;
    }
}
