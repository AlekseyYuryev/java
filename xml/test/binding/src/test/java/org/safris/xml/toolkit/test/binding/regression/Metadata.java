/*  Copyright Safris Software 2006
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

package org.safris.xml.toolkit.test.binding.regression;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import org.junit.Ignore;
import org.safris.commons.xml.binding.Base64Binary;

@Ignore("Make this a real test!")
public class Metadata {
  private static final String PASSWORD = "liberty";
  private static final String ALIAS = "client";

  protected static Base64Binary getKeyInfo(String host) {
    File keyStoreFile = null;
    try {
      final KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
      keyStoreFile = new File("src/main/resources/keystore");
      if (!keyStoreFile.exists())
        keyStoreFile = new File("src/test/resources/keystore");

      keyStore.load(new FileInputStream(keyStoreFile), PASSWORD.toCharArray());
      Certificate certificate = keyStore.getCertificate(ALIAS);
      return new Base64Binary(certificate.getEncoded());
    }
    catch (Exception e) {
      if (keyStoreFile != null)
        throw new Error(keyStoreFile.getAbsolutePath(), e);

      throw new Error(e);
    }
  }
}
