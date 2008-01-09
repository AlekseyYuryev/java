package org.safris.xml.toolkit.test.binding.regression;

import org.safris.xml.toolkit.test.binding.regression.DsRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.safris.xml.toolkit.test.binding.regression.XencRegressionTest;
import org.w3.x2001.x04.xmlenc.IXencEncryptedDataType;
import org.w3.x2001.x04.xmlenc.IXencEncryptedKeyType;
import org.w3.x2001.x04.xmlenc.IXencEncryptedType;
import org.w3.x2001.x04.xmlenc.IXencEncryptionMethodType;
import org.w3.x2001.x04.xmlenc.IXencReferenceType;
import org.w3.x2001.x04.xmlenc.IXencTransformsType;
import org.w3.x2001.x04.xmlenc.XencAgreementMethod;
import org.w3.x2001.x04.xmlenc.XencCipherData;
import org.w3.x2001.x04.xmlenc.XencCipherReference;
import org.w3.x2001.x04.xmlenc.XencEncryptedData;
import org.w3.x2001.x04.xmlenc.XencEncryptedKey;
import org.w3.x2001.x04.xmlenc.XencEncryptionProperties;
import org.w3.x2001.x04.xmlenc.XencEncryptionProperty;
import org.w3.x2001.x04.xmlenc.XencReferenceList;

