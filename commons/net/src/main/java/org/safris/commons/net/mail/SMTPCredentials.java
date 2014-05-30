/*  Copyright Safris Software 2009
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.net.mail;

public final class SMTPCredentials {
  private final String hostname;
  private final String username;
  private final String password;

  public SMTPCredentials(final String hostname, final String username, final String password) {
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

  public boolean equals(final Object obj) {
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