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

package org.safris.xml.toolkit.test.binding.regression;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import org.junit.Ignore;
import org.safris.commons.xml.binding.Base64Binary;

@Ignore("Make this a real test!")
public class Metadata
{
	private static final String PASSWORD = "liberty";
	private static final String ALIAS = "client";

	protected static Base64Binary getKeyInfo(String host)
	{
		File keyStoreFile = null;
		try
		{
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			keyStoreFile = new File("src/main/resources/keystore");
			if(!keyStoreFile.exists())
				keyStoreFile = new File("src/test/resources/keystore");

			keyStore.load(new FileInputStream(keyStoreFile), PASSWORD.toCharArray());
			Certificate certificate = keyStore.getCertificate(ALIAS);
			return new Base64Binary(certificate.getEncoded());
		}
		catch(Exception e)
		{
			if(keyStoreFile != null)
				throw new Error(keyStoreFile.getAbsolutePath(), e);

			throw new Error(keyStoreFile.getAbsolutePath(), e);
		}
	}
}
