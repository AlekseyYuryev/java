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

package org.safris.commons.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.safris.commons.util.Hexadecimal;

public class SHA1 {
  private static MessageDigest messageDigest = null;

  static {
    try {
      messageDigest = MessageDigest.getInstance("SHA");
    }
    catch (NoSuchAlgorithmException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  public static Hexadecimal encode(String string) {
    messageDigest.update(string.getBytes());
    final byte[] digest = messageDigest.digest();
    return new Hexadecimal(digest);
  }
}
