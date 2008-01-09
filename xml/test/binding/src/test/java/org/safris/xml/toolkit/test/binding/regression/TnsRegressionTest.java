package org.safris.xml.toolkit.test.binding.regression;

import org.safris.xml.toolkit.test.binding.regression.DscRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.xmlsoap.schemas.soap.envelope.TnsBody;
import org.xmlsoap.schemas.soap.envelope.TnsEnvelope;
import org.xmlsoap.schemas.soap.envelope.TnsFault;
import org.xmlsoap.schemas.soap.envelope.TnsHeader;

public class TnsRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "http://schemas.xmlsoap.org/soap/envelope/";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new DscRegressionTest();

	public static void main(String[] args)
	{
		getEnvelope();
		getFault();
	}

	public static TnsEnvelope getEnvelope()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		TnsEnvelope binding = new TnsEnvelope();
		while(Math.random() < ADD_SEED)
			binding.setTnsHeader(getHeader());
		binding.setTnsBody(getBody());
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
//			while(Math.random() < ADD_SEED)
//				binding.addANYATTR(instance.getAnyAttribute(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static TnsHeader getHeader()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		TnsHeader binding = new TnsHeader();
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
//			do
//				binding.addANYATTR(instance.getAnyAttribute(getRandomString()));
//			while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static TnsBody getBody()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		TnsBody binding = new TnsBody();
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
//		do
//			binding.addANYATTR(instance.getAnyAttribute(getRandomString()));
//		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static TnsFault getFault()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		TnsFault binding = new TnsFault();
		binding.setTnsFaultcode(new TnsFault.TnsFaultcode(getRandomQName()));
		binding.setTnsFaultstring(new TnsFault.TnsFaultstring(getRandomString()));
		binding.setTnsFaultactor(new TnsFault.TnsFaultactor(getRandomString()));
		TnsFault.TnsDetail detail = new TnsFault.TnsDetail();
		while(Math.random() < ADD_SEED)
			detail.addANY(instance.getAny());
//		while(Math.random() < ADD_SEED)
//			detail.addANYATTR(instance.getAnyAttribute(getRandomString()));
		binding.setTnsDetail(detail);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}
}