public class XencRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "http://www.w3.org/2001/04/xmlenc#";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new XencRegressionTest();

	public static void main(String[] args)
	{
		getCipherData();
		getEncryptedData();
		getEncryptedKey();
		getAgreementMethod();
		getEncryptionProperties();
	}

	public static XencCipherData getCipherData()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		XencCipherData binding = new XencCipherData();
		if(Math.random() < CHOICE_SEED)
			binding.setXencCipherValue(new XencCipherData.XencCipherValue(getBase64Binary()));
		else
			binding.setXencCipherReference(getCipherReference());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static XencCipherReference getCipherReference()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		XencCipherReference binding = new XencCipherReference();
		IXencTransformsType transforms = new IXencTransformsType()
		{
			protected IXencTransformsType inherits()
			{
				return null;
			}
		};
		do
		transforms.addDsTransform(DsRegressionTest.getTransform());
		while(Math.random() < ADD_SEED);
		binding.setXencURIAttr(new XencCipherReference.XencURIAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static IXencEncryptedDataType getEncryptedData()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		XencEncryptedData binding = new XencEncryptedData(getEncryptedDataType());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static XencEncryptedKey getEncryptedKey()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		XencEncryptedKey binding = new XencEncryptedKey(getEncryptedKeyType());
		binding.setXencReferenceList(getReferenceList());
		binding.setXencCarriedKeyName(new XencEncryptedKey.XencCarriedKeyName(getRandomString()));
		binding.setXencRecipientAttr(new XencEncryptedKey.XencRecipientAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static XencAgreementMethod getAgreementMethod()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		XencAgreementMethod binding = new XencAgreementMethod();
		binding.setXencKANonce(new XencAgreementMethod.XencKANonce(getBase64Binary()));
		binding.setXencOriginatorKeyInfo(new XencAgreementMethod.XencOriginatorKeyInfo(DsRegressionTest.getKeyInfo()));
		binding.setXencRecipientKeyInfo(DsRegressionTest.getKeyInfo());
		binding.setXencAlgorithmAttr(new XencAgreementMethod.XencAlgorithmAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static XencReferenceList getReferenceList()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		XencReferenceList binding = new XencReferenceList();
		if(Math.random() < CHOICE_SEED)
			binding.addXencDataReference(getReferenceType());
		else
			binding.addXencKeyReference(getReferenceType());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static final IXencReferenceType getReferenceType()
	{
		IXencReferenceType binding = new IXencReferenceType()
		{
			protected IXencReferenceType inherits()
			{
				return null;
			}
		};
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
		binding.setXencURIAttr(new IXencReferenceType.XencURIAttr(getRandomString()));
		return binding;
	}

	public static XencEncryptionProperties getEncryptionProperties()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		XencEncryptionProperties binding = new XencEncryptionProperties();
		do
		binding.addXencEncryptionProperty(getEncryptionProperty());
		while(Math.random() < ADD_SEED);
		binding.setXencIdAttr(new XencEncryptionProperties.XencIdAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static XencEncryptionProperty getEncryptionProperty()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		XencEncryptionProperty binding = new XencEncryptionProperty(getRandomString());
		do
		binding.addANY(instance.getAny());
		while(Math.random() < ADD_SEED);
		binding.setXencTargetAttr(new XencEncryptionProperty.XencTargetAttr(getRandomString()));
		binding.setXencIdAttr(new XencEncryptionProperty.XencIdAttr(getRandomString()));
		// FIXME: I cannot figure out how this works... What am I missing?
		/*		do
		 binding.addAnyAttribute(new Attribute("http://www.w3.org/XML/1998/namespace", "lang", new QName(getRandomString() + ":" + getRandomString(), getRandomString()), getRandomString().substring(0, 2)));
		 while(Math.random() < ADD_SEED);

		 do
		 binding.addAnyAttribute(new Attribute("http://www.w3.org/XML/1998/namespace", "space", new QName(getRandomString() + ":" + getRandomString(), getRandomString()), getRandomString().substring(0, 2)));
		 while(Math.random() < ADD_SEED);*/

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static final IXencEncryptedKeyType getEncryptedKeyType()
	{
		IXencEncryptedKeyType binding = new IXencEncryptedKeyType()
		{
			protected IXencEncryptedType inherits()
			{
				return null;
			}
		};

		binding.setXencEncryptionMethod(new IXencEncryptedDataType.XencEncryptionMethod(getEncryptionMethodType()));
		binding.setDsKeyInfo(DsRegressionTest.getKeyInfo());
		binding.setXencCipherData(getCipherData());
		binding.setXencEncryptionProperties(getEncryptionProperties());
		binding.setXencIdAttr(new IXencEncryptedDataType.XencIdAttr(getRandomString()));
		binding.setXencTypeAttr(new IXencEncryptedDataType.XencTypeAttr(getRandomString()));
		binding.setXencMimeTypeAttr(new IXencEncryptedDataType.XencMimeTypeAttr(getRandomString()));
		binding.setXencEncodingAttr(new IXencEncryptedDataType.XencEncodingAttr(getRandomString()));
		binding.setXencReferenceList(getReferenceList());
		binding.setXencCarriedKeyName(new IXencEncryptedKeyType.XencCarriedKeyName(getRandomString()));
		return binding;
	}

	public static final IXencEncryptedDataType getEncryptedDataType()
	{
		IXencEncryptedDataType binding = new IXencEncryptedDataType()
		{
			protected IXencEncryptedType inherits()
			{
				return null;
			}
		};

		binding.setXencEncryptionMethod(new IXencEncryptedDataType.XencEncryptionMethod(getEncryptionMethodType()));
		binding.setDsKeyInfo(DsRegressionTest.getKeyInfo());
		binding.setXencCipherData(getCipherData());
		binding.setXencEncryptionProperties(getEncryptionProperties());
		binding.setXencIdAttr(new IXencEncryptedDataType.XencIdAttr(getRandomString()));
		binding.setXencTypeAttr(new IXencEncryptedDataType.XencTypeAttr(getRandomString()));
		binding.setXencMimeTypeAttr(new IXencEncryptedDataType.XencMimeTypeAttr(getRandomString()));
		binding.setXencEncodingAttr(new IXencEncryptedDataType.XencEncodingAttr(getRandomString()));
		return binding;
	}

	public static final IXencEncryptionMethodType getEncryptionMethodType()
	{
		IXencEncryptionMethodType binding = new IXencEncryptionMethodType()
		{
			protected IXencEncryptionMethodType inherits()
			{
				return null;
			}
		};

		binding.setXencKeySize(new IXencEncryptionMethodType.XencKeySize(getRandomInteger()));
		binding.setXencOAEPparams(new IXencEncryptionMethodType.XencOAEPparams(getBase64Binary()));
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
		binding.setXencAlgorithmAttr(new IXencEncryptionMethodType.XencAlgorithmAttr(getRandomString()));
		return binding;
	}
}
