package org.safris.xml.toolkit.test.binding.regression;

import org.safris.xml.toolkit.test.binding.regression.DsRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.w3.x2000.x09.xmldsig.DsCanonicalizationMethod;
import org.w3.x2000.x09.xmldsig.DsDSAKeyValue;
import org.w3.x2000.x09.xmldsig.DsDigestMethod;
import org.w3.x2000.x09.xmldsig.DsDigestValue;
import org.w3.x2000.x09.xmldsig.DsKeyInfo;
import org.w3.x2000.x09.xmldsig.DsKeyName;
import org.w3.x2000.x09.xmldsig.DsKeyValue;
import org.w3.x2000.x09.xmldsig.DsManifest;
import org.w3.x2000.x09.xmldsig.DsMgmtData;
import org.w3.x2000.x09.xmldsig.DsObject;
import org.w3.x2000.x09.xmldsig.DsPGPData;
import org.w3.x2000.x09.xmldsig.DsRSAKeyValue;
import org.w3.x2000.x09.xmldsig.DsReference;
import org.w3.x2000.x09.xmldsig.DsRetrievalMethod;
import org.w3.x2000.x09.xmldsig.DsSPKIData;
import org.w3.x2000.x09.xmldsig.DsSignature;
import org.w3.x2000.x09.xmldsig.DsSignatureMethod;
import org.w3.x2000.x09.xmldsig.DsSignatureProperties;
import org.w3.x2000.x09.xmldsig.DsSignatureProperty;
import org.w3.x2000.x09.xmldsig.DsSignatureValue;
import org.w3.x2000.x09.xmldsig.DsSignedInfo;
import org.w3.x2000.x09.xmldsig.DsTransform;
import org.w3.x2000.x09.xmldsig.DsTransforms;
import org.w3.x2000.x09.xmldsig.DsX509Data;
import org.w3.x2000.x09.xmldsig.IDsX509IssuerSerialType;

