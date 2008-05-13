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
