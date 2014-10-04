/* Copyright (c) 2009 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
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