public class DsRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "http://www.w3.org/2000/09/xmldsig#";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new DsRegressionTest();

	public static void main(String[] args)
	{
		getSignatureProperties();
		getManifest();
		getSignature();
	}

	public static DsSignature getSignature()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsSignature binding = new DsSignature();
		binding.setDsIdAttr(new DsSignature.DsIdAttr(getRandomString()));
		binding.setDsKeyInfo(getKeyInfo());
		do
		binding.addDsObject(getObject());
		while(Math.random() < ADD_SEED);
		binding.setDsSignatureValue(getSignatureValue());
		binding.setDsSignedInfo(getSignedInfo());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsSignatureValue getSignatureValue()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsSignatureValue binding = new DsSignatureValue();
		binding.setDsIdAttr(new DsSignatureValue.DsIdAttr(getRandomString()));
		binding.setTEXT(getBase64Binary());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsSignedInfo getSignedInfo()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsSignedInfo binding = new DsSignedInfo();
		binding.setDsCanonicalizationMethod(getCanonicalizationMethod());
		binding.setDsIdAttr(new DsSignedInfo.DsIdAttr(getRandomString()));
		do
		binding.addDsReference(getReference());
		while(Math.random() < ADD_SEED);
		binding.setDsSignatureMethod(getSignatureMethod());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsCanonicalizationMethod getCanonicalizationMethod()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsCanonicalizationMethod binding = new DsCanonicalizationMethod();
		binding.setDsAlgorithmAttr(new DsCanonicalizationMethod.DsAlgorithmAttr(getRandomString()));
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsSignatureMethod getSignatureMethod()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsSignatureMethod binding = new DsSignatureMethod();
		binding.setDsAlgorithmAttr(new DsSignatureMethod.DsAlgorithmAttr(getRandomString()));
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
		binding.setDsHMACOutputLength(new DsSignatureMethod.DsHMACOutputLength(getRandomInteger()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsReference getReference()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsReference binding = new DsReference();
		binding.setDsDigestMethod(getDigestMethod());
		binding.setDsDigestValue(getDigestValue());
		binding.setDsIdAttr(new DsReference.DsIdAttr(getRandomString()));
		binding.setDsTransforms(getTransforms());
		binding.setDsTypeAttr(new DsReference.DsTypeAttr(getRandomString()));
		binding.setDsURIAttr(new DsReference.DsURIAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsTransforms getTransforms()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsTransforms binding = new DsTransforms();
		do
		binding.addDsTransform(getTransform());
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsTransform getTransform()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsTransform binding = new DsTransform();
		binding.setDsAlgorithmAttr(new DsTransform.DsAlgorithmAttr(getRandomString()));
		double random = Math.random();
		if(random < 1 / 2)
			while(Math.random() < ADD_SEED)
				binding.addANY(instance.getAny());
		else
			while(Math.random() < ADD_SEED)
				binding.addDsXPath(new DsTransform.DsXPath(getRandomString()));

		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsDigestMethod getDigestMethod()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsDigestMethod binding = new DsDigestMethod();
		binding.setDsAlgorithmAttr(new DsDigestMethod.DsAlgorithmAttr(getRandomString()));
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsDigestValue getDigestValue()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsDigestValue binding = new DsDigestValue();
		binding.setTEXT(getBase64Binary());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsKeyInfo getKeyInfo()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsKeyInfo binding = new DsKeyInfo();
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
		do
		binding.addDsKeyName(getKeyName());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsKeyValue(getKeyValue());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsMgmtData(getMgmtData());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsPGPData(getPGPData());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsRetrievalMethod(getRetrievalMethod());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsSPKIData(getSPKIData());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsX509Data(getX509Data());
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsKeyName getKeyName()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsKeyName binding = new DsKeyName();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsMgmtData getMgmtData()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsMgmtData binding = new DsMgmtData();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsKeyValue getKeyValue()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsKeyValue binding = new DsKeyValue();
		double random = Math.random();
		if(random < 1 / 3)
			binding.setANY(instance.getAny());
		else if(random < 2 / 3)
			binding.setDsDSAKeyValue(getDsAKeyValue());
		else
			binding.setDsRSAKeyValue(getRSAKeyValue());
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsRetrievalMethod getRetrievalMethod()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsRetrievalMethod binding = new DsRetrievalMethod();
		binding.setDsTransforms(getTransforms());
		binding.setDsTypeAttr(new DsRetrievalMethod.DsTypeAttr(getRandomString()));
		binding.setDsURIAttr(new DsRetrievalMethod.DsURIAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsX509Data getX509Data()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsX509Data binding = new DsX509Data();
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());

		do
		binding.addDsX509Certificate(new DsX509Data.DsX509Certificate(getBase64Binary()));
		while(Math.random() < ADD_SEED);
		do
		binding.addDsX509CRL(new DsX509Data.DsX509CRL(getBase64Binary()));
		while(Math.random() < ADD_SEED);
		do
		binding.addDsX509IssuerSerial(getIX509IssuerSerialType());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsX509SKI(new DsX509Data.DsX509SKI(getBase64Binary()));
		while(Math.random() < ADD_SEED);
		do
		binding.addDsX509SubjectName(new DsX509Data.DsX509SubjectName(getRandomString()));
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsPGPData getPGPData()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsPGPData binding = new DsPGPData();
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
		binding.setDsPGPKeyID(new DsPGPData.DsPGPKeyID(getBase64Binary()));
		binding.setDsPGPKeyPacket(new DsPGPData.DsPGPKeyPacket(getBase64Binary()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsSPKIData getSPKIData()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsSPKIData binding = new DsSPKIData();
		// FIXME: This is an "if" because this is a
		// FIXME: <sequence maxOccurs="unbounded">. How do we deal with this?
		// FIXME: Make Sequence inner classes? Blah!
		if(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());

		do
		binding.addDsSPKISexp(new DsSPKIData.DsSPKISexp(getBase64Binary()));
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsObject getObject()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsObject binding = new DsObject();
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
		binding.setDsEncodingAttr(new DsObject.DsEncodingAttr(getRandomString()));
		binding.setDsIdAttr(new DsObject.DsIdAttr(getRandomString()));
		binding.setDsMimeTypeAttr(new DsObject.DsMimeTypeAttr(getRandomString()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsManifest getManifest()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsManifest binding = new DsManifest();
		do
		{
			binding.addDsReference(getReference());
		}
		while(Math.random() < ADD_SEED);
		binding.setDsIdAttr(new DsManifest.DsIdAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsSignatureProperties getSignatureProperties()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsSignatureProperties binding = new DsSignatureProperties();
		do
		binding.addDsSignatureProperty(getSignatureProperty());
		while(Math.random() < ADD_SEED);
		binding.setDsIdAttr(new DsSignatureProperties.DsIdAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsSignatureProperty getSignatureProperty()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsSignatureProperty binding = new DsSignatureProperty();
		do
		binding.addANY(instance.getAny());
		while(Math.random() < ADD_SEED);
		binding.setDsIdAttr(new DsSignatureProperty.DsIdAttr(getRandomString()));
		binding.setDsTargetAttr(new DsSignatureProperty.DsTargetAttr(getRandomString()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsDSAKeyValue getDsAKeyValue()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsDSAKeyValue binding = new DsDSAKeyValue();
		binding.setDsG(new DsDSAKeyValue.DsG(getBase64Binary()));
		binding.setDsJ(new DsDSAKeyValue.DsJ(getBase64Binary()));
		binding.setDsP(new DsDSAKeyValue.DsP(getBase64Binary()));
		binding.setDsPgenCounter(new DsDSAKeyValue.DsPgenCounter(getBase64Binary()));
		binding.setDsQ(new DsDSAKeyValue.DsQ(getBase64Binary()));
		binding.setDsSeed(new DsDSAKeyValue.DsSeed(getBase64Binary()));
		binding.setDsY(new DsDSAKeyValue.DsY(getBase64Binary()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsRSAKeyValue getRSAKeyValue()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsRSAKeyValue binding = new DsRSAKeyValue();
		binding.setDsExponent(new DsRSAKeyValue.DsExponent(getBase64Binary()));
		binding.setDsModulus(new DsRSAKeyValue.DsModulus(getBase64Binary()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static final IDsX509IssuerSerialType getIX509IssuerSerialType()
	{
		DsX509Data.DsX509IssuerSerial binding = new DsX509Data.DsX509IssuerSerial();
		binding.setDsX509IssuerName(new DsX509Data.DsX509IssuerSerial.DsX509IssuerName(getRandomString()));
		binding.setDsX509SerialNumber(new DsX509Data.DsX509IssuerSerial.DsX509SerialNumber(getRandomInteger()));
		return binding;
	}
}